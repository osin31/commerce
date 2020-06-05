<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>일괄반려</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">일괄반려</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>일괄반려</caption>
				<colgroup>
					<col style="width: 150px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">반려사유 입력</th>
						<td class="input">
							<textarea class="ui-textarea" title="반려사유 입력" id="returnRsnText"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="#" class="btn-normal btn-del" id="closeBtn">취소</a>
				<a href="#" class="btn-normal btn-func" id="returnBtn">승인반려</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	
	<script type="text/javascript" src="/static/common/js/biz/promotion/abcmart.promotion.planning.display.feedback.js<%=_DP_REV%>"></script>
</body>

</html>