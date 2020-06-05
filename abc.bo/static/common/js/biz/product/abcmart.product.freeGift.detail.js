(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.freeGift.detail");

	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.sheet.init();
		_object.sheet.event();
		_object.event();

	}

	_object.sheet = {};
	_object.sheet.init = function() {
		createIBSheet2(document.getElementById("freeGift-promotion-list"), "list", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"번호",				Type:"Seq",			SaveName:"",						Width: 3,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
			{ Header:"프로모션 ID",			Type:"Text",		SaveName:"promoNo",					Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"프로모션명",			Type:"Text",		SaveName:"promoName",				Width: 15,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
			{ Header:"회원유형", 			Type:"Text", 		SaveName:"promotionMemberTypeCodeName", 		Width: 20,  Align:"Center", Edit:0, Cursor:"Pointer" },
			{ Header:"디바이스",			Type:"Html",		SaveName:"promotionTargetDevice",	Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"프로모션 시작일",		Type:"Date",		SaveName:"promoStartDtm",			Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"프로모션 종료일",		Type:"Date",		SaveName:"promoEndDtm",				Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN },
			{ Header:"수정자번호",			Type:"Text",		SaveName:"moderNo",					Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"수정자",			Type:"Text",		SaveName:"moderInfo",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
			{ Header:"수정일시",			Type:"Date",		SaveName:"modDtm",					Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN }
		];

		IBS_InitSheet(list , initSheet);
		list.SetCountPosition(3);
		list.SetPagingPosition(2);
		list.FitColWidth();
		list.SetExtendLastCol(1);

		list.SetColFontUnderline('promoName', 1);
		list.SetColFontUnderline('deviceName', 1);

		_object.sheet.list = list;

		_object.getList();

	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		list_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if ( row != 0) {
				if(list.ColSaveName(col) == 'promoName') {
					location.href = "/promotion/promotion/detail?promoNo=" + list.GetRowData(row).promoNo;
				} else if(list.ColSaveName(col) == 'promotionTargetDevice') {

				}
			}
		}
	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		new abc.biz.display.common.processImage( { file: '#pcBannerImg', name: '#pcBannerImgName' } );
		new abc.biz.display.common.processImage( { file: '#moBannerImg', name: '#moBannerImgName' } );

		// 재고수량 , 찍기
		$('input[name="productOption.totalStockQty"]').off().on('keydown keyup', function(e) {
			var value = $(this).val().replace(/,/gi, "");
			if(value < 99999) {
				$('#chkTotalStockQty').prop('checked', false);
			} else {
				$('#chkTotalStockQty').prop('checked', true);
			}
			if(99999 < value) {
				$(this).val(0);
				return;
			}
			value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			$(this).val(value);

		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		//무제한 체크
		$('#chkTotalStockQty').change(function() {
			if($(this).is(':checked')) {
				$('input[name="productOption.totalStockQty"]').val('99,999');
			} else {
				$('input[name="productOption.totalStockQty"]').val('');
			}
		});

		// 사은품 저장 이벤트
		$("#save").click(function(e) {
			e.preventDefault();

			var type = $("#prdt-no").val() == "" ? "save" : "modify";
			$('#form-freeGift').append('<input type="hidden" name="type" value="' + type + '" />');
			var totalStockQty = $('input[name="productOption.totalStockQty"]').val();
			$('input[name="productOption.totalStockQty"]').val(totalStockQty.replace(/,/gi, ""));

			$.form("#form-freeGift").submit({
				url : "/product/freeGift/" + type,
				type : "POST",
				dataType : "json",
				success : function(data) {
					alert("사은품이 등록되었습니다.");
					location.href = "/product/freeGift";
				},
				error : function(e) {
					if(e != null && e.message != null && e.message != "") {
						alert(e.message);
					}
					console.log(e);
				}
			});
		});

	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {

		var url = "/product/freeGift/promotion";
		var serializedFormData = $("#search-form").serialize();
		serializedFormData += '&promoTypeCode=10005&prdtType=G&prdtNo='+$("#prdt-no").val(); //*PRDT_TYPE_CODE = 10005 : 사은품 지급  *PR_프로모션대상상품 테이블 PRDT_TYPE = G : 사은품

		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "list"
			};
		DataSearchPaging(param);
	}

	$(function() {
		_object.init();
	});

})();