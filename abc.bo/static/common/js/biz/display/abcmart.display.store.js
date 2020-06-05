(function() {

	var _store = abc.object.createNestedObject(window,"abc.biz.display.store");
	
	/**
	 * 초기화
	 */
	_store.init = function() {

		abc.biz.display.common.checkBoxAll({allId: '#storeServiceCodeAll', itemsClass: '.storeServiceCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkStoreAll', itemsClass: '.storeTypeCode'});
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		
		if(abc.biz.display.common.setFormParameter.setParam()){
			
			var param = localStorage.getItem("param");
			var areaNo = '';
			var areaDtlSeq = '';
			
			if(param != null){
				param = $.parseJSON(param);
				
				$.each(param, function(i,v){
					if(v.name == 'areaNo') {
						areaNo = v.value;
					}else if(v.name == 'areaDtlSeq'){
						areaDtlSeq = v.value;
					}else{
						return true;
					}
				});
			}
			
			param = { 'areaNo' : areaNo };
			
			if(areaNo == '') {
				$('#areaDtlSeq').html($('<option>').text('구/군')).prop('selected', true);
				_store.initSheet();
			}else{
				$.ajax({
					type : 'post',
					url : '/display/store/area',
					data : param
				}).done(function(data) {
					$('#areaDtlSeq').html($('<option>').text('구/군')).val('');
					
					if(data.length == 0){
						$('#areaDtlSeq option').eq(0).prop('selected', true);
					}
					
					var isSelect = false;
					
					$.each(data, function(i,v) {
						if(areaDtlSeq != ''){
							if(areaDtlSeq == v.areaDtlSeq){
								isSelect = true;
							}else{
								isSelect = false;
							}
						}
						var $option = $('<option>').val(v.areaDtlSeq).text(v.areaDtlName).prop('selected', isSelect);
						$('#areaDtlSeq').append($option);
					});
					
					_store.initSheet();
				}).fail(function(e) {
					console.log("e :" + e);
					_store.initSheet();
				})	
			}
		}else{
			_store.initSheet();
		}
		
		_store.event();
		
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_store.initSheet = function() {
		
		// ====================================================================================================
		// 전시 카테고리 목록
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:0};
		initSheet.Cols = [
				{Header:'번호', 				Type:'Seq',		SaveName:'',					Width:50,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'전시여부',			Type:'Combo',	SaveName:'dispYn',				Width:50,	Edit:0,	Align:'Center',	ComboText:"예|아니오",	ComboCode : "Y|N", Sort:0}
			  , {Header:'지역구분',			Type:'Text',	SaveName:'areaName',			Width:100,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'매장형태', 			Type:'Combo',	SaveName:'storeTypeCode',		Width:50,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'매장명',		 		Type:'Text',	SaveName:'storeName',			Width:150,	Edit:0,	Align:'Center',	FontUnderline:1,	Cursor:'Pointer'}
			  , {Header:'매장ID', 			Type:'Text',	SaveName:'storeIdText',			Width:70,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'매장 전화번호', 		Type:'Text',	SaveName:'telNoText',			Width:100,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'매장 제공 서비스', 		Type:'Text',	SaveName:'storeServiceArr',		Width:280,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'영업\n시작시간',		Type:'Text',	SaveName:'businessStartTimeString',Width:80,Edit:0,	Align:'Center', Sort:0}
			  , {Header:'영업\n마감시간',		Type:'Text',	SaveName:'businessEndTimeString',Width:80,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'매장픽업\n가능여부', 	Type:'Combo',	SaveName:'pickupPsbltYn',		Width:80,	Edit:0,	Align:'Center',	ComboText:"가능|불가능",ComboCode : "Y|N", Sort:0}
			  , {Header:'임직원가 구매\n가능여부',	Type:'Combo',	SaveName:'empPriceBuyPsbltYn',	Width:80,	Edit:0,	Align:'Center',	ComboText:"가능|불가능",ComboCode : "Y|N", Sort:0}
			  , {Header:'주소',				Type:'Text',	SaveName:'fullAddr',			Width:330,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'작성자',				Type:'Text',	SaveName:'rgsterInfo',			Width:100,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'작성일시',			Type:'Date',	SaveName:'rgstDtm',				Width:140,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자',				Type:'Text',	SaveName:'moderInfo',			Width:100,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'수정일시',			Type:'Date',	SaveName:'modDtm',				Width:140,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'매장번호', 			Type:'Text',	SaveName:'storeNo',				Hidden:1,	Edit:0}
		];

		createIBSheet2(document.getElementById("storeSheet"), "list", "100%", "370px");
		IBS_InitSheet(list, initSheet);
		
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		//list.FitColWidth();
		list.SetExtendLastCol(1);
		
		list.InitDataCombo(0, "storeTypeCode", this.codeCombo.STORE_TYPE_CODE.text, this.codeCombo.STORE_TYPE_CODE.code);
		
		_store.doAction('search');
	}
	
	/**
	 * grid action
	 */
	_store.doAction = function(sAction){
		
		switch (sAction) {
		case 'search':
			
			var pageCount = $("#pageCount").val();
			var param = { url : '/display/store/list'
						, sheet : 'list'
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.searchForm)
						, CPage : +abc.biz.display.common.setFormParameter.getPageNum()	};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_store.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {			
			var data = list.GetRowData(row);
			
			if (row > 0) {				
				if (list.ColSaveName(col) == 'storeName') {	
					abc.biz.display.common.setFormParameter.setDetailMove();
					location.href = '/display/store/detail?storeNo=' + data.storeNo;
				}
			}
		}
		
		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
			
			if(list.SearchRows() > 0){
				$('#excelDownBtn').removeClass('disabled');
			} else {
				$('#excelDownBtn').addClass('disabled');
			}
		}

		// 지역구분
		$('#areaNo').on('change', function() {
			var areaNo = $(this).val();
			var param = { 'areaNo' : areaNo };
			
			if(areaNo == '') {
				$('#areaDtlSeq').html($('<option>').text('구/군')).prop('selected', true);
				return;
			}
			
			$.ajax({
				type : 'post',
				url : '/display/store/area',
				data : param
			}).done(function(data) {
				$('#areaDtlSeq').html($('<option>').text('구/군')).val('');
				
				if(data.length == 0){
					$('#areaDtlSeq option').eq(0).prop('selected', true);
				}
				
				$.each(data, function(i,v) {
					var $option = $('<option>').val(v.areaDtlSeq).text(v.areaDtlName);
					$('#areaDtlSeq').append($option);
				});				
			}).fail(function(e) {
				console.log("e :" + e);
			})	
		});
		
		// 검색
		$('#searchBtn').on('click', function() {
			abc.biz.display.common.setFormParameter.setPageNum(1);
			_store.doAction('search');
		});
		
		// 목록개수
		$('#pageCount').on('change', function() {			
			_store.doAction('search');
		});
		
		// 초기화
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();	
		});
		
		
		//오프라인 매장관리 엑셀다운로드
		$("#excelDownBtn").click(function(){
			searchForm.action = '/display/store/excel-down';
			searchForm.method = 'post';
			searchForm.submit();
		});
	}
	
	$(function() {
		_store.init();
	});
	
})();