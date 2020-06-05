/***
 * 회원관리 상세 회원정보 탭 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								회원 기프트카드 구매내역 탭
	 *************************************************************************/
	var _giftcardPurchase = abc.object.createNestedObject(window,"abc.biz.member.member.giftcardpurchase");

	$("#dlvyTypeCode").change(function(){
		var value = $(this).val();
		if (value == '10002' || value == '99999') {
			abc.biz.member.member.order.getChannelList(value);
			$("#chnnlNo").show();
		}else{
			$("#chnnlNo").hide();
		}
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
	
	/**
	 * 그리드 초기 세팅
	 */
	_giftcardPurchase.initGiftCardSheet = function() {
		
		if(typeof giftcardSheet != 'undefined'){
			
			giftcardSheet.Reset();
			giftcardSheet.DisposeSheet(1);
		}
		
		createIBSheet2(document.getElementById("giftcardGrid"), "giftcardSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg 		 = {SearchMode:smServerPaging2, Page:pageCount, DeferredVScroll:1, AutoFitColWidth: 'init',MergeSheet:msHeaderOnly};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"번호", 					Type:"Seq",			SaveName:"",					 Width: 20, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문번호", 					Type:"Text",		SaveName:"giftCardOrderNo",		 Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"구분", 					Type:"Combo",		SaveName:"giftCardOrderTypeCode",Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"카드번호", 					Type:"Text",		SaveName:"cardNoText",			 Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"결제일시", 					Type:"Date",		SaveName:"pymntDtm",			 Width: 50, 	Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"취소일시", 					Type:"Date",		SaveName:"modDtm",				 Width: 50, 	Align:"Center", 	Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			, {Header:"결제수단", 					Type:"Combo",		SaveName:"pymntMeansCode",		 Width: 100, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"결제/취소금액", 				Type:"Float",		SaveName:"pymntAmt",			 Width: 50, 	Align:"Center", 	Edit:0, Format: "#,##0원"}
			, {Header:"상태", 				    Type:"Text",		SaveName:"orderStatCodeName",	 Width: 50, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"관리자 처리", 				Type:"Text",		SaveName:"reSend",				 Width: 40, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"관리자 처리", 				Type:"Text",		SaveName:"pymntCancel",			 Width: 40, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"결제변환날짜", 				Type:"Text",		SaveName:"pymntDate",			 Width: 50, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"주문상태코드",   			Type:"Text",		SaveName:"orderStatCode",		 Width: 50, 	Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"재전송일시",   				Type:"Date",		SaveName:"mmsReSendDtm",		 Width: 50, 	Align:"Center", 	Edit:0, Format:"Ymd", Hidden:1}
			, {Header:"기프트카드상태코드",   		Type:"Text",		SaveName:"giftCardStatCode",	 Width: 50, 	Align:"Center", 	Edit:0, Hidden:1}
		];
		
		// Grid 초기화
		IBS_InitSheet(giftcardSheet , initSheet);
		// Grid 건수 정보 표시
		giftcardSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		giftcardSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		giftcardSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		giftcardSheet.SetExtendLastCol(1);
		
		giftcardSheet.InitDataCombo(0 , "giftCardOrderTypeCode" , this.codeCombo.GIFT_CARD_ORDER_TYPE_CODE.text , this.codeCombo.GIFT_CARD_ORDER_TYPE_CODE.code);
		giftcardSheet.InitDataCombo(0 , "pymntMeansCode" , this.codeCombo.PYMNT_MEANS_CODE.text , this.codeCombo.PYMNT_MEANS_CODE.code);
	}
	
	_giftcardPurchase.giftcardSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			// 결제취소 버튼 - 일경우 액션 없음.
			if ( giftcardSheet.ColSaveName(Col) == "pymntCancel" && Value != "" ) {
				var pymntCancel = giftcardSheet.GetRowData(Row).pymntCancel;
				if(pymntCancel == "-"){
				}else{
					var giftCardOrderNo = giftcardSheet.GetRowData(Row).giftCardOrderNo;
					_giftcardPurchase.giftCardCancel(giftCardOrderNo);
				}
			}
			else if ( giftcardSheet.ColSaveName(Col) == "reSend" && Value != "" ) {
				var reSend = giftcardSheet.GetRowData(Row).reSend;
				if(reSend == "-"){
				}else{
					var giftCardOrderNo = giftcardSheet.GetRowData(Row).giftCardOrderNo;
					_giftcardPurchase.giftCardResend(giftCardOrderNo,Row, Col);
				}
			}
		}
	}
	
	/**
	 * 캘린더 버튼 설정(일)
	 */
	$("a[name^=perToday]").click(function(){
		abc.date.days(this);
	});
	
	/**
	 * 캘린더 버튼 설정(주)
	 */
	$("a[name^=perWeek]").click(function(){
		abc.date.week(this);
	});
	
	/**
	 * 캘린더 버튼 설정(월) 
	 */
	$("a[name^=per1Month]").click(function(){
		abc.date.month(this);
	});
	
	/**
	 * 캘린더 버튼 설정(전체)
	 */
	$("a[name^=per1Year]").click(function(){
		abc.date.year(this);
	});
	
	/**
	 * 회원 기프트  목록 Action 관리
	 */
	_giftcardPurchase.memberGiftCardDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
