(function() {

	var _memberCouponPopup = abc.object.createNestedObject(window,"abc.biz.promotion.member.coupon.popup");
	
	/**
	 * 초기화
	 */
	_memberCouponPopup.init = function(){
		console.log('init');
		_memberCouponPopup.sheet.init();
		_memberCouponPopup.event();
		abc.biz.display.common.checkBoxAll({allId: '#chkUse01', itemsClass: '[name=cpnUseYns]'});
		abc.biz.display.common.checkBoxAll({allId: '#chkMemberTypeAll', itemsClass: '.chkMember'});
		
		_memberCouponPopup.getList('formType1');
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_memberCouponPopup.sheet = {};
	_memberCouponPopup.sheet.init = function(){
		createIBSheet2(document.getElementById("memberCouponSheet"), "memberCouponList", "100%", "370px");
		
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:$('#pageCount1').val(), DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header : "번호", Type:"Seq", SaveName:"", Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"회원ID" , Type:"Text", SaveName:"detailLoginId", Width: 35,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			, {Header :"회원명" , Type:"Text", SaveName:"detailMemberName", Width: 20,  Align:"Center", Edit:0, FontBold:1, Sort:0}
			/*, {Header :"회원등급" , Type:"Text", SaveName:"mbshpGradeCodeName", Width: 35,  Align:"Center", Edit:0}*/
			, {Header :"회원유형" , Type:"Text", SaveName:"memberTypeCodeName", Width: 35,  Align:"Center", Edit:0, Sort:0}
			, {Header :"쿠폰번호" , Type:"Text", SaveName:"cpnNo", Width: 35,  Align:"Center", Edit:0, Hidden:1, Sort:0}
			, {Header : "쿠폰 사용일", Type:"Date", SaveName:"cpnUseDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "쿠폰 발급일", Type:"Date", SaveName:"cpnIssueDtm", Width: 20, Align:"Center", Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			/*, {Header :"쿠폰사용처" , Type:"Text", SaveName:"cpnUseGbnTypeName", Width: 35,  Align:"Center", Edit:0}*/
			, {Header :"주문번호" , Type:"Text", SaveName:"orderNo", Width: 35,  Align:"Center", Edit:0, Sort:0}
			, {Header :"사용여부" , Type:"Text", SaveName:"cpnStatCodeName", Width: 35,  Align:"Center", Edit:0, Sort:0}
			, {Header :"" , Type:"Text", SaveName:"useYnVal", Width: 35,  Align:"Center", Edit:0, Hidden:1}
			, {Header :"" , Type:"Text", SaveName:"holdCpnSeq", Width: 35,  Align:"Center", Edit:0, Hidden:1}
			, {Header :"관리" , Type:"Button", SaveName:"cpnManage", Width: 35,  Align:"Center", Edit:0, Sort:0}
			,{Header : "", Type:"Text", SaveName:"memberNo", Align:"Center", 	Edit:0, Sort:0, Hidden:1}
		];
		
		IBS_InitSheet(memberCouponList , initSheet);
		memberCouponList.SetCountPosition(3);
		memberCouponList.SetPagingPosition(2);
		memberCouponList.FitColWidth();
		memberCouponList.SetExtendLastCol(1);
		
		createIBSheet2(document.getElementById("memberCouponAddSheet"), "memberCouponAddList", "100%", "370px");
		
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:$('#pageCount2').val(), DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header : "", Type:"CheckBox", SaveName:"checkMember", Width: 80, Align:"Center", 	Edit:1, Sort:0}
			,{Header : "", Type:"Text", SaveName:"memberNo", Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			,{Header : "", Type:"Date", SaveName:"validStartDtm", Align:"Center", Edit:0, Sort:0, Hidden:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			,{Header : "", Type:"Date", SaveName:"validEndDtm", Align:"Center",	Edit:0, Sort:0, Hidden:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header : "번호", Type:"Seq", SaveName:"", Width: 80, Align:"Center", 	Edit:0, Sort:0}
			, {Header :"회원ID" , Type:"Text", SaveName:"detailLoginId", Width: 240,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"회원명" , Type:"Text", SaveName:"detailMemberName", Width: 240,  Align:"Center", Edit:0, FontBold:1}
			, {Header :"회원유형" , Type:"Text", SaveName:"memberTypeCodeName", Width: 240, Align:"Center", Edit:0}
		];
		
		IBS_InitSheet(memberCouponAddList , initSheet);
		memberCouponAddList.SetCountPosition(3);
		memberCouponAddList.SetPagingPosition(2);
		memberCouponAddList.FitColWidth();
		memberCouponAddList.SetExtendLastCol(1);
		
		_memberCouponPopup.sheet.event();
		
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_memberCouponPopup.sheet.event = function(){
		memberCouponList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				var useYnVal = memberCouponList.GetCellValue(row, "useYnVal");
				if ( memberCouponList.ColSaveName(col) == "cpnManage" && useYnVal != "NONE") {
					var cpnMemberData = new Object();
					
					cpnMemberData.cpnNo = memberCouponList.GetCellValue(row, "cpnNo");
					cpnMemberData.memberNo = memberCouponList.GetCellValue(row, "memberNo");
					cpnMemberData.holdCpnSeq = memberCouponList.GetCellValue(row, "holdCpnSeq");
					cpnMemberData.useYnVal = useYnVal;
					
					if(useYnVal == 'POSSIBLE_STOP') {
						cpnMemberData.cpnStatCode = '10004'
					} else if(useYnVal == 'RE_ISSUE') {
						cpnMemberData.cpnStatCode = '10001'
					} else if(useYnVal == 'UNPAUSE') {
						cpnMemberData.cpnStatCode = '10000'
					}
					
					if(useYnVal == 'RE_ISSUE') {
						$.ajax({
							'type': 'POST',
							'url': '/promotion/coupon/member/condition',
							'data' : cpnMemberData
						}).done(function(d){
							if(d) {
								_memberCouponPopup.statusModify(cpnMemberData);
							} else {
								alert('쿠폰 발행 수량을 초과하였습니다.')	;
							}
						}).fail(function(e){
							alert(e.message);
							console.log(e);
						});
					} else {
						_memberCouponPopup.statusModify(cpnMemberData);
					}
					
				}
			}
		}
	}
	
	_memberCouponPopup.statusModify = function(cpnMemberData) {
		$.ajax({
		    'type': 'POST',
		    'url': '/promotion/coupon/status/modify',
		    'data' : cpnMemberData
		}).done(function(d){
	    	alert('저장 되었습니다.');
	    	location.reload();
		}).fail(function(e){
	    	alert(e.message);
	    	console.log(e);
		});
	}
	
	/**
	 * 이벤트
	 */
	_memberCouponPopup.event = function(){
		//검색 tab1
		$(document.forms.searchForm1).on('submit', function(e){
			_memberCouponPopup.getList('formType1');
			return false;
		});
		
		//검색 tab2
		$(document.forms.searchForm2).on('submit', function(e){
			_memberCouponPopup.getList('formType2');
			return false;
		});
		
		$('body').on('click', '.btn-file-del', function(e) {
			$('#inputFile').val('');
			$(this).closest('li').remove();
		});
		
		//excel 업로드
		$('#inputFile').on('change', function(e){
			var ext = this.files[0].name.substring(this.files[0].name.lastIndexOf('.') + 1);
			if(ext != 'xlsx' && ext != 'xls') {
				alert('등록 가능한 확장자명은 xlsx, xls입니다.');
				return;
			}
			
			if(this.files && this.files[0]){
				$('#excelName').empty();
				
				var reader = new FileReader();
				
				reader.readAsDataURL(this.files[0]);
				
				$('#excelArea').find('.addLi').remove();
				
				$('#excelArea').append($('#addMemberTemplate').html());
				$('#excelArea').find('.subject').last().text(this.files[0].name);
			}
		});
		
		//excel 업로드 데이터
		$('#excelUpload').on('click', function(e){
			$('[name=addMemberCouponForm]').find('[name=memberNos]').remove();
			if($('#excelArea').find('.subject').length == 0) {
				alert('엑셀 파일을 추가해주세요.');
			} else {
				var form = $.form(document.forms.excelForm);
				form.submit({
					'type': 'POST',
					'url': '/promotion/coupon/member/excel/list',
					'success': function(d) {
						if(d != null && d.length > 0) {
							$('#addMemberArea').empty();
							$('#checkMemberCount').text(d.length);
							$.each(d, function(i, v){
								if(v.memberName == '' || v.memberName == undefined) {
									alert('회원정보를 찾을 수 없습니다. (' + v.memberNo + ')');
									$('#addMemberArea').empty();
									return false;
								}
								
								$('#addMemberArea').append($('#addMemberTemplate').html());
								$('#addMemberArea').find('.subject').last().text(v.memberName + '(' + v.loginId + ')');
								$('[name=addMemberCouponForm]').append('<input type="hidden" name="memberNos" value="' + v.memberNo + '" />');
							});
						} else {
							alert('입력된 데이터가 없습니다.');
						}
					},
					'error': function(e){
						alert(e.message);
						console.log(e);
					}
				});
			}
		});
		
		//엑셀폼 다운로드
		$('#excelDownload').on('click', function(e){
			$('[name=excelForm]').attr('action', '/promotion/coupon/member/excel/download');
			$('[name=excelForm]').submit();
		});
		
		//쿠폰발급
		$('#addExcelCoupon').on('click', function(e){
			var form = $.form(document.forms.addMemberCouponForm);
			form.submit({
			    'type': 'POST',
			    'url': '/promotion/coupon/member/check',
			    'success': function(check) {
			    	if(check) _memberCouponPopup.memberCouponSave();
			    	else alert('회원정보를 확인해주세요.');
			    },
			    'error': function(e){
			    	alert(e.message);
			    	console.log(e);
			    }
			});
		});
		
		$('#removeAllMember').on('click', function(e){
			$('#addMemberArea').empty();
			$('#checkMemberCount').text(0);
		});
		
		$('#addMemberCoupon').on('click', function(e) {
			/*var chkCnt = memberCouponAddList.CheckedRows("checkMember");
			var sRow = memberCouponAddList.FindCheckedRow("checkMember");
			var sRowSplit = sRow.split('|');
			
			var memberNos = new Array();
			for(var i = 0; i < sRowSplit.length; i++) {
				memberNos.push(memberCouponAddList.GetCellValue(sRowSplit[i], "memberNo"));
			}
			
			if (chkCnt <= 0 ){
				alert("회원을 선택하세요.");
				return false;
			} else {
				var form = $.form(document.forms.searchForm2);
				form.submit({
				    'type': 'POST',
				    'url': '/promotion/coupon/member/save',
				    'data' : $.paramObject({memberNos : memberNos}),
				    'success': function(d) {
				    	alert('발급되었습니다.');
				    	location.reload();
				    },
				    'error': function(e){
				    	alert(e.message);
				    	console.log(e);
				    }
				});
			}*/
			
			var list = memberCouponAddList.ExportData({ Type : "json" }).data;
			//var duplCheck = true;
			$.each(list, function(i, v){
				if(v.checkMember == 1) {
					/*$('[name=memberNos]').each(function(j, x){
						if($(x).val() == v.memberNo) {
							duplCheck = false;
							return;
						}
					});*/
					//if(duplCheck) {
						$('#addMemberArea').append($('#addMemberTemplate').html());
						$('#addMemberArea').find('.subject').last().text(v.detailMemberName + '(' + v.detailLoginId + ')');
						$('[name=addMemberCouponForm]').append('<input type="hidden" name="memberNos" value="' + v.memberNo + '" />');
					//}
				}
				//duplCheck = true;
			});
			
			$('#targetMemberCount').empty();
			$('#targetMemberCount').append('발급할 회원 목록 (총 <span id="checkMemberCount">' + $('.addLi').length + '</span>건)');
		});
		
		$('.selectPageCount').on('change', function() {
			_memberCouponPopup.getList($(this).data('formType'));
		});
		
		/** 초기화 */
		$('.clear-form').on('click', function() {
			abc.biz.display.common.initFormData('searchForm1', false);
			abc.biz.display.common.initFormData('searchForm2', false);
			
			$('#chkUse01').prop('checked', true);
			$('[name=cpnUseYns]').prop('checked', true);
			$('[name=memberTypeCodes]').prop('checked', true);
		});
	}
	
	_memberCouponPopup.memberCouponSave = function() {
		var form = $.form(document.forms.addMemberCouponForm);
		
		if($('[name=memberNos]').length == 0) {
			alert('발급할 회원을 추가해주세요.');
			return false;
		}
		
		form.submit({
		    'type': 'POST',
		    'url': '/promotion/coupon/member/save',
		    'success': function(d) {
		    	alert('발급되었습니다.');
		    	location.reload();
		    },
		    'error': function(e){
		    	alert(e.message);
		    	console.log(e);
		    }
		});
	}
	
	
	/**
	 * 리스트 조회
	 */
	_memberCouponPopup.getList = function(formType){
		var form = "";
		var ibsheet = "";
		var pageCount = ""; // 한페이지내 결과 로우 갯수
		var url = "";
		if(formType == 'formType1') {
			form = document.forms.searchForm1;
			ibsheet = 'memberCouponList';
			pageCount = $('#pageCount1').val();
			url = '/promotion/coupon/member/list/read';
		} else {
			form = document.forms.searchForm2;
			ibsheet = 'memberCouponAddList';
			pageCount = $('#pageCount2').val();
			url = '/promotion/coupon/member/sub/list/read';
		}
		
		
		var param = { url : url
			, onePageRow : pageCount
			, subparam : FormQueryStringEnc(form)
			, sheet : ibsheet };
		
		DataSearchPaging(param);
	}
	
	//StringDate = YYYYMMDDHHmmss
	_memberCouponPopup.getForamtString = function(StringDate){
		var formatString = '';
		if(StringDate != '' && StringDate != null) {
			formatString = StringDate.substring(0, 4) + '.' + StringDate.substring(4, 6) + '.' + StringDate.substring(6, 8) + ' ' 
			+ StringDate.substring(8, 10) + ':' + StringDate.substring(10, 12) + ':' + StringDate.substring(12, 14);
		}
		
		return  formatString;
	}
	
	$(function() {
		_memberCouponPopup.init();
	});
	
})();