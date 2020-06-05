/***
 * 메뉴 관리.
 *
 */
(function() {

	var _menu = abc.object.createNestedObject(window,"abc.biz.system.menu");
	_menu.data = { focusRow: 1, newFlag : false };
	/**
	 * 메뉴 sheet init
	 */
	_menu.initMenuSheet = function() {
		var initMenuSheet = {};
		initMenuSheet.Cfg = {};
		initMenuSheet.Cols = [
				{Header:"메뉴명", 		Type:"Text",	SaveName:"menuName",			Width:20,	Align:"Left",	Edit:0,	TreeCol:1,	FontUnderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:"메뉴번호",		Type:"Text",	SaveName:"menuNo",				Width:60,	MinWidth:60,	Align:"Center",	Hidden:1, Edit:1}
			  , {Header:'상위메뉴번호',		Type:"Text",	SaveName:"upMenuNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'메뉴구분',		Type:"Text",	SaveName:"menuGbnType",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'메뉴URL',		Type:"Text",	SaveName:"menuUrl",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'리소스URL',		Type:"Text",	SaveName:"rsrcUrl",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'리소스구분',		Type:"Text",	SaveName:"rsrcType",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'메뉴이미지명',		Type:"Text",	SaveName:"menuImageName",		Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'메뉴이미지URL',	Type:"Text",	SaveName:"menuImageUrl",		Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'메뉴LEVEL',		Type:"Text",	SaveName:"level",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'전체경로메뉴번호',	Type:"Text",	SaveName:"allPathMenuNo",		Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'전체경로메뉴명',	Type:"Text",	SaveName:"allPathMenuName",		Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'회원정보관리여부',	Type:"Text",	SaveName:"memberInfoMgmtYn",	Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'권한적용시스템',	Type:"Text",	SaveName:"authApplySystemType",	Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'정렬순번',		Type:"Text",	SaveName:"sortSeq",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'사용여부',		Type:"Text",	SaveName:"useYn",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'전시여부',		Type:"Text",	SaveName:"dispYn",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'비고',			Type:"Text",	SaveName:"noteText",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록자이름',		Type:"Text",	SaveName:"rgsterName",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록자번호',		Type:"Text",	SaveName:"rgsterNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'등록일시',		Type:"Date",	SaveName:"rgstDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자이름',		Type:"Text",	SaveName:"moderName",			Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'수정자번호',		Type:"Text",	SaveName:"moderNo",				Width:100,	Align:"Left",	Hidden:1,	Edit:1}
			  , {Header:'수정일시',		Type:"Date",	SaveName:"modDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("menuGrid"), "menuSheet", "100%", "100%");
		IBS_InitSheet(menuSheet, initMenuSheet);
		menuSheet.FitColWidth();
		this.doActionMenu("search");
	}

	/**
	 * 메뉴 grid action
	 */
	_menu.doActionMenu = function(sAction){
		switch (sAction) {
		case "search":

			var param = { url : "/system/menu/read-list"
						, subparam : FormQueryStringEnc(document.menuForm)
						, sheet : "menuSheet"
			};
			DataSearch(param);
			break;
		}
	}

	/**
	 * 선택된 그리드정보를 메뉴정보 영역에 세팅
	 */
	_menu.setMenuData = function(Row){

		$("#status").val(abc.consts.CRUD_U);
		$("#allPathMenuNameText").text(menuSheet.GetRowData(Row).allPathMenuName);
		$("#allPathMenuName").val(menuSheet.GetRowData(Row).allPathMenuName);
		$("#allPathMenuNo").val(menuSheet.GetRowData(Row).allPathMenuNo);
		if($("#authApplySystemType").val() == 'B'){
			$("#authApplySystemTypeName").text("BO(관리자 시스템)");
		}else{
			$("#authApplySystemTypeName").text("PO(파트너 시스템)");
		}

		$("#menuNo").val(menuSheet.GetRowData(Row).menuNo);
		$("#menuName").val(menuSheet.GetRowData(Row).menuName);
		$("#menuNameCnt").html("("+$("#menuName").val().length+" / 20자)");

		$("#upMenuNo").val(menuSheet.GetRowData(Row).upMenuNo);
		$("#menuLevel").val(menuSheet.GetRowData(Row).level);
		$("#menuGbnType").val(menuSheet.GetRowData(Row).menuGbnType);
		$("#menuUrl").val(menuSheet.GetRowData(Row).menuUrl);
		$("#rsrcUrl").val(menuSheet.GetRowData(Row).rsrcUrl);
		$("#sortSeq").val(menuSheet.GetRowData(Row).sortSeq);

		$("#useYn").val(menuSheet.GetRowData(Row).useYn);
		if(menuSheet.GetRowData(Row).useYn == 'Y'){
			$("#radioUseY").prop('checked', true);
			$("#radioUseN").prop('checked', false);
		}else{
			$("#radioUseY").prop('checked', false);
			$("#radioUseN").prop('checked', true);
		}

		$("#dispYn").val(menuSheet.GetRowData(Row).useYn);
		if(menuSheet.GetRowData(Row).dispYn == 'Y'){
			$("#radioDispY").prop('checked', true);
			$("#radioDispN").prop('checked', false);
		}else{
			$("#radioDispY").prop('checked', false);
			$("#radioDispN").prop('checked', true);
		}

		$("#rgsterName").text(menuSheet.GetRowData(Row).rgsterName);
		$("#rgsterNo").val(menuSheet.GetRowData(Row).rgsterNo);
		$("#rgstDtm").text(menuSheet.GetCellText(Row, 'rgstDtm'));
		$("#moderName").text(menuSheet.GetRowData(Row).moderName);
		$("#moderNo").val(menuSheet.GetRowData(Row).moderNo);
		$("#modDtm").text(menuSheet.GetCellText(Row, 'modDtm'));

		if(menuSheet.GetRowData(Row).menuNo == "10000"){
			$("#btnSaveDiv").hide();
			$("#btnRemoveDiv").hide();
		}else{
			$("#btnSaveDiv").show();
			$("#btnRemoveDiv").show();
		}


		menuSheet.SetSelectRow(Row);
		$('input').attr("readonly", false);
		$(':radio').attr('disabled', false);

		if(Row == 1){
			$("#btnSaveDiv").hide();
			$('input').attr("readonly", true);
			$(':radio').attr('disabled', true);
		}

		var i = 0;
		while(i < 3){
			i++;
			var parentRow = menuSheet.GetParentRow(Row);
			if(parentRow == 1 ){
				break;
			}
			menuSheet.SetRowExpanded(parentRow, 1);
			Row = parentRow;
		}
	}

	/**
	 * 메뉴명 카운터
	 */
	_menu.menuNameCount = function(){
		var content = $("#menuName").val();
		$("#menuNameCnt").html("("+content.length+" / 20자)");

		if (content.length > 20){
			alert("최대 20자까지 입력 가능합니다.");
			$(this).val(content.substring(0, 20));
			$('#menuNameCnt').html("(20 / 20자)");
		}
	}

	/**
	 * 메뉴신규등록버튼 이벤트
	 */
	_menu.menuRegistBtn = function(){
		var allPathMenuNoArr = $("#allPathMenuNo").val().split("^");
		if(allPathMenuNoArr.length == 1){
			$("#upMenuNo").val(allPathMenuNoArr.shift());
			$("#menuGbnType").val("G");
		}else if(allPathMenuNoArr.length == 2){
			$("#upMenuNo").val(allPathMenuNoArr.slice(1,2));
			$("#menuGbnType").val("M");
		}else if(allPathMenuNoArr.length == 3){
			$("#upMenuNo").val(allPathMenuNoArr.pop());
			$("#menuGbnType").val("S");

		}else{
			alert("4뎁스 메뉴에서는 메뉴 추가를 할수 없습니다");
			return;
		}

		_menu.data.newFlag = true;

		$("#status").val(abc.consts.CRUD_C);
		$("#menuNo").val("");
		$("#menuName").val("");
		$("#menuNameCnt").html("("+$("#menuName").val().length+" / 20자)");
		$("#menuUrl").val("");
		$("#rsrcUrl").val("");
		$("#useYn").val("Y");
		$("#radioUseN").prop('checked', false);
		$("#radioUseY").prop('checked', true);
		$("#dispYn").val("Y");
		$("#radioDispN").prop('checked', false);
		$("#radioDispY").prop('checked', true);
		$("#rgsterName").text("");
		$("#rgstDtm").text("");
		$("#moderName").text("");
		$("#modDtm").text("");

		$("#btnSaveDiv").show();
		//삭제버튼 hide
		$("#btnRemoveDiv").hide();

		var parent = menuSheet.GetSelectRow();
		var child = menuSheet.GetLastChildRow(parent);
		var target = child > 0 ? child + 1 : parent + 1;
		var added = menuSheet.DataInsert(target);

		$("#sortSeq").val(menuSheet.GetChildNodeCount(parent));

		menuSheet.SetRowExpanded(parent, 1);
		menuSheet.SetRowData(added, {
			menuName:'새 메뉴'
		});
		$('input').attr("readonly", false);
		$(':radio').attr('disabled', false);
		_menu.data.focusRow = added;
	}

	/***
	 * 메뉴 기본 정보 저장
	 */
	_menu.menuSaveBtn = function() {

		var status = $("#status").val();

		if (!this.menuValidate()) {
			return;
		}

		if (!confirm('저장하시겠습니까?')) {
			return;
		}

		$.ajax({
			type :"post",
			url : "/system/menu/update",
			data: $("#menuForm").serialize()
		})
		.done(function(data){
			if(status == abc.consts.CRUD_C){
				alert("저장되었습니다");
			}else{
				alert("수정되었습니다");
			}
			_menu.data.newFlag = false;
			_menu.doActionMenu("search");
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
			alert("저장에 실패했습니다");
		});

	}

	/***
	 * 메뉴 삭제
	 */
	_menu.menuRemoveBtn = function() {

		if (!confirm('삭제하시겠습니까?')) {
			return;
		}

		$.ajax({
			type :"post",
			url : "/system/menu/delete",
			data: $("#menuForm").serialize()
		})
		.done(function(data){
			alert(data.Message);
			if(data.result == abc.consts.BOOLEAN_TRUE){
				_menu.doActionMenu("search");
			}
			_menu.data.newFlag = false;
		})
		.fail(function(e){
			console.log("e :" + e);
			alert("삭제 실패하였습니다");
		});

	}

	/***
	 * 메뉴 validation
	 */
	_menu.menuValidate = function(){
		if(abc.text.isBlank($("#menuName").val())){
			alert("메뉴명를 입력해주세요");
			$("#menuName").focus();
			return false;
		}
		if(abc.text.isBlank($("#sortSeq").val())){
			alert("메뉴순서를 입력해주세요");
			$("#sortSeq").focus();
			return false;
		}

		return true;
	}

})();