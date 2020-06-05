(function() {

	var _planning = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display");
	
	/**
	 * 초기화
	 */
	_planning.init = function() {
		
		if ($("#dateSearchS").val() == "" && $("#dateSearchE").val() == "" ){
			$("#dateSearchE").datepicker("setDate", "today");
			$("#dateSearchS").datepicker("setDate", "-1M");
		}
		abc.biz.display.common.searchFormCalendarBtnEvent('#dateSearchS', '#dateSearchE', '.area-calendar-btn-group');
		
		abc.biz.display.common.checkBoxAll({allId: '#chkTypeAll', itemsClass: '.plndpTypeCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkChannelAll', itemsClass: '.chnnlNo'});
		abc.biz.display.common.checkBoxAll({allId: '#chkMemberAll', itemsClass: '.memberTypeCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkDeviceAll', itemsClass: '.deviceCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkProgressStatusAll', itemsClass: '.progressStatus'});
		abc.biz.display.common.checkBoxAll({allId: '#chkStatusAll', itemsClass: '.plndpStatCode'});
		
		abc.biz.display.common.setFormParameter.setForm($(document.forms.searchForm));
		abc.biz.display.common.setFormParameter.setParam();
		
		_planning.event();
		_planning.initSheet();
		
		// from dashboard
		var dashboardType = abc.param.getParams().dashboardType;		
		if (!abc.text.allNull(dashboardType)) {
			if (dashboardType == 'ongoing') {				
				$('#chkStatusO').trigger('click');
				_planning.doAction('search');
			}
		} else {
			_planning.doAction('search');
		}
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_planning.initSheet = function() {
		
		// ====================================================================================================
		// 기획전 리스트
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		var isAdmin = abc.biz.promotion.planning.display.isAdmin;
		
		/*initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init', "MergeSheet": msHeaderOnly};*/
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, "MergeSheet": msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
		initSheet.Cols = [
			    {Header:"",					Type:"CheckBox",	SaveName:"checked",				Width:10,  	Edit:1, Align:"Center", Sort:0, Hidden:isAdmin ? 1 : 0}
			  ,	{Header:'번호', 				Type:'Seq',			SaveName:'seq',					Width:10,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'입점사명',			Type:'Text',		SaveName:'vndrNameLabel',		Width:40,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'승인상태',			Type:'Combo',		SaveName:'plndpStatCode',		Width:20,	Edit:0,	Align:'Center', Hidden:isAdmin ? 1 : 0, Sort:0}
			  , {Header:'기획전 ID',			Type:'Text',		SaveName:'plndpNo',				Width:25,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'기획전명',			Type:'Text',		SaveName:'plndpName',			Width:80,	Edit:0,	Align:'Left',	FontUnderline:1, Cursor:'Pointer'}
			  , {Header:'기획전\n상품관리',		Type:'Text',		SaveName:'productManage',		Width:20,	Edit:0,	Align:'Center',	FontUnderline:1, Cursor:'Pointer', DefaultValue:'상품관리', Sort:0}
			  , {Header:'디바이스\n(미리보기)',	Type:'Text',		SaveName:'deviceCodeStr',		Width:25,	Edit:0,	Hidden:1}
			  , {Header:'디바이스',			Type:'Text',		SaveName:'deviceCodeNameStr',	Width:25,	Edit:0,	Hidden:1}
			  , {Header:'미리보기', 			Type:"Button",	 	SaveName:"pcPreview", 			Width:25,	Align:"Center", Edit:0, DefaultValue:"PC", Sort:0}
			  , {Header:'미리보기',			Type:"Button", 		SaveName:"moPreview", 			Width:25,	Align:"Center", Edit:0, DefaultValue:"MOBILE", Sort:0}
			  , {Header:'대상회원',			Type:'Text',		SaveName:'memberTypeCodeStr',	Width:40,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'전시채널번호', 		Type:'Text',		SaveName:'chnnlNo',				Width:15,	Edit:0,	Align:'Center', Sort:0, Hidden:1}
			  , {Header:'전시채널', 			Type:'Text',		SaveName:'chnnlName',			Width:15,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'A-RT전시여부',		Type:'Combo',		SaveName:'artDispYn',			Width:15,	Edit:0,	Align:'Center',	ComboText:"예|아니오", ComboCode : "Y|N", Sort:0}
			  , {Header:'사용여부',			Type:'Combo',		SaveName:'useYn',				Width:15,	Edit:0,	Align:'Center',	ComboText:"예|아니오", ComboCode : "Y|N", Sort:0}
			  , {Header:'전시여부',			Type:'Combo',		SaveName:'dispYn',				Width:15,	Edit:0,	Align:'Center',	ComboText:"예|아니오", ComboCode : "Y|N", Sort:0}
			  , {Header:'전시유형',			Type:'Combo',		SaveName:'dispType',			Width:15,	Edit:0,	Align:'Center',	ComboText:"일반|모아보기", ComboCode : "B|S", Sort:0}
			  , {Header:'진행상태', 			Type:'Text',		SaveName:'progress',			Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'시작일',				Type:'Date',		SaveName:'plndpStartDtm',		Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'종료일',				Type:'Date',		SaveName:'plndpEndDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'기획전 승인일',			Type:'Date',		SaveName:'aprverDtm',			Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'승인자',				Type:'Text',		SaveName:'aprverInfo',			Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'작성자',				Type:'Text',		SaveName:'rgsterInfo',			Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'작성자번호',			Type:'Text',		SaveName:'rgsterNo',			Width:20,	Hidden:1}
			  , {Header:'작성일시',			Type:'Date',		SaveName:'rgstDtm',				Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자',				Type:'Text',		SaveName:'moderInfo',			Width:20,	Edit:0,	Align:'Center', Sort:0}
			  , {Header:'수정자번호',			Type:'Text',		SaveName:'moderNo',				Width:20,	Hidden:1}
			  , {Header:'수정일시',			Type:'Date',		SaveName:'modDtm',				Width:30,	Edit:0,	Align:'Center',	Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'임직원여부',			Type:'Text',		SaveName:'empYn',				Width:15,	Edit:0,	Hidden:1}
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
		list.SetColWidth("aprverDtm", 120);
		list.SetColWidth("aprverInfo", 120);
		list.SetColWidth("rgsterInfo", 120);
		list.SetColWidth("rgstDtm", 120);
		list.SetColWidth("moderInfo", 120);
		list.SetColWidth("modDtm", 120);
		
		list.InitDataCombo(0, "plndpStatCode", this.codeCombo.PLNDP_STAT_CODE.text, this.codeCombo.PLNDP_STAT_CODE.code);
	}
	
	/**
	 * grid action
	 */
	_planning.doAction = function(sAction){
		
		switch (sAction) {
		case 'search':
			
			var pageCount = $("#pageCount").val();
			var param = { url : '/promotion/planning-display/list'
						, sheet : 'list'
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.searchForm)
						, CPage : +abc.biz.display.common.setFormParameter.getPageNum()
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_planning.event = function(){
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			var data = list.GetRowData(row);
			
			if (row > 0) {
				if (list.ColSaveName(col) == 'plndpName') {

					abc.biz.display.common.setFormParameter.setDetailMove();
					location.href = '/promotion/planning-display/detail?plndpNo=' + data.plndpNo;
				}
				
				if (list.ColSaveName(col) == 'productManage') {

					abc.biz.display.common.setFormParameter.setDetailMove();
					location.href = '/promotion/planning-display/product/manage?plndpNo=' + data.plndpNo;
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
				window.open(url+'?isPreview=Y&plndpNo='+data.plndpNo);
			} else if (list.ColSaveName(col) == 'moPreview') {
				var param = {
						plndpNo : data.plndpNo,
						isPreview : abc.consts.BOOLEAN_TRUE,
						viewer : 'mobile'
					};
				
				abc.open.popup({
					url 	:	url,
					winname :	"preview",
					title 	:	"preview",
					method	: 	"get",
					width	:	490,
					height	:	1060,
					params	:	param
				});
			}
		}
		
		list_OnRowSearchEnd = function(row) {
			
			var data = list.GetRowData(row);
			
			if (data.empYn == 'Y') {
				list.SetRowData(row, {memberTypeCodeStr:'임직원'});
			}
			
			if ( data.deviceCodeStr != '' ) {
				if (data.deviceCodeStr.indexOf('10000') > -1) { // PC
					list.SetCellEditable(row, 'pcPreview', true);
				}
				
				if (data.deviceCodeStr.indexOf('10001') > -1 || data.deviceCodeStr.indexOf('10002') > -1) { // MOBILE
					list.SetCellEditable(row, 'moPreview', true);
				}
			}
			
			// 기획전상태코드 != 임시저장,승인반려
			if (data.plndpStatCode != '10000' && data.plndpStatCode != '10002') {
				// 체크박스 삭제·비활성화
				list.SetCellEditable(data.seq, 'checked', 0);
			}
			
		}
		
		list_OnSearchEnd = function(code, msg){
			if(code == 0) {
				abc.biz.display.common.setFormParameter.saveParam(list.GetCurrentPage());
			}
		}
		
		/** 검색 */
		$('#searchBtn').on('click', function() {
			if($('[name=searchType]').val() == '' && $('[name=searchWord]').val() != '') {
				alert('검색어 구분값을 선택해주세요.');
				return false;
			}
			abc.biz.display.common.setFormParameter.setPageNum(1);
			
			if ($('#dateSearchS').val() == '' || $('#dateSearchE').val() == '') {
				alert('기간을 모두 입력해주세요.');
				return false;
			} 
						
			_planning.doAction('search');
		});
		
		/** 입점사 검색 */
		$('#vndrSearchBtn').on('click', function() {
			
			if ($('input[name=isAdmin]').val() == 'false')
				return false;
			
			abc.vndrSearchPopup(false, 'vndrSearchCallback');
		});
		
		/** 목록개수 */
		$('#pageCount').on('change', function() {			
			_planning.doAction('search');
		});
		
		// 초기화
		$('#clear-form').on('click', function() {
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화				
		});
		
		/** 일괄반려 popup */
		$('#returnBtn').on('click', function() {
			
			var result = _planning.isAppropriate('10001');
			
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
			
			var result = _planning.isAppropriate('10000');
			
			if (!result) return false;
			
			var url = '/promotion/planning-display/approval/remove';
			var param = { 'Col' : 'checked', 'Quest' : 0 };
			
			list.DoSave(url, param);
			
			alert('삭제했습니다.');
			
			_planning.doAction('search');
		});
		
		/** PO : 선택 승인요청 */
		$('#requestBtn').on('click', function() {
			
			var result = _planning.isAppropriate('10000');
			
			if (!result) return false;
			
			var url = '/promotion/planning-display/approval/request';
			var param = { 'Col' : 'checked', 'Quest' : 0 };
			
			list.DoSave(url, param);
			
			alert('승인을 요청했습니다.');
			
			_planning.doAction('search');
		});
		
	}
	
	/** validation */
	_planning.isAppropriate = function(plndpStatCode) {
		
		var result = true;
		
		var checkedRows = list.FindCheckedRow("checked").split('|');
		if(checkedRows == "") { 
			alert("선택한 항목이 없습니다."); 
			result = false;
		}
		
		return result;
		
		/*var rowData = list.ExportData({'Type':'json'}).data;
		var type = plndpStatCode == '10000' ? '임시저장' : '승인요청';
		
		
		$.each(rowData, function(i,v) {
			if(v.checked == 1) {
				if(v.plndpStatCode != plndpStatCode) {
					result = false;
				}
			}
		});

		if(!result)
			alert(type + '만 선택할 수 있습니다.');
		
		return result;*/
	}
	
	$(function() {
		_planning.init();
	});
	
})();

/**
 * 입점사 검색 callback
 */
function vndrSearchCallback(data)  {
	var vndrName = data.arrayVndrName[0];
	var vndrNo = data.arrayVndrNo[0];
	
	$('#radioStoreTypeV').prop('checked', true);
	$('input[name=vndrWord]').val(vndrName);
	$('input[name=vndrNo').val(vndrNo);
}