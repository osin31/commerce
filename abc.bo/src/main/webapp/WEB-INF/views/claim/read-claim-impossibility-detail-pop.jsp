<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<% pageContext.setAttribute("enter","\r\n"); %>

<script type="text/javascript">
	
	$(function() {
		
		//var dlvyTodoYmd = "${ocOrderDeliveryHistory.dlvyTodoYmd}";
		//$("#dlvyTodoYmd").text(dlvyTodoYmd.substring(0,4) + "." + dlvyTodoYmd.substring(4,6) + "." + dlvyTodoYmd.substring(6,8));
	
		var dlvyMemoTextLength = $("#dlvyMemoText").val().length;
		$("#dlvyMemoTextLength").text(dlvyMemoTextLength);
		
		$("#btnClose").click(function(){
			window.close();
		});
	});
	
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>클레임 불가 처리</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">클레임 불가 상품 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>클레임 불가 상품 정보</caption>
				<colgroup>
					<col>
					<col>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">업체상품코드</th>
						<th scope="col">상품이미지</th>
						<th scope="col">상품명</th>
						<th scope="col">상품코드</th>
						<th scope="col">옵션</th>
						<th scope="col">판매가</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-center"><c:out value="${ocClaimProduct.vndrPrdtNoText}"/></td>
						<c:choose>
							<c:when test="${empty ocClaimProduct.imageUrl}">
								<td class="input text-center clear-float">
									<img src="../../static/images/temp/temp_prod_img.jpg" alt="임시이미지">
								</td>
							</c:when>
							<c:otherwise>
								<!-- 상품 관련 파일이 존재할 경우 -->
								<td class="input text-center clear-float">
									<a src="#"><img src="${ocClaimProduct.imageUrl}" alt="${ocClaimProduct.altrnText}" style="width:100px; height:100px;"></a>
								</td>
							</c:otherwise>
						</c:choose>
						<td class="text-center"><c:out value="${ocClaimProduct.prdtName}"/></td>
						<td class="text-center"><c:out value="${ocClaimProduct.prdtNo}"/></td>
						<td class="text-center"><c:out value="${ocClaimProduct.optnName}"/></td>
						<td class="text-center"><fmt:formatNumber value="${ocClaimProduct.sellAmt}" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"/>&nbsp;원</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-col -->

			<!-- S : 클레임불가 처리 후 조회 팝업 -->
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>클레임 불가 상품 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">클레임 불가 사유</th>
						<td colspan="3"><c:out value="${ocClaimProduct.clmImpsbltRsnText}"/></td>
					</tr>
					<tr>
						<th scope="row">요청 내용</th>
						<td colspan="3">
						${fn:replace(ocClaimProduct.clmImpsbltContText, enter, "<br/>")}
						</td>
					</tr>
					<tr>
						<th scope="row">운송장정보 </th>
						<td colspan="3"><c:out value="${ocClaimProduct.logisVndrCodeName}"/> / <a href="#" class="link"><c:out value="${ocClaimProduct.waybilNoText}"/></a></td>
					</tr>
					<tr>
						<th scope="row">발송예정일</th>
						<td colspan="3" id="dlvyTodoYmd">
							<fmt:formatDate value="${ocOrderDeliveryHistory.dlvyTodoYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/>
						</td>
					</tr>
					<tr>
						<th scope="row">요청일시</th>
						<td><fmt:formatDate value="${ocOrderDeliveryHistory.rgstDtm}" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/></td>
						<th scope="row">요청자</th>
						<td>
							<c:out value="${ocOrderDeliveryHistory.loginId}"/>(${ocOrderDeliveryHistory.adminName})
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">반송지 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>반송지 정보</caption>
				<colgroup>
					<col style="width: 130px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">
							<span class="th-required">수령자명</span>
						</th>
						<td><c:out value="${ocOrderDeliveryHistory.rcvrName}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">휴대폰번호</span>
						</th>
						<td><c:out value="${ocOrderDeliveryHistory.rcvrHdphnNoText}"/></td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">주소</span>
						</th>
						<td>
							[<c:out value="${fn:trim(ocOrderDeliveryHistory.rcvrPostCodeText)}"/>]<br><br>
							<c:out value="${ocOrderDeliveryHistory.rcvrPostAddrText}"/>&nbsp;<c:out value="${ocOrderDeliveryHistory.rcvrDtlAddrText}"/></td>
					</tr>
					<tr>
						<th scope="row">배송 시 요청사항</th>
						<td class="input">
							<!-- S : msg-wrap -->
							<span class="msg-wrap">
								<span class="msg-box ip-size-lg">
									<textarea class="ui-textarea" id="dlvyMemoText" title="배송 메세지 입력" readonly><c:out value="${ocOrderDeliveryHistory.dlvyMemoText}"/></textarea>
									<span class="text-limit">
										<span class="desc">(<span id="dlvyMemoTextLength">0</span>/40 자)</span>
									</span>
								</span>
							</span>
							<!-- E : msg-wrap -->
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->
			<!-- E : 클레임불가 처리 후 조회 팝업 -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link" id="btnClose">확인</a>
			</div>
			<!-- E : window-btn-group -->

		</div>
	</div>
</body>