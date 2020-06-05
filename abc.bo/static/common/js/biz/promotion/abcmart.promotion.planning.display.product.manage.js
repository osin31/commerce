(function() {

	var _manage = abc.object.createNestedObject(window,"abc.biz.promotion.planning.display.manage");
	
	/**
	 * 초기화
	 */
	_manage.data = {
			fileInputs : {}
	}
	_manage.init = function() {
		
		_manage.event();
		_manage.initSheet();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_manage.initSheet = function() {
		
		if (!_manage.data.isInsert) {
			// ====================================================================================================
			// 기획전 상품 리스트
			var initProductSheet = {};
			var productPageCount = $('#productPageCount').val();		
			
			initProductSheet.Cfg = {SearchMode:smServerPaging2, Page:productPageCount, DeferredVScroll:1, AutoFitColWidth: 'init', "MergeSheet": msHeaderOnly};
			initProductSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
			initProductSheet.Cols = [
					{Header:'',					Type:'CheckBox',SaveName:'checked',					Width:10,	Edit:1,	Align:'Center'}
				  , {Header:'',					Type:'Seq',		SaveName:'seq',						Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'상태',				Type:'Status',	SaveName:'status',					Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'노출순서',			Type:'Int',		SaveName:'sortSeq',					Width:15,	Edit:1,	Align:'Center',	KeyField:true}
				  , {Header:'온라인상품코드',		Type:'Text',	SaveName:'prdtNo',					Width:15,	Edit:0,	Align:'Center'}
				  , {Header:'브랜드', 			Type:'Text',	SaveName:'brandName',				Width:15,	Edit:0,	Align:'Center'}
				  , {Header:'상품이미지',			Type:'Image',	SaveName:'titleImageUrl',			Width:20,	Edit:0,	Align:'Center', ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png"}
				  , {Header:'상품명',				Type:'Text',	SaveName:'prdtName',				Width:50,	Edit:0,	Align:'Center'}
				  , {Header:'스타일',				Type:'Text',	SaveName:'styleInfo',				Width:20,	Edit:0,	Align:'Center'}
				  , {Header:'컬러',				Type:'Text',	SaveName:'prdtColorInfo',			Width:20,	Edit:0,	Align:'Center'}
				  
				  , {Header:"정상가",				Type:"Text",	SaveName:"normalAmt",				Width: 15,	Align:"Center", Edit:0}
				  , {Header:"판매가",				Type:"Text",	SaveName:"sellAmt",					Width: 15,	Align:"Center", Edit:0}
				  , {Header:"할인율",				Type:"Int",		SaveName:"onlnDscntRate",			Width: 10,	Align:"Center", Edit:0}
				  , {Header:"주문가능수량",		Type:"Text",	SaveName:"orderPsbltQty",			Width: 10,	Align:"Center", Edit:0}
				  , {Header:"가용률",				Type:"Text",	SaveName:"availabilityRate",		Width: 10,	Align:"Center", Edit:0}
				  , {Header:"판매중인옵션갯수",	Type:"Int",		SaveName:"useYnQty",				Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 }
				  , {Header:"전체옵션갯수",		Type:"Int",		SaveName:"totalOptionCount",		Width: 0,	Align:"Center",	Edit:0, Sort:0, Cursor:"Pointer", Hidden:1 }
				  
				  , {Header:'자사/입점 구분',		Type:'Combo',	SaveName:'mmnyPrdtYn',				Width:15,	Edit:0,	Align:'Center', ComboText:"자사|입점사", ComboCode : "Y|N"}
				  , {Header:'전시채널', 			Type:'Combo',	SaveName:'chnnlNo',					Width:15,	Edit:0,	Align:'Center'}
				  , {Header:'전시카테고리', 		Type:'Text',	SaveName:'stdCtgrName',				Width:40,	Edit:0,	Align:'Center'}
				  , {Header:'판매상태',			Type:'Combo',	SaveName:'sellStatCode',			Width:15,	Edit:0,	Align:'Center'}
				  , {Header:'전시상태',			Type:'Combo',	SaveName:'dispYn',					Width:15,	Edit:0,	Align:'Center',	ComboText:"전시|전시안함", ComboCode : "Y|N"}
				  , {Header:'대상 프로모션명',		Type:'Text',	SaveName:'appliedPromotionInfo',	Width:35,	Edit:0,	Align:'Center'}
				  , {Header:'기획전번호',			Type:'Text',	SaveName:'plndpNo',					Width:15,	Edit:0,	Align:'Center', Hidden:1}
			];
			
			createIBSheet2(document.getElementById('productSheet'), 'productList', '100%', '370px');
			IBS_InitSheet(productList, initProductSheet);
			
			productList.SetCountPosition(3);
			productList.SetPagingPosition(2);
			productList.FitColWidth();
			
			productList.InitDataCombo(0, "sellStatCode", this.codeCombo.SELL_STAT_CODE.text, this.codeCombo.SELL_STAT_CODE.code);
			productList.InitDataCombo(0, "chnnlNo", this.chnnlCombo.text, this.chnnlCombo.code);
		
			_manage.doAction('product');
		
			// ====================================================================================================
			// 기획전 코너 리스트
			var initCornerSheet = {};
			var cornerPageCount = $('#cornerPageCount').val();
			
			initCornerSheet.Cfg = {SearchMode:smServerPaging2, Page:cornerPageCount, DeferredVScroll:1, AutoFitColWidth: 'init', "MergeSheet": msHeaderOnly};
			initCornerSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
			initCornerSheet.Cols = [
					{Header:'', 			Type:'CheckBox',SaveName:'checked',				Width:10,	Edit:1,	Align:'Center'}
				  , {Header:'', 			Type:'Seq',		SaveName:'seq',					Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'상태',			Type:'Status',	SaveName:'status',				Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'노출순서',			Type:'Int',		SaveName:'sortSeq',				Width:10,	Edit:1,	Align:'Center', KeyField:true}
				  , {Header:'소제목',			Type:'Image',	SaveName:'imageUrl',			Width:10,	Edit:1,	Align:'Center', ImgHeight: 50}
				  , {Header:'소제목',			Type:'Text',	SaveName:'plndpCornerName',		Width:50,	Edit:0,	Align:'Center',	FontUnderline:1,	Cursor:'Pointer'}
				  , {Header:'PC 템플릿',		Type:'Combo',	SaveName:'pcDispTypeCode',		Width:20,	Edit:0,	Align:'Center'}
				  , {Header:'Mobile 템플릿',	Type:'Combo',	SaveName:'mobileDispTypeCode',	Width:20,	Edit:0,	Align:'Center'}
				  , {Header:'사용여부',			Type:'Combo',	SaveName:'useYn',				Width:10,	Edit:0,	Align:'Center',	ComboText:'예|아니오',	ComboCode : 'Y|N'}
				  , {Header:'상품수', 			Type:'Text',	SaveName:'prdtCount',			Width:10,	Edit:0,	Align:'Center'}
				  , {Header:'관리', 			Type:'Button',	SaveName:'prdtManagement',		Width:30,	Edit:1,	Align:'Center', DefaultValue:'대상 상품관리'}
				  , {Header:'기획전번호',		Type:'Text',	SaveName:'plndpNo',				Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'기획전코너순번',		Type:'Text',	SaveName:'plndpCornerSeq',		Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'코너설명',			Type:'Text',	SaveName:'cornerDescText',		Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'전시코너명 노출 유형',	Type:'Text',	SaveName:'cornerNameDispType',	Width:10,	Edit:1,	Align:'Center', Hidden:1}
				  , {Header:'이미지 대체 텍스트',	Type:'Text',	SaveName:'altrnText',			Width:10,	Edit:1,	Align:'Center', Hidden:1}			  
				  , {Header:'이미지 경로',		Type:'Text',	SaveName:'imagePathText',		Width:10,	Edit:1,	Align:'Center', Hidden:1}
			];
	
			createIBSheet2(document.getElementById('cornerSheet'), 'cornerList', '100%', '370px');
			IBS_InitSheet(cornerList, initCornerSheet);
			
			cornerList.SetCountPosition(3);
			cornerList.SetPagingPosition(2);
			cornerList.FitColWidth();
			
			cornerList.InitDataCombo(0, "pcDispTypeCode", this.codeCombo.PC_DISP_TYPE_CODE.text, this.codeCombo.PC_DISP_TYPE_CODE.code);
			cornerList.InitDataCombo(0, "mobileDispTypeCode", this.codeCombo.MOBILE_DISP_TYPE_CODE.text, this.codeCombo.MOBILE_DISP_TYPE_CODE.code);
					
			_manage.doAction('corner');
		}
	}
	
	/**
	 * grid action
	 */
	_manage.doAction = function(sAction){
		
		switch (sAction) {
		case 'product':
			
			var pageCount = $("#productPageCount").val();
			var param = { url : '/promotion/planning-display/product/list'
						, sheet : 'productList'
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.saveForm)
			};
			DataSearchPaging(param);
			break;
		case 'corner':
			
			var pageCount = $("#cornerPageCount").val();
			var param = { url : '/promotion/planning-display/corner/list'
						, sheet : 'cornerList'
						, onePageRow : pageCount
						, subparam : FormQueryStringEnc(document.forms.saveForm)
			};
			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 이벤트
	 */
	_manage.event = function(){
		
		cornerList_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype) {
			
			if (row > 0) {
				if (cornerList.ColSaveName(col) == 'plndpCornerName') {
					_manage.data.selectRow = cornerList.GetSelectRow();
					
					// 팝업
					var pop = abc.open.popup({
						url 	:	'/promotion/planning-display/corner',
						winname :	'planning-display-corner',
						method	: 	'get',
						title 	:	'planning-display-corner',
						width 	:	920,
						height	:	700,
						params	:	{selectRow : cornerList.GetSelectRow()}
					});
				}
				
				if (cornerList.ColSaveName(col) == 'prdtManagement' && cornerList.GetCellEditable(row, col) > 0) {
					var data = cornerList.GetRowData(row);
					
					$('input[name=plndpCornerSeq]').val(data.plndpCornerSeq);
					
					var option = {
						    name : '기획전',    // 상품관리명
						    count : data.prdtCount,    // 대상상품전체갯수
						    searchFormId : 'saveForm',    // 조회 조건 FORM ID
						    searchUrl : '/noacl/promotion/planning-display/product-management/list',    // 조회 URI. 공통유틸에서 POST를 이용하여 통신함
						    saveAdditionalParamFormId : 'saveForm',    // 저장 시 전달 할 파라미터가 설정된 FORM ID
						    saveUrl : '/noacl/promotion/planning-display/product-management/save',    // 저장 URI. API에서 POST를 이용하여 통신함
						    isHideSortSeq : false    // 노출순서 숨김 여부
					};

					abc.targetProductManagementPopup(option);    // 대상상품관리 팝업창 호출
				}
			}
		}
		
		productList_OnRowSearchEnd = function(row) {
			productList.SetRowData(row, {
				"normalAmt"			: abc.text.isMakeComma(productList.GetRowData(row).normalAmt) + "원",
				"sellAmt"			: abc.text.isMakeComma(productList.GetRowData(row).sellAmt) + "원",
				"availabilityRate"	: Math.ceil(productList.GetRowData(row).useYnQty / productList.GetRowData(row).totalOptionCount * 100)
			});
		}
		
		/** 기획전 소제목 적용 */
		if (!_manage.data.isInsert) {
			$('input[name=cornerApplyYn]').on('click', function() {
				if ($(this).val() == 'Y') {
					$('.corner-disp-type-area').show();
					$('.corner-sheet-area').show();
					$('.product-sheet-area').hide();
				} else if ($(this).val() == 'N') {
					$('.corner-disp-type-area').hide();
					$('.corner-sheet-area').hide();
					$('.product-sheet-area').show();
				}
			});
		}
				
		/** 소제목 추가 */
		$('#cornerBtn').on('click', function(e) {
			
			e.preventDefault();
			
			var pop = abc.open.popup({
				url 	:	'/promotion/planning-display/corner',
				winname :	'planning-display-corner',
				method	: 	'get',
				title 	:	'planning-display-corner',
				width 	:	920,
				height	:	700,
				params	:	{ 'plndpNo' : $('input[name=plndpNo]').val()}
			});
		});
		
		/** 소제목 삭제 */
		$('#cornerRemoveBtn').on('click', function() {
			
			var url = '/promotion/planning-display/corner/remove'
			var param = { 'Col' : 'status', 'Quest' : '0' };
			var success = false;
			
			var rowData = cornerList.ExportData({"Type":"json"}).data;
			
			var cornerSeqArr = [];
			var delRowSeqStr = '';
			$.each(rowData, function(i,v){
				if(v.checked == 1){
					cornerSeqArr.push(v.plndpCornerSeq);
					delRowSeqStr += v.seq + '|';
				}
			});
			if(cornerSeqArr.length == 0) {
				alert('선택된 항목이 업습니다.');
				return false;
			}
			
			var flag = confirm('선택한 항목을 삭제하시겠습니까?');
			
			// 기획전 번호 추가 필요
			var plndpNo = $('input[name=plndpNo]').val();
			
			if(flag) {
				$.ajax({
					dataType : "text",
					type :"POST",
					url : "/promotion/planning-display/corner/remove",
					data : { cornerSeqArr : cornerSeqArr,
							 plndpNo : 	plndpNo	}
				})
				.done(function(){
					alert('삭제 되었습니다.');
					_manage.doAction('corner');
				})
				.fail(function(e){
					console.log(e);
				});
			} else {
				return false;
			}
		});
		
		/** 소제목 순서 저장 */
		$('#cornerSortBtn').on('click', function() {
						
			var url = '/promotion/planning-display/corner/save'; // TODO
			var param = { 'Col' : 'status', 'Quest' : '0' };
			
			var success = cornerList.DoSave(url, param);
			
			if (success) {
				alert('저장하였습니다.');
				_manage.doAction('corner');
			}
		});
		
		/** 소제목 저장 */
		$('#saveCornerBtn').on('click', function() {
			_manage.saveCorner();
			_manage.savePlndp();
		});
		
		/** 상품 추가 */
		$('#productBtn').on('click', function() {
			
			var option = {
					vndrNo : $('input[name=vndrNo]').val(),
					vndrName : $('input[name=vndrName]').val()
			}
			
			abc.productSearchPopup(true, 'abc.biz.promotion.planning.display.manage.productCallback', false, option);
		});
		
		/** 상품 삭제 */
		$('#productRemoveBtn').on('click', function() {
			
			var url = '/promotion/planning-display/product/remove';
			var param = { 'Col' : 'status', 'Quest' : '0' };
			var success = false;
			
			var rowData = productList.ExportData({"Type":"json"}).data;
			
			var prdtNoArr = [];
			var delRowSeqStr = '';
			$.each(rowData, function(i,v){
				if(v.checked == 1){
					prdtNoArr.push(v.prdtNo);
					delRowSeqStr += productList.FindText('prdtNo', v.prdtNo, 0, 0, 0) + '|';
				}
			});
			
			if(prdtNoArr.length == 0) {
				alert('선택된 항목이 업습니다.');
				return false;
			}
			
			var flag = confirm('선택한 항목을 삭제하시겠습니까?');
			
			if(flag) {
				$.ajax({
					dataType : "text",
					type :"POST",
					url : "/promotion/planning-display/product/remove",
					data : {
						prdtNoArr : prdtNoArr,
						plndpNo : $('input[name=plndpNo]').val()
					}
				})
				.done(function(){
					
					alert('삭제 되었습니다.');
					productList.RowDelete(delRowSeqStr);
				})
				.fail(function(e){
					console.log(e);
				});
			} else {
				return false;
			}
		});
		
		/** 상품 순서 저장 */
		$('#productSortBtn').on('click', function() {
			
			var url = '/promotion/planning-display/product/save';
			var param = { 'Col' : 'status', 'Quest' : '0' };
			
			var success = productList.DoSave(url, param);
			
			if (success) {
				alert('저장하였습니다.');
				_manage.doAction('product');
			}
		});
		
		/** 상품 저장 */
		$('#saveProductBtn').on('click', function() {
			_manage.saveProduct();
			_manage.savePlndp();
		});
		
		/** 목록개수 */
		$('.pageCount').on('change', function() {
			
			if ($(this).hasClass('cornerType')) {
				_manage.doAction('corner');
			} else if ($(this).hasClass('productType')) {
				_manage.doAction('product');
			}
		});
		
		/** 저장 (PO) */
		$('.btn-save-vndr').off().on('click', function() {
			if ($(this).hasClass('save-tmprly')) {
				$('input[name="plndpStatCode"]').val('10000');
			} else {
				$('input[name="plndpStatCode"]').val('10001');
			}

			var cornerApplyYn = $('input[name="cornerApplyYn"]:checked').val(); // Y 소제목 N 상품
			if(cornerApplyYn == 'Y') {
				_manage.saveCorner();
			} else {
				_manage.saveProduct();
			}
			
			_manage.savePlndp();
			
		});
		
		/** 기획전 삭제 */
		$('#deletePlndpBtn').on('click', function() {
			
			var flag = confirm('삭제하시겠습니까?');
			
			if (flag) {
				$.ajax({
					type :"POST",
					url : "/promotion/planning-display/approval/remove",
					data : { plndpNo : abc.param.getParams().plndpNo }
				})
				.done(function(){
					location.href = '/promotion/planning-display';
				})
				.fail(function(e){
					console.log(e);
				});
			}		
		});
		
		$('#listBtn').on('click', function() {
			
			if (abc.param.getParams().prevPage == 'A')
				location.href = '/promotion/planning-display/approval';
			else
				location.href = '/promotion/planning-display';
		});
	}
	
	/**
	 * col의 타입 조회
	 */
	_manage.getColType = function(list, name){

	    var type;
	    $.each(list.Cols, function(i,v){
	        if(name == v.SaveName){
	            type = v.Type;
	            return false;
	        }
	    });
	    
	    return type;
	}
	
	/** 상품 추가 팝업 콜백 */
	_manage.productCallback = function(data) {
		
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
		
		// 노출순서 입력
		var sortSeq = productList.GetColMaxValue('sortSeq');
		if (sortSeq < 0) { // 그리드 비어 있음.
			sortSeq = 0;
		}
		
		$.each(data, function(i,v) {
			
			if (productList.FindText('prdtNo', v.prdtNo, 0, 0, 0) < 0) {
				var idx = productList.RowCount() + 1;
				productList.SetRowData(idx, {
					'sortSeq' : ++sortSeq,
					'prdtNo' : v.prdtNo,
					'brandName' : v.brandName,
					'titleImageUrl' : v.titleImageUrl,
					'prdtName' : v.prdtName,
					'styleInfo' : v.styleInfo,
					'prdtColorInfo' : v.prdtColorInfo,
					
					"normalAmt" : abc.text.isMakeComma(v.normalAmt),
					"sellAmt" : abc.text.isMakeComma(v.sellAmt),
					"onlnDscntRate" : v.onlnDscntRate,
					"orderPsbltQty" : v.orderPsbltQty,
					"availabilityRate"	: Math.ceil(v.useYnQty / v.totalOptionCount * 100),
					
					'mmnyPrdtYn' : v.mmnyPrdtYn,
					'chnnlNo' : v.chnnlNo,
					'stdCtgrName' : v.stdCtgrName,
					'sellStatCode' : v.sellStatCode,
					'dispYn' : v.dispYn,
					'appliedPromotionInfo' : v.appliedPromotionInfo,
					'plndpNo' : abc.param.getParams().plndpNo
				}, rowOption);
			}		
		});
	}
	
	/** 소제목 추가 팝업 콜백 */
	_manage.cornerCallback = function(data) {
		
		var insertRowNumber = cornerList.RowCount() + 1;
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
		
		if (data.type == 'insert') {
			cornerList.SetRowData(insertRowNumber, data, rowOption);
			seq = cornerList.GetRowData(insertRowNumber).seq;
			cornerList.SetCellEditable(insertRowNumber, 'prdtManagement', 0);
		} else if (data.type == 'update') {
			cornerList.SetRowData(_manage.data.selectRow, data);
			seq = cornerList.GetRowData(_manage.data.selectRow).seq;
		}
		
		if (data.imageFile != null || data.imageFile != '')
			_manage.data.fileInputs[seq] = data.imageFile;
	}
	
	_manage.getGridRow = function(row){
		return cornerList.GetRowData(row);
	}
	
	/** 기획전 정보 저장 */
	_manage.savePlndp = function() {
		
		var plndpStatCode = $('input[name="plndpStatCode"]').val();
				
		var url = '/promotion/planning-display/product/manage/modify';
		
		if (plndpStatCode == '10001') { // 승인요청
			url = '/promotion/planning-display/approval/request';
		}
		
		var form = $.form(document.forms.saveForm);
		
		form.submit({
			url : url,
			method : 'POST',
			valid	: function($f){
				return true;
			},
			success : function(data) {
				
				var plndpStatCode = $('input[name="plndpStatCode"]').val();
				
				if (plndpStatCode == '10000') {
					alert('임시저장되었습니다.');
				} else if (plndpStatCode == '10001'){
					alert('승인요청 상태로 저장됩니다.\n관리자 승인 후 전시 가능합니다.');
				} else {
					alert('저장되었습니다.');
				}
				
				//location.reload();
				location.href = '/promotion/planning-display';
			},
			error : function(e) {
				alert(e.message);
		    	console.log(e);
			}
		});
	}
	
	/**
	 * 상품 저장
	 */
	_manage.saveProduct = function() {
		if (productList.GetSaveJson().data.length > 0) {
			
			var url = '/promotion/planning-display/product/add';
			var param = { 'Col' : 'status', 'Quest' : '0', 'CallBack' : function() {
				_manage.doAction('product');
			} };
			
			productList.DoSave(url, param);
		}
	}
	
	/**
	 * 소제목 저장
	 */
	_manage.saveCorner = function() {
		var jsonData = cornerList.GetSaveJson().data;
		
		if (jsonData.length > 0) {
			// 초기화
			$(document.cornerSaveForm).html('');
			
			var url = '/promotion/planning-display/corner/add';
			var form = $.form(document.cornerSaveForm);
			
			$.each(jsonData, function(idx, json){
			    $.each(json, function(i, v){
			        
			        var type = _manage.getColType(cornerList, i);
			        
			        var input = $('<input>').attr({
			            type : 'hidden',
			            name : i,
			            value : v
		            });

		            $(document.cornerSaveForm).append(input);

			        if(i == 'seq'){
		        		var imageFileInput = _manage.data.fileInputs[v];
		        		if(imageFileInput){
		        			imageFileInput.attr('name', 'imageFile');
		        			$(document.cornerSaveForm).append(imageFileInput);
		        		} else {
		        			var fileInput = $('<input>').attr({
					            type : 'file',
					            name : 'imageFile'     
				            });
		        			$(document.cornerSaveForm).append(fileInput);
		        		}
			        }
			    })
			});
			
			form.submit({
				url : url,
				method : 'POST',
				valid	: function($f){
					return true;
				},
				success : function(data) {
					
					_manage.doAction('corner');
				},
				error : function(e) {
					alert(e.message);
			    	console.log(e);
				}
			});
		}
	}
	
	$(function() {
		_manage.init();
	});
	
})();