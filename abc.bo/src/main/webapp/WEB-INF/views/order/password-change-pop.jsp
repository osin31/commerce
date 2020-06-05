<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	
</script>

<body class="window-body">
<form id="orderForm" name="orderForm" method="post" onsubmit="return false;">
<input type="hidden" id="orderNo" name="orderNo" value='<c:out value="${orderDtail.orderNo}"/>'>
<input type="hidden" id="OldPswdText" name="OldPswdText" value='<c:out value="${orderDtail.nmbrCrtfcNoText}"/>'>
	<div class="window-wrap">
		<div class="window-title">
			<h1>${orderDtail.buyerName}님 주문 비밀번호 변경</h1>
		</div>
		<div class="window-content">

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">주문자 정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>주문자 정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">주문번호</th>
						<td>${orderDtail.orderNo}</td>
					</tr>
					<tr>
						<th scope="row">이메일</th>
						<td>${orderDtail.buyerEmailAddrText}</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">비밀번호 변경</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>비밀번호 변경</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">비밀번호 입력</th>
						<td class="input">
							<input type="password" class="ui-input" title="비밀번호 입력" id="pswdText" name="pswdText" placeholder="6자리입력" maxlength="6">
						</td>
					</tr>
					<tr>
						<th scope="row">비밀번호 재입력</th>
						<td class="input">
							<input type="password" class="ui-input" title="비밀번호 입력" id="pswdTextConfirm" name="pswdTextConfirm" placeholder="6자리입력" maxlength="6">
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-link" id="passwordSave">확인</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</form>	
</body>

<script src="/static/common/js/biz/order/abcmart.order.nonCustPassword.js<%=_DP_REV%>"></script>
