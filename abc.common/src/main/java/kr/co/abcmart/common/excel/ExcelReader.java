package kr.co.abcmart.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReader<T> {

	@SuppressWarnings("hiding")
	public interface RowConverter<T> {
		T convert(Sheet sheet, int rowNum, String[] row);
	}

	private static final int MAX_SHEET = 1;

	public static class Builder {
		private RowConverter<?> converter;
		private int rowCacheSize = 100;
		private int bufferSize = 4096;
		private int topPosition = 0;
		private int leftPosition = 0;

		public Builder(int topPosition, int leftPosition) {
			this.topPosition = topPosition;
			this.leftPosition = leftPosition;
		}

		public Builder(int topPosition) {
			this.topPosition = topPosition;
		}

		public Builder() {
		}

		public Builder converter(RowConverter<?> converter) {
			this.converter = converter;
			return this;
		}

		public Builder rowCacheSize(int rowCacheSize) {
			this.rowCacheSize = rowCacheSize;
			return this;
		}

		public Builder bufferSize(int bufferSize) {
			this.bufferSize = bufferSize;
			return this;
		}

		public ExcelReader build() {
			return new ExcelReader(this);
		}
	}

	private Builder info;

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(int topPosition) {
		return new Builder(topPosition);
	}

	public static Builder builder(int topPosition, int leftPosition) {
		return new Builder(topPosition, leftPosition);
	}

	private ExcelReader(Builder info) {
		this.info = info;
	}

	private Workbook getWorkBook(InputStream is) {
		return StreamingReader.builder().rowCacheSize(this.info.rowCacheSize).bufferSize(this.info.bufferSize).open(is);
	}

	public List<?> read(int sheetNum, String fileName) throws Exception {
		try (FileInputStream is = new FileInputStream(fileName)) {
			return read(sheetNum, is);
		}
	}

	public List<?> read(String sheetName, String fileName) throws Exception {
		try (FileInputStream is = new FileInputStream(fileName)) {
			return read(sheetName, is);
		}
	}

	public List<?> read(String fileName) throws Exception {
		try (FileInputStream is = new FileInputStream(fileName)) {
			return read(is);
		}
	}

	public List<T> read(int sheetNum, File file) throws Exception {
		try (FileInputStream is = new FileInputStream(file)) {
			return read(sheetNum, is);
		}
	}

	public List<T> read(String sheetName, File file) throws Exception {
		try (FileInputStream is = new FileInputStream(file)) {
			return read(sheetName, is);
		}
	}

	public List<T> read(File file) throws Exception {
		try (FileInputStream is = new FileInputStream(file)) {
			return read(is);
		}
	}

	public List<T> read(InputStream is) throws Exception {
		return read(is, false);
	}

	public List<T> read(InputStream is, boolean allSheet) throws Exception {
		Workbook workbook = getWorkBook(is);

		List<T> objList = new ArrayList<>();
		if (allSheet) {
			for (Sheet sheet : workbook) {
				objList.add(readXlsx(sheet, is).get(0));
			}
		} else {
			objList = readXlsx(workbook.getSheetAt(0), is);
		}

		workbook.close();

		return objList;
	}

	public List<T> read(int sheetNum, InputStream is) throws Exception {
		Workbook workbook = getWorkBook(is);
		List<T> objList = readXlsx(workbook.getSheetAt(sheetNum), is);
		workbook.close();

		return objList;
	}

	public List<T> read(String sheetName, InputStream is) throws Exception {

		Workbook workbook = getWorkBook(is);
		List<T> objList = readXlsx(workbook.getSheet(sheetName), is);
		workbook.close();

		return objList;
	}

	@SuppressWarnings("unchecked")
	private List<T> readXlsx(Sheet sheet, InputStream is) throws Exception {

		List<T> objList = new ArrayList<>();

		for (Row r : sheet) {

			if (info.topPosition > 0 && info.topPosition > r.getRowNum()) {
				continue;
			}

			int tmpLength = getCellLength(
					(info.leftPosition > 0 && info.leftPosition > r.getFirstCellNum()) ? info.leftPosition
							: r.getFirstCellNum(),
					r.getLastCellNum());
			String[] rowData = new String[tmpLength];

			int i = 0;
			for (Cell c : r) {

				if (info.leftPosition > 0 && c.getColumnIndex() < info.leftPosition) {
					continue;
				}

//    			cell.getStringCellValue()
//    			rowData[i] = getValue(c);
				rowData[i] = c.getStringCellValue();
				i++;
			}
			T object = (T) info.converter.convert(sheet, r.getRowNum(), rowData);
			if (object != null) {
				objList.add(object);
			}
		}

		return objList;
	}

	private int getCellLength(int startCellIndex, int endCellIndex) {
		return endCellIndex - startCellIndex;
	}

	@SuppressWarnings("unused")
	private Object getValue(Cell cell) {

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return cell.getNumericCellValue();
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case ERROR:
			return cell.getErrorCellValue();
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
			return null;
		default:
			break;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		String excelPath = "D:\\project\\abc\\workspace\\abc.sample\\src\\main\\resources\\templates\\_board.xlsx";

//    	
		ExcelReader reader = ExcelReader.builder(2).converter((Sheet sheet, int rowNum, String[] row) -> {
			SampleExcelReader ser = new SampleExcelReader();
			ser.setTitle(row[0]);
			ser.setName(row[1]);
			ser.setAge(row[2]);
			ser.setDate(row[3]);
			return ser;
		}).build();

		List<SampleExcelReader> list = reader.read(excelPath);

		log.debug("{}", list);
//    	log.debug("end {}");
	}

	@ToString
	@Data
	public static class SampleExcelReader {
		public String title;
		public String name;
		public String age;
		public String date;
	}
}
