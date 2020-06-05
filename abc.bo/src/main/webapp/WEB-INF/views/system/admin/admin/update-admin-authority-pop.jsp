<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- 관리자 권한그룹 변경 팝업 초기세팅. --%>
		abc.biz.system.admin.admin.initAuthoritySheet();

		<%-- 관리자 아이디 중복 확인 --%>
		$("#updateBtn").click(function(){
			abc.biz.system.admin.admin.adminAuthorithDoAction('update');
		});

		<%-- 목록개수 변경에 따른 재검색 --%>
		$("#pageCount").off().on("change", function() {
			abc.biz.system.admin.admin.adminAuthorithDoAction("search");
		});
		
	});
	
	<%-- DataSave End 이벤트 --%>
	function adminAuthoritySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		if(Code >= 0){
			alert(Msg);
			opener.parent.abc.biz.system.admin.admin.adminDoAction('search');
			window.self.close();			
		}else{
			alert(Msg);
			window.self.close();
		}
	}
</script>

<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1>권한그룹 변경</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">관리자 목록</h2>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-controller -->
			<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
			<c:forEach var="adminNoStr" items="${adminNoStr}">
				<input type="hidden" name="adminNos" value="${adminNoStr}">
			</c:forEach>
			<div class="tbl-controller">
				<div class="fl">
					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="selView01">목록개수</label>
						<select id="pageCount" name="pageCount" class="ui-sel" id="selView01">
							<!-- <option value="15">15개 보기</option> -->
							<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							
						</select>
					</span>
					<!-- E : opt-group -->

					<!-- S : opt-group -->
					<span class="opt-group">
						<label class="title" for="authNo">변경할 권한그룹명</label>
						<select class="ui-sel" id="authNo" name="authNo">
							<option value="">선택</option>
							<c:forEach var="authGroup" items="${authGroup}">
								<option value="${authGroup.authNo}">${authGroup.authName}</option>
							</c:forEach>
						</select>
					</span>
					<!-- E : opt-group -->

					<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
					<a href="#" id="updateBtn" class="btn-sm btn-link">권한그룹 변경</a>
					<!-- E : 20190114 수정 -->
				</div>
			</div>
			</form>
			<!-- E : tbl-controller -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div style="width:100%; height:429px;" id="authorityGrid">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>

<script src="/static/common/js/biz/system/abcmart.system.admin.admin.js<%=_DP_REV%>">
</script>