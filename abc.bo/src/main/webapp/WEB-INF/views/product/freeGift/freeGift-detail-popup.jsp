<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>사은품 검색</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">사은품 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<%@include file="freeGift-list-search.jsp" %>

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="page-count">목록개수</label>
						<select class="ui-sel" id="page-count">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="freeGift-list">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-save" id="save-for-promotion">등록</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
<script type="text/javascript" src="/static/common/js/biz/product/abcmart.product.freeGift.js<%=_DP_REV%>"></script>
<script type="text/javascript">
$(function() {
	list.SetColHidden('updateCheck',0);
	list.SetColFontUnderline('prdtName',0);
	list_OnClick = function(e) {};
	
	var isMultiple = abc.param.getParams().isMultiple; // 다중선택여부
	// 전체 선택 체크박스 제어
	list_OnBeforeCheckAll = function(row, col) {
		if(!isMultiple) {
			return false;
		}
	}
	
	// 체크박스 선택 제어
	list_OnBeforeCheck = function(row, col) {
		if(!isMultiple && list.ColSaveName(col) == 'updateCheck') {
			// 체크박스 모두 해제
			var checkedRows = list.FindCheckedRow("updateCheck").split('|');
			for(var i in checkedRows) {
				var currentRow = checkedRows[i];
				list.SetRowData(currentRow, { updateCheck : 0 });	
			}
		}
	}
});
</script>
<%@include file="/WEB-INF/views/common/footer.jsp" %>