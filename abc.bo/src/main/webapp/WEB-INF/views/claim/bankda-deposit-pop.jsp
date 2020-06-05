<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		$("#save").click(function(){
			var callback = getCallBack();
			callback($("#depositForm").serialize());
			self.close();
		})
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
			<h1>입금정보 등록</h1>
		</div>
		<div class="window-content">
			<form id="depositForm" method="post" onsubmit="return false;">
				<input type="hidden" name=bkcode value="${bankdaInfo.bkcode}">
				<input type="hidden" name=depositconfirm value="${bankdaInfo.depositconfirm}">
				<input type="hidden" name=depositdtm value="${bankdaInfo.depositdtm}">
				<table class="tbl-row">
					<caption>현재 입금정보/입금구분</caption>
					<colgroup>
						<col style="width: 130px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">
								<span class="">입금정보</span>
							</th>
							<td class="input">
								<input type="text" name="depositinfo" value="${bankdaInfo.depositinfo}" class="ui-input" title="">
							</td>
						</tr>
						<tr>
							<th scope="row">
								<span class="">입금구분</span>
							</th>
							<td class="input">
								<input type="text" name="depositgubun" value="${bankdaInfo.depositgubun}" class="ui-input" title="">
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<div class="window-btn-group">
				<button id="save" class="btn-normal btn-link">저장</button>
				<button onclick="self.close();" class="btn-normal btn-link">닫기</button>
			</div>
		</div>
	</div>
</body>
