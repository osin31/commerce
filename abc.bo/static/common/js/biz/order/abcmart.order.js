/***
 * 주문 목록 관련 함수 정의
 *
 */
(function() {
	var _order = abc.object.createNestedObject(window,"abc.biz.order.order");

	_order.init = function() {
		_order.event();
		_order.setDate();
	}

	_order.event = function(){
		// 초기화
		$("#resetBtn").click(function(){
			$('#orderForm')[0].reset();
			_order.setDate();
			_order.ordMbInfoSelectChange("buyerName");
		});


		/** 회원유형 변경 함수  */
		$("#chkMemberTypeAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=memberTypeCode]").prop("checked",true);
				$("#chkMemberTypeERP").prop("checked",true);
				$("input[custom=mbshpGradeCode]").prop("checked",true);
			} else {
				$("input[custom=memberTypeCode]").prop("checked",false);
				$("#chkMemberTypeERP").prop("checked",false);
				$("input[custom=mbshpGradeCode]").prop("checked",false);
			}
		});

		$("input[custom=memberTypeCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=memberTypeCode]:not(:checked)");
					var unChecked2 = $("#chkMemberTypeERP:not(:checked)");

					if (unChecked.length == 0 && unChecked2.length == 0 ) {
						$("#chkMemberTypeAll").prop("checked",true);
					}
					if($(this).attr("id") == "chkMemberType10001" ){
						$("input[custom=mbshpGradeCode]").prop("checked",true);
					}
				} else {
					$("#chkMemberTypeAll").prop("checked",false);
					if($(this).attr("id") == "chkMemberType10001" ){
						if(!$("#chkMemberType10001").is(":checked")){

							$("input[custom=mbshpGradeCode]").prop("checked",false);
						}else {
							$("input[custom=mbshpGradeCode]").prop("checked",true);
						}
					}
				}
			})
		});


		$("input[custom=mbshpGradeCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=mbshpGradeCode]:not(:checked)");

					//if (unChecked.length == 0) {
						$("#chkMemberType10001").prop("checked",true);
					//}
				} else {
					var unChecked = $("input[custom=mbshpGradeCode]:not(:checked)");

				}
			})
		});

		/** 발송처 변경 함수  */
		$("#stockGbnCodeAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=stockGbnCode]").prop("checked",true);
			} else {
				$("input[custom=stockGbnCode]").prop("checked",false);
			}
		});

		$("input[custom=stockGbnCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=stockGbnCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#stockGbnCodeAll").prop("checked",true);
					}
				} else {
					$("#stockGbnCodeAll").prop("checked",false);
				}
			})
		});

		/** 결제구분 변경 함수  */
		$("#deviceCodeAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=deviceCode]").prop("checked",true);
			} else {
				$("input[custom=deviceCode]").prop("checked",false);
			}
		});

		$("input[custom=deviceCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=deviceCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#deviceCodeAll").prop("checked",true);
					}
				} else {
					$("#deviceCodeAll").prop("checked",false);
				}
			})
		});

		/** 주문취소여부 변경 함수 */
		$("#chkCancelAll").click(function(){
			if($(this).is(":checked")){
				$("input[name=chkCancel]").prop("checked",true);
			} else {
				$("input[name=chkCancel]").prop("checked",false);
			}
		});

		$("input[name=chkCancel]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[name=chkCancel]:not(:checked)");
					if (unChecked.length == 0) {
						$("#chkCancelAll").prop("checked",true);
					}
				} else {
					$("#chkCancelAll").prop("checked",false);
				}
			})
		});

		/** 주문배송상태 변경 함수   */
		$("#orderPrdtStatCodeAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=orderPrdtStatCode]").prop("checked",true);
			} else {
				$("input[custom=orderPrdtStatCode]").prop("checked",false);
			}
		});

		$("input[custom=orderPrdtStatCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=orderPrdtStatCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#orderPrdtStatCodeAll").prop("checked",true);
					}
				} else {
					$("#orderPrdtStatCodeAll").prop("checked",false);
				}
			})
		});


		/** 결제수단 변경 함수 */
		$("#chkPaymentAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=pymntMeansCode]").prop("checked",true);
				$("#chkEscroTrue").prop("checked",true);
				$("#chkEscroFalse").prop("checked",true);
			} else {
				$("input[custom=pymntMeansCode]").prop("checked",false);
				$("#chkEscroTrue").prop("checked",false);
				$("#chkEscroFalse").prop("checked",false);
			}
		});

		$("#dlvyTypeCode").change(function(){
			var value = $(this).val();
			if (value == '10002' || value == '99999') {
				abc.biz.order.order.getChannelList(value);
				$("#chnnlNo").show();
			}else{
				$("#chnnlNo").hide();
			}
		});

		$("input[custom=pymntMeansCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=pymntMeansCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#chkPaymentAll").prop("checked",true);
					}
					if($(this).attr("id") == "chkPayment10001" || $(this).attr("id") == "chkPayment10002"){
						$("#chkEscroTrue").prop("checked",true);
						$("#chkEscroFalse").prop("checked",true);
					}
				} else {
					$("#chkPaymentAll").prop("checked",false);
					if($(this).attr("id") == "chkPayment10001" || $(this).attr("id") == "chkPayment10002"){
						if(!$("#chkPayment10001").is(":checked") && !$("#chkPayment10002").is(":checked")){
							$("#chkEscroTrue").prop("checked",false);
							$("#chkEscroFalse").prop("checked",false);
						}
					}
				}
			})
		});

		$("input[custom=escrApplyYn]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=escrApplyYn]:not(:checked)");
					if (unChecked.length > 0) {
						$("#chkPayment10001").prop("checked",true);
						$("#chkPayment10002").prop("checked",true);
					}
				} else {
				}
			})
		});


		// 캘린더 버튼 설정(일)
		$("a[name^=perToday]").click(function(){
			abc.date.days(this);
		});

		// 캘린더 버튼 설정(주)
		$("a[name^=perWeek]").click(function(){
			abc.date.week(this);
		});

		// 캘린더 버튼 설정(월)
		$("a[name^=perOneMonth]").click(function(){
			abc.date.month(this);
		});

		// 캘린더 버튼 설정(월)
		$("a[name^=perYearMonth]").click(function(){
			abc.date.year(this);
		});

		// 선택 다운로드
		$("#selectOrderForExcel").click(function(){
			var sRow = orderSheet.FindCheckedRow("updateCheck");

			var orderNos = [] ;
			var orderPrdtSeqs = [] ;

			if (sRow <= 0 ){
				alert( "다운로드 할 주문을 선택하세요 " );
				return false;
			} else {

				// 배열 처리
				for(var i=1; i <= orderSheet.RowCount(); i++){
					if(orderSheet.GetCellValue(i, "updateCheck") == "1"){
						orderNos.push(orderSheet.GetCellValue(i, "orderNo"));
						orderPrdtSeqs.push(orderSheet.GetCellValue(i, "orderPrdtSeq"));
					}
				}

				console.log ("orderNos >> "  ,  orderNos);
				$("#strOrderNos").val(orderNos);
				//$("#orderPrdtSeqs").val(orderPrdtSeqs);

				orderForm.action = "/order/onOrderSelectListExcelDn";
				orderForm.submit();
			}

		});


		// 전체 다운로드
		$("#OrderForExcel").click(function(){

			orderForm.action = "/order/onOrdeListExcelDn";
			orderForm.submit();
		});

		// 입점사 찾기 팝업
		$("#searchVendor").click(function(){
			// false 단건 , false n건
			var options = { callback : "abc.biz.order.order.callbackFindVendor", vndrName : $('[name=vndrName]').val() };
			abc.vndrSearchPopup(false, options);
		});

		//브랜드 찾기 팝업
		$("#serchBrandPop").click(function(){
			var options = { callback : "abc.biz.order.order.callbackFindBrand", brandName : $('[name=brandName]').val() };
			abc.brandSearchPopup(false, options);

		});

		//주문번호 유무에 따른 날짜세팅.
		$('#orderNo').change(function(e){
			var orderNo = $(this);
			if(orderNo.val() == '' && orderNo.val().length <= 0) {
				if ($("#toDate").val() == '' && $("#fromDate").val() == ''){
					abc.date.days($('#toDate'));
				}
			} 
			/*else {
				$('#fromDate').val('');
				$('#toDate').val('');
			}*/
		});
	}

	// 날짜세팅
	_order.setDate = function () {
		var _Today = new Date();

		if ($("#toDate").val() == '' && $("#fromDate").val() == ''){
			$("#toDate, #fromDate").val($.datepicker.formatDate($("#toDate").datepicker("option", "dateFormat"), _Today));
		}
	}

	/**
	 * 입점사 찾기 callback 이벤트
	 */
	_order.callbackFindVendor = function(data) {
		console.log(data);
		$("#vndrNo").val(data.arrayVndrNo[0]);
		$("#vndrName").val(data.arrayVndrName[0]);
	}

	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_order.callbackFindBrand = function(data) {
		$("#brandNo").val(data[0].brandNo);
		$("#brandName").val(data[0].brandName);

	}

	//숫자만입력 처리
	$("#orderNo").keyup(function(event){
		$(this).val($(this).val().replace(/[^0-9]/g,''));
	});

	/*************************************************************************
	 *								주문 목록  목록
	 *************************************************************************/

	/**
	 * 주문 목록 초기 세팅
	 */
	_order.initOrderSheet = function() {
		//
		createIBSheet2(document.getElementById("orderGrid"), "orderSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 40,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"사이트", 			Type:"Combo",		SaveName:"siteNo",				Width: 85, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"결제구분", 			Type:"Combo",		SaveName:"deviceCode",			Width: 60, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문유형", 			Type:"Combo",		SaveName:"rsvOrderYn",			Width: 60, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문번호", 			Type:"Text",		SaveName:"orderNo",				Width: 100, Align:"Center", 	Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"결제상태", 			Type:"Combo",		SaveName:"orderStatCode",		Width: 70, Align:"Center", 	Edit:0, Sort:0}			
			, {Header:"주문취소여부", 		Type:"Combo",		SaveName:"orderCancelFlag",		Width: 80, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"발송처", 			Type:"Text",		SaveName:"stockGbnCodeName",	Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문-배송유형", 		Type:"Combo",		SaveName:"dlvyTypeCode",		Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"상품명", 			Type:"Text",		SaveName:"orderPrdtInfo",		Width: 300, Align:"Left", 	Edit:0, Sort:0}
			, {Header:"주문일시", 			Type:"Date",		SaveName:"orderDtm",			Width: 125, Align:"Center", 	Edit:0, Sort:1 , Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"주문자", 			Type:"Text",		SaveName:"buyerGridName",		Width: 120, Align:"Center", 	Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"수령자", 			Type:"Text",		SaveName:"rcvrGridName",		Width: 80, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"결제수단", 			Type:"Text",		SaveName:"pymntMeansCodeName",	Width: 250, Align:"Center", Edit:0, Sort:0}
			, {Header:"총결제금액", 		Type:"Float",		SaveName:"pymntAmt",			Width: 90, Align:"Right", 	Edit:0, Sort:1 , Format: "#,##0원"}
			, {Header:"결제확인일", 		Type:"Date",		SaveName:"pymntDtm",			Width: 125, Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			//, {Header:"주문배송상태", 		Type:"Text",		SaveName:"",				Width: 30, Align:"Center", 	Edit:0}
			, {Header:"회원유형코드", 		Type:"Text",		SaveName:"memberTypeCode",		Width: 0, Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"주문자명", 			Type:"Text",		SaveName:"nonUserDpName",		Width: 0, Align:"Center", 	Edit:0, Sort:0, Hidden:1}
			, {Header:"회원번호", 			Type:"Text",		SaveName:"memberNo",			Width: 0, Align:"Center", 	Edit:0, Sort:0, Hidden:1}
		];

		// Grid 초기화
		IBS_InitSheet(orderSheet , initSheet);
		// Grid 건수 정보 표시
		orderSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		orderSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//orderSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		orderSheet.SetExtendLastCol(1);

		//
		orderSheet.InitDataCombo(0 , "siteNo" , this.siteCombo.text , this.siteCombo.code  );
		orderSheet.InitDataCombo(0 , "deviceCode" , this.codeCombo.DEVICE_CODE.text , this.codeCombo.DEVICE_CODE.code  );
		orderSheet.InitDataCombo(0 , "rsvOrderYn" , "예약|일반" , "Y|N"  );
		orderSheet.InitDataCombo(0 , "dlvyTypeCode" , this.codeCombo.DLVY_TYPE_CODE.text+"|택배전환" , this.codeCombo.DLVY_TYPE_CODE.code+"|99999"  );
		orderSheet.InitDataCombo(0 , "orderStatCode" , this.codeCombo.ORDER_STAT_CODE.text , this.codeCombo.ORDER_STAT_CODE.code  );
		orderSheet.InitDataCombo(0 , "orderCancelFlag" , "전체|부분|-" , "total|part|"   );
	}

	/**
	 * 주문 목록 Action관리
	 */
	_order.orderDoAction = function(sAction){
		switch(sAction) {
			// 조회
			case "search" :
				
				if (!_order.searchValidation()){
					return;
				}

				var pageCount = $('#pageCount').val();

				var param = { url : "/order/read-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.orderForm)
					, sheet : "orderSheet" };

				DataSearchPaging(param);

				break;
		}
	}


	/**
	 * 주문배송유형 선택 -- multi select 채널정보
	 */
	_order.getChannelList = function(value) {
		$.ajax({
			type:"post",
			url: "/order/channel-list/"
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.order.order.setChannelList(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	/**
	 * 주문배송유형 선택 -- multi select 채널정보
	 */
	_order.setChannelList = function(data) {
		var select = $("#chnnlNo");
		select.find("option").remove();

		var all = "";
		$.each(data, function(index, item) {
			select.append(new Option(item.chnnlName, item.chnnlNo));
			all += (index == 0) ? item.chnnlNo : ","+item.chnnlNo;
		});

		select.prepend(new Option("전체", "" , false, true));
	}

	/**
	 * 검색 조건 validation
	 */
	_order.searchValidation = function(sAction){

		var orderSearchKey = $("#orderSearchKey option:selected").val();
		var orderSearchText = $("#orderSearchText").val();

		if (!abc.text.allNull(orderSearchText)) {
			if (orderSearchKey == "buyerName" || orderSearchKey == "rcvrName" ) {
				if(orderSearchText.length > 100 ){
					alert("100자를 넘을수 없습니다. ");
					return false;
				}
			}else if (orderSearchKey == "buyerHdphnNoText"){
				if (isNaN(orderSearchText)){
					alert("휴대폰 번호는 숫자만 입력 가능 합니다. ");
					$("#orderSearchText").val("");
					return false;
				}
			}else if (orderSearchKey == "loginId") {

			}
		}

		var productSearchKey = $("#productSearchKey option:selected").val();
		var productSearchText = $("#productSearchText").val();
		if (!abc.text.allNull(productSearchText)) {
			if (productSearchKey == "prdtNo" || productSearchKey == "styleInfo" || productSearchKey == "vndrPrdtNoText" ){

			}else if (productSearchKey == "prdtName") {

			}
		}
		// 주문일 결제 확인일  , 주문번호가 있을 경우 날짜에 상관없음

		var fromDate = new Date($('#fromDate').val());
		var toDate = new Date($('#toDate').val());
		var orderNo = $('#orderNo').val();

		if ( fromDate != "" && toDate != "") {

			if(fromDate > toDate){
				alert("시작일은 종료일 이전 날짜만 가능합니다.");
				return false;
			}

			console.log ( " dateDiff " ,  abc.date.dateDiff(fromDate , toDate))

			var dateDiff =  abc.date.dateDiff(fromDate , toDate);

			if (orderNo == "") {
				if ( dateDiff > 365 ) {
					alert("기간은 최대 365일까지 가능합니다.");
					return false;
				}
			}
		}
		if(orderNo == ""){
			if(!abc.text.isDate(abc.text.validateStringSignRemove($("#fromDate").val()))){
				alert("시작일자를 확인해주세요");
				$("#fromDate").focus();
				
				return false;
			}
			
			if(!abc.text.isDate(abc.text.validateStringSignRemove($("#toDate").val()))){
				alert("종료일자를 확인해주세요");
				$("#toDate").focus();
				
				return false;
			}
		}

		// 주분번호로 검색시에는 기간을 적용하지 아니한다
		/*if(!abc.text.allNull($("#orderNo").val())) {
			$("#fromDate").val("");
			$("#toDate").val("");
		}*/

		return true;
	}
	
	/**
	 * 검색어 변경 이벤트
	 */
	_order.ordMbInfoSelectChange = function(searchType) {
		
		$("#orderSearchText").val("");
		$("#buyerHdphnBackNo").val("");
		
		if(searchType == "buyerName"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").show();
		}else if(searchType == "rcvrName"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").show();
		}else if(searchType == "buyerHdphnNoText"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").hide();
		}else if(searchType == "loginId"){
			$("#orderMemberInfoInp").show();
			$("#buyerHdphnBackNo").hide();
		}
	}


})();