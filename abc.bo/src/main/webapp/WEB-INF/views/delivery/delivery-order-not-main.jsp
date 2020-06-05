<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
	$(function() { 
		abc.biz.delivery.order.not.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.not.main.siteCombo = ${siteCombo}; //stie 코드 값
				 
		abc.biz.delivery.order.not.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.delivery.order.not.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.delivery.order.not.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.delivery.order.not.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.delivery.order.not.main.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";
		
		//조회영역 셋팅
		abc.biz.delivery.order.not.main.event();
		// 목록 그리드 초기화
		abc.biz.delivery.order.not.main.initDataListSheet();
				
		//초기화 셋팅
		abc.biz.delivery.order.not.main.init();

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
		abc.biz.delivery.order.not.main.dataListSheetOnSearchEnd();
	} //end function 그리드 SarchEnd 이벤트
	
	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	function setSelectedBrand(data) {
			$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}
	
</script>

		<!-- S : container -->
		<div class="container">
		<form id="gForm" name="gForm">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">미출고 현황</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>미출고 현황 </li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">미출고 현황 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>미출고 현황 검색</caption>
							<colgroup>
								<col style="width: 110px;">
								<col>
								<col style="width: 79px">
								<col style="width: 110px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사이트 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="siteNoArr" id="siteNoArr" checked value="">
													<label for="siteNoArr">전체</label>
												</span>
											</li>
											<c:forEach var="siteParam" items="${siteList}" varStatus="status">
											<li>
												<span class="ui-chk">
											    <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input  type="checkbox" name="siteNoArr" id="siteNoArr${status.count}" class="ip_chk" clickYn="siteNo" checked value="${siteParam.siteNo}">
												<label for="siteNoArr${status.count}"><c:out value="${siteParam.siteName}"/></label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">입점사</th>
									<td class="input">
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
												<input type="hidden" name="vndrGbnType"  id="vndrGbnType" value="N">
										</c:if>
										<c:if test="${!isAdmin}">
												<input type="text" class="ui-input" title="검색어 입력" name="vndrName" id="vndrName" readonly value="${vndrName}">
												<input type="text" class="ui-input" title="검색어 입력" name="vndrNo" id="vndrNo" readonly value="${vndrNo}">
												<input type="hidden" name="vndrGbnType" id="vndrGbnType" value="Y">
										</c:if>
										</span>
 									</td>
								</tr>
								<c:if test="${userDetails.adminAuthorities[0].upAuthNo ne Const.ROLE_VENDER_GROUP}">	<%-- 업체 관리자는 보이지 않는다.  --%>
								<tr>
									<th scope="row">배송유형</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="dlvyTypeCodeArr" id="dlvyTypeCodeArr" checked value="">
													<label for="dlvyTypeCodeArr">전체</label>
												</span>
											</li>
											
											<c:forEach items="${codeList.DLVY_TYPE_CODE}" var="code"  varStatus="status">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="checkbox" name="dlvyTypeCodeArr" id="dlvyTypeCodeArr${status.count}" clickYn="dlvyTypeCode" checked="checked" value="${code.codeDtlNo}">
														<label for="dlvyTypeCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">픽업 예정 매장</th>
									<td class="input">
										<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
										<span class="ip-search-box">
											<input type="text" name="storeNo" id="storeNo" class="ui-input" title="검색어 입력" readonly>
											<input type="text" name="storeName" id="storeName" class="ui-input" title="검색어 입력" readonly>
											<a href="javascript:void(0);" class="btn-search" id="searchStorePopUp"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
										<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
									</td>
								</tr>
								</c:if>
								<tr>
									<th scope="row">미출고조회</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<span class="ip-text-box">
											<span class="text">결제완료일 기준</span>
											<select class="ui-sel" name="orderDlvOverDay" id="orderDlvOverDay" title="결제완료일 선택">
												<option value="3">3일</option>
												<option value="5">5일</option>
												<option value="7">1주일(7일)</option>
												<option value="14">2주일(14일)</option>
												<option value="30">한달(30일)</option>
											</select>
											<span class="text">일 이상 경과</span>
										</span>
										<!-- E : ip-text-box -->
									</td>
									<td></td>
									<th scope="row">주문</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select name="searchOrderKey"   id="searchOrderKey"  class="ui-sel" title="검색어 타입 선택">
												<option value="buyerName">주문자</option>
												<option value="rcvrName">수취인</option>
												<option value="orderNo">주문번호</option>
												<option value="loginId">주문자ID&nbsp;&nbsp;&nbsp; </option>
											</select>
											<input type="text" id="searchOrderValue" class="ui-input" title="검색어 입력">
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								<tr>
									<c:if test="${userDetails.adminAuthorities[0].upAuthNo ne Const.ROLE_VENDER_GROUP}">	<%-- 업체 관리자는 보이지 않는다.  --%>
									<th scope="row">배송예외구분</th>
									<td class="input">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요
													
													-->
													<input id="deliveryEtcStat1" name="deliveryEtcStatArr" type="checkbox" value="dlvyDscntcYn">
													<label for="deliveryEtcStat1">상품대기</label>
												</span>
											</li>
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 
													define : 클레임에서 분실 재배송 접수된 상품기준의 데이타 기준
													-->
													<input id="deliveryEtcStat2" name="deliveryEtcStatArr" type="checkbox" clickYn="deliveryEtcStat" value="missProcYn">
													<label for="deliveryEtcStat2">분실대기</label>
												</span>
											</li>
										</ul>
									</td>
									<td></td>
									</c:if>
									<th scope="row">상품</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" title="검색어 타입 선택" name="searchOrderProdKey"  id="searchOrderProdKey">
												<option value="prdtName">상품명</option>
												<option value="prdtNo">상품코드</option>
												<option value="styleInfo">스타일코드</option>
											</select>
											<input type="text" name="searchOrderProdValue"  id="searchOrderProdValue" class="ui-input" title="검색어 입력">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								<c:if test="${userDetails.adminAuthorities[0].upAuthNo ne Const.ROLE_VENDER_GROUP}">	<%-- 업체 관리자는 보이지 않는다.  --%>
								<tr>
									<th scope="row">자사/입점</th>
									<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk">
															<!-- DESC : 비활성화시 disabled 속성 추가, input id / label for 동일하게 맞춰주세요 -->
															<input id="mmnyPrdtYn_Y" type="checkbox" name="mmnyPrdtYnArr" value="Y">
															<label for="mmnyPrdtYn_Y">자사</label>
														</span>
													</li>
													<li>
														<span class="ui-chk">
															<!-- DESC : 비활성화시 disabled 속성 추가, input id / label for 동일하게 맞춰주세요 -->
															<input id="mmnyPrdtYn_N" type="checkbox" name="mmnyPrdtYnArr" value="N">
															<label for="mmnyPrdtYn_N">입점</label>
														</span>
													</li>
												</ul>
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
								</c:if>
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
						<h3 class="content-title">미출고현황 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select id="pageCount" name="rowsPerPage" class="ui-sel">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:abc.biz.delivery.order.not.main.downloadAllExcel()" class="btn-sm btn-func">엑셀 전체 다운로드</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="dataListGrid"></div> 
					</div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</form>
		</div>
		<!-- E : container -->

<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.not.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
