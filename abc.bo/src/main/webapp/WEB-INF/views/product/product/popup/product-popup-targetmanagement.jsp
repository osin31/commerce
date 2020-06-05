<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>대상상품관리</h1>
		</div>
		<div class="window-content">
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>대상상품관리</caption>
				<colgroup>
					<col style="width: 130px;"/>
					<col/>
					<col style="width: 130px;"/>
					<col/>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">상품 관리 항목</th>
						<td data-area="name"></td>
						<th scope="row">대상상품 수</th>
						<td data-area="count"></td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : 20190215 수정 // 탭별로 페이지 분기처리 -->
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">대상상품 목록</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="page-count">목록개수</label>
						<select class="ui-sel" id="page-count">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
					<!-- E : opt-group -->
					<a href="#" class="btn-sm btn-del" data-button="remove-rows" data-sheet-name="list">선택 삭제</a>
				</div>
				<div class="fr">
					<a href="#" class="btn-sm btn-func" data-button="add-products">상품추가</a>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="product-list">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-save" data-button="save">저장</a>
			</div>
			<!-- E : window-btn-group -->
			<!-- E : 20190215 수정 // 탭별로 페이지 분기처리 -->
		</div>
	</div>
	<script type="text/javascript">
		var codes = ${gridCodes };<%-- 판매상태 코드 --%>
	</script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.product.popup.targetmanagement.js<%=_DP_REV%>"></script>
</body>
</html>
