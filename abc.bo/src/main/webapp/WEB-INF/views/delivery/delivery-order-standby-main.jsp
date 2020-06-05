<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() { 

		abc.biz.delivery.order.standby.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.standby.main.siteCombo = ${siteCombo}; //stie 코드 값
		
		abc.biz.delivery.order.standby.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.delivery.order.standby.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.delivery.order.standby.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.delivery.order.standby.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.delivery.order.standby.main.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";
		
		//조회영역 셋팅
		abc.biz.delivery.order.standby.main.event();

		//초기화 셋팅
		abc.biz.delivery.order.standby.main.init();

		// 목록 그리드 초기화
		abc.biz.delivery.order.standby.main.initDataListSheet();
	});
 
//일괄 재배송 결과	
function dataListSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	if(StCode =="200"){
		alert("재배송 처리되었습니다.");
		abc.biz.delivery.order.standby.main.doActionJs("search");
	}else{
		alert("재배송 처리가 실패되었습니다. \n 다시 시도해주세요.");
	}
} 

//조회가 정상적으로 발생된 이후 발생
function dataListSheet_OnSearchEnd() {
	abc.biz.delivery.order.standby.main.dataListSheetOnSearchEnd();
} //end function 그리드 SarchEnd 이벤트 

</script>

		<!-- S : container -->
		<div class="container">
			<form id="gForm" name="gForm">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">상품대기조회</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>상품대기조회</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품대기 조회 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>상품대기조회 검색</caption>
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
									<td class="input" colspan="4">
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
									<!-- DESC : 20190215 수정 // th 타이틀 수정 -->
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
												<select class="ui-sel" name="searchDateKey" id="searchDateKey" title="조회기간 선택">
												<option  value="orderDtm">주문일</option>
												<option  value="dlvyDscntcAcceptDtm">접수일</option>
											</select>
											 	<%@include file="/WEB-INF/views/common/perDateSearch.jsp"%>
										</span>
										<!-- E : term-date-wrap -->
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
								<tr>
									<th scope="row">처리상태</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="dlvyDscntcProc01" name="dlvyDscntcProc" type="radio" checked value="">
													<label for="dlvyDscntcProc01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="dlvyDscntcProc02" name="dlvyDscntcProc" type="radio"  value="01">
													<label for="dlvyDscntcProc02">대기</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="dlvyDscntcProc03" name="dlvyDscntcProc" type="radio" value="02">
													<label for="dlvyDscntcProc03">완료</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">미출처</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="stockGbnCode" name="stockGbnCode" type="radio" checked value="">
													<label for="stockGbnCode">전체</label>
												</span>
											</li> 
											<c:forEach items="${codeList.STOCK_GBN_CODE}" var="code"  varStatus="status">
												 <c:if test="${code.codeDtlNo != '10003'}">
												<li>
													<span class="ui-rdo">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input name="stockGbnCode" id="stockGbnCode${status.count}" type="radio" value="${code.codeDtlNo}">
														<label for="stockGbnCode${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											   </c:if>

										
											</c:forEach> 
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">배송중단사유</th>
									<td class="input">
										<select id="dlvyDscntcRsnCode" name="dlvyDscntcRsnCode" class="ui-sel" title="배송중단사유 선택">
											<!-- DESC : option value="" 값 기존 AS-IS와 동일  -->
											<option value="">선택</option>
											<c:forEach items="${codeList.DLVY_DSCNTC_RSN_CODE}" var="code"  varStatus="status">
											<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
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
														<input type="checkbox" name="dlvyTypeCodeArr" id="dlvyTypeCodeArr${status.count}"  clickYn="dlvyTypeCode"  checked="checked" value="${code.codeDtlNo}">
														<label for="dlvyTypeCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
								</tr>
								<tr>
									<th scope="row">처리구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list" id="procGubnCheckArea">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="procGubnCodeArr" id="procGubnCodeArr" checked value="">
													<label for="procGubnCodeArr">전체</label>
												</span>
											</li>											
											<c:forEach items="${codeList.PROC_GUBN_CODE}" var="code"  varStatus="status">
												<c:if test="${code.codeDtlNo ne '10001'}">
													<li>
														<span class="ui-chk">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="checkbox" name="procGubnCodeArr" id="procGubnCodeArr${status.count}"  clickYn="procGubnCodeArr"  checked="checked" value="${code.codeDtlNo}">
															<label for="procGubnCodeArr${status.count}">${code.codeDtlName}</label>
														</span>
													</li>
												</c:if> 
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
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
						<li>* 매장픽업 건과 배송지 기준 제주도 배송건은 선택 대상에서 제외됩니다.</li>
					</ol>
				</div>
				<!-- E : tbl-desc-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">상품대기조회 목록</h3>
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
						<span class="opt-group">
							<label class="title" for="selProdModule01">선택한 상품</label>
							<select  id="newStockGbnCode" name="newStockGbnCode" class="ui-sel" >
								<option value="">발송처 선택</option>								
								<c:forEach items="${codeList.STOCK_GBN_CODE}" var="code"  varStatus="status">
									   <c:if test="${code.codeDtlNo != '10003'}">
									   <option value="${code.codeDtlNo}">${code.codeDtlName}</option>
									   </c:if>
								</c:forEach> 
 							</select>

							<!-- S : 20190318 수정 // 매장 선택시 단일검색 추가 -- >
							<span class="ip-search-box">
								<input type="text" class="ui-input" title="검색어 입력" readonly>
								<a href="#" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
							</span>
							<  !-- E : 20190318 수정 // 매장 선택시 단일검색 추가 -->
						</span>
						<span class="btn-group">
							<a href="javascript:void(0);"  id="deliveryRepeat" class="btn-sm btn-func">일괄 재배송</a>
						</span>
						
						<span class="opt-group">
							<label class="title" for="procGubnSelect">&nbsp;&nbsp;처리구분</label>
							<select  id="procGubnSelect" name="procGubnSelect" class="ui-sel" >
								<c:forEach items="${codeList.PROC_GUBN_CODE}" var="code"  varStatus="status">
									<c:if test="${code.codeDtlNo ne '10001'}">
										<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
									</c:if>
								</c:forEach> 
 							</select>
						</span>
						<span class="btn-group">
							<a href="javascript:void(0);" id="procGubnConfirm" class="btn-sm btn-func">확정</a>
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
	
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.standby.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
