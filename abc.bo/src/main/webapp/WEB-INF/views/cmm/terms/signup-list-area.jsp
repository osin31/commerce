<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- S : tbl-row -->
<table class="tbl-row" id="signUpListArea_<c:out value='${signUpListIndex}'/>">
	<input type="hidden" id="termsDtlSeq_<c:out value='${signUpListIndex}'/>" value="<c:out value='${signUpListIndex}'/>" >
	<input type="hidden" id="reqAgreeYnValue_<c:out value='${signUpListIndex}'/>" value="<c:out value='${cmTermsDetail.getReqAgreeYn()}'/>" >
	<caption>회원가입 동의 항목</caption>
	<colgroup>
		<col style="width: 130px;">
		<col>
		<col style="width: 130px;">
		<col>
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">항목명</th>
			<td class="input" colspan="3">
				<input type="text" id="temrsName_<c:out value='${signUpListIndex}'/>" name="termsName_<c:out value='${signUpListIndex}'/>" value="<c:out value='${cmTermsDetail.getTermsName()}'/>" maxlength="50" class="ui-input" title="항목명 입력">
			</td>
		</tr>
		<tr>
			<th scope="row">내용</th>
			<td class="input" colspan="3">
				<!-- S : editor-wrap -->
				<div class="editor-wrap">
					<textarea rows="10" cols="80" id="termsInfo_<c:out value='${signUpListIndex}'/>" name="termsInfo_<c:out value='${signUpListIndex}'/>" maxlength="4000" class="w100"><c:out value="${cmTermsDetail.getTermsInfo()}"/></textarea>
				</div>
				<!-- E : editor-wrap -->
			</td>
		</tr>
		<tr>
			<th scope="row">필수여부</th>
			<td class="input" colspan="2">
				<!-- S : ip-box-list -->
				<ul class="ip-box-list">
					<li>
						<span class="ui-rdo">
							<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
							<input id="reqAgree_Y_<c:out value='${signUpListIndex}'/>" name="reqAgreeYn_<c:out value='${signUpListIndex}'/>" type="radio" value="Y" checked="checked">
							<label for="reqAgree_Y_<c:out value='${signUpListIndex}'/>">필수</label>
						</span>
					</li>
					<li>
						<span class="ui-rdo">
							<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
							<input id="reqAgree_N_<c:out value='${signUpListIndex}'/>" name="reqAgreeYn_<c:out value='${signUpListIndex}'/>" value="N" type="radio">
							<label for="reqAgree_N_<c:out value='${signUpListIndex}'/>">선택</label>
						</span>
					</li>
				</ul>
				<!-- E : ip-box-list -->
			</td>
		</tr>
	</tbody>
</table>
<!-- E : tbl-row -->

<!-- S : tbl-desc-wrap -->
<div class="tbl-desc-wrap" id="btnArea_<c:out value='${signUpListIndex}'/>">
	<div class="fr">
		<!-- S : 20190114 수정 //버튼 가이드 변경으로 버튼 수정 -->
		<!-- DESC : default 추가 버튼 노출, 회원가입 동의 영역 추가시 삭제버튼 노출 -->
		<a href="javascript:void(0)" id="btnTableAdd_<c:out value='${signUpListIndex}'/>" value="<c:out value='${signUpListIndex}'/>" class="btn-func btn-normal">추가</a>
		<a href="javascript:void(0)" id="btnTableDelete_<c:out value='${signUpListIndex}'/>" value="<c:out value='${signUpListIndex}'/>" class="btn-del btn-normal">삭제</a>
		<!-- E : 20190114 수정 -->
	</div>
</div>
<!-- E : tbl-desc-wrap -->

