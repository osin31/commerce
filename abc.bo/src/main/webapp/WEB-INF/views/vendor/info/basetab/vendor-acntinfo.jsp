<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">거래 계좌 정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>거래 계좌 정보</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">
					<span class="th-required">금융기관</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : 20190130 수정 // 기획변경으로 수정 -->
					<select class="ui-sel" id="bankCode" name="bankCode" title="금융기관 선택">
						<option value="">선택</option>
						<c:forEach var="bankCode" items="${codeList.BANK_CODE}" varStatus="status">
							<option value="${bankCode.codeDtlNo}" <c:if test="${vendorInfo.bankCode eq bankCode.codeDtlNo}">selected</c:if> >${bankCode.codeDtlName}</option>
						</c:forEach>
					</select>
					<!-- E : 20190130 수정 // 기획변경으로 수정 -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">계좌번호</span>
				</th>
				<td class="input">
					<input type="text" id="acntNoText" name="acntNoText" class="ui-input" title="계좌번호 입력" maxlength="30" value='<c:out value="${vendorInfo.acntNoText}"/>'>
				</td>
				<th scope="row">
					<span class="th-required">예금주 </span>
				</th>
				<td class="input">
					<input type="text" id="acntHldrName" name="acntHldrName" class="ui-input" title="예금주명 입력" maxlength="50" value='<c:out value="${vendorInfo.acntHldrName}"/>'>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">전자세금계산서<br />발송용 이메일</span>
				</th>
				<td class="input" colspan="3">
					<input type="text" id="billIssueEmailAddrText" name="billIssueEmailAddrText" class="ui-input" title="이메일 입력" maxlength="100" value='<c:out value="${vendorInfo.billIssueEmailAddrText}"/>'>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
