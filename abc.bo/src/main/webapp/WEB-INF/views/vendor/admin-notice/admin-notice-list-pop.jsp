<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(function() {
		<%-- 관리자 공지 목록 팝업 초기 세팅. --%>
		abc.biz.vendor.adminnotice.initAdminNoticePopSheet();

		<%-- 관리자 공지 목록 화면 호출 시 첫 검색, 대시보드에서 이동시 대시보드 URL 타도록 수정(검색, 상세팝업) --%>
		abc.biz.vendor.adminnotice.adminnoticePopDoAction("search", '<c:out value="${fromDash}" />');

		<%-- 검색 : 검색어 선택박스 --%>
		$("#searchSelectBox").change(function(){
			//검색어 선택박스 선택 시 히든값 초기화
			$("#notcTitleText").val('');
			$("#loginId").val('');
			$("#adminName").val('');
			//검색어 선택박스 선택 시 검색어 인풋 박스 초기화
			$("#searchInputBox").val('');
			
		});
		
		<%-- 검색 조건 초기화 --%>
		$("#btnSearchReset").click(function(){
			$('#frmSearch').each(function(){
				this.reset();
			});
			
			$("#notcTitleText").val('');
			$("#adminName").val('');
			$("#loginId").val('');
			$("#searchInputBox").val('');
		});
	});
	
	<%-- 그리드 Click 이벤트 --%>
	function adminnoticePopSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if ( Row != 0) {
			if ( adminnoticePopSheet.ColSaveName(Col) == "notcTitleText" && Value != "" ) {
				
				var url = "/vendor/admin-notice/read-detail-pop";
				var fromDash = '<c:out value="${fromDash}" />';
				
				if(fromDash === "BO") {
					url = "/bo/dashboard/read-detail-pop";
				}else if(fromDash === "PO") {
					url = "/po/dashboard/read-detail-pop";
				}
				
				var params = {};
				params.adminNotcSeq = adminnoticePopSheet.GetRowData(Row).adminNotcSeq;
				
				abc.open.popup({
					url 	:	url,
					winname :	"admin-notice-detail-pop",	//여러 팝업 창이나 현재 팝업창 조작이 필요할 경우 직접 이 값으로 접근 할 수도 있다.
					method	: 	"get",
					title 	:	"관리자공지 상세보기",
					width 	:	800,
					height	:	803,
					params	:	params
				});
			}
		}
	}
</script>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>관리자 공지 목록</h1>
		</div>
		<div class="window-content">
			<!-- S : search-wrap -->
			<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
				<input type="hidden" id="notcTitleText"	  name="notcTitleText"	 value=""/>
				<input type="hidden" id="loginId"		  name="loginId"		 value=""/>
				<input type="hidden" id="adminName"		  name="adminName"		 value=""/>
				
			<div class="search-wrap">
				<div class="search-inner">
					<table class="tbl-search">
						<caption>공지 검색</caption>
						<colgroup>
							<col style="width: 130px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									<span>검색어</span>
								</th>
								<td class="input">
									<div class="opt-keyword-box">
										<select class="ui-sel" id="searchSelectBox" title="검색어 타입 선택">
											<option value="">선택</option>
											<option value="notcTitleText">제목</option>
											<option value="loginId">작성자 ID</option>
											<option value="adminName">작성자 이름</option>
										</select>
										<input type="text" class="ui-input" id="searchInputBox" placeholder="검색어 입력" title="검색어 입력">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="confirm-box">
						<div class="fl">
							<!-- S : 20190114 수정 // 버튼 가이드 수정으로 인하여 초기화 버튼 일괄 수정 -->
							<a href="javascript:void(0);" class="btn-sm btn-func" id="btnSearchReset"><i class="ico ico-refresh"></i>초기화</a>
							<!-- E : 20190114 수정 -->
						</div>
						<div class="fr">
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="javascript:abc.biz.vendor.adminnotice.adminnoticePopDoAction('search')" class="btn-normal btn-func">검색</a>
							<!-- E : 20190114 수정 -->
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
					<h3 class="content-title">검색 결과</h3>
				</div>
			</div>
			<!-- E : content-header -->
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

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div id="noticeGrid" style="width:100%; height:429px;">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
	
	<script src="/static/common/js/biz/vendor/abcmart.vendor.adminnotice.js<%=_DP_REV%>">
	</script>
</body>
