package kr.co.abcmart.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.exception.DateUtilsException;

public class UtilsDate extends DateUtils{
	
	public static final String DEFAULT_DATE_PATTERN = BaseConst.DEFAULT_DATE_PATTERN;
	public static final String DEFAULT_DATE_PATTERN_NOT_CHARACTERS = BaseConst.DEFAULT_DATE_PATTERN_NOT_CHARACTERS;
	public static final String DEFAULT_DATETIME_PATTERN = BaseConst.DEFAULT_DATETIME_PATTERN;
	public static final String DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS = BaseConst.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS;
	public static final String DEFAULT_DATETIME_MILI_PATTERN = BaseConst.DEFAULT_DATETIME_MILI_PATTERN;
	public static final String DEFAULT_DATETIME_MILI_PATTERN_NOT_CHARACTERS = BaseConst.DEFAULT_DATETIME_MILI_PATTERN_NOT_CHARACTERS;

	/***
	 * System.currentTimeMillis() 시간을 java.sql.Date 형식으로 변경한다.
	 * System.currentTimeMillis() 시간을 DB에 저장 시 java.sql.Date 형식으로 변환 할 경우 사용 한다.
	 * @return
	 */
    public static java.sql.Date getSqlDate() {
        return getSqlDate(new Date(System.currentTimeMillis()));
    }
    
	/***
	 * java.util.Date 시간을 java.sql.Date 형식으로 변경한다.
	 * java.util.Date 시간을 DB에 저장 시 java.sql.Date 형식으로 변환 할 경우 사용 한다.
	 * 
	 * @param date java.sql.Date
	 * @return
	 */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }
    
	/***
	 * System.currentTimeMillis() 시간을 java.sql.Timestamp 형식으로 변경한다.
	 * System.currentTimeMillis() 시간을 DB에 저장 시 java.sql.Timestamp 형식으로 변환 할 경우 사용 한다.
	 * @return
	 */
    public static java.sql.Timestamp getSqlTimeStamp() {
        return getSqlTimeStamp(new Date(System.currentTimeMillis()));
    }
    
	/***
	 * java.util.Date 시간을 java.sql.Timestamp 형식으로 변경한다.
	 * java.util.Date 시간을 DB에 저장 시 java.sql.Timestamp 형식으로 변환 할 경우 사용 한다.
	 * @param date java.util.Date
	 * @return
	 */
    public static java.sql.Timestamp getSqlTimeStamp(Date date) {
        return new java.sql.Timestamp(date.getTime());
    }
    
	/***
	 * java.sql.Date 시간을 java.sql.Timestamp 형식으로 변경한다.
	 * java.sql.Date 시간을 DB에 저장 시 java.sql.Timestamp 형식으로 변환 할 경우 사용 한다.
	 *
	 * @param date java.sql.Date
	 * @return
	 */
    public static java.sql.Timestamp getSqlTimeStamp(java.sql.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }
    
    /***
     * 현재 날짜 정보를 가져온다.
     * @return Date
     */
    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }
    
	/***
	 * java.sql.Timestamp 시간을 java.util.Date 형식으로 변경한다.
	 * 
	 * @param timestamp java.sql.Timestamp
	 * @return java.util.Date
	 */
    public static Date getDate(java.sql.Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
    
    /**
     * 현재 날짜 정보를 읽어온다.
     * 기본 포맷 : yyyy-MM-dd 
     * @return String
     */
    public static String today() {
    	return today(DEFAULT_DATE_PATTERN);
    }
    
    /**
     * 현재 날짜 정보를 읽어온다.
     * @param format 날짜 포맷 을 지정
     * @return String
     */
    public static String today(String format) {
        return formatter(format, getDate());
    }
    
    /***
     * milisecond형식의 날짜 데이터를 기본 date 패턴에 맞게 변경한다.
     *  
     * @param date milisecond
     * @return String
     */
    public static String formatter(long date) {
    	if(date <= 0) {
    		return null;
    	}
        return formatter(DEFAULT_DATE_PATTERN, new Date(date));
    }
    
    /***
     * milisecond형식의 날짜 데이터를 date 패턴에 맞게 변경한다.
     *  
     * @param format date pattern
     * @param date milisecond
     * @return String
     */
    public static String formatter(String format, long date) {
    	if(date <= 0) {
    		return null;
    	}
        return formatter(format, new Date(date));
    }
    
    /***
     * Date 객체의 날짜 데이터를 기본 date 패턴에 맞게 변경한다.
     *  
     * @param date java.util.Date
     * @return String
     */
    public static String formatter(Date date) {
        return formatter(DEFAULT_DATE_PATTERN, date);
    }
    
    /***
     * Date 객체의 날짜 데이터를 date 패턴에 맞게 변경한다.
     *     
     * @param format date pattern
     * @param date java.util.Date
     * @return String
     */
    public static String formatter(String format,Date date) {
    	
    	String value = null;
    	
    	try {
    		
        	if( date == null) {
        		throw new DateUtilsException("The date value is null.");
        	}			
        	
        	SimpleDateFormat sdf = new SimpleDateFormat(format);
        	value = sdf.format(date);
        	
    	} catch (Exception e) {
			e.printStackTrace();
		}
        return value;
    }
    
    /***
     * 문자열 date 형식을 java.util.Date 객체로 변경한다.
     *     
     * @param date 문자열 형식의 date 데이터
     * @param pattern date pattern
     * @return String
     */
    public static Date parseDate(String date,String pattern) {
    	try {
			return DateUtils.parseDate(date, pattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /***
     * 문자열 date 형식을 java.util.Date 객체로 변경한다.
     *     
     * @param date 문자열 형식의 date 데이터
     * @param pattern date pattern
     * @param locale 국가 지역에 따른 date객체를 생성한다.
     * @return String
     */
    public static Date parseDate(String date,String pattern,Locale locale) {
    	
    	try {
			return DateUtils.parseDate(date, locale, pattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }

    
    

    
	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM uuuu").withLocale(Locale.getDefault());

		System.out.println(formatter("yyyy-MM-dd HH:mm:ss",new Date(1543469640000l)));
	}

}
