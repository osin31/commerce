<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		$("#managerIndex").val($("[name=vendorMangerContentDiv]").length);
	});
</script>

	<!-- S : content-header -->
<c:forEach var="manager" items="${managerList}" varStatus="status">
<div id="vendorMangerContentDiv_${status.index}" name="vendorMangerContentDiv" class="content-header">
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 담당자 정보</h3>
		</div>
	</div>

	<input type="hidden" name="managerList.managetStatus" value="U">
	<input type="hidden" id="vndrMngrIdSuccess_${status.index}" value="Y">
	<input type="hidden" id="managerDelYn_${status.index}" name="managerList.managerDelYn" value="N">
	<input type="hidden" name="managerList.vndrMngrNo" value="${manager.vndrMngrNo}">
	<input type="hidden" name="managerList.vndrMngrAdminNo" value="${manager.vndrMngrAdminNo}">

	<!-- E : content-header -->
	<!-- S : tbl-row -->
	<table class="tbl-row">
		<caption>입점사 추가담당자 정보</caption>
		<colgroup>
			<col style="width: 150px">
			<col>
			<col style="width: 150px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">담당업무</th>
				<td class="input">
					<input type="text" id="workTypeText_${status.index}" name="managerList.workTypeText" class="ui-input" maxlength="50" title="담당업무 입력"  value='<c:out value="${manager.workTypeText}"/>'>
				</td>
				<th scope="row">
					<span class="th-required" name="representative"><c:if test="${status.index eq 0 }">대표 </c:if>담당자명</span>
				</th>
				<td class="input">
					<input type="text" id="vndrMngrName_${status.index}" name="managerList.vndrMngrName" class="ui-input" maxlength="50" title="담당자명 입력" value='<c:out value="${manager.vndrMngrName}"/>'>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required" name="representative">담당자 ID</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" id="vndrMngrId_${status.index}" name="managerList.vndrMngrId" class="ui-input" readonly title="담당자 ID 입력" value='<c:out value="${manager.vndrMngrId}"/>'>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required" name="representative"><c:if test="${status.index eq 0 }">대표 </c:if>담당자 연락처 1</span>
				</th>
				<td class="input">
					<input type="text" id="hdphnNoText_${status.index}" name="managerList.hdphnNoText" class="ui-input" title="담당자 연락처 입력" value='<c:out value="${manager.hdphnNoText}"/>'>
				</td>
				<th scope="row">
					<span name="representative"><c:if test="${status.index eq 0 }">대표 </c:if>담당자 연락처 2</span>
				</th>
				<td class="input">
					<input type="text" id="telNoText_${status.index}" name="managerList.telNoText" class="ui-input" title="담당자 연락처 입력" value='<c:out value="${manager.telNoText}"/>'>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required" name="representative"><c:if test="${status.index eq 0 }">대표 </c:if>담당자 이메일</span>
				</th>
				<td class="input" colspan="3">
					<input type="text" id="emailAddrText_${status.index}" name="managerList.emailAddrText" class="ui-input" title="담당자 이메일 입력" value='<c:out value="${manager.emailAddrText}"/>'>
				</td>
			</tr>
			<!-- DESC : 20190130 삭제 // 기획변경으로 사용여부 row 삭제 -->
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : tbl-desc-wrap -->
	<div class="tbl-desc-wrap">
		<div class="fr">
			<a href="javascript:void(0);" name="vendorManagerRemoveBtn" id="vendorManagerRemoveBtn_${status.index}" class="btn-normal btn-del">담당자 삭제</a>
			<a href="javascript:void(0);" name="vendorManagerAddBtn" id="vendorManagerAddBtn_${status.index}" class="btn-normal btn-func">담당자 추가</a>
		</div>
	</div>
	<!-- E : tbl-desc-wrap -->
</div>
</c:forEach>
