package kr.co.abcmart.common.excel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.abcmart.common.bean.Bean;

public interface Excel {

	/**
	 * 컬럼 이름을 등록 한다.
	 * 해당 방식은 index순서로 저장 된다.
	 * @param columnName
	 */
	public void addColumnNames(String columnName);

	/**
	 * 컬럼 이름을 등록 한다.
	 * 해당 방식은 key, value 방식으로 처리 된다.
	 * DB에서 읽어온 데이터의 컬럼 명이 KEY 값이 된다.
	 * 이때 이름을 따로 정의 하고 싶을 경우 컬럼 이름을 따로 작성 한다.
	 * .addColumn("NAME","이름")
	 * @param columnName
	 */
	public void addColumnNames(String key, String columnName);
	
	/**
	 * 컬럼 이름을 등록 한다.
	 * 해당 방식은 key, value 방식으로 처리 된다.
	 * DB에서 읽어온 데이터의 컬럼 명이 KEY 값이 된다.
	 * 이때 이름을 따로 정의 하고 싶을 경우 컬럼 이름을 따로 작성 한다.
	 * .addColumn(data)
	 * Map 형식으로 한번에 데이터 데이터를 처리할 경우 사용 한다.
	 * @param columnName 
	 */
	public void addColumnNames(LinkedHashMap<String,String> columnName);

	/**
	 * 컬럼 이름을 등록 한다.
	 * 해당 방식은 key, value 방식으로 처리 된다.
	 * DB에서 읽어온 데이터의 컬럼 명이 KEY 값이 된다.
	 * 이때 이름을 따로 정의 하고 싶을 경우 컬럼 이름을 따로 작성 한다.
	 * .addColumn(data)
	 * List 형식으로 한번에 데이터 데이터를 처리할 경우 사용 한다.
	 * @param columnName 
	 */
	public void addColumnNames(List<String> columnName);

	public void addData(List<?> data);
	
	public void addData(String[] data);

	public void addData(int[] data);

	public void addData(long[] data);

	public void addData(double[] data);
	
	public void addData(float[] data);
}
