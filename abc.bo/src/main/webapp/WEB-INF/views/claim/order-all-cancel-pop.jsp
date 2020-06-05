<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">

	$(function() {
		var processIng = false;
		
		<%-- 취소유형 변경 시 기타 사유 활성화 --%>
		$("#clmRsnCode").change(function() {
			if($("#clmRsnCode option:selected").val() == "${CommonCode.CLM_RSN_CODE_CANCEL_ETC}") { // 기타 사유
				$("#clmEtcRsnText").prop("disabled", false);
			} else {
				$("#clmEtcRsnText").prop("disabled", true);
				$("#clmEtcRsnText").val("");
			}
	    });
		
		<%-- 주문 취소 --%>
		$("#orderCancelBtn").click(function(e) {
			e.preventDefault();
			
			if(processIng) {
				alert('처리중입니다.');
				return false;
			}
			
			if( abc.text.isBlank($("#clmRsnCode").val()) ) {
				alert("취소유형을 선택하세요.");
				return false;
			}
			if($("#clmRsnCode option:selected").val() == "${CommonCode.CLM_RSN_CODE_CANCEL_ETC}") { // 기타 사유
				if( abc.text.isBlank($("#claimProductForm input[name=clmEtcRsnText]").val()) ) {
					alert("취소 사유를 입력하세요.");
					$("#claimProductForm input[name=clmEtcRsnText]").focus();
					return false;
				}
			}
			if(abc.text.isLimitLength($("#claimProductForm input[name=clmEtcRsnText]").val(), 100)){
				alert("취소 사유가 최대 길이(100-영문1,한글2)를 초과하였습니다.");
				$("#claimProductForm input[name=clmEtcRsnText]").focus();
				return false;
			}
			
			if(confirm("주문취소를 하시겠습니까?")) {
				processIng = true;
				
				var request	= 
							$.ajax({
								type :"post",
								url : "/claim/claim/order-all-cancel",
								data : $("#claimProductForm").serialize(),
							})
							request.done(function(data){
								if (data.success == "Y"){
									alert("주문취소가 처리되었습니다.");
									
									if(window.opener != null) {
										/*
										if (typeof(window.opener.tabPanelLoad) == "function" || typeof(window.opener.tabPanelLoad) == "object") {
											window.opener.tabPanelLoad("tabOrderArea");
							            }
										*/
										opener.location.reload();
									}
								} else {
									alert(data.message);
								}
								
								window.close();
							})
							request.fail(function(jqXHR, textStatus, errorThrown){
								if(jqXHR.responseJSON.message != undefined) {
									alert(jqXHR.responseJSON.message);
								} else {
									alert("주문취소 처리에 실패했습니다.");
								}
								
								window.close();
							});
			}
		});
	});
	
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>취소유형 선택</h1>
		</div>
		<div class="window-content">
			<form id="claimProductForm" name="claimProductForm" method="post">
			<input type="hidden" name="orderNo" value="${claimInfo.orderNo}"/>
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>취소유형 선택</caption>
				<colgroup>
					<col style="width:130px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><span class="th-required">취소유형</span></th>
						<td class="input">
							<select class="ui-sel" title="은행 선택" id="clmRsnCode" name="clmRsnCode">
								<option value="">취소유형 선택</option>
								<c:forEach var="code" items="${CLM_RSN_CODE}" varStatus="status">
									<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">취소사유</th>
						<td class="input">
							<input id="clmEtcRsnText" name="clmEtcRsnText" type="text" class="ui-input" title="기타 사유 입력" placeholder="내용" disabled="disabled">
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			</form>
			
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link" id="orderCancelBtn">주문취소</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
