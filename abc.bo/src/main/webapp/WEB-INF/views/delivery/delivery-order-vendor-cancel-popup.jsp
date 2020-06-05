<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
		$(function() { 
				//초기화
				abc.biz.delivery.order.vendor.cancel.popup.init();
		});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>판매취소 요청</h1>
		</div>
		<div class="window-content">
				<form id="gForm" name="gForm">			
			<!-- S : 판매취소 요청 등록화면 -->
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 판매취소 요청 시 A-RT 고객센터 담당자가 주문건을 확인하고 고객과 협의하여 주문취소를 진행합니다.</li>
					<li>* 판매취소 요청에 대한 현황은 “판매취소 요청 현황” 메뉴에서 확인하실 수 있습니다.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="guide-text">총 <span id="totalcnt">${ocOrderProductDeliveryList.size()}</span>건</span>
				</div>
			</div>
			<!-- E : tbl-controller -->
 
			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>취소 상품 정보</caption>
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
						<br>
						</c:forEach>
				</c:if>  
				</tbody>
			</table>
			<!-- E : tbl-col -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>판매취소 요청</caption>
				<colgroup>
					<col style="width:130px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">취소요청 사유</span>
						</th>
						<td class="input">
							<select class="ui-sel" title="취소요청 사유 선택" name="sellCnclRsnCode" id="sellCnclRsnCode"  required="취소요청 사유 선택">
									<option value="">선택해주세요</option>
									<c:forEach items="${codeList.SELL_CNCL_RSN_CODE}" var="code"  varStatus="status">
									<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
									</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">취소요청 내용(<span id="counter" class="state-num">0</span>/200자)
						</th>
						<td class="input">
							<textarea class="ui-textarea"  id="sellCnclRsnText" name="sellCnclRsnText"  title="취소요청 내용 입력" required="취소요청 내용 입력"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<ol class="tbl-desc-list">
					<li>* 입력하신 내용은 처리 후 수정이 불가합니다. 취소사유를 정확하게 입력해주세요.</li>
				</ol>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);"  id="popupCancel"  name="popupCancel"  class="btn-normal btn-del">취소</a>
				<a href="javascript:void(0);"  id="pupupSave" name="pupupSave"  class="btn-normal btn-link">판매취소요청</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
			</form>
	</div>
</body>
</html>
<script type="text/javascript" src="/static/common/js/biz/delivery/abcmart.delivery.order.vendor.cancel.popup.js<%=_DP_REV%>"></script>