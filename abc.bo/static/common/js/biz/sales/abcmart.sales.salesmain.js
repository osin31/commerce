/***
 * AS 목록/검색 과 관련된 함수 정의.
 */
(function() {
	
	var _salesmain = abc.object.createNestedObject(window,"abc.biz.sales.salesmain");
	
	/*************************************************************************
	 *								AS 목록/검색
	 *************************************************************************/
	
	/**
	 * AS관리 목록 초기 세팅
	 */
	_salesmain.initafterserviceSheet = function() {
		//Grid 객체 생성
		createIBSheet2(document.getElementById("salesGrid"), "salesSheet", "100%", "370px");
		
		//Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		
		initSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.Cols 		 = [
			    {Header:"",				    Type:"CheckBox",       	SaveName:"downloadCheck",		Width: 50,  	Align:"Center", Edit:1, Sort:0}
			  , {Header:"seq",			    Type:"Text",           	SaveName:"rowSeq", 				Width: 60,  	Align:"Center", Edit:0, Sort:0,Hidden:1}
			  , {Header:"B코드",			    Type:"Text",           	SaveName:"cbcd", 				Width: 60,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"매장코드",			Type:"Text",	       	SaveName:"maejangcd",			Width: 60,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"작업일자",			Type:"Date",	       	SaveName:"iljai",				Width: 80,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"포스번호", 			Type:"Text",	       	SaveName:"posno",	    		Width: 80,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"거래번호", 			Type:"Text",	       	SaveName:"grNo",	    		Width: 80, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"시퀀스",				Type:"Text",	       	SaveName:"seqNo",				Width: 50,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"판매구분",			Type:"Text",      	   	SaveName:"pan",					Width: 80, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"시분", 				Type:"Text",	       	SaveName:"hhmm",				Width: 80,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"사원번호", 			Type:"Text",	       	SaveName:"sawonno",		        Width: 80,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"상품풀번호",			Type:"Text",	       	SaveName:"srcmkcd",				Width: 110, 	Align:"Center", Edit:0, Sort:0}
			  , {Header:"수량",				Type:"Float",	       	SaveName:"saleqty",    			Width: 50, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"매출액",				Type:"Float",	       	SaveName:"saleamt",				Width: 80,  	Align:"Right",  Edit:0, Sort:0,Format:"#,##0원"}
			  , {Header:"세일/할인",			Type:"Float",	       	SaveName:"salehalin",			Width: 80, 		Align:"Right",  Edit:0, Sort:0,Format:"#,##0원"}
			  , {Header:"에누리", 			Type:"Float",	       	SaveName:"saleenury",			Width: 80, 		Align:"Right",  Edit:0, Sort:0,Format:"#,##0원"}
			  , {Header:"구매포인트 사용액", 		Type:"Float",	       	SaveName:"pointamt",			Width: 120, 	Align:"Right",  Edit:0, Sort:0,Format:"#,##0원"}
			  , {Header:"쿠폰", 				Type:"Float",	      	SaveName:"coupon",				Width: 80, 		Align:"Right",  Edit:0, Sort:0,Format:"#,##0원"}
			  , {Header:"이벤트구분",			Type:"Text",	       	SaveName:"eventgb",				Width: 100,  	Align:"Center", Edit:0, Sort:0}
			  , {Header:"원주문작업일자",		Type:"Date",	       	SaveName:"wonIlja",				Width: 100, 	Align:"Center", Edit:0, Sort:0}
			  , {Header:"원주문 포스번호",		Type:"Text",	       	SaveName:"wonPosno",			Width: 100, 	Align:"Center", Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:"원주문 거래번호",		Type:"Text",	      	SaveName:"wonGrNo",			  	Width: 100, 	Align:"Center", Edit:0, Sort:0}
			  , {Header:"등록일",				Type:"Date",	       	SaveName:"regdate",		    	Width: 80, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"오류상태",			Type:"Text",	       	SaveName:"errorstatus",			Width: 60, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"작업구분",			Type:"Text",	      	SaveName:"workdiv",		        Width: 50, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"주문번호",			Type:"Text",	       	SaveName:"ordno",   			Width: 120, 	Align:"Center", Edit:0, Sort:0}
			  , {Header:"매출일련번호",			Type:"Text",	       	SaveName:"itemsno",		    	Width: 90, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"몰명",				Type:"Text",	       	SaveName:"mallname",		    Width: 80, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"작업일자",			Type:"Date",	       	SaveName:"workday",		    	Width: 80, 		Align:"Center", Edit:0, Sort:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:"차수",				Type:"Text",	       	SaveName:"chasu",		    	Width: 50, 		Align:"Center", Edit:0, Sort:0}
			  , {Header:"안심키",				Type:"Text",	       	SaveName:"safeKey",	    		Width: 150, 	Align:"Center", Edit:0, Sort:0}
			  , {Header:"이벤트포인트사용액",   	Type:"Float",			SaveName:"eventpointamt",		Width: 120,		Align:"Center", Edit:0, Sort:0,Format:"#,##0원"}
		];

		//Grid 초기화
		IBS_InitSheet(salesSheet, initSheet);
		// Grid 건수 정보 표시
		salesSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		salesSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		//salesSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		salesSheet.SetExtendLastCol(1);
		
		
		salesSheet.InitDataCombo(0, "siteNo",  this.siteCombo .text,   this.siteCombo.code );		//사이트 콤보
	   
		
		salesSheet_OnSearchEnd = function(Code, Msg, StCode, StMsg, Response){
			_salesmain.checkRowCount(salesSheet.RowCount());
			_salesmain.getIfMaechulSelector();
		};
	}
	
	/**
	 * 사이트 URL
	 */
	_salesmain.ABC_FO_URL = "";
	_salesmain.OTS_FO_URL = "";
	
	_salesmain.SITE_NO_ART = "";
	_salesmain.SITE_NO_OTS = "";
	
	
	
	_salesmain.fromDash = "";
	
	/**
	 * dash 보드로 부터 접속 시
	 */
	_salesmain.fromDashboard = function(){
		
		if( !abc.text.allNull(_salesmain.fromDash) ){
			if( _salesmain.fromDash == "Y" ){
				_salesmain.salesDoAction('search');
			}
		}
	}
	
	
	
	/**
	 * 결제구분 선택
	 */
	_salesmain.deviceCodeCheck = function(){
		
		/** 결제수단 변경 함수 */
		$("#chkDeviceCodeModuleAll").click(function(){
			if($(this).is(":checked")){
				$("input[custom=deviceCode]").prop("checked",true);
			} else {
				$("input[custom=deviceCode]").prop("checked",false);
			}
		})
		
		$("input[custom=deviceCode]").each(function(){
			$(this).click(function(){
				if($(this).is(":checked")){
					var unChecked = $("input[custom=deviceCode]:not(:checked)");
					if (unChecked.length == 0) {
						$("#chkDeviceCodeModuleAll").prop("checked",true);					
					}
				} else {
					$("#chkDeviceCodeModuleAll").prop("checked",false);
				}
			})
		})
	}
	
	//페이지선택시
	$("#pageCount").change(function(){
		_salesmain.salesDoAction('search');
	});
	
	/**
	 *주문번호 선택 후, 입력 input
	 */
	_salesmain.asOrderNoSelect = function(){
		
		// AS 및 주문번호 selectBox 선택 시
		$("#asOrderNoSelect").change(function(){
			$("#asOrderNoInp").off();
			$("#asOrderNoInp").val("");
			// 상품번호로 검색할 때
			if($("#asOrderNoSelect option:selected").val() == "srcmkcd"){
				$("#asOrderNoInp").attr("maxlength", "13");
				$("#asOrderNoInp").attr("name", "srcmkcd").on("keyup", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
				});
			} 
				// 주문번호로 검색할 때 
			else if($("#asOrderNoSelect option:selected").val() == "ordno") {
				$("#asOrderNoInp").attr("maxlength", "13");
				$("#asOrderNoInp").attr("name", "ordno").on("keyup", function() {
					$(this).val($(this).val().replace(/[^0-9]/g,""));
				});
			}
		});
	}
	
	
	// 카운트 존재할때 
	_salesmain.checkRowCount = function(count) {
		if(count <= 1) {
			$('#downExcel').addClass('disabled');
		} else {
			$('#downExcel').removeClass('disabled');
		}
	};
	
	// 조건별 SUM 
	_salesmain.getIfMaechulSelector = function(){
		$.ajax({
			type:"post",
			url: "/sales/getIfMaechulSelector",
			data: $("#frmSearch").serialize()
		}).done(function(data, textStatus, jqXHR) {
			$("#saleAmtSum").text(abc.text.isMakeComma(data.saleAmtSum) + "원");
			$("#saleEnurySum").text(abc.text.isMakeComma(data.saleEnurySum) + "원");
			$("#pointAmtSum").text(abc.text.isMakeComma(data.pointAmtSum) + "원");
			$("#eventPointAmtSum").text(abc.text.isMakeComma(data.eventPointAmtSum) + "원");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
		
	}
	
		
	
	/**
	 * 자사 매출 목록 Action 관리
	 */
	_salesmain.salesDoAction = function(sAction){
		switch(sAction) {
		
		case "search" :
			
			// 날짜 및 input valition 체크 
			if(!_salesmain.searchValidation()){
				return;
			}
			
			// ibsheet 페이지 변수 
			var pageCount = $('#pageCount').val();
			
			// 체크값에 따라 매출, 취소 구분값
			var allChk =  $('#chkdeviceTypeAll').prop('checked');  
			if(allChk){
				$('#salesCnclGbnType').val('all');
			}else{
				$("input[name=deviceTypeCodeArr]:checked").each(function() {
					var checkBoxSel = $(this).val();
					if(checkBoxSel == "order"){
						$('#salesCnclGbnType').val('order');
					}else if(checkBoxSel == "return"){
						$('#salesCnclGbnType').val('return');
					}else{
						$('#salesCnclGbnType').val('all');
					}
				});
			}
			
			var param = { url : "/sales/read-sales-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "salesSheet" };
		
			DataSearchPaging(param);
			break;
		}
	}
	
	
	
	/**
	 * 한 페이지의 전체 매출 목록 엑설로 다운로드
	 */
	_salesmain.downloadAllExcel = function() {
		if(salesSheet.RowCount() == 0) {
			alert("다운로드 할 데이타가 없습니다.");
		}else{
			frmSearch.action = "/sales/download-sales-all-excel";
			frmSearch.submit();
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
	 *  검색 실행
	 */
	_salesmain.searchValidation = function(){
		// 검색시 날짜 조건 
		if(!abc.date.searchValidate()){
			$("a[name=per1Month]").trigger("click");
			return false;
		}
		// 검색 기간 validate
		var dateDiff = abc.date.dateDiffAbs($("#fromDate").val(), $("#toDate").val());
		
		if($("input:checkbox[name=deviceTypeCodeArr]:checked").length == 0){
			alert("판매구분중 하나는 선택되어야 합니다.");
			return false;
		}
		
		if(dateDiff > 365) {
			alert("검색 기간은 1년을 넘을 수 없습니다.");
			$("a[name=per1Month]").trigger("click");
			return false;
		}
		//주문번호  자릿수 체크 
		if($("#asOrderNoSelect option:selected").val() == "ordno") {
			if(abc.text.isLimitLength($("#asOrderNoInp").val(), 13)){
				alert("주문번호 최대 자리수를 초과하였습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
		
		//상품번호
		if($("#asOrderNoSelect option:selected").val() == "srcmkcd") {
			if(abc.text.isLimitLength($("#asOrderNoInp").val(), 15)){
				alert("상품번호  최대 자리수를 초과하였습니다.");
				$("#asOrderNoInp").focus();
				return false;
			}
		}
		return true;
	}
	
	// 초기 이벤트 
	_salesmain.event = function(){
		// 전체 선택 했을경우
		$('#chkdeviceTypeAll').on("click", function(){
			var checkFlag 	= $(this).is(":checked");
			console.log("1",checkFlag);
			$("[id^=chkdeviceType0]").each(function(){
				$(this).prop('checked', checkFlag);
				console.log("2",checkFlag);
			});
		});
		// 매출을 선택 경우 
		$('#chkdeviceType01').click(function() {
			var ischecked = $('#chkdeviceType01').prop('checked',true);
			$('#chkdeviceType02').prop('checked',false);
			$('#chkdeviceTypeAll').prop('checked',false);
		});
		// 취소를 선택 경우 
		$('#chkdeviceType02').click(function() {
			$('#chkdeviceTypeAll').prop('checked',false);
			$('#chkdeviceType01').prop('checked',false);
			var ischecked = $('#chkdeviceType02').prop('checked',true);
		});
	}
	
	// 폼 초기화 
	_salesmain.searchFormReset = function() {
		
		$('#frmSearch').each(function(){
			this.reset();
			$("a[name^=per1Month]").trigger("click");
			$("#saleAmtSum").text("-");
			$("#saleEnurySum").text("-");
			$("#pointAmtSum").text("-");
			$("#eventPointAmtSum").text("-");
		});
	}
	
})();