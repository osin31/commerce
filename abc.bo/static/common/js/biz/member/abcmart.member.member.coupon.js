/***
 * 회원관리 상세 회원쿠폰 탭 함수 정의.
 * 
 */
(function() {
	
	/*************************************************************************
	 *								회원 쿠폰 탭
	 *************************************************************************/
	var _memberCoupon = abc.object.createNestedObject(window,"abc.biz.member.member.coupon");
	
	_memberCoupon.memberNo;
	
	/**
	 * 그리드 초기 세팅
	 */
	_memberCoupon.initCouponSheet = function() {
		
		if(typeof couponSheet != 'undefined'){
			couponSheet.Reset();
		}
		//
		createIBSheet2(document.getElementById("couponGrid"), "couponSheet", "100%", "370px");
		
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		initSheet.Cols = [
			  {Header:"" ,		 				Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"" ,		 				Type:"Int",			SaveName:"holdCpnSeq",			Width: 0,  Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"" ,		 				Type:"Text",		SaveName:"useYnVal",			Width: 0,  Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"" ,		 				Type:"Text",		SaveName:"memberNo",			Width: 0,  Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"번호", 					Type:"Seq",			SaveName:"",					Width: 50, Align:"Center", 		Edit:0, Sort:0}
			, {Header:"쿠폰번호", 					Type:"Text",		SaveName:"cpnNo",				Width: 120, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"쿠폰명", 					Type:"Text",		SaveName:"cpnName",				Width: 200, Align:"Left", 		Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"쿠폰유형", 					Type:"Combo",		SaveName:"cpnTypeCode",			Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"쿠폰분류", 					Type:"Combo",		SaveName:"normalCpnYn",			Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"쿠폰속성", 					Type:"Combo",		SaveName:"cpnUseGbnType",		Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"할인율/금액", 				Type:"Text",		SaveName:"dscntText",			Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사용처\n\r온오프라인사용 여부", 	Type:"Combo",		SaveName:"usePlaceGbnType",		Width: 150, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"디바이스", 					Type:"Text",		SaveName:"deviceName",			Width: 150, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"채널", 					Type:"Text",		SaveName:"chnnlName",			Width: 150, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"유효기간", 					Type:"Text",		SaveName:"validDate",			Width: 200, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"쿠폰사용일", 				Type:"Text",		SaveName:"cpnUseDtm",			Width: 200, Align:"Center", 	Edit:0, Sort:0, Format:"YmdHms"}
			, {Header:"쿠폰발급일", 				Type:"Text",		SaveName:"cpnIssueDtm",			Width: 200, Align:"Center", 	Edit:0, Sort:0, Format:"YmdHms"}
			, {Header:"발급자id", 				Type:"Text",		SaveName:"adminName",			Width: 150, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"주문번호", 					Type:"Text",		SaveName:"orderNo",				Width: 150, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사용여부", 					Type:"Combo",		SaveName:"cpnStatCode",			Width: 100, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"관리", 					Type:"Button",		SaveName:"cpnManage",			Width: 100, Align:"Center", 	Edit:1, Sort:0}
			
		];
		
		// Grid 초기화
		IBS_InitSheet(couponSheet , initSheet);
		// Grid 건수 정보 표시
		couponSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		couponSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//couponSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		couponSheet.SetExtendLastCol(1);
		couponSheet.SetFrozenCol(8);
		
		couponSheet.InitDataCombo(0 , "cpnTypeCode" , this.codeCombo.CPN_TYPE_CODE.text , this.codeCombo.CPN_TYPE_CODE.code);
		couponSheet.InitDataCombo(0 , "normalCpnYn" , "일반쿠폰|플러스쿠폰" , "Y|N");
		couponSheet.InitDataCombo(0 , "cpnUseGbnType" , "행사|CS|eDM" , "E|C|S");
		couponSheet.InitDataCombo(0 , "usePlaceGbnType" , "온라인|오프라인|온/오프라인" , "O|F|A");
		couponSheet.InitDataCombo(0 , "cpnStatCode" , "미사용|미사용|사용완료|기간만료|사용중지", "10000|10001|10002|10003|10004");
		
		_memberCoupon.event();
	}
	
	_memberCoupon.event = function(){
		// 페이지별 갯수 변경 이벤트
		$("#pageCount").on('change', function() {
			_memberCoupon.memberCouponDoAction('search');
		});
	}
	
	/**
	 * 회원 목록 Action 관리
	 */
	_memberCoupon.memberCouponDoAction = function(sAction){
		switch(sAction) {
			// 조회 
			case "search" :
				var fromDate = Number(abc.text.validateStringSignRemove($("#fromDate").val()));;
				var toDate = Number(abc.text.validateStringSignRemove($("#toDate").val()));
				
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
				
				if(fromDate > toDate){
					alert("검색시작일은 종료일보다 미래 일 수 없습니다.\n검색기간을 재설정 해 주십시오.");
					$("#fromDate").focus();
					return false;
				}
				
				var pageCount = $("#pageCount").val();
				$("#frmSearch").append("<input type='hidden' name='memberNo' value='"+ _memberCoupon.memberNo +"'>");
				
				var param = { url : "/member/read-member-coupon-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "couponSheet" };
				
				DataSearchPaging(param);
				
				break;
		}
	}
	
	_memberCoupon.insertMemberCoupon = function(data) {
		var memberNos = new Array();
		memberNos.push(abc.biz.member.member.coupon.memberNo);
		
		var form = $.form(document.forms.frmSearch);
		form.submit({
		    'type': 'POST',
		    'url': '/promotion/coupon/member/save',
		    'data' : $.paramObject({memberNos : memberNos, cpnNo : data[0].cpnNo}),
		    'success': function(d) {
		    	alert('발급되었습니다.');
		    	_memberCoupon.memberCouponDoAction('search');
		    	//location.reload();
		    },
		    'error': function(e){
		    	alert(e.message);
		    	console.log(e);
		    }
		});
		
	}
	
	_memberCoupon.checkBoxAll = function(options){
		
		options = $.extend({
					allId: '#check-all-item',
					itemsClass: '.check-item'
			}, options);
		
		options.itemsLength = $(options.itemsClass).length;
		
		$(options.allId).on('click', function (e) {
		
			if($(this).is(':checked')){
				$(options.itemsClass).prop('checked', true);
			}else{
				$(options.itemsClass).prop('checked', false);
			}
			
		});
		
		$(options.itemsClass).on('click', function (e) {
			if ($(options.itemsClass + ':checked').length == options.itemsLength) {
				$(options.itemsClass + ':checked').prop('checked', false);
				$(options.allId).prop('checked', true);
			} else {
				$(options.allId).prop('checked', false);
			}
		});
	}
	
})();