/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _settlementmain = abc.object.createNestedObject(window,"abc.biz.settlement.main");


	/**
	 * ibsheet 초기화
	 */
	_settlementmain.sheetInit = function(){
		// Grid 객체 생성
		createIBSheet2(document.getElementById("settleMentGrid"), "settleMentSheet", "100%", "429px");
		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};

		initSheet.Cfg = {SearchMode:smLazyLoad, MergeSheet:msHeaderOnly, AutoFitColWidth: "init|search|resize"};
		initSheet.HeaderMode = {};
		initSheet.Cols = [
						  {Header:"입점사코드\n(거래처코드)|입점사코드\n(거래처코드)", Type:"Text",	SaveName:"vndrNo",		Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
						, {Header:"업체명|업체명", 							  Type:"Text",	SaveName:"vndrName",	Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
						, {Header:"판매금액(+)|결제대상건수", 				      Type:"Float",	SaveName:"pymntCount",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0건"}
						, {Header:"판매금액(+)|결제금액", 					  Type:"Float",	SaveName:"pymntAmt",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0원"}
						, {Header:"판매금액(+)|배송비", 						  Type:"Float",	SaveName:"dlvyAmt",		Align:"Center", 	Edit:0, Sort:0, Format: "#,##0원"}
						, {Header:"공제금액(-)|판매수수료", 					  Type:"Float",	SaveName:"sellCmsnAmt",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0원"}
						, {Header:"공제금액(-)|프로모션비용", 					  Type:"Float",	SaveName:"promoAmt",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0원"}
						, {Header:"패널티금액(-)|패널티 건수", 					  Type:"Float",	SaveName:"penltyCount",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0건"}
						, {Header:"패널티금액(-)|패널티 산정금액", 				  Type:"Float",	SaveName:"penltyAmt",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0원"}
						, {Header:"최종정산금액|정산금액", 					  Type:"Float",	SaveName:"excclcAmt",	Align:"Center", 	Edit:0, Sort:0, Format: "#,##0원", FontBold:1, FontUnderline:1, Cursor:"Pointer"}
						, {Header:"", 									  Type:"Button",	SaveName:"excclcDcsnYn",Align:"Center", 	Edit:0, Sort:0}
						, {Header:"정산순번",		Type:"Text",	SaveName:"excclcSeq",   Align:"Center", Edit:0, Hidden:1}
						, {Header:"정산년월",		Type:"Text",	SaveName:"excclcYm",   Align:"Center", Edit:0, Hidden:1}
						];
		// Grid 초기화
		IBS_InitSheet(settleMentSheet, initSheet);
		// Grid 건수 정보 표시
		//memberLoginSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		//memberLoginSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		settleMentSheet.FitColWidth("10|10|10|10|10|10|10|10|10|10|10");
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		settleMentSheet.SetExtendLastCol(1);

	}
	
	_settlementmain.settleMentSheetOnClick = function(Row, Col, Value, CellX, CellY, CellW, CellH){
		
		if ( Row != 0 ) {
			if ( settleMentSheet.ColSaveName(Col) == "vndrNo" && Value != "" ) {
				
				var vndrNo = settleMentSheet.GetRowData(Row).vndrNo;
				abc.vendorInfoPop(vndrNo);
			}
			else if (settleMentSheet.ColSaveName(Col) == "vndrName" && Value != "" ) {
				var excclcSeq = settleMentSheet.GetRowData(Row).excclcSeq;
				var excclcYm = settleMentSheet.GetRowData(Row).excclcYm;
				var vndrNo = settleMentSheet.GetRowData(Row).vndrNo;
				var vndrName = settleMentSheet.GetRowData(Row).vndrName;
				_settlementmain.goSettleMentMonth(excclcSeq,excclcYm,vndrNo,vndrName);
				
			}
			else if (settleMentSheet.ColSaveName(Col) == "excclcAmt" && Value != "" ) {
				var vndrNo = asSheet.GetRowData(Row).vndrNo;
			
			}
			
			if ( settleMentSheet.ColSaveName(Col) == "excclcDcsnYn" && Value != "" ) {
				var excclcDcsnYn = settleMentSheet.GetRowData(Row).excclcDcsnYn;
				if(excclcDcsnYn == "-"){
				}else{
					var excclcSeq = settleMentSheet.GetRowData(Row).excclcSeq;
					var excclcYm = settleMentSheet.GetRowData(Row).excclcYm;
					var vndrNo = settleMentSheet.GetRowData(Row).vndrNo;
					_settlementmain.propertySettleMent(excclcSeq,excclcYm,vndrNo);
				}
			}
		}
	}

	_settlementmain.event = function(){
		/**
		 * 엑셀 다운로드
		 */
		$('#excel').on("click", function(){
			todayLoginForm.action = "/stats/member/today-login-list-excel";
			todayLoginForm.submit();
			
		});

		/**
		 * 검색
		 */
		$('#settlementmainSearch').on("click", function(){
			_settlementmain.getList();
		});
		
		// 검색 초기화
		$('#resetBtn').off().on('click', function(){
			$('#search-form')[0].reset();
		});
		
		// 화면 진입시 기본값 0으로 
		$('#totalAmt').text("0"+"원");
		// 해당년도 월을 기본으로 셋팅
		$('#currMonth').trigger("click");
	}
	
	/**
	 * 전월 
	 */
	$('#preMonth').on("click", function(){
		var currDay = new Date();
		var month = currDay.getMonth()+1;
		var year = currDay.getFullYear()+1;
		var yearIndex = $("#year option").index($("#year option:selected"));
		var monthIndex = $("#month option").index($("#month option:selected"));
		
		if($('#month option:selected').text() == 12){
			$("#month option:eq("+(monthIndex-1)+")").prop("selected", true);
			$("#month option:eq("+(monthIndex)+")").prop("selected", false);
		}else if($('#month option:selected').text() == 1){
			if(yearIndex == 0){
				
				$("#year option:eq("+(yearIndex)+")").prop("selected", true);
				$("#month option:eq("+(monthIndex-1)+")").prop("selected", true);
				$("#month option:eq("+(monthIndex)+")").prop("selected", false);
			}else{
				$("#year option:eq("+(yearIndex-1)+")").prop("selected", true);
				$('#month option:last').prop('selected',true);
			}
		}else{
			// 전월로 selectBox 를 바꿈. 
			$("#month option:eq("+(monthIndex-1)+")").prop("selected", true);
			$("#month option:eq("+(monthIndex)+")").prop("selected", false);
		}
	});
	
	
	/**
	 * 당월 
	 */
	$('#currMonth').on("click", function(){
		console.log("1");
		var currDay = new Date();
		var month = currDay.getMonth()+1;
		var year = currDay.getFullYear();
		$("#year").val(year);
		if(Number(month) < 10){
			$("#month").val("0"+month);
		}else{
			$("#month").val(month);
		}
		
	});
	
	/**
	 * 그리드 데이터 조회
	 */
	_settlementmain.getList = function() {
		var url =  "/settlement/settlement-list"
		var month = $("select[name=month]").val();
		var year = $("select[name=year]").val();
		$('#excclcYm').val(year+month);
		
		var serializedFormData = $("#search-form").serialize();
		var param = {
				url : url,
				onePageRow : $("#pageCount").val(),
				subparam :serializedFormData ,
				sheet : "settleMentSheet"
			};
		DataSearchPaging(param);
		_settlementmain.ajaxSettlaMentTotalSum();
	}
	
	/**
	 * 업체별 정산 합계 
	 */
	_settlementmain.ajaxSettlaMentTotalSum = function(){
		var serializedFormData = $("#search-form").serialize();
		$.ajax({
	        url: '/settlement/settlement-total-amt',
	        type: 'post',
	        data:  $("#search-form").serialize(),
	        success:function(data){
	            if(data.searchSuccYn == 'Y') {
	            	$('#totalAmt').text(abc.text.isMakeComma(data.totalAmt)+" 원");
	            } else {
	            	$('#totalAmt').text(abc.text.isMakeComma(data.totalAmt)+" 원");
	            }
	        },
	        error:function(e){
//	        	abc.errorMessage();
	        	alert("서비스 연결이 원활하지 않습니다. 잠시 후 다시 시도해주시기 바랍니다.");
	        }
	    });
	}
	
	/**
	 * 월정산 화면으로 이동 
	 */
	_settlementmain.goSettleMentMonth = function(excclcSeq,excclcYm,vndrNo,vndrName){
		var from = $("#search-form");
		$("#search-form").attr('method','post')
		$("#search-form").append("<input type='hidden' name='excclcSeq' value='"+excclcSeq+"'>");
		$("#search-form").append("<input type='hidden' name='vndrNo' value='"+vndrNo+"'>");
		$("#search-form").append("<input type='hidden' name='vndrName' value='"+vndrName+"'>");
		from.attr('action', '/settlement/settlement-month-main');
		from.submit();
	}
	
	/**
	 * 재정산 버튼 클릭 이벤트 
	 */
	_settlementmain.propertySettleMent = function(excclcSeq,excclcYm,vndrNo){
		$.ajax({
	        url: '/settlement/property-settlement',
	        type: 'post',
	        data:  {"excclcSeq":excclcSeq ,"excclcYm":excclcYm ,"vndrNo":vndrNo},
	        success:function(data){
	        	console.log(data.procResult);
	            if(data.procResult == '0') {
	            	_settlementmain.getList();
	            } else {
	            	alert("2");
	            }
	        },
	        error:function(e){
//	        	abc.errorMessage();
	        	alert("서비스 연결이 원활하지 않습니다. 잠시 후 다시 시도해주시기 바랍니다.");
	        }
	    });
	}
	
	
	$(function(){
		_settlementmain.sheetInit();
		_settlementmain.event();
	});
})();
$(document).ready(function(){
	//$('#currMonth').trigger("click");
});