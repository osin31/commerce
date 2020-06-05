<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script>
$(function() {
	abc.biz.display.aconnect.product.codeCombo = ${codeCombo};	
});
</script>
	
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>상품상세정보</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">상품기본정보</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>상품기본정보</caption>
				<colgroup>
					<col style="width: 140px;">
					<col>
					<col style="width: 140px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">영문/국문상품명</th>
						<td>${pdProduct.engPrdtName} / ${pdProduct.prdtName}</td>
						<th scope="row">상품구분</th>
						<td>${stdCtgrName}</td>
					</tr>
					<tr>
						<th scope="row">상품코드</th>
						<td>${pdProduct.prdtNo}</td>
						<th scope="row">색상코드</th>
						<td>${pdProduct.prdtColorInfo}</td>
					</tr>
					<tr>
						<th scope="row">스타일코드</th>
						<td>${pdProduct.styleInfo}</td>
						<th scope="row">브랜드</th>
						<td>${pdProduct.brandNo}</td>
					</tr>
					<tr>
						<th scope="row">원산지</th>
						<td>
							<c:forEach items="${orgPlaceCodeList}" var="code">
								<c:if test="${pdProduct.orgPlaceCode eq code.codeDtlNo}">
									${code.codeDtlName}
								</c:if>
							</c:forEach>
						</td>
						<th scope="row">판매여부</th>
						<td>
							<c:forEach items="${sellStatCodeList}" var="code">
								<c:if test="${pdProduct.sellStatCode eq code.codeDtlNo}">
									${code.codeDtlName}
								</c:if>
							</c:forEach>							
						</td>
					</tr>
					<tr>
						<th scope="row">제조사</th>
						<td>${pdProduct.mnftrName}</td>
						<th scope="row">전시여부</th>
						<td>${pdProduct.dispYn eq 'Y' ? '전시' : '전시안함'}</td>
					</tr>
					<tr>
						<th scope="row">승인상태</th>
						<td>
							<c:forEach items="${aprvStatCodeList}" var="code">
								<c:if test="${pdProduct.aprvStatCode eq code.codeDtlNo}">
									${code.codeDtlName}
								</c:if>
							</c:forEach>							
						</td>
						<th scope="row">
							<span class="th-required">A-Connect전시여부</span>
						</th>
						<form id="historyForm" name="historyForm">
						<td class="input">
							<!-- S : ip-box-list -->
							<input type="hidden" name="prdtNo" value="${pdProduct.prdtNo}">
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input type="radio" name="aconnectDispYn" id="radioDisplay02" value="Y" ${pdProduct.aconnectDispYn eq 'Y' ? 'checked' : ''}>
										<label for="radioDisplay02">전시</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input type="radio" name="aconnectDispYn" id="radioDisplay03" value="N" ${pdProduct.aconnectDispYn eq 'N' ? 'checked' : ''}>
										<label for="radioDisplay03">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						</form>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">변경이력</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="pageCount">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->
			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div style="width:100%; height:429px;" id="historySheet"></div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-save" id="saveBtn">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.aconnect.product.detail.js<%=_DP_REV%>"></script>
</body>
</html>