(function() {

	var _search = abc.object.createNestedObject(window,"abc.biz.display.search");
	
	/**
	 * 2020.02.07 : 인기검색어 / 추천검색어 배열 추가 (순위변경 시 검색어중복체크를 탈지안탈지 확인하기 위해)
	 */
	_search.hotSearchWordArray = new Array();
	_search.suggestionSearchWordArray = new Array();
	
	/**
	 * 초기화
	 */
	_search.init = function() {
		
		_search.event();
		_search.initSheet('base');
		_search.initSheet('hot');
		_search.initSheet('suggestion');
		_search.initSheet('channel');
		_search.initSubSheet();		
		_search.doAction('base');
		
		_search.hotSearchWordArray = new Array();
		_search.suggestionSearchWordArray = new Array();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_search.initSheet = function(type) {
		
		var initSheet = {};
		var pageCount = $('#'+type+'PageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initSheet.Cols = _search.getColsData(type);

		createIBSheet2(document.getElementById(type+'Sheet'), type+'List', '100%', '370px');
		
		var typeList = type == 'base' ? baseList : type == 'hot' ? hotList : type == 'suggestion' ? suggestionList : channelList;
		
		IBS_InitSheet(typeList, initSheet);
		
		typeList.SetCountPosition(3);
		typeList.SetPagingPosition(2);
		typeList.FitColWidth();
	}
	
	_search.getColsData = function(type){
		var srchWordGbnType = type == 'base' ? 'S' : type == 'hot' ? 'P' : type == 'suggestion' ? 'R' : 'C';
		var arr = [
				{Header:'삭제', 		Type:'CheckBox',SaveName:'delYn',			Width:15,	Edit:1,	Align:'Center', Sort:0}
			  , {Header:'순위',		Type:'Int',		SaveName:'sortSeq',			Width:15,	Edit:1,	Align:'Center', Hidden:type == 'base' ? 1 : 0, DefaultValue:0, KeyField:true}
			  , {Header:'검색어',		Type:'Text',	SaveName:'srchWordText',	Width:50,	Edit:1,	Align:'Center', KeyField:true}
			  , {Header:'연결방식',	Type:'Combo',	SaveName:'cnnctnType',		Width:20,	Edit:1,	Align:'Center',	ComboCode:'S|U', ComboText:'검색결과|URL'}
			  , {Header:'연결URL',	Type:'Text',	SaveName:'cnnctnUrl',		Width:60,	Edit:0,	Align:'Center', EditLen:200}
			  , {Header:'전시여부',	Type:'Combo',	SaveName:'dispYn',			Width:15,	Edit:1,	Align:'Center',	ComboCode:'Y|N', ComboText:'예|아니오'}
			  , {Header:'작성자',		Type:'Text',	SaveName:'rgsterInfo',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'작성일시',	Type:'Date',	SaveName:'rgstDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자',		Type:'Text',	SaveName:'moderInfo',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'수정일시',	Type:'Date',	SaveName:'modDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'status',	Type:'Status',	SaveName:'status',			Width:30,	Edit:0,	Align:'Center', Hidden:1}
			  , {Header:'검색어순번',	Type:'Text',	SaveName:'srchWordSeq',		Width:20,	Edit:0,	Align:'Center', Hidden:1}
			  , {Header:'사이트번호',	Type:'Text',	SaveName:'siteNo',			Width:20,	Edit:0,	Align:'Center', Hidden:1}
			  , {Header:'검색어구분',	Type:'Text',	SaveName:'srchWordGbnType',	Width:30,	Edit:0,	Align:'Center', Hidden:1, DefaultValue:srchWordGbnType}
		];
		
		var chnnlArr = [
				{Header:'삭제', 		Type:'CheckBox',SaveName:'delYn',			Width:15,	Edit:1,	Align:'Center', Sort:0}
			  , {Header:'채널',		Type:'Text',	SaveName:'chnnlName',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'검색어',		Type:'Text',	SaveName:'srchWordText',	Width:50,	Edit:1,	Align:'Center', KeyField:true}
			  , {Header:'연결방식',	Type:'Combo',	SaveName:'cnnctnType',		Width:20,	Edit:1,	Align:'Center',	ComboCode:'S|U', ComboText:'검색결과|URL',	Hidden:1}
			  , {Header:'연결URL',	Type:'Text',	SaveName:'cnnctnUrl',		Width:60,	Edit:0,	Align:'Center', EditLen:200,							Hidden:1}
			  , {Header:'전시여부',	Type:'Combo',	SaveName:'dispYn',			Width:15,	Edit:1,	Align:'Center',	ComboCode:'Y|N', ComboText:'예|아니오',	Hidden:1}
			  , {Header:'작성자',		Type:'Text',	SaveName:'rgsterInfo',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'작성일시',	Type:'Date',	SaveName:'rgstDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자',		Type:'Text',	SaveName:'moderInfo',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'수정일시',	Type:'Date',	SaveName:'modDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'status',	Type:'Status',	SaveName:'status',			Width:30,	Edit:0,	Align:'Center', Hidden:1}	
			  , {Header:'검색어순번',	Type:'Text',	SaveName:'chnnlSrchWordSeq',Width:20,	Edit:0,	Align:'Center', Hidden:1}
			  , {Header:'사이트번호',	Type:'Text',	SaveName:'siteNo',			Width:20,	Edit:0,	Align:'Center', Hidden:1}
			  , {Header:'채널번호',	Type:'Text',	SaveName:'chnnlNo',			Width:20,	Edit:0,	Align:'Center', Hidden:1}
		]
		
		if( srchWordGbnType == "C" ){
			return chnnlArr;
		} else {
			return arr;
		}
	}
	
	/**
	 *  IBSheet 초기화 (검색어 순위, 급등 검색어) : 
	 *  ************************* 추후 변경*************************
	 */
	_search.initSubSheet = function() {
		
		// 검색어 순위
		var initRankingSheet = {};
		
		initRankingSheet.Cfg = {DeferredVScroll:1, AutoFitColWidth: 'init'};
		initRankingSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initRankingSheet.Cols = [
				{Header:'선택', 		Type:'CheckBox',SaveName:'checked',			Width:15,	Edit:1,	Align:'Center'}
			  , {Header:'순위',		Type:'Int',		SaveName:'RANKING',			Width:15,	Edit:0,	Align:'Center'}
			  , {Header:'이전순위',	Type:'Int',		SaveName:'PREV_RANK',		Width:15,	Edit:0,	Align:'Center',	Hidden:1}
			  , {Header:'검색어',		Type:'Text',	SaveName:'KEYWORD',			Width:45,	Edit:0,	Align:'Center'}			  
			  , {Header:'횟수',		Type:'Text',	SaveName:'COUNT',			Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'seq',		Type:'Seq',		SaveName:'seq',				Width:20,	Edit:0,	Hidden:1}
		];

		createIBSheet2(document.getElementById('rankingSheet'), 'rankingList', '100%', '370px');
		IBS_InitSheet(rankingList, initRankingSheet);
		
		rankingList.FitColWidth();		
		
		// 급등 검색어
		var initJumpingSheet = {};
		
		initJumpingSheet.Cfg = {DeferredVScroll:1, AutoFitColWidth: 'init'};
		initJumpingSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initJumpingSheet.Cols = [
				{Header:'선택', 		Type:'CheckBox',SaveName:'checked',			Width:15,	Edit:1,	Align:'Center'}
			  , {Header:'순위',		Type:'Int',		SaveName:'RANKING',			Width:15,	Edit:0,	Align:'Center'}
			  , {Header:'이전순위',	Type:'Int',		SaveName:'PREV_RANK',		Width:15,	Edit:0,	Align:'Center',	Hidden:1}
			  , {Header:'검색어',		Type:'Text',	SaveName:'KEYWORD',			Width:45,	Edit:0,	Align:'Center'}
			  , {Header:'횟수',		Type:'Text',	SaveName:'COUNT',			Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'seq',		Type:'Seq',		SaveName:'seq',				Width:20,	Edit:0,	Hidden:1}
		];

		createIBSheet2(document.getElementById('jumpingSheet'), 'jumpingList', '100%', '370px');
		IBS_InitSheet(jumpingList, initJumpingSheet);
		
		jumpingList.FitColWidth();
	}
	
	/**
	 * get grid data
	 */
	_search.doAction = function(type){
		
		// 2020.02.07 : 그리드 조회시 Array에 저장된 검색어들 초기화
		_search.hotSearchWordArray = new Array();
		_search.suggestionSearchWordArray = new Array();
		
		var url = type == 'ranking' || type == 'jumping' ? '/display/search-word/popular/list' : '/display/search-word/list';
	
		var pageCount = $('#'+type+'PageCount').val();
		var param = { url : url
					, sheet : type+'List'
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.getElementById(type+'SearchForm'))
		};
		if (type == 'ranking' || type == 'jumping') {
			DataSearch(param);
		} else {
			DataSearchPaging(param);
		}
	}
	
	/**
	 * get grid data (채널검색어관리)
	 */
	_search.doSearch = function(){

		var pageCount = $('#channelPageCount').val();
		var param = { url : '/display/search-word/read-channel-word-list'
					, sheet : 'channelList'
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.getElementById('channelSearchForm'))
		};

		DataSearchPaging(param);
	}
	
	/**
	 * 이벤트
	 */
	_search.event = function(){
		
		baseList_OnRowSearchEnd = function(row) {			
			_search.cnnctnEvent(baseList, row, true);
		}
		
		hotList_OnRowSearchEnd = function(row) {			
			_search.cnnctnEvent(hotList, row, true);
			
			// 2020.02.06 검색하면 검색어 저장
			console.log(hotList.GetRowData(row).srchWordText);
			_search.hotSearchWordArray.push( hotList.GetRowData(row).srchWordText );
		}
		
		suggestionList_OnRowSearchEnd = function(row) {	
			_search.cnnctnEvent(suggestionList, row, true);
			
			// 2020.02.06 검색하면 검색어 저장
			console.log(suggestionList.GetRowData(row).srchWordText);
			_search.suggestionSearchWordArray.push( suggestionList.GetRowData(row).srchWordText );
		}
		
		baseList_OnChange = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {					
			if (row > 0) {
				if (baseList.ColSaveName(col) == 'cnnctnType') {
					_search.cnnctnEvent(baseList, row, false);
				}
			}
		}
		hotList_OnChange = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {					
			if (row > 0) {
				if (baseList.ColSaveName(col) == 'cnnctnType') {
					_search.cnnctnEvent(hotList, row, false);
				}
			}
		}
		suggestionList_OnChange = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {					
			if (row > 0) {
				if (baseList.ColSaveName(col) == 'cnnctnType') {
					_search.cnnctnEvent(suggestionList, row, false);
				}
			}
		}
		
		// 급등 검색어
		jumpingList_OnSearchEnd = function() {
			
			var data = jumpingList.ExportData({"Type":"json"}).data;
			var fluc;
			var index = 1;
			
			$.each(data, function(i,v) {
				
				var fluc = v.PREV_RANK - v.RANKING;
				
				// 검색어 순위기 3위 이상 상승한 경우만 노출
				if (fluc <= 3) {
					jumpingList.RowDelete(index);
				} else {
					index++;
				}				
			});
		}		
		
		/************* S : 그리드 저장 후 이벤트 *****************************/
		
		baseList_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response) {
			
			if(Code == 0){
				alert("변경사항을 저장하였습니다.");
				_search.doAction('base');
			}else{
				alert("변경사항 저장을 실패하였습니다.");
			}	
		}
		
		hotList_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response) {

			if(Code == 0){
				alert("변경사항을 저장하였습니다.");
				_search.doAction('hot');
			}else{
				alert("변경사항 저장을 실패하였습니다.");
			}	
		}
		
		suggestionList_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response) {

			if(Code == 0){
				alert("변경사항을 저장하였습니다.");
				_search.doAction('suggestion');
			}else{
				alert("변경사항 저장을 실패하였습니다.");
			}	
		}
		
		channelList_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response) {
			
			if(Code == 0){
				alert("변경사항을 저장하였습니다.");
				_search.doSearch();
			}else{
				alert("변경사항 저장을 실패하였습니다.");
			}	
		}
		
		/************* E : 그리드 저장 후 이벤트 *****************************/
		
		/**
		 * 검색
		 */
		$('.searchBtn').on('click', function() {
			
			var type = $(this).hasClass('baseType') ? 'base' : $(this).hasClass('hotType') ? 'hot' :  $(this).hasClass('suggestionType')? 'suggestion' : 'channel';
			
			if( type == 'channel' ){
				_search.doSearch();
			} else{
				_search.doAction(type);
			}
		});
		
		/**
		 * 목록개수
		 */
		$('.pageCount').on('change', function() {
			
			var type = $(this).hasClass('baseType') ? 'base' : $(this).hasClass('hotType') ? 'hot' : $(this).hasClass('suggestionType')? 'suggestion' : 'channel';
			
			if( type == 'channel' ){
				_search.doSearch();
			} else{
				_search.doAction(type);
			}
		});
		
		/**
		 * 히스토리 보기
		 */
		$('.historyBtn').on('click', function() {
			
			var type = $(this).hasClass('baseType') ? 'base' : $(this).hasClass('hotType') ? 'hot' : 'suggestion';
			
			var params = {
					'siteNo' : $('.'+type+'SiteNo:checked').val(),
					'siteName' : $('.'+type+'SiteNo:checked').data('siteName'),
					'srchWordGbnType' : $('#'+type+'GbnType').val()
			}
			
			var pop = abc.open.popup({
				url 	:	'/display/search-word/history',
				winname :	'history-pop',
				method	: 	'get',
				title 	:	'history-pop',
				width 	:	800,
				height	:	585,
				params	:	params
			});
		});
		
		/**
		 * 히스토리 보기 (채널검색어관리)
		 */
		$('.channelHistoryBtn').on('click', function() {
			// TODO 
		});
		
		/**
		 * 행 추가
		 */
		$('.insertBtn').on('click', function() {
			
			var type = $(this).hasClass('baseType') ? [baseList, 'base'] : $(this).hasClass('hotType') ? [hotList, 'hot'] : $(this).hasClass('suggestionType') ? [suggestionList, 'suggestion'] : [channelList, 'channel'];
			
			_search.dataInsert(type);
		});
		
		/**
		 * 저장
		 */
		$('.saveBtn').on('click', function() {
			
			var type = $(this).hasClass('baseType') ? [baseList, 'base'] : $(this).hasClass('hotType') ? [hotList, 'hot'] : [suggestionList, 'suggestion'];
			
			_search.save(type);
		});
		
		/**
		 * 저장 (채널검색어관리)
		 */
		$('.channelSaveBtn').on('click', function() {
			_search.channelWordSave();
		});
		
		/**
		 * 인기 검색어 추가
		 */
		$('#addBtn').on('click', function() {
			
			if ($('input[name=poolType]').val() == 'R')
				_search.add(rankingList);
			else 
				_search.add(jumpingList);
		});
		
		/**
		 * tab 이동
		 */
		$('#hotTab').on('click', function() {
			
			// 인기검색어 조회
			_search.doAction('hot');
			
			// 검색어순위,급등검색어 조회
			_search.doAction('ranking');
			_search.doAction('jumping');
		});
		$('#suggestionTab').on('click', function() {
			_search.doAction('suggestion');
		});
		$('#channelTab').on('click', function() {
			_search.doSearch();
		});
		
		$('#rankingTab').on('click', function() {
			
			$('input[name=poolType]').val('R');
		});		
		$('#jumpingTab').on('click', function() {
			
			$('input[name=poolType]').val('J');
		});
		
		/**
		 * 초기화
		 */
		$('.clearBtn').on('click', function() {
			
			var type = $(this).hasClass('baseType') ? 'base' : $(this).hasClass('hotType') ? 'hot' : $(this).hasClass('suggestionType') ? 'suggestion' : 'channel';			
			$('#'+type+'SearchForm')[0].reset();
		});
		
		//삭제
		$('.btn-del').on('click', function(){
			
			var type = $(this).hasClass('baseType') ? [baseList, 'base'] : $(this).hasClass('hotType') ? [hotList, 'hot'] : $(this).hasClass('suggestionType') ? [suggestionList, 'suggestion'] : [channelList, 'channel'];
			var data = type[0].ExportData({'Type':'json'}).data;
			var isAnyUpdate = false;
			
			$.each(data, function(i,v) {
				if(v.delYn == 1 && v.status == 'U') {
					type[0].RowDelete(v);
				}
				if(v.status != 'R') {
					isAnyUpdate = true;
				}
			});
			console.log(data);
			
			if (!isAnyUpdate) {
				alert('삭제할 검색어를 선택해주세요.');
				return false;
			}
			
			var flag = confirm('삭제하시겠습니까?');
			
			if (flag) {
				var url = "";
				
				if( type[1] == 'channel' ){
					url = '/display/search-word/save-channel-word';
				}else{
					url = '/display/search-word/save';
				}
				
				type[0].DoAllSave(url, {CallBack  : function(){
					
					if( type[1] == 'channel' ){
						_search.doSearch();
					}else{
						_search.doAction(type[1]);
					}
				}});
			}
		});
		
	}
	
	/**
	 * 행추가
	 */
	_search.dataInsert = function(type) {
		
		var lastIdx = type[0].RowCount() > 0 ? type[0].GetColMaxValue('sortSeq') : 0;
		
		var idx = type[0].DataInsert(type[0].RowCount()+1);
		var siteNo = $('.'+type[1]+'SiteNo:checked').val();
		
		var chnnlNo;
		if( type[1] == 'channel' ){
			chnnlNo = $("input[name='chnnlNo']:checked").val();
			console.log(chnnlNo);
			type[0].SetRowData(idx, {chnnlNo:chnnlNo});
		}
		
		type[0].SetRowData(idx, {siteNo:siteNo});
		
		if (type[1] != 'base') {
			type[0].SetRowData(idx, {sortSeq:lastIdx+1});
		}
	}
	
	/**
	 * 저장
	 */
	_search.save = function(type) {
		
		var flag = confirm('변경사항을 저장하시겠습니까?');
		
		if (flag) {
			// TODO: sort_seq가 들어가는 인기 검색어 관리 || 추천 검색어 관리
			var gridType = type[1];
			console.log(gridType);
			if( gridType == 'hot' ){
				// 인기 검색어 관리라면
				
				// 검색어 중복체크를 할지여부
				var checkDuplicateYn = "N";
				
				for(var i=1; i<=hotList.RowCount(); i++){
					var status = hotList.GetCellValue(i, "status");
					
					if( status == "U" ){
						// 그리드 중 업데이트 하는 항목이 있다면
						var srchWordText = hotList.GetCellValue(i, "srchWordText");
						// search마다 그리드의 검색어들을 저장하는 Array의 항목과 비교
						if( srchWordText != _search.hotSearchWordArray[i-1] ){
							checkDuplicateYn = "Y";
						} else {
							checkDuplicateYn = "N"
						}
					}
				}
				
				if( checkDuplicateYn == "N" ){
					var url = '/display/search-word/save';
					hotList.DoAllSave(url, {CallBack  : function(){
						_search.doAction('hot');
					}});
				}else{
					_search.checkDuplicateSearchWord(type);
				}
				
			} else if( gridType == 'suggestion' ){
				
				// 인기 검색어 관리라면
				
				// 검색어 중복체크를 할지여부
				var checkDuplicateYn = "N";
				
				for(var i=1; i<=suggestionList.RowCount(); i++){
					var status = suggestionList.GetCellValue(i, "status");
					
					if( status == "U" ){
						// 그리드 중 업데이트 하는 항목이 있다면
						var srchWordText = suggestionList.GetCellValue(i, "srchWordText");
						// search마다 그리드의 검색어들을 저장하는 Array의 항목과 비교
						if( srchWordText != _search.suggestionSearchWordArray[i-1] ){
							checkDuplicateYn = "Y";
						} else {
							checkDuplicateYn = "N"
						}
					}
				}
				
				if( checkDuplicateYn == "N" ){
					var url = '/display/search-word/save';
					suggestionList.DoAllSave(url, {CallBack  : function(){
						_search.doAction('suggestion');
					}});
				}else{
					_search.checkDuplicateSearchWord(type);
				}
				
			} else if( gridType == 'base' ){
				_search.checkDuplicateSearchWord(type);
			}
		}		
	}

	/**
	 * 검색어 중복 체크
	 */
	_search.checkDuplicateSearchWord = function(type){
		
		var gridType = type[1];
		var resultBoolean = true;
		var formData;
		
		if( gridType == 'base' ){	
			// 검색창 검색어 관리
			formData = $("#baseSearchForm").serialize()
		} else if( gridType == 'hot' ){
			// 인기 검색어 관리
			formData = $("#hotSearchForm").serialize()
		} else if( gridType == 'suggestion' ){
			// 추천 검색어 관리
			formData = $("#suggestionSearchForm").serialize()
		}
		
		$.ajax({
			type :"post",
			url : "/display/search-word/read-all-search-word-list",
			data : formData
		})
		.done(function(data){
			
			if( gridType == 'base' ){
				for(var i=1; i<=baseList.GetDataLastRow(); i++){
					var status = baseList.GetCellValue(i, "status");
					var srchWordText = baseList.GetCellValue(i, "srchWordText");
					
					if( status == "U" || status == "I"){
						resultBoolean = _search.returnDuplicateBoolean(srchWordText, data.content, gridType);
					}
				}
				
			} else if( gridType == 'hot' ){
				for(var i=1; i<=hotList.GetDataLastRow(); i++){
					var status = hotList.GetCellValue(i, "status");
					var srchWordText = hotList.GetCellValue(i, "srchWordText");
					
					if( status == "U" || status == "I"){
						resultBoolean = _search.returnDuplicateBoolean(srchWordText, data.content, gridType);
					}
				}
				
			} else if( gridType == 'suggestion' ){
				for(var i=1; i<=suggestionList.GetDataLastRow(); i++){
					var status = suggestionList.GetCellValue(i, "status");
					var srchWordText = suggestionList.GetCellValue(i, "srchWordText");
					
					if( status == "U" || status == "I"){
						resultBoolean = _search.returnDuplicateBoolean(srchWordText, data.content, gridType);
					}
				}
			} 
			
			if(resultBoolean){
				var url = '/display/search-word/save';
				type[0].DoAllSave(url, {CallBack  : function(){
					_search.doAction(type[1]);
				}});
			} else{
				_search.doAction(type[1]);
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			
		});
	}
	
	/**
	 * 채널 검색어 변경사항 저장
	 */
	_search.channelWordSave = function(type) {
		
		var flag = confirm('변경사항을 저장하시겠습니까?');
		
		if (flag) {
			_search.checkDuplicateChannelSearchWord();
		}		
	}
	
	_search.checkDuplicateChannelSearchWord = function() {
		
		var resultBoolean = true;
		
		$.ajax({
			type :"post",
			url : "/display/search-word/read-all-channel-search-word-list",
			data : $("#channelSearchForm").serialize()
		})
		.done(function(data){
			console.log(data)
			for(var i=1; i<=channelList.GetDataLastRow(); i++){
				var status = channelList.GetCellValue(i, "status");
				var srchWordText = channelList.GetCellValue(i, "srchWordText");
				
				if( status == "U" || status == "I"){
					resultBoolean = _search.returnDuplicateBoolean(srchWordText, data.content, 'channel');
				}
			}
			
			if(resultBoolean){
				var url = '/display/search-word/save-channel-word';
				channelList.DoAllSave(url, {CallBack  : function(){
					_search.doSearch();
				}});
			} else{
				_search.doSearch();
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			resultBoolean = false;
		});
	}
	
	_search.returnDuplicateBoolean = function(srchWordText, arr, type) {
		console.log(srchWordText);
        for (var i = 0; i < arr.length; i++) {
        	console.log(arr[i])
        	if( arr[i].srchWordText == srchWordText){
        		if( type == 'base' ){
        			alert( "'" + srchWordText + "'은(는) 이미 등록된 검색창 검색어 입니다." );
        			_search.doAction('base');
            		return false;
        		} else if( type == 'hot' ){
        			alert( "'" + srchWordText + "'은(는) 이미 등록된 인기 검색어 입니다." );
        			_search.doAction('hot');
            		return false;
        		} else if( type == 'suggestion' ){
        			alert( "'" + srchWordText + "'은(는) 이미 등록된 추천 검색어 입니다." );
        			_search.doAction('suggestion');
            		return false;
        		} else if( type == 'channel' ){
        			alert( "'" + srchWordText + "'은(는) 채널 " + arr[i].chnnlName + "에 이미 등록된 채널 검색어 입니다." );
        			_search.doSearch();
        			return false;
        		}
        	}
        }

        return true;
    }
	
	/**
	 * 연결방식 onchange
	 */
	_search.cnnctnEvent = function(typeList, row, isSearching) {
		
		var data = typeList.GetRowData(row);
		var cnnctnType = data.cnnctnType;
		
		if (cnnctnType == 'U') { // 연결방식 == URL
			typeList.SetCellEditable(row, 'cnnctnUrl', 1);
		} else if (cnnctnType == 'S') { // 연결방식 == 검색결과
			if (!isSearching)
				typeList.SetRowData(row, {'cnnctnUrl':''});		
			typeList.SetCellEditable(row, 'cnnctnUrl', 0);
		}
	}
	
	/**
	 * 인기 검색어 추가
	 */
	_search.add = function(typeList) {
		
		var data = typeList.ExportData({'Type':'json'}).data;
		var result = [];
		var isAnyChecked = false;
		
		$.each(data, function(i,v) {
			
			if (v.checked == 1) {
				isAnyChecked = true;
				result.push(v);
			}
		});
		
		if (!isAnyChecked) {
			alert('추가할 검색어를 선택해주세요');
			return false;
		}
		
		alert(result.length + '개 검색어를 추가했습니다.');
		
		var maxRow = hotList.RowCount();
		$.each(result, function(i,v) {
			
			maxSortSeq = hotList.RowCount() > 0 ? hotList.GetColMaxValue('sortSeq') : 0;
			
			var newIdx = hotList.DataInsert(++maxRow);
			
			hotList.SetRowData(newIdx, {
				'sortSeq' : ++maxSortSeq,
				'srchWordText' : v.KEYWORD,
				'siteNo' : $('.hotSiteNo:checked').val(),
				'srchWordGbnType' : 'P'
			});
		});
	}
	
	$(function() {
		_search.init();
	});
	
})();