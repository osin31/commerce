/***
 * 권한그룹 관리.
 * 
 */
(function() {

	/*************************************************************************
	 *					권한그룹관리 : 목록
	 *************************************************************************/
	var _authority = abc.object.createNestedObject(window,"abc.biz.system.admin.authority");
	
	/**
	 *  sheet init
	 */
	_authority.initAuthSheet = function() {
		createIBSheet2(document.getElementById("adminAuthGrid"), "authSheet", "100%", "370px");
		
		var initAuthSheet = {};
		var pageCount = $('#pageCount').val();

		initAuthSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initAuthSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initAuthSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"권한 적용 시스템", 	Type:"Text",		SaveName:"authApplySystemType",	Width: 20, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"권한 적용 시스템", 	Type:"Text",		SaveName:"authApplySystemTypeText",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"권한번호" ,			Type:"Text",		SaveName:"authNo",				Width: 0,  Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"권한 그룹명", 		Type:"Text",		SaveName:"authName",			Width: 20, Align:"Left", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"관리자", 			Type:"Text",		SaveName:"adminCount",			Width: 20, Align:"Center", 	Edit:0, Sort:1}
			, {Header:"권한메뉴", 			Type:"Button",		SaveName:"authMenuButton",		Width: 20, Align:"Center",  DefaultValue:"등록" , Sort:0}
			, {Header:"권한메뉴카운트", 		Type:"Text",		SaveName:"authMenuCount",		Width: 35, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"설명", 			Type:"Text",		SaveName:"noteText",			Width: 35, Align:"Center", 	Edit:0 , Sort:0}
			, {Header:"사용여부", 			Type:"Text",		SaveName:"useYn",				Width: 15, Align:"Center", 	Edit:0 , Sort:0}
			, {Header:"작성자", 			Type:"Text",		SaveName:"rgsterName",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"작성일시", 			Type:"Date",		SaveName:"rgstDtm",				Width: 30, Align:"Center", 	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN, Sort:1}
		];
		
		IBS_InitSheet(authSheet , initAuthSheet);
		authSheet.SetCountPosition(3);

		authSheet.SetPagingPosition(2);

		authSheet.FitColWidth();

		authSheet.SetExtendLastCol(1);
		
		_authority.doActionAuthGroup("search");
	}
	
	_authority.doActionAuthGroup = function(sAction){
		switch (sAction) {
		case "search":
			var pageCount = $('#pageCount').val();
			
			var param = { url : "/system/admin/authority/read-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.adminAuthForm)
				, sheet : "authSheet" };

			DataSearchPaging(param);
			
			break;
		}
	}
	/**
	 * 권한그룹 변경이력 이벤트
	 */
	_authority.authGroupHistory = function (){
		var url = "/system/admin/authority/history-pop";
		var params = {};

		abc.open.popup({
					url 	:	url,
					winname :	"authority-history-pop",
					method	: 	"get",
					title 	:	"권한그룹관리변경이력",
					width 	:	600,
					height	:	750,
					params	:	params
			
		});
	}
	
	/**
	 * 권한그룹 등록 이벤트
	 */
	_authority.authGroupRegist = function (){
		var url = "/system/admin/authority/update-pop";
		var params = {};
		params.status = abc.consts.CRUD_U;
		abc.open.popup({
					url 	:	url,
					winname :	"authority-create-pop",
					method	: 	"get",
					title 	:	"권한그룹관리등록",
					width 	:	600,
					height	:	380,
					params	:	params
			
		});
	}
	
	
})();