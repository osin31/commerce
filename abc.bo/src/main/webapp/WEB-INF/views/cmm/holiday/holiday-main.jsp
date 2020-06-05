<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<%--
휴일 관리를 등록/수정하는 화면
휴일의 종류 - 시스템 휴일, 공통 휴일, 업체 휴일
	L 시스템 휴일[HLDY_GBN_TYPE:S] : api를 통해서 수신한 휴일정보
	L 공통 휴일[HLDY_GBN_TYPE:C] : 시스템 휴일을 기준으로 시스템 관리자가 관리하는 휴일 정, 업체별 휴일정도 등록 시 기준이 되는 휴일 정보
	L 업체 휴일 : 업체에서 각자 등록한 휴일 정보
기본적으로 휴일은 시스템 휴일이 제일 먼저 등록된다. 주말정보, 국가공휴일, 대체 공휴일 정보 등 
(api를 통해서 실시간으로 수신하려 했으나, api가 불안한 관계로 '시스템 휴일' 데이터가 없을 때 년도별로 한 번만 수신해서 데이터 세팅 후 api를 참고하지 않고, 시스템 휴일을 기준으로함)
공통 휴일 정보는 시스템 관리자가 관리하며 통합몰의 업체에 모두 적용되는 휴일 정보이다. 처음에는 업체별로 모두 insert, 그 후에는 업체별 update
업체 휴일은 업체의 관리자가 관리하는 휴일 정보이다.

****************************************** 중요 *************************************************
관리자와 업체가 같은 화면을 사용하므로 권한에 주의해서 수정 할 것
ROLE_10000:총괄 관리자 그룹
ROLE_30000:업체 관리자 그룹
****************************************** 중요 *************************************************
 --%>
