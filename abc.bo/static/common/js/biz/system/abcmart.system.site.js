/***
 * 사이트 관리.
 * 
 */
(function() {

	var _site = abc.object.createNestedObject(window,"abc.biz.system.site");
	
	/*************************************************************************
	 *					사이트
	 *************************************************************************/
	
	_site.selectedRow = 1;
	_site.selectedSiteNo = 0;
	_site.channelPageCount = 15;
	_site.applySnsList = [];
	
	/**
	 * 사이트 sheet init
	 */
	_site.initSiteSheet = function() {
		var initSiteSheet = {};
		initSiteSheet.Cfg = {};
		initSiteSheet.Cols = [
				{Header:"사이트명", 		Type:"Text",	SaveName:"siteName",			Width:20,	Align:"Left",	Edit:0,	TreeCol:0,	FontUnderline:0, Cursor:"Pointer", Sort:0}
			  , {Header:"사이트번호",		Type:"Text",	SaveName:"siteNo",				Width:60,	Align:"Center",	Hidden:1,	Edit:0}
			  , {Header:'무료배송기준금액',	Type:"Text",	SaveName:"freeDlvyStdrAmt",		Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'배송비',			Type:"Text",	SaveName:"dlvyAmt",				Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'AS배송비',		Type:"Text",	SaveName:"asDlvyAmt",			Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'교환배송비',		Type:"Text",	SaveName:"exchngDlvyAmt",		Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'무료배송반품비',	Type:"Text",	SaveName:"freeDlvyRtnAmt",		Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'유료배송반품비',	Type:"Text",	SaveName:"paidDlvyRtnAmt",		Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'작성자번호',		Type:"Text",	SaveName:"rgsterNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'작성자',			Type:"Text",	SaveName:"rgsterDisplayName",	Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'작성일시',		Type:"Date",	SaveName:"rgstDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'수정자번호',		Type:"Text",	SaveName:"moderNo",				Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'수정자',			Type:"Text",	SaveName:"moderDisplayName",	Width:100,	Align:"Left",	Hidden:1,	Edit:0}
			  , {Header:'수정일시',		Type:"Date",	SaveName:"modDtm",				Width:100,	Align:"Left",	Hidden:1,	Edit:0, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];

		createIBSheet2(document.getElementById("siteGrid"), "siteSheet", "100%", "100%");
		IBS_InitSheet(siteSheet, initSiteSheet);
		siteSheet.FitColWidth();
		
		abc.biz.system.site.doActionSite("search");
	}
	
	/**
	 * 사이트 grid action
	 */
	_site.doActionSite = function(sAction){
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/site/list",
				sheet : "siteSheet"
			};
			
			var str = siteSheet.GetSearchData(param.url);
			_site.setApplySnsList(str);
			siteSheet.LoadSearchData(str);
			
			break;
		}
	}
	
	_site.setApplySnsList = function(str) {
		if (str) {
			var obj = JSON.parse(str);
			var data = obj.data;
			
			_site.applySnsList = [];
			data.forEach(function(element) {
				var sns = {
					siteNo: element.siteNo,
					snsList : element.applySnsList
				}
				_site.applySnsList.push(sns);
			});
		}
	}
	
	/**
	 * 사이트 grid 클릭 시 처리.
	 */
	_site.changeSiteData = function() {
		var data = siteSheet.GetRowData(abc.biz.system.site.selectedRow);
		
		if (data != -1) {
			abc.biz.system.site.tabOn();
			abc.biz.system.site.setSiteData(data);
			
			var tabIndex = 0;
			var tab = location.hash || "tabDelivery";
			if (tab == "#tabDelivery") {
				tabIndex = 0;	
			} else if(tab == "#tabChannel") {
				tabIndex = 1;
			} else if(tab == "#tabPayment") {
				tabIndex = 2;
			} else if(tab == "#tabGuide") {
				tabIndex = 3;
			}
			
			var activeTab = $("#tabSiteDetail").tabs("option", "active");
			switch(activeTab) {
				case 0:
					if (tabIndex == activeTab) {
						abc.biz.system.site.initDelivery(data);
					} else {
						$("#tabSiteDetail").tabs("option", "active", tabIndex);
					}
					break;
				case 1:
					if (tabIndex == activeTab) {
						abc.biz.system.site.initChannel();
					} else {
						$("#tabSiteDetail").tabs("option", "active", tabIndex);
					}
					break;
				case 2:
					if (tabIndex == activeTab) {
						abc.biz.system.site.initPayment();
					} else {
						$("#tabSiteDetail").tabs("option", "active", tabIndex);
					}
				  	break;
				case 3:
					if (tabIndex == activeTab) {
						abc.biz.system.site.initGuide();
					} else {
						$("#tabSiteDetail").tabs("option", "active", tabIndex);
					}
				  	break;
				default:
			}
		}
	}
	
	/**
	 * 탭 on
	 */
	_site.tabOn = function() {
		$("#tabSiteDetail").tabs("option", "disabled", []);
	}
	
	/**
	 * 탭 off
	 */	
	_site.tabOff = function() {
		$("#tabSiteDetail").tabs("option", "disabled", [1, 2, 3]);
	}
	
	/**
	 * 사이트 기본 정보를 set한다.
	 */
	_site.setSiteData = function(data) {
		abc.biz.system.site.selectedSiteNo = data.siteNo;
		$("#siteName").val(data.siteName);
		
		var checkAll = true;
		_site.applySnsList.forEach(function (element) {
			if (element.siteNo == data.siteNo) {
				if (element.snsList.length == 0) {
					$("#chkSNS01").prop("checked", false);
					$("input[custom=snsChnnlCheckbox]").prop("checked", false);
					$("input[custom=snsChnnlHidden]").val("N");
					checkAll = false;
				} else {
					element.snsList.forEach(function (element) {
						var $input = $("#snsChnnl"+element.snsChnnlCode);
						var $checkbox = $("#snsChnnlCheckbox"+element.snsChnnlCode);
						$checkbox.prop("checked", element.useYn == "Y" ? true : false);
						$input.val(element.useYn);
						
						if (element.useYn == "N") checkAll = false;
					});
					return;
				}				
			}
		});
		$("#chkSNS01").prop("checked", checkAll);
		
		$("input[name=siteNo]").val(abc.biz.system.site.selectedSiteNo);
	}
	
	_site.siteOn = function() {
		$("#siteName").prop('disabled', false);
		$("#chkSNS01").prop('disabled', false);
		$("input[custom=snsChnnlCheckbox]").prop('disabled', false);
	}
	
	_site.siteOff = function() {
		$("#siteName").prop('disabled', true);
		$("#chkSNS01").prop('disabled', true);
		$("input[custom=snsChnnlCheckbox]").prop('disabled', true);
	}
	
	// 사이트 배송 초기화	
	_site.initDelivery = function(data) {
		abc.biz.system.site.siteOn();
		
		if (data != null && data != "undefined") {
			$("#siteRgsterTable").show();
			
			Object.keys(data).forEach(function(key) {
//				 console.log('Key : ' + key + ', Value : ' + data[key]);
				$('#' + key).val(data[key]);
			});
			$('#deliveryRgster').text(data.rgsterDisplayName);
			$('#deliveryRgstDtm').text(data.rgstDtm.displayDateFormat());
			$('#deliveryModer').text(data.moderDisplayName);
			$('#deliveryModDtm').text(data.modDtm.displayDateFormat());
			
			// 배송유형 조회
			abc.biz.system.site.getDeliveryType();
		}
		
		// 사이트 저장
		$("#siteSaveBtn").off().on('click', function() {
			abc.biz.system.site.siteSave();
		});
		
		// 신규 사이트
		$("#newSiteBtn").off().on('click', function() {
			$("#tabSiteDetail").tabs("option", "active", 0);
			$("#siteForm")[0].reset();
			$("#channelForm")[0].reset();
//			$("#paymentForm")[0].reset();
			$("#guideForm")[0].reset();
			$("input[name=siteNo]").val('');
			$("#siteRgsterTable").hide();
			abc.biz.system.site.tabOff();
		});
		
		// SNS 전체 클릭
		$("#chkSNS01").off().on('click', function() {
			if ($(this).is(":checked")) {
				$("input[custom=snsChnnlCheckbox]").prop("checked", true);
				$("input[custom=snsChnnlHidden]").val("Y");
			} else {
				$("input[custom=snsChnnlCheckbox]").prop("checked", false);
				$("input[custom=snsChnnlHidden]").val("N");
			}	 
		});
		
		// SNS 개별 change 
		$("input[custom=snsChnnlCheckbox]").off().on('change', function() {
			var id = $(this).attr("data");
			if ($(this).is(":checked")) {
				$("#snsChnnl"+id).val("Y");
				var unChecked = $("input[custom=snsChnnlCheckbox]:not(:checked)");
				if (unChecked.length == 0) {
					$("#chkSNS01").prop("checked", true);
				}	
			} else {
				$("#snsChnnl"+id).val("N");
				$("#chkSNS01").prop("checked", false);
			}
		});
		
		// 배송유형 전체 클릭
		$("#chkDelivery01").off().on('click', function() {
			if ($(this).is(":checked")) {
				$("input[custom=dlvyTypeCheckbox]").prop("checked", true);
				$("input[custom=dlvyTypeHidden]").val("Y");
			} else {
				$("input[custom=dlvyTypeCheckbox]").prop("checked", false);
				$("input[custom=dlvyTypeHidden]").val("N");
			}	 
		});
		
		// 배송유형 개별 change 
		$("input[custom=dlvyTypeCheckbox]").off().on('change', function() {
			var id = $(this).attr("data");
			if ($(this).is(":checked")) {
				$("#deliveryType"+id).val("Y");
				var unChecked = $("input[custom=dlvyTypeCheckbox]:not(:checked)");
				if (unChecked.length == 0) {
					$("#chkDelivery01").prop("checked", true);
				}	
			} else {
				$("#deliveryType"+id).val("N");
				$("#chkDelivery01").prop("checked", false);
			}
		});
		
		// 금액 필드
		$("input[custom=number]").off().on('input', function(event) {
			$(this).val($(this).val().replace(/[^0-9\.]/g, ''));
		});
	}
	
	/**
	 * 사이트의 배송유형을 조회한다.
	 */
	_site.getDeliveryType = function() {
		$.ajax({
			type:"post",
			url: "/system/site/delivery-type",
			data: "siteNo="+abc.biz.system.site.selectedSiteNo
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.system.site.setDeliveryType(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * 사이트 화면의 배송유형의 데이터를 set 한다.
	 */
	_site.setDeliveryType = function(data) {
		if (data.length == 0) {
			$("input[custom=dlvyTypeCheckbox]").prop("checked", false);
			$("input[custom=dlvyTypeHidden]").val("N");
		} else {
			var checkAll = true;
			data.forEach(function(element) {
				if (element.useYn == "Y") {
					$("#deliveryType"+element.dlvyTypeCode).val("Y");
					$("#checkbox"+element.dlvyTypeCode).prop("checked", true);
				} else {
					checkAll = false;
					$("#deliveryType"+element.dlvyTypeCode).val("N");
					$("#checkbox"+element.dlvyTypeCode).prop("checked", false);
				}
			});
			
			$("#chkDelivery01").prop("checked", checkAll);
		}
	}
	
	/***
	 * 사이트 & 배송정보 저장
	 */
	_site.siteSave = function() {
		var $form = $("#siteForm");
		var $siteName = $("#siteName");
		var $freeDlvyStdrAmt = $("#freeDlvyStdrAmt");
		var $dlvyAmt = $("#dlvyAmt");
		var $asDlvyAmt = $("#asDlvyAmt");
		var $exchngDlvyAmt = $("#exchngDlvyAmt");
		var $freeDlvyRtnAmt = $("#freeDlvyRtnAmt");
		var $paidDlvyRtnAmt = $("#paidDlvyRtnAmt");
		
		if(abc.text.isBlank($siteName.val())){
			alert("사이트명을 입력해 주십시요.");
			$siteName.focus();
			return false;
		}
		if(abc.text.isLimitLength($siteName.val(), 100)){
			alert("사이트명이 최대값을 초과하였습니다.");
			$siteName.focus();
			return false;
		}
		
		var checkResult = false;
		$("input[custom=dlvyTypeCheckbox]").each(function(i) {
			checkResult = checkResult || $(this).is(":checked");
		});
		if(!checkResult) {
			alert("배송유형을 선택하십시요.");
			return false;
		}
		
		if(abc.text.isBlank($freeDlvyStdrAmt.val())){
			alert("구매금액을 입력해 주십시요.");
			$freeDlvyStdrAmt.focus();
			return false;
		}
		if($freeDlvyStdrAmt.val() > abc.consts.INT_MAX_VALUE){
			alert("구매금액이 최대값을 초과하였습니다.");
			$freeDlvyStdrAmt.focus();
			return false;
		}
		
		if(abc.text.isBlank($dlvyAmt.val())){
			alert("배송비용을 입력해 주십시요.");
			$dlvyAmt.focus();
			return false;
		}
		if($dlvyAmt.val() > abc.consts.INT_MAX_VALUE){
			alert("배송비용이 최대값을 초과하였습니다.");
			$dlvyAmt.focus();
			return false;
		}
		
		if(abc.text.isBlank($asDlvyAmt.val())){
			alert("A/S 배송비용을 입력해 주십시요.");
			$asDlvyAmt.focus();
			return false;
		}
		if($asDlvyAmt.val() > abc.consts.INT_MAX_VALUE){
			alert("A/S 배송비용이 최대값을 초과하였습니다.");
			$asDlvyAmt.focus();
			return false;
		}
		
		if(abc.text.isBlank($exchngDlvyAmt.val())){
			alert("교환 배송비용을 입력해 주십시요.");
			$exchngDlvyAmt.focus();
			return false;
		}
		if($exchngDlvyAmt.val() > abc.consts.INT_MAX_VALUE){
			alert("교환 배송비용이 최대값을 초과하였습니다.");
			$exchngDlvyAmt.focus();
			return false;
		}
		
		if(abc.text.isBlank($freeDlvyRtnAmt.val())){
			alert("무료배송 상품의 반품 배송비용을 입력해 주십시요.");
			$freeDlvyRtnAmt.focus();
			return false;
		}
		if($freeDlvyRtnAmt.val() > abc.consts.INT_MAX_VALUE){
			alert("무료배송 상품의 반품 배송비용이 최대값을 초과하였습니다.");
			$freeDlvyRtnAmt.focus();
			return false;
		}
		
		if(abc.text.isBlank($paidDlvyRtnAmt.val())){
			alert("유료배송 상품의 반품 배송비용을 입력해 주십시요.");
			$paidDlvyRtnAmt.focus();
			return false;
		}
		if($paidDlvyRtnAmt.val() > abc.consts.INT_MAX_VALUE){
			alert("유료배송 상품의 반품 배송비용이 최대값을 초과하였습니다.");
			$paidDlvyRtnAmt.focus();
			return false;
		}
		
		$.ajax({
			type:"post",
			url: "/system/site/update",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			alert("저장되었습니다.");
			abc.biz.system.site.doActionSite("search");
		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	
	/*************************************************************************
	 *					사이트 : 채널
	 *************************************************************************/
	/**
	 * 채널 그리드 초기화
	 */
	_site.initChannelSheet = function() {
		var initChannelSheet = {};
		initChannelSheet.Cfg = {SearchMode:smServerPaging2, Page:abc.biz.system.site.channelPageCount, MaxSort:1};
		initChannelSheet.Cols = [
				{Header:"" ,		Type:"Status",		SaveName:"status",				Width: 0,	Align:"", 		Hidden:1,	Edit:0,		Sort:0}
			  , {Header:"사이트번호",	Type:"Text",		SaveName:"siteNo",				Width:30,	Align:"Center",	Hidden:1,	Edit:0,		Sort:0}
			  , {Header:"번호",	 	Type:"Text",		SaveName:"chnnlNo",				Width:50,	Align:"Center",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'정렬순서',	Type:"Int",			SaveName:"sortSeq",				Width:50,	Align:"Center",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'채널명',		Type:"Text",		SaveName:"chnnlName",			Width:120,	Align:"Left",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'입점사허용여부',Type:"CheckBox",	SaveName:"vndrUseYn",			Width:80,	Align:"Left",	Hidden:0,	Edit:0,		Sort:0, TrueValue:"Y", FalseValue:"N", HeaderCheck:0}
			  , {Header:'판매여부',	Type:"CheckBox",	SaveName:"sellPsbltYn",			Width:80,	Align:"Left",	Hidden:0,	Edit:0,		Sort:0, TrueValue:"Y", FalseValue:"N", HeaderCheck:0}
			  , {Header:'매장코드',	Type:"Text",		SaveName:"insdMgmtInfoText",	Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'업체번호',	Type:"Text",		SaveName:"vndrNo",				Width:80,	Align:"Center",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'사용여부',	Type:"CheckBox",	SaveName:"useYn",				Width:80,	Align:"Left",	Hidden:0,	Edit:0,		Sort:0, TrueValue:"Y", FalseValue:"N", HeaderCheck:0}
			  , {Header:'수정자번호',	Type:"Text",		SaveName:"moderNo",				Width:100,	Align:"Left",	Hidden:1,	Edit:0,		Sort:0}
			  , {Header:'수정자',		Type:"Text",		SaveName:"moderDisplayName",	Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'수정일시',	Type:"Date",		SaveName:"modDtm",				Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Sort:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
			  , {Header:'작성자번호',	Type:"Text",		SaveName:"rgsterNo",			Width:100,	Align:"Left",	Hidden:1,	Edit:0,		Sort:0}
			  , {Header:'작성자',		Type:"Text",		SaveName:"rgsterDisplayName",	Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Sort:0}
			  , {Header:'작성일시',	Type:"Date",		SaveName:"rgstDtm",				Width:100,	Align:"Center",	Hidden:0,	Edit:0,		Sort:1, Format:abc.consts.DEFAULT_DATETIME_PATTERN}
		];
		
		createIBSheet2(document.getElementById("channelGrid"), "channelSheet", "100%", "300px");
		IBS_InitSheet(channelSheet, initChannelSheet);
		
		channelSheet.SetCountPosition(3);
		channelSheet.SetPagingPosition(2);
		channelSheet.SetFocusAfterProcess(0);
		channelSheet.FitColWidth();
	}
	
	_site.initChannel = function() {
		abc.biz.system.site.channelPageCount = $("#channelPageCount").val();
		abc.biz.system.site.siteOff();
		$(".file-list").children().find("button").hide();
		
		if (typeof channelSheet === "undefined") {
			abc.biz.system.site.initChannelSheet();
		}
		abc.biz.system.site.doActionChannel("search");
		abc.biz.system.site.resetMethod();
		// 채널 추가.
		$("#newChannelBtn").off().on('click', function() {
			if (abc.text.isBlank($("#chnnlName").val())) {
				alert("채널명을 입력해 주십시요.");
				$("#chnnlName").focus();
				return false;
			}
			if (abc.text.isLimitLength($("#chnnlName").val(), 100)) {
				alert("채널명이 최대값을 초과하였습니다.");
				$("#chnnlName").focus();
				return false;
			}
			
			if ($("#chkUse02").is(":checked")) {
				if (abc.text.isBlank($("#insdMgmtInfoText").val())) {
					alert("매장코드를 입력해 주십시요.");
					$("#insdMgmtInfoText").focus();
					return false;
				}
				
				if (abc.text.isLimitLength($("#insdMgmtInfoText").val(), 20)) {
					alert("매장코드가 최대값을 초과하였습니다.");
					$("#insdMgmtInfoText").focus();
					return false;
				}
			} 
			
			var data = {
				siteNo: abc.biz.system.site.selectedSiteNo,
				chnnlName: $("#chnnlName").val(),
				vndrUseYn: $("#chkUse03").is(":checked") ? "Y" : "N",
				sellPsbltYn : $("#chkUse02").is(":checked") ? "Y" : "N",
				insdMgmtInfoText: $("#insdMgmtInfoText").val(),
				useYn: $("#chkUse01").is(":checked") ? "Y" : "N",
				vndrNo: $("#vndrNo").val()
			};
			
			var rowIdx = channelSheet.DataInsert(-1);
			channelSheet.SetRowData(rowIdx, data);
			$("#channelForm")[0].reset();
			$("#insdMgmtInfoText").prop("disabled", false);
		});
		
		// 채널 신규등록
		$("#newChnnelBtn").off().on('click', function() {
			channelSheet.SelectCell(1,0);
			abc.biz.system.site.resetMethod();
		});
		
		// 채널 저장
		$("#channelSaveBtn").off().on('click', function() {
			abc.biz.system.site.channelSave();
		});
		
		// 채널 목록 갯수		
		$("#channelPageCount").off().on('change', function() {
			channelSheet.SetPageCount($(this).val());
			abc.biz.system.site.channelPageCount = $(this).val(); 
			abc.biz.system.site.doActionChannel("search");
		});
		
		//판매여부 클릭	
		$("#chkUse02").off().on('click', function() {
			if ($(this).is(":checked")) {
				$("#insdMgmtInfoText").prop("disabled", false);
			} else {
				$("#insdMgmtInfoText").val('');
				$("#insdMgmtInfoText").prop("disabled", true);
			}
		});
	}
	
	/**
	 * 채널 grid action
	 */
	_site.doActionChannel = function(sAction){
		switch (sAction) {
		case "search":
			var param = {
				url : "/system/site/channel/list"
				, onePageRow : abc.biz.system.site.channelPageCount
				, subparam : FormQueryStringEnc(document.channelForm)
				, sheet : "channelSheet"
			};

			DataSearchPaging(param);
			break;
		}
	}
	
	/**
	 * 채널 grid save
	 */
	_site.channelSave = function() {
		
		if(!abc.biz.system.site.checkChnnlDataValid()){
			return false; 
		}
		
		if($("input:checkbox[id='chkUse03']").is(":checked")){
			$("#vndrUseYn").val(abc.consts.BOOLEAN_TRUE);
		}else{
			$("#vndrUseYn").val(abc.consts.BOOLEAN_FALSE);
		}
		
		if($("input:checkbox[id='chkUse02']").is(":checked")){
			$("#sellPsbltYn").val(abc.consts.BOOLEAN_TRUE);
		}else{
			$("#sellPsbltYn").val(abc.consts.BOOLEAN_FALSE);
		}
		
		if($("input:checkbox[id='chkUse01']").is(":checked")){
			$("#useYn").val(abc.consts.BOOLEAN_TRUE);
		}else{
			$("#useYn").val(abc.consts.BOOLEAN_FALSE);
		}
		
		var form = $.form(document.forms.channelForm);
		
		console.log(form);
		form.submit({
			url	: "/system/site/channel/update",
			method	: "POST",
			success : function(data){
				alert("저장되었습니다.");
				abc.biz.system.site.resetMethod();
				abc.biz.system.site.doActionChannel("search");
			},
			error : function(e){
				alert(e.message);
		    	console.log(e);
			}
		});
	}
	
	_site.checkChnnlDataValid = function(){
		
		if(abc.text.allNull($("#chnnlName").val())){
			alert("채널명을 입력해 주세요.");
			$("#chnnlName").focus();
			return false;
		}
		
		if(abc.text.allNull($("#sortSeq").val())){
			alert("정렬순서를 입력해 주세요.");
			$("#sortSeq").focus();
			return false;
		}
		
		if(abc.text.allNull($("#chnnlPrdtGbnNo").val())){
			alert("채널 상품 구분 번호(자사)를 입력해 주세요.");
			$("#chnnlPrdtGbnNo").focus();
			return false;
		}
		
		if(abc.text.allNull($("#pcLogoName").val())){
			alert("채널 로고 이미지[PC]를 등록해 주세요.");
			return false;
		}
		
		if(abc.text.allNull($("#moLogoName").val())){
			alert("채널 로고 이미지[MO]를 등록해 주세요.");
			return false;
		}
		
		if(abc.text.allNull($("#gnbLogoName").val())){
			alert("GNB 로고 이미지를 등록해 주세요.");
			return false;
		}
		
		var chnnlPrdtGbnNo = $("#chnnlPrdtGbnNo").val();
		if(chnnlPrdtGbnNo < 100 || chnnlPrdtGbnNo > 699){
			alert("채널 상품 구분번호는 100~699 사이만 입력 가능합니다.");
			return false;
		}
		
		if(chnnlPrdtGbnNo == 190){
			alert("해당 채널 상품 구분번호는 배송비 상품으로 사용이 불가합니다.");
			return false;
		}
		
		return true;
	}
	
	
	/*************************************************************************
	 *					사이트 : 결제수단
	 *************************************************************************/
	
	_site.initPayment = function() {
		abc.biz.system.site.siteOff();
		
		abc.biz.system.site.getPayment();
		
		// 정책 저장			
		$("#paymentSaveBtn").off().on('click', function() {
			abc.biz.system.site.paymentSave();
		});
		
		// 전체 check box event		
		$("input.paymentCheckboxAll").off().on('click', function() {
			var group = $(this).attr("data-group");
			if ($(this).is(":checked")) {
				$("input[data-group="+group+"Checkbox]").prop("checked", true);
				$("input[data-group="+group+"Hidden]").val("Y");
			} else {
				$("input[data-group="+group+"Checkbox]").prop("checked", false);
				$("input[data-group="+group+"Hidden]").val("N");
			}
		});
		
		// 개별 check box event
		$("input.paymentCheckbox").off().on('click', function() {
			var id = $(this).attr("data-id");
			var groupId = $(this).attr("data-group");
			var checkAllGid = groupId.replace("Checkbox", "");
			
			if ($(this).is(":checked")) {
				$("#"+id).val("Y");
				var unChecked = $("input[data-group="+groupId+"]:not(:checked)");
				if (unChecked.length == 0) {
					$("input[data-group="+checkAllGid+"]").prop("checked", true);
				}
			} else {
				$("#"+id).val("N");
				$("input[data-group="+checkAllGid+"]").prop("checked", false);
			}
		});
	}
	
	/**
	 * 결제수단 조회
	 */
	_site.getPayment = function(){
		var $form = $("#guideForm");
		
		$.ajax({
			type:"post",
			url: "/system/site/payment",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.system.site.setPayment(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * 결제수단 set
	 */
	_site.setPayment = function(data) {
		if (data.length == 0) {
			$("input.paymentCheckboxAll").prop("checked", false);
			$("input.paymentCheckbox").prop("checked", false);
			
			$("#paymentRgster").text("");
			$("#paymentRgstDtm").text("");
			$("#pyamentModer").text("");
			$("#paymentModDtm").text("");
		} else {
			var idx = 0, groupId = "";
			var checkAllArr = new Array();
			data.forEach(function(element) {
				// group id 조합 : 멤버십코드 + 임직원여부 + 주문타입(일반,예약) + 주결제수단여부
				var tmpGid = element.memberTypeCode+element.empYn+element.orderType+element.mainPymntMeansYn
				if (groupId != tmpGid) {
					groupId = tmpGid;
					var group = {
						id : groupId,
						value : true
					}
					checkAllArr.push(group);
				}
				// id 조합 : 멤버십코드 + 임직원여부 + 주문타입(일반,예약) + 주결제수단여부 + 결제수단코드
				var elementId = element.memberTypeCode+element.empYn+element.orderType+element.mainPymntMeansYn+element.pymntMeansCode;
				if (element.useYn == "Y") {
					$("#"+elementId+"Checkbox").prop("checked", true);
				} else {
					$("#"+elementId+"Checkbox").prop("checked", false);
					var idx = -1;
					checkAllArr.some(function(group, i) {
					    if (group.id == groupId) {
					    	idx = i;
					        return true;
					    }
					});
//					idx = checkAllArr.findIndex(group => group.id == groupId);
					checkAllArr[idx].value = false;
				}
				
				$("#"+elementId).val(element.useYn);
			});
			
			// 개별 체크박스의 체크여부에 따라서 전체 체크박스의 체크여부를 처리.			
			checkAllArr.forEach(function(element) {
				$("input[data-group="+element.id+"].paymentCheckboxAll").prop("checked", element.value);
			});
			
			var rgstDtm = new Date(data[0].rgstDtm);
			var modDtm = new Date(data[0].modDtm);
			$("#paymentRgster").text(data[0].rgsterDisplayName);
			$("#paymentRgstDtm").text(rgstDtm.format());
			$("#pyamentModer").text(data[0].moderDisplayName);
			$("#paymentModDtm").text(modDtm.format());
		}
	}
	
	/**
	 * 결제수단 save
	 */
	_site.paymentSave = function(){
		var $form = $("#paymentForm");
		
//		console.log($form.serialize());
		$.ajax({
			type:"post",
			url: "/system/site/payment/update",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			location.hash = "tabPayment";
			alert("저장되었습니다.");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	
	/*************************************************************************
	 *					사이트 : 정책
	 *************************************************************************/
	
	_site.initGuide = function() {
		abc.biz.system.site.siteOff();
		// ckeditor init
		if (!CKEDITOR.instances['dlvyGuideInfo']) {
			CKEDITOR.replace('dlvyGuideInfo');
		}
		
		abc.biz.system.site.getGuid();
		
		// 정책 select change
		$("#dlvyGuideBgnCode").off().on('change', function() {
			abc.biz.system.site.getGuid();
		});
		
		// 정책 저장			
		$("#guideSaveBtn").off().on('click', function() {
			abc.biz.system.site.guideSave();
		});
	}
	
	/**
	 * 정책 조회
	 */
	_site.getGuid = function(){
		var $form = $("#guideForm");
		
		$.ajax({
			type:"post",
			url: "/system/site/guide",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.system.site.setGuide(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * 정책 set
	 */
	_site.setGuide = function(data) {
		var editor = CKEDITOR.instances['dlvyGuideInfo'];
		if (data != null && data != "undefined") {
			editor.setData(data.dlvyGuideInfo);
			
			var rgstDtm = new Date(data.rgstDtm);
			var modDtm = new Date(data.modDtm);
			
			$("#guideRgster").text(data.rgsterDisplayName);
			$("#guideRgstDtm").text(rgstDtm.format());
			$("#guideModer").text(data.moderDisplayName);
			$("#guideModDtm").text(modDtm.format());
		} else {
			editor.setData("");
			
			$("#guideRgster").text("");
			$("#guideRgstDtm").text("");
			$("#guideModer").text("");
			$("#guideModDtm").text("");
		}
	}
	
	/**
	 * 정책 save
	 */
	_site.guideSave = function(){
		var $form = $("#guideForm");
		var editor = CKEDITOR.instances['dlvyGuideInfo'];
		var data = editor.getData();
		$("#dlvyGuideInfo").val(data);
		
		if(abc.text.isLimitLength(data, 8000)){
			alert("정책내용이 최대값을 초과하였습니다.");
			editor.focus();
			return false;
		}
		
		$.ajax({
			type:"post",
			url: "/system/site/guide/update",
			data: $form.serialize()
		}).done(function(data, textStatus, jqXHR) {
			location.hash = "tabGuide";
			alert("저장되었습니다.");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * 채널 상세조회
	 */
	_site.channelDetailInfo = function(params){
		
		$.ajax({
			type:"post",
			url: "/system/site/channel/detail",
			data: params
		}).done(function(data, textStatus, jqXHR) {
			abc.biz.system.site.setChannelData(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	/**
	 * 채널 상세데이터 세팅
	 */
	_site.setChannelData = function(data){
		console.log(data);
		
		var vndrUseYn = data.vndrUseYn;
		var sellPsbltYn = data.sellPsbltYn;
		var useYn = data.useYn;
		var srchRelmExpsrYn = data.srchRelmExpsrYn;
		
		$("#chnnlNo").val(data.chnnlNo);
		$("#chnnlName").val(data.chnnlName);
		$("#sortSeq").val(data.sortSeq);
		$("#insdMgmtInfoText").val(data.insdMgmtInfoText);
		$("#vndrNo").val(data.vndrNo);
		$("#chnnlPrdtGbnNo").val(data.chnnlPrdtGbnNo);
		
		//채널 상품 구분번호(등록 후 수정불가)
		$("#chnnlPrdtGbnNo").prop("disabled", true);
		
		//입점사 허용여부
		if(vndrUseYn == abc.consts.BOOLEAN_TRUE){
			$("input:checkbox[id='chkUse03']").prop("checked", true);
		}else{
			$("input:checkbox[id='chkUse03']").prop("checked", false);
		}
		
		//판매여부
		if(sellPsbltYn == abc.consts.BOOLEAN_TRUE){
			$("input:checkbox[id='chkUse02']").prop("checked", true);
			$("#insdMgmtInfoText").prop("disabled", false);
		}else{
			$("input:checkbox[id='chkUse02']").prop("checked", false);
			$("#insdMgmtInfoText").prop("disabled", true);
		}
		
		//사용여부
		if(useYn == abc.consts.BOOLEAN_TRUE){
			$("input:checkbox[id='chkUse01']").prop("checked", true);
		}else{
			$("input:checkbox[id='chkUse01']").prop("checked", false);
		}
		
		//검색 영역 채널 전시 여부
		if(srchRelmExpsrYn == abc.consts.BOOLEAN_TRUE){
			$("#srchRelmExpsrY").prop("checked", true)
		}else{
			$("#srchRelmExpsrN").prop("checked", true)
		}
		
		//이미지 데이터 세팅
		abc.biz.system.site.setImgData(data);
	}
	
	/**
	 * 채널 상세 조회 시 이미지 데이터 세팅
	 */
	_site.setImgData = function(data){
		var pcImg = new Image();
		var moImg = new Image();
		var gnbImg = new Image();
		
		$(".file-list").children().find("a").text("");
		$(".file-list").children().find("button").hide();
		$(".img-wrap").children().remove();
		$("#pcLogoName").val("");
		$("#moLogoName").val("");
		$("#gnbLogoName").val("");
		
		//채널로고 이미지(PC)
		if(!abc.text.allNull(data.pcLogoImgUrl)){
			$(pcImg).attr('src', data.pcLogoImgUrl + "?shrink=160:70");
			$("#pcLogoImg").closest('div').find("a").text(data.pcLogoImgName);
			$("#pcLogoImg").closest('div').find("button").show();
			$("#pcLogoImg").closest('div').find(".img-wrap").append(pcImg);
			$("#pcLogoName").val(data.pcLogoImgName);
		}
		
		//채널로고 이미지(MO)
		if(!abc.text.allNull(data.moLogoImgUrl)){
			$(moImg).attr('src', data.moLogoImgUrl + "?shrink=160:70");
			$("#moLogoImg").closest('div').find("a").text(data.moLogoImgName);
			$("#moLogoImg").closest('div').find("button").show();
			$("#moLogoImg").closest('div').find(".img-wrap").append(moImg);
			$("#moLogoName").val(data.moLogoImgName);
		}
		
		//GNB로고 이미지
		if(!abc.text.allNull(data.gnbLogoImgUrl)){
			$(gnbImg).attr('src', data.gnbLogoImgUrl + "?shrink=160:70");
			$("#gnbLogoImg").closest('div').find("a").text(data.gnbLogoImgName);
			$("#gnbLogoImg").closest('div').find("button").show();
			$("#gnbLogoImg").closest('div').find(".img-wrap").append(gnbImg);
			$("#gnbLogoName").val(data.gnbLogoImgName);
		}
	}
	
	/**
	 * 이미지 처리
	 */
	_site.processImage = function(option){

		var options;
		var limitSize = 10485760;

		init = function(option){

			options = $.extend({
				file: '',
				name: '',
				type: 'I',
				allow: ''
	        }, option);
			
			if(options.allow == ''){
				options.allow = $(options.file).closest('td').prev().text().trim();
			}

			var fileWrap = $(options.file).parents('.file-wrap');

			options.delButton = fileWrap.find('.btn-file-del');
			options.previewArea = fileWrap.find('.img-wrap');
			options.nameText = fileWrap.find('.subject');

			uploadImage();
			removeImage();
		}

		var removeImage = function(){
			$(options.delButton).on('click', function(e){
				$(options.previewArea).html('');
				$(options.nameText).text('');
				$(options.file).val('');
				$(options.delButton).hide();
				$(options.name).val('');
			});
		}

		var uploadImage = function(){
			$(options.file).on('change', function(e){

				var img = new Image();
				var reader = new FileReader();
				if(this.files && this.files[0]){
					var file = this.files[0]; 

					var reader = new FileReader();
					reader.onload = function(e){
						
						if(options.type == 'I'){
							
							if(abc.text.confirmImgFileExtension(file.name.toLocaleLowerCase())){
								alert('허용되지 않는 파일입니다.');
								$(options.nameText).text('');
								$(options.name).val('');
								$(options.delButton).hide();
								$(options.previewArea).children('img').remove();
								return false;
							}

							
							if(e.total > limitSize){
								alert('최대 10MB까지 등록 가능 합니다.');
								$(options.nameText).text('');
								$(options.name).val('');
								$(options.delButton).hide();
								$(options.previewArea).children('img').remove();
								return false;
							}
						}
						
						$(img).attr('src', e.target.result);
						$(img).attr('width', '160px');
						$(img).attr('height', '70px');
						
						$(options.previewArea).children('img').remove();
						$(options.previewArea).append(img);
					}

					reader.readAsDataURL(this.files[0]);
					$(options.nameText).text(this.files[0].name);
					$(options.name).val(this.files[0].name);
					$(options.delButton).show();
				}
			});
		}
		init(option);
	}
	
	_site.resetMethod = function() {
		$.form("#channelForm").reset();
		
		if($("input:checkbox[id='chkUse02']").is(":checked")){
			$("#insdMgmtInfoText").prop("disabled", false);
		}else{
			$("#insdMgmtInfoText").prop("disabled", true);
		}
		
		$("#chnnlNo").val("");
		$("#chnnlPrdtGbnNo").prop("disabled", false);
		
		$(".file-list").children().find("a").text("");
		$(".file-list").children().find("button").hide();
		$(".img-wrap").children().remove();
	}
})();