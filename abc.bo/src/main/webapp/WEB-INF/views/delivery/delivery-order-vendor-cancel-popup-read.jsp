<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
		$(function() { 
				//초기화
			 
		});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>판매취소 요청</h1>
		</div>
		<div class="window-content">  

			<!-- S : 판매취소요청 조회화면 -->
			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="guide-text">총 3건</span>
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
					<tr>
						<td class="text-center">${ocOrderProduct.prdtNo}</td>
						<td class="text-center">${ocOrderProduct.prdtName}</td>
						<td class="text-center">${ocOrderProduct.optnName}</td>
						<td class="text-center"><fmt:formatNumber value="${ocOrderProduct.sellAmt}" pattern="#,##0" />원</td>
						<td class="text-center">${ocOrderProduct.vndrPrdtNoText}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-col -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>판매취소요청</caption>
				<colgroup>
					<col style="width:130px">
					<col>
					<col style="width:130px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">취소요청 사유</th>
						<td colspan="3"> 
									<c:forEach items="${codeList.SELL_CNCL_RSN_CODE}" var="code"  varStatus="status">
									      <c:if test="${ code.codeDtlNo eq  ocOrderProduct.sellCnclRsnCode}">  
												${code.codeDtlName}
											</c:if>
									</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">취소요청 내용</th>
						<td colspan="3">${ocOrderProduct.sellCnclRsnText}</td>
					</tr>
					<tr>
						<th scope="row">요청일시</th>
						<td>${ocOrderProduct.sellCnclReqDtm}</td>
						<th scope="row">요청자</th>
						<td>${ocOrderProduct.sellCnclReqtrNo}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			
			<!-- S : tbl-desc-wrap -->
			<div class="tbl-desc-wrap">
				<div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div>
			</div>
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-save">확인</a>
			</div>
			<!-- E : window-btn-group -->
			<!-- E : 판매취소요청 조회화면 -->
		</div>
	</div>
</body>
</html>
