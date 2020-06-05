<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		// 결과 목록 그리드 init
		abc.biz.vendor.individual.inquiry.inquiryGridSheet();

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

		// 문의유형 코드 변경
		$("#searchCnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#searchCnslTypeCode option:selected");		// 상담유형코드
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "searchCnslTypeDtlCode");
		});


		//검색어 변경시에 초기화 및 포커스
		$("#searchKey").change(function(){
			abc.biz.vendor.individual.inquiry.searchValueSet();
		});

		$("#searchBtn").off().on('click', function() {
			if(abc.date.searchValidate()){
				$("#currentRow").val("1");
				abc.biz.vendor.individual.inquiry.doActionCounselScript("search");
			}else{
				alert("검색 기간을 확인해주세요.");
				inquiryGridSheet.RemoveAll();
			}
		});

		// 초기화
		$("#resetBtn").click(function(f) {
			abc.biz.vendor.individual.inquiry.resetButton();
			$("a[name^=per1Month]").trigger("click");
		});

		$("a[name^=per1Month]").trigger("click");
		
		// 대시보드에서 더보기 클릭으로 왔을때 미답변 검색
		abc.biz.vendor.individual.inquiry.fromDashSearch('<c:out value="${fromDash}"/>');
	
	});

	// 1:1문의 상세 페이지 이동
	function inquiryGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( inquiryGridSheet.ColSaveName(Col) == "inqryTitleText" && Value != "" ) {
				var _url = "/vendor/individual-inquiry/detail";
				var _params = {}
				
				_params.cnslGbnCode = inquiryGridSheet.GetCellValue(Row, "cnslGbnCode");
				_params.memberCnslSeq = inquiryGridSheet.GetCellValue(Row, "memberCnslSeq");
				
				abc.open.popup({
					winname :	"inquiryDetailPop",
					url 	:	_url,
					width 	:	1270,
					height	:	990,
					params	:	_params
				});

			} else if ( inquiryGridSheet.ColSaveName(Col) == "aswrInfo" && Value != "" ) {
				if ("" != inquiryGridSheet.GetCellValue(Row, "aswrNo")) {
					abc.adminDetailPopup(inquiryGridSheet.GetCellValue(Row, "aswrNo"));
				}
			}
		}
	}
	
	//그리드 search후 컬럼 선택
	function inquiryGridSheet_OnSearchEnd(row){
		row = $("#currentRow").val();
		inquiryGridSheet.SetSelectCol("inqryTitleText");
		inquiryGridSheet.SetSelectRow(row);

	}
	
	//첨부파일 아이콘 추가
	function inquiryGridSheet_OnRowSearchEnd(row){
		var checkAtchFile = inquiryGridSheet.GetCellValue(row, "checkAtchFile");

		if (checkAtchFile > 0) {
			var inqryTitleText = inquiryGridSheet.GetCellValue(row, "inqryTitleText");
			var htmlIcon = "<i class='far fa-file-alt' style='float: right;'></i>"
			inquiryGridSheet.SetCellValue(row, "inqryTitleText", inqryTitleText + htmlIcon);
		}

	}
	
</script>
<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">1:1문의관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>입점관리</li>
						<li>1:1문의관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">1:1문의 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->
		<input type="hidden" name="currentRow" id="currentRow" value="1"/>
		<!-- S : search-wrap -->
		<form id="frmSearch" name="frmSearch" method="get">
		<input type="hidden" name="memberCnslSeq" id="memberCnslSeq" />
		<input type="hidden" name="pageCount" id="pageCount1" />
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>1:1문의 검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택">
										<option name="">선택</option>
										<option value="inqryDtm">문의일</option>
										<option value="escalationDtm" selected>이관일</option>
										<option value="aswrDtm">최종답변일</option>
									</select>
									<span class="date-box">
										<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
									</span>
									<span class="btn-group">
										<a href="javascript:void(0)" name="perToday" class="btn-sm btn-func">오늘</a>
										<a href="javascript:void(0)" name="perWeek" class="btn-sm btn-func">일주일</a>
										<a href="javascript:void(0)" name="per1Month" class="btn-sm btn-func">한달</a>
										<a href="javascript:void(0)" name="perYear" class="btn-sm btn-func">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">답변상태</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="searchAswrStatCode" id="aswrStatCode0000" value="" checked>
											<label for="aswrStatCode0000">전체</label>
										</span>
									</li>
								<c:forEach var="aswrStatCode" items="${codeList.ASWR_STAT_CODE}" varStatus="status">
								<c:if test="${status.count ne 1 }">
								
								
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="searchAswrStatCode" id="${aswrStatCode.codeDtlNo}" value="${aswrStatCode.codeDtlNo}">
											<label for="${aswrStatCode.codeDtlNo}">${aswrStatCode.codeDtlName}</label>
										</span>
									</li>
								</c:if>
								</c:forEach>
							</td>
							<td></td>
							<th scope="row">문의유형</th>
							<td class="input">
								<!-- TODO : Front확인 후 선택항목 수정 -->
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select id="searchCnslTypeCode" name="searchCnslTypeCode" class="ui-sel" title="문의유형 대분류 선택">
										<option value="">전체</option>
										<c:forEach var="cnslTypeCode" items="${codeList.CNSL_TYPE_CODE}">
											<option value="${cnslTypeCode.codeDtlNo}">${cnslTypeCode.codeDtlName}</option>
										</c:forEach>
									</select>
									<select id="searchCnslTypeDtlCode" name="searchCnslTypeDtlCode" class="ui-sel" style="width:150px;" title="문의유형 소분류 선택">
										<option value="">선택</option>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">검색어</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
										<option value="">선택</option>
										<option value="inqryTitleText">제목</option>
										<option value="rgsterName">답변자</option>
									</select>
									<input id="searchValue" name="searchValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="30">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="javascript:void(0)" class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" class="btn-normal btn-func" id="searchBtn">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		</form>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">1:1문의 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="selTermsModule">목록개수</label>
					<select class="ui-sel" id="pageCount" name="pageCount">
						<option value="20">20 보기</option>
						<option value="50">50개 보기</option>
						<option value="100">100개 보기</option>
					</select>
				</span>
			</div>
		</div>
		<!-- E : tbl-controller -->
		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="inquiryGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/vendor/abcmart.vendor.individual.inquiry.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>