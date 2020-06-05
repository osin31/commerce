(function() {

	var _template = abc.object.createNestedObject(window,"abc.biz.display.template");
	
	/**
	 * 초기화
	 */
	_template.init = function(){
		_template.sheet.init();
		_template.event();
		
		abc.biz.display.common.checkBoxAll();
		
		_template.getList();
		
		var menuType = $('#menuType').val();
		

	}
	
	/**
	 *  IBSheet 초기화
	 */
	_template.sheet = {};
	_template.sheet.init = function(){
		createIBSheet2(document.getElementById("templateSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		console.log(pageCount);
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header:"사이트" , Type:"Text", SaveName:"siteName", Width: 10,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header:"채널", Type:"Text", SaveName:"chnnlName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"템플릿 유형", Type:"Text", SaveName:"tmplTypeName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"디바이스", Type:"Text", SaveName:"deviceName", Width: 10, Align:"Center", Edit:0, Sort:0}
			, {Header:"템플릿코드", Type:"Text", SaveName:"dispTmplNo", Width: 10, Align:"Center",  Edit:0, Sort:0}
			, {Header:"템플릿명", Type:"Text", SaveName:"tmplName", Width: 20, Align:"Left",  Edit:0, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"사용여부", Type:"Text", SaveName:"useYnName", Width: 10, Align:"Center",  Edit:0, Sort:0}
			, {Header:"작성자", Type:"Text", SaveName:"rgsterInfo", Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"수정자", Type:"Text", SaveName:"moderInfo", Width: 15, Align:"Center", Edit:0, Sort:0}
			, {Header:"", Type:"Button", SaveName:"select", Width: 15, Align:"Center", Edit:1, DefaultValue:"선택", Sort:0}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
	}
	
	
	/**
	 * 이벤트
	 */
	_template.event = function(){
		
		$(document.forms.searchForm).on('submit', function(){
			_template.getList();
			return false;
		});
		
		list_OnButtonClick = function(row, col){
			var data = list.GetRowData(row);
			console.log(data);
			
			var cb = $('#callback').val();
			var opFunc = new Function('return opener.'+cb)();

			opFunc(data);
			window.close();
		}
		
		$('#selTermsModule').on('change', function(e){
			_template.getList();
		})
		
	}
	
	/**
	 * 리스트 조회
	 */
	_template.getList = function(){
			
		var pageCount = $('#selTermsModule').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/display/template/list"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" };
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_template.init();
	});
	
})();