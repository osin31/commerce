<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		//입점사검색 영역 세팅
		abc.biz.vendor.pop.event();

		// 입점사 목록 그리드 초기화
		abc.biz.vendor.pop.initvendorInfoPopSheet();

		abc.biz.vendor.pop.doActionvendorInfoPop("search");
	});
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>입점업체 찾기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">입점사 검색</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<form id="vendorForm" name="vendorForm">
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>입점사 검색</caption>
						<colgroup>
							<col style="width:15%;">
							<col>
							<col style="width:79px;">
							<col style="width:15%;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">업체명</th>
								<td class="input">
									<input type="text" id="vndrName" name="vndrName" class="ui-input" title="업체명 입력">
								</td>
								<td></td>
								<th scope="row">입점사 ID</th>
								<td class="input">
									<input type="text" id="vndrNo" name="vndrNo" class="ui-input" title="입점사 ID 입력">
								</td>
							</tr>
							<tr>
								<th scope="row">업체 담당자명</th>
								<td class="input">
									<input type="text" id="vndrMngrName" name="vndrMngrName" class="ui-input" title="업체 담당자명 입력">
								</td>
								<td></td>
								<!-- <th scope="row">담당 MD 명</th>
								<td class="input">
									<input type="text" id="adminName" name="adminName" class="ui-input" title="담당 MD 명 입력">
								</td> -->
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a href="javascript:void(0);" id="vendorFormReset" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a href="javascript:void(0);" id="vendorInfoSerch" class="btn-normal btn-func">검색</a>
						</div>
					</div>
				</div>
				<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
			</div>
			</form>
			<!-- E : search-wrap -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">입점사 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selView03">목록개수</label>
						<select class="ui-sel" id="pageCount">
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
						</select>
					</span>
				</div>
			</div>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="vendorInfoPopGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
			<div class="window-btn-group">
				<a href="javascript:void(0)" class="btn-normal btn-save" id="vendorSelectBtn">선택</a>
			</div>
		</div>
	</div>
</body>
<script src="/static/common/js/biz/vendor/abcmart.vendor.pop.js<%=_DP_REV%>"></script>
</html>