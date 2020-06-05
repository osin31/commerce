<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<%@include file="/WEB-INF/views/order/order-const.jsp"%>
<script type="text/javascript">
	$(function() {

		abc.biz.order.order.init();

		abc.biz.order.order.siteCombo = ${siteCombo};
		abc.biz.order.order.codeCombo = ${codeCombo};

		<%-- 주문 목록 초기 세팅. --%>
		abc.biz.order.order.initOrderSheet();

		<%-- 주문  목록 조회. --%>
		$("#searchBtn").click(function(){
			abc.biz.order.order.orderDoAction('search');
		});
		
		<%-- 휴대폰 번호 뒷자리 입력 시 숫자만 입력되도록 설정 --%>
		$("#buyerHdphnBackNo").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		<%-- 주문 검색어 선택 이벤트. --%>
		$("#orderSearchKey").change(function(){
			var searchType = $(this).val();
			abc.biz.order.order.ordMbInfoSelectChange(searchType);
		});
		
		<%-- enter 검색 --%>
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});

		<%-- 심의 팝업  --%>
		$("#reviewBtn").click(function(){
			abc.biz.order.order.reviewRegPop();
		});

		<%-- 수선 팝업  --%>
		$("#repairBtn").click(function(){
			abc.biz.order.order.repairRegPop();
		});
		
		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#searchBtn").trigger("click");
			}
		});

		$("#detailBtn").click(function(){
			var params = {};
			var url = "/order/read-detail-pop";
			abc.open.popup({
				winname :	"detailPop",
				url 	:	url,
				width 	:	1240,
				height	:	990,
				params	:	params
			});
		});
		
		//페이지 목록 개수 클릭시
		$("#pageCount").change(function(){
			$("#searchBtn").trigger('click');
		});
		
		// 대시보드에서 넘어왔을경우
		if(!abc.text.allNull(abc.param.getParams().fromDash)){
			$("#orderPrdtStatCodeAll").trigger('click');
			$('[name="perOneMonth"]').trigger('click');
			
			if('${fromDashOrderPrdtStatCode}'== '10002'){	// 결제완료 일 때
				$("input:checkbox[custom='orderPrdtStatCode'][value='${fromDashOrderPrdtStatCode}']").prop('checked', true);	// 결제완료 체크	
			}else if('${fromDashOrderPrdtStatCode}'== '10003'){	// 상품준비중 일 때
				$("input:checkbox[custom='orderPrdtStatCode'][value='${fromDashOrderPrdtStatCode}']").prop('checked', true);	// 결제완료 체크
				$("input:checkbox[custom='orderPrdtStatCode'][value='10004']").prop('checked', true);	// 상품출고 체크
			}

			if('${tabIdx}' == '2'){	// 자사 일 때
				$("input:checkbox[custom='stockGbnCode'][value='10003']").trigger('click');
			}else if('${tabIdx}' == '3'){	// 입점 일 때
				$("#stockGbnCodeAll").trigger('click');
				$("input:checkbox[custom='stockGbnCode'][value='10003']").trigger('click');
			}
		}
	});

	function orderSheet_OnRowSearchEnd(row){
		var memberTypeCode 			= orderSheet.GetCellValue(row, "memberTypeCode");

		if (memberTypeCode == abc.order.consts.MEMBER_TYPE_CODE_NONMEMBER) {
			var nonUserDpName = orderSheet.GetCellValue(row, "nonUserDpName");
			orderSheet.SetCellFontUnderline( row ,"buyerGridName" , 0 ) ;
			orderSheet.SetCellValue(row, "buyerGridName", nonUserDpName);
		}

	}

	<%-- 그리드 Click 이벤트 --%>
	function orderSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		var memberTypeCode 			= orderSheet.GetCellValue(Row, "memberTypeCode");

		if ( Row != 0) {
			if ( orderSheet.ColSaveName(Col) == "orderNo" && Value != "" ) {
				var saveName = "orderNo";
				abc.orderDetailPopup(orderSheet ,  saveName , Row, Col);
			}
			if ( orderSheet.ColSaveName(Col) == "buyerGridName" && Value != "" ) {
				if (memberTypeCode != abc.order.consts.MEMBER_TYPE_CODE_NONMEMBER) {
					abc.memberDetailPopup(orderSheet.GetRowData(Row).memberNo);
				}
			}
		}
	}

	function setSelectedVendors(arg){
		$("#vndrName").val(arg.arrayVndrName);
		$("#vndrNo").val(arg.arrayVndrNo);
	}


