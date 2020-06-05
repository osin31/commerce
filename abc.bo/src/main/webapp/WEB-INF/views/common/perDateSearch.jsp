<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<span class="date-box">
		<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
</span>
<span class="text">~</span>
<span class="date-box">
		<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
</span>
<span class="btn-group">
	<a href="javascript:void(0);" name="perToday" 		class="btn-sm btn-func">오늘</a>
	<a href="javascript:void(0);" name="perWeek" 		class="btn-sm btn-func">일주일</a>
	<a href="javascript:void(0);" name="perOneMonth" 	class="btn-sm btn-func">한달</a>
	<a href="javascript:void(0);" name="perYearMonth" 	class="btn-sm btn-func">1년</a>
</span>