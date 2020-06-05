/***
 * 약관설정 
 * 
 */
(function() {

	var _terms = abc.object.createNestedObject(window,"abc.biz.cmm.terms");
	/**
	 * 		약관설정 
	 * */
	
	
	_terms.termsOfUseSheet = function(){
		//<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("termsOfUseGrid"), "termsOfUseGrid");
		//<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var termsOfUseSheet = {};
		var usePageCount = $('#pageCount').val();
		
		termsOfUseSheet.Cfg = {SearchMode:smServerPaging2, Page:usePageCount, DeferredVScroll:1, MaxSort:1};
		termsOfUseSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		termsOfUseSheet.Cols = [
			  {Header:"" ,		Type:"Status",	SaveName:"status",			Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관순번",	Type:"Int",		SaveName:"termsSeq",		Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관코드",	Type:"Text",	SaveName:"termsDtlCode",	Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"상세번호",	Type:"Int",		SaveName:"termsDtlSeq",		Width: 8,	Align:"Center",	Edit:0, Hidden:1}
			, {Header:"번호",		Type:"Seq",		SaveName:"",				Width: 8,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"약관구분",	Type:"Text",	SaveName:"termsCodeName",	Width: 20,	Align:"Left",	Edit:0 			}
			, {Header:"시행일자",	Type:"Date",	SaveName:"termsApplyYmd",	Width: 30,	Align:"Left",	Edit:0, Format:"yyyy년MM월dd일", FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"시행여부",	Type:"Text",	SaveName:"liveDisp",		Width: 15,	Align:"Center",	Edit:0, Sort:0}
			, {Header:"작성자",	Type:"Text",	SaveName:"moderDpName",		Width: 15,	Align:"Center",	Edit:0, Sort:0}
			, {Header:"작성일시",	Type:"Date",	SaveName:"modDtm",			Width: 20,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		//<%-- Grid 초기화 --%>
		IBS_InitSheet(termsOfUseGrid , termsOfUseSheet);
		//<%-- Grid 건수 정보 표시 --%>
		termsOfUseGrid.SetCountPosition(3);
		//<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		termsOfUseGrid.SetPagingPosition(2);
		//<%-- Grid width 자동 조절 --%>
		termsOfUseGrid.FitColWidth();
		//<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		termsOfUseGrid.SetExtendLastCol(1);
		
//		abc.biz.cmm.terms.doAction('readTermsOfUseGrid');
	}
	
	_terms.privacySheet = function(){
		//<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("privacyGrid"), "privacyGrid");
		//<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var privacySheet = {};
		var privacyPageCount = $('#privacyPageCount').val();
		
		privacySheet.Cfg = {SearchMode:smServerPaging2, Page:privacyPageCount, DeferredVScroll:1, MaxSort:1};
		privacySheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		privacySheet.Cols = [
			  {Header:"" ,		Type:"Status",	SaveName:"status",			Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관순번",	Type:"Int",		SaveName:"termsSeq",		Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관코드" ,	Type:"Text",	SaveName:"termsDtlCode",	Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"상세번호",	Type:"Int",		SaveName:"termsDtlSeq",		Width: 8,	Align:"Center",	Edit:0, Hidden:1}
			, {Header:"번호",		Type:"Seq",		SaveName:"",				Width: 8,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"시행일자",	Type:"Date",	SaveName:"termsApplyYmd",	Width: 30,	Align:"Left",	Edit:0, Format:"yyyy년MM월dd일", FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"시행여부",	Type:"Text",	SaveName:"liveDisp",		Width: 15,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"작성자",	Type:"Text",	SaveName:"moderDpName",		Width: 15,	Align:"Center",	Edit:0,	Sort:0	}
			, {Header:"작성일시",	Type:"Date",	SaveName:"modDtm",			Width: 20,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		//<%-- Grid 초기화 --%>
		IBS_InitSheet(privacyGrid , privacySheet);
		//<%-- Grid 건수 정보 표시 --%>
		privacyGrid.SetCountPosition(3);
		//<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		privacyGrid.SetPagingPosition(2);
		//<%-- Grid width 자동 조절 
		privacyGrid.FitColWidth();
		//<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		privacyGrid.SetExtendLastCol(1);
		
//		abc.biz.cmm.terms.doAction('readPrivacyGrid');
	}
	
	_terms.signUpSheet = function(){
		//<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("signUpGrid"), "signUpGrid");
		//<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var signUpSheet = {};
		var signupPageCount = $('#signupPageCount').val();
		
		signUpSheet.Cfg = {SearchMode:smServerPaging2, Page:signupPageCount, DeferredVScroll:1, MaxSort:1};
		signUpSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		signUpSheet.Cols = [
			  {Header:"" ,		Type:"Status",	SaveName:"status",			Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관순번",	Type:"Int",		SaveName:"termsSeq",		Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관코드" ,	Type:"Text",	SaveName:"termsDtlCode",	Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"상세번호",	Type:"Int",		SaveName:"termsDtlSeq",		Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"번호",		Type:"Seq",		SaveName:"",				Width: 8,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"회원구분",	Type:"Text",	SaveName:"termsCodeName",	Width: 15,	Align:"Center",	Edit:0}
			, {Header:"시행일자",	Type:"Date",	SaveName:"termsApplyYmd",	Width: 30,	Align:"Left",	Edit:0, Format:"yyyy년MM월dd일", FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"시행여부",	Type:"Text",	SaveName:"liveDisp",		Width: 15,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"작성자",	Type:"Text",	SaveName:"moderDpName",		Width: 15,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"작성일시",	Type:"Date",	SaveName:"modDtm",			Width: 20,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		//<%-- Grid 초기화 --%>
		IBS_InitSheet(signUpGrid , signUpSheet);
		//<%-- Grid 건수 정보 표시 --%>
		signUpGrid.SetCountPosition(3);
		//<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		signUpGrid.SetPagingPosition(2);
		//<%-- Grid width 자동 조절 --%>
		signUpGrid.FitColWidth();
		//<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		signUpGrid.SetExtendLastCol(1);
		
//		abc.biz.cmm.terms.doAction('readSignUpGrid');
	}
	
	_terms.orderSheet = function(){
		//<%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("orderGrid"), "orderGrid");
		//<%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var orderSheet = {};
		var orderPageCount = $('#orderPageCount').val();
		
		orderSheet.Cfg = {SearchMode:smServerPaging2, Page:orderPageCount, DeferredVScroll:1, MaxSort:1};
		orderSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};	
		orderSheet.Cols = [
			  {Header:"" ,		Type:"Status",	SaveName:"status",			Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관순번",	Type:"Int",		SaveName:"termsSeq",		Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"약관코드" ,	Type:"Text",	SaveName:"termsDtlCode",	Width: 0,	Align:"",		Edit:0, Hidden:1}
			, {Header:"상세번호",	Type:"Int",		SaveName:"termsDtlSeq",		Width: 8,	Align:"Center",	Edit:0, Hidden:1}
			, {Header:"번호",		Type:"Seq",		SaveName:"",				Width: 8,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"회원구분",	Type:"Text",	SaveName:"termsCodeName",	Width: 15,	Align:"Center",	Edit:0}
			, {Header:"시행일자",	Type:"Date",	SaveName:"termsApplyYmd",	Width: 30,	Align:"Left",	Edit:0, Format:"yyyy년MM월dd일", FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header:"시행여부",	Type:"Text",	SaveName:"liveDisp",		Width: 15,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"작성자",	Type:"Text",	SaveName:"moderDpName",		Width: 15,	Align:"Center",	Edit:0, Sort:0	}
			, {Header:"작성일시",	Type:"Date",	SaveName:"modDtm",			Width: 20,	Align:"Center",	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		//<%-- Grid 초기화 --%>
		IBS_InitSheet(orderGrid , orderSheet);
		//<%-- Grid 건수 정보 표시 --%>
		orderGrid.SetCountPosition(3);
		//<%-- Grid 페이지 네비게이션 버튼 표시 --%>
		orderGrid.SetPagingPosition(2);
		//<%-- Grid width 자동 조절 --%>
		orderGrid.FitColWidth();
		//<%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		orderGrid.SetExtendLastCol(1);
		
//		abc.biz.cmm.terms.doAction('readOrderGrid');
	}
	
	_terms.saveCheck = false;
	
	_terms.doAction = function(sAction){
		switch(sAction) {
				
			//<%-- 조회 --%>
			case "readTermsOfUseGrid" :
				var usePageCount = $("#usePageCount").val();
				
				var param = { url : "/cmm/terms/read-terms-grid"
					, onePageRow : usePageCount
					, subparam : FormQueryStringEnc(document.termsOfUseForm)
					, CPage : $('#useListPageNum').val()
					, sheet : "termsOfUseGrid" };
				
				DataSearchPaging(param);
				$('#useListPageNum').val('1');
				break;
			//개인정보 취급방침 조회
			case "readPrivacyGrid" :
				var privacyPageCount = $("#privacyPageCount").val();
				
				var param = { url : "/cmm/terms/read-terms-grid"
					, onePageRow : privacyPageCount
					, subparam : FormQueryStringEnc(document.privacyForm)
					, CPage : $('#privacyListPageNum').val()
					, sheet : "privacyGrid" };
				
				DataSearchPaging(param);
				$('#privacyListPageNum').val('1');
				break;
			//회원가입동의 조회
			case "readSignUpGrid" :
				var signupPageCount = $("#signupPageCount").val();
				
				var param = { url : "/cmm/terms/read-terms-grid"
					, onePageRow : signupPageCount
					, subparam : FormQueryStringEnc(document.signUpForm)
					, CPage : $('#signupListPageNum').val()
					, sheet : "signUpGrid" };
				
				DataSearchPaging(param);
				$('#signupListPageNum').val('1');
				break;
			//주문동의 조회
			case "readOrderGrid" :
				var orderPageCount = $("#orderPageCount").val();
				
				var param = { url : "/cmm/terms/read-terms-grid"
					, onePageRow : orderPageCount
					, subparam : FormQueryStringEnc(document.orderForm)
					, CPage : $('#orderListPageNum').val()
					, sheet : "orderGrid" };
				
				DataSearchPaging(param);
				$('#orderListPageNum').val('1');
				break;
			//목록 버튼 
			case "pageBack" :
				frmSearch.action = "/cmm/terms"
				
				if(abc.biz.cmm.terms.saveCheck){
					if(abc.consts.TERMS_TYPE_CODE_TERMSOFUSE == $("#termsTypeCode").val() || abc.consts.TERMS_TYPE_CODE_PRIVACY == $("#termsTypeCode").val()){
						if(abc.text.allNull(CKEDITOR.instances.termsInfo.getData())){
							_terms.tabIndexSet();
						}else{
							if(confirm("등록중인 내용은 저장되지 않습니다. 돌아가시겠습니까?")){
								_terms.tabIndexSet();
							}
						}
					}else{
						var state = true;
						
						for(var i = 1; i <= $(".w100").length ;i++){
							if(!abc.text.allNull(CKEDITOR.instances['termsInfo_'+i].getData())){
								state = false;
							}
						}
						if(state){
							_terms.tabIndexSet();
						}else{
							if(confirm("등록중인 내용은 저장되지 않습니다. 돌아가시겠습니까?")){
								_terms.tabIndexSet();
							}
						}
					}
				}else{
					if(confirm("수정중인 내용은 저장되지 않습니다. 돌아가시겠습니까?")){
						frmSearch.submit();
					}
				}
				
				break;
			//이용약관 저장
			case "termsOfUseSaveCheck" :
				var message = "";
				if(abc.biz.cmm.terms.saveCheck){
					message = "등록하시겠습니까?";
				}else{
					message = "수정하시겠습니까?";
				}

				if(this.termsOfUseValidate()){
					if(confirm(message)){
						if(!abc.biz.cmm.terms.saveCheck){
							if(abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) == 0){
								$("#directChange").val(abc.consts.BOOLEAN_TRUE);
							}
						}
						$("#termsInfo").val(CKEDITOR.instances.termsInfo.getData());
						$("select[name='termsDtlCode']").attr("disabled", false);
						abc.biz.cmm.terms.createTermsOfUsePrivacy();
					}
				}
				
				break;
			//개인정보 취급방침 저장
			case "privacySaveCheck" :
				var message = "";
				
				if(abc.biz.cmm.terms.saveCheck){
					message = "등록하시겠습니까?";
				}else{
					message = "수정하시겠습니까?";
				}
				
				if(this.privacyValidate()){
					if(confirm(message)){
						if(!abc.biz.cmm.terms.saveCheck){
							if(abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) == 0){
								$("#directChange").val(abc.consts.BOOLEAN_TRUE);
							}
						}
						$("#termsInfo").val(CKEDITOR.instances.termsInfo.getData());
						abc.biz.cmm.terms.createTermsOfUsePrivacy();
					}
				}
				
				break;
			//회원가입 동의 저장
			case "signUpSaveCheck" :
				if(this.signUpValidate()){
					if(confirm("등록하시겠습니까?")){
						
						abc.biz.cmm.terms.createSignUpOrder();
					}
				}
				break;
			//주문 동의 저장
			case "orderSaveCheck" :
				if(this.orderValidate()){
					if(confirm("등록하시겠습니까?")){
						abc.biz.cmm.terms.createSignUpOrder();
					}
				}
				break;
			//회원가입동의 수정 체크
			case "signUpUpdateCheck" :	
				if(this.signUpValidate()){
					if(confirm("수정하시겠습니까?")){
						if(abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) == 0){
							$("#directChange").val(abc.consts.BOOLEAN_TRUE);
						}
						$("select[name='termsDtlCode']").attr("disabled", false);
						abc.biz.cmm.terms.createSignUpOrder();
					}
				}
				break;
			//주문동의 수정 체크
			case "orderUpdateCheck" :	
				if(this.orderValidate()){
					if(confirm("수정하시겠습니까?")){
						if(abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) == 0){
							$("#directChange").val(abc.consts.BOOLEAN_TRUE);
						}
						$("select[name='termsDtlCode']").attr("disabled", false);
						abc.biz.cmm.terms.createSignUpOrder();
					}
				}
				break;
			//약관 삭제
			case "deleteTerms" :
				if(confirm("삭제하시겠습니까?")){
					abc.biz.cmm.terms.deleteTerms();
				}
				break;
			
		}
	}
	//중간값 세팅
	_terms.middleValueSet = function(){
		if("선택"==$("#termsDtlCode").val()){
			$("#midlleValue").text("약관설정");
		}else{
			$("#middleValue").text($("#termsDtlCode").val());
		}
		
	}
	//에디터 데이터 빼오기
	_terms.setTermsInfo = function(sIndex){
		for(var i=1; i<=sIndex;i++){
			$("#termsInfo_"+i).val(CKEDITOR.instances['termsInfo_'+i].getData());
		}
	}
	//텝 번호를 매겨준다.
	_terms.tabIndexSet = function(){
		var param = abc.param.getParams().termsTypeCode;
		var menuNoParam = abc.param.getParams().menuNo;
		if(param=='10000'){
			location.href = "/cmm/terms?tabIndex=0"+"&menuNo="+menuNoParam;
		}
		if(param=='10001'){
			location.href = "/cmm/terms?tabIndex=1"+"&menuNo="+menuNoParam;
		}
		if(param=='10002'){
			location.href = "/cmm/terms?tabIndex=2"+"&menuNo="+menuNoParam;
		}
		if(param=='10003'){
			location.href = "/cmm/terms?tabIndex=3"+"&menuNo="+menuNoParam;
		}
	}
	//목록이나 저장후에 페이지 이동후 텝 포커스
	_terms.tabFocus = function(){
		var tIndex = abc.param.getParams().tabIndex;
		if(tIndex==0){
			$("#tabDiv").tabs("option", "active", tIndex);
		}
		if(tIndex==1){
			$("#tabDiv").tabs("option", "active", tIndex);
		}
		if(tIndex==2){
			$("#tabDiv").tabs("option", "active", tIndex);
		}
		if(tIndex==3){
			$("#tabDiv").tabs("option", "active", tIndex);
		}
	}
	
	$("#termsApplyYmd").datepicker({
		constrainInput: false ,
		showOn: "both" ,
		minDate : "0"
	});
	
	//시행일자 숫자만 입력
	$("#termsApplyYmd").off().on('input', function(event){
		abc.text.validateOnlyNumber(this);
	});
	
	//이용약관 저장시에 작성칸 숨김
	_terms.saveHide = function(){
		$("#saveHide").hide();
		$("#deleteHide").hide();
		abc.biz.cmm.terms.saveCheck = true;
	}
	
	//수정시에 화면 셋팅
	_terms.termsOfUseSet = function(){
		abc.biz.cmm.terms.saveCheck = false;
		$("select[name='termsDtlCode']").attr("disabled", true);
		
	}
	//데이터 피커 셋팅
	_terms.datePickerSet = function(dateP){
		dateP = dateP.slice(0,4)+"."+dateP.slice(4);
		dateP = dateP.slice(0,7)+"."+dateP.slice(7);
		
		return dateP;
	}
	
	
	//추가 버튼 누를때 영역 호출
	_terms.getSignUpListArea = function(){
		var $form = $("#signUpForm");
		var idx = Number($('#signUpListIndex').val()) + 1; 
	  	$('#signUpListIndex').val(idx);
	  	
	  	var request = $.ajax({
			  type: "POST"
			, url: "/cmm/terms/signup-list-area"
			, data: $form.serialize()
			, dataType: "html"
			, async: false
	  	});
	  	request.done(function(data) {
	  		$("#signUpListArea").append(data);
	  		var result = _terms.getLoopCount('table', 'signUpListArea');
	  		if(result.cnt >= 1){
	  			
	  			$('[id^="signUpListArea"]').each(function(i,e) { // 
	  				var id 	= $(e).attr('id');
	  				var idx = id.substring(id.lastIndexOf('_')+1);
	  				
	  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
	  					$('#btnTableAdd_'+idx).show();
	  					$('#btnTableDelete_'+idx).hide();
	  				}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
	  					if(idx == result.lastIdx){	// 맨 마지막
	  						$('#btnTableAdd_'+idx).show();
		  					$('#btnTableDelete_'+idx).show();
	  					}else{
	  						$('#btnTableAdd_'+idx).hide();
		  					$('#btnTableDelete_'+idx).show();
	  					}
	  				}
	  			});
	  		}
			CKEDITOR.replace("termsInfo_"+idx, {
				width:"100%",
				height:"300px"
			});
	  	});
	  	request.fail(function(jqXHR, textStatus) {
	  		alert("에러가 발생했습니다.");
            var idx	= Number($('#signUpIndex').val()) - 1; 
    	  	$('#signUpIndex').val(idx);
	  	});
	}
	//회원가입동의 상세보기시에 리스트 영역 호출
	_terms.getSignUpDetailArea = function(termsSeq, termsDtlSeq){
		var $form = $("#signUpForm");
		var idx = Number($('#signUpListIndex').val()) + 1; 
	  	$('#signUpListIndex').val(idx);
	  	$('#termsSeq').val(termsSeq);
	  	$('#termsDtlSeq').val(termsDtlSeq);
	  	
	  	var request = $.ajax({
					        type: "POST"
					      , url: "/cmm/terms/signup-detail-area"
					      , data: $form.serialize()
					      , dataType: "html"
				          , async: false
	  	});
	  	request.done(function(data) {
	  		$("#signUpListArea").append(data);
	  		var result = _terms.getLoopCount("table", "signUpListArea");
	  		
	  		if(result.cnt >= 1){
	  			
	  			$('[id^="signUpListArea"]').each(function(i,e) { // 
	  				var id 	= $(e).attr('id');
	  				var idx = id.substring(id.lastIndexOf('_')+1);
	  				
	  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
	  					$('#btnTableAdd_'+idx).show();
	  					$('#btnTableDelete_'+idx).hide();
	  				}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
	  					if(idx == result.lastIdx){	// 맨 마지막
	  						$('#btnTableAdd_'+idx).show();
		  					$('#btnTableDelete_'+idx).show();
	  					}else{
	  						$('#btnTableAdd_'+idx).hide();
		  					$('#btnTableDelete_'+idx).show();
	  					}
	  				}
	  			});
	  		}
			CKEDITOR.replace("termsInfo_"+idx, {
				width:"100%",
				height:"300px"
			});
			
			var reqAgreeYn = $(data).children("#reqAgreeYnValue_"+idx).val();
			
			$("input:radio[name='reqAgreeYn_"+idx+"'][value='"+reqAgreeYn+"']").attr('checked', true);
	  	});
	  	request.fail(function(jqXHR, textStatus) {
	  		alert("에러가 발생했습니다.");
            var idx	= Number($('#signUpIndex').val()) - 1; 
    	  	$('#signUpIndex').val(idx);
	  	});
	}
	
	//회원가입동의 삭제 버튼 누를떄 영역 삭제
	_terms.deleteSignUpList = function(deleteIndex){
		var result = _terms.getLoopCount('table', 'signUpListArea');
		if(result.cnt > 1){
			var idx = deleteIndex;
			$('#btnArea_'+idx).remove();
			$('#signUpListArea_'+idx).remove();
			result = _terms.getLoopCount('table', 'signUpListArea');
			
			if(result.cnt >= 1){
	  			$('[id^="signUpListArea"]').each(function(i,e) { // 
	  				var id 	= $(e).attr('id');
	  				var idx = id.substring(id.lastIndexOf('_')+1);
	  				
	  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
	  					$('#btnTableAdd_'+idx).show();
	  					$('#btnTableDelete_'+idx).hide();
	  				}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
	  					if(idx == result.lastIdx){	// 맨 마지막
	  						$('#btnTableAdd_'+idx).show();
		  					$('#btnTableDelete_'+idx).show();
	  					}else{
	  						$('#btnTableAdd_'+idx).hide();
		  					$('#btnTableDelete_'+idx).show();
	  					}
	  				}
	  			});
	  		}
		}else{
			alert("삭제할 수 없습니다.");
		}
	}
	//주문동의 삭제 버튼 누를때 영역 삭제
	_terms.deleteOrderList = function(deleteIndex){
		var result = _terms.getLoopCount('table', 'orderListArea');
		if(result.cnt > 1){
			var idx = deleteIndex;
			$('#btnArea_'+idx).remove();
			$('#orderListArea_'+idx).remove();
			result = _terms.getLoopCount('table', 'orderListArea');
			
			if(result.cnt >= 1){
				$('[id^="orderListArea"]').each(function(i,e) { // 
					var id 	= $(e).attr('id');
					var idx = id.substring(id.lastIndexOf('_')+1);
					
					if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
						$('#btnTableAdd_'+idx).show();
						$('#btnTableDelete_'+idx).hide();
					}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
						if(idx == result.lastIdx){	// 맨 마지막
							$('#btnTableAdd_'+idx).show();
							$('#btnTableDelete_'+idx).show();
						}else{
							$('#btnTableAdd_'+idx).hide();
							$('#btnTableDelete_'+idx).show();
						}
					}
				});
			}
		}else{
			alert("삭제할 수 없습니다.");
		}
	}
	
	//주문동의 추가버튼 누를때 리스트 영역 호출
	_terms.getOrderListArea = function(){
		var $form = $("#orderForm");
		var idx = Number($('#orderListIndex').val()) + 1; 
	  	$('#orderListIndex').val(idx);
	  	
	  	var request = $.ajax({
					        type: "POST"
					      , url: "/cmm/terms/order-list-area"
					      , data: $form.serialize()
					      , dataType: "html"
				          , async: false
	  	});
	  	request.done(function(data) {
	  		$("#orderListArea").append(data);
	  		var result = _terms.getLoopCount('table', 'orderListArea');
	  		if(result.cnt >= 1){
	  			
	  			$('[id^="orderListArea"]').each(function(i,e) { // 
	  				var id 	= $(e).attr('id');
	  				var idx = id.substring(id.lastIndexOf('_')+1);
	  				
	  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
	  					$('#btnTableAdd_'+idx).show();
	  					$('#btnTableDelete_'+idx).hide();
	  				}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
	  					if(idx == result.lastIdx){	// 맨 마지막
	  						$('#btnTableAdd_'+idx).show();
		  					$('#btnTableDelete_'+idx).show();
	  					}else{
	  						$('#btnTableAdd_'+idx).hide();
		  					$('#btnTableDelete_'+idx).show();
	  					}
	  				}
	  			});
	  		}
			CKEDITOR.replace("termsInfo_"+idx, {
			width:"100%",
			height:"300px"
			});
	  	});
	  	request.fail(function(jqXHR, textStatus) {
	  		alert("에러가 발생했습니다.");
            var idx	= Number($('#orderIndex').val()) - 1; 
    	  	$('#orderIndex').val(idx);
	  	});
	}
	//주문동의 상세보기시에 리스트 영역 호출
	_terms.getOrderDetailArea = function(termsSeq, termsDtlSeq){
		var $form = $("#orderForm");
		var idx = Number($('#orderListIndex').val()) + 1; 
	  	$('#orderListIndex').val(idx);
	  	$('#termsSeq').val(termsSeq);
	  	$('#termsDtlSeq').val(termsDtlSeq);
	  	
	  	var request = $.ajax({
					        type: "POST"
					      , url: "/cmm/terms/order-detail-area"
					      , data: $form.serialize()
					      , dataType: "html"
				          , async: false
	  	});
	  	request.done(function(data) {
	  		$("#orderListArea").append(data);
	  		var result = _terms.getLoopCount("table", "orderListArea");
	  		
	  		if(result.cnt >= 1){
	  			
	  			$('[id^="orderListArea"]').each(function(i,e) { // 
	  				var id 	= $(e).attr('id');
	  				var idx = id.substring(id.lastIndexOf('_')+1);
	  				
	  				if(result.cnt==1){	// 혜택영역이 1개일 때는 '추가' 버튼만 보이도록 함
	  					$('#btnTableAdd_'+idx).show();
	  					$('#btnTableDelete_'+idx).hide();
	  				}else{	// 혜택 영역이 2개 이상 일때는 맨마지막만 '추가'를 보이고 나머지는 '삭제' 버튼만 보이도록 함
	  					if(idx == result.lastIdx){	// 맨 마지막
	  						$('#btnTableAdd_'+idx).show();
		  					$('#btnTableDelete_'+idx).show();
	  					}else{
	  						$('#btnTableAdd_'+idx).hide();
		  					$('#btnTableDelete_'+idx).show();
	  					}
	  				}
	  			});
	  		}
			CKEDITOR.replace("termsInfo_"+idx, {
			width:"100%",
			height:"300px"
			});
			
			var reqAgreeYn = $(data).children("#reqAgreeYnValue_"+idx).val();
			
			$("input:radio[name='reqAgreeYn_"+idx+"'][value='"+reqAgreeYn+"']").attr('checked', true);
	  	});
	  	request.fail(function(jqXHR, textStatus) {
	  		alert("에러가 발생했습니다.");
            var idx	= Number($('#orderIndex').val()) - 1; 
    	  	$('#orderIndex').val(idx);
	  	});
	}
	
	//해당 태그와 이름에서 인덱스 파악
	_terms.getLoopCount = function(tag, obj){
		var result = new Object();
		result.cnt = 0;

		$(tag+'[id^="'+obj+'"]').each(function(i,e) { // 
			var id	= $(e).attr('id');
			var idx	= id.substring(id.lastIndexOf('_')+1);
			result.cnt += 1;
			result.lastIdx = idx;
		});
		return result;
	}
	
	// 이용약관 등록 
	_terms.createTermsOfUsePrivacy = function(){
		var $form;
		if(abc.consts.TERMS_TYPE_CODE_TERMSOFUSE == $("#termsTypeCode").val()){
			$form = $("#termsOfUseForm");
		}else{
			$form = $("#privacyForm");
		}

		$.ajax({
			type:"post",
			url: "/cmm/terms/create-termsofuse-privacy",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			if (data.result == 1) {
				alert("저장되었습니다.");
				var url = "/cmm/terms/"+"?menuNo="+abc.param.getParams().menuNo+"&tabIndex="+abc.param.getParams().tabIndex;
				$(location).attr('href', url);
			} else {
				alert(data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	//약관 삭제
	_terms.deleteTerms = function(){
		if($("#tabTitle").val()=="1"){
			var $form = $("#termsOfUseForm");
			var tabIndex = 0;
		}else if($("#tabTitle").val()=="2"){
			var $form = $("#privacyForm");
			var tabIndex = 1;
		}else if($("#tabTitle").val()=="3"){
			var $form = $("#signUpForm");
			var tabIndex = 2;
		}else if($("#tabTitle").val()=="4"){
			var $form = $("#orderForm");
			var tabIndex = 3;
		}
		
		$.ajax({
			type:"post",
			url: "/cmm/terms/remove-terms",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			if (data.result == 1) {
				alert("삭제되었습니다.");
				var url = "/cmm/terms/"+"?menuNo="+abc.param.getParams().menuNo+"&tabIndex="+abc.param.getParams().tabIndex;
				$(location).attr('href', url);
			} else {
				alert(data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	//회원가입 동의 등록
	_terms.createSignUpOrder = function(){
		var $form;
		if(abc.consts.TERMS_TYPE_CODE_SIGNUP == $("#termsTypeCode").val()){
			$form = $("#signUpForm");
		}else{
			$form = $("#orderForm");
		}
		$.ajax({
			type:"post",
			url: "/cmm/terms/create-signup-order",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			if (data.result == 1) {
				alert("등록되었습니다.");
				var url = "/cmm/terms/"+"?menuNo="+abc.param.getParams().menuNo+"&tabIndex="+abc.param.getParams().tabIndex;
				$(location).attr('href', url);
			} else {
				alert(data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	
	//약관삭제버튼
	$("#termsDelete").off().on('click', function() {
		if(abc.biz.cmm.terms.termsDispCheck()){
			abc.biz.cmm.terms.doAction("deleteTerms");
		}
	});
	
	//이용약관 그리드 검색버튼
	$("#btnSelectTermsOfUse").off().on('click', function(){
		abc.biz.cmm.terms.doAction('readTermsOfUseGrid');
	});
	//개인정보 취급방침 검색 버튼
	$("#btnSelectPrivacyGrid").off().on('click', function(){
		abc.biz.cmm.terms.doAction('readPrivacyGrid');
	});
	//회원가입동의 그리드 검색
	$("#signUpSearch").off().on('click', function() {
		abc.biz.cmm.terms.doAction("readSignUpGrid");
	});
	//주문동의 그리드 검색
	$("#orderSearch").off().on('click', function() {
		abc.biz.cmm.terms.doAction("readOrderGrid");
	});
	//목록 버튼
	$("#btnPageBack").off().on('click', function(){
		abc.biz.cmm.terms.doAction('pageBack');
	});
	
	//초기화버튼
	$("#privacyReload").off().on('click', function(){
		$.form("#privacyForm").reset();
	});
	//초기화버튼
	$("#signUpReload").off().on('click', function(){
		$.form("#signUpForm").reset();
	});
	//초기화버튼
	$("#orderReload").off().on('click', function(){
		$.form("#orderForm").reset();
	});
	
	//이용약관 등록버튼
	$("#btnCreateTermsOfUse").off().on('click', function(){
		location.href='/cmm/terms/termsofuse-form?termsTypeCode='+$("#btnCreateTermsOfUse").attr("value")+"&tabIndex=0"+"&menuNo="+abc.param.getParams().menuNo;
	});
	//개인정보취급방침 등록
	$("#btnCreatePrivacy").off().on('click', function(){
		location.href='/cmm/terms/privacy-statement-form?termsTypeCode='+$("#btnCreatePrivacy").attr("value")+'&termsDtlCode='+$("#privacyCode").val()+"&tabIndex=1"+"&menuNo="+abc.param.getParams().menuNo;
	});
	//회원가입동의 등록페이지
	$("#btnCreateSignUp").off().on('click', function() {
		location.href="/cmm/terms/signup-regist-form?termsTypeCode="+$("#btnCreateSignUp").attr("value")+"&tabIndex=2"+"&menuNo="+abc.param.getParams().menuNo;
	});
	//주문동의 등록페이지
	$("#btnCreateOrder").off().on('click', function() {
		location.href="/cmm/terms/order-regist-form?termsTypeCode="+$("#btnCreateOrder").attr("value")+"&tabIndex=3"+"&menuNo="+abc.param.getParams().menuNo;
	});
	

	//이용약관 그리드 상세
	_terms.readUseTermsDetail = function(termsSeq, termsDtlCode, termsDtlSeq){
		location.href = "/cmm/terms/read-termsofuse-detail?termsTypeCode="+$("#btnCreateTermsOfUse").attr("value")
		+"&termsSeq="+termsSeq+"&termsDtlSeq="+termsDtlSeq+"&termsDtlCode="+termsDtlCode+"&tabIndex=0"+"&menuNo="+abc.param.getParams().menuNo
		+"&searchTermsDtlCode="+$("#termsDtlCode").val()+"&listPageNum="+termsOfUseGrid.GetCurrentPage()+"&pageCount="+$("#usePageCount").val();
	}
	
	//개인정보 취급방침 그리드 상세
	_terms.readPrivacyTermsDetail = function(termsSeq, termsDtlCode, termsDtlSeq){
		location.href = "/cmm/terms/read-privacy-detail?termsTypeCode="+$("#btnCreatePrivacy").attr("value")
		+"&termsSeq="+termsSeq+"&termsDtlSeq="+termsDtlSeq+"&termsDtlCode="+termsDtlCode+"&tabIndex=1"+"&menuNo="+abc.param.getParams().menuNo
		+"&listPageNum="+privacyGrid.GetCurrentPage()+"&pageCount="+$("#privacyPageCount").val();
	}
	//회원가입동의  그리드 상세
	_terms.readSignUpTermsDetail = function(termsSeq, termsDtlCode, termsDtlSeq){
		location.href = "/cmm/terms/read-signup-detail?termsTypeCode="+$("#btnCreateSignUp").attr("value")
		+"&termsSeq="+termsSeq+"&termsDtlSeq="+termsDtlSeq+"&termsDtlCode="+termsDtlCode+"&tabIndex=2"+"&menuNo="+abc.param.getParams().menuNo
		+"&searchTermsDtlCode="+$("#signUpForm input[name='termsDtlCode']:checked").val()+"&listPageNum="+signUpGrid.GetCurrentPage()+"&pageCount="+$("#signupPageCount").val();
	}
	//주문동의  그리드 상세
	_terms.readOrderTermsDetail = function(termsSeq, termsDtlCode, termsDtlSeq){
		location.href = "/cmm/terms/read-order-detail?termsTypeCode="+$("#btnCreateOrder").attr("value")
		+"&termsSeq="+termsSeq+"&termsDtlSeq="+termsDtlSeq+"&termsDtlCode="+termsDtlCode+"&tabIndex=3"+"&menuNo="+abc.param.getParams().menuNo
		+"&searchTermsDtlCode="+$("#orderForm input[name='termsDtlCode']:checked").val()+"&listPageNum="+orderGrid.GetCurrentPage()+"&pageCount="+$("#orderPageCount").val();
	}
	//개인정보 취급방침 초기화
	_terms.reloadPrivacyForm = function(){
		$.form("#privacyForm").reset();
		CKEDITOR.instances.termsInfo.setData("");
	}
	//회원가입 동의 초기화
	_terms.reloadSignUpForm = function(termsDtlCode){
		$.form("#signUpForm").reset();
		if(abc.biz.cmm.terms.saveCheck == false){
			$("select[name='termsDtlCode']").attr("disabled", false);
			$("#termsDtlCode").val(termsDtlCode);
			$("select[name='termsDtlCode']").attr("disabled", true);
		}
		$(".ui-input").each(function(){
			$(this).val("");
		});
		
		for(var i=1; i<=$(".w100").length;i++){
			CKEDITOR.instances['termsInfo_'+i].setData("");
		}
	}
	//주문 동의 초기화
	_terms.reloadOrderForm = function(termsDtlCode){
		alert(termsDtlCode);
		$.form("#reloadOrderForm").reset();
		if(abc.biz.cmm.terms.saveCheck == false){
			$("#termsDtlCode").val(termsDtlCode);
			
		}else{
			$("#termsDtlCode").val("");
		}
		$(".ui-input").each(function(){
			$(this).val("");
		});
		
		for(var i=1; i<=$(".w100").length;i++){
			CKEDITOR.instances['termsInfo_'+i].setData("");
		}
	}
	
	//약관 삭제 전 해당약관이 전시중인지 여부 체크
	_terms.termsDispCheck = function(){
		var _termsApplyYmd = $("#orgTermsApplyYmd").val();
		if (abc.date.dateDiff(new Date(), _termsApplyYmd) <= 0) {
			alert("현재 시행중이거나 시행종료된 약관은 삭제할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	//이용약관 폼 validate
	_terms.termsOfUseValidate = function(){
		if("선택" == $("#termsDtlCode").val()){
			alert("약관구분을 선택해주세요");
			$("#termsDtlCode").focus();
			
			return false;
		}
		if(abc.text.allNull(CKEDITOR.instances.termsInfo.getData())){
			alert("내용을 입력해주세요.");
			$("#termsInfo").focus();
			
			return false;
		}
		if(abc.text.allNull($("#termsApplyYmd").val())){
			alert("시행일자를 지정해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		if(abc.biz.cmm.terms.saveCheck){
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) <= 0) {
				alert("시행일자는 익일부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}else{
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) < 0) {
				alert("시행일자는 오늘부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}
		if(!abc.text.allNull($("#termsApplyYmd").val())){
			var termsApplyYmd = abc.text.validateStringSignRemove($("#termsApplyYmd").val());
			if(!abc.text.isDate(termsApplyYmd)){
				alert("시행일자를 확인해주세요");
				$("#termsApplyYmd").focus();
				
				return false;
			}
		}else{
			alert("시행일자를 입력해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		return true;
	}
	
	//개인정보취급방침 validate
	_terms.privacyValidate = function(){
		if("선택"==$("#termsDtlCode").val()){
			alert("약관구분을 선택해주세요");
			$("#termsDtlCode").focus();
			
			return false;
		}
		if(abc.text.allNull(CKEDITOR.instances.termsInfo.getData())){
			alert("내용을 입력해주세요.");
			$("#termsInfo").focus();
			
			return false;
		}
		if(abc.text.allNull($("#termsApplyYmd").val())){
			alert("시행일자를 정해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		if(abc.biz.cmm.terms.saveCheck){
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) <= 0) {
				alert("시행일자는 익일부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}else{
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) < 0) {
				alert("시행일자는 오늘부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}
		if(!abc.text.allNull($("#termsApplyYmd").val())){
			var termsApplyYmd = abc.text.validateStringSignRemove($("#termsApplyYmd").val());
			if(!abc.text.isDate(termsApplyYmd)){
				alert("시행일자를 확인해주세요");
				$("#termsApplyYmd").focus();
				
				return false;
			}
		}else{
			alert("시행일자를 입력해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		return true;
	}
	
	//회원가입동의 폼 validate
	_terms.signUpValidate = function(){
		var state = true;
		if("선택" == $("#termsDtlCode").val()){
			alert("약관구분을 선택해주세요");
			$("#termsDtlCode").focus();
			
			return false;
		}
		$(".ui-input").each(function(){
			if(abc.text.isLimitLength($(this).val(), 100)){
				alert("항목명은 100byte를 넘기지 못합니다.");
				$(this).focus();
				state = false;
				return false;
			}else if(abc.text.allNull($(this).val())){
				alert("항목명을 입력해주세요.");
				$(this).focus();
				state = false;
				return false;
			}
		});
		if(state == false){
			return false;
		}
		$(".w100").each(function(){
			if(abc.text.allNull($(this).val())){
				alert("내용을 입력해주세요.");
				$(this).focus();
				state = false;
				return false;
			}
		});
		if(state == false){
			return false;
		}
		if(abc.text.allNull($("#termsApplyYmd").val())){
			alert("시행일자를 정해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		if(abc.biz.cmm.terms.saveCheck){
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) <= 0) {
				alert("시행일자는 익일부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}else{
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) < 0) {
				alert("시행일자는 오늘부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}
		if(!abc.text.allNull($("#termsApplyYmd").val())){
			var termsApplyYmd = abc.text.validateStringSignRemove($("#termsApplyYmd").val());
			if(!abc.text.isDate(termsApplyYmd)){
				alert("시행일자를 확인해주세요");
				$("#termsApplyYmd").focus();
				
				return false;
			}
		}else{
			alert("시행일자를 입력해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		return true;
	}
	
	//주문동의 폼 validate
	_terms.orderValidate = function(){
		var state = true;
		var currentDate =new Date().format();
		
		if("" == $("#termsDtlCode").val()){
			alert("약관구분을 선택해주세요");
			$("#termsDtlCode").focus();
			
			return false;
		}
		$(".ui-input").each(function(){
			if(abc.text.isLimitLength($(this).val(), 100)){
				alert("항목명은 100byte를 넘기지 못합니다.");
				$(this).focus();
				state = false;
				return false;
			}else if(abc.text.allNull($(this).val())){
				alert("항목명을 입력해주세요.");
				$(this).focus();
				state = false;
				return false;
			}
		});
		if(state == false){
			return false;
		}
		$(".w100").each(function(){
			if(abc.text.allNull($(this).val())){
				alert("내용을 입력해주세요.");
				$(this).focus();
				state = false;
				return false;
			}
		});
		if(state == false){
			return false;
		}
		if(abc.text.allNull($("#termsApplyYmd").val())){
			alert("시행일자를 정해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		if(abc.biz.cmm.terms.saveCheck){
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) <= 0) {
				alert("시행일자는 익일부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}else{
			if (abc.date.dateDiff(new Date(), $("#termsApplyYmd").val()) < 0) {
				alert("시행일자는 오늘부터 등록 가능 합니다.");
				$("#termsApplyYmd").focus();
				return false;
			}
		}
		if(!abc.text.allNull($("#termsApplyYmd").val())){
			var termsApplyYmd = abc.text.validateStringSignRemove($("#termsApplyYmd").val());
			if(!abc.text.isDate(termsApplyYmd)){
				alert("시행일자를 확인해주세요");
				$("#termsApplyYmd").focus();
				
				return false;
			}
		}else{
			alert("시행일자를 입력해주세요");
			$("#termsApplyYmd").focus();
			
			return false;
		}
		return true;
	}
	
	
	
})();	