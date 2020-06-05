(function() {

	var _approval = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display.approval");
	
	/**
	 * 초기화
	 */
	_approval.init = function() {
		
		_approval.event();
		_approval.initSheet();
		
		if ($("#dateSearchS").val() == "" && $("#dateSearchE").val() == "" ){
			$("#dateSearchE").datepicker("setDate", "today");
			$("#dateSearchS").datepicker("setDate", "-1M");
		}		
		abc.biz.display.common.searchFormCalendarBtnEvent('#dateSearchS', '#dateSearchE', '.area-calendar-btn-group');
		
		abc.biz.display.common.checkBoxAll({allId: '#chkStatusAll', itemsClass: '.plndpStatCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkDeviceAll', itemsClass: '.deviceCode'});
		
		_approval.doAction('search');
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_approval.initSheet = function() {
		
		// ====================================================================================================
		// 기획전 리스트
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, "MergeSheet": msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:'', 				Type:'CheckBox',SaveName:'checked',			Width:7,	Edit:1,	Align:'Center', Sort:0}
			  , {Header:'번호', 				Type:'Seq',		SaveName:'seq',				Width:10,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'입점사명',			Type:'Text',	SaveName:'vndrNameLabel',	Width:20,	Edit:0,	Align:'Center', Hidden: this.isAdmin ? 0 : 1, Sort:0}
			  , {Header:'승인상태',			Type:'Combo',	SaveName:'plndpStatCode',	Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'기획전 ID',			Type:'Text',	SaveName:'plndpNo',			Width:18,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'기획전명',			Type:'Text',	SaveName:'plndpName',		Width:50,	Edit:0,	Align:'Left',	FontUnderline:1, Cursor:'Pointer'}
			  , {Header:'기획전\n상품관리',		Type:'Text',	SaveName:'productManage',	Width:18,	Edit:0,	Align:'Center',	FontUnderline:1, Cursor:'Pointer', DefaultValue:'상품관리', Sort:0}
			  , {Header:'디바이스\n(미리보기)',	Type:'Text',	SaveName:'deviceCodeStr',	Width:25,	Edit:0,	Hidden:1}
			  , {Header:'디바이스',			Type:'Text',	SaveName:'deviceCodeNameStr',Width:25,	Edit:0,	Hidden:1}
			  , {Header:'미리보기', 			Type:"Button",	SaveName:"pcPreview", 		Width:9,	Align:"Center", Edit:0, DefaultValue:"PC", Sort:0}
			  , {Header:'미리보기',			Type:"Button", 	SaveName:"moPreview", 		Width:18,	Align:"Center", Edit:0, DefaultValue:"MOBILE", Sort:0}
			  //, {Header:'대상회원',			Type:'Text',	SaveName:'memberTypeCodeStr',Width:40,	Edit:0,	Align:'Center'}
			  , {Header:'전시채널번호',			Type:'Text',	SaveName:'chnnlNo',			Width:18,	Edit:0,	Align:'Center', Sort:0, Hidden:1}
			  , {Header:'전시채널',			Type:'Text',	SaveName:'chnnlName',		Width:18,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'A-RT전시여부',		Type:'Combo',	SaveName:'artDispYn',		Width:20,	Edit:0,	Align:'Center',	ComboText:"예|아니오", ComboCode : "Y|N", Sort:0}
			  //, {Header:'사용여부',			Type:'Combo',	SaveName:'useYn',			Width:15,	Edit:0,	Align:'Center',	ComboText:"예|아니오", ComboCode : "Y|N"}
			  //, {Header:'전시여부',			Type:'Combo',	SaveName:'dispYn',			Width:15,	Edit:0,	Align:'Center',	ComboText:"예|아니오", ComboCode : "Y|N"}
			  , {Header:'전시유형',			Type:'Combo',	SaveName:'dispType',		Width:15,	Edit:0,	Align:'Center',	ComboText:"일반|모아보기", ComboCode : "B|S", Sort:0}
			  //, {Header:'진행상태', 		Type:'Text',	SaveName:'progress',		Width:20,	Edit:0,	Align:'Center'}
			  , {Header:'시작일',				Type:'Date',	SaveName:'plndpStartDtm',	Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'종료일',				Type:'Date',	SaveName:'plndpEndDtm',		Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'승인 요청일',			Type:'Text',	SaveName:'reqDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'작성자',				Type:'Text',	SaveName:'rgsterInfo',		Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'작성자번호',			Type:'Text',	SaveName:'rgsterNo',		Width:20,	Hidden:1}
			  , {Header:'작성일시',			Type:'Date',	SaveName:'rgstDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자',				Type:'Text',	SaveName:'moderInfo',		Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'수정자번호',			Type:'Text',	SaveName:'moderNo',			Width:20,	Hidden:1}
			  , {Header:'수정일시',			Type:'Date',	SaveName:'modDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'반려사유', 			Type:'Text',	SaveName:'returnRsnText',	Width:20,	Edit:0,	Align:'Center', Hidden: 1}
		];

		createIBSheet2(document.getElementById("dispSheet"), "list", "100%", "370px");
		IBS_InitSheet(list, initSheet);
		
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		//list.FitColWidth();
		//list.SetExtendLastCol(1);
		
		list.SetColWidth("checked", 40);
		list.SetColWidth("seq", 40);
		list.SetColWidth("vndrNameLabel", 100);
		list.SetColWidth("plndpStatCode", 80);
		list.SetColWidth("plndpNo", 80);
		list.SetColWidth("plndpName", 300);
		list.SetColWidth("productManage", 80);
		list.SetColWidth("deviceCodeNameStr", 120);
		list.SetColWidth("pcPreview", 50);
		list.SetColWidth("moPreview", 80);
		list.SetColWidth("memberTypeCodeStr", 200);
		list.SetColWidth("chnnlName", 100);
		list.SetColWidth("artDispYn", 80);
		list.SetColWidth("useYn", 80);
		list.SetColWidth("dispYn", 80);
		list.SetColWidth("dispType", 80);
		list.SetColWidth("progress", 80);
		list.SetColWidth("plndpStartDtm", 120);
		list.SetColWidth("plndpEndDtm", 120);
		list.SetColWidth("reqDtm", 120);
		list.SetColWidth("aprverDtm", 120);
		list.SetColWidth("aprverInfo", 120);
		list.SetColWidth("rgsterInfo", 120);
		list.SetColWidth("rgstDtm", 120);
		list.SetColWidth("moderInfo", 120);
		list.SetColWidth("modDtm", 120);
		
		list.InitDataCombo(0, "chnnlNo", this.chnnlCombo.text, this.chnnlCombo.code);
		list.InitDataCombo(0, "plndpStatCode", this.codeCombo.PLNDP_STAT_CODE.text, this.codeCombo.PLNDP_STAT_CODE.code);
	}
	
	/**
	 * grid action
	 */
	_approval.doAction = function(sAction){
		
		switch (sAction) {
		case 'search':
			
			var pageCount = $("#pageCount").val();
			var param = { url : '/promotion/planning-display/approval/list'
						, sheet : 'list'
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.searchForm)
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_approval.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			
			var data = list.GetRowData(row);
			if (row > 0) {
				if (list.ColSaveName(col) == 'plndpName') {
					
					location.href = '/promotion/planning-display/detail?plndpNo=' + data.plndpNo + '&pageType=A';
				}
				
				if (list.ColSaveName(col) == 'productManage') {
					
					location.href = '/promotion/planning-display/product/manage?plndpNo=' + data.plndpNo+'&prevPage=A';
				}
			}			
		}
		
		list_OnButtonClick = function(row, col) {
			var data = list.GetRowData(row);
			// 미리보기
			var url = '';			
			
			if (list.ColSaveName(col) == 'pcPreview') {					
				if (data.chnnlNo == '10001'){
					url = $('input[name=abcFoUrl]').val();
				} else if (data.chnnlNo == '10002'){
					url = $('input[name=gsFoUrl]').val();
				} else if (data.chnnlNo == '10003'){
					url = $('input[name=otsFoUrl]').val();
				} else if (data.chnnlNo == '10004'){
					url = $('input[name=kidsFoUrl]').val();
				} else {
					url = $('input[name=artFoUrl]').val();
				}
			} else if (list.ColSaveName(col) == 'moPreview') {
				if (data.chnnlNo == '10001'){
					url = $('input[name=abcMoUrl]').val();
				} else if (data.chnnlNo == '10002'){
					url = $('input[name=gsMoUrl]').val();
				} else if (data.chnnlNo == '10003'){
					url = $('input[name=otsMoUrl]').val();
				} else if (data.chnnlNo == '10004'){
					url = $('input[name=kidsMoUrl]').val();
				} else {
					url = $('input[name=artMoUrl]').val();
				}
			}
			
			if( abc.text.allNull(url) ){
				alert("도메인 미등록 채널입니다.");
				return false;
			}
			
			url += '/promotion/planning-display/detail';
			
			if (list.ColSaveName(col) == 'pcPreview') {
				window.open(url+'?isPreview=y&plndpNo='+data.plndpNo);
			} else if (list.ColSaveName(col) == 'moPreview') {
				abc.open.popup({
					url 	:	url,
					winname :	"preview",
					title 	:	"preview",
					method	: 	"get",
					width	:	490,
					height	:	1060,
					params	:	{
						isPreview : 'y',
						plndpNo : data.plndpNo,
						viewer : 'mobile'
					}
				});
			}
		}
		
		list_OnRowSearchEnd = function(row) {
			
			var data = list.GetRowData(row);
			
			if ( data.deviceCodeStr != '' ) {
				if (data.deviceCodeStr.includes('10000')) { // PC
					list.SetCellEditable(row, 'pcPreview', true);
				}
				
				if (data.deviceCodeStr.includes('10001') || data.deviceCodeStr.includes('10002')) { // MOBILE
					list.SetCellEditable(row, 'moPreview', true);
				}
			}

			if(_approval.isAdmin && data.plndpStatCode != '10001') {
				list.SetCellEditable(row, 'checked', 0);
			}
		}
		
		/** 미리보기 */
		$(document).on('click', '.preview-link', function() {
			
			var plndpNo = list.GetRowData(list.GetSelectRow()).plndpNo;
			
			var url;
			
			if ($(this).hasClass('preview-mobile')) {
				url = $('input[name=moUrl]').val() + '/promotion/planning-display/detail';
			} else if ($(this).hasClass('preview-pc')) {
				url = $('input[name=foUrl]').val() + '/promotion/planning-display/detail';
			} else if ($(this).hasClass('preview-app')) {
				url = $('input[name=moUrl]').val() + '/promotion/planning-display/detail';
			}
			
			abc.open.popup({
				url 	:	url,
				winname :	"preview",
				title 	:	"preview",
				method	: 	"get",
				params	:	{
					isPreview : 'y',
					plndpNo : plndpNo
				}
			});
			
		});
		
		/** 검색 */
		$('#searchBtn').on('click', function() {
			if($('[name=searchType]').val() == '' && $('[name=searchWord]').val() != '') {
				alert('검색어 구분값을 선택해주세요.');
				return false;
			}
			if ($('#dateSearchS').val() == '' || $('#dateSearchE').val() == '') {
				alert('기간을 모두 입력해주세요.');
				return false;
			}
			_approval.doAction('search');
		});
		
		/** 승인상태 checkbox */
		$('input[name=plndpStatCodeAll]').on('click', function() {
			if ($(this).is(':checked')) {
				$('input[name=plndpStatCodeArr]').prop('checked', true);
			} else {
				$('input[name=plndpStatCodeArr]').prop('checked', false);
			}			
		});
		$('input[name=plndpStatCodeArr]').on('click', function() {
			if ($('input[name=plndpStatCodeArr]:checked').length == $('input[name=plndpStatCodeArr]').length) {
				$('input[name=plndpStatCodeAll]').prop('checked', true);
			} else {
				$('input[name=plndpStatCodeAll]').prop('checked', false);
			}
		});
		
		/** 디바이스 checkbox */
		$('input[name=deviceCodeAll]').on('click', function() {
			if ($(this).is(':checked')) {
				$('input[name=deviceCodeArr]').prop('checked', true);
			} else {
				$('input[name=deviceCodeArr]').prop('checked', false);
			}			
		});
		$('input[name=deviceCodeArr]').on('click', function() {
			if ($('input[name=deviceCodeArr]:checked').length == $('input[name=deviceCodeArr]').length) {
				$('input[name=deviceCodeAll]').prop('checked', true);
			} else {
				$('input[name=deviceCodeAll]').prop('checked', false);
			}
		});
		
		/** 관리자 찾기 popup */
		$('#searchAdmin').on('click', function() {
			
			abc.adminSearchPopup('searchAdminCallback');
		});
		
		/** 입점사 검색 popup */
		$('#searchVendor').on('click', function() {
			
			abc.vndrSearchPopup(false, 'searchVendorCallback');
		});
		
		/** 목록개수 */
		$('#pageCount').on('change', function() {
			
			_approval.doAction('search');
		});
		
		/** 일괄반려 popup */
		$('#returnBtn').on('click', function() {
			
			var result = _approval.isAppropriate('10001');
			
			if (!result) return false;
			
			// 팝업
			var pop = abc.open.popup({
				url 	:	'/promotion/planning-display/approval/feedback/popup',
				winname :	'planning-display-feedback',
				method	: 	'get',
				title 	:	'planning-display-feedback',
				width 	:	1000,
				height	:	340
			});
		});
		
		/** PO : 선택 삭제 */
		$('#removeBtn').on('click', function() {
			
			var result = _approval.isAppropriate('10000');
			
			if (!result) return false;
			
			var url = '/promotion/planning-display/approval/remove';
			var param = { 'Col' : 'checked', 'Quest' : 0 };
			
			list.DoSave(url, param);
			
			alert('삭제했습니다.');
			
			_approval.doAction('search');
		});
		
		/** PO : 선택 승인요청 */
		$('#requestBtn').on('click', function() {
			
			var result = _approval.isAppropriate('10000');
			
			if (!result) return false;
			
			var url = '/promotion/planning-display/approval/request';
			var param = { 'Col' : 'checked', 'Quest' : 0 };
			
			list.DoSave(url, param);
			
			alert('승인을 요청했습니다.');
			
			_approval.doAction('search');
		});
		
		/** 초기화 */
		$('#clear-form').on('click', function() {			
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
			$('input[name=vndrNp]').val(''); // 입점사 초기화
			$('input[name=vndrGbnType]').val(''); // 입점사 초기화
		});
		
	}
	
	/** validation */
	_approval.isAppropriate = function(plndpStatCode) {
		var rowData = list.ExportData({'Type':'json'}).data;
		var type = plndpStatCode == '10000' ? '임시저장' : '승인요청';
		var result = true;
		
		$.each(rowData, function(i,v) {
			if(v.checked == 1) {
				if(v.plndpStatCode != plndpStatCode) {
					result = false;
				}
			}
		});

		if(!result)
			alert(type + '만 선택할 수 있습니다.');
		
		return result;
	}
	
	/** BO : 선택 일괄반려 */
	_approval.returnCallback = function(data) {
		
		var rowData = list.ExportData({'Type':'json'}).data;
		
		$.each(rowData, function(i,v) {
			if(v.checked == 1) {
				
				list.SetRowData(v.seq, { returnRsnText : data });
			}
		});
		
		var url = '/promotion/planning-display/approval/feedback';
		var param = { 'Col' : 'checked', 'Quest' : 0, 'CallBack' : function(){ _approval.doAction('search'); } };
		
		list.DoSave(url, param);
		
		alert('승인을 반려했습니다.');
	}
	
	$(function() {
		_approval.init();
	});
	
})();

/** 입점사 검색 callback */
function searchVendorCallback(data) {
	
	var vndrName = data.arrayVndrName[0];
	var vndrNo = data.arrayVndrNo[0];
	
	$('input[name=vndrName]').val(vndrName);
	$('input[name=vndrNo]').val(vndrNo);
	$('input[name=vndrGbnType]').val('V');
}