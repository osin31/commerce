<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>

<script type="text/javascript">
	//[저장] 버튼 -> 메뉴 수정 : 0 / 메뉴 등록 : 1
	var saveBtnCheck = 0;
	// 트리 메뉴의 자식 메뉴 겟수 카운팅
	var childCount = 0;  
	
	$(document).ready(function(){
		
		<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("ib-container1"), "mySheet", "200px", "500px");
		
		<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var initSheet = {};
		
		initSheet.Cfg = {AutoFitColWidth: "init|search|resize|init|colhidden|rowtransaction", DeferredVScroll: 1, UseJsonTreeLevel:1};
		initSheet.HeaderMode = {};	
		initSheet.Cols = [
		  	  { SaveName: "level", Type: "Int", Hidden:1}
        	, { SaveName: "menuName", Type: "Text", Width: 100,  TreeCol: 1, TreeCheck: 0} 
        	, { SaveName: "menuNo", Type: "Text", Hidden:1 }
        	, { SaveName: "menuUrl", Type: "Text", Hidden:1 }
        	, { SaveName: "rsrcUrl", Type: "Text", Hidden:1 }
        	, { SaveName: "allPathMenuNo", Type: "Text", Hidden:1 }
        	, { SaveName: "allPathMenuName", Type: "Text", Hidden:1 }
        	, { SaveName: "upMenuNo", Type: "Text", Hidden:1 }
        	, { SaveName: "sortSeq", Type: "Int", Hidden:1 }
        	, { SaveName: "useYn", SaveName: "useYn", Hidden:1 }
        	, { SaveName: "rgsterName", Type: "Text", Hidden:1 }
        	, { SaveName: "rgstDtm", Type: "Text", Hidden:1 }
        	, { SaveName: "moderName", Type : "Text", Hidden:1}
        	, { SaveName: "modDtm", Type : "Text", Hidden:1}
        ];
		
		<%-- Grid 초기화 --%>
		IBS_InitSheet(mySheet , initSheet);
		
		<%-- Grid 건수 정보 표시 --%>
		//mySheet.SetCountPosition(3);
		
		<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		//mySheet.SetPagingPosition(2);
		
		<%-- Grid width 자동 조절 --%>
		//mySheet.FitColWidth();
		
		<%-- 최초 화면진입시 조회 --%>
		doAction("search");
		
		<%-- [등록] 버튼 클릭시 상세input박스들 초기화 --%>
		$("#insertMenu").click(function() {
			saveBtnCheck = 1;
			setInsert();
			console.log(saveBtnCheck);
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
		
		<%-- 삭제 --%>
		$("#deleteBtn").click(function(){
			doAction("delete");
		});
	});
	
	function doAction(sAction){
		switch(sAction) {
			<%-- 조회 --%>
			case "search" :
				var param = { url : "menu/list"
							, subparam : FormQueryStringEnc(document.frmSearch)
							, sheet : "mySheet"
				};
							
				DataSearch(param);
				
				break;
			
			<%-- 저장 --%>
			case "save" :

				if(saveBtnCheck == 0){
					updateMenu();
				}else if(saveBtnCheck == 1){
					insertMenu();
				}
		}
	}
	
	<%-- 그리드 Click 이벤트 --%>
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		//그리드 내 컬럼 클릭 시 이벤트 발생
		
		saveBtnCheck = 0; // 셀을 선택했을때는 저장버튼이 업데이트 버튼으로
    	
        if (Row == 0) return; //헤더행일때는 폼에 반영 안함.
        var obj = mySheet.GetRowData(Row),
            elem = null;
        
        for (elem in obj) {
            if ($("#" + elem)[0]) {
                $("#" + elem).val(obj[elem]);  
            }
        }

    	if(obj["useYn"] == 'N'){
    		$("#useUncheck").prop("checked", true);
    	}else{
    		$("#useCheck").prop("checked", true);
    	}
    	
    	childCount = mySheet.GetChildNodeCount(Row);
	}
	
	<%-- DataSearch End 이벤트 --%>
	function mySheet_OnRowSearchEnd(row){
		//TO-DO
		//조회 이후 이벤트
	}
	
	<%-- DataSave End 이벤트 --%>
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		//저장 이후 이벤트 
		var rtnCode = Code.RETVAL;
		
		if(rtnCode == 'Y'){
			alert(Msg);
			doAction("search");
		}else{
			alert(Msg);
		}	
	}
	
	<%-- 메뉴 상세 input 초기화 --%>
	function setInsert(){
		$("#level").val(Number($("#level").val()) + 1);
		$("#upMenuNo").val($("#menuNo").val());
		$("#menuName").val("");
		$("#menuUrl").val("");
		$("#rsrcUrl").val("");
		$("#sortSeq").val("");
		$("#useCheck").prop("checked", true);
		$("#rgsterName").val("");
		$("#rgstDtm").val("");
		$("#moderName").val("");
		$("#modDtm").val("");
	}
	
	<%-- 메뉴 등록 ajax --%>
	function insertMenu(){
		var menuLevel 		= $("#level").val();
		var menuNo 			= "";
		var upMenuNo 		= $("#upMenuNo").val();
		var menuName 		= $("#menuName").val();
		var menuUrl			= $("#menuUrl").val();
		var rsrcUrl 		= $("#rsrcUrl").val();
	 	var allPathMenuNo   = $("#allPathMenuNo").val();
	 	var allPathMenuName = $("#allPathMenuName").val();
	 	var sortSeq = $("#sortSeq").val();
	 	var useYn = $("#useCheck").is(":checked");
      	if(useYn == true){ useYn = 'Y'; }
      	else{ useYn = 'N'; }
      	
      	var param = {
      		menuLevel 		: menuLevel,
      		menuNo 			: menuNo,
      		upMenuNo 		: upMenuNo,
            menuName 		: menuName,
            menuUrl			: menuUrl,
            rsrcUrl 		: rsrcUrl,
            allPathMenuNo 	: allPathMenuNo,
            allPathMenuName : allPathMenuName,
            sortSeq 		: sortSeq,
            useYn 			: useYn
        };

        $.ajax({
            type :"post",
            data : param,
            url:"menu/add"
        })
        .done(function(result){
        	alert(result.Result.Message);
        	doAction("search");
        })
        .fail(function(e){
            console.log("e :" + e);
        });
	}
	
	<%-- 메뉴 수정 ajax --%>
	function updateMenu(){
		var menuNo 	 = $("#menuNo").val();
        var menuName = $("#menuName").val();
        var menuUrl  = $("#menuUrl").val();
    	var rsrcUrl  = $("#rsrcUrl").val();
    	var sortSeq  = $("#sortSeq").val();
    	var useYn 	 = $("#useCheck").is(":checked");
      	if(useYn == true){ useYn = 'Y'; }
      	else{ useYn = 'N'; }
      		
        var param = {
    		menuNo 	 : menuNo,
        	menuName : menuName,
        	menuUrl	 : menuUrl,
        	rsrcUrl  : rsrcUrl,
        	sortSeq  : sortSeq,
        	useYn 	 : useYn
        };
        
        $.ajax({
            type :"post",
            data : param,
            url:"menu/modify"
        })
        .done(function(result){
        	alert(result.Result.Message);
        	doAction("search");
        })
        .fail(function(e){
            console.log("e :" + e);
        });
	}
