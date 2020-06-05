<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() {  
	 
		abc.biz.delivery.order.vendor.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.vendor.main.siteCombo = ${siteCombo}; //stie 코드 값

		abc.biz.delivery.order.vendor.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.delivery.order.vendor.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.delivery.order.vendor.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.delivery.order.vendor.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.delivery.order.vendor.main.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";
		
		abc.biz.delivery.order.vendor.main.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE = "${CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE}";
		
		// 목록 그리드 초기화
		abc.biz.delivery.order.vendor.main.initDataListSheet();

		//초기화 셋팅
		abc.biz.delivery.order.vendor.main.init();

		//조회영역 셋팅
		abc.biz.delivery.order.vendor.main.event();
		
		if(!abc.text.allNull(abc.param.getParams().fromDash)){
			$("#dlvyStatCode option:eq("+abc.param.getParams().selectBoxIndex+")").attr("selected", "selected");
			$('[name="perOneMonth"]').trigger('click');
		}
	});

	/**
	 *  업체 검색 결과 callback 이벤트
	 */
	function setSelectedVendors(data){
		$("#vndrName").val(data.arrayVndrName);
		$("#vndrNo").val(data.arrayVndrNo);
	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	function setSelectedBrand(data) {
			$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}

	//조회가 정상적으로 발생된 이후 발생
	function dataListSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response)  {
		
		//변경 대상 옵션 초기화
		var select = $("#newDlvyStatCode"); //변경 배송상태
				select.find("option").remove();
				select.append(new Option("선택", ""));

		//그리드 수정 불가 초기화
		dataListSheet.SetColEditable("logisVndrCode",0);    // 택배사
		dataListSheet.SetColEditable("waybilNoText",0);   //송장번호
		dataListSheet.SetColEditable("dlvyProcDtm",0);   // 발송일자

		  if(StCode =="200"){
				var dlvyStatCode = $("#dlvyStatCode").val(); //검색조건 배송상태
				//결제완료 -> 상품준비중
				if( dlvyStatCode =="10000")  {
					select.append(new Option("상품준비중", "10001"));
				//상품준비중 ->  배송중 [송장번호, - 택배사  - 발송일자 입력 필수]
				}else if( dlvyStatCode =="10001")  {
					select.append(new Option("배송중", "10003"));
					dataListSheet.SetColEditable("logisVndrCode",1);    // 택배사
					dataListSheet.SetColEditable("waybilNoText",1);   //송장번호
					dataListSheet.SetColEditable("dlvyProcDtm",1);   // 발송일자
				//배송중
				}else if( dlvyStatCode =="10003")  {
					select.append(new Option("배송완료", "10005"));
				}
				//주문상품 상태별 건수
				abc.biz.delivery.order.vendor.main.getDeliveryOrderVendorPrdtStatCnt();
		  } 
		
		// 한페이지의 목록 갯수
		var pageCount = $('#pageCount').val();
		// 현재 페이지 번호
		var currentPage = dataListSheet.GetCurrentPage();
		// 현재 페이지 첫 로우
		var pageFirstRow;
		
		if( currentPage == 1){
			pageFirstRow = 1;
		}else if( currentPage > 1 ){
			pageFirstRow = (currentPage - 1) * currentPage + 1;
		}
		
		for(var i=pageFirstRow; i<=dataListSheet.GetDataLastRow(); i++){

			var sellCnclReqYn =  dataListSheet.GetCellValue( i , "sellCnclReqYn"); //판매취소 요청					
			var memberNo = dataListSheet.GetRowData(i).memberNo;
			
			if( memberNo != abc.biz.delivery.order.vendor.main.NON_MEMBER_NO){
				dataListSheet.SetRowData(i, {buyerName : dataListSheet.GetRowData(i).buyerName + "(" + dataListSheet.GetRowData(i).buyerId + ")"});
			}else{
				dataListSheet.SetCellFontUnderline(i,28,0);
			}
			//조회 default를 만든다

		//상단에서 ROWDATA를 변경하기에 수정으로 처리되는것을 초기화 시킨다.
		//setcellvalue로 처리해되 들듯 한데
		dataListSheet.SetCellValue(i, 0, "");

			//판매취소요청건은 색상을 칠하자
			if(sellCnclReqYn =='Y'){
				dataListSheet.SetRowBackColor(i,  "#ccff00"); 
			}
			
			// 2020.05.25 : "S" 아닌 매출구분은 언더라인 삭제
			if( dataListSheet.GetRowData(i).salesCnclGbnType != "S" ){
				dataListSheet.SetCellFontUnderline(i ,"orderNo", 0) ;
			}
			
			// 2020.05.25 : 교환재배송건 파랗게 보이게
			if( dataListSheet.GetRowData(i).salesCnclGbnType == "D" ){
				dataListSheet.SetRowBackColor(i, "#E0ECF8");
			}

			// 취소완료건은 붉은색 처리
			if( dataListSheet.GetRowData(i).orderPrdtStatCode == abc.biz.delivery.order.vendor.main.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE ){
				dataListSheet.SetRowBackColor(i, "#F5A9A9");
			}
		}
	} 

//일괄 재배송 결과	
function dataListSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	  if(StCode =="200"){
		  alert("변경 처리되었습니다.");
		  abc.biz.delivery.order.vendor.main.doActionJs("search");
	  }else{
		  alert("변경  처리가 실패되었습니다. \n 다시 시도해주세요.");
	  }
} 

/*
  엑셀 다운로드 후 호출
*/
function dataListSheet_OnDownFinish(downloadType, result) {
		alert(downloadType + ' 다운이 완료되었습니다.');
	}

 

</script> 
		<!-- S : container -->
		<div class="container">
		<form id="gForm" name="gForm"  method="post" onsubmit="return false;">
			 <input type="hidden" id="excelType" name="excelType" value="ALL">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">업체 주문 배송관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>업체 배송관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">업체 주문 배송관리</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="guide-text">최근 3개월 이내 주문 기준</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0);" id="icoRefresh"   class="btn-sm btn-func"><i class="ico ico-refresh"></i>새로고침</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : tbl-col -->
				<table class="tbl-col">
					<caption>업체 주문 배송관리</caption>
					<colgroup>
						<col>
						<col style="width: 17%;">
						<col>
						<col>
						<col>
						<col>
					</colgroup>
					<thead>
						<tr>
							<th scope="col">결제완료 (미확인 주문)</th>
							<th scope="col">상품준비중 (송장등록 대기)</th>
							<th scope="col">상품출고</th>
							<th scope="col">배송중</th>
							<th scope="col">배송완료</th>
							<th scope="col">주문취소</th>
						</tr>
					</thead>
					<tbody>
							<tr>
								<td class="text-center"><a href="javascript:void(0);" class="link"><span class="cntNumber" id="orderPrdtStatCodeComplete">-</span></a>건</td>
								<td class="text-center"><a href="javascript:void(0);" class="link"><span class="cntNumber" id="orderPrdtStatCodeProductPreparation">-</span></a>건</td>
								<td class="text-center"><a href="javascript:void(0);" class="link"><span class="cntNumber" id="orderPrdtStatCodeProductDelivery">-</span></a>건</td>
								<td class="text-center"><a href="javascript:void(0);" class="link"><span class="cntNumber" id="orderPrdtStatCodeDeliveryIng">-</span></a>건</td>
								<td class="text-center"><a href="javascript:void(0);" class="link"><span class="cntNumber" id="orderPrdtStatCodeDeliveryFinish">-</span></a>건</td>
								<td class="text-center"><span class="cntNumber" id="orderPrdtStatCodeCancelComplete">-</span>건</td>
							</tr>
					</tbody>
				</table>
				<!-- E : tbl-col -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>업체 주문 배송관리 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">업체구분</th>
									<td>
										<!-- S : 자사 통합몰 BO담당자인 경우, 노출되는 영역 -->
										<!-- DESC : td영역 input 클래스 추가 해주세요 -->
										<!-- S : ip-text-box -->
										<!--
										<span class="ip-text-box">
											<select class="ui-sel" title="입점사 선택">
												<option>전체</option>
											</select>
											<span class="search-dropdown-box">
												<select class="search-dropdown" title="입점사 자동완성" data-placeholder="입점사 선택"></select>
											</span>
										</span>
										-->
										<span class="ip-search-box">
										<c:if test="${isAdmin}">
												<!-- S : 자사 통합몰 BO담당자인 경우, 노출되는 영역 -->
												<!-- DESC : td영역 input 클래스 추가 해주세요 -->
												<!-- S : ip-text-box -->											
												<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName" readonly>
												<input type="text" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo" readonly>
												<a href="javascript:void(0);" class="btn-search" id="searchVendor"><i class="ico-search"><span class="offscreen" >검색</span></i></a>											
												<!-- E : ip-text-box -->
												<!-- E : 자사 통합몰 BO담당자인 경우, 노출되는 영역 -->			
												<input type="hidden" name="vndrGbnType" id="vndrGbnType" value="N">
										</c:if>
										<c:if test="${!isAdmin}">
												<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName" readonly value="${vndrName}">
												<input type="text" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo" readonly value="${vndrNo}">
												<input type="hidden" name="vndrGbnType" id="vndrGbnType" value="Y">
										</c:if>
										</span>

										<!-- E : ip-text-box -->
										<!-- E : 자사 통합몰 BO담당자인 경우, 노출되는 영역 -->
									</td>
									<td></td>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNoArr" id="siteNoArr0" value="" checked>
													<label for="siteNoArr0">전체</label>
												</span>
											</li>
											<c:forEach var="siteParam" items="${siteList}" varStatus="status">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNoArr" id="siteNoArr${status.count}"  clickYn="siteNoArr"   value="${siteParam.siteNo}">
													<label for="siteNoArr${status.count}"><c:out value="${siteParam.siteName}"/></label>
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
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="deviceCode01" name="deviceCode" type="radio" checked="checked" value="">
													<label for="deviceCode01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="deviceCode02" name="deviceCode" type="radio" value="10000">
													<label for="deviceCode02">PC</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="deviceCode03" name="deviceCode" type="radio" value="10001">
													<label for="deviceCode03">Mobile</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="deviceCode04" name="deviceCode" type="radio"  value="10002">
													<label for="deviceCode04">APP</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">주문상품구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rsvOrderYn01" name="rsvOrderYn" type="radio" checked="checked" value="">
													<label for="rsvOrderYn01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rsvOrderYn02" name="rsvOrderYn" type="radio" value="N">
													<label for="rsvOrderYn02">일반</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rsvOrderYn03" name="rsvOrderYn" type="radio" value="Y">
													<label for="rsvOrderYn03">예약</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">주문</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">											
											<select name="searchOrderKey"   id="searchOrderKey"  class="ui-sel" title="검색어 타입 선택">
												<option value="buyerName">주문자</option>
												<option value="rcvrName">수령자</option>
												<option value="loginId">주문자ID</option>
												<option value="buyerHdphnNoText">주문자휴대폰</option>
											</select>
											<input type="text" id="searchOrderValue" class="ui-input" title="검색어 입력"> 
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">주문번호</th>
									<td class="input">
										<input type="text" class="ui-input" name="orderNo" id="orderNo"  title="주문번호 입력" maxlength="13">
									</td>
								</tr>
								<tr>
									<th scope="row">주문배송상태</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<select class="ui-sel" title="주문배송상태 선택" name="dlvyStatCode" id="dlvyStatCode">
												<option value="">전체</option>
												<option value="10000">결제완료</option>
												<option value="10001">상품준비중</option>
												<!-- <option value="10002">상품출고</option> -->
												<option value="10003">배송중</option>
												<option value="10005">배송완료</option>
												<!-- c:forEach items="${codeList.DLVY_STAT_CODE}" var="code"  varStatus="status"
												<option  value="${code.codeDtlNo}">${code.codeDtlName}</option>
												c:forEach --> 
											</select>

											<!-- S : 상품준비중 option 선택시에만 노출 -->
											<span class="ui-chk" id="waybilNoDisplay" style="display:none">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="waybilNoYn" type="checkbox" name="waybilNoYn"checked="checked" value="Y">
												<label for="waybilNoYn">운송장 정보 있음</label>
											</span>
											<!-- E : 상품준비중 option 선택시에만 노출 -->
										</span>
										<!-- E : ip-text-box -->
									</td>
									<td></td>
									<th scope="row">쿠폰 사용여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="cpnDscntYn01" name="cpnDscntYn" type="radio" checked="checked" value="">
													<label for="cpnDscntYn01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="cpnDscntYn02" name="cpnDscntYn" type="radio" value="Y">
													<label for="cpnDscntYn02">사용</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="cpnDscntYn03" name="cpnDscntYn" type="radio" value="N">
													<label for="cpnDscntYn03">미사용</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">조회기간</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" name="searchDateKey" id="searchDateKey" title="조회기간 선택">
												<option  value="orderDtm">주문일</option>
												<option  value="dlvyDscntcAcceptDtm">결제완료일</option>
												<option>발송일</option>
											</select> 
											<%@include file="/WEB-INF/views/common/perDateSearch.jsp"%>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">상품</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
												<select class="ui-sel" title="검색어 타입 선택" name="searchOrderProdKey"  id="searchOrderProdKey">
												<option value="prdtName">상품명</option>
												<option value="prdtNo">상품코드</option>
												<option value="styleInfo">스타일코드</option>
												<option value="vndrPrdtNoText">업체상품코드</option>
											</select>
											<input type="text" name="searchOrderProdValue"  id="searchOrderProdValue" class="ui-input" title="검색어 입력">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">브랜드 *</th>
									<td class="input">
										<!-- TODO : Search Drop down 기능 협의 후 수정 -->
										<!-- S : search-dropdown-box -->
										<span class="ip-search-box">
											<input type="text" class="ui-input" title="브랜드명" name="brandName" id="brandName"  readonly>
											<input type="text" class="ui-input" title="브랜드코드" name="brandNo" id="brandNo" readonly>
											<a href="javascript:void(0);" id="serchBrandPop" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
										<!-- E : search-dropdown-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">발송지연 안내 여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="delaySendYn01" name="delaySendYn" type="radio" checked="checked" value=""> 
													<label for="delaySendYn01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="delaySendYn02" name="delaySendYn" type="radio" value="Y">
													<label for="delaySendYn02">예</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="delaySendYn03" name="delaySendYn" type="radio" value="N">
													<label for="delaySendYn03">아니오</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">판매취소요청</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="sellCnclReqYn01" name="sellCnclReqYn" type="radio" checked="checked" value="">
													<label for="sellCnclReqYn01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="sellCnclReqYn02" name="sellCnclReqYn" type="radio" value="Y">
													<label for="sellCnclReqYn02">예</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="sellCnclReqYn03" name="sellCnclReqYn" type="radio" value="N">
													<label for="sellCnclReqYn03">아니오</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0);" id="gFormReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0);"  id="gFormSearch"  class="btn-normal btn-func">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				<!-- E : search-wrap -->
	 
				<!-- S : tbl-desc-wrap -->
				<div class="tbl-desc-wrap border-box">
					<ol class="tbl-desc-list">
						<li>1. 택배사 코드는 다운로드하여 참고해주시기 바랍니다. </li>
						<li>2. 엑셀 업로드 발송처리 시 처리하고자 하는 주문건에 대한 "선택 다운로드" 후 택배사코드, 운송장번호를 입력하여 업로드하시기 바랍니다. </li>
						<li>3. 엑셀 업로드 발송처리의 경우 필수항목 (택배사코드, 운송장번호) 미입력시 등록오류 처리되어 배송중으로 변경되지 않습니다. (정상등록건만 발송처리됩니다.)</li>
						<li>4. 주문배송상태에 따라 배송상태를 일괄 적용하실 수 있습니다.</li>
						<li>5. 목록에서 취소 요청 <input type="text"  style="width:40px;height:20px;background-color:#ccff00; border:1px solid #ccff00;" readonly>  , 배송취소 <input type="text"  style="width:40px;height:20px;background-color:#F5A9A9; border:1px solid #F5A9A9;" readonly> 색상으로 표기됩니다.</li>
					</ol>
					<div class="fr">
						<a href="javascript:void(0)" id="logisVndrCodeExcelDown" class="btn-sm btn-func">택배사 코드 다운로드(엑셀)</a>
						<a href="javascript:void(0)" id="logisVndrCodePopup" class="btn-sm btn-func">택배사 코드 보기(팝업)</a>
					</div>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selViewModule01">목록개수</label>
							<select id="pageCount" class="ui-sel">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
						<span class="opt-group">
							<label class="title" for="newDlvyStatCode">선택한 상품</label>
							<select class="ui-sel" name="newDlvyStatCode"  id="newDlvyStatCode">
							<option value="">선택</option>
								<!-- 
									<option value="">선택</option>
								<option value="10001">상품준비중</option>
								<option value="10002">상품출고</option>
								<option value="10003">배송중</option>
								<option value="10005">배송완료</option>
								-->
							</select>
						</span>
						<span class="btn-group">
							<a href="javasccript:void(0)"  id="dlvyStatCodeUpdate" class="btn-sm btn-func">일괄적용</a>
							<span class="gap"></span>
							<!-- DESC : html/delivery/BO-DL-01007.html 파일 확인 해주세요 -->
							<a href="javascript:void(0)" id="orderExcelUpload" class="btn-sm btn-func">엑셀 일괄 업로드</a>
							<a href="javascript:void(0)" id="orderReservationPopup"  name="orderReservationPopup"  class="btn-sm btn-func">발송지연 안내</a>
							<a href="javascript:void(0)" id="orderCancelPopup"  name="orderCancelPopup"  class="btn-sm btn-func">판매취소 요청</a>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" id="deliverySave" class="btn-sm btn-save">저장</a>
						<a href="javascript:void(0)" id="excelDownSelect" class="btn-sm btn-func">엑셀 선택 다운로드</a>
						<a href="javascript:void(0)" id="excelDownAll" class="btn-sm btn-func">엑셀 전체 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dataListGrid"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
				</form>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
