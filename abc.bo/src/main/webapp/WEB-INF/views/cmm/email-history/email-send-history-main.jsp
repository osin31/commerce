<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("mailGrid"), "mailSheet");
		<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var initSheet = {};

		var listPageCount = ('<c:out value="${param.listPageCount}"/>' == '') ?  $('#listPageCount').val(): '<c:out value="${param.listPageCount}"/>';
		$('#listPageCount').val(listPageCount);

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:listPageCount};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"상태",			Type:"Status",		SaveName:"Status", 				Width:10,	Hidden:1}
			, {Header:"",			Type:"Text",		SaveName:"emailSndrSeq",		Width:5, 	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"번호", 		Type:"Seq", 		SaveName:"", 					Width:5, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사이트",		Type:"Text",		SaveName:"siteName",			Width:8, 	Align:"Center",		Edit:0, Sort:0}
			, {Header:"제목",			Type:"Text",		SaveName:"emailTitleText",		Width:15, 	Align:"Left", 		Edit:0, FontUnderline:1, Sort:0}
			, {Header:"분류",			Type:"Text",		SaveName:"adminSendYnName",		Width:5, 	Align:"Center", 	Edit:0}
			, {Header:"보낸사람",		Type:"Text",		SaveName:"gridAdminInfo",		Width:10, 	Align:"Center",		Edit:0, Sort:0}
			, {Header:"보낸 이메일",		Type:"Text",		SaveName:"gridSendEmailInfo",	Width:15, 	Align:"Center",		Edit:0, Sort:0}
			, {Header:"발송여부",		Type:"Combo",		SaveName:"sendYn",				Width:10, 	Align:"Center", 	Edit:0, Sort:0, ComboText : "발송|미발송",	 ComboCode : "Y|N"}
			, {Header:"등록일시",		Type:"Text",		SaveName:"rgstDtm",				Width:10, 	Align:"Center", 	Edit:0, Format:"YmdHms"}
			, {Header:"받는사람",		Type:"Text",		SaveName:"gridMemberInfo",		Width:10, 	Align:"Center",		Edit:0, FontUnderline:1, Sort:0}
			, {Header:"받는이메일",		Type:"Text",		SaveName:"gridRcvrEmailInfo",	Width:15, 	Align:"Center",		Edit:0, Sort:0}
			, {Header:"발송일시",		Type:"Text",		SaveName:"sendDtm",				Width:10, 	Align:"Center", 	Edit:0, Format:"YmdHms"}
			, {Header:"등록자",		Type:"Text",		SaveName:"rgsterNo",			Width:10, 	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"회원번호",		Type:"Text",		SaveName:"memberNo",			Width:10, 	Align:"Center", 	Edit:0, Sort:0, Hidden:1}
		];

		<%-- Grid 초기화 --%>
		IBS_InitSheet(mailSheet , initSheet);
		<%-- Grid 건수 정보 표시 --%>
		mailSheet.SetCountPosition(3);
		<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		mailSheet.SetPagingPosition(2);
		<%-- Grid width 자동 조절 --%>
		mailSheet.FitColWidth();
		<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		mailSheet.SetExtendLastCol(1);

		<%-- 캘린더 버튼 설정(일) --%>
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});

		<%-- 캘린더 버튼 설정(주)--%>
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});

		<%-- 캘린더 버튼 설정(월) --%>
		$("a[name^=per1Month]").click(function(){
			abc.date.month(this);
		});

		<%-- 캘린더 버튼 설정(1년) --%>
		$("a[name^=perYear]").click(function(){
			abc.date.year(this);
		});

		$("#searchBtn").click(function(f) {
			doAction("search");	// 메일 발송 이력 리스트 그리드 조회
		});

		// 초기화
		$("#resetBtn").click(function(f) {
			$.form("#mailHistoryForm").reset();
			$('#per1Month').trigger('click');
			$("input:radio[name='siteNo'][value='ALL']").prop('checked', true);
		});

		// 초기에는 무조건 한달, 상세에서 목록으로 올 때는 파라미터의 날짜를 세팅해야함, 추후수정-최경호
		$('#per1Month').trigger('click');

		<c:if test="${not empty param.emailSndrSeq}">
			$("input:radio[name='siteNo'][value='<c:out value='${param.siteNo}'/>']").prop('checked', true);
			$('#rcvrSearchType').val('<c:out value="${param.rcvrSearchType}"/>');
			$('#rcvrSearchText').val('<c:out value="${param.rcvrSearchText}"/>');
			$('#emailTitleText').val('<c:out value="${param.emailTitleText}"/>');
			$("input:radio[name='adminSendYn'][value='<c:out value='${param.adminSendYn}'/>']").prop('checked', true);
			$('#dateSearchType').val('<c:out value="${param.dateSearchType}"/>');
			$('#fromDate').val('<c:out value="${param.fromDate}"/>');
			$('#toDate').val('<c:out value="${param.toDate}"/>');
			$('#sendYn').val('<c:out value="${param.sendYn}"/>');

			var jsonArr = '<c:out value="${param.arrSendYn}" escapeXml="false" />';
			jsonArr 	= JSON.parse(jsonArr);

			for(i=0; i<jsonArr.length; i++){
				$("input[id='"+ jsonArr[i].id+"']").prop("checked", jsonArr[i].flag);
			}

			doAction("search");	// 그리드 리스트 조회
		</c:if>
			
		// enter 검색
		$(".search-wrap")
		.find(".ui-input, .js-ui-cal")
		.filter("input[type=text]:not([readonly]):not([disabled])")
		.off().on("keyup", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
		    }
		});

	});

	<%-- 코드그룹 그리드 Click 이벤트 --%>
	function mailSheet_OnClick(Row, Col, Value) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			if (mailSheet.ColSaveName(Col) == "emailTitleText") {	<%-- 메일발송 이력 상세보기 --%>
				var emailSndrSeq = mailSheet.GetCellValue(Row, "emailSndrSeq");
				var pageNum = mailSheet.GetCurrentPage();

				$('#emailSndrSeq').val(emailSndrSeq);	<%-- 키 값 세팅 --%>
				$('#listPageNum').val(pageNum);

				var arrData = new Array();
				var data    = "";
				$("input[name=sendYn]:checkbox").each(function(i, v) {
					data 	 		= new Object();
					data["id"] 		= $(this).attr('id');;
					data["flag"] 	= $(this).is(":checked");

					arrData.push(data);
				});

				$('#arrSendYn').val(JSON.stringify(arrData));

				abc.biz.system.mailsend.history.getMailHistoryDetail();
			}else if (mailSheet.ColSaveName(Col) == "gridMemberInfo") {	<%-- 회원정보 팝업 --%>
				var memberNo = mailSheet.GetCellValue(Row, "memberNo");
				console.log(memberNo);
				if(memberNo == ''){
					alert("회원 정보가 없습니다.");
				}else{
					abc.memberDetailPopup(memberNo);
				}
			}
			
			//관리자 상세 팝업