</script>

<div style="float:left;width: 300px; font-size:15px;">
	<span>ALL(관리자+파트너)</span>
	<input type="button" value="등록" id="insertMenu">
	<div id='ib-container1' style="display:inline;"></div>
</div>

<div id='ib-container2' style='float:right;width: 700px; height: 550px; font-size:15px;'>
	<!-- display:none 숨긴 input 태그들 -->
	<div style='display:none; width: 500px; padding-bottom: 4px;'>
		<span>레벨</span>
		<input type='text' id='level' placeholder='메뉴 뎁스 레벨' style='width: 100%;' readonly>
	</div>
	<div style='display:none; width: 500px; padding-bottom: 4px;'>
		<span>상위메뉴번호</span>
		<input type='text' id='upMenuNo' placeholder='메뉴 경로' style='width: 100%; border: 0px;' readonly>
	</div>
	<div style='display:none; width: 500px; padding-bottom: 4px;'>
		<span>메뉴경로(번호 순)</span>
		<input type='text' id='allPathMenuNo' placeholder='숫자만 입력' style='width: 100%;' readonly>
	</div>
	<div style='display:none; width: 500px; padding-bottom: 4px;'>
		<span>메뉴번호</span>
		<input type='text' id='menuNo' placeholder='' style='width: 100%; border: 0px;' readonly>
	</div>
		<!--===============================================-->
			
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>메뉴경로(이름 순)</span>
		<input type='text' id='allPathMenuName' placeholder='숫자만 입력' style='width: 100%; border: 0px;' readonly>
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>메뉴명</span>
		<input type='text' id='menuName' placeholder='공백 포함하여 입력' style='width: 100%;'> (0 / 20 자)
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>권한 적용 시스템</span>
		<input type='text' id='' placeholder='' style='width: 100%;'>
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>메뉴URL</span>
		<input type='text' id='menuUrl' style='width: 100%;'>
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>권한URL</span>
		<input type='text' id='rsrcUrl' style='width: 100%;'>
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>메뉴순서</span>
		<input type='text' id='sortSeq' placeholder='숫자만 입력' style='width: 100%;'>
	</div>
	<div stpye="padding-bottom: 4px;">
		<span>사용여부</span>
		<div>
			<input style="display:none;" type="text" id='useYn'>				
		사용<input type='radio' name='useYn' id="useCheck">
	사용안함<input type='radio' name='useYn' id="useUncheck">
		</div>
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>작성자</span>
		<input type='text' id='rgsterName' placeholder='메뉴 작성자' style='width: 20%; border: 0px;' readonly>
		<span>작성일</span>
		<input type='text' id='rgstDtm' placeholder='메뉴 작성일' style='width: 48%; border: 0px;' readonly>
	</div>
	<div style='width: 500px; padding-bottom: 4px;'>
		<span>수정자</span>
		<input type='text' id='moderName' placeholder='메뉴 수정자' style='width: 20%; border: 0px;' readonly>
		<span>수정일</span> 
		<input type='text' id='modDtm' placeholder='메뉴 수정일' style='width: 48%; border: 0px;' readonly>
	</div>
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="button" class="btn-lblack" id="searchBtn" value="조회">
		<input type="button" class="btn-lblack" id="saveBtn" value="저장">
	</form>
</div>
	

<%@include file="/WEB-INF/views/common/footer.jsp" %>