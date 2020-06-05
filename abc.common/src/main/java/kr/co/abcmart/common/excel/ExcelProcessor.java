package kr.co.abcmart.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abcmart.common.bean.Bean;
import lombok.extern.slf4j.Slf4j;

/***
 * Excel 파일 형식으로 데이터를 처리한다.
 * 
 * @author 장진철 zerocooldog
 */

@Slf4j
public class ExcelProcessor implements Excel {

	private final int DEFAULT_SHEET_INDEX = 0;

	private List<String> excludeColumn = new ArrayList<>();

	private int topPosition = 0;

	private int leftPosition = 0;

	private boolean template = false;

	private boolean isAddColumnNames = false;

	private int ROW_ACCESS_WINDOW_SIZE = 10000;

	private AtomicInteger columnIndex = new AtomicInteger(0);

	private LinkedHashMap<String, String> columnNames = new LinkedHashMap<>();
	private LinkedHashMap<String, Integer> columnNamesIndex = new LinkedHashMap<>();

	private SXSSFWorkbook workBook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);

	private XSSFCellStyle fontStyle;
	private XSSFCellStyle floatStyle;
	private XSSFCellStyle dateStyle;

	public ExcelProcessor() {
		workBook.createSheet();
		setDefaultCellStyle();
		excludeColumn.add("log");
	}

	public ExcelProcessor(XSSFWorkbook xssWorkBook) {

		workBook = new SXSSFWorkbook(xssWorkBook, ROW_ACCESS_WINDOW_SIZE);
		setDefaultCellStyle();
		excludeColumn.add("log");
	}

	public List<String> getExcludeColumn() {
		return excludeColumn;
	}

	public void setExcludeColumn(List<String> excludeColumn) {

		for (String exclude : excludeColumn) {
			this.excludeColumn.add(exclude);
		}
	}

	public int getTopPosition() {
		return topPosition;
	}

	public void setTopPosition(int topPosition) {
		this.topPosition = topPosition;
	}

	public int getLeftPosition() {
		return leftPosition;
	}

	public void setLeftPosition(int leftPosition) {
		this.leftPosition = leftPosition;
	}

	public boolean isTemplate() {
		return template;
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}

	private void setDefaultCellStyle() {

		fontStyle = (XSSFCellStyle) workBook.createCellStyle();
		Font font = workBook.createFont();
		font.setFontName("맑은 고딕");
		font.setFontHeightInPoints((short) 9);
		fontStyle.setFont(font);

		floatStyle = (XSSFCellStyle) workBook.createCellStyle();
		floatStyle.setDataFormat(workBook.createDataFormat().getFormat("0.00"));
		floatStyle.setFont(font);

		dateStyle = (XSSFCellStyle) workBook.createCellStyle();
		dateStyle.setDataFormat(workBook.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
		dateStyle.setFont(font);

	}

	public Sheet addSheet() {
		return workBook.createSheet();
	}

	public Sheet addSheet(String sheetName) {
		return workBook.createSheet(sheetName);
	}

	@Override
	public void addColumnNames(String columnName) {

		// exclude 컬럼이 있으면 엑셀에 생성하지 않는다.
		if (excludeColumn.contains(columnName)) {
			return;
		}

		int index = columnIndex.get();

		columnNames.put(columnName, columnName);
		columnNamesIndex.put(columnName, index);
		columnIndex.incrementAndGet();
	}

	@Override
	public void addColumnNames(String columnNameKey, String columnName) {

		// exclude 컬럼이 있으면 엑셀에 생성하지 않는다.
		if (excludeColumn.contains(columnNameKey)) {
			return;
		}

		int index = columnIndex.get();
		columnNames.put(columnNameKey, columnName);
		columnNamesIndex.put(columnNameKey, index);
		columnIndex.incrementAndGet();

	}

	@Override
	public void addColumnNames(LinkedHashMap<String, String> columnHeader) {

		this.columnNames = columnHeader.entrySet().stream().filter(e -> !excludeColumn.contains(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, null, LinkedHashMap::new));

		for (String key : columnHeader.keySet()) {
			columnNamesIndex.put(key, columnIndex.getAndIncrement());
		}
	}

	@Override
	public void addColumnNames(List<String> columnNames) {

		for (String columnName : columnNames) {
			if (excludeColumn.contains(columnName)) {
				continue;
			}

			this.columnNames.put(columnName, columnName);
			this.columnNamesIndex.put(columnName, columnIndex.getAndIncrement());
		}
	}

	private void setCell(int rowIndex, Sheet sheet, Object data) throws Exception {

		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}

		for (String columnNameKey : columnNamesIndex.keySet()) {

			// exclude 컬럼이 있거나, 컬럼 index정보가 없으면 엑셀에 생성하지 않는다.
			if (excludeColumn.contains(columnNameKey) || columnNamesIndex.get(columnNameKey) == null) {
				continue;
			}

			Object value = (data instanceof Map) ? ((Map) data).get(columnNameKey)
					: PropertyUtils.getProperty(data, columnNameKey);

			int columnIndex = columnNamesIndex.get(columnNameKey);

			Cell cell = row.getCell(leftPosition + columnIndex);
			if (cell == null) {
				cell = row.createCell(leftPosition + columnIndex);
			}

			setValue(cell, value);
		}
	}

	private Row addRowColumnHeader(Sheet sheet) {

		// 템플릿 방식이면 0번째 row컬럼 헤더를 만들지 않는다.
		if (template || isAddColumnNames) {
			return null;
		}

		Row row = sheet.getRow(topPosition);
		if (row == null) {
			row = sheet.createRow(topPosition);
		}

		int headerCellIndex = leftPosition;

		for (String columnNameKey : columnNames.keySet()) {

			if (excludeColumn.contains(columnNameKey)) {
				continue;
			}

			String value = columnNames.get(columnNameKey);

			Cell cell = row.createCell(headerCellIndex);
			cell.setCellValue(value);

			headerCellIndex++;
		}

		isAddColumnNames = true;

		return row;
	}

	@Override
	public void addData(List<?> data) {

		// 템플릿이 아니고 컬럼 명이 아무 값도 없으면 첫번째 row에 column명으로 헤더 값이 생긴다.
		// 실질적은 데이터는 헤더 row 다음에 위치하도록 +1값을 지정한다.

		SXSSFSheet sheet = workBook.getSheetAt(DEFAULT_SHEET_INDEX);

		int dataSize = data.size();
		int startRow = (!template) ? topPosition + 1 : topPosition;

		try {

			for (int i = 0; i < dataSize; i++) {

				Object rowData = data.get(i);

				if (rowData instanceof Map) {

					Map<String, Object> cellData = (Map<String, Object>) rowData;

					if (columnNames.size() == 0) {

						Set<String> keySet = cellData.keySet();

						for (String key : keySet) {
							addColumnNames(key);
						}
					}

					addRowColumnHeader(sheet);
					setCell(startRow + i, sheet, rowData);

				} else if (rowData instanceof Bean) {

					if (columnNames.size() == 0) {

						Field[] fields = FieldUtils.getAllFields(rowData.getClass());

						for (Field field : fields) {
							addColumnNames(field.getName());
						}
					}

					addRowColumnHeader(sheet);
					setCell(startRow + i, sheet, rowData);
				}
			}

			sheet.flushRows(dataSize + startRow);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addData(String[] data) {

		Sheet sheet = workBook.getSheetAt(DEFAULT_SHEET_INDEX);
		Row row = sheet.createRow(sheet.getLastRowNum());

		int startCell = 0;

		for (String cellValue : data) {
			Cell cell = row.createCell(startCell);
			setValue(cell, cellValue);
			startCell++;
		}
	}

	@Override
	public void addData(int[] data) {

		Sheet sheet = workBook.getSheetAt(DEFAULT_SHEET_INDEX);
		Row row = sheet.createRow(sheet.getLastRowNum());

		int startCell = 0;

		for (int cellValue : data) {
			Cell cell = row.createCell(startCell);
			setValue(cell, cellValue);
			startCell++;
		}
	}

	@Override
	public void addData(long[] data) {

		Sheet sheet = workBook.getSheetAt(DEFAULT_SHEET_INDEX);
		Row row = sheet.createRow(sheet.getLastRowNum());

		int startCell = 0;

		for (long cellValue : data) {
			Cell cell = row.createCell(startCell);
			setValue(cell, cellValue);
			startCell++;
		}
	}

	@Override
	public void addData(double[] data) {

		Sheet sheet = workBook.getSheetAt(DEFAULT_SHEET_INDEX);
		Row row = sheet.createRow(sheet.getLastRowNum());

		int startCell = 0;

		for (double cellValue : data) {
			Cell cell = row.createCell(startCell);
			setValue(cell, cellValue);
			startCell++;
		}
	}

	@Override
	public void addData(float[] data) {

		Sheet sheet = workBook.getSheetAt(DEFAULT_SHEET_INDEX);
		Row row = sheet.createRow(sheet.getLastRowNum());

		int startCell = 0;

		for (float cellValue : data) {
			Cell cell = row.createCell(startCell);
			setValue(cell, cellValue);
			startCell++;
		}
	}

	private void setValue(Cell cell, Object value) {

		try {

			cell.setCellStyle(fontStyle);

			if (value instanceof Integer) {
				cell.setCellValue(String.valueOf(value));
			} else if (value instanceof Long) {
				cell.setCellValue(String.valueOf(value));
			} else if (value instanceof Float) {
				cell.setCellStyle(floatStyle);
				cell.setCellValue(String.valueOf(value));
			} else if (value instanceof Double) {
				cell.setCellValue((double) value);
			} else if (value instanceof Boolean) {
				cell.setCellValue((boolean) value);
			} else if (value instanceof Calendar) {
				cell.setCellValue((Calendar) value);
			} else if (value instanceof Date) {
				cell.setCellStyle(dateStyle);
				cell.setCellValue((Date) value);
			} else if (value instanceof java.sql.Timestamp) {
				cell.setCellStyle(dateStyle);
				cell.setCellValue(new Date(((Timestamp) value).getTime()));
			} else {
				cell.setCellValue((String) value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(OutputStream outputStream) throws IOException {
		workBook.write(outputStream);
	}

	public void dispose() {
		workBook.dispose();
	}

}
