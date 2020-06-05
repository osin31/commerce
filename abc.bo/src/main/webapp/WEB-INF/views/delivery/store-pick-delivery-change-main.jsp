<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() { 

		abc.biz.store.pick.delivery.change.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.store.pick.delivery.change.main.siteCombo = ${siteCombo}; //stie 코드 값
		
		abc.biz.store.pick.delivery.change.main.SITE_NO_ART	= "${Const.SITE_NO_ART}";
		abc.biz.store.pick.delivery.change.main.SITE_NO_OTS	= "${Const.SITE_NO_OTS}";
		
		abc.biz.store.pick.delivery.change.main.ABC_FO_URL = "${Const.SERVICE_DOMAIN_ART_FO}";
		abc.biz.store.pick.delivery.change.main.OTS_FO_URL = "${Const.SERVICE_DOMAIN_OTS_FO}";
		
		abc.biz.store.pick.delivery.change.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";
		
		//조회영역 셋팅
		abc.biz.store.pick.delivery.change.main.event();

		//초기화 셋팅
		abc.biz.store.pick.delivery.change.main.init();

		// 목록 그리드 초기화
		abc.biz.store.pick.delivery.change.main.initDataListSheet();
	});
	
	//조회가 정상적으로 발생된 이후 발생
	function dataListSheet_OnSearchEnd() {
		abc.biz.store.pick.delivery.change.main.dataListSheetOnSearchEnd();
	} //end function 그리드 SarchEnd 이벤트 
</script>

		<!-- S : container -->
		<div class="container">
			<form id="gForm" name="gForm">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">매장픽업 택배전환</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>매장픽업 택배전환</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">매장픽업 택배전환 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>매장픽업 택배전환 검색</caption>
							<colgroup>
								<col style="width: 110px;">
								<col>
								<col style="width: 79px">
								<col style="width: 110px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">채널 구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-chk">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input type="checkbox" name="chanListArr" id="chanListArr" value="" checked>
													<label for="chanListArr">전체</label>
												</span>
											</li>
											<c:forEach var="chanParam" items="${chanList}" varStatus="status">
											<li>
												<span class="ui-chk">
											    <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input  type="checkbox" name="chanListArr" id="chanListArr${status.count}" clickYn="chanList"   checked class="ip_chk"  value="${chanParam.chnnlNo}">
												<label for="chanListArr${status.count}"><c:out value="${chanParam.chnnlName}"/></label>
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
											<select name="searchOrderKey"   id="searchOrderKey"  class="ui-sel" title="검색어 타입 선택" required="검색어 타입 선택">
												<option value="buyerName">주문자</option>
												<option value="rcvrName">수취인</option>
												<option value="orderNo">주문번호</option>
												<option value="loginId">주문자ID</option>
											</select>
											<input type="text" id="searchOrderValue" name="searchOrderValue" value="" class="ui-input" title="검색어 입력">
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
								</tr>
								<tr>
									<th scope="row">기간 검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select class="ui-sel" name="searchDateKey" id="searchDateKey" title="조회기간 선택">
												<option  value="logisCnvrtReqDtm">신청접수일</option>
												<option  value="dlvyProcDtm">발송일</option>
												<option  value="dlvyDscntcProcDtm">처리완료일</option>
											</select>
											<%@include file="/WEB-INF/views/common/perDateSearch.jsp"%>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
								<tr>
									<th scope="row">택배전환 상태</th>
									<td class="input">
										<!-- S : ip-text-box -->
											<span class="ip-text-box">
												<select class="ui-sel" id="selectChangeType" name="selectChangeType" title="발송여부">
													<option value="" selected>전체</option>
													<option value="send" >발송</option>
													<option value="noSend">미발송</option>
												</select>

												<select class="ui-sel" id="deliveryAddressYn" name="deliveryAddressYn" title="미발상태">
													<option value="" selected>전체</option>
													<option value="Y">신청</option>  <!-- 미발송 배송지 주소가 없는 경우-->
													<option value="N">접수</option>  <!-- 미발송 배송지 주소가 있는 경우-->
												</select>

												<select class="ui-sel" id="dlvyStatCode" name="dlvyStatCode" title="발송상태">
													<option value="" selected>전체</option>
													<option value="10002">상품출고</option>  <!-- 발송 -->
													<option value="10003">배송중</option> <!-- 발송 -->
													<option value="10005">배송완료</option> <!-- 발송 -->
													<option value="10008">구매확정</option> <!-- 발송 -->
												</select>

										</div>
									</td>
									<td></td>
									<th scope="row">전환사유</th>
									<td class="input">
										<select class="ui-sel" id="logisCnvrtRsnCode" name="logisCnvrtRsnCode"title="전환사유 선택">
											<option value="">전체</option>
											<c:forEach items="${codeList.LOGIS_CNVRT_RSN_CODE}" var="code"  varStatus="status">
											<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row">발송처 (매장명)</th>
									<td class="input" colspan="4">
										 <!-- S : ip-text-box -->
										<span class="ip-text-box">
											<!-- select class="ui-sel" id="storeGbnCode" name="storeGbnCode" title="매장구분 선택">
												<option value="">매장구분 선택</option>
												<c:forEach items="${codeList.STORE_GBN_CODE}" var="code" varStatus="status">
													<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select-->
											<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
											<span class="ip-search-box">
												<input type="text" name="storeNo" id="storeNo" class="ui-input" title="검색어 입력" readonly>
												<input type="text" name="storeName" id="storeName" class="ui-input" title="검색어 입력" readonly>
												<a href="javascript:void(0);" class="btn-search" id="searchStorePopUp"><i class="ico-search"><span class="offscreen">검색</span></i></a>
											</span>
											<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
										</span>
										<!-- E : ip-text-box -->
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
						<h3 class="content-title">매장픽업 택배전환 목록</h3>
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
							<span class="title">선택한 상품</span>
							<a href="javascript:void(0);"  id="deliveryChangePopup" class="btn-sm btn-func">택배전환 정보입력</a>
						</span>
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

<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.store.pick.delivery.change.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
