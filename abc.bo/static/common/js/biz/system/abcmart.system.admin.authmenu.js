/***
 * 권한그룹 관리.
 * 
 */
(function() {

	/*************************************************************************
	 *					권한그룹관리 : 권한메뉴 팝업
	 *************************************************************************/
	var _authMenu = abc.object.createNestedObject(window,"abc.biz.system.admin.authMenu");
	
	// Grid 객체 생성
	_authMenu.initAuthMenuSheet = function() {
		createIBSheet2(document.getElementById("menuListGrid"), "authMenuSheet", "100%", "430px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initAuthMenuSheet = {};
		
		initAuthMenuSheet.Cfg = {};
		initAuthMenuSheet.Cols = [
				{Header:"" ,			Type:"Status",	SaveName:"status",				Width: 0,	Align:"Left", 	Edit:0, Hidden:1}	
			  , {Header:"메뉴목록", 		Type:"Text",	SaveName:"menuName",			Width:60,	Align:"Left",	Edit:1,	TreeCol:1,	FontUnderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:"사용여부",		Type:"CheckBox",SaveName:"useYnCode",			Width:30,	Align:"Center",	Sort:0, HeaderCheck:0}
			  , {Header:"메뉴번호",		Type:"Text",	SaveName:"menuNo",				Width:60,	MinWidth:60,	Align:"Center",	Hidden:1, Edit:1}
			  , {Header:'메뉴구분',		Type:"Text",	SaveName:"menuGbnType",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'메뉴LEVEL',		Type:"Text",	SaveName:"level",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'권한적용시스템',	Type:"Text",	SaveName:"authApplySystemType",	Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'정렬순번',		Type:"Text",	SaveName:"sortSeq",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
		];
		
		// Grid 초기화
		IBS_InitSheet(authMenuSheet , initAuthMenuSheet);
		
		// Grid width 자동 조절
		authMenuSheet.FitColWidth();

		// 메뉴 리스트
		this.doActionAuthMenu("search");
	}

	_authMenu.doActionAuthMenu = function(sAction){
		switch(sAction) {
		// 조회
		case "search" :
			var param = { url : "/system/admin/authority/menu-read-list"
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "authMenuSheet" };
			
			DataSearch(param);
			
			break;
		
		// 저장 
		case "save" :
			var param = { url:"/system/admin/authority/menu-update"
				, subparam:FormQueryStringEnc(document.frmSearch)
				, sheet:"authMenuSheet"};

			DataSave(param);
			
			break;
		}
	}

	_authMenu.parentRowCheckYn = function(parentRow){
		var childRows = authMenuSheet.GetChildRows(parentRow).split("|");
		var checkCount = 0;
		for(var i in childRows){
			checkCount += authMenuSheet.GetCellValue(childRows[i], "useYnCode");
		}
		if(checkCount > 0){
			authMenuSheet.SetCellValue(parentRow, "useYnCode", 1);
		}else{
			authMenuSheet.SetCellValue(parentRow, "useYnCode", 0);
		}
	}
	
	_authMenu.authGroupMenuSave = function(){
		if (!_authMenu.regValidate()) {
			return;
		}
		if (!confirm('저장하시겠습니까?')) {
			return;
		}
		_authMenu.doActionAuthMenu('save');
	}
	
	_authMenu.regValidate = function(){
		if(authMenuSheet.CheckedRows("useYnCode") == 0){
			alert("권한메뉴는 최소 1개 이상 선택해주세요");
			return false;
		}

		return true;
	}
	
})();