<script type="text/javascript">
	$(document).ready(
			function() {
				<c:if test="${vendorListSize eq 0}">
				alert("업체 정보가 없습니다.");
				$(location).attr('href', "/");
				</c:if>
<%-- Grid 객체 생성 --%>
	createIBSheet2(document.getElementById("holidayGrid"),
						"yearSheet", "100%", "429px");
<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
	var initSheet = {};

				initSheet.Cfg = {
					SearchMode : smServerPaging2,
					Page : $('#pageCount').val()
				};
				initSheet.HeaderMode = {
					Sort : 1,
					ColMove : 0,
					ColResize : 1
				};
				initSheet.Cols = [ {
					Header : "",
					Type : "DelCheck",
					SaveName : "chkData",
					Width : 5,
					Align : "Left",
					Edit : 1,
					Sort : 0
				}, {
					Header : "hldySeq",
					Type : "Text",
					SaveName : "hldySeq",
					Width : 10,
					Align : "Center",
					Edit : 0,
					Hidden : 1
				}, {
					Header : "상태",
					Type : "Status",
					SaveName : "Status",
					Width : 10,
					Hidden : 1,
					Sort : 0
				}, {
					Header : "번호",
					Type : "Seq",
					SaveName : "",
					Width : 5,
					Align : "Center",
					Edit : 0,
					Sort : 0
				}, {
					Header : "휴일",
					Type : "Text",
					SaveName : "hldyYmd",
					Width : 15,
					Align : "Center",
					Edit : 0,
					Format : "Ymd",
					Sort : 0
				}, {
					Header : "휴일명",
					Type : "Text",
					SaveName : "hldyName",
					Width : 25,
					Align : "Center",
					Hidden : 1,
					Edit : 0,
					Sort : 0
				}, {
					Header : "작성자",
					Type : "Text",
					SaveName : "adminInfo",
					Width : 20,
					Align : "Center",
					Edit : 0,
					Sort : 0
				}, {
					Header : "작성일시",
					Type : "Text",
					SaveName : "rgstDtm",
					Width : 10,
					Align : "Center",
					Edit : 0,
					Format : "YmdHms",
					Sort : 0
				} ];
<%-- Grid 초기화 --%>
	IBS_InitSheet(yearSheet, initSheet);
<%-- Grid 건수 정보 표시 --%>
	yearSheet.SetCountPosition(3);
<%-- Grid 페이지 네비게이션 버튼 표시 --%>
	yearSheet.SetPagingPosition(2);
<%-- Grid width 자동 조절 --%>
	yearSheet.FitColWidth();
<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
	yearSheet.SetExtendLastCol(1);
<%-- 휴일정보 입력(저장) --%>
	$("#holidayInsert").click(function(f) {
					abc.biz.system.holiday.setHolidayData();
				});
<%-- 년도 선택 시 휴일정보 세팅 --%>
	$("[id^=yearClick]").click(function() {
					abc.biz.system.holiday.getHolidayData($(this).val());
				});
<%-- 초기화 --%>
	$("#init").click(function(f) {
					abc.biz.system.holiday.init();
				});

				$('#year').on('change', function(event) {
					//$('#year').val(this.value);	// 년도 파라미터 세팅
					
					if ($(this).val() == '${thisYear}') {
						$('#month').val('${thisMonth}');
					}else{
						$('#month').val("01");
					}
					doAction("search");
				});

				$('#vndrNo').on('change', function(event) {
					//alert("업체 선택 개발중");
					return;
				});

				$('#pageCount').on('change', function(event) {
					yearSheet.SetPageCount(this.value); // 그리드의 페이지 개수를 선택한 개수로 조정한다.
					doAction("search");
				});
<%-- 일괄삭제 --%>
	$("#holidayBundleDelete").click(function(f) {
					doAction("delete");
				});
<%-- 업체 추가 --%>
	$("#addVendor").click(function(f) {
					abc.biz.system.holiday.addVendorHoliday();
				});
<%-- keyup 숫자만 남기기 --%>
	$("#holidayDataArea").keyup(function(f) {
					var charCode = event.keyCode; // keyCode
					var value = $(this).val();
					$(this).val(value.replace(/[^\d\,]/g, ""));
				});
<%-- focusout 숫자만 남기기 --%>
	$("#holidayDataArea").focusout(function(f) {
					var value = $(this).val();
					$(this).val(value.replace(/[^\d\,]/g, ""));
				});
<%-- 캘린더 보기 팝업 --%>
	$("#popupHolidayCalendar").click(function(f) {
					abc.biz.system.holiday.popupHolidayCalendar("S");
				});

				$(document).on("click", ".ico-item-del", function() {
					abc.biz.system.holiday.deleteVendorHoliday(this);
				});

				$('#year').val('<c:out value ="${thisYear}"/>'); // 년도선택 기본값(해당년도) 세팅

				doAction("search"); // 휴일 리스트 그리드 조회
			});

	function doAction(sAction) {
		switch (sAction) {
		case "search":
<%-- 조회 --%>
	var isAuthority = abc.biz.system.holiday.isAuthority(false);
			var roll = $('#role').val();
			var vndrNo = $('#vndrNoArr').val();

			if (isAuthority) { // 화면의 권한이 있는지 체크
				if (roll == abc.consts.ROLE_VENDER_GROUP) { // 업체 권한이라면 vndrNo tpxld
					$('#holidayForm')
							.append(
									'<input type="hidden" name="vndrNo" id="vndrNo" value="'+vndrNo+'" />');
				}
				var param = {
					url : "/cmm/holiday/read-list",
					onePageRow : $('#pageCount').val(),
					subparam : FormQueryStringEnc(document.holidayForm),
					sheet : "yearSheet"
				};
				DataSearchPaging(param);
			}
			break;
		case "delete":
<%-- 일괄삭제  --%>
	var checkCnt = yearSheet.FindCheckedRow("chkData");

			if (checkCnt < 1) {
				alert("그리드를 선택하세요");
				return false;
			} else {

				var valFlag = abc.biz.system.holiday.isAuthority(true);
				if (valFlag) {
					var param = {
						url : "/cmm/holiday/update",
						subparam : FormQueryStringEnc(document.holidayForm),
						sheet : "yearSheet"
					};
					DataSave(param);
				}
			}
			break;
		}
	}

	function yearSheet_OnSaveEnd(Code, Msg, StCode, StMsg, Response) {
		if (StCode == "200") {
			alert("삭제하였습니다.");
		} else {
			alert("에러가 발생하였습니다.");
		}
		doAction("search");
	}
