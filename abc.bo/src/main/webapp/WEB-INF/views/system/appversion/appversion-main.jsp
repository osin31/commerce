<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("appversionGrid"), "appversionSheet");
		<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:$('#pageCount').val()};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"상태",		Type:"Status",		SaveName:"Status", 			Width:10,	Hidden:1}
			, {Header:"번호", 	Type:"Seq", 		SaveName:"", 				Width: 5, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"OS",		Type:"Text",		SaveName:"codeDtlName",		Width:15, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사이트",	Type:"Text",		SaveName:"siteName",		Width:25, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"버전정보",	Type:"Text",		SaveName:"appVerText",		Width:20, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"작성자",	Type:"Text",		SaveName:"adminInfo",		Width:10, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"앱 등록일",	Type:"Date",		SaveName:"appRgstYmd",		Width:10, 	Align:"Center", 	Format:"Ymd",	Edit:0, Sort:0}
		];

		<%-- Grid 초기화 --%>
		IBS_InitSheet(appversionSheet , initSheet);
		<%-- Grid 건수 정보 표시 --%>
		appversionSheet.SetCountPosition(3);
		<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		appversionSheet.SetPagingPosition(2);
		<%-- Grid width 자동 조절 --%>
		//appversionSheet.FitColWidth();
		appversionSheet.FitColWidth("10|15|20|15|20|10");
		<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		appversionSheet.SetExtendLastCol(1);

		<%-- 앱정보 입력(저장) --%>
		$("#appversionInsert").click(function(f) {
			abc.biz.system.appversion.setAppversionData();
		});

		<%-- 초기화 --%>
		$("#init").click(function(f) {
			abc.biz.system.appversion.init();
		});

		<%-- focusout 숫자만 남기기 --%>
		$("#appVerText").focusout(function(event){
			var value = $(this).val().toString();
			$(this).val(value.replace(/[^0-9\.]/g,""));
		});

		<%-- keyup 숫자만 남기기 --%>
		$("#appVerText").keyup(function(event){
			var value = $(this).val().toString();
			$(this).val(value.replace(/[^0-9\.]/g,""));
		});

		<%-- 숫자 validation 체크 --%>
		$("#appVerText").keypress(function(event){
		    var charCode 	= event.keyCode;	// keyCode
	        var _value 		= $(this).val();	// Textbox value

	        if(_value == '' && charCode == 46){
	            return false;
	        }

	        if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
	            return false;
	        }

			<%-- 소수점(.)이 두번 이상 나오지 못하게 --%>
	       // var _pattern0 = /^\d*[.]\d*$/; // 현재 value값에 소수점(.) 이 있으면 . 입력불가
	       // if (_pattern0.test(_value)) {
	       //    if (charCode == 46) {
	       //         return false;
	       //    }
	       //}

			<%-- 100자리 이하의 숫자만 입력가능 --%>
	        var _pattern1 = /^\d{3}$/; // 현재 value값이 100자리 숫자이면 .만 입력가능
	        if (_pattern1.test(_value)) {
	            if (charCode != 46) {
	                return false;
	            }
	        }
	        return true;
	    });

		doAction("search");	// 휴일 리스트 그리드 조회

		$('#pageCount').on('change', function(event) {
			doAction("search");
		});
	});

	function doAction(sAction){
		switch(sAction) {
			case "search" :	<%-- 조회 --%>
				var param = { url : "/system/appversion/read-list"
					, onePageRow : $('#pageCount').val()
					, subparam : FormQueryStringEnc(document.appversionForm)
					, sheet : "appversionSheet" };
				DataSearchPaging(param);
				break;
			case "delete" :	<%-- 일괄삭제(기능 사용안함-추후 삭제)  --%>
			var checkCnt = appversionSheet.FindCheckedRow("chkData");

			if(checkCnt < 1){
				alert("그리드의 날짜를 체크해주세요");
				return false;
			}else{
				var param = { url : "/system/appversion/delete"
					, subparam:FormQueryStringEnc(document.appversionForm)
					, sheet:"appversionSheet" };
				DataSave(param);
			}
			break;
		}
	}
</script>

	<!-- S : container -->
	<form id="appversionForm" name="appversionForm" method="post" onsubmit="return false;">
	<input type="hidden" id="appOsCodeField" name="appOsCodeField" value="APP_OS_CODE">
	<!-- S : container -->
	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">APP 버전 관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>시스템 관리</li>
							<li>APP 버전 관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">APP 버전 등록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>APP 버전 등록</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">버전정보</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<c:forEach var="data" items="${APP_OS_CODE}" varStatus="i">
								<li>
									<span class="ui-rdo">
										<input id="AppOsCode<c:out value="${i.count}"/>" name="appOsCode" type="radio" value="<c:out value="${data.codeDtlNo}"/>">
										<label for="AppOsCode<c:out value="${i.count}"/>"><c:out value="${data.codeDtlName}"/></label>
									</span>
									<c:if test="${i.last}">
									<!-- S : ip-text-box -->
									<span class="ip-text-box">
										<input type="text" class="ui-input" title="버전 정보 입력" placeholder="버전 정보를 입력하세요." maxlength="10" id="appVerText" name="appVerText">
									</span>
									<!-- E : ip-text-box -->
									</c:if>
								</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span>사이트</span>
						</th>
						<td class="input">
							<ul class="ip-box-list">
								<c:forEach items="${SITE_CODE}" var="data" varStatus="i">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="siteName<c:out value="${i.count}"/>" name="siteNo" type="radio" value="<c:out value="${data.siteNo}"/>">
											<label for="siteName<c:out value="${i.count}"/>"><c:out value="${data.siteName}"/></label>
										</span>
									</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">앱 등록일</span>
						</th>
						<td class="input">
							<span class="date-box">
								<input type="text" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" id="appRgstYmd" name="appRgstYmd">
							</span>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-bottom -->
			<div class="content-bottom">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="javascript:void(0);" class="btn-lg btn-save" id="appversionInsert" name="appversionInsert" >저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : content-bottom -->

			<!-- S : 20180109 수정 // 테이블 헤더영역 수정 -->
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">APP 버전 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount" name="pageCount" >
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
				</div>
			</div>
			<!-- E : tbl-controller -->
			<!-- E : 20180109 수정 // 테이블 헤더영역 수정 -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="appversionGrid" style="width:100%; height:429px;"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	<!-- E : container -->
	</form>

<script type="text/javascript" src="/static/common/js/biz/system/abcmart.system.appversion.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>