//				if(abc.text.isBlank($('#cardNoText').val())){
//					alert("보유카드를 선택해주세요.");
//					$('#cardNoText').focus();
//					return false;
//				}
//				
				
				if(abc.text.isLimitLength($("#giftCardOrderNo").val(), 13)){
					alert("주문번호 최대 자리수를 초과하였습니다.");
					$("#giftCardOrderNo").focus();
					return false;
				}
				var pageCount = $("#pageCount").val();
				var param = { url : "/member/giftcard/read-member-giftcard-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "giftcardSheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
	/**
	 * 목록 조회 완료후 카드 잔액 확인
	 */
	_giftcardPurchase.cardNoSel = function() {
		
//		if(abc.text.isBlank($('#cardNoText').val())){
//			alert("보유카드를 선택해주세요.");
//			$('#cardNoText').focus();
//			return false;
//		}
//		
		
		if(abc.text.isLimitLength($("#giftCardOrderNo").val(), 13)){
			alert("주문번호 최대 자리수를 초과하였습니다.");
			$("#giftCardOrderNo").focus();
			return false;
		}
		
		var cardNo = $('#cardNoText').val();
		$.ajax({
	        url: '/member/giftcard/read-giftcard-balance',
	        type: 'post',
	        data: 'cardNoText=' + cardNo,
	        success:function(data){
	            if(data.niceResCode == '0000') {
	            	 data.balanceData = data;
	                //조회결과를 id 값에 넣음.
	            	 var conBal = data.balanceData.niceResData.conBalanceAmount;
	            	
	            	 $('#conBalanceAmount').val(Number(conBal));
	            	 _giftcardPurchase.memberGiftCardDoAction('search');
	            } else {
	            	 $('#conBalanceAmount').val(0);
	            	 _giftcardPurchase.memberGiftCardDoAction('search');
	            }
	        },
	        error:function(e){
//	        	abc.errorMessage();
	        	alert("서비스 연결이 원활하지 않습니다. 잠시 후 다시 시도해주시기 바랍니다.");
	        }
	    });
	}
	
	/**
	 * 숫자 콤마 세자리 
	 */
	_giftcardPurchase.numberWithCommas = function(x) {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	
	/**
	 * 결제취소 버튼 클릭시 
	 */
	_giftcardPurchase.giftCardCancel = function(giftCardOrderNo) {
		if(confirm("결제취소는 결제일로부터 최대 7일 내 온라인에서 취소가 가능합니다.(7일 이후 취소 불가) \n해당 카드를 결제취소 하시겠습니까?")){
			$.ajax({
		        url: '/member/giftcard/set-giftcard-hisoty-cancel',
		        type: 'post',
		        data: 'giftCardOrderNo=' + giftCardOrderNo,
		        success:function(data){
		        	console.log(data);
		        	console.log(data.nideCancelData);
					if(data.niceCancelRes == '0000') {
						alert('결제취소 되었습니다.');
	//					 $('#conBalanceAmount').val(0);
						_giftcardPurchase.cardNoSel();
					} else {
						alert(data.niceCancelMsg);
					}
		        },
		        error:function(e){
//		        	abc.errorMessage();
		        	alert("서비스 연결이 원활하지 않습니다. 잠시 후 다시 시도해주시기 바랍니다.");
		        }
		    });
		}
	}
	
	/**
	 * MMS 재전송 
	 */
	_giftcardPurchase.giftCardResend = function(giftCardOrderNo,Row,Col) {
		console.log("giftCardOrderNo",giftCardOrderNo);
		if(confirm("재전송하시겠습니까?")){
			$.ajax({
		        url: '/member/giftcard/set-giftcard-mms-resend',
		        type: 'post',
		        data: 'giftCardOrderNo=' + giftCardOrderNo,
		        success:function(data){
					if(data.niceResultCode == '0000') {
						alert('재전송  되었습니다.');
						_giftcardPurchase.memberGiftCardDoAction('search');
					} else {
						alert(data.niceResultMsg);
					}
		        },
		        error:function(e){
//		        	abc.errorMessage();
		        	alert("서비스 연결이 원활하지 않습니다. 잠시 후 다시 시도해주시기 바랍니다.");
		        }
		    });
		}
	}
})();