(function() {

	var _sizechart = abc.object.createNestedObject(window,"abc.biz.product.sizechart");
	
	/**
	 * 초기화
	 */
	_sizechart.init = function(){

		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_sizechart.sheet.init();
		_sizechart.event();
		
		_sizechart.getList();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_sizechart.sheet = {};
	_sizechart.sheet.init = function(){
		createIBSheet2(document.getElementById("sizechartSheet"), "list", "100%", "370px");
		
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			 {Header:"번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사이즈가이드 ID" , Type:"Text", SaveName:"sizeChartSeq", Width: 30,  Align:"Center", Edit:0, FontBold:1}
			, {Header:"사이즈가이드 명" , Type:"Text", SaveName:"sizeChartName", Width: 30,  Align:"Left", Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",	Width : 20,	Align : "Center",	Edit : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}
			, {Header:"표준카테고리(대)", Type:"Text", SaveName:"stdCtgrName", Width : 20, Align:"Center", Edit:0}
			, {Header:"브랜드", Type:"Text", SaveName:"brandName",  Align:"Center", Edit:0}
			, {Header:"테마", Type:"Combo", SaveName:"themeType", Width: 10, Align:"Center", Edit:0, ComboText:"남성|여성|아동|공용", ComboCode : "M|F|C|A"}
			, {Header:"작성자", Type:"Text", SaveName:"rgsterInfo", Width: 20, Align:"Center", Edit:0}
			, {Header:"등록일시", Type:"Date", SaveName:"rgstDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수정자", Type:"Text", SaveName:"moderInfo", Width: 20, Align:"Center", Edit:0}
			, {Header:"수정일시", Type:"Date", SaveName:"modDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		_sizechart.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_sizechart.sheet.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( list.ColSaveName(col) == "sizeChartName" && value != "" ) {
					abc.biz.display.common.setFormParameter.setDetailMove();
					//상세페이지 이동
					location.href = "/product/sizechart/detail?sizeChartSeq=" + list.GetRowData(row).sizeChartSeq;
				}
			}
		}

		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
	}
	
	/**
	 * 이벤트
	 */
	_sizechart.event = function(){
		//검색
		$(document.forms.searchForm).on('submit', function(e){
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_sizechart.getList();
			return false;
		});
		
		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, "abc.biz.product.sizechart.callbackFindBrand");
		});
		
		$('[name=brandAssignYn]').on('change', function(){
			$('.ip-search-box').hide();
			if($(this).val() == 'Y') $('.ip-search-box').show();
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			$('.ip-search-box').hide();
			$("#brandName").val('');
		});
		
		$('#pageCount').on('change', function(event) {
			_sizechart.getList();
		});
	}
	
	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_sizechart.callbackFindBrand = function(data) {
		//$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);
	}
	
	/**
	 * 리스트 조회
	 */
	_sizechart.getList = function(){
		var pageCount = $('#pageCount').val(); // 한페이지내 결과 로우 갯수
		var param = { url : "/product/sizechart/list/read"
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(document.forms.searchForm)
			, sheet : "list" 
			, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};
		
		DataSearchPaging(param);
		
	}
	
	$(function() {
		_sizechart.init();
	});
	
})();