<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
$(function() {
	// 결과 목록 그리드 init
	abc.biz.system.admin.admin.initPopAdminList();

	$("#resetBtn").off().on('click', function() {
		$("#frmSearch")[0].reset();
	});

	// 검색 버튼
	$("#searchBtn").off().on('click', function() {
		abc.biz.system.admin.admin.popUpAdminSearch();
	});

	/* $("#pageCount").off().on('change', function() {
		abc.biz.system.admin.admin.popUpAdminSearch();
	}); */
	
	$("#setAdminInfo").click(function(){
		var param = abc.param.getParams();
		var chkRow = adminSheet.FindCheckedRow("popChk");
		var data = {adminNo : adminSheet.GetCellValue(chkRow, "adminNo")
				  , adminName : adminSheet.GetCellValue(chkRow,"adminName")
				  , loginId : adminSheet.GetCellValue(chkRow,"loginId")};

		var cb = param.callbackFunction;
		var opFunc = null;

		if(typeof(opener.window[cb])!="undefined"){
			opFunc = opener.window[cb];	
		}

		opFunc(data);
		window.close();
	});
});

<%-- 그리드 Click 이벤트 --%>
function adminSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	//그리드 내 컬럼 클릭 시 이벤트 발생
	if ( Row != 0) {
		if (adminSheet.ColSaveName(Col) == "popChk") {
			adminSheet.CheckAll("popChk", 0);
			adminSheet.SetCellValue(Row, Col, 1);
			
		}
	}
}


</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>관리자 찾기</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">관리자검색</h2>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : search-wrap -->
			<form name="frmSearch" id="frmSearch">
			<input type="hidden" id="pageCount" value="1">
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>관리자검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">사이트 구분</th>
								<td class="input">
									<ul class="ip-box-list">
									<c:forEach var="siteInfo" items="${siteInfo}" varStatus="status">
										<li>
											<span class="ui-chk">
												<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
												<input id="siteNo${status.count}" name="siteNos" class="ip_chk" type="checkbox" value="${siteInfo.siteNo}" checked>
												<label for="siteNo${status.count}"><c:out value="${siteInfo.siteName}"/></label>
											</span>
										</li>
									</c:forEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th scope="row">관리자명</th>
								<td class="input">
									<input id="adminName" name="adminName" type="text" class="ui-input" title="관리자명 입력">
								</td>
							</tr>
							<tr>
								<th scope="row">관리자ID</th>
								<td class="input">
									<input id="loginId" name="loginId" type="text" class="ui-input" title="관리자ID 입력">
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<a class="btn-sm btn-func" id="resetBtn"><i class="ico ico-refresh"></i>초기화</a>
						</div>
						<div class="fr">
							<a class="btn-normal btn-func" id="searchBtn">검색</a>
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
					<h3 class="content-title">관리자 목록</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : tbl-controller -->
			<!-- <div class="tbl-controller">
				<div class="fl">
					<span class="opt-group">
						<label class="title" for="selView03">목록개수</label>
						<select id="pageCount" name="pageCount" class="ui-sel" id="selView03">
							<option value="15">15개 보기</option>
							<option value="30">30개 보기</option>
						</select>
					</span>
				</div>
			</div> -->
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="adminGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
			
			<div class="content-bottom">
				<a href="javascript:void(0);" id="setAdminInfo" class="btn-normal btn-link">등록</a>
			</div>
		
		</div>
		
		
	</div>
</body>
<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>
</html>