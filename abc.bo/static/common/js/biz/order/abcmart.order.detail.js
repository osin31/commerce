/***
 *  주문 정보 tab 업무와 관련된 함수 정의.
 *
 */
(function() {

	var _orderInfo = abc.object.createNestedObject(window,"abc.biz.order.info.tab");

	_orderInfo.initData = function(param){
		// 계좌정보 체크
		var memberTypeCode = param.memberTypeCode;

		if( memberTypeCode == abc.order.consts.MEMBER_TYPE_CODE_NONMEMBER ){ // 비회원
			$("#regInquiry").hide(); // 유선상담 접수
			$("#passwordChange").show(); // 비회원 비번변경

		}else { // 회원
			$("#regInquiry").show(); // 유선상담 접수
		}
	}


	// 회원명 클릭
	$("#memberPop").click(function(){
		var memberTypeCode 	= $("#memberTypeCode").val(); // 회원구분코드
		if (memberTypeCode != abc.order.consts.MEMBER_TYPE_CODE_NONMEMBER){
			abc.memberDetailPopup($("#memberNo").val());
		}
	});

	// 계좌변경 버튼
	$("#accountChangeBtn").click(function(){
		$("#memBankInfo").hide();
		$("#memBankRegInfo").show();
	});

	// 휴대폰 번호 키 체크
    $("#accountNo").keyup(function(){
       abc.text.validateOnlyNumber(this);
    });


    // 유선상담 공통 팝업창 호출
	$("#regInquiry").click(function(){
		var memberNo = $("#memberNo").val();
		abc.registMemberCounsel(memberNo);
	});

	// 업체 문의 공통 팝업창 호출
	$("#vendorInquiry").click(function(){
		var vndrNo = $("#vndrNo option:selected").val();
		
		if (vndrNo == "") {
			alert("업체를 선택하여 주세요.");
			return; 
		} 
		
		abc.vendorInquiryPopup(vndrNo);
	});

	$("#passwordChange").click(function(){
		var orderNo = $("#orderNo").val();

		var url = "/order/passwordChangePop";
		var params = {}
		params.orderNo = orderNo;

		abc.open.popup({
			winname :	"주문 비밀번호 변경",
			url 	:	url,
			width 	:	400,
			height	:	470,
			params	:	params

		});

	});

	// 구매확정 
	$("#orderConfirm").click(function(){
		if(confirm('구매확정 하시겠습니까?')){
			_orderInfo.orderConfirm($("#orderNo").val());
		}
	});

	// 구매확정 처리
	_orderInfo.orderConfirm = function (orderNo){

		var param = {
			orderNo   : orderNo
		};

		$.ajax({
			type :"post",
			url : "/order/order-Confirm",
			async: false,
			data: param
		})
		.done(function(data){
			if ( data.resultCode == "Y"){
				alert("구매확정 되었습니다.");
				location.reload();
			}else{
				alert("구매확정 처리되지 않았습니다.");
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			console.log(jqXHR.responseJSON.message);
			alert("구매확정 처리되지 않았습니다.");
			return false;
		});
	}

	// 주문 전체 취소
	$("#orderCancel").click(function(){
		var orderNo 			= $("#orderNo").val();

		var type = "4"; // 전체 취소 가능 여부 체크 변수
		var param = {
			  orderNo : orderNo
			, vaildationType : type
		};

		$.ajax({
			type :"post",
			url : "/order/order-vaildation",
			async: false,
			data: param
		})
		.done(function(data){
			if(data.resultCode == abc.consts.BOOLEAN_TRUE){
				_orderInfo.orderCancelSave(orderNo);
			}else{
				alert("주문 취소 불가능 상태 입니다. 확인 바랍니다.");
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			console.log(jqXHR.responseJSON.message);
			alert("주문 취소 불가능 상태 입니다. 확인 바랍니다.");
			return ;
		});

	});

	// 주문 전체 취소 처리
	_orderInfo.orderCancelSave = function (orderNo){
		$.ajax({
			type :"post",
			url : "/claim/claim/validate-order-all-cancel",
			async: false, 
			data: {orderNo : orderNo}
		})
		.done(function(data){
			if(data.success == abc.consts.BOOLEAN_TRUE){
				abc.open.popup({
					winname :	"orderAllCancel",
					url 	:	"/claim/claim/order-all-cancel-pop",
					width 	:	500,
					height	:	240,
					params	:	{orderNo : orderNo}

				});
			}else{
				alert(data.message) ;
				return;
			}
		})
		.fail(function(e){
			console.log("e :" + e);
			return false;
		});
	}

})();