<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
		$(function() { 
			//초기화
			abc.biz.delivery.order.vendor.reservation.popup.init();
		});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>발송 지연 안내</h1>
		</div>
		<div class="window-content">
		<form id="gForm" name="gForm">	
			<!-- S : 발송지연안내 등록화면 -->
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
			   
				<ol class="tbl-desc-list">
					<li>* 발송지연 안내 처리는 1회만 가능하며, 입력하신 지연사유는 구매자에게 안내됩니다.</li>
					<li>* 발송기한 경과시까지 발송처리가 되지 않으면 구매자 취소요청 시 즉시 환불처리</li>
					<li>* 발송지연 안내는 결제완료,상품준비중상태만 가능 합니다.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="guide-text">총  <span id="totalcnt">${ocOrderProductDeliveryList.size()}</span>건</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>대상 상품 정보</caption>
				<colgroup>
					<col>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
					<thead>
						<tr>
							<th scope="col">상품코드</th>
							<th scope="col">상품명</th>
							<th scope="col">옵션</th>
							<th scope="col">결제금액</th>
							<th scope="col">업체상품코드</th>
						</tr>
					</thead>
				<tbody>
			  <c:if test="${!empty ocOrderProductDeliveryList}">
					<c:forEach var="ocOrderProduct" items="${ocOrderProductDeliveryList}" varStatus="status">
					<tr>
						<td class="text-center">${ocOrderProduct.prdtNo}</td>
						<td class="text-center">${ocOrderProduct.prdtName}</td>
						<td class="text-center">${ocOrderProduct.optnName}</td>
						<td class="text-center"><fmt:formatNumber value="${ocOrderProduct.sellAmt}" pattern="#,##0" />원</td>
						<td class="text-center">${ocOrderProduct.vndrPrdtNoText}</td>
					</tr>
						<input type="hidden" id="orderNo" name="orderNo" value="${ocOrderProduct.orderNo}">
						<input type="hidden" id="dlvyIdText" name="dlvyIdText" value="${ocOrderProduct.dlvyIdText}">
						<input type="hidden" id="prdtNo" name="prdtNo" value="${ocOrderProduct.prdtNo}">
						<input type="hidden" id="orderPrdtSeq" name="orderPrdtSeq" value="${ocOrderProduct.orderPrdtSeq}">
						<input type="hidden" id="prdtName" name="prdtName" value="${ocOrderProduct.prdtName}">
						<input type="hidden" id="optnName" name="optnName" value="${ocOrderProduct.optnName}">
						<br>
						</c:forEach>
				</c:if>  
				</tbody>
			</table>
			<!-- E : tbl-col -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>발송지연안내</caption>
				<colgroup>
					<col style="width:130px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">예상 발송일</span>
						</th>
						<td class="input">
							<span class="date-box">
								<input type="text" data-role="datepicker" name="delaySendDay" id="delaySendDay" class="ui-cal js-ui-cal" title="예상 발송일 선택"  required="예상 발송일">
							</span>
							<span class="text">결제일부터 기준 3개월 이내 날짜만 선택 가능</span>
							<input type="hidden" id="pymntDtm" value="<fmt:formatDate value="${mainPayment.pymntDtm}" pattern="yyyy.MM.dd"/>">
							<input type="hidden" id="limitDlvyDtm" value="">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">발송지연사유</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="발송지연사유 선택" id="cnslScriptSeq" name="cnslScriptSeq" required="발송지연사유 선택">
								<option value="">선택하세요</option>								
								  <c:if test="${!empty cmCounselScriptList}">
										<c:forEach var="cmScript" items="${cmCounselScriptList}" varStatus="status">
												<option value="${cmScript.cnslScriptSeq}">${cmScript.cnslScriptTitleText}</option>
										</c:forEach>
								</c:if>   
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">발송지연 상세사유(<span id="counter" class="state-num">0</span>/200자)
						</th>
						<td class="input">
							<textarea class="ui-textarea" title="상세사유 입력" id="mesgContText" name="mesgContText" required="발송지연 상세사유"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 입력하신 내용은 처리 후 수정이 불가합니다. 발송기한을 정확하게 입력해주세요.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->
			<!-- E : 발송지연안내 등록화면 -->
 

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- DESC : 조회화면일경우, 취소버튼 미노출 -->
				<a href="javascript:void(0);"  id="popupCancel"  name="popupCancel"  class="btn-normal btn-del">취소</a>
				<a href="javascript:void(0);"  id="pupupSave" name="pupupSave"  class="btn-normal btn-link">확인</a>
			</div>
			<!-- E : window-btn-group -->
			</form>
		</div>
	</div>
</body>
</html>
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.reservation.popup.js<%=_DP_REV%>"></script>