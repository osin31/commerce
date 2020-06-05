<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function(){
		var isChecBankAccount = false;
		
		var regDigit = /^[0-9]/gi;
		
		$('#pymntOrgan').change(function(obj){
			$('input[name=pymntOrganName]').val($('#pymntOrgan option:selected').text());
			$('input[name=pymntOrganCodeText]').val($('#pymntOrgan').val());
		});
		
		$('#pymntOrganNoText').off().on('keyup', function() {
			isChecBankAccount = false;
			abc.text.validateOnlyNumber(this);
		});
		
		$('#pymntOrganNoText').focusout(function() {
			abc.text.validateOnlyNumber(this);
		});
		
		$('#authBankAccount').click(function(){		
			console.log('authBankAccount click');
			isChecBankAccount = checkBankAccount();
		});
		
		$('#save').click(function(){
			console.log('${refundInfo.procYn}' == 'Y')
			if('${refundInfo.procYn}' == 'Y'){
				self.close();
				return false;
			}
			
			if(checkValidation()){
				if(!isChecBankAccount){
					alert('계좌인증을 확인해주세요');
					return false;
				}
				
				var callback = getCallBack();
				callback($("#refundInfoForm").serialize());
				self.close();
			}
		});
		
		checkBankAccount = function(){
			if(checkValidation()){
				var params = {};
				params.acntHldrName = $('#acntHldrName').val();
				params.backCode = $("#pymntOrgan").val();
				params.pymntOrganNoText = $("#pymntOrganNoText").val();
				console.log(params);
				// TODO : 계좌인증 처리. 
				if(true){
					alert('인증이 완료되었습니다.');
					return true;
				} else {
					alert('계좌정보와 예금주명이 정확하지 않습니다.');
					return false;
				}
			}
			return false;
		}
		
		checkValidation = function(){
			
			if($('#acntHldrName').val() == null || $('#acntHldrName').val() == ''){
				alert('예금주명을 확인해주세요');
				return false;
			}
			
			if($("#pymntOrgan").val() == '') {
				alert('은행을 확인해주세요');
				return false;
			}
			console.log($("#pymntOrganNoText").val() == '' )
			console.log(regDigit.test($('#pymntOrganNoText').val()))
			if($("#pymntOrganNoText").val() == '' || regDigit.test($('#pymntOrganNoText').val())){
				alert('계좌번호를 확인해주세요');
				return false;
			}
						
			return true;
		} 
	});
	
	function getCallBack() {
		var callback = '${param.callback}';
		if(callback != null){
			callback = "window.opener." + callback;
			return  new Function("return " + callback)();
		}
		return null;
	}
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>환불계좌 정보</h1>
		</div>
		<div class="window-content">
			<!-- S : tbl-row -->
			<c:if test="${refundInfo.procYn == 'N'}">
				<form id="refundInfoForm" name="refundInfoForm" method="post" onsubmit="return false;">
					<input type="hidden" name="orderNo" value="${refundInfo.orderNo}">
					<input type="hidden" name="orderPymntSeq" value="${refundInfo.orderPymntSeq}">
					<input type="hidden" name="pymntOrganName" value="${refundInfo.pymntOrganName}">
					<input type="hidden" name="pymntOrganCodeText" value="${refundInfo.pymntOrganCodeText}">				
					<table class="tbl-row">
						<caption>환불계좌 정보</caption>
						<colgroup>
							<col style="width:130px">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><span class="th-required">예금주명</span></th>
								<td class="input">
									<input type="text" class="ui-input" id="acntHldrName" name="acntHldrName" title="예금주명 입력" value="${refundInfo.acntHldrName}">
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="th-required">은행</span></th>
								<td class="input">
									<select class="ui-sel" id="pymntOrgan" title="은행 선택" value="${refundInfo.pymntOrganCodeText}">
										<c:if test="${!empty codeList.BANK_CODE}">
											<option name="" value="">선택</option>
											<c:forEach items="${codeList.BANK_CODE}" var="bankCode">
												<option value="${bankCode.codeDtlNo}" <c:if test="${refundInfo.pymntOrganCodeText ==  bankCode.codeDtlNo }">selected</c:if> >${bankCode.codeDtlName}</option>
											</c:forEach>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="th-required">계좌번호</span></th>
								<td class="input">
									<!-- S : refund-account-box -->
									<span class="refund-account-box">
										<span class="refund-input">
											<input type="text" class="ui-input account-number" id="pymntOrganNoText" name="pymntOrganNoText" placeholder="계좌번호 (-)없이 입력" title="계좌번호 입력" value="${refundInfo.pymntOrganNoText}">
											<button type="button" class="btn-sm btn-func" id="authBankAccount">계좌인증</button>
										</span>
									</span>
									<!-- E : refund-account-box -->
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</c:if>
			<c:if test="${refundInfo.procYn == 'Y'}">
				<table class="tbl-row">
					<caption>환불계좌 정보</caption>
					<colgroup>
						<col style="width:130px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><span class="text">예금주명</span></th>
							<td class="input"><span class="text">${refundInfo.acntHldrName}</span></td>
						</tr>
						<tr>
							<th scope="row"><span class="text">은행</span></th>
							<td class="input"><span class="text">${refundInfo.pymntOrganName}</span></td>
						</tr>
						<tr>
							<th scope="row"><span class="text">계좌번호</span></th>
							<td class="input"><span class="text">${refundInfo.pymntOrganNoText}</span></td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<!-- E : tbl-row -->
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link" id="save">확인</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
</html>