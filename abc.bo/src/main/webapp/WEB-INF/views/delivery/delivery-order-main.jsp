<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() { 
		abc.biz.delivery.order.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.main.siteCombo = ${siteCombo}; //stie 코드 값
				 
		abc.biz.delivery.order.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.delivery.order.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.delivery.order.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.delivery.order.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.delivery.order.main.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";
		
		//조회영역 셋팅
		abc.biz.delivery.order.main.event();
		
		//초기화 셋팅
		abc.biz.delivery.order.main.init();

		// 목록 그리드 초기화
		abc.biz.delivery.order.main.initDataListSheet();
		
		
		// 대시보드에서 넘어왔을경우
		if(!abc.text.allNull(abc.param.getParams().fromDash)){
			$("#dlvyStatCodeArr").trigger('click');
			$('[name="perOneMonth"]').trigger('click');
			
			$("input:checkbox[name='dlvyStatCodeArr'][value='${fromDashDlvyStatCode}']").prop('checked', true);
			if('${tabIdx}' == '2'){	// 자사 일 때
				$("input:checkbox[name='stockGbnCodeArr'][value='10003']").trigger('click');
			}else if('${tabIdx}' == '3'){	// 입점 일 때
				$("#stockGbnCodeArr").trigger('click');
				$("input:checkbox[name='stockGbnCodeArr'][value='10003']").trigger('click');
			}
			$("#gFormSearch").trigger('click');
		}
	});
	
	//조회가 정상적으로 발생된 이후 발생
	function dataListSheet_OnSearchEnd() {
		abc.biz.delivery.order.main.dataListSheetOnSearchEnd();
	} //end function 그리드 SarchEnd 이벤트 
	
</script>

		<!-- S : container -->
		<div class="container">
		<form id="gForm" name="gForm">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">배송조회<br></h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>배송조회</li>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">배송조회 검색 </h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>배송조회 검색</caption>
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
												<input  type="checkbox" name="siteNoArr" id="siteNoArr${status.count}" class="ip_chk" clickYn="siteNo"   checked value="${siteParam.siteNo}">
												<label for="siteNoArr${status.count}"><c:out value="${siteParam.siteName}"/></label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
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
												<option value="loginId">주문자ID</option>
											</select>
											<input type="text" id="searchOrderValue" name="searchOrderValue" class="ui-input" title="검색어 입력">
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">조회기간</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel"  name="searchDateKey" id="searchDateKey" title="기간 선택" >
												<option value="orderDtm">주문일</option>
												<option value="dlvyProcDtm">배송일</option>
												<option value="dlvyProcEndDtm">배송완료일</option>
											</select>
											<%@include file="/WEB-INF/views/common/perDateSearch.jsp"%>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
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
														<input type="checkbox" name="dlvyTypeCodeArr" id="dlvyTypeCodeArr${status.count}"  clickYn="dlvyTypeCode"  checked="checked" value="${code.codeDtlNo}">
														<label for="dlvyTypeCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">운송장 정보</th>
									<td class="input">
										<!-- S : opt-keyword-box -->
										<div class="opt-keyword-box">
											<select class="ui-sel" name="logisVndrCode" id="logisVndrCode" title="택배사 선택">
												<option selected value="">택배사 선택</option>
												<c:forEach items="${codeList.LOGIS_VNDR_CODE}" var="code"  varStatus="status">
												<option value="${code.codeDtlNo}"> ${code.codeDtlName}</option>
 												</c:forEach>
											
											</select>
											<input type="text"  name="waybilNoText" id="waybilNoText" class="ui-input" title="운송장번호 입력" placeholder="운송장번호">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">배송상태</th>
									<td class="input" colspan="4">
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="dlvyStatCodeArr" name="dlvyStatCodeArr" type="checkbox" value="" checked="checked">
													<label for="dlvyStatCodeArr">전체</label>
												</span>
											</li>
											<c:forEach items="${codeList.DLVY_STAT_CODE}" var="code"  varStatus="status">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="dlvyStatCodeArr${status.count}" name="dlvyStatCodeArr" type="checkbox"  clickYn="dlvyStatCode"  checked="checked" value="${code.codeDtlNo}">
														<label for="dlvyStatCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
											 
										</ul>
									</td>
								</tr>
								<tr>
									<th scope="row">발송처</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="stockGbnCodeArr" name="stockGbnCodeArr" type="checkbox" checked="checked" value="">
													<label for="stockGbnCodeArr">전체</label>
												</span>
											</li>
											<c:forEach items="${codeList.STOCK_GBN_CODE}" var="code" varStatus="status">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="stockGbnCodeArr${status.count}" name="stockGbnCodeArr" type="checkbox" clickYn="stockGbnCode"   checked="checked" value="${code.codeDtlNo}">
														<label for="stockGbnCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
											 
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">매장명</th>
									<td class="input">
										<!-- S : ip-text-box -->
										<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
										<span class="ip-search-box">
											<input type="text" name="storeNo" id="storeNo" class="ui-input" title="검색어 입력" readonly>
											<input type="text" name="storeName" id="storeName" class="ui-input" title="검색어 입력" readonly>
											<a href="javascript:void(0);" class="btn-search" id="searchStorePopUp"><i class="ico-search"><span class="offscreen">검색</span></i></a>
										</span>
										<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
									</td>
								</tr>
								<tr>
									<th scope="row">상품</th>
									<td class="input" colspan="4">
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
									<th scope="row">결제수단</th>
									<td class="input" colspan="4">
										<ul class="ip-box-list">
											
											 
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="pymntMeansCodeArr" name="pymntMeansCodeArr" type="checkbox" value="" checked="checked">
													<label for="pymntMeansCodeArr">전체</label>
												</span>
											</li>
											<c:forEach items="${codeList.PYMNT_MEANS_CODE}" var="code"  varStatus="status">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="pymntMeansCodeArr${status.count}" name="pymntMeansCodeArr" type="checkbox" clickYn="pymntMeansCode"  checked="checked" value="${code.codeDtlNo}">
														<label for="pymntMeansCodeArr${status.count}">${code.codeDtlName}</label>
													</span>
												</li> 
											</c:forEach>
												
												 
										</ul>
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
						<h3 class="content-title">배송조회 목록</h3>
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

						<span class="btn-group">
							<!-- DESC : html/delivery/BO-DL-01007.html 파일 확인 해주세요 -->
 							<a href="javascript:void(0)" id="orderReservationPopup"  name="orderReservationPopup"  class="btn-sm btn-func">발송지연 안내</a>
 						</span>

					</div>
					<div class="fr">
						<a href="javascript:void(0)" class="btn-sm btn-func" id="allExcelDown">엑셀 전체 다운로드</a>
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

<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