// 			else if (mailSheet.ColSaveName(Col) == "gridAdminInfo") {	
// 				var rgsterNo = mailSheet.GetCellValue(Row, "rgsterNo");

// 				if(rgsterNo == '' || rgsterNo == 'system' || rgsterNo == 'SY00000000'){
// 					alert("관리자 정보가 없습니다.");
// 				}else{
// 					abc.adminDetailPopup(rgsterNo);
// 				}
// 			}
		}
	}

	function doAction(sAction){
		switch(sAction) {
			case "search" :	<%-- 조회 --%>
				mailSheet.SetPageCount($('#listPageCount').val());
				var param = { url : "/cmm/email-send-history/read-list"
					, onePageRow : $('#listPageCount').val()
					, subparam : FormQueryStringEnc(document.mailHistoryForm)
					, CPage : $('#listPageNum').val()
					, sheet : "mailSheet"};
				DataSearchPaging(param);
				$('#listPageNum').val('1');
				break;
		}
	}

</script>

<!-- S : container -->
<form id="mailHistoryForm" name="mailHistoryForm" method="post" onsubmit="return false;">
	<input type="hidden" id="emailSndrSeq" name="emailSndrSeq" value="<c:out value="${param.emailSndrSeq}"/>">
	<input type="hidden" id="listPageNum" name="listPageNum" value="<c:out value="${param.listPageNum}" />">
	<input type="hidden" id="arrSendYn" name="arrSendYn" value="<c:out value="${param.arrSendYn}" />">
	<c:set var="arrData" value="sendYn_Y:true|sendYn_N:true"></c:set>

	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">메일 발송 이력</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>시스템 관리</li>
							<li>메일 발송 이력</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">메일 발송 이력 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>메일 발송 이력 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">사이트</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<c:forEach var="data" items="${SITE_CODE}" varStatus="i">
											<c:if test="${i.first}">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="siteName0" name="siteNo" type="radio" value="" <c:if test="${empty param.siteNo}">checked</c:if>>
														<label for="siteName0">전체</label>
													</span>
												</li>
											</c:if>

											<li>
												<span class="ui-rdo">
													<input id="siteName<c:out value="${i.count}"/>" name="siteNo" type="radio" value="<c:out value="${data.siteNo}"/>">
													<label for="siteName<c:out value="${i.count}"/>"><c:out value="${data.siteName}"/></label>
												</span>
											</li>
										</c:forEach>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">받는 사람</th>
								<td class="input">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select class="ui-sel" title="유형 선택" name="rcvrSearchType" id="rcvrSearchType">
											<option value="rcvrName">이름</option>
											<option value="loginId">아이디</option>	<%-- 현재 테이블에 받는사람 아이디 없음 추가 필요, 추후수정 -최경호 --%>
											<option value="rcvrEmailAddrText">이메일</option>
										</select>
										<input type="text" class="ui-input" name="rcvrSearchText" id="rcvrSearchText" title="받는사람 입력" maxlength="50">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td class="input">
									<input type="text" class="ui-input" name="emailTitleText" id="emailTitleText" title="제목 입력" maxlength="100">
								</td>
								<td></td>
								<th scope="row">분류</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="adminSendYn" id="adminSendYn1" value="" <c:if test="${empty param.adminSendYn}">checked</c:if>>
												<label for="adminSendYn1">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="adminSendYn" id="adminSendYn2" value="Y">
												<label for="adminSendYn2">관리자</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="adminSendYn" id="adminSendYn3" value="N">
												<label for="adminSendYn3">시스템</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<tr>
								<th scope="row">기간 검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<div class="term-date-wrap">
										<select class="ui-sel" title="기간 선택" id="dateSearchType" name="dateSearchType">
											<option value="rgstDtm">등록일시</option>
											<option value="sendDtm">발송일시</option>
										</select>
										<span class="date-box">
											<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group">
											<a href="javascript:void(0)" name="perToday" id="perToday" class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0)" name="perWeek" id="perWeek" class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0)" name="per1Month" id="per1Month" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0)" name="perYear" id="perYear" class="btn-sm btn-func">1년</a>
										</span>
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : 비활성화시 disabled 속성 추가, input id / label for 동일하게 맞춰주세요 -->
													<input id="sendYn_Y" type="checkbox" name="sendYn" value="Y">
													<label for="sendYn_Y">발송</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : 비활성화시 disabled 속성 추가, input id / label for 동일하게 맞춰주세요 -->
													<input id="sendYn_N" type="checkbox" name="sendYn" value="N">
													<label for="sendYn_N">미발송</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</div>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0)" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-normal btn-func" id="searchBtn">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">메일 발송 이력</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="listPageCount">목록개수</label>
						<select class="ui-sel" id="listPageCount" name="listPageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="mailGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</form>
<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.mailsend.history.js<%=_DP_REV%>" ></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>