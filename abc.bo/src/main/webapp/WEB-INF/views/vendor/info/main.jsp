<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">
	$(function() {

		var pageCount = ('<c:out value="${param.pageCount}"/>' == '') ?  $('#pageCount').val(): '<c:out value="${param.pageCount}"/>';
		$('#pageCount').val(pageCount);

		// 입점사 목록 그리드 초기화
		abc.biz.vendor.info.initVendorInfoSheet();

		//입점사검색 영역 세팅
		abc.biz.vendor.info.event();


		// 검색조건 유지
		<c:if test="${not empty param.s_vndrNo}">
			$("#selVndrStdCtgrNo").val('<c:out value="${param.s_vndrStdCtgrNo}"/>');

			var channelCheckAll = '<c:out value="${param.s_channelCheckAll}"/>';
			console.log("channelCheckAll : " + channelCheckAll)
			if(channelCheckAll != 'on'){
				var selChnnlNoArr = '<c:out value="${param.s_selChnnlNoArr}"/>';
				$("input[id^=chkChannel_]").prop("checked", false);
				if(!abc.text.allNull(selChnnlNoArr)){
					var arrRow = selChnnlNoArr.split(",");
					$("#channelCheckAll").prop("checked", false);
					for (idx=0; idx<arrRow.length; idx++){
						$("#chkChannel_"+arrRow[idx]).prop("checked", true);
					}
				}
			}

			var vndrStatCode = '<c:out value="${param.s_vndrStatCode}"/>';
			if(abc.text.allNull(vndrStatCode)){
				$("#vndrStatCheckAll").prop("checked", true);
			}else{
				$("#vndrStatCheckAll").prop("checked", false);
				$("#radioDeal_"+vndrStatCode).prop("checked", true);
			}

			var exceptionCommissionApplyYn = '<c:out value="${param.s_exceptionCommissionApplyYn}"/>';
			if(exceptionCommissionApplyYn != 'on'){
				$("#radioExceptionCommissionApplyon").prop("checked", false);
				$("#radioExceptionCommissionApply"+exceptionCommissionApplyYn).prop("checked", true);
			}

			var employeeDiscountApplyYn = '<c:out value="${param.s_employeeDiscountApplyYn}"/>';
			if(employeeDiscountApplyYn != 'on'){
				$("#radioEmployeeDiscountApplyon").prop("checked", false);
				$("#radioEmployeeDiscountApply"+exceptionCommissionApplyYn).prop("checked", true);
			}

			$('#vndrBrandNo').val('<c:out value="${param.s_vndrBrandNo}"/>');
			$('#brandName').val('<c:out value="${param.s_brandName}"/>');

			$('#searchKey').val('<c:out value="${param.s_searchKey}"/>');
			$('#searchValue').val('<c:out value="${param.s_searchValue}"/>');

			$('#fromDate').val('<c:out value="${param.s_fromDate}"/>');
			$('#toDate').val('<c:out value="${param.s_toDate}"/>');

			abc.biz.vendor.info.doActionVendorInfo("search");	// 그리드 리스트 조회
		</c:if>



	});

	<%-- 그리드 Click 이벤트 --%>
	function vendorInfoSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			//입점사 업체정보
			if ( vendorInfoSheet.ColSaveName(Col) == "vndrName") {
// 				var url = "/vendor/info/read-detail?menuNo="+ abc.param.getParams().menuNo+"&vndrNo="+vendorInfoSheet.GetRowData(Row).vndrNo;
// 				$(location).attr('href', url);

				$("#vndrNo").val(vendorInfoSheet.GetRowData(Row).vndrNo);

				vendorForm.method = "post";
				vendorForm.action = "/vendor/info/read-detail";
				vendorForm.submit();
			}
		}
	}

	// 관리자찾기 팝업 callBack함수
	function adminSearchPopupResult(data){
		$("#chargeMdNo").val(data.adminNo);
	//	$("#spanChargeMd").text(data.adminName);
	}
