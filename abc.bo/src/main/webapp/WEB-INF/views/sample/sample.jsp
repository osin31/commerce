<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>

<script type="text/javascript">

	$(document).ready(function(){
		
		<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("sampleGrid"), "mySheet", "100%", "370px");
		
		<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var initSheet = {};
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:10, DragMode:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 Type:"Status",		SaveName:"status",			Width: 0,  Align:"Left", 	Edit:0, Hidden:1}
			, {Header:"테스트1", Type:"Text",		SaveName:"menuNo",			Width: 20, Align:"Left", 	Edit:0}
			, {Header:"테스트2", Type:"Text",		SaveName:"upMenuNo",		Width: 20, Align:"Left", 	Edit:0}
			, {Header:"테스트3", Type:"Text",		SaveName:"menuGbnType",	Width: 15, Align:"Center", 	Edit:1}
			, {Header:"테스트4", Type:"Text",		SaveName:"menuName",		Width: 30, Align:"Left", 	Edit:1}
			, {Header:"테스트5", Type:"Text",		SaveName:"rsrcUrl",		Width: 30, Align:"Left", 	Edit:1}
		];
		
		<%-- Grid 초기화 --%>
		IBS_InitSheet(mySheet , initSheet);
		
		<%-- Grid 건수 정보 표시 --%>
		mySheet.SetCountPosition(3);
		
		<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		mySheet.SetPagingPosition(2);
		
		<%-- Grid width 자동 조절 --%>
		mySheet.FitColWidth();
		
		<%-- CKEDITOR 셋팅--%>
		CKEDITOR.replace("contentsTxt", {
			width:"50%",
			height:"100px"
		});
		
		<%-- 조회 --%>
		$("#searchBtn").click(function(){
			doAction("search");
		});
		
		<%-- 저장 --%>
		$("#saveBtn").click(function(){
			var gridCnt = mySheet.RowCount();
			
			if(gridCnt <= 0){
				alert("조회된 데이터가 없습니다.");
				return false;
			}
			
			if(confirm("저장하시겠습니까?")){
				doAction("save");
			}
		});
		
		<%-- 저장 --%>
		$("#dataSaveBtn").click(function(){
			if(confirm("저장하시겠습니까?")){
				doAction("saveData");
			}
		});
		
		<%-- 삭제 --%>
		$("#deleteBtn").click(function(){
			doAction("delete");
		});
		
		doAction("search");
	});
	
	function doAction(sAction){
		switch(sAction) {
			<%-- 조회 --%>
			case "search" :
				var param = { url : "/sample/sampleListData"
					, onePageRow : 10
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "mySheet" };
				
				DataSearchPaging(param);
				
				break;
			<%-- 그리드저장 --%>	
			case "save" :
				var param = { url:"/sample/sampleListUpdate/"
					, subparam:FormQueryStringEnc(document.frmSearch)
					, sheet:"mySheet"};
				
				DataSave(param);
				
				break;
			<%-- 데이터저장 --%>	
			case "saveData" :
				$("#contentsTxt").val(CKEDITOR.instances.contentsTxt.getData());
				
				$.ajax({ url:"/sample/sampleInsert/"
					, data: $("#gForm").serialize()
					, success: function(data){
						
					}
					, error: function(xhr,status, error){
						
					}
				});
				
				break;
		}
	}
	
	<%-- 그리드 Click 이벤트 --%>
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		if ( Row != 0) {
			if (mySheet.ColSaveName(Col) == "menuNo") {
				var menuNo = mySheet.GetCellValue(Row, "MENU_NO");
				var menuNm = mySheet.GetCellValue(Row, "MENU_NAME");
			}
		}
	}
	
	<%-- DataSearch(Row) End 이벤트
	function mySheet_OnRowSearchEnd(row){
		//TO-DO
		//조회 이후 이벤트
	}
	--%>
	
	<%-- DataSearch End 이벤트 --%>
	function mySheet_OnSearchEnd(Code, Msg, StCode, StMsg, Response){
		
	}
	
	<%-- DataSave End 이벤트 --%>
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		//저장 이후 이벤트 
		if(Code > 0){
			alert(Msg);
			doAction("search");
		}else{
			alert(Msg);
		}
	}
	
	<%--
	드래그 앤 드롭
	--%>
	function mySheet_OnDropEnd(FromSheet, FromRow, ToSheet, ToRow, X, Y, Type){
		var fromJsonData =  mySheet.GetRowData(FromRow);
		var toJsonData =  mySheet.GetRowData(ToRow);

		mySheet.DataInsert(ToRow+1);
		mySheet.SetRowData(ToRow+1, fromJsonData);
		mySheet.RowDelete(FromRow);
	}
	
</script>

<div>
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="button" class="btn-lblack" id="searchBtn" value="조회">
		<input type="button" class="btn-lblack" id="saveBtn" value="그리드저장">
		<input type="button" class="btn-lblack" id="dataSaveBtn" value="등록">
	</form>
	<div id="sampleGrid"></div>
	<form id="gForm" name="gForm" method="post" onsubmit="return false;">
	<div>
		<textarea rows="10" cols="80" name="contentsTxt" id="contentsTxt" class="w100"></textarea>
	</div>
	</form>
</div>

<%@include file="/WEB-INF/views/common/footer.jsp" %>
