<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
$(function(){
	// 시작일
	var toDate = new Date();
	$("#startDate").datepicker("option", "minDate", toDate);
	$("#startDate").datepicker("setDate", toDate);
	
	// 종료일 default setting
	$("#endDate").datepicker("setDate", "${Const.DEFAULT_END_DATE}");
	
	// save btn
	$("#scheduleSaveBtn").off().on("click", function() {
		abc.biz.system.batch.saveSchedule();
	});
});
</script>

<body class="window-body">
	<form name="scheduleForm" id="scheduleForm">
	<input type="hidden" name="triggerStartTime" id="triggerStartTime">
	<input type="hidden" name="triggerEndTime" id="triggerEndTime">
	<div class="window-wrap">
		<div class="window-title">
			<h1>배치 업무별 일정 등록</h1>
		</div>
		<div class="window-content">

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">기본정보</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>기본정보</caption>
				<colgroup>
					<col style="width: 135px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">사이트명</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="사이트 선택" name="siteId" id="siteId">
							<c:forEach items="${siteList}" var="site" varStatus="status">
								<option value="${site.siteNo}">${site.siteName}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">업무 그룹</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="업무 그룹 선택" name="jobGroup" id="jobGroup">
							<c:forEach items="${jobGroupList}" var="code" varStatus="status">
								<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">업무명</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="업무명 입력" name="jobName" id="jobName">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">배치명</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="배치명 입력" name="triggerName" id="triggerName">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span>파라메터</span>
						</th>
						<td class="input">
							<div class="ip-text-box">
								<input type="text" class="ui-input" title="파라메터 입력" name="parameters" id="parameters">
								<span class="text">Ex) queryString : a=b&amp;c=d</span>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row">배치설명</th>
						<td class="input">
							<textarea class="ui-textarea" title="배치설명 입력" name="description" id="triggerDescription"></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">일정 표현식</span>
						</th>
						<td class="input">
							<input type="text" class="ui-input" title="일정 표현식 입력" name="cronExpression" id="cronExpression">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">일정 시작일</span>
						</th>
						<td class="input">
							<!-- S : 20180114 수정 // 일정 시작일 마크업 수정 -->
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<!-- S : date-box -->
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="일정 시작일 선택" id="startDate">
								</span>
								<!-- E : date-box -->

								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="시작 시 선택" id="startHour">
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
									<span class="text">시</span>
								</span>
								<!-- E : ip-text-box -->

								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="시작 분 선택" id="startMinute">
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31">31</option>
										<option value="32">32</option>
										<option value="33">33</option>
										<option value="34">34</option>
										<option value="35">35</option>
										<option value="36">36</option>
										<option value="37">37</option>
										<option value="38">38</option>
										<option value="39">39</option>
										<option value="40">40</option>
										<option value="41">41</option>
										<option value="42">42</option>
										<option value="43">43</option>
										<option value="44">44</option>
										<option value="45">45</option>
										<option value="46">46</option>
										<option value="47">47</option>
										<option value="48">48</option>
										<option value="49">49</option>
										<option value="51">51</option>
										<option value="52">52</option>
										<option value="53">53</option>
										<option value="54">54</option>
										<option value="55">55</option>
										<option value="56">56</option>
										<option value="57">57</option>
										<option value="58">58</option>
										<option value="59">59</option>
									</select>
									<span class="text">분</span>
								</span>
								<!-- E : ip-text-box -->
							</span>
							<!-- S : term-date-wrap -->
							<!-- E : 20180114 수정 // 일정 시작일 마크업 수정 -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">일정 종료일</span>
						</th>
						<td class="input">
							<!-- S : 20180114 수정 // 일정 시작일 마크업 수정 -->
							<!-- S : term-date-wrap -->
							<span class="term-date-wrap">
								<!-- S : date-box -->
								<span class="date-box">
									<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="일정 종료일 선택" id="endDate">
								</span>
								<!-- E : date-box -->

								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="종료 시 선택" id="endHour">
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23" selected>23</option>
									</select>
									<span class="text">시</span>
								</span>
								<!-- E : ip-text-box -->

								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select class="ui-sel" title="종료 분 선택" id="endMinute">
										<option value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31">31</option>
										<option value="32">32</option>
										<option value="33">33</option>
										<option value="34">34</option>
										<option value="35">35</option>
										<option value="36">36</option>
										<option value="37">37</option>
										<option value="38">38</option>
										<option value="39">39</option>
										<option value="40">40</option>
										<option value="41">41</option>
										<option value="42">42</option>
										<option value="43">43</option>
										<option value="44">44</option>
										<option value="45">45</option>
										<option value="46">46</option>
										<option value="47">47</option>
										<option value="48">48</option>
										<option value="49">49</option>
										<option value="51">51</option>
										<option value="52">52</option>
										<option value="53">53</option>
										<option value="54">54</option>
										<option value="55">55</option>
										<option value="56">56</option>
										<option value="57">57</option>
										<option value="58">58</option>
										<option value="59" selected>59</option>
									</select>
									<span class="text">분</span>
								</span>
								<!-- E : ip-text-box -->
							</span>
							<!-- S : term-date-wrap -->
							<!-- E : 20180114 수정 // 일정 시작일 마크업 수정 -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="#" class="btn-normal btn-save" id="scheduleSaveBtn">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	</form>
	
	<script src="/static/common/js/biz/system/abcmart.system.batch.js<%=_DP_REV%>"></script>
	
</body>
</html>