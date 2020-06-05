<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		// 예외수수료 적용이력 조회초기화
		abc.biz.vendor.info.commission.history.initHistorySheet();
		
		$("#closeDiv").click(function(){
			window.close();
		});
	});
	
	<%-- DataSearch(Row) End 이벤트--%>
	function historySheet_OnRowSearchEnd(row){

		if(historySheet.GetCellValue(row,"commissionGubun") == "D" ){
			historySheet.SetCellValue(row, "commissionGubunText", "삭제");
		}else{
			historySheet.SetCellValue(row, "commissionGubunText", "예외");
		}
	}
</script>

<body class="window-body">
<form id="historyForm" name="historyForm">
	<input type="hidden" name="vndrNo" value="${vndrNo}">
</form>
	<div class="window-wrap">
		<div class="window-title">
			<h1>예외수수료 적용이력 조회</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">예외 수수료 적용 이력 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="historyGrid"></div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" id="closeDiv" class="btn-normal btn-link">닫기</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/vendor/abcmart.vendor.info.commission.history.js<%=_DP_REV%>"></script>
</html>