<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
$(function() {
	//abc.biz.order.memo.init('${orderNo}');
});
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>[주문번호 <span>${orderNo}</span>] 옵션 변경</h1>
		</div>
		<div class="window-content">

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">상품정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>상품정보</caption>
				<colgroup>
					<col>
					<col style="width: 70%">
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">상품코드</th>
						<th scope="col">상품명</th>
						<th scope="col">옵션</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-center">${prdtInfo.prdtNo}</td>
						<td class="input clear-float">
							<!-- S : prod-box -->
							<span class="prod-box">
								<!-- DESC : 이미지 크기는 100x100px 고정으로 이미지 사이즈가 다를경우 가로 100px 고정으로 비율에 맞춰 조절되며,
											썸네일 이미지 경로는 style="background-image:url();"에서 해당 이미지에 맞게 경로 수정이 필요합니다.
											이미지 크기 변경시 css 수정 필요 -->
								<c:choose>
									<c:when test="${not empty prdtInfo.imageUrl}">
										<span class="thumb-box" style="background-image:url(${prdtInfo.imageUrl});"></span>
									</c:when>
									<c:otherwise>
										<span class="thumb-box" >${prdtInfo.altrnText}</span>
									</c:otherwise>
								</c:choose>
								<span class="text">[${prdtInfo.brandName}] ${prdtInfo.prdtName}</span>
							</span>
							<!-- E : prod-box -->
						</td>
						<td class="text-center">${prdtInfo.optnName}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-col -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">변경 옵션 ${promoNo}</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>변경 옵션 </caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
				<input type="hidden" id="addOption" 	value="${addOptn}" />
				<input type="hidden" id="prdtNo" 		value="${prdtInfo.prdtNo}" />
				<input type="hidden" id="prdtOptnNo" 	value="${prdtInfo.prdtOptnNo}" />
				<input type="hidden" id="mmnyPrdtYn" 	value="${prdtInfo.mmnyPrdtYn}" />
				<input type="hidden" id="optnAddAmt" 	value="${prdtInfo.optnAddAmt}" />
				<input type="hidden" id="orderQty" 		value="${prdtInfo.orderQty}" />
				<input type="hidden" id="promoNo" 		value="${promoNo}" />
				<input type="hidden" id="layerType" 	value="${layerType}" />
				<input type="hidden" id="dlvyTypeCode" 	value="${dlvyTypeCode}" />
					<tr>
						<th scope="row">옵션 선택</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list vertical">
								<c:choose>
									<c:when test="${addOptn < 1}">
										<li>
											<select class="ui-sel size-full" title="옵션 선택"  id="option_size">
												<c:forEach items="${prdtOptList}" var="optn">
													<option ${optn.optnSelected == 'true' ? 'selected' : ''} value="${optn.prdtOptnNo}" data-qty="${optn.stockTotQty}" data-optnAddAmt="${optn.optnAddAmt}"><c:if test="${optn.stockTotQty <= 0}">[품절]</c:if>	${optn.optnName}</option>
												</c:forEach>
											</select>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<select class="ui-sel size-full" title="옵션 선택"  id="option_size">
												<c:forEach items="${OptnList}" var="optn">
													<option ${optn.optnSelected == 'true' ? 'selected' : ''} value="${optn.prdtOptnNo}"  data-qty="${optn.stockTotQty}" data-optnAddAmt="${optn.optnAddAmt}"><c:if test="${optn.stockTotQty <= 0}">[품절]</c:if>${optn.optnName}</option>
												</c:forEach>
											</select>
										</li>
										<li>
											<select class="ui-sel size-full" title="옵션 선택" id="option_add">
												<c:forEach items="${addOptnList}" var="optnAdd">
													<option ${optnAdd.optnSelected == 'true' ? 'selected' : ''} value="${optnAdd.prdtOptnNo}"   data-qty="${optnAdd.stockTotQty}" data-optnAddAmt="${optnAdd.optnAddAmt}"><c:if test="${optnAdd.stockTotQty <= 0}">[품절]</c:if>${optnAdd.addOptn2Text}	</option>
												</c:forEach>
											</select>
										</li>
									</c:otherwise>
								</c:choose>
								
								
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : tbl-desc-wrap -->
			<!-- 
			<div class="tbl-desc-wrap">
				<div class="fl">
					<a href="#" class="btn-normal btn-del">삭제</a>
				</div>
			</div>
			 -->
			<!-- E : tbl-desc-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link" id="optChangeSave">확인</a>
			</div>
			<!-- E : window-btn-group -->

			<%-- 
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">관리자 메모</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : manager-msg-wrap -->
			<form id="memoForm" name="memoForm" method="post" onsubmit="return false;">
				<input type="hidden" name="orderNo" value="${orderNo}">
				<input type="hidden" name="orderMemoSeq" >
				<input type="hidden" name="orderMemoGbnCode" value="10001">
				<div class="manager-msg-wrap">
					<div class="msg-box">
						<textarea title="관리자 메모 입력" name="memoText"></textarea>
					</div>
					<button type="button" id="btnMemoRegist" class="btn-normal btn-save">저장</button>
				</div>
			</form>
			<!-- E : manager-msg-wrap -->

			<!-- S : msg-list-wrap -->
			<div class="msg-list-wrap">
				<ul class="msg-list" id="memoList">
				</ul>
			</div>
			<!-- E : msg-list-wrap -->
			--%>
		</div>
	</div>
</body>
<script src="/static/common/js/biz/order/abcmart.order.optionchange.js<%=_DP_REV%>"></script>