</script>
<!-- S : container -->
<form id="holidayForm" name="holidayForm" method="post"
	onsubmit="return false;">
	<input type="hidden" id="insertYear" name="insertYear" value="">
	<%-- 휴일 등록 시, 연도를 눌렀을 때 선택한 년도의 정보를 저장하는 변수 --%>
	<input type="hidden" id="role" name="role"
		value="<c:out value="${userDetails.adminAuthorities[0].upAuthNo}"/>">
	<%-- 관리자의 권한 --%>
	<input type="hidden" id="roleAdminGroup" name="roleAdminGroup"
		value="<%=Const.ROLE_ADMIN_GROUP%>">
	<%-- 총괄 관리자 권한 그룹 코드 // --%>
	<input type="hidden" id="roleVenderGroup" name="roleVenderGroup"
		value="<%=Const.ROLE_VENDER_GROUP%>">
	<%-- 업체 권한 그룹 코드  //--%>
	<input type="hidden" id="authType" name="authType"
		value="<c:out value="${authType}"/>">
	<%-- 권한 타입 --%>
	<input type="hidden" id="commonHoliday" name="commonHoliday"
		value="<%=Config.getString("holiday.common", "C")%>"> <input
		type="hidden" id="standardHoliday" name="standardHoliday"
		value="<%=Config.getString("holiday.standard", "S")%>">

	<%-- 총괄 관리자일 경우 모든 업체리스트를 hidden으로 가지고 있고, 업체 관리자 그룹일 경우 자기업체의 권한을 가지고 있음 --%>
	<c:forEach items="${vendorList}" var="data">
		<input type="hidden" id="vndrNoArr" name="vndrNoArr"
			value="<c:out value="${data.vndrNo}"/>">
	</c:forEach>

	<div class="container">
		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">휴일관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites">
						<span class="ico ico-star"><span class="offscreen">즐겨찾기
								등록</span></span>
					</button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span
										class="offscreen">홈</span></i></a></li>
							<c:if test="${authType eq 'B'}">
								<%-- 시스템 휴일관리 일 때 네비게이션 --%>
								<li>시스템 관리</li>
								<li>휴일관리</li>
							</c:if>
							<c:if test="${authType ne 'B'}">
								<%-- 업체 휴일관리 일 때 네비게이션 --%>
								<li>입점관리</li>
								<li>입점사 관리</li>
								<li>입점사 휴일 관리</li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">휴일정보 등록</h3>
				</div>
				<div class="fr">
					<div class="btn-group">
						<a href="javascript:void(0);" class="btn-sm btn-func" id="init"><i
							class="ico ico-refresh"></i>초기화</a>
					</div>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : 20190121 추가 // 기획 변경으로 공통휴일 적용 업체 테이블 추가 -->
			<!-- S : tbl-row -->
			<%--
			<table class="tbl-row">
				<caption>공통휴일 적용 업체</caption>
				<colgroup>
					<col style="width: 150px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" rowspan="2">
							<span class="th-required">공통휴일 적용 업체</span>
						</th>
						<td class="input">
							<!-- TODO : Search Drop down 기능 협의 후 수정 -->
							<!-- S : search-dropdown-box -->
							<span class="search-dropdown-box">
								
								<select class="ui-sel" title="공통휴일 자동완성" id="vndrNo" name="vndrNo">
									<option value="">선택하세요</option>
									<c:forEach items="${vendorList}" var="data" >
									<option value="<c:out value="${data.vndrNo}"/>"><c:out value="${data.vndrName}"/></option>
									</c:forEach>
								</select>
								
								<button type="button" class="btn-sm btn-func" id="addVendor" >추가</button>
							</span>
							<!-- E : search-dropdown-box -->
						</td>
					</tr>
					<tr>
						<td class="input">
							<!-- S : item-list -->
							<ul class="item-list" id="addVendorArea">
							</ul>
							<!-- E : item-list -->
						</td>
					</tr>
				</tbody>
			</table>
			--%>
			<!-- E : tbl-row -->
			<!-- E : 20190121 추가 // 기획 변경으로 공통휴일 적용 업체 테이블 추가 -->

			<!-- S : tbl-row -->
			<c:if test="${authType eq 'P'}">
				<%-- 업체 권한의 관리자 일 때만 업체 정보를 보여준다. --%>
				<table class="tbl-row">
					<caption>휴일정보 등록</caption>
					<colgroup>
						<col style="width: 150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">업체명</th>
							<td><c:if test="${not empty userDetails.vndrNo}">
									<c:out value="${vendorList[0].vndrName}" />
								</c:if> <c:if test="${empty userDetails.vndrNo}">
								-
							</c:if></td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<!-- E : tbl-row -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>휴일정보 등록</caption>
				<colgroup>
					<col style="width: 150px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><span class="th-required">휴일 등록</span></th>
						<td class="input"><span class="td-btn-group">
								<button href="javascript:void(0);" class="btn-sm btn-func"
									id="yearClickThis" value="<c:out value="${thisYear}"/>">
									<c:out value="${thisYear}" />
									년
								</button>
								<button href="javascript:void(0);" class="btn-sm btn-func"
									id="yearClickYear" value="<c:out value="${nextYear}"/>">
									<c:out value="${nextYear}" />
									년
								</button>
						</span> <textarea class="ui-textarea" title="휴일 입력" id="holidayDataArea"
								name="holidayDataArea"></textarea></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 다수의 휴일을 입력하시려면 공백없이 ’,(쉼표)’로 붙여서 등록해주세요. (예시)
						yyyymmdd,yyyymmdd</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : 20190121 추가 // 기획 수정으로 휴일정보 등록 영역 수정 -->
			<!-- S : content-bottom -->
			<div class="content-bottom">
				<a href="javascript:void(0);" class="btn-lg btn-save"
					id="holidayInsert">저장</a>
			</div>
			<!-- E : content-bottom -->
			<!-- E : 20190121 추가 // 기획 수정으로 휴일정보 등록 영역 수정 -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group"> <label for="year" class="title">년도
							선택</label> <select class="ui-sel" id="year" name="year">
							<option value="">선택</option>
							<c:forEach begin="${beforeTenYear}" end="${nextYear}" var="year">
								<option value="<c:out value="${year}"/>"><c:out
										value="${year}" /></option>
							</c:forEach>
					</select> <input type="hidden" id="month" name="month" value="">
					</span>
					<!-- E : opt-group -->
					<!-- S : opt-group -->
					<span class="opt-group"> <label for="pageCount"
						class="title">목록개수</label> <select class="ui-sel" id="pageCount"
						name="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
					</span>
					<!-- E : opt-group -->
					<span class="gap"></span> <a href="javascript:void(0);"
						class="btn-sm btn-del" id="holidayBundleDelete">선택 항목 삭제</a>
				</div>
				<div class="fr">
					<span class="btn-group"> <!-- DESC : html/common/BO-CM-14001.html 파일 확인 해주세요. -->
						<a href="javascript:void(0);" class="btn-sm btn-link"
						id="popupHolidayCalendar">캘린더 보기</a>
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div id="holidayGrid"></div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</form>
<!-- E : container -->

<script type="text/javascript"
	src="/static/common/js/biz/system/abcmart.system.holiday.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
