<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%
	String benefitIndex = (String)request.getAttribute("benefitIndex");
%>
<%--
	온라인 회원 혜택 등록/상세 페이지에서 혜택 영역의 공통 화면이다. ajax로 로딩된다.
--%>
<!-- S : tbl-row -->
<table class="tbl-row" id="DeleteBenefitArea_<%=benefitIndex%>">
	<caption>혜택 관리 설정</caption>
	<colgroup>
		<col style="width: 160px;">
		<col>
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">
				<span>조건</span>
			</th>
			<td class="input">
				<!-- S : 20190308 수정 // 조건없음 삭제 -->
				<!-- S : ip-text-box -->
				<span class="ip-text-box">
					<span class="text">조건 설정</span>
					<input type="text" class="ui-input text-right num-unit10000000" title="최소 금액 입력" id="buyAmt_<%=benefitIndex%>" name="buyAmt_<%=benefitIndex%>" maxlength="8" value="<c:out value="${BENEFIT_DATA.buyAmt}"/>">
					<span class="text">원 이상 &amp;</span>
					<input type="text" class="ui-input text-right num-unit10000000" title="최소 구매건수 입력" id="buyQty_<%=benefitIndex%>" name="buyQty_<%=benefitIndex%>" maxlength="8" value="<c:out value="${BENEFIT_DATA.buyQty}"/>">
					<span class="text">건 이상 구매</span>
				</span>
				<!-- E : ip-text-box -->
				<!-- E : 20190308 수정 // 조건없음 삭제 -->
			</td>
		</tr>
		<tr>
			<th scope="row">
				<span>혜택</span>
			</th>
			<td class="input">
				<div class="tbl-header">
					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<button type="button" class="btn-sm btn-link" id="benefit_cpn_search_<%=benefitIndex%>" value="<%=benefitIndex%>">검색</button>
					<!-- E : 20190114 수정 -->
	
					<!-- DESC : 20190308 수정 // 쿠폰 개수 제한 수정 -->
					<span class="text">* 최소 1개이상, 최대 5개까지 설정 가능</span>
				</div>
				<table class="tbl-col">
					<caption>멤버십 회원 혜택</caption>
					<colgroup>
						<col style="width:25%">
						<col style="width:auto">
						<col style="width:20%">
						<col style="width:10%">
						<col style="width:10%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">쿠폰코드</th>
							<th scope="col">쿠폰명</th>
							<th scope="col">쿠폰유효기간</th>
							<th scope="col">수량</th>
							<th scope="col">삭제</th>
						</tr>

					</thead>
					<tbody id="cpn_tbody_<%=benefitIndex%>">
						<tr id="cpn_nodata_<%=benefitIndex%>" style="display:none;">
							<td colspan="5" class="text-center">등록된 데이터가 없습니다.</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</tbody>
</table>
<!-- E : tbl-row -->

<!-- S : tbl-desc-wrap -->
<div class="tbl-desc-wrap" id="DeleteBtnArea_<%=benefitIndex%>">
	<div class="fr">
		<button class="btn-normal btn-func" id="btnAdd_<c:out value="${benefitIndex}"/>" value="<c:out value="${benefitIndex}"/>">추가</button>
		<button class="btn-normal btn-del" id="btnDel_<c:out value="${benefitIndex}"/>" value="<c:out value="${benefitIndex}"/>">삭제</button>
	</div>
</div>
<!-- E : tbl-desc-wrap -->
