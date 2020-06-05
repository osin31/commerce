<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		// 결과 목록 그리드 init
		abc.biz.board.inquiry.inquiryGridSheet();
		
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

		// 문의유형 코드 변경
		$("#searchCnslTypeCode").off().on('change', function(f) {
			var cnslTypeCode = $("#searchCnslTypeCode option:selected");		// 상담유형코드
			abc.setLowerCodeList("CNSL_TYPE_DTL_CODE", cnslTypeCode.val(), "searchCnslTypeDtlCode");
			if(abc.text.isBlank($("#searchCnslTypeCode").val())){
				$("#searchCnslTypeDtlCode").hide();
			}else{
				$("#searchCnslTypeDtlCode").show();
			}
		});

		//searchChageVndr
		$("#searchChageVndr").off().on('change', function(){
			var val = $("#searchChageVndr").val();

			if (val == "vndrNm") {
				$('#searchChageVndrValue').show();
			} else {
				$('#searchChageVndrValue').hide();
			}
			console.log(val);
		});
		//검색어 변경시에 초기화 및 포커스
		$("#searchKey").change(function(){
			abc.biz.board.inquiry.searchValueSet();
		});
		
		//검색
		$("#searchBtn").off().on('click', function() {
			if(abc.date.searchValidate()){
				$("#currentRow").val("1");
				abc.biz.board.inquiry.doActionCounselScript("search");
			}else{
				inquiryGridSheet.RemoveAll();
			}
		});
		
		$("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});

		// 초기화
		$("#resetBtn").click(function(f) {
			abc.biz.board.inquiry.resetButton();
			$("a[name^=per1Month]").trigger("click");
		});

		$("a[name^=per1Month]").trigger("click");
		
		if(abc.text.allNull(abc.param.getParams().fromDash)){
			$("#searchBtn").trigger('click');
		}else{
			// 대시보드에서 더보기 클릭으로 왔을때 미답변 검색
			abc.biz.board.inquiry.fromDashSearch('<c:out value="${fromDash}"/>', '<c:out value="${tabIdx}"/>');
		}
		
		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});
		
		$("#downExcel").click(function(){
			abc.biz.board.inquiry.inquiryExcelDown();
		});
		
	});

	// 1:1문의 상세 페이지 이동
	function inquiryGridSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( inquiryGridSheet.ColSaveName(Col) == "inqryTitleText" && Value != "" ) {
				var _params = {}
				_params.cnslGbnCode = inquiryGridSheet.GetCellValue(Row, "cnslGbnCode");
				var _url;

				if(_params.cnslGbnCode == abc.consts.CNSL_GBN_CODE_CALL){
					_url = "/member/member-counsel-pop";
					_width = 1270;
					_height = 885;
					_params.memberNo = inquiryGridSheet.GetCellValue(Row, "memberNo");
				}else{
					$("#currentRow").val(Row);

					_url = "/board/inquiry/read-detail-pop";

				}
					_params.memberCnslSeq = inquiryGridSheet.GetCellValue(Row, "memberCnslSeq")

					abc.open.popup({
						winname :	"inquiryDetailPop",
						url 	:	_url,
						width 	:	1270,
						height	:	990,
						params	:	_params
					});

			} else if ( inquiryGridSheet.ColSaveName(Col) == "memberInfo" && Value != "" ) {
				if ("" != inquiryGridSheet.GetCellValue(Row, "memberNo")) {
					abc.memberDetailPopup(inquiryGridSheet.GetCellValue(Row, "memberNo"));
				}
			} else if ( inquiryGridSheet.ColSaveName(Col) == "vndrName" && inquiryGridSheet.GetCellValue(Row, "vndrGbnType") == 'V' && Value != "" ) {
				if ("" != inquiryGridSheet.GetCellValue(Row, "vndrNo")) {
					abc.vendorInfoPop(inquiryGridSheet.GetCellValue(Row, "vndrNo"));
				}
			}
		}
	}
	//그리드 search후 컬럼 선택
	function inquiryGridSheet_OnSearchEnd(row){
		row = $("#currentRow").val();
		inquiryGridSheet.SetSelectCol("inqryTitleText");
		inquiryGridSheet.SetSelectRow(row);
		
		if(inquiryGridSheet.SearchRows() > 0){
			$('#downExcel').removeClass('disabled');
		} else {
			$('#downExcel').addClass('disabled');
		}
	}
	//첨부파일 아이콘 추가, 입점업체 인지 자사인지 구분 
	function inquiryGridSheet_OnRowSearchEnd(row){
		var checkAtchFile = inquiryGridSheet.GetCellValue(row, "checkAtchFile");
		var vndrGbnType = inquiryGridSheet.GetCellValue(row, "vndrGbnType");

		if (checkAtchFile > 0) {
			var inqryTitleText = inquiryGridSheet.GetCellValue(row, "inqryTitleText");
			var htmlIcon = "<i class='far fa-file-alt' style='float: right;'></i>"
			inquiryGridSheet.SetCellValue(row, "inqryTitleText", inqryTitleText + htmlIcon);
		}
		
		//TODO 고객 요청시에 수정해야 할수도있음
		if('V' == vndrGbnType){
			inquiryGridSheet.SetCellFontBold(row, 'vndrName', 1);
			inquiryGridSheet.SetCellFontUnderline(row, 'vndrName', 1);
			inquiryGridSheet.SetCellCursor(row, 'vndrName', 'Pointer');
		}else{
			inquiryGridSheet.SetCellValue(row, 'vndrName', "ABC");
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
						<li>고객응대관리</li>
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
		<input type="hidden" name="menuNo" id="menuNo" />
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
						<!-- S : 20190308 추가 // 검색영역 분류 추가 -->
						<tr>
							<th scope="row">분류</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<input id="cnslGbnCode_on" name="cnslGbnCode" value="${CommonCode.CNSL_GBN_CODE_INQUIRY}" type="radio" checked>
											<label for="cnslGbnCode_on">온라인</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<input id="cnslGbnCode_tel" name="cnslGbnCode" value="${CommonCode.CNSL_GBN_CODE_TELCNSL}" type="radio">
											<label for="cnslGbnCode_tel">유선상담</label>
										</span>
									</li>
								</ul>
							</td>
							<td></td>
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
						</tr>
						<!-- E : 20190308 추가 // 검색영역 분류 추가 -->
						<tr>
							<th scope="row">기간검색</th>
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
							<th scope="row">문의유형</th>
							<td class="input">
								<!-- TODO : Front확인 후 선택항목 수정 -->
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select id="searchCnslTypeCode" name="searchCnslTypeCode" class="ui-sel" title="문의유형 대분류 선택">
										<option value="">전체</option>
										<c:forEach var="cnslTypeCode" items="${cnslTypeCode}">
											<option value="${cnslTypeCode.codeDtlNo}">${cnslTypeCode.codeDtlName}</option>
										</c:forEach>
									</select>
									<select id="searchCnslTypeDtlCode" name="searchCnslTypeDtlCode" class="ui-sel" style="width:150px;" title="문의유형 소분류 선택">
										<option value="">전체</option>
									</select>
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">담당업체</th>
							<td class="input">
								<!-- S : ip-direct-search-box -->
								<span class="ip-direct-search-box">
									<select id="searchChageVndr" name="searchChageVndr" class="ui-sel" title="담당업체 선택">
										<option value="">전체</option>
										<option value="cs">ABC고객센터</option>
										<option value="vndr">입점사</option>
										<option value="vndrNm">직접검색</option>
									</select>
									<input id="searchChageVndrValue" name="searchChageVndrValue" type="text" class="ui-input" placeholder="검색어 입력" title="검색어 입력" style="display:none" maxlength="30">
								</span>
								<!-- E : ip-direct-search-box -->
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
			<div id="inquiryGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/board/abcmart.board.inquiry.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>