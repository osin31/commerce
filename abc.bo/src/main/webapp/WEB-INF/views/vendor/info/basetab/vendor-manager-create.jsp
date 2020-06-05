<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<script type="text/javascript">
	$(function() {
		$("#managerIndex").val('<c:out value="${managerIndex}" />');
	});
</script>

	<!-- S : content-header -->
<div id="vendorMangerContentDiv_${managerIndex}" name="vendorMangerContentDiv">
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 담당자 정보</h3>
		</div>
	</div>
	
	<input type="hidden" name="managerList.managetStatus" value="C">
	<input type="hidden" id="vndrMngrIdSuccess_${managerIndex}" value="N">
	<input type="hidden" id="managerDelYn_${managerIndex}" name="managerList.managerDelYn" value="N">
	<input type="hidden" name="managerList.vndrMngrNo">
	<input type="hidden" name="managerList.vndrMngrAdminNo">

	
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
					<input type="text" id="workTypeText_${managerIndex}" name="managerList.workTypeText" maxlength="100" class="ui-input" title="담당업무 입력">
				</td>
				<th scope="row">
					<span class="th-required" name="representative"><c:if test="${managerIndex eq 1 }">대표 </c:if>담당자명</span>
				</th>
				<td class="input">
					<input type="text" id="vndrMngrName_${managerIndex}" name="managerList.vndrMngrName" maxlength="100" class="ui-input" title="담당자명 입력">
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required">담당자 ID</span>
				</th>
				<td class="input" colspan="3">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" id="vndrMngrId_${managerIndex}" name="managerList.vndrMngrId" class="ui-input" maxlength="30" title="담당자 ID 입력" placeholder="영어, 숫자만 입력 가능">
						<button type="button" id="vndrMngrIdDup_${managerIndex}" class="btn-sm btn-func">중복확인</button>
					</span>
					<!-- E : ip-text-box -->

					<!-- S : td-text-list -->
					<ul class="td-text-list">
						<li>* 영문(대소문자 구분) 및 숫자 3~20자까지 가능합니다. </li>
						<li>* 특수문자 및 공백은 사용 불가합니다.</li>
					</ul>
					<!-- E : td-text-list -->
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required" name="representative"><c:if test="${managerIndex eq 1 }">대표 </c:if>담당자 연락처 1</span>
				</th>
				<td class="input">
					<input type="text" id="hdphnNoText_${managerIndex}" maxlength="15" name="managerList.hdphnNoText" class="ui-input" title="담당자 연락처 입력">
				</td>
				<th scope="row">
					<span name="representative"><c:if test="${managerIndex eq 1 }">대표 </c:if>담당자 연락처 2</span>
				</th>
				<td class="input">
					<input type="text" id="telNoText_${managerIndex}" maxlength="15" name="managerList.telNoText" class="ui-input" title="담당자 연락처 입력">
				</td>
			</tr>
			<tr>
				<th scope="row">
					<span class="th-required" name="representative"><c:if test="${managerIndex eq 1 }">대표 </c:if>담당자 이메일</span>
				</th>
				<td class="input" colspan="3">
					<input type="text" id="emailAddrText_${managerIndex}" maxlength="200" name="managerList.emailAddrText" class="ui-input" title="담당자 이메일 입력">
				</td>
			</tr>
			<!-- DESC : 20190130 삭제 // 기획변경으로 사용여부 row 삭제 -->
		</tbody>
	</table>
	<!-- E : tbl-row -->

	<!-- S : tbl-desc-wrap -->
	<div class="tbl-desc-wrap">
		<div class="fr">
			<a href="javascript:void(0);" name="vendorManagerRemoveBtn" id="vendorManagerRemoveBtn_${managerIndex}" class="btn-normal btn-del">담당자 삭제</a>
			<a href="javascript:void(0);" name="vendorManagerAddBtn" id="vendorManagerAddBtn_${managerIndex}" class="btn-normal btn-func">담당자 추가</a>
		</div>
	</div>
	<!-- E : tbl-desc-wrap -->
</div>
