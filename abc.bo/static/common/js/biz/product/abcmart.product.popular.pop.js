(function() {

	var _productPopularPop = abc.object.createNestedObject(window,"abc.biz.product.popular.pop");
	
	_productPopularPop.data = { selectGridId : '', sellStatCodes : {}};
	
	/**
	 * 초기화
	 */
	_productPopularPop.init = function(){
		_productPopularPop.initSheet();
		_productPopularPop.event();
		
		abc.biz.display.common.checkBoxAll({allId: '#chkServiceAll', itemsClass: '.storeServiceCode'});
		abc.biz.display.common.checkBoxAll({allId: '#chkStoreAll', itemsClass: '.storeTypeCode'});
		$("[data-button-period=month]").click();
	}
	
	/**
	 *  IBSheet 초기화
	 */
	_productPopularPop.initSheet = function() {
		
		// ====================================================================================================
		// 전시 카테고리 목록
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init'};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			 	{Header:"", 			Type:"CheckBox", 	SaveName:"checked", 					Width: 5, Align:"Center", Edit:1}
			  ,	{Header:'순위',  			Type:'Seq',			SaveName:'', 							Width:10,	Edit:0,	Align:'Center'}
			  , { Header:"업체상품코드",	Type:"Text",		SaveName:"vndrPrdtNoText",				Width: 15,	Align:"Center",	Edit:0,	Cursor:"Pointer" }				
			  , { Header:"전시채널",		Type:"Text",		SaveName:"chnnlName",					Width: 15,	Align:"Center",	Edit:0, Cursor:"Pointer" }
			  , { Header:"상품코드",		Type:"Text",		SaveName:"prdtNo",						Width: 20,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 }
			  , { Header:"상품명",		Type:"Text",		SaveName:"prdtName",					Width: 15,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
			  , {Header:"상품이미지", Type:"Image", SaveName:"titleImageUrl", Width: 10, Align:"Center", Edit:0, ImgHeight:100, ImgWidth: 100 }
			  , { Header:"브랜드",		Type:"Text",		SaveName:"brandName",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" }
			  , { Header:"판매상태",		Type:"Combo",		SaveName:"sellStatCode",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : codes.SELL_STAT_CODE.text,	ComboCode : codes.SELL_STAT_CODE.code }
			  , { Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",						Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "전시|전시안함",					ComboCode : "Y|N" }
			  , { Header:"표준카테고리",				Type:"Text",		SaveName:"stdCtgrName",					Width: 15,	Align:"Center",	Edit:0, Cursor:"Pointer" }
			  , { Header:"전시카테고리(기준)",			Type:"Text",		SaveName:"stdrCtgrName",				Width: 15,	Align:"Center",	Edit:0, Cursor:"Pointer" }
			  ];

		createIBSheet2(document.getElementById("cornerPopularSheet"), "list", "100%", "370px");
		var prdtPageCount = $('#prdtPageCount').val();
		
		IBS_InitSheet(list, initSheet);
		
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);
		
	}
	
	/**
	 * 목록 조회
	 */
	_productPopularPop.getList = function() {
		var url = "/display/contents/popup/corner-product-popular-pop";
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#pageCount").val(),
				subparam : serializedFormData,
				sheet : "list"
			};
		DataSearchPaging(param);
		console.log(serializedFormData);
	}
	
	/**
	 * callback 목록
	 */
	_productPopularPop.productCallback = function(data){
		
		console.log(data);
		
		var list = abc.biz.display.content.corner.info.pop.sheet.id[_cornerInfoPop.data.selectGridId];
		var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정
		
		var gridList = list.ExportData({"Type":"json"}).data;
		
		for(var i in data) {
			var v = data[i];
			console.log(v);
			
			var insertRowNumber = list.RowCount() + 1;
			var flag = false;
			
			for(var j in gridList){
				
				var w = gridList[j];
				
				if(w.addInfo1 == v.prdtNo){	// 값이 중복되면
					alert('중복된 상품은 제외하고 등록됩니다.'); 
					flag = true;
				}
				
			};
			
			if(flag) {
				continue;
			}
			
			list.SetRowData(insertRowNumber, {
				'sortSeq' : insertRowNumber,
				'addInfo1' : v.prdtNo,
				'dispYn' : 'Y',
				"dispStartYmd": '', 
				"dispEndYmd" : '',
				"contTypeCode" : '10001',
				"chnnlName" : v.chnnlName+'('+v.siteName+')',
				"titleImageUrl" : v.titleImageUrl,
				"mmnyPrdtYn" : v.mmnyPrdtYn == 'Y' ? '자사' : '입점사',
				"stdrCtgrName" : v.stdrCtgrName,
				"sellStatCode" : _cornerInfoPop.data.sellStatCodes[v.sellStatCode],
				"useYnQty" : v.useYnQty,
				"availabilityRate" : v.availabilityRate+'%',
				"normalAmt" : v.normalAmt,
				"sellAmt" : v.sellAmt,
				"prdtName" : v.prdtName,
				"onlnDscntRate" : v.onlnDscntRate+'%'				
			}, rowOption);
			
		};
	};
	
	
	/**
	 * 이벤트
	 */
	_productPopularPop.event = function(){

		_productPopularPop.setDpCategoryEvent();
		
		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			console.log("검색 영역을 초기화합니다.");
			abc.biz.product.utils.initFormData("search-form");
		});
		
		// 기간 제어
		$("[data-button-period]").click(function(e) {
			var type = $(this).data("button-period");
			switch(type) {
			case "today" :
				abc.date.days(this);
				break;
			case "week" :
				abc.date.week(this);
				break;
			case "month" :
				abc.date.month(this);
				break;
			case "year" :
				abc.date.year(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
			
		});
		
		$("[data-button]").click(function(e) {
			var type = $(this).data("button");
			switch(type) {
			case "add-product" :
				var result = [];
				var productList = list.ExportData({ Type : "json" }).data;
				
				if(productList.length > 0) {
					for(var i=0 ; i<productList.length ; i++) {
						if(productList[i].checked == 1) {
							result.push(productList[i]);
						}
					}
				} else {
					alert("조회된 데이터가 없습니다.");
					return;
				}

				console.log(result);
				
				// callback 실행
				var callback = abc.biz.product.utils.parameter.runCallback("callback");
				console.log(callback);
				if(typeof callback === "function") {
					if(result.length > 0) {
						callback(result);
						self.close();
					} else {
						alert("선택된 데이터가 없습니다.");
					}
				} else {
					alert("callback 함수를 찾을 수 없습니다.");
				}
				break;
			default :
				console.log("알 수 없는 유형입니다. " + type);
			}
		});
		/*	초기화	*/
		$("#reset").on('click',function(){
			$('#search-form')[0].reset();
			$('.1depth').prop('disabled',false);
			$('.2depth').prop('disabled',false);
			$('.3depth').prop('disabled',false);
			$("[data-button-period=month]").click();
		});
		
		/*
		 * 검색
		 * 
		 * */
		$("#searchBtn").on('click',function(){

			_productPopularPop.getList();
		});
		
		// 브랜드 찾기
		$("[data-button-popup]").click(function(e) {
			var type = $(this).data("button-popup");
			switch(type) {
			case "find-brand" :
				// 브랜드 찾기
				e.preventDefault();
				window.abc.brandSearchPopup(false, "abc.biz.product.popular.pop.callbackFindBrand");
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});
		
		// 페이지별 갯수 변경 이벤트
		$("#pageCount").change(function() {
			_productPopularPop.getList();
		});
		

		/**
		 * 브랜드 찾기 callback 이벤트
		 */
		_productPopularPop.callbackFindBrand = function(data) {
			$("#brand-no").val(data[0].brandNo);
			$("#brand-name").val(data[0].brandName);
		}
		
	};
	

	/**
	 * 전시카테고리 select 선택 이벤트
	 */
	_productPopularPop.setDpCategoryEvent = new function(option) {
		var self = this;
		
		init = function(option) {
			
			options = $.extend({
				area : '.dp-category-area',
				select : '.dp-category-select'
	        }, option);
			
			self.event();
			
			$('.dp-category-select.chnnl-no').trigger('change');
		};
		
		self.getList = function(selectEl, isChnnl) {
			
			var selected = $(selectEl).val();
			var param = {};
			
			$(selectEl).nextAll('.dp-category-select').each(function(i,v) {
				var str = $(this).hasClass('1depth') ? '대' : $(this).hasClass('2depth') ? '중' : $(this).hasClass('3depth') ? '소' : '세';
				$(this).html('<option value="">'+str+'카테고리 선택</option>');
			});
			
			if (selected == '') {
				if (!($(selectEl).prev().hasClass('chnnl-no'))) {

//					$('.dp-category-select').attr('name', '');
//					$(selectEl).prev().attr('name', 'ctgrNo');
				}
				return;
			}
			
			if (isChnnl) {
				param = { 'ctgrLevel' : 1, 'chnnlNo' : selected };
			} else {
				param = { 'upCtgrNo' : selected };
			}

			var targetSelect = $(selectEl).next();
			var nextSelects = $(selectEl).nextAll();
			
			$.ajax({
				type : 'post',
				url : '/noacl/display/display-category/list',
				data : param
			}).done(function(data) {
				
				var txt = '';
				
				var type = targetSelect.hasClass('1depth') ? '대' : targetSelect.hasClass('2depth') ? '중' : targetSelect.hasClass('3depth') ? '소' : '세';
				targetSelect.html('<option value>'+type+'카테고리 선택</option>');
				
				if (data.leafCtgrYn == 'Y') {
					$.each(nextSelects, function(i,v) {
						$(v).prop('disabled', true);
					});
				} else {
					$.each(nextSelects, function(i,v) {
						$(v).prop('disabled', false);
					});
					$.each(data, function(i,v) {
						txt = '<option value="'+v.ctgrNo+'">'+v.ctgrName+'</option>';
						targetSelect.append(txt);
					});
				}
				
			}).fail(function(e) {
				console.log("e :" + e);
			})
		}
		
		self.event = function() {
			$(document).on('change', options.select, function() {
				
				// ctgrNo 초기화
//				$('.dp-category-select').attr('name', '');
				
				var lastDepth = $('.dp-category-select.chnnl-no').val() == '10000' ? '4depth' : '3depth';
				
				if ($(this).hasClass(lastDepth)) {
					
					if ($(this).val() == '') {
//						$(this).prev().attr('name', 'ctgrNo');
					} else {
//						$(this).attr('name', 'ctgrNo');
					}
					return false;
				}
				
				if ($(this).hasClass('chnnl-no')) {
					
					if($(this).val() == '10000') {
						if (!$('.dp-category-select.4depth').length > 0) {
							$('.dp-category-select.3depth').attr('name', '');
							$(options.area).find('.3depth').after($('<select>', {
								'class' : 'ui-sel dp-category-select 4depth'
							}));
						}
					} else {
						$('.dp-category-select.4depth').remove();
					}
					
//					$('.dp-category-select.1depth').attr('name', 'ctgrNo');
					self.getList(this, true);
				} else {
										
//					$(this).attr('name', 'ctgrNo');
					self.getList(this);
				}
			});
		}
		
		return init;		
	}
	
	
	$(function() {
		_productPopularPop.init();
	});
	
})();