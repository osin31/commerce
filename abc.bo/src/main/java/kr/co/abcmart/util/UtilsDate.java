package kr.co.abcmart.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilsDate extends kr.co.abcmart.common.util.UtilsDate  {

	public static void getYearDateList(int year, List<Map<String, Object>> resultList) throws Exception {
		
		try {
			//1~12월까지 순환
	        for(int i=1; i<=12; i++){
	             //몇월 달력인지 출력, 요일 레이블 출력
	             
	             //Calendar객체를 이용하여 마지막 날짜 및 요일을 알아낼 수 있다.
	             Calendar calendar = Calendar.getInstance();
	             
	             calendar.set(year, (i-1), 1);
	             int lastDate = calendar.getActualMaximum(Calendar.DATE);
	             
	             //1일부터 해당 월의 마지막 날까지 순환
	             for(int j=1;j<=lastDate;j++){
	            	 Map<String, Object> dateInfo = new HashMap<String, Object>(); 
	            	 String locdate = "";
	            	 String month 	= (i < 10 ? "0"+i: String.valueOf(i));
	            	 String day 	= (j < 10 ? "0"+j: String.valueOf(j));
	            	 String isHoliday = "";
	            	 String dateName = "";
	            	 calendar.set(year, Integer.parseInt(month)-1, Integer.parseInt(day));
	            	 int dayNum = calendar.get(calendar.DAY_OF_WEEK);
	            	 
	            	 switch(dayNum){
			             case 1:
			            	 isHoliday = "Y";
			            	 dateName  = "일요일";
			                 break ;
			             case 2:
			            	 isHoliday = "N";
			                 break ;
			             case 3:
			            	 isHoliday = "N";
			                 break ;
			             case 4:
			            	 isHoliday = "N";
			                 break ;
			             case 5:
			            	 isHoliday = "N";
			                 break ;
			             case 6:
			            	 isHoliday = "N";
			                 break ;
			             case 7:
			            	 isHoliday = "Y";
			            	 dateName  = "토요일";
			                 break ;
	            	 }
	            	 locdate = year+month+day;
	            	 
	            	 dateInfo.put("dateName", dateName);
	            	 dateInfo.put("locdate", locdate);
	            	 dateInfo.put("isHoliday", isHoliday);
	            	 
	            	 resultList.add(dateInfo);
	             }
	        }
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}
	
}
