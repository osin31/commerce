<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>


<script type="text/javascript">
	$(function() {  
	 
		abc.biz.delivery.order.loss.main.codeCombo = ${codeCombo}; //combo 코드 값
		abc.biz.delivery.order.loss.main.siteCombo = ${siteCombo}; //stie 코드 값
		abc.biz.delivery.order.loss.main.NON_MEMBER_NO = "${Const.NON_MEMBER_NO}";

		// 목록 그리드 초기화
		abc.biz.delivery.order.loss.main.initDataListSheet();

		//초기화 셋팅
		abc.biz.delivery.order.loss.main.init();

		//조회영역 셋팅
		abc.biz.delivery.order.loss.main.event();
	});

	//분실배송조회 저장 결과	
	function dataListSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		  if(StCode =="200"){
			  alert("저장 되었습니다.");
			  abc.biz.delivery.order.loss.main.doActionJs("search");
		  }else{
			  alert("저장에 실패되었습니다. \n 다시 시도해주세요.");
		  }
	} 
	
	//조회가 정상적으로 발생된 이후 발생
	function dataListSheet_OnSearchEnd() {
		abc.biz.delivery.order.loss.main.dataListSheetOnSearchEnd();
	} //end function 그리드 SarchEnd 이벤트 
	
</script>

		<!-- S : container -->
		<div class="container">
		<form id="gForm" name="gForm"  method="post" onsubmit="return false;" >
			<input type="hidden" id="excelType" name="excelType" value="ALL">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">분실배송조회</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>주문관리</li>
								<li>배송관리</li>
								<li>분실배송조회</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">분실배송조회 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>분실배송조회 검색</caption>
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
										<!-- S : 20190215 수정 // radio 버튼으로 수정 -->
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
												<input  type="checkbox" name="siteNoArr" id="siteNoArr${status.count}" class="ip_chk"  clickYn="siteNo"  checked value="${siteParam.siteNo}">
												<label for="siteNoArr${status.count}"><c:out value="${siteParam.siteName}"/></label>
												</span>
											</li>
											</c:forEach>
										</ul>
										<!-- E : ip-box-list -->
										<!-- E : 20190215 수정 // radio 버튼으로 수정 -->
									</td>
									<td></td> 
									<td>
										<!-- S : 20190305 수정 // search dropdown 단일검색 수정 -->
										<!-- S : 자사 A-RT BO담당자인 경우, 노출되는 영역 -->
										<!-- DESC : td영역 input 클래스 추가 해주세요 -->
										<!-- S : ip-text-box -->
										<!--
										<span class="ip-text-box">
											<select class="ui-sel" title="입점사 선택">
												<option>전체</option>
											</select>
											<span class="ip-search-box">
												<input type="text" class="ui-input" title="검색어 입력" readonly>
												<a href="#" class="btn-search"><i class="ico-search"><span class="offscreen">검색</span></i></a>
											</span>
										</span>
										 -->
										<!-- E : ip-text-box -->
										<!-- E : 자사 A-RT BO담당자인 경우, 노출되는 영역 -->
										<!-- E : 20190305 수정 // search dropdown 단일검색 수정 -->
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
											</select>
											<input type="text" id="searchOrderValue" class="ui-input" title="검색어 입력">
											<input type="text" id="buyerHdphnBackNo" name="buyerHdphnBackNo" class="ui-input" title="휴대폰번호 뒷자리 입력" placeholder="휴대폰번호 뒤4자리입력" maxlength="4">
										</div>
										<!-- E : opt-keyword-box -->
									</td>
									<td></td>
									<th scope="row">운송장정보</th>
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
									<!-- DESC : 20190215 수정 // th 타이틀 수정 -->
									<th scope="row">기간검색</th>
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
									<th scope="row">분실처리구분</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="missProcTypeCode01" name="missProcTypeCode" type="radio" checked value="">
													<label for="missProcTypeCode01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input  id="missProcTypeCode02" name="missProcTypeCode"  type="radio" value="10001">
													<label for="missProcTypeCode02">분실주문취소</label>
												</span>
											</li> 
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input  id="missProcTypeCode03" name="missProcTypeCode"  type="radio" value="10002">
													<label for="missProcTypeCode03">분실재배송</label>
												</span>
											</li>
										</ul>
										<!-- E : ip-box-list -->
									</td>
									<td></td>
									<th scope="row">처리완료여부</th>
									<td class="input">
										<!-- S : ip-box-list -->
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="missProcYn01" name="missProcYn" type="radio" checked value="">
													<label for="missProcYn01">전체</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="missProcYn02" name="missProcYn" type="radio"  value="Y">
													<label for="missProcYn02">처리</label>
												</span>
											</li>
											<li>
												<span class="ui-rdo">
													<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="missProcYn03" name="missProcYn" type="radio" value="N">
													<label for="missProcYn03">미처리</label>
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

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">분실배송조회 목록</h3>
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
							<!--
							<span class="title">선택한 상품</span>
								DESC : 자사인경우, disabled 클래스 추가 해주세요
							  <a href="#" class="btn-sm btn-func">재배송 처리</a>  
						</span>
						 -->
					</div>
					<div class="fr">
						<a href="javascript:void(0)" id="deliverySave" class="btn-sm btn-save">저장</a>
						<span class="gap"></span>
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
	
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.loss.main.js<%=_DP_REV%>"></script>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
