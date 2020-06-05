<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>
<script type="text/javascript">
	$(function(){
		//고객의소리 그리드
		abc.biz.board.inquiry.voiceOfCustomerGridSheet();

		<%-- 캘린더 버튼 설정(일) --%>
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});
		
		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
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

		//검색어 변경시에 초기화 및 포커스
		$("#searchKey").change(function(){
			abc.biz.board.inquiry.searchValueSet();
		});
		
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});

		abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", "${cnslTypeCode.get(0).getCodeDtlNo()}", "searchCnslTypeDtlCode");

		$("#searchBtn").off().on('click', function() {
			if(abc.date.searchValidate()){
				$("#currentRow").val("1");
				abc.biz.board.inquiry.doActionCounselScript("voiceOfCustomerSearch");
			}else{
				voiceOfCustomerGridSheet.RemoveAll();
			}
		});

		// 초기화
		$("#resetBtn").click(function(f) {
			$.form("#frmSearch").reset();
			$("a[name^=per1Month]").trigger("click");
		});

		$("a[name^=per1Month]").trigger("click");
		
		if(abc.text.allNull(abc.param.getParams().fromDash)){
			$("#searchBtn").trigger('click');
		}else{
			abc.biz.board.inquiry.fromDashSearch('<c:out value="${fromDash}"/>', '<c:out value="${tabIdx}"/>','voiceCustomer');
		}

		
		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});
		
		$("#downExcel").click(function(){
			abc.biz.board.inquiry.vocExcelDown();
		});
	});


	// 고객의 소리 상세 페이지 이동
	function voiceOfCustomerGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( voiceOfCustomerGridSheet.ColSaveName(Col) == "inqryTitleText" && Value != "" ) {
				$("#currentRow").val(Row);

				var _url = "/board/voiceofcustomer/read-detail-pop";

				var _params = {}
				_params.memberCnslSeq = voiceOfCustomerGridSheet.GetCellValue(Row, "memberCnslSeq")
				_params.cnslGbnCode = $("#cnslGbnCode").val()

				abc.open.popup({
					winname :	"voiceOfCustomerDetailPop",
					url 	:	_url,
					width 	:	1270,
					height	:	900,
					params	:	_params
				});

			} else if ( voiceOfCustomerGridSheet.ColSaveName(Col) == "memberInfo" && Value != "" ) {
				if ("" != voiceOfCustomerGridSheet.GetCellValue(Row, "memberNo")) {
					abc.memberDetailPopup(voiceOfCustomerGridSheet.GetCellValue(Row, "memberNo"));
				}
			}
		}
	}

	function voiceOfCustomerGridSheet_OnSearchEnd(row){
		row = $("#currentRow").val();
		voiceOfCustomerGridSheet.SetSelectCol("inqryTitleText");
		voiceOfCustomerGridSheet.SetSelectRow(row);
		
		if(voiceOfCustomerGridSheet.SearchRows() > 0){
			$('#downExcel').removeClass('disabled');
		} else {
			$('#downExcel').addClass('disabled');
		}
	}



</script>


<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">고객의 소리 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>고객응대관리</li>
						<li>고객의 소리 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">고객의 소리 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<input type="hidden" name="currentRow" id="currentRow" value="1">
		<form id="frmSearch" name="frmSearch" method="get">
		<input type="hidden" name="cnslGbnCode" id="cnslGbnCode" value='<c:out value="${CommonCode.CNSL_GBN_CODE_VOC}"/>'/>
		<input type="hidden" name="memberCnslSeq" id="memberCnslSeq" />
		<input type="hidden" name="menuNo" id="menuNo" />
		<input type="hidden" name="pageCount" id="pageCount1" />
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>고객의 소리 검색</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
						<col style="width: 79px">
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">작성일자</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택">
										<option value="">선택</option>
										<option value="inqryDtm" selected>문의일</option>
										<option value="aswrDtm">답변일</option>
									</select>
									<span class="date-box">
										<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택" maxlength="10">
									</span>
									<span class="text">~</span>
									<span class="date-box">
										<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택" maxlength="10">
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
								<select id="searchAswrStatCode" class="ui-sel" title="답변상태 선택">
									<option value="">전체</option>
									<c:forEach var="aswrStatCode" items="${aswrStatCode}">
										<option value="${aswrStatCode.codeDtlNo}">${aswrStatCode.codeDtlName}</option>
									</c:forEach>
								</select>
							</td>
							<td></td>
							<th scope="row">유형</th>
							<td class="input">
								<!-- TODO : Front확인 후 선택항목 수정 -->
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<%-- <select id="searchCnslTypeCode" name="searchCnslTypeCode" class="ui-sel" title="문의유형 대분류 선택">
										<option value="">선택</option>
										<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
											<option value="${cnslTypeCode.codeDtlNo}">${cnslTypeCode.codeDtlName}</option>
										</c:forEach>
									</select> --%>
									<select id="searchCnslTypeDtlCode" name="searchCnslTypeDtlCode" class="ui-sel" style="width:150px;" title="문의유형 소분류 선택">
										<option value="">전체</option>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">사이트 구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="siteNo" id="siteNo0" value="" checked>
											<label for="siteNo0">전체</label>
										</span>
									</li>
									<c:forEach var="siteNoList" items="${siteNoList}" varStatus="status">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="siteNo<c:out value="${status.count}" />"
												       value="<c:out value="${siteNoList.siteNo}" />" >
												<label for="siteNo<c:out value="${status.count}" />" >
													<c:out value="${siteNoList.siteName}" />
												</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">검색어</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
										<option value="">선택</option>
										<option value="inqryTitleText">제목/내용</option>
										<option value="loginId">회원id</option>
										<option value="memberName">회원명</option>
										<option value="rgsterName">답변자</option>
									</select>
									<input id="searchValue" name="searchValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" maxlength="30">
									<input type="text" class="ui-input" placeholder="휴대폰번호 뒷자리 입력" title="휴대폰번호 뒷자리 입력 " id="hdphnBackNoText" name="hdphnBackNoText" maxlength="4" style="display:none;">
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
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">고객의소리 목록</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : tbl-controller -->
		<div class="tbl-controller">
			<div class="fl">
				<span class="opt-group">
					<label class="title" for="pageCount">목록개수</label>
					<select class="ui-sel" id="pageCount" name="pageCount">
						<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
					</select>
				</span>
			</div>
			<div class="fr">
				<a href="javascript:void(0);" id="downExcel" class="btn-sm btn-func disabled">엑셀 다운로드</a>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="voiceOfCustomerGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->


<script src="/static/common/js/biz/board/abcmart.board.inquiry.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>