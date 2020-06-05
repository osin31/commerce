package kr.co.abcmart.common.excel;

import java.util.LinkedHashMap;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/***
 * Excel 파일 형식으로 데이터를 처리한다.
 * @author 장진철 zerocooldog
 */

@Slf4j
public class ExcelValue {
	
	/**
	 * 엑셀 데이터 삽입 top row 위치
	 */

	@Getter
	private int topPosition;
	
	/**
	 * 엑셀 데이터 삽입 left column 위치
	 */
	@Getter
	private int leftPosition;
	
	/**
	 * 컬럼 정보.
	 */
	@Getter
	private Object columnNames;

	@Getter
	private Object data;

	/**
	 * 엑셀 데이터 삽입 시 제외 할 컬럼 명.
	 */
	@Getter
	private List<String> exclude;

	
	public static class Builder {
        private int topPosition 		= 0;
        private int leftPosition 		= 0;

        private Object columnNames    = null;
        private Object data           = null;
        private List<String> exclude  = null;

        public Builder() {}
        
        public Builder(int topPosition) {
            this.topPosition 	= topPosition;
        }
        
        public Builder(int topPosition, int leftPosition) {
            this.topPosition 	= topPosition;
            this.leftPosition   = leftPosition;
        }

        public Builder columnNames(LinkedHashMap<String, String> columnNames) {
        	this.columnNames = columnNames;
            return this; 
        }
        public Builder columnNames(List<String> columnNames) {
        	this.columnNames = columnNames;
            return this; 
        }
        public Builder exclude(List<String> exclude) {
            this.exclude = exclude;
            return this;
        }
        public Builder data(List<?> data) {
            this.data = data;
            return this;
        }
        public ExcelValue build() {
            return new ExcelValue(this);
        }
    }
	
    private ExcelValue(Builder builder) {
    	
    	this.topPosition 		= builder.topPosition;
    	this.leftPosition 		= builder.leftPosition;
    	this.columnNames 		= builder.columnNames;
    	this.exclude 			= builder.exclude;
    	this.data 				= builder.data;

    }
    
    public static Builder builder() {
		return new Builder();
    }
    public static Builder builder(int topPosition) {
		return new Builder(topPosition);
    }
    public static Builder builder(int topPosition, int leftPosition) {
		return new Builder(topPosition, leftPosition);
    }
}

