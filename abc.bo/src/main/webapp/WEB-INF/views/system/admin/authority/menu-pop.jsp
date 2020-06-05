<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		<%-- Grid 객체 생성 --%>
		abc.biz.system.admin.authMenu.initAuthMenuSheet();
	
		$("#authGroupMenuSave").click(function(){
			abc.biz.system.admin.authMenu.authGroupMenuSave();
		});
	});

	<%-- Grid 검색 후 호출 --%>
	function authMenuSheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response){
	
		if(authMenuSheet.GetRowExpanded(1) == 0){
			authMenuSheet.SetRowExpanded(1, 1);
		}
	}

	<%-- 그리드 Click 이벤트 --%>
	function authMenuSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			var useYnCode;
			//그리드의 useYn값으로 체크박스 세팅
			if (authMenuSheet.ColSaveName(Col) == "useYnCode") {
				//자식노드가 있을경우 부모노드의 checkbox세팅값으로 설정
				if(authMenuSheet.GetChildNodeCount(Row) > 0){
					var childRows = authMenuSheet.GetChildRows(Row).split("|");
					for(var i in childRows){
						authMenuSheet.SetCellValue(childRows[i], "useYnCode", authMenuSheet.GetCellValue(Row,"useYnCode"));
					}
				}

				// 자식노드의 체크박스값에 따라 부모노드 체크박스 세팅
				if(authMenuSheet.GetCellValue(Row,"menuNo") != "10000"){
					var parentRow = authMenuSheet.GetParentRow(Row);
					abc.biz.system.admin.authMenu.parentRowCheckYn(parentRow);
					if(authMenuSheet.GetCellValue(Row,"menuGbnType") == "S"){
						var parentRow1 = authMenuSheet.GetParentRow(parentRow);
						abc.biz.system.admin.authMenu.parentRowCheckYn(parentRow1);
					}
				}
				
			}
		}
	}

	<%-- DataSave End 이벤트 --%>
	function authMenuSheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
	}

</script>
<form id="frmSearch" name="frmSearch">
	<input type="hidden" id="authApplySystemType" name="authApplySystemType" value="${params.authApplySystemType}"/>
	<input type="hidden" id="authNo" name="authNo" value="${params.authNo}"/>
<body class="window-scroll-none">
	<div class="window-wrap">
		<div class="window-title">
			<h1>[<c:out value="${params.authName}"/>] 권한 메뉴(<c:out value="${params.adminCount}"/>)</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h2 class="content-title">메뉴 목록</h2>
				</div>
				<div class="fr">
					<span class="guide-text"> 권한 적용 시스템 : 
					<c:choose>
						<c:when test = "${params.authApplySystemType == 'B'}">
							BO(관리자시스템)
						</c:when>
						<c:when test = "${params.authApplySystemType == 'P'}">
							PO(파트너시스템)
						</c:when>
					</c:choose>
					</span>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
					<div id="menuListGrid"></div>
			</div>
			<!-- E : ibsheet-wrap -->

			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
				<a href="javascript:void(0);" id="authGroupMenuSave" class="btn-normal btn-save">저장</a>
				<!-- E : 20190114 수정 -->
			</div>
			<!-- E : window-btn-group -->
		</div>
	</div>
	
<script src="/static/common/js/biz/system/abcmart.system.admin.authmenu.js<%=_DP_REV%>"></script>
</body>
</form>