</script>

	<!-- S : container -->
	<div class="container">
	<form id="vendorForm" name="vendorForm">
	<input type="hidden" id="vndrNo" name="vndrNo" value="">
	<input type="hidden" id="selChnnlNoArr" name="selChnnlNoArr" value="">
	<input type="hidden" id="listPageNum" name="listPageNum" value="<c:out value="${param.listPageNum}" />">

		<div class="content-box">
			<!-- S : page-header -->
			<div class="page-header">
				<div class="fl">
					<h2 class="page-title">입점사 정보관리</h2>
					<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
					<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
				</div>
				<div class="fr">
					<div class="navi-wrap">
						<ul class="navi">
							<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
							<li>입점관리</li>
							<li>입점사 정보관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- E : page-header -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">입점사 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>입점사 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
							<col style="width: 79px">
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">검색어</th>
								<td class="input" colspan="4">
									<!-- S : opt-keyword-box -->
									<div class="opt-keyword-box">
										<select id="searchKey" name="searchKey" class="ui-sel" title="검색어 타입 선택">
											<option value="vndrName">업체명</option>
											<option value="insdMgmtInfoText">입점사 코드(ERP)</option>
											<option value="bossName">대표자</option>
											<option value="bizNoText">사업자번호</option>
											<option value="vndrMngrName">업체담당자</option>
											<option value="searchModerName">최종수정자</option>
										</select>
										<input type="text" id="searchValue" name="searchValue" class="ui-input" title="검색어 입력" placeholder="검색어 입력" maxlength="100">
									</div>
									<!-- E : opt-keyword-box -->
								</td>
							</tr>
							<tr>
								<th scope="row">전시채널</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="checkbox" name="channelCheckAll" id="channelCheckAll" checked="checked">
												<label for="channelCheckAll">전체</label>
											</span>
										</li>
										<c:if test="${!empty channelList}">
											<c:forEach var="channelList" items="${channelList}" varStatus="status">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" custom="chnnlNo" name="chnnlNoList.chnnlNo" id="chkChannel_${channelList.chnnlNo}" value="${channelList.chnnlNo}" checked="checked">
														<label for="chkChannel_${channelList.chnnlNo}">${channelList.chnnlName}</label>
													</span>
												</li>
											</c:forEach>
										</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">거래상태</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
									<c:if test="${!empty codeList.VNDR_STAT_CODE}">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="vndrStatCode" id="vndrStatCheckAll" value= "" checked >
												<label for="vndrStatCheckAll">전체</label>
											</span>
										</li>
										<c:forEach var="vndrStatCode" items="${codeList.VNDR_STAT_CODE}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="vndrStatCode" id="radioDeal_${vndrStatCode.codeDtlNo}" value="${vndrStatCode.codeDtlNo}" >
													<label for="radioDeal_${vndrStatCode.codeDtlNo}">${vndrStatCode.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</c:if>
									</ul>
									<!-- E : ip-box-list -->
								</td>
							</tr>
							<!-- S : 20190313 수정 // 담당 관리자 영역 삭제, 브랜드 영역 단일검색 수정 -->
							<tr>
								<th scope="row">예외수수료 적용여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="exceptionCommissionApplyYn" id="radioExceptionCommissionApplyon" checked>
												<label for="radioExceptionCommissionApplyon">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="exceptionCommissionApplyYn" id="radioExceptionCommissionApplyY" value="Y">
												<label for="radioExceptionCommissionApplyY">적용</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="exceptionCommissionApplyYn" id="radioExceptionCommissionApplyN" value="N">
												<label for="radioExceptionCommissionApplyN">적용안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">브랜드</th>
								<td class="input">
									<span class="ip-search-box">
										<input type="text" class="ui-input" id="brandName" name="brandName" title="검색어 입력" placeholder="브랜드명 (국문/영문)" readonly>
										<input type="hidden" id="vndrBrandNo" name="vndrBrandNo">
										<a href="javascript:void(0);" id="serchBrandPop" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
									</span>
								</td>
							</tr>
							<tr>
								<th scope="row">임직원 할인 적용 여부</th>
								<td class="input">
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="employeeDiscountApplyYn" id="radioEmployeeDiscountApplyon" checked>
												<label for="radioEmployeeDiscountApplyon">전체</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="employeeDiscountApplyYn" id="radioEmployeeDiscountApplyY" value="Y">
												<label for="radioEmployeeDiscountApplyY">적용</label>
											</span>
										</li>
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="employeeDiscountApplyYn" id="radioEmployeeDiscountApplyN" value="N">
												<label for="radioEmployeeDiscountApplyN">적용안함</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->
								</td>
								<td></td>
								<th scope="row">표준 카테고리 (대)</th>
								<td class="input">
									<select class="ui-sel" title="표준 카테고리 (대) 선택" name="vndrStdCtgrNo" id="selVndrStdCtgrNo">
										<option value="">선택</option>
										<c:forEach var="standardCategory" items="${standardCategoryList}" varStatus="status">
											<option value="${standardCategory.stdCtgrNo}">${standardCategory.stdCtgrName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<!-- E : 20190313 수정 // 담당 관리자 영역 삭제, 브랜드 영역 단일검색 수정 -->

							<tr>
								<th scope="row">기간검색</th>
								<td class="input" colspan="4">
									<!-- S : term-date-wrap -->
									<span class="term-date-wrap">
										<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 검색 선택">
											<option value="">선택</option>
											<option value="rgstDtm" selected>등록일시</option>
											<option value="modDtm">수정일시</option>
										</select>
										<span class="date-box">
											<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
										</span>
										<span class="text">~</span>
										<span class="date-box">
											<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
										</span>
										<span class="btn-group">
											<a href="javascript:void(0);" name="perToday" class="btn-sm btn-func">오늘</a>
											<a href="javascript:void(0);" name="perWeek" class="btn-sm btn-func">일주일</a>
											<a href="javascript:void(0);" name="per1Month" class="btn-sm btn-func">한달</a>
											<a href="javascript:void(0);" name="perYearMonth" 	class="btn-sm btn-func text-center">1년</a>
										</span>
									</span>
									<!-- E : term-date-wrap -->
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" id="vendorFormReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" id="vendorInfoSerch" class="btn-normal btn-func">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			<!-- E : search-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">입점사 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="selTermsModule">목록개수</label>
						<select class="ui-sel" id="pageCount" name="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
					<!-- S : btn-group -->
					<span class="btn-group">
						<!-- TODO : SMS 발송 팝업 기획 fix 후 작업 -->
						<a href="javascript:void(0);" id="sendVendorSmsAll" class="btn-sm btn-link">전체 SMS발송</a>
						<a href="javascript:void(0);" id="sendVendorSmsSel" class="btn-sm btn-link">선택 SMS발송</a>

						<!-- TODO : 메일 발송 팝업 기획 fix 후 작업 -->
						<a href="javascript:void(0);" id="sendVendorEmailAll" class="btn-sm btn-link">전체 메일발송</a>
						<a href="javascript:void(0);" id="sendVendorEmailSel" class="btn-sm btn-link">선택 메일발송</a>
					</span>
					<!-- E : btn-group -->
				</div>
				<div class="fr">
					<a href="javascript:void(0);" id="vendorInfoRegist" class="btn-sm btn-link">등록</a>
					<a href="javascript:void(0);" id="vendorInfoExcelDown" class="btn-sm btn-func">엑셀 다운로드</a>
				</div>
			</div>

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="vendorInfoGrid"></div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</form>
	</div>
	<!-- E : container -->


<script src="/static/common/js/biz/vendor/abcmart.vendor.info.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>