</script>

<!-- S : container -->
<div class="container">
	<form id="orderForm" name="orderForm" method="post" onsubmit="return false;" >
	<input type="hidden" id="strOrderNos" 		name="strOrderNos" value="">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">주문정보관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
						<li>주문관리</li>
						<li>주문정보관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">주문정보 검색</h3>
			</div>
		</div>
		<!-- E : content-header -->

		<!-- S : search-wrap -->
		<div class="search-wrap">
			<div class="search-inner">
				<table class="tbl-search">
					<caption>주문정보 검색</caption>
					<colgroup>
						<col style="width: 110px;">
						<col>
						<col style="width: 79px">
						<col style="width: 110px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">회원유형</th>
							<td class="input">
							<ul class="ip-box-list">
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkMemberTypeAll" name="chkMemberTypeAll" type="checkbox" checked>
										<label for="chkMemberTypeAll">전체</label>
									</span>
								</li>
								<c:forEach var="memberTypeCode" items="${codeList.MEMBER_TYPE_CODE}" varStatus="status">
									<li>
										<span class="ui-chk">
											<input id="chkMemberType${memberTypeCode.codeDtlNo}" value="${memberTypeCode.codeDtlNo}"  custom="memberTypeCode" name="memberTypeCodes"  type="checkbox" checked="checked">
											<label for="chkMemberType${memberTypeCode.codeDtlNo}">${memberTypeCode.codeDtlName}</label>
										</span>
									</li>
								</c:forEach>
								<li>
									<span class="ui-chk">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="chkMemberTypeERP" name="chkMemberTypeERP" custom="memberTypeCode" type="checkbox" checked>
										<label for="chkMemberTypeERP">임직원</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->

							<!-- S : member-grade-list -->
							<!-- 회원 등급 삭제 21090920 
							<span class="member-grade-list">
								<span class="text">( 회원등급 :</span>
								!-- S : ip-box-list --
								<ul class="ip-box-list">
									<c:forEach var="mbshpGradeCode" items="${codeList.MBSHP_GRADE_CODE}" varStatus="status">
										<li>
											<span class="ui-chk">
												<input id="chkMbshpGrade${mbshpGradeCode.codeDtlNo}" value="${mbshpGradeCode.codeDtlNo}"  custom="mbshpGradeCode" name="mbshpGradeCodes"  type="checkbox" checked="checked">
												<label for="chkMbshpGrade${mbshpGradeCode.codeDtlNo}">${mbshpGradeCode.codeDtlName}</label>
											</span>
										</li>
									</c:forEach>
								</ul>
								!-- E : ip-box-list --
								<span class="text">)</span>
							</span>
							 -->
						</td>
							<td></td>
							<th scope="row">사이트</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="siteNo" id="radioSite01" value="" checked>
											<label for="radioSite01">전체</label>
										</span>
									</li>
									<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
										<li>
											<span class="ui-rdo">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input type="radio" name="siteNo" id="radioSite${status.count}" value="${siteInfo.siteNo}">
												<label for="radioSite${status.count}"><c:out value="${siteInfo.siteName}"/></label>
											</span>
										</li>
									</c:forEach>
								</ul>
								<!-- E : ip-box-list -->
							</td>
						</tr>
						<tr>
							<th scope="row">결제구분</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">

									<li>
										<span class="ui-chk">
											<input id="deviceCodeAll" name="deviceCodeAll" type="checkbox" checked="checked">
											<label for="deviceCodeAll">전체</label>
										</span>
									</li>

									<c:if test="${!empty codeList.DEVICE_CODE}">
										<c:forEach items="${codeList.DEVICE_CODE}" var="deviceCode">
											<li>
												<span class="ui-chk">
													<input id="deviceCode${deviceCode.codeDtlName}" value="${deviceCode.codeDtlNo}"  custom="deviceCode" name="deviceCodes"  type="checkbox" checked="checked">
													<label for="deviceCode${deviceCode.codeDtlName}">${deviceCode.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</c:if>

								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">주문배송유형</th>
							<td class="input">
								<!-- S : ip-text-box -->
								<span class="ip-text-box">
									<select id="dlvyTypeCode" name="dlvyTypeCode" class="ui-sel" title="주문배송유형 선택">
										<option value="">전체</option>
										<c:forEach items="${codeList.DLVY_TYPE_CODE}" var="dlvyTypeCode">
											<option value="${dlvyTypeCode.codeDtlNo}" >${dlvyTypeCode.codeDtlName}</option>
										</c:forEach>
										<option value="99999">택배전환</option>
									</select>

									<!-- S : 전체 이외 항목 선택시 노출되는 영역 -->
									<select class="ui-sel" title="주문배송유형 선택" id="chnnlNo" name="chnnlNo" style="display:none;" >
										<option value="">전체</option>
									</select>
									<!-- E : 전체 이외 항목 선택시 노출되는 영역 -->
								</span>
								<!-- E : ip-text-box -->
							</td>
						</tr>
						<tr>
							<th scope="row">발송처</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<input id="stockGbnCodeAll" name="stockGbnCodeAll" type="checkbox" value="all" checked="checked">
											<label for="stockGbnCodeAll">전체</label>
										</span>
									</li>

									<c:if test="${!empty codeList.STOCK_GBN_CODE}">
										<c:forEach items="${codeList.STOCK_GBN_CODE}" var="stockGbnCode">
											<li>
												<span class="ui-chk">
													<input id="stockGbnCode${stockGbnCode.codeDtlName}" value="${stockGbnCode.codeDtlNo}" custom="stockGbnCode" name="stockGbnCodeList.stockGbnCode"  type="checkbox" checked="checked">
													<label for="stockGbnCode${stockGbnCode.codeDtlName}">${stockGbnCode.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</c:if>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">입점사</th>
							<td class="input">
								<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
								<span class="ip-search-box">
									<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName" maxlength="70">
									<input type="hidden" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo">
									<a href="javascript:void(0);" class="btn-search" id="searchVendor"><i class="ico-search"><span class="offscreen" >검색</span></i></a>
								</span>
								<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
							</td>
						</tr>
						<tr>
							<th scope="row">주문</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
 								<select class="ui-sel" title="검색어 타입 선택" id="orderSearchKey" name="orderSearchKey" title="주문자정보 유형 선택" >
										<option value="buyerName" >주문자명</option>
										<option value="rcvrName" >수령자</option>
										<option value="buyerHdphnNoText" >휴대폰 번호</option>
										<option value="loginId" >주문자ID</option>
									</select>
									<!-- DESC : 휴대폰번호 선택시, input 영역 placeholder="-없이 전체 입력" 속성 추가 해주세요  -->
									<input type="text" class="ui-input" title="검색어 입력"  id="orderSearchText" value="" name="orderSearchText" maxlength="30">
									<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
								</div>
								<!-- E : opt-keyword-box -->
							</td>
							<td></td>
							<th scope="row">주문번호</th>
							<td class="input">
								<input type="text" class="ui-input" title="주문번호 입력" name="orderNo" id="orderNo" maxlength="13">
							</td>
						</tr>
						<tr>
							<th scope="row">상품</th>
							<td class="input">
								<!-- S : opt-keyword-box -->
								<div class="opt-keyword-box">
									<select class="ui-sel" title="검색어 타입 선택" name="productSearchKey">
										<option value="prdtNo">상품코드</option>
										<option value="prdtName" selected="selected">상품명</option>
										<option value="styleInfo" >스타일코드</option>
										<option value="vndrPrdtNoText" >업체상품코드</option>
									</select>
									<input type="text" class="ui-input" title="검색어 입력" name="productSearchText" maxlength="70" >
								</div>
								<!-- E : opt-keyword-box -->
							</td>
							<td></td>
							<th scope="row">브랜드</th>
							<td class="input">
								<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
								<span class="ip-search-box">
									<input type="text" class="ui-input" title="검색어 입력" name="brandName" id="brandName" maxlength="70">
									<input type="hidden" class="ui-input" title="검색어 입력" name="brandNo" id="brandNo">
									<a href="#" id="serchBrandPop" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
								</span>
								<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->

							</td>
						</tr>
						<tr>
							<th scope="row">주문유형</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rsvOrderYn" id="rsvOrderYn01" value="" checked>
											<label for="rsvOrderYn01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rsvOrderYn" id="rsvOrderYn02" value="N">
											<label for="rsvOrderYn02">일반</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="rsvOrderYn" id="rsvOrderYn03" value="Y" >
											<label for="rsvOrderYn03">예약</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">사이즈</th>
							<td class="input">
								<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
								<span class="ip-search-box">
									<input type="text" class="ui-input" title="검색어 입력" name="optnName" id="optnName" maxlength="100">
								</span>
								<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->

							</td>
						</tr>
						<tr>
							<th scope="row">기간검색</th>
							<td class="input" colspan="4">
								<!-- S : term-date-wrap -->
								<span class="term-date-wrap">
									<select class="ui-sel" title="기간 선택" name="orderDateKey">
										<option value="orderDtm" >주문일</option>
										<option value="pymntDtm" >결제확인일</option>
									</select>
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
										<a href="javascript:void(0);" name="perYearMonth" 	class="btn-sm btn-func text-center">1년</a>
									</span>
								</span>
								<!-- E : term-date-wrap -->
							</td>
						</tr>
						<tr>
							<th scope="row">쿠폰사용여부</th>
							<td class="input">
								<!-- S : ip-box-list -->
								<ul class="ip-box-list">
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="cpnDscntAmtYn" id="cpnDscntAmt01" value="" checked >
											<label for="cpnDscntAmt01">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="cpnDscntAmtYn" id="cpnDscntAmt02" value="Y">
											<label for="cpnDscntAmt02">사용</label>
										</span>
									</li>
									<li>
										<span class="ui-rdo">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input type="radio" name="cpnDscntAmtYn" id="cpnDscntAmt03" value="N">
											<label for="cpnDscntAmt03">미사용</label>
										</span>
									</li>
								</ul>
								<!-- E : ip-box-list -->
							</td>
							<td></td>
							<th scope="row">주문취소여부</th>
							<td class="input">
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkCancelAll" name="chkCancelAll" type="checkbox" value="all" >
											<label for="chkCancelAll">전체</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkCancel02" name="chkCancel" type="checkbox" value="cancel" >
											<label for="chkCancel02">전체취소</label>
										</span>
									</li>
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkCancel03" name="chkCancel" type="checkbox" value="part" >
											<label for="chkCancel03">부분취소</label>
										</span>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">주문배송상태</th>
							<td class="input" colspan="4">
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="orderPrdtStatCodeAll" name="orderPrdtStatCodeAll" value="all" type="checkbox" checked="checked">
											<label for="orderPrdtStatCodeAll">전체</label>
										</span>
									</li>
									<c:if test="${!empty codeList.ORDER_PRDT_STAT_CODE}">
										<c:forEach items="${codeList.ORDER_PRDT_STAT_CODE}" var="orderPrdtStatCode">
											<c:if test="${orderPrdtStatCode.codeDtlNo != '10000'}"> <!-- 임시주문 제거 -->
												<li>
													<span class="ui-chk">
														<input id="orderPrdtStatCode${orderPrdtStatCode.codeDtlName}" value="${orderPrdtStatCode.codeDtlNo}" custom="orderPrdtStatCode" name="orderPrdtStatCodeList.orderPrdtStatCode"  type="checkbox" checked="checked">
														<label for="orderPrdtStatCode${orderPrdtStatCode.codeDtlName}">${orderPrdtStatCode.codeDtlName}</label>
													</span>
												</li>
											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</td>
						</tr>
						<tr>
							<th scope="row">결제수단</th>
							<td class="input" colspan="4">
								<ul class="ip-box-list">
									<li>
										<span class="ui-chk">
											<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
											<input id="chkPaymentAll" name="chkPaymentAll" type="checkbox" value="all" checked="checked">
											<label for="chkPaymentAll">전체</label>
										</span>
									</li>
									<c:if test="${!empty codeList.PYMNT_MEANS_CODE}">
										<c:forEach items="${codeList.PYMNT_MEANS_CODE}" var="paymentCode">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkPayment${paymentCode.codeDtlNo}" value="${paymentCode.codeDtlNo}" custom="pymntMeansCode" name="pymntMeansCodeList.pymntMeansCode" type="checkbox" checked="checked">
													<label for="chkPayment${paymentCode.codeDtlNo}">${paymentCode.codeDtlName}</label>
												</span>
											</li>
										</c:forEach>
									</c:if>
									<!--
									<li>
										<span class="ui-chk">
											<input id="chkPaymentESCR" name="chkPaymentESCR" type="checkbox" checked="checked">
											<label for="chkPaymentESCR">에스크로 적용</label>
										</span>
									</li>
									 -->
								</ul>
								<!-- S : 20190227 수정 // 에스크로 적용 영역 수정 -->
								<!-- S : member-grade-list -->
								<div class="member-grade-list">
									
									<span class="text">에스크로 적용 (</span>
									<!-- S : ip-box-list -->
									<ul class="ip-box-list">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkEscroTrue" name="chkEscroTrue" custom="escrApplyYn" type="checkbox" checked>
												<label for="chkEscroTrue">예</label>
											</span>
										</li>
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="chkEscroFalse" name="chkEscroFalse" custom="escrApplyYn" type="checkbox" checked>
												<label for="chkEscroFalse">아니오</label>
											</span>
										</li>
									</ul>
									<!-- E : ip-box-list -->

									<span class="text">)</span>
								</div>
								<!-- E : member-grade-list -->
								<!-- E : 20190227 수정 // 에스크로 적용 영역 수정 -->
							</td>
						</tr>
					</tbody>
				</table>
				<div class="confirm-box">
					<div class="fl">
						<a href="javascript:void(0);" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" id="searchBtn" class="btn-normal btn-func">검색</a>
					</div>
				</div>
			</div>
			<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
		</div>
		<!-- E : search-wrap -->

		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">주문정보 목록</h3>
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
				<!--
				<span class="btn-group">
					<span class="opt-desc">선택한 주문</span>
					!-- DESC : 활성화시 disabled 클래스 삭제 해주세요 --
					<a href="#" id="reviewBtn" class="btn-sm btn-link">A/S 심의 접수</a>
					<a href="#" id="repairBtn" class="btn-sm btn-link disabled">A/S 수선 접수</a>
				</span>
				  -->
			</div>
			<div class="fr">
				<button type="button" id="selectOrderForExcel" class="btn-sm btn-func">엑셀 선택 다운로드</button>
				<button type="button" id="OrderForExcel" class="btn-sm btn-func">엑셀 전체 다운로드</button>
			</div>
		</div>
		<!-- E : tbl-controller -->

		<!-- S : ibsheet-wrap -->
		<div class="ibsheet-wrap">
			<div id="orderGrid" style="width:100%; height:429px;">
			</div>
		</div>
		<!-- E : ibsheet-wrap -->
	</div>
	</form>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/order/abcmart.order.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>