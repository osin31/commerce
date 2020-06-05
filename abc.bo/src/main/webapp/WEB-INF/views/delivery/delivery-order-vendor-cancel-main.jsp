<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() { 

		abc.biz.delivery.order.vendor.cancel.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.vendor.cancel.main.siteCombo = ${siteCombo}; //stie 코드 값
		
		abc.biz.delivery.order.vendor.cancel.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.delivery.order.vendor.cancel.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.delivery.order.vendor.cancel.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.delivery.order.vendor.cancel.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.delivery.order.vendor.cancel.main.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";
		
		abc.biz.delivery.order.vendor.cancel.main.fromDash = "${fromDash}";
		
		//조회영역 셋팅
		abc.biz.delivery.order.vendor.cancel.main.event();

		//초기화 셋팅
		abc.biz.delivery.order.vendor.cancel.main.init();

		// 목록 그리드 초기화
		abc.biz.delivery.order.vendor.cancel.main.initDataListSheet();
		
		// from dashboard
		abc.biz.delivery.order.vendor.cancel.main.fromDashBoard();
	});

	
	/**
	 *  업체 검색 결과 callback 이벤트
	 */
	function setSelectedVendors(data){
		$("#vndrName").val(data.arrayVndrName);
		$("#vndrNo").val(data.arrayVndrNo);
	}
	
	//조회가 정상적으로 발생된 이후 발생
	function dataListSheet_OnSearchEnd() {
		abc.biz.delivery.order.vendor.cancel.main.dataListSheetOnSearchEnd();
	} //end function 그리드 SarchEnd 이벤트 
	
</script>
		<!-- S : container -->
		<div class="container">
		<form id="gForm" name="gForm"  method="post" onsubmit="return false;">
			 <input type="hidden" id="excelType" name="excelType" value="ALL">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">판매 취소 요청 현황</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>판매 취소 요청 현황 </li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">판매 취소 요청 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>판매 취소 요청 검색</caption>
							<colgroup>
								<col style="width: 110px;">
								<col>
								<col style="width: 79px">
								<col style="width: 110px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">업체구분</th>
									<td class="input">
										<!-- S : ip-text-box -- >
										<span class="ip-text-box">
											<select class="ui-sel" title="업체구분 선택">
												<option>전체</option>
											</select>
											< !-- S : 20190305 수정 // search dropdown 단일검색 수정 -- >
											<span class="ip-search-box">
												<input type="text" class="ui-input" title="검색어 입력" readonly>
												<a href="#" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
											</span>
											< !-- E : 20190305 수정 // search dropdown 단일검색 수정 -- >
										</span>
										< !-- E : ip-text-box -->
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
									</td>
									<td></td>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : 20190215 수정 // radio 버튼으로 수정 -->
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="radio" name="siteNo" id="siteNo" value="" checked>
													<label for="siteNo">전체</label>
												</span>
											</li>
											<c:forEach var="siteParam" items="${siteList}" varStatus="status">
											<li>
												<span class="ui-rdo">
											    <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input  type="radio" name="siteNo" id="siteNo${status.count}"  class="ip_chk"  value="${siteParam.siteNo}">
												<label for="siteNo${status.count}"><c:out value="${siteParam.siteName}"/></label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
										<!-- E : 20190215 수정 // radio 버튼으로 수정 -->
									</td>
								</tr>
								<tr>
									<th scope="row">주문상품구분</th>
									<td class="input">
										<!-- S : ip-box-lis t -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="rsvOrderYn01" name="rsvOrderYn" type="radio" checked value="">
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
									<td></td>
									<th scope="row">결제구분</th>
									<td class="input">
										<!-- S : 20190215 수정 // checkbox로 수정 -->
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input name="deviceCodeArr" id="deviceCodeArr"   type="checkbox" value=""   checked>
													<label for="deviceCodeArr">전체</label>
												</span>
											</li>
											<c:forEach items="${codeList.DEVICE_CODE}" var="code"  varStatus="status"> 
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="deviceCodeArr" id="deviceCodeArr${status.count}"  clickYn="deviceCode"  checked="checked" value="${code.codeDtlNo}">
														<label for="deviceCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
										<!-- E : 20190215 수정 // checkbox로 수정 -->
									</td>
								</tr>
								<tr>
									<th scope="row">주문</th>
									<td class="input"> 
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select name="searchOrderKey"   id="searchOrderKey"  class="ui-sel" title="검색어 타입 선택">
												<option value="buyerName">주문자</option>
												<option value="rcvrName">수취인</option>
												<option value="orderNo">주문번호</option>
												<option value="loginId">주문자ID</option>
												<option value="buyerHdphnNoText">주문자휴대폰</option>
											</select>
											<input type="text" id="searchOrderValue" class="ui-input" title="검색어 입력">
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">취소요청 사유</th>
									<td class="input">
										<select class="ui-sel" title="취소요청 사유 선택" name="sellCnclRsnCode" id="sellCnclRsnCode">
									 			<option selected value="">전체</option>
												<c:forEach items="${codeList.SELL_CNCL_RSN_CODE}" var="code"  varStatus="status">
												<option value="${code.codeDtlNo}"> ${code.codeDtlName}</option>
 												</c:forEach> 
										</select>
									</td>
								</tr>
								<tr>
									<!-- DESC : 20190215 수정 // th 타이틀 수정 -->
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
												<select class="ui-sel" name="searchDateKey" id="searchDateKey" title="조회기간 선택">
												<option  value="orderDtm">주문일</option>
												<option  value="sellCnclReqDtm">요청일</option>
											</select>
											 	<%@include file="/WEB-INF/views/common/perDateSearch.jsp"%>
										</span>
										<!-- E : term-date-wrap -->
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

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">판매취소요청 주문 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select id="pageCount" class="ui-sel">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
							<a href="javascript:void(0);"  id="excelDownSelect"class="btn-sm btn-func">엑셀 선택 다운로드</a>
						<a href="javascript:void(0);" id="excelDownAll" class="btn-sm btn-func">엑셀 전체 다운로드</a>
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
	
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.cancel.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>