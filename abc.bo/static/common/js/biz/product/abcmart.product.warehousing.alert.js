(function() {

	var _warehousing = abc.object.createNestedObject(window,"abc.biz.product.warehousing");
	
	/**
	 * 초기화
	 */
	_warehousing.init = function() {
		
		this.event();
		
		//일자를 한달로 기본셋팅
		if ($("#startYmd").val() == "" && $("#endYmd").val() == "" ){
			$("#endYmd").datepicker("setDate", "today");
			$("#startYmd").datepicker("setDate", "-1M");
		}
		abc.biz.display.common.searchFormCalendarBtnEvent('#startYmd', '#endYmd', '.area-calendar-btn-group');
		
		abc.biz.display.common.checkBoxAll({allId: '#chkSellStatusAll', itemsClass: '[name=sellStatCodeArr]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkProcessAll', itemsClass: '[name=wrhsAlertStatCodeArr]'});
		
		this.initSheet();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_warehousing.initSheet = function() {
		createIBSheet2(document.getElementById('alertSheet'), 'list', '100%', '370px');
		var pageCount = $('#pageCount').val();
		
		var initSheet = {};
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
				{Header:'번호', 			Type:'Seq',		SaveName:'seq',				Width:20,	Align:'Center', Hidden:1}
			  , {Header:'상태',			Type:'Status',	SaveName:'status',			Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'',				Type:'CheckBox',SaveName:'chkBox',			Width:10,	Align:'Center', Sort:0}
			  , {Header:'사이트',			Type:'Combo',	SaveName:'siteNo',			Width:20,	Align:'Center', Edit:0, Sort:0}
			  , {Header:'ID',			Type:'Text',	SaveName:'loginIdLabel',	Width:20,	Align:'Center',	Edit:0, FontUnderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:'이름',			Type:'Text',	SaveName:'memberNameLabel',	Width:20,	Align:'Center',	Edit:0, FontUnderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:'신청일시',		Type:'Date',	SaveName:'rgstDtm',			Width:30,	Align:'Center', Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'상품코드',		Type:'Text',	SaveName:'prdtNo',			Width:20,	Align:'Center',	Edit:0, FontUnderline:1, Cursor:"Pointer", Sort:0}
			  , {Header:'옵션명',			Type:'Text',	SaveName:'optnName',		Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'추가옵션명',		Type:'Text',	SaveName:'addOptn2Text',	Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'옵션전시여부',		Type:'Text',	SaveName:'useYnStr',		Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'정상가',			Type:'Int',		SaveName:'normalAmt',		Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'판매가',			Type:'Int',		SaveName:'sellAmt',			Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'할인율',			Type:'Text',	SaveName:'dscntRate',		Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'주문가능수량',		Type:'Text',	SaveName:'orderPsbltQty',	Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'처리상태',		Type:'Combo',	SaveName:'wrhsAlertStatCode',Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'처리일시',		Type:'Text',	SaveName:'wrhsAlertSendDtm',Width:30,	Align:'Center',	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'임의발송',		Type:'Button',	SaveName:'sendBtn',			Width:20,	Align:'Center',	Edit:1, DefaultValue:'임의처리', Sort:0}
			  , {Header:'주문여부',		Type:'Combo',	SaveName:'orderYn',			Width:20,	Align:'Center',	Edit:0, ComboText:'예|아니오', ComboCode : 'Y|N', Sort:0}
			  , {Header:'주문일시',		Type:'Text',	SaveName:'orderDtm',		Width:20,	Align:'Center',	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'주문번호',		Type:'Text',	SaveName:'orderNo',			Width:20,	Align:'Center',	Edit:0}
			  , {Header:'발송처',			Type:'Text',	SaveName:'stockGbnCode',	Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'결제금액',		Type:'Int',		SaveName:'pymntAmt',		Width:20,	Align:'Center',	Edit:0, Sort:0}
			  , {Header:'전시여부',		Type:'Combo',	SaveName:'dispYn',			Width:20,	Align:'Center',	Edit:0, ComboText:'전시|전시안함', ComboCode : 'Y|N', Sort:0}
			  , {Header:'매출순번',		Type:'Text',	SaveName:'salesSeq',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'회원번호',		Type:'Text',	SaveName:'memberNo',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'브랜드명',		Type:'Text',	SaveName:'brandName',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'상품명',			Type:'Text',	SaveName:'prdtName',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'관심상품순번',		Type:'Text',	SaveName:'intrstPrdtSeq',	Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'채널번호',		Type:'Text',	SaveName:'chnnlNo',			Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'카테고리번호',		Type:'Text',	SaveName:'ctgrNo',			Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'상품옵션번호',		Type:'Text',	SaveName:'prdtOptnNo',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'핸드폰번호',		Type:'Text',	SaveName:'hdphnNoText',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'입고알림신청여부',	Type:'Text',	SaveName:'wrhsAlertReqYn',	Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'입고알림발송여부',	Type:'Text',	SaveName:'wrhsAlertSendYn',	Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'회원명',			Type:'Text',	SaveName:'memberName',		Width:100,	Align:'Center',	Hidden:1,	Edit:1}
			  , {Header:'옵션전시여부',		Type:'Text',	SaveName:'useYn',			Width:100,	Align:'Center',	Hidden:1,	Edit:1}
		];
		
		IBS_InitSheet(list, initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
		list.InitDataCombo(0, "siteNo", this.siteCombo.text, this.siteCombo.code);
		list.InitDataCombo(0, "wrhsAlertStatCode", this.codeCombo.WRHS_ALERT_STAT_CODE.text, this.codeCombo.WRHS_ALERT_STAT_CODE.code);
		
		_warehousing.doAction('search');
	}
	
	/**
	 * 메뉴 grid action
	 */
	_warehousing.doAction = function(sAction){
		switch (sAction) {
		case "search":
			var pageCount = $("#pageCount").val();
			var param = { url : '/product/warehousing-alert/list'
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
	_warehousing.event = function() {
		
		list_OnRowSearchEnd = function(row) {
			// 행 데이터
			var data = list.GetRowData(row);
			
			// 재입고 알림 처리상태 != 알림등록(대기)
			if (data.wrhsAlertStatCode != '10000') {
				// 임의처리 버튼 삭제·비활성화
				list.SetRowData(row, {'sendBtn' : ''});
				list.SetCellEditable(row, 'sendBtn', 0);
				// 체크박스 삭제·비활성화
				list.InitCellProperty(row, 'chkBox', {'Type': 'Text'});
				list.SetRowData(row, {'chkBox' : ''});
				list.SetCellEditable(row, 'chkBox', 0);
			}
			
			// 발송처 combo 설정
			if (data.stockGbnCode != '') {
				var str;
				$.each(_warehousing.codeCombo.STOCK_GBN_CODE.code.split('|'), function(i,v) {
					if (v == data.stockGbnCode) {
						str = _warehousing.codeCombo.STOCK_GBN_CODE.text.split('|')[i];
					}
				});
				
				list.SetRowData(row, {'stockGbnCode' : str});
			}
			
			// 결제금액
			if (data.pymntAmt == 0) {
				list.SetRowData(row, {'pymntAmt' : ''});
			}
		}
		
		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			if (row != 0) {
				// 회원ID,회원명
				if (list.SaveNameCol('loginIdLabel') == col || list.SaveNameCol('memberNameLabel') == col) {
					var memberNo = list.GetRowData(row).memberNo;
					abc.memberDetailPopup(memberNo);
				}
				// 상품코드
				if (list.SaveNameCol('prdtNo') == col) {
					abc.readonlyProductDetailPopup({
						prdtNo : list.GetRowData(row).prdtNo
					});
				}
			}
		}
		
		list_OnButtonClick = function(row, col) {
			// 임의처리
			if (list.SaveNameCol('sendBtn') == col) {
				
				_warehousing.sendMsg('single', row);
			}
		}
		 
		
		// form 초기화
		$('#clearBtn').on('click', function() {
			$('#searchForm')[0].reset();
			$('.area-calendar-btn-group .calendar-month').trigger('click'); // 기간 검색 초기화
		});
		
		// 검색
		$('#searchBtn').on('click', function() {
			_warehousing.doAction('search');
		});
		
		// 목록개수 변경
		$('#pageCount').on('change', function() {
			_warehousing.doAction('search');
		});
		
		// 일괄처리
		$('#atOnceBtn').on('click', function() {
			_warehousing.sendMsg('multiple');
		});
		
		// 신청취소
		$('#cancelRequestBtn').on('click', function() {
			_warehousing.cancelAlert();
		});
		
	}
	
	/**
	 * 임의처리·일괄처리
	 */
	_warehousing.sendMsg = function(type, row) {
		var flag = confirm('선택한 회원에게 재입고 알림 서비스 안내 처리하시겠습니까?');
		
		if (flag) {			
			var url = '/product/warehousing-alert/send';
			
			if (type == 'single') { // 임의처리 (단일)
				if(list.GetRowData(row).useYn == abc.consts.BOOLEAN_FALSE){
					alert("해당옵션의 전시여부가 전시안함인경우 재입고 알림 처리가 불가능합니다.");
					return false;
				}
				
				$.ajax({
					type :'post',
					url : url,
					data: list.RowSaveStr(row)
				})
				.done(function(data) {
					_warehousing.doAction('search');
					alert('선택한 회원에게 재입고 알림 서비스 안내 처리가 완료되었습니다.');
				})
				.fail(function(e) {
					console.log(e)
				});				
			} else if (type == 'multiple') { // 일괄처리 (다수)
				var sFlag = false;
				
				for(var i = list.GetDataFirstRow(); i<=list.GetDataLastRow() ; i++){
					if(list.GetRowData(i).useYn == abc.consts.BOOLEAN_FALSE){
						alert("옵션의 전시여부가 전시안함인 경우가 포함되어\n재입고 알림 처리가 불가능합니다.");
						sFlag = true;
						break;
					}
				}
				if(sFlag){
					return false;
				}
				var param = { 'Col' : 'chkBox', 'CallBack' : function() {
					_warehousing.doAction('search');
					alert('선택한 회원에게 재입고 알림 서비스 안내 처리가 완료되었습니다. (00건)');
				}, 'Quest' : 0};
				console.log(param);
				list.DoSave(url, param);
			}			
			
		}
	}
	
	/**
	 * 신청취소
	 */
	_warehousing.cancelAlert = function() {
		var flag = confirm('선택한 회원의 재입고 알림 서비스 신청을 취소 처리하시겠습니까?');
		
		if (flag) {
			var url = '/product/warehousing-alert/cancel';
			var param = { 'Col' : 'chkBox', 'CallBack' : function() {
				_warehousing.doAction('search');
			}, 'Quest' : 0};
			list.DoSave(url, param);
		}
	}
	
	$(function() {
		
		_warehousing.init();
	});
	
})();