(function() {

	var _couponVendor = abc.object.createNestedObject(window,"abc.biz.promotion.coupon.vendor");
	var beforeVendorlist = {};

	/**
	 * 초기화
	 */
	_couponVendor.init = function(){
		console.log('init');
		_couponVendor.sheet.init();
		_couponVendor.event();
		if($('#cpnNo').val() != "") _couponVendor.getList();
		/*if($('#memberCouponCount').val() > 0) {
			vendorlist.SetColEditable('shareRate', 0);
		}*/
	}

	/**
	 *  IBSheet 초기화
	 */
	_couponVendor.sheet = {};
	_couponVendor.sheet.init = function(){
		createIBSheet2(document.getElementById("couponVendorSheet"), "vendorlist", "100%", "370px");

		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};

		initSheet.Cols = [
			{Header : "", Type:"Status", SaveName:"status", Width: 10, Align:"Center", Edit:1, Hidden:1}
			/*, {Header:"번호", Type:"Seq", SaveName:"vendorGridSeq", Width: 15, Align:"Center", Edit:0,	 Sort:0, Hidden:1}*/
			/*, {Header : "삭제", Type:"DelCheck", SaveName:"checkVendor", Width: 10, Align:"Center", 	Edit:1, Sort:0}*/
			, {Header : "", Type:"CheckBox", SaveName:"checkVendor", Width: 10, Align:"Center", 	Edit:1}
			, {Header :"입점사코드" , Type:"Text", SaveName:"vndrNo", Width: 15,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"입점사명" , Type:"Text", SaveName:"vndrName", Width: 40,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"입점사분담률(%)" , Type:"Int", SaveName:"shareRate", Width: 30,  Align:"Center", Edit:1, MaximumValue:100}
			, {Header :"" , Type:"Text", SaveName:"editType", Width: 30,  Align:"Center", Edit:1, Hidden:1}
		];

		IBS_InitSheet(vendorlist , initSheet);
		vendorlist.FitColWidth();
		vendorlist.SetExtendLastCol(1);

	}

	/**
	 * 이벤트
	 */
	_couponVendor.event = function(){
		$('#addVendor').on('click', function(e) {
			abc.vndrSearchPopup(true, "appendVendor");
		});

		$('.vendorDelBtn').on('click', function(e) {
			var seq = '';
			seq = vendorlist.FindCheckedRow('checkVendor')
			if(seq == '') {
				alert('대상 입점사를 선택해주세요.');
				return;
			}

			if(confirm('삭제하시겠습니까?')) {
				vendorlist.RowDelete(seq);
			}
		});

		$('#shareRateBtn').on('click', function(e) {
			var shareRate = $('#shareRateVal').val();
			var list = '';

			if(shareRate == '') {
				alert('입점사 분담률을 입력해주세요.');
				return;
			}

			if(!$.isNumeric(shareRate)) {
				alert('분담율은 숫자만 입력이 가능합니다.');
				return ;
			}

			if(vendorlist.CheckedRows(1) == 0) {
				alert('대상 입점사를 선택해주세요.');
				return ;
			}

			if(+shareRate > 100) {
				alert('입점사 분담률의 최대값은 100입니다.');
				$('#shareRateVal').focus();
				return ;
			}

			list = vendorlist.ExportData({"Type":"json"}).data;
			for(var i = 0; i < list.length; i++ ) {
				if(list[i].checkVendor == 1 && list[i].editType == 1) vendorlist.SetCellValue(i + 1, 'shareRate', shareRate);
			}
		});
	}

	_couponVendor.appendVendor = function(d) {
		var sheet = eval("vendorlist");	// 시트 객체 획득

		var insertRowNumber = sheet.RowCount() + 1;
		var initialRowData = {};	// 추가될 행 데이터
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
		initialRowData = {
				"vndrNo"		: d.vndrNo,
				"vndrName"	: d.vndrName,
				"shareRate"	: d.shareRate,
				"editType" 		: 1
		};
		sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
	}

	/**
	 * 리스트 조회
	 */
	_couponVendor.getList = function(){
		var form = $.form(document.forms.frm);
		form.submit({
			'type': 'POST',
			'url': '/promotion/coupon/vendor/list/read',
			'success': function(d) {
				if(d != null && d.length > 0){
					$.each(d, function(i, v) {
						_couponVendor.appendVendor(v);
					});
				}
			},
			'error': function(e){
				alert(e.message);
				console.log(e);
			}
		});
	}

	/**
	 * 입점사 기본 정보 ajax
	 */
	_couponVendor.getBasicInfoList = function(vndrNoArr, cpnNo){
		//적용전 데이터
		//beforeVendorlist = vendorlist.ExportData({"Type":"json"}).data;
		vendorlist.RemoveAll();
		$.ajax({
            type :'get',
            data : {vndrNoList : vndrNoArr, cpnNo : cpnNo},
            url:'/promotion/coupon/vendor/info/list/read'
        }).done(function(d){
        	if(d != null && d.length > 0){
				$.each(d, function(i, v) {
					_couponVendor.appendVendor(v);
				});
			}

        	//다운로드한 쿠폰에대해서 이미 등록되어잇는 입점사 분담률은 변경 불가하게했었으나 풀어놓음 - 주석처리함(현업요청)
        	/*var afterVendorlist = vendorlist.ExportData({"Type":"json"}).data;

        	$.each(afterVendorlist, function(i, v){
        		var matchCheck = false;
        		var matchShareRate = '';
				$.each(beforeVendorlist, function(j, x){
					if(v.vndrNo == x.vndrNo) {
						matchCheck = true;
						matchShareRate = x.shareRate;
						return false;
					}
				});
				if(matchCheck) {
					vendorlist.SetCellValue(i + 1, 'shareRate', matchShareRate);
					vendorlist.SetCellValue(i + 1, 'editType', 0);
				} else {
					vendorlist.SetCellEditable(i + 1, 'shareRate', 1);
					vendorlist.SetCellValue(i + 1, 'editType', 1);
				}
			});*/
        }).fail(function(e){
        	alert(e.message);
	    	console.log(e);
        });
	}

	$(function() {
		_couponVendor.init();
	});

})();

//입점사 pop - callback
function appendVendor(d)  {
	var sheet = eval("vendorlist");	// 시트 객체 획득

	var insertRowNumber = "";
	var initialRowData = {};	// 추가될 행 데이터
	var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정

	$.each(d.arrayVndrNo, function(i, v) {
		var duplCheck = true;
		var list = vendorlist.ExportData({"Type":"json"}).data;

		$.each(list, function(j, x) {
			if(v == x.vndrNo) {
				duplCheck = false;
				return false;
			}
		});
		
		if(duplCheck) {
			insertRowNumber = sheet.RowCount() + 1;
			initialRowData = {
					"vndrNo"	: d.arrayVndrNo[i],
					"vndrName"	: d.arrayVndrName[i],
					"editType"	: 1,
					"shareRate" : d.shareRate == 0 ? "" : d.shareRate
			};
			sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
		}
	});
}