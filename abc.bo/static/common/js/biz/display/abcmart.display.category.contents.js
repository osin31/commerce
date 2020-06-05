(function() {

	var _contents = abc.object.createNestedObject(window,"abc.biz.display.category.contents");
	
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
			  {Header:"상태",			Type:"Status",	SaveName:"status",				Hidden:1,	Edit:1}
			  , {Header:"템플릿번호",	Type:"Text",	SaveName:"dispTmplNo",			Hidden:1,	Align:"Center",	Edit:0}
			  , {Header:"노출순서",		Type:"Text",	SaveName:"sortSeq",				Width:20,	Align:"Center",	Edit:0}
			  , {Header:"코너번호",		Type:"Text",	SaveName:"dispTmplCornerSeq",	Width:30,	Align:"Center",	Edit:0}
			  , {Header:"코너명",		Type:"Text",	SaveName:"cornerName",			Width:60,	Align:"Center",	Edit:0,	FontUnderline:1,	Cursor:"Pointer"}
			  , {Header:"전시대상",		Type:"Text",	SaveName:"cornerSetName",		Width:60,	Align:"Center",	Edit:0}
			  , {Header:"코너설명",		Type:"Text",	SaveName:"noteText",			Width:60,	Align:"Center",	Edit:0}
			  , {Header:"작성자",		Type:"Text",	SaveName:"rgsterName",			Width:30,	Align:"Center",	Edit:0}
			  , {Header:"작성일시",		Type:"Text",	SaveName:"rgstDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:"수정자",		Type:"Text",	SaveName:"moderName",			Width:30,	Align:"Center",	Edit:0}
			  , {Header:"수정일시",		Type:"Text",	SaveName:"modDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
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
			, {Header:"코너번호",		Type:"Text",	SaveName:"dispTmplCornerSeq",	Width:30,	Align:"Center",	Edit:0}
			, {Header:"코너명",		Type:"Text",	SaveName:"cornerName",			Width:60,	Align:"Center",	Edit:0,	FontUnderline:1,	Cursor:"Pointer"}
			, {Header:"전시대상",		Type:"Text",	SaveName:"cornerSetName",		Width:60,	Align:"Center",	Edit:0}
			, {Header:"코너설명",		Type:"Text",	SaveName:"noteText",			Width:60,	Align:"Center",	Edit:0}
			, {Header:"작성자",		Type:"Text",	SaveName:"rgsterName",			Width:30,	Align:"Center",	Edit:0}
			, {Header:"작성일시",		Type:"Text",	SaveName:"rgstDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"수정자",		Type:"Text",	SaveName:"moderName",			Width:30,	Align:"Center",	Edit:0}
			, {Header:"수정일시",		Type:"Text",	SaveName:"modDtm",				Width:40,	Align:"Center",	Edit:0,	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("mobileCornerSheet"), "mobileList", "100%", "370px");
		IBS_InitSheet(mobileList, initMobileCornerSheet);
		tree.FitColWidth();
	}
	
	/**
	 * 메뉴 grid action
	 */
	_contents.doActionDpContents = function(sAction){
		
		var dispTmplNo = sAction == 'pc' ? $('#pcDispTmplNo').val() : $('#mobileDispTmplNo').val();
		
		var param = { url : '/display/category/contents/list/read'
					, sheet : sAction + 'List'
					, subparam : 'dispTmplNo='+dispTmplNo+'&ctgrNo='+$('input[name=ctgrNo]').val()
		};
		
		DataSearch(param);
		
	}
	
	/**
	 * 이벤트
	 */
	_contents.event = function(){
				
		pcList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( pcList.ColSaveName(col) == "cornerName" && value != "" ) {
					var param = {
							ctgrNo : tree.GetRowData(tree.GetSelectRow()).ctgrNo,
							dispTmplCornerSeq: pcList.GetRowData(row).dispTmplCornerSeq,
							dispTmplNo: pcList.GetRowData(row).dispTmplNo,
							deviceTypeName : 'PC'
					}
					abc.biz.display.common.openCornerInfoPopup(param);
				}
			}
		}
		
		mobileList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if ( mobileList.ColSaveName(col) == "cornerName" && value != "" ) {
					var param = {
							ctgrNo : tree.GetRowData(tree.GetSelectRow()).ctgrNo,
							dispTmplCornerSeq: mobileList.GetRowData(row).dispTmplCornerSeq,
							dispTmplNo: mobileList.GetRowData(row).dispTmplNo,
							deviceTypeName : 'Mobile'
					}
					abc.biz.display.common.openCornerInfoPopup(param);
				}
			}
		}
		
		
		$('input[name=useYn]').change(function(){
			var useYn = $('input:radio[name=useYn]:checked').val();
			var dispYn = $('input:radio[name=dispYn]:checked').val();
			
			if(useYn == 'N' && dispYn == 'Y'){
				alert('전시중인 카테고리입니다.');
			}
		});
		
		//pc 미리보기 
		$('body').on('click', '#preview-pc-page', function(e){
			var ctgrNo = $('#ctgrNoArea').text();
			var url = abc.biz.display.category.data.domain.pc+'/display/category?ctgrNo='+ctgrNo;
			var date = $('#preview-pc-date').val();
			
			if(abc.text.isBlank(ctgrNo)){
				alert('카테고리를 선택해주세요');
				return false;
			}
			
			if(abc.text.isBlank(date)){
				alert('미리보기 일시를 입력해 주세요.');
				return false;
			}
			
			if(abc.text.isBlank($('#pcDispTmplNo').val())){
				alert('템플릿이 없습니다.');
				return false;
			}
			
			window.open(url+'&previewDate='+date, "미리보기", "width=1700, height=970, toolbar=no, menubar=no, scrollbars=no, resizable=yes");  
		});
		
		//모바일 미리보기
		$('body').on('click', '#preview-mobile-page', function(e){
			var ctgrNo = $('#ctgrNoArea').text();
			var url = abc.biz.display.category.data.domain.mobile+'/display/category?ctgrNo='+ctgrNo;
			var date = $('#preview-mobile-date').val();
			
			if(abc.text.isBlank(ctgrNo)){
				alert('카테고리를 선택해주세요');
				return false;
			}
			
			if(abc.text.isBlank(date)){
				alert('미리보기 일시를 입력해 주세요.');
				return false;
			}
			
			if(abc.text.isBlank($('#mobileDispTmplNo').val())){
				alert('템플릿이 없습니다.');
				return false;
			}
			
			 window.open(url+'&viewer=mobile&previewDate='+date, "미리보기", "width=450, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes");  
		});
				
	}	
	
	$(function() {
		_contents.init();
	});
	
})();