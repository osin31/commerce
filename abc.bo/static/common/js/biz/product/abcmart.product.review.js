(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.review");

	/**
	 * 초기화
	 */
	_object.init = function() {
		_object.event();
		_object.sheet.init();
		_object.sheet.event();
		// 회웑벙보 탭에서 이동시 세탕
		if(abc.biz.member) {
			abc.biz.member.memberDetail.moveTab("radio");
		}
	}

	_object.sheet = {};
	_object.sheet.init = function() {

		if(typeof reviewSheet != 'undefined'){
			reviewSheet.Reset();
			reviewSheet.DisposeSheet(1);
		}

		createIBSheet2(document.getElementById("review-list"), "reviewSheet", "100%", "429px");

		var initSheet = {};
		var pageCount = $('#page-count').val();
		initSheet.Cfg = { SearchMode:smServerPaging2, Page:pageCount, MaxSort:1 };
		initSheet.HeaderMode = { Sort:1, ColMove:0, ColResize:1 };
		// BO 상품후기관리 – 목록 정렬 항목 : 포인트, 자사/입점구분, 업체상품코드, 상품코드, 상품명, 주문번호, 내용, 후기작성일시, 답변일시
		initSheet.Cols = [
			{ Header:"상태",				Type:"Status",		SaveName:"status",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 6,   Align:"Center",	Edit:1, Sort:0},
			{ Header:"번호",				Type:"Seq",			SaveName:"seq",					Width: 6,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer" },
			{ Header:"후기순번",			Type:"Text",		SaveName:"prdtRvwSeq",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시여부",			Type:"Combo",		SaveName:"dispYn",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", ComboText:"전시|전시안함", ComboCode: "Y|N" },
			{ Header:"답변여부",			Type:"Combo",		SaveName:"aswrStatCode",		Width: 10,	Align:"Center",	Edit:1,	Sort:0,	Cursor:"Pointer", ComboText: _object.codeCombo.ASWR_STAT_CODE.text.replace('|답변보류', ''),	ComboCode: _object.codeCombo.ASWR_STAT_CODE.code.replace('|10001', '') },
			{ Header:"확인여부",			Type:"Combo",		SaveName:"cnfrmYn",				Width: 10,	Align:"Center",	Edit:1,	Sort:0,	Cursor:"Pointer", ComboText:"확인|미확인", ComboCode: "Y|N" },
			{ Header:"지급여부",			Type:"Combo",		SaveName:"pointPayType",		Width: 10,	Align:"Center",	Edit:1,	Sort:0,	Cursor:"Pointer", ComboText:"미지급|일반포인트 지급|포토포인트 지급|지급취소|지급불가", ComboCode: "N|G|P|C|I" },
			{ Header:"포인트",			Type:"Int",			SaveName:"payPointAmt",			Width: 10,	Align:"Center",	Edit:0,	Sort:1, Cursor:"Pointer" },
			{ Header:"베스트여부",			Type:"Combo",		SaveName:"bestYn",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", ComboText:"예|아니요", ComboCode: "Y|N" },
			{ Header:"자사/입점",			Type:"Text",		SaveName:"vndrName",			Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer" },
			{ Header:"자사구분",			Type:"Text",		SaveName:"mmnyPrdtYn",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"사이트구분",			Type:"Text",		SaveName:"siteName",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer" },
			{ Header:"업체상품코드",  		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer" },
			{ Header:"상품코드",  			Type:"Html",		SaveName:"prdtNo",				Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer" },
			{ Header:"상품코드(Hidden)",	Type:"Text",		SaveName:"hiddenPrdtNo",		Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"상품이미지대체텍스트",	Type:"Text",		SaveName:"productAltrnText",	Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"상품이미지",			Type:"Image",		SaveName:"productImageUrl",		Width: 30,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png" },
			{ Header:"상품명",			Type:"Html",		SaveName:"prdtName",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", FontUnderline:1 },
			{ Header:"구입처",			Type:"Combo",		SaveName:"onlnBuyYn",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", ComboText:"온라인|오프라인", ComboCode: "Y|N" },
			{ Header:"주문번호",			Type:"Text",		SaveName:"orderNo",				Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer" },
			{ Header:"내용",				Type:"Text",		SaveName:"rvwContText",			Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer", Ellipsis:1 },
			{ Header:"후기구분",			Type:"Combo",		SaveName:"rvwType",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", ComboText:"일반|포토", ComboCode: "G|P" },
			{ Header:"상품후기이미지명",		Type:"Text",		SaveName:"reviewImageName",		Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"상품후기이미지",		Type:"Text",		SaveName:"reviewImageUrl",		Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"회원구분",			Type:"Text",		SaveName:"memberTypeCodeName",	Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer" },
			{ Header:"답변내용",			Type:"Text",		SaveName:"aswrContText",		Width: 10,	Align:"Center",	Edit:1,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"작성자번호",			Type:"Text",		SaveName:"memberNo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"회원",				Type:"Text",		SaveName:"memberInfo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer" },
			{ Header:"후기작성일시",		Type:"Date",		SaveName:"writeDtm",			Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN},
			{ Header:"답변자번호",			Type:"Text",		SaveName:"aswrNo",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"답변자",			Type:"Text",		SaveName:"aswrInfo",			Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer" },
			{ Header:"답변일시",			Type:"Date",		SaveName:"aswrDtm",				Width: 10,	Align:"Center",	Edit:0,	Sort:1,	Cursor:"Pointer", Format:abc.consts.DEFAULT_DATETIME_PATTERN},
			{ Header:"사이트번호",			Type:"Text",		SaveName:"siteNo",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",			Type:"Text",		SaveName:"chnnlNo",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"ERP상품코드",		Type:"Text",		SaveName:"vndrPrdtNoText",		Width: 10,	Align:"Center",	Edit:0,	Sort:0, Cursor:"Pointer", Hidden:1 },
			{ Header:"입점사번호",			Type:"Text",		SaveName:"vndrNo",				Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"포인트지급여부",		Type:"Text",		SaveName:"pointPayYn",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"포인트지급취소여부",	Type:"Text",		SaveName:"pointPayCnclYn",		Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"포인트지급불가여부",	Type:"Text",		SaveName:"pointPayImpsbltYn",	Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"구매확정일",			Type:"Text",		SaveName:"buyDcsnDtm",			Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"기존지급여부",		Type:"Text",		SaveName:"orgPointPayType",		Width: 10,	Align:"Center",	Edit:0,	Sort:0,	Cursor:"Pointer", Hidden:1 },

		];

		IBS_InitSheet(reviewSheet , initSheet);
		reviewSheet.SetCountPosition(3);
		reviewSheet.SetPagingPosition(2);
		reviewSheet.FitColWidth();
		reviewSheet.SetExtendLastCol(1);

		reviewSheet.SetColFontUnderline('prdtNo', 1);
		reviewSheet.SetColFontUnderline('rvwContText', 1);
		reviewSheet.SetColFontUnderline('orderNo', 1);

		_object.sheet.list = reviewSheet;
		_object.getList();

	}

	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){

		/**
		 * 행 클릭 시
		 */
		reviewSheet_OnClick = function(row, col, value, cellX, cellY, cellW, cellH, rowtype){
			if (row < 1) {
				return false;
			}

			if(reviewSheet.ColSaveName(col) == 'rvwContText') {
				// 상품후기 새창 출력
				var params = { prdtRvwSeq : reviewSheet.GetRowData(row).prdtRvwSeq };
				abc.open.popup({
					url 	:	"/product/review/detail" ,
					winname :	"registration",
					method	: 	"get",
					title 	:	"prdt-review-detail",
					width 	:	1250,
					height	:	820,
					params	:	params
				});
			} else if(reviewSheet.ColSaveName(col) == 'vndrName') {
				if(reviewSheet.GetRowData(row).mmnyPrdtYn == "N") {
					// 입점사 정보조회 새창 출력
					abc.vendorInfoPop(reviewSheet.GetRowData(row).vndrNo);
				}
			} else if(reviewSheet.ColSaveName(col) == 'prdtName') {
				// 상품명 클릭 시, 프론트 상품상세 띄움
				abc.productFrontUrl(reviewSheet.GetRowData(row).hiddenPrdtNo);
			} else if(reviewSheet.ColSaveName(col) == 'orderNo') {
				// 주문번호 상세 새창 출력
				abc.orderDetailPopup(reviewSheet,"orderNo",row,col,'orderNo='+reviewSheet.GetRowData(row).orderNo);
			} 
		}

		/**
		 * 한 행마다 적용
		 */
		reviewSheet_OnRowSearchEnd = function(row) {
			// 지급상태에 따른 이벤트
			_object.afterRowSearchEnd(reviewSheet.GetRowData(row));
		}

		/**
		 * 조회 후 목록 재조정
		 */
		reviewSheet_OnSearchEnd = function(code, msg, stCode, stMsg, responseText) {
			reviewSheet.FitSize(1, 1);
			reviewSheet.SetColWidth('rvwContText', 100);
		}

		/**
		 * Combo 유효성 체크
		 */
		reviewSheet_OnEditValidation = function(row, col, value) {
			if (row < 1) {
				return false;
			}
			if(reviewSheet.ColSaveName(col) == 'aswrStatCode') {
				// 답변여부
			} else if(reviewSheet.ColSaveName(col) == 'cnfrmYn') {
				// 확인여부
			} else if(reviewSheet.ColSaveName(col) == 'pointPayType') {
				var item = reviewSheet.GetRowData(row);
				// 지급여부
				if('N' == item.cnfrmYn) {
					alert('미확인 후기는 지급변경이 제한됩니다.');
					reviewSheet.ValidateFail(1);
				}
				if('G' == item.rvwType && 'P' == value) {
					alert('일반후기는 포토포인트 지급변경이 제한됩니다.');
					reviewSheet.ValidateFail(1);
				}
			}
		}

	}

	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {

		//enter 검색
		$(".search-wrap").find(".ui-input, .js-ui-cal").on("keypress", function(e) {
			if(e.keyCode === 13) {
				$("#search").trigger("click");
			}
		});

		// 검색영역 초기화
		$("#clear").on("click", function(e) {
			abc.biz.product.utils.initFormData("search-form");
			$('a[name="per1Month"]').click();
			$("#brand-name").val('');
			$("#vndr-name").val('');
		});

		// 검색 버튼 이벤트
		$("#search").click(function() {
			_object.getList();
		});

		// 페이지별 갯수 변경 이벤트
		$("#page-count").change(function() {
			_object.getList();
		});

		// 브랜드 버튼 이벤트
		$('#search-brand').click(function() {
			window.abc.brandSearchPopup(false, { callback : "abc.biz.product.review.callbackFindBrand", brandName : $('#brand-name').val() });
		});

		// 기간 제어
		$(".term-date-wrap .btn-group .btn-func").click(function(e) {
			var type = $(this).attr("name");
			switch(type) {
			case "perToday" :
				abc.date.days(this);
				break;
			case "perWeek" :
				abc.date.week(this);
				break;
			case "per1Month" :
				abc.date.month(this);
				break;
			case "perYear" :
				abc.date.year(this);
				break;
			default :
				console.log("지원하지 않는 유형입니다. " + type);
			}
		});

		// 선택 일괄답변
		$('#batch-aswr').click(function() {
			_object.batchAswrPopup = window.abc.batchAswrPopup("abc.biz.product.review.callbackBatchAswr");
		});

		// 선택 일괄확인
		$('#batch-cnfrm').click(function() {
			var checkedRows = reviewSheet.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				reviewSheet.SetCellValue(row, 'cnfrmYn', 'Y');
			}
			alert('일괄 확인 처리됩니다.');
		});

		// 포인트 지급상태 일괄변경
		$('#batch-point').click(function() {
			var batchPointPayYn = $('select[name="batchPointPayYn"]').val();
			if(!batchPointPayYn) {
				alert("지급 여부를 선택하세요.");
				return false;
			}

			var checkedRows = reviewSheet.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }

			var countY = 0; 	// 지급건 갯수
			var countN = 0; 	// 미지급건 갯수
			var countC = 0; 	// 지급취소건 갯수
			var countCnfrm = 0; // 미확인건 갯수
			var countG = 0; 	// 일반포인트 지급 갯수
			var countI = 0;		// 지급불가건 갯수

			// 미확인 먼저 확인
			for(var i=0; i<checkedRows.length; i++) {
				var row = checkedRows[i];
				var cnfrmYn = reviewSheet.GetCellValue(row, 'cnfrmYn'); // 확인상태

				if(cnfrmYn === 'N') {
					countCnfrm++;
				}
			}

			if(0 < countCnfrm) {
				alert('미확인건 ' + countCnfrm + '건이 포함되어있습니다.\n확인 처리 후 포인트 지급 가능합니다.');
				return false;
			}

			/**
			 * 후기작성시 일반회원 또는 2만원미만 구매건은 지급불가로 등록되어있음(확인처리 필요)
			 * 지급을 할수 있는 조건으로는 멤버쉽회원 후기건 && 확인 && 구매확정일 30일 이내
			 *
			 * 미지급은 미지급,일반포인트지급,포토포인트지급(포토만 노출),지급불가
			 *
			 * 일반후기는 일반포인트지급,지급취소
			 * 포토후기는 일반포인트지급,포토포인트지급,지급취소
             *
			 * 지급이후
			 * 일반후기는 일반포인트지급,지급취소
			 * 포토후기는 포토포인트지급,지급취소
             *
			 * 지급취소
			 * 지급취소는 지급한 포인트타입 노출,지급취소
             *
			 * 지급불가
			 * 지급불가는 지급한 포인트타입 노출,지급불가
			 */
			for(var i=0; i<checkedRows.length; i++) {
				var bool = true;
				var row = checkedRows[i];
				var batchPointPayYn = $('select[name="batchPointPayYn"]').val(); 			// 선택한 지급조건 | 일반포인트 지급: G 포토포인트 지급: P 지급취소: C
				var pointPayType = reviewSheet.GetCellValue(row, 'pointPayType'); 			// 기존 지급상태
				var orgPointPayType = reviewSheet.GetCellValue(row, 'orgPointPayType'); 	// 원래 지급상태
				// 지급불가는 지급상태 변경하지 않음
				if('I' == orgPointPayType) {
					bool = false;
					countI++;
				} else {
					switch (batchPointPayYn) { // 선택한 지급조건
					case 'G': // 일반포인트 지급
					case 'P': // 포토포인트 지급
						if('G' == orgPointPayType || 'P' == orgPointPayType) {
							// 지급건은 재지급 불가
							bool = false;
							countY++;
						}
						if('P' == batchPointPayYn && 'G' == reviewSheet.GetCellValue(row, 'rvwType')) {
							// 일반후기는 포토포인트 지급 불가
							bool = false;
							countG++;
						}
						break;
					case 'C': // 지급취소
						if('N' == orgPointPayType) {
							// 미지급은 지급취소 불가
							bool = false;
							countN++;
						}
						if('C' == orgPointPayType) {
							// 지급취소는 재지급취소 불기
							bool = false;
							countC++;
						}
						break;
					default:
						reviewSheet.SetCellValue(row, 'updateCheck', 0);
						break;
					}
				}

				if(bool) {
					reviewSheet.SetCellValue(row, 'pointPayType', batchPointPayYn);
				} else {
					if('I' != orgPointPayType) {
						// 미답변, 미확인을 수정할 수 있어서 지급불가건은 체크를 해제하지않는다.
						reviewSheet.SetCellValue(row, 'updateCheck', 0);
					}
				}
			}

			if(0 < countI) {
				alert('지급불가인 ' + countI +'건은 변경되지 않습니다.');
			}
			var message = '지급상태가 일괄변경되었습니다.';
			if('G' == batchPointPayYn || 'P' == batchPointPayYn) { // 일괄 지급
				// 지급때는 지급건만 확인
				if(0 < countY) {
					message = countY + '건의 지급건을 제외하고 일괄 지급 처리됩니다.';
				}
				if(0 < countG) {
					message = countG + '건의 일반후기를 제외하고 일괄 지급 처리됩니다.';
				}
			} else { // 일괄 지급취소
				// 지급취소때는 지급건, 지급취소건 확인
				if(0 < countN) {
					message = countN + '건의 미지급건을 제외하고 일괄 지급취소 처리됩니다.';
				}
				if(0 < countC) {
					message = countC + '건의 지급취소건을 제외하고 일괄 지급취소 처리됩니다.';
				}
				if(0 < countN && 0 < countC) {
					message = countN + '건의 미지급건과 ' + countC + '건의 지급취소건을 제외하고 일괄 지급취소 처리됩니다.';
				}
			}
			alert(message);
		});

		// 변경 저장
		$('#change-save').click(function() {
			var checkedRows = reviewSheet.FindCheckedRow("updateCheck").split('|');
			if(checkedRows == "") { alert("선택한 항목이 없습니다."); return false; }
			var reviewList = [];
			for(var i=0; i<checkedRows.length; i++) {
				reviewList.push(reviewSheet.GetRowData(checkedRows[i]));
			}
			$.ajax({
				dataType : "json",
				type :"POST",
				url : "/product/review/batchSave",
				data : $.paramObject(reviewList)
			})
			.done(function(data){
				alert('변경사항이 저장되었습니다.');
				_object.getList();
			})
			.fail(function(e){
				console.log(e);
				alert(e.responseJSON.message);
			});
		});

		// 표준카테고리 이벤트
		abc.biz.display.common.setStdCategoryEvent();

		// 시작기간~끝기간 설정
		abc.biz.product.utils.setSearchStartAndEndDate($('input[name="periodStartDate"]'), $('input[name="periodEndDate"]'));
		$('a[name="per1Month"]').click();

		// 자사/입점 제어
		$('[name=mmnyPrdtYn]').off().on('click', function(e) {
		   if($(this).val() == "N"){
			   $("#search-vndr").removeClass("disabled");
			   $("#vndr-name").removeAttr("readonly");
		   } else {
			   $("#vndr-name").val('');
			   $("#vndr-name").prop('readonly', true);
			   $("#search-vndr").addClass("disabled");
		   }
		});

	   // 입점사 조회
	   $('#search-vndr').click(function() {
		   var options = { callback : "callbackVndr", vndrName : $('[name=vndrName]').val() };
		   abc.vndrSearchPopup(false, options);
	   });

	   // 상품코드 20건까지 검색 제한
	   abc.biz.product.utils.setPrdtCodeKeywordLimit($('textarea[name="prdtCodeKeyword"]'), 100);
	   
	   $("#hdphnBackNoText").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
	   //회원명일경우 걸러내야함
	   $("select[name=searchMemberType]").change(function(){
		  if($("select[name=searchMemberType]").val() == "member-name"){
			 $("#hdphnBackNoText").show(); 
		  }else{
			  $("#hdphnBackNoText").hide();
		  }
	   });
	   
	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_object.callbackFindBrand = function(data) {
		$("#brand-no").val(data[0].brandNo);
		$("#brand-name").val(data[0].brandName);
	}

	/**
	 * IBsheet 후기상태변경
	 */
	_object.afterRowSearchEnd = function(item) {
		reviewSheet.SetToolTipText(item.seq, 'rvwContText', item.rvwContText.replace(/\n/g, "<br/>") );

		// 주문번호
		reviewSheet.SetCellText(item.seq, 'orderNo', ('Y' == item.onlnBuyYn ? item.orderNo : item.dealNo));
		
		// 숨김처리 상품번호
		reviewSheet.SetCellText(item.seq, 'hiddenPrdtNo', item.prdtNo);

		// 상품상세 새창 출력
		reviewSheet.SetCellText(item.seq, 'prdtNo', (item.vndrPrdtNoText ? item.vndrPrdtNoText + ' / ' : '' )
				+ '<u onclick="abc.readonlyProductDetailPopup({'
				+ 'prdtNo : &quot;' + item.prdtNo + '&quot;});"'
				+ '>' + item.prdtNo + '</u>');

		// 포토면 밑줄
		reviewSheet.SetCellFontUnderline(item.seq, 'rvwType', (item.rvwType == "G" ? 0 : 1) );

		// 자사/입점 구분
		if(item.mmnyPrdtYn == "N") {
			reviewSheet.SetCellFontUnderline(item.seq, "vndrName", 1);
		} else {
			reviewSheet.SetCellText(item.seq, 'vndrName', '자사');
		}

		// 상품후기이미지 설정
		if(abc.biz.product.utils.isNotEmpty(item.reviewImageUrl)) {
			var imageTag = "<img src=" + item.reviewImageUrl + " alt=" + item.reviewImageName + " width=\"200\" height=\"200\"/>";
			reviewSheet.SetToolTipText(item.seq, 'rvwType', imageTag);
		}

		// 지급상태를 변경할 수 있기에 기존 지급상태 관리
		reviewSheet.SetCellText(item.seq, 'orgPointPayType', item.pointPayType);

		// 지급상태별 처리
		if(item.pointPayType == 'I') {
			// 포인트지급불가여부
			reviewSheet.SetCellEditable(item.seq, 'pointPayType', 0);
		} else if(item.pointPayType == 'C' || item.pointPayType == 'G' || item.pointPayType == 'P') {
			// 포인트지급취소여부 또는 포인트지급여부
			var reviewComboText = "지급취소";
			var reviewComboCode = "C";
			if(item.rvwType == 'P') {
				// 포토후기
				reviewComboText = '포토포인트 지급|' + reviewComboText;
				reviewComboCode = 'P|' + reviewComboCode;
			}
			if(item.pointPayType != 'P') {
				reviewComboText = '일반포인트 지급|' + reviewComboText
				reviewComboCode = 'G|' + reviewComboCode
			}

			reviewSheet.CellComboItem(item.seq, 'pointPayType', { ComboText: reviewComboText, ComboCode: reviewComboCode } );
		} else if(item.pointPayType == 'N' ) {
			// 미지급
			var reviewComboText = "미지급|일반포인트 지급";
			var reviewComboCode = "N|G";
			if(item.rvwType == 'P') {
				// 포토후기
				reviewComboText += '|포토포인트 지급';
				reviewComboCode += '|P';
			}
			reviewSheet.CellComboItem(item.seq, 'pointPayType', { ComboText: reviewComboText + "|지급불가", ComboCode: reviewComboCode + "|I" } );
		}
	}

	/**
	 * 목록 조회
	 */
	_object.getList = function() {
		if (!abc.date.searchValidate()) {
			return false;
		}
		if($("select[name=searchMemberType]").val() == "member-name"){
			if(abc.text.allNull($("input[name=searchMember]").val())
					&& !abc.text.allNull($("#hdphnBackNoText").val())){
				alert("회원명을 입력해주세요.");
				return false;
			}
			if(!abc.text.allNull($("input[name=searchMember]").val()) 
					&& abc.text.allNull($("#hdphnBackNoText").val())){
				alert("휴대폰번호 뒷자리를 입력해주세요.");
				return false;
			}
			if(!abc.text.allNull($("input[name=searchMember]").val()) 
					&& $("#hdphnBackNoText").val().length < 4){
				alert("휴대폰번호 뒤 4자리를 입력해주세요.");
				return false;
			}
		}
		var url = "/product/review";
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#page-count").val(),
				subparam : serializedFormData,
				sheet : "reviewSheet"
			};
		DataSearchPaging(param);
	}

	/**
	 * 선택 일괄답변 콜백
	 */
	_object.callbackBatchAswr = function() {
		var checkedRows = reviewSheet.FindCheckedRow("updateCheck").split('|');
		if(checkedRows == "") { _object.batchAswrPopup.alert("선택한 항목이 없습니다."); return false; }
		var count = 0; // 답변완료건
		for(var i=0; i<checkedRows.length; i++) {
			var row = checkedRows[i];
			if(reviewSheet.GetCellValue(row, 'aswrStatCode') == '10002') {
				if(!reviewSheet.IsCellModified(row, 'aswrStatCode')) {
					reviewSheet.SetCellValue(row, 'updateCheck', 0);
					count++;
				}
			} else {
				//팝업 내용 가져오기
				reviewSheet.SetCellValue(row, 'aswrContText', _object.batchAswrPopup.$('#aswr-cont-text').val());
				reviewSheet.SetCellValue(row, 'aswrStatCode', '10002');
			}

			// 미확인은 확인으로 변경
			if(reviewSheet.GetCellValue(row, 'cnfrmYn') == 'N') {
				reviewSheet.SetCellValue(row, 'cnfrmYn', 'Y');
			}
		}
		_object.batchAswrPopup.alert(count < 1 ? '일괄답변 처리되었습니다.' : count + '건의 답변완료건을 제외하고 일괄답변 처리되었습니다.');
		_object.batchAswrPopup.close();
	}

	$(function() {
		_object.init();
	});

})();

/**
 * 입점사 찾기 팝업 콜백함수
 */
function callbackVndr(data) {
	$('#vndr-name').val(data.arrayVndrName[0]);
	$('#vndr-no').val(data.arrayVndrNo[0]);
}