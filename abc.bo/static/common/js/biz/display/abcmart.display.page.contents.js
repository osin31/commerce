(function() {

	var _contents = abc.object.createNestedObject(window,"abc.biz.display.page.contents");
	
	/**
	 * 초기화
	 */
	_contents.init = function(){
		
		_contents.event();
		_contents.initSheet();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_contents.initSheet = function() {
		
		// ====================================================================================================
		// PC 코너
		var initPcCornerSheet = {};
		
		initPcCornerSheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initPcCornerSheet.Cols = [
			  	{Header:"상태",		Type:"Status",	SaveName:"status",				Hidden:1,	Edit:1}
			  , {Header:"템플릿번호",	Type:"Text",	SaveName:"dispTmplNo",			Hidden:1,	Align:"Center",	Edit:0}
			  , {Header:"노출순서",	Type:"Text",	SaveName:"sortSeq",				Width:20,	Align:"Center",	Edit:0}
			  , {Header:"전시여부",	Type:"Combo",	SaveName:"dispYn",				Width:30,	Align:"Center",	Edit:1,	ComboText : "전시|전시안함",	ComboCode : "Y|N"}
			  , {Header:"코너번호",	Type:"Text",	SaveName:"dispTmplCornerSeq",	Width:30,	Align:"Center",	Edit:0}
			  , {Header:"코너명",		Type:"Html",	SaveName:"cornerName",			Width:60,	Align:"Center",	Edit:0,	FontUnderline:1,	Cursor:"Pointer"}
			  , {Header:"전시대상",	Type:"Text",	SaveName:"cornerSetName",		Width:60,	Align:"Center",	Edit:0}
			  , {Header:"코너설명",	Type:"Text",	SaveName:"noteText",			Width:60,	Align:"Center",	Edit:0}
			  , {Header:"작성자",		Type:"Text",	SaveName:"authRgsterInfo",		Width:30,	Align:"Center",	Edit:0}
			  , {Header:"작성일시",	Type:"Text",	SaveName:"rgstDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:"수정자",		Type:"Text",	SaveName:"authModerInfo",		Width:30,	Align:"Center",	Edit:0}
			  , {Header:"수정일시",	Type:"Text",	SaveName:"modDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("pcCornerSheet"), "pcList", "100%", "370px");
		IBS_InitSheet(pcList, initPcCornerSheet);
		pcList.FitColWidth();
		
		// ====================================================================================================
		// Mobile 코너
		var initMobileCornerSheet = {};
		
		initMobileCornerSheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initMobileCornerSheet.Cols = [
			  {Header:"상태",			Type:"Status",	SaveName:"status",				Hidden:1,	Edit:1}
			, {Header:"템플릿번호",		Type:"Text",	SaveName:"dispTmplNo",			Hidden:1,	Align:"Center",	Edit:0}
			, {Header:"노출순서",		Type:"Text",	SaveName:"sortSeq",				Width:20,	Align:"Center",	Edit:0}
			, {Header:"전시여부",		Type:"Combo",	SaveName:"dispYn",				Width:30,	Align:"Center",	Edit:1,	ComboText : "전시|전시안함",	ComboCode : "Y|N"}
			, {Header:"코너번호",		Type:"Text",	SaveName:"dispTmplCornerSeq",	Width:30,	Align:"Center",	Edit:0}
			, {Header:"코너명",		Type:"Html",	SaveName:"cornerName",			Width:60,	Align:"Center",	Edit:0,	FontUnderline:1,	Cursor:"Pointer"}
			, {Header:"전시대상",		Type:"Text",	SaveName:"cornerSetName",		Width:60,	Align:"Center",	Edit:0}
			, {Header:"코너설명",		Type:"Html",	SaveName:"noteText",			Width:60,	Align:"Center",	Edit:0}
			, {Header:"작성자",		Type:"Text",	SaveName:"authRgsterInfo",		Width:30,	Align:"Center",	Edit:0}
			, {Header:"작성일시",		Type:"Text",	SaveName:"rgstDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수정자",		Type:"Text",	SaveName:"authModerInfo",		Width:30,	Align:"Center",	Edit:0}
			, {Header:"수정일시",		Type:"Text",	SaveName:"modDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("mobileCornerSheet"), "mobileList", "100%", "370px");
		IBS_InitSheet(mobileList, initMobileCornerSheet);
		mobileList.FitColWidth();
	}
	
	/**
	 * 메뉴 grid action
	 */
	_contents.doActionDpContents = function(sAction){
		
		var dispTmplNo = sAction == 'pc' ? $('#pcDispTmplNo').val() : $('#mobileDispTmplNo').val();
		
		var param = { url : '/display/page/contents/list'
					, sheet : sAction + 'List'
					, subparam : 'dispTmplNo='+dispTmplNo+'&dispPageNo='+$('input[name=dispPageNo]').val()
		};
		
		DataSearch(param);
	}
	
	// 전시여부 수정 
	_contents.dataSave = function(sAction, row){
		var listObject = sAction == 'pc' ? pcList : mobileList;
		var param = {
			dispPageNo : tree.GetRowData(tree.GetSelectRow()).dispPageNo,
			dispTmplCornerSeq : listObject.GetRowData(row).dispTmplCornerSeq,
			dispTmplNo : listObject.GetRowData(row).dispTmplNo,
			dispYn : listObject.GetRowData(row).dispYn,
		};
		$.ajax({
			type : "post",
			url : "/display/page/content/updateDispCornerDispYn",
			data : param,
			async : false
		}).done(function(data) {
			_contents.doActionDpContents(sAction);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	_contents.previewUri;
	//미리보기 url
	_contents.getCurrentPreviewUrl = function(treeRow, deviceCheck){
		var chnnlNo = tree.GetRowData(treeRow).chnnlNo;
		var deviceCode = deviceCheck == 'pc' ? '10000' : '10001';
		
		$.ajax({
			type  : "get",
			url   : '/display/page/content/getCurrentPreviewUrl?chnnlNo=' + chnnlNo + '&deviceCode=' + deviceCode,
			async : false
		}).done(function(data, textStatus, jqXHR) {
			_contents.previewUri = data;
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	
	/**
	 * 이벤트
	 */
	_contents.event = function(){		
		pcList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( pcList.ColSaveName(col) == "cornerName" && value != "" ) {
					var param = {
							dispPageNo: tree.GetRowData(tree.GetSelectRow()).dispPageNo,
							dispTmplCornerSeq: pcList.GetRowData(row).dispTmplCornerSeq,
							dispTmplNo: pcList.GetRowData(row).dispTmplNo,
							deviceTypeName : 'PC'
					}
					abc.biz.display.common.openCornerInfoPopup(param);
				}
			}
		}
		
		pcList_OnChange = function(Row, Col, Value, OldValue, RaiseFlag) {
			if(Col == 3) {
				_contents.dataSave("pc", Row);
			}
		}
		
		mobileList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( mobileList.ColSaveName(col) == "cornerName" && value != "" ) {
					var param = {
							dispPageNo: tree.GetRowData(tree.GetSelectRow()).dispPageNo,
							dispTmplCornerSeq: mobileList.GetRowData(row).dispTmplCornerSeq,
							dispTmplNo: mobileList.GetRowData(row).dispTmplNo,
							deviceTypeName : 'Mobile'
					}
					abc.biz.display.common.openCornerInfoPopup(param);
				}
			}
		}
		
		mobileList_OnChange = function(Row, Col, Value, OldValue, RaiseFlag) {
			if(Col == 3) {
				_contents.dataSave("mobile", Row);
			}
		}
		
		//pc 미리보기 
		$('body').on('click', '#preview-pc-page', function(e){
			var date = $('#preview-pc-date').val();
			var treeRow = tree.GetSelectRow();
			var plusUrl = $('input[name=pcDispUrl]').val();
			var pageTypeCode = tree.GetRowData(treeRow).dispPageTypeCode;
			var dispPageNo = tree.GetRowData(treeRow).dispPageNo;
			
			if(pageTypeCode >= Number('10009') && pageTypeCode <= Number('10015')){
				if(abc.text.isBlank(plusUrl)){
					alert('PC 전시 URL을 등록해 주세요.');
					return false;
				}
			}
			
			if(abc.text.isBlank(date)){
				alert('미리보기 일시를 입력해 주세요.');
				return false;
			}
			_contents.getCurrentPreviewUrl(treeRow, 'pc');
			
			var url = _contents.previewUri;
			url += plusUrl;
			url += "?previewDate=" + date;
			
			if(abc.text.allNull(plusUrl)){
				url += "&previewDispPageNo=" + dispPageNo + "&previewType=M";
			}
			
//			if(".1000021.1000022.1000023.1000020.".indexOf($("[name='upDispPageNo']").val()) > -1) {
//				// MAIN of ART/ABC/GS/OTS
//				url += "&previewDispPageNo=" + $("[name='dispPageNo']").val();
//				url += "&previewType=M";
//			}
//			window.open(url+'?previewDate='+date, "미리보기", "width=1700, height=970, toolbar=no, menubar=no, scrollbars=no, resizable=yes");
			window.open(url, "미리보기", "width=1700, height=970, toolbar=no, menubar=no, scrollbars=no, resizable=yes");  
		});
		
		//모바일 미리보기
		$('body').on('click', '#preview-mobile-page', function(e){
			var date = $('#preview-mobile-date').val();
			var treeRow = tree.GetSelectRow();
			var plusUrl = $('input[name=mobileDispUrl]').val();
			var pageTypeCode = tree.GetRowData(treeRow).dispPageTypeCode;
			var dispPageNo = tree.GetRowData(treeRow).dispPageNo;
			
			if(pageTypeCode >= Number('10009') && pageTypeCode <= Number('10015')){
				if(abc.text.isBlank(plusUrl)){
					alert('MOBILE 전시 URL을 등록해 주세요.');
					return false;
				}
			}
			
			if(abc.text.isBlank(date)){
				alert('미리보기 일시를 입력해 주세요.');
				return false;
			}
			var url = _contents.getCurrentPreviewUrl(treeRow, 'mobile');
			
			url += "?previewDate=" + date + "&viewer=mobile";
			
			if(abc.text.allNull(plusUrl)){
				url += "&previewDispPageNo=" + dispPageNo + "&previewType=M";
			}
			
//			if(".1000021.1000022.1000023.1000020.".indexOf($("[name='upDispPageNo']").val()) > -1) {
				// MAIN of ART/ABC/GS/OTS
//				url += "&previewDispPageNo=" + $("[name='dispPageNo']").val();
//				url += "&previewType=M";
//			}
//			 window.open(url+'?viewer=mobile&previewDate='+date, "미리보기", "width=450, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");  
			 window.open(url, "미리보기", "width=450, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");  
		});
	}	
	
	$(function() {
		_contents.init();
	});
	
})();