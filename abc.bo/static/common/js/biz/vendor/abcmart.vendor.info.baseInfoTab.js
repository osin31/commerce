/***
 * 입점관리 > 입점사 정보등록
 *
 */
(function() {

	var _vendor = abc.object.createNestedObject(window,"abc.biz.vendor.info.base");
	_vendor.selChnnlNo;

	var insdMgmtInfoSuccess = "N";
	var bizNoSuccess = "N";

	//화면초기화
	_vendor.init = function(){
		var status = $("#baseInfoStatus").val();
		var readonlyIds = [];
		var chnableIds = [];
		var hideIds = [];

		if($("#upAuthNo").val() == "ROLE_30000"){
			$("#tabVendorDetail").tabs("option", "disabled", []);

			$('input').attr("readonly", true);
			$(':checkbox').attr('disabled', true);
			$(':radio').attr('disabled', true);
			$('select').attr("disabled", true);
			$('textarea').attr("readonly", true);

			chnableIds = ["workTypeText_", "hdphnNoText_", "telNoText_", "emailAddrText_", "asMngrText", "dlvyGuideInfo_"];

			hideIds = ["postSearchBtnBase", "vendorInfoGoList", "addStandardCategory"
					 , "vendorManagerRemoveBtn_", "vendorManagerAddBtn_", "serchBrandPop"
				];

			for(var i in chnableIds){
				$("[id^="+chnableIds[i]+"]").attr("readonly", false);
			}

		}else if($("#popUnYn").val() == 'Y'){
			$("#tabVendorDetail").tabs("option", "disabled", []);

			$('input').attr("readonly", true);
			$(':checkbox').attr('disabled', true);
			$(':radio').attr('disabled', true);
			$('select').attr("disabled", true);
			$('textarea').attr("readonly", true);

			hideIds = ["postSearchBtnBase", "vendorInfoGoList", "addStandardCategory"
				 , "vendorManagerRemoveBtn_", "vendorManagerAddBtn_", "serchBrandPop", "vedorBasicInfoSave"
			];

		}else if(status == "U"){
			$("#tabVendorDetail").tabs("option", "disabled", []);
		}


		for(var i in hideIds){
			$("[id^="+hideIds[i]+"]").hide();
		}

		_vendor.event();
	}


	// 화면이벤트
	_vendor.event = function(){

		// 숫자만 입력가능
		$("#rprsntTelNoText, #faxNoText, #acntNoText, #penltyLevyRate, #penltyExmtRate").keyup(function(){
			abc.text.validateOnlyNumber(this);
		});

		$("[class*=num-unit]").keyup(function() {
			abc.text.validateOnlyNumber(this);
		});

		//입점사코드 중복확인
		$("#insdMgmtInfoText").change(function (event) {
			insdMgmtInfoSuccess = "N";
		});

		$("#checkInsdMgmtInfo").click(function(){
			if( abc.text.allNull($("#insdMgmtInfoText").val())){
				alert("입점사코드를 입력하세요");
				$("#insdMgmtInfoText").focus();
				return false;
			}

			var params = {"insdMgmtInfoText" : $("#insdMgmtInfoText").val()};

			$.ajax({
				type :"post",
				url : "/vendor/info/check-insdmgmtinfo",
				data: params
			})
			.done(function(data){
				if(data.result == abc.consts.BOOLEAN_TRUE){
					alert("사용가능한 입점사코드입니다.");
				}else{
					alert("이미 존재하는 입점사코드 입니다.");
				}
				insdMgmtInfoSuccess = data.result;
			})
			.fail(function(e){
				alert("중복체크중 에러가 발생하였습니다\n다시 시도해 주세요.");
				console.log("e :" + JSON.stringify(e));
			});
		});

		//사업자번호 중복확인
//		$("input[id^=bizNo]").keyup(function(){
//			abc.text.validateOnlyNumber(this);
//		})

		$("input[name=bizNo1]").change(function(){
			bizNoSuccess = "N";
		});
		$("input[name=bizNo2]").change(function(){
			bizNoSuccess = "N";
		});
		$("input[name=bizNo3]").change(function(){
			bizNoSuccess = "N";
		});

		$("#checkbizNo").click(function(){

			if(!_vendor.bizNoValidation()){
				return false;
			}
			var bizNoText = $("#bizNo1").val().concat("-", $("#bizNo2").val(), "-", $("#bizNo3").val());

			// 사업자번호 유효성 체크
//			var checkBizID = bizNoText.replace(/-/gi,"");
//			var isBizID = abc.text.checkBizID(checkBizID);
//			if(!isBizID){
//				alert("사용이 불가한 사업자 등록번호입니다.");
//				return false;
//			}

			$("#bizNoText").val(bizNoText);
			var params = {"bizNoText" : $("#bizNoText").val()};

			$.ajax({
				type :"post",
				url : "/vendor/info/check-bizNoText",
				data: params
			})
			.done(function(data){
				if(data.result == abc.consts.BOOLEAN_TRUE){
					alert("사용가능한 사업자번호입니다.");
				}else{
					alert("이미 존재하는 사업자번호 입니다.");
				}
				bizNoSuccess = data.result;
			})
			.fail(function(e){
				alert("중복체크중 에러가 발생하였습니다\n다시 시도해 주세요.");
				console.log("e :" + JSON.stringify(e));
			});

		});

		//패널티 부과비율
		$("#penltyLevyRate").change(function(){
			if($(this).val() > 20 ){
				alert("20%를 초과할수 없습니다.");
				$(this).val("");
				$(this).focus();
				return false;
			}
		});
		//패널티 면제비율
		$("#penltyExmtRate").change(function(){
			_vendor.validateOnlyNumber(this);
		});

		//전자세금계산서 발송용 이메일 유효성체크
		$("#billIssueEmailAddrText").change(function(){
			if(!abc.text.isEmail($(this).val())){
				alert("메일주소를 올바르게 입력해주세요");
				return false;
			}
		});

		//전시체널 전체선택
		$("#channelCheckAll").click(function(){
			$("input[id^=chkChannel_]").prop("checked", this.checked);

			//담당MD정보 화면 컨트롤
			_vendor.chargeMdDisplay();
		});

		$("input[id^=chkChannel_]").click(function(){
			if(this.checked == false){
				$("#channelCheckAll").prop("checked", false);
			}else if($("input[id^=chkChannel_]:checkbox:checked").length == $("input[id^=chkChannel_]").length){
				$("#channelCheckAll").prop("checked", true);
			}
			//담당MD정보 화면 컨트롤
			_vendor.chargeMdDisplay();
		});

		// 담당MD찾기 팝업
		$("a[id^=searchChargeMdNo]").click(function(){
			var chnnlNo = $(this).attr("id").split("_")[1];
			_vendor.selChnnlNo = chnnlNo;
			abc.adminSearchPopup('adminSearchPopupResult');

			$("#deleteChargeMdNo_"+chnnlNo).css("display","");

		});

		$("[id^=deleteChargeMdNo]").click(function(){
			var chnnlNo = $(this).attr("id").split("_")[1];
			$("#chargeMdNo_"+chnnlNo).val("");
			$("#spanChargeMd_"+chnnlNo).text("");
			$("#deleteChargeMdNo_"+chnnlNo).css("display","none");
		});

		// 기본정산수수료
		$("[id^=dfltCmsnRate]").keyup(function(){
			_vendor.validateOnlyNumber(this);
		});

//		// 품절보상쿠폰 선택시 분담률 disabled 처리
//		$("#chkCoupon_10006").change(function(){
//			$("#shareRate").prop("disabled", !this.checked);
//			if(!this.checked && ($("#baseInfoStatus").val() == 'C')){
//				$("#shareRate").val("");
//			}
//		});
//
//		// 분담률
//		$("#shareRate").change(function(){
//			_vendor.validateOnlyNumber(this);
//		});

		//표준카테고리 추가
		$("#addStandardCategory").click(function(){
			var stdCtgrNo = $("#selStandardCategory option:selected").val();
			var stdCtgrName = $("#selStandardCategory option:selected").text();
			if(abc.text.allNull(stdCtgrNo)){
				return;
			}
			var isDup = false;
			$("input[id^=vendorStdCtgrNo_]").each(function(){
				if($(this).val() == stdCtgrNo){
					isDup = true;
					return;
				}
			});
			if(isDup){
				alert(stdCtgrName+" 표준카테고리는 이미 추가 되어있습니다");
				return;
			}

			var cnt = $("#ulStandardCategory li").length + 1;
			var strHtml = "";
				strHtml += '<li>';
				strHtml += '<input type="hidden" name="vendorStdCtgList.vendorStdCtgrStatus" value="C">';
				strHtml += '<input type="hidden" name="vendorStdCtgList.vendorStdCtgrNo" id="vendorStdCtgrNo_'+cnt+'" value="'+ stdCtgrNo +'">';
				strHtml += '	<span class="subject">'+ stdCtgrName +'</span>';
				strHtml += '	<button type="button" onclick="abc.biz.vendor.info.base.parentAreaRemove(this);"  class="btn-item-del">';
				strHtml += '		<span class="ico ico-item-del"><span class="offscreen">'+ stdCtgrName +' 삭제</span></span>';
				strHtml += '	</button>';
				strHtml += '</li>';

				$("#ulStandardCategory").append(strHtml);
		});

		//브랜드 찾기 팝업
		$("#serchBrandPop").click(function(){
			abc.brandSearchPopup(true, "abc.biz.vendor.info.base.callbackFindBrand");
		});

		// 입점사 기본정보 저장
		$("#vedorBasicInfoSave").click(function(){
			if (!confirm('저장하시겠습니까?')) {
				return;
			}

			if(!_vendor.saveBaseValidation()){
				return;
			}

			//법인번호
			var crprtNoText = $("#crprtNo1").val().concat("-", $("#crprtNo2").val());
			var checkCrprtNoText = crprtNoText.replace(/-/gi,"");
			if(!abc.text.allNull(checkCrprtNoText)){
				var isCrprtNoText = abc.text.checkCorpID(checkCrprtNoText);
				if(!isCrprtNoText){
					alert("사용이 불가한 법인번호입니다.");
					return false;
				}
				$("#crprtNoText").val(crprtNoText);
			}

			//통신판매번호
			var mailBizNoText = $("#mailBizNo1").val().concat("-", $("#mailBizNo2").val(), "-", $("#mailBizNo3").val());
			$("#mailBizNoText").val(mailBizNoText);

			//배송안내
			for(var i=0; i<$('textArea[name="deliveryGuideList.dlvyGuideInfo"]').length; i++) {
				var textArea = $('textArea[name="deliveryGuideList.dlvyGuideInfo"]').eq(i);
				var id = textArea.prop('id');
				$('#' + id).val(CKEDITOR.instances[id].getData());
				
			}
//			for(var i in CKEDITOR.instances){
//				$("#"+CKEDITOR.instances[i].name).val(CKEDITOR.instances[i].getData());
//			}

			//품절보상 쿠폰
//			$("input:checkbox[id^=chkCoupon_]").each(function(){
//				if($(this).is(":checked")){
//					var value= 0;
//					if($(this).val() == '10006'){
//						value = $("#shareRate").val();
//					}
//					$("#vendorBaseForm").append('<input type="hidden" name="applyCouponList.shareRate" value="' + value + '" />');
//				}
//			});

			var url = "";
			if($("#upAuthNo").val() == "ROLE_30000"){
				url = "/vendor/info/update-basetab-po";
			}else if($("#baseInfoStatus").val() == "C"){
				url = "/vendor/info/create-basetab";
			}else{
				url = "/vendor/info/update-basetab";
			}
			$.ajax({
				type :"post",
				url : url,
				data : $("#vendorBaseForm").serialize()
			})
			.done(function(data){
				if(data.result == 'Y'){
					alert("저장하였습니다.");
					var url = "/vendor/info/read-detail?vndrNo="+data.vndrNo;
					$(location).attr('href', url);

				}else{
					alert("저장에 실패했습니다.");
				}
			})
			.fail(function(e){
				console.log("e : " + JSON.stringify(e));
				alert("저장에 실패했습니다.");
			});

		});

		// 우편번호찾기
		$("#postSearchBtnBase").click(function(){
			abc.postPopup(abc.biz.vendor.info.base.setPostCodeBase);
		});

		//입점사목록가기
		$("#vendorInfoGoList").click(function(){
			var msg = "입력한 내용을 저장하지 않고 목록화면으로 이동합니다 ";
			if(!confirm(msg)){
				return;
			}

			vendorSearchForm.method = "post";
			vendorSearchForm.action = "/vendor/info";
			vendorSearchForm.submit();
		});

		//입점사 부가정보 클릭
		$("#vendorAddInfo").click(function(){
			if($("#vendorAddForm").length < 1){
				_vendor.vendorAddInfo();
			}
		});

		//휴일정보 보기
		$("#vendorHoliday").click(function(){
			abc.biz.system.holiday.popupHolidayCalendar("");
		});

		//담당자 추가
		$(document).on("click","[name=vendorManagerAddBtn]",function(){
			_vendor.managerInfo();
		});

		//담당자 삭제
		$(document).on("click","[id^=vendorManagerRemoveBtn_]",function(){
			var managerIndex = $(this).attr("id").split("_").pop();

			if($("[name=vendorMangerContentDiv]").length <= 1){
				alert("담당자는 최소 1명 이상 있어야 합니다");
				return;
			}
			var cnt = 0;
			$("#managerDelYn_"+managerIndex).val("Y");
			$("input[id^=managerDelYn_]").each(function(){
				if($(this).val() == 'N'){
					cnt++;
				}
			});
			if(cnt < 1){
				alert("담당자는 최소 1명 이상 있어야 합니다");
				return;
			}

			// 가장 먼저 등록된 담당자(최상단)에 대표 문구를 붙여준다.
			if($(this).closest("div[name=vendorMangerContentDiv]").attr("id") === $("div[name=vendorMangerContentDiv]:visible").eq(0).attr("id")) {
				var spans = $("div[name=vendorMangerContentDiv]:visible").eq(1).find("span[name=representative]");

				if(spans.length > 0) {
					for(var i=0; i<spans.length; i++) {
						var text = $(spans[i]).text();
						$(spans[i]).text("대표 "+text);
					}
				}
			}

			$("#vendorMangerContentDiv_"+managerIndex).hide();
			$("#vendorMangerContentDiv_"+managerIndex).find(".tbl-row").find("input").attr("disabled", true);

		});

		// 담당자 연락처 1 숫자 체크
		$(document).on("keyup","[id^=hdphnNoText_]",function(){
			abc.text.validateOnlyNumber(this);
		});

		// 담당자 연락처 2 숫자 체크
		$(document).on("keyup","[id^=telNoText_]",function(){
			abc.text.validateOnlyNumber(this);
		});

		//담당자ID 중복확인
		$(document).on("change","[id^=vndrMngrId_]",function(){
			var managerIndex = $(this).attr("id").split("_").pop();
			$("#vndrMngrIdSuccess_"+managerIndex).val("N");
		});

		//담당자ID중복확인
		$(document).on("click","[id^=vndrMngrIdDup_]",function(){
			var managerIndex = $(this).attr("id").split("_").pop();
			_vendor.loginIdDup(managerIndex);
		});

		//담당자ID문자열 체크
		$(document).on("keyup","[id^=vndrMngrId_]",function(){
			abc.text.validateOnlyAlphabetNum(this);
		});

		//담당자이메일 유효성체크
		$(document).on("change","[id^=emailAddrText_]",function(){
			if(!abc.text.isEmail($(this).val())){
				alert("메일주소를 올바르게 입력해주세요");
				var id = $(this).attr("id");
				$("#"+id).get(0).focus();
				return false;
			}
		});

	}


	/**
	 * 브랜드 찾기 callback 이벤트
	 */
	_vendor.callbackFindBrand = function(data) {
		var brandNo;
		var brandName;
		for(var i in data){
			brandNo = data[i].brandNo;
			brandName = data[i].brandName;

			var isDup = false;
			$("input[id^=vendorBrandNo_]").each(function(){
				if($(this).val() == brandNo){
					isDup = true;
					return;
				}
			});
			if(isDup){
				alert(brandName+" 브랜드는  이미 추가 되어있습니다")
				return;
			}

			var cnt = $("#ulVendorBrand li").length + 1;
			var strHtml = "";
			strHtml += '<li>';
			strHtml += '<input type="hidden" name="vendorbrandList.vendorBrandStatus" value="C">';
			strHtml += '<input type="hidden" name="vendorbrandList.vendorBrandNo" id="vendorBrandNo_'+cnt+'" value="'+ brandNo +'">';
			strHtml += '	<span class="subject">'+ brandName +'</span>';
			strHtml += '	<button type="button" onclick="abc.biz.vendor.info.base.parentAreaRemove(this);"  class="btn-item-del">';
			strHtml += '		<span class="ico ico-item-del"><span class="offscreen">'+ brandName +' 삭제</span></span>';
			strHtml += '	</button>';
			strHtml += '</li>';

			$("#ulVendorBrand").append(strHtml);

		}
	}

	// 우편번호 세팅
	_vendor.setPostCodeBase = function(data){
		$("#postCodeTextBase").val(data.postCode);
		$("#postAddrTextBase").val(data.postAddress);
//        $("#buildingCode").val(data.buildingCode); 건물번호
		$("#dtlAddrTextBase").focus();  // 상세주소에 포커스
	}


	//입점사 부가정보 영역
	_vendor.vendorAddInfo = function(){
		var params = {"vndrNo" : $("#vndrNo").val()};
		var url = "/noacl/info/create-addinfo";

		$.ajax({
			type :"get",
			url : url,
			dataType: "html",
			data: params
		})
		.done(function(data){
			$("#tabContent2").append($(data).fadeIn());
			$(".ui-cal").each(function(){
				abc.namespace.front.backOffice.setDatepicker(this);
			});
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
		});
	}


	// SAVE VALIDATION
	_vendor.saveBaseValidation = function(){

		var inputObjs = $(".th-required");
		var bEmpty = true;

		inputObjs.each(function(index) {

			var inputTarget = $(this).parent().next().find("input");

			inputTarget.each(function(){
				if (abc.text.allNull($(this).val()) && !this.disabled && ( $(this).attr('name') != 'dtlAddrText')) {
					bEmpty = false;

					alert($(this).attr('title') + "은 필수 입력사항입니다.");
					$(this).focus();
					return false;
				}
			})
			if (!bEmpty){
				return false;
			}
		});

		if (!bEmpty){
			return false;
		}

		//배송업체 지정
		if(abc.text.allNull($("#logisVndrCode option:selected").val())){
			alert("배송업체 지정을 해주세요");
			$("#logisVndrCode").focus();
			return false;
		}

		//패널티 부과비율
		if($("#penltyLevyRate").val() > 20){
			alert("패널티 부과비율은 20%를 초과할수 없습니다.");
			$(this).val("");
			$(this).focus();
			return false;
		}

		//전시채널
		if($("input[id^=chkChannel_]:checkbox:checked").length == 0){
			alert("전시채널을 선택해 주세요");
			$("input[id^=chkChannel_]")[0].focus();
			return false;
		}

		//표준카테고리지정
		if($("input[id^=vendorStdCtgrNo_]").length == 0){
			alert("표준카테고리를 지정해 주세요");
			$("#selStandardCategory").focus();
			return false;
		}
		//브랜드지정
		if($("input[id^=vendorBrandNo_]").length == 0){
			alert("브랜드를 지정해 주세요");
			$("#serchBrandPop").focus();
			return false;
		}

		//금융기관
		if(abc.text.allNull($("#bankCode option:selected").val())){
			alert("금융기관을 선택해 주세요");
			$("#bankCode").focus();
			return false;
		}

		// 입점사 담당자 정보
		if($("[name=vendorMangerContentDiv]").length == 0){
			alert("담당자는 최소 1명 이상 있어야 합니다");
			return;
		}
		var managerCnt = 0;
		$("input[id^=managerDelYn_]").each(function(){
			if($(this).val() == 'N'){
				managerCnt++;
			}
		});
		if(managerCnt == 0){
			alert("담당자는 최소 1명 이상 있어야 합니다");
			return;
		}

		// 신규등록화면에서만 체크
		if($("#baseInfoStatus").val() != "U"){
			//입점사코드 중복확인
			if(insdMgmtInfoSuccess == 'N'){
				alert("입점사코드 중복확인을 해주세요");
				$("#insdMgmtInfoText").focus();
				return false;
			}

			//사업자번호 중복확인
			if(bizNoSuccess == 'N'){
//				alert("사업자번호 중복확인을 해주세요");
//				$("#bizNo1").focus();
//				return false;
			}
		}

		//담당자ID 중복체크
		var isDup = false;
		$('[id^="vndrMngrIdSuccess"]').each(function(idx,e) {
			idx++;
			if($("#vndrMngrIdSuccess_"+idx).val() == 'N' && $("#managerDelYn_"+idx).val() == 'N'){
				isDup = true;
				alert("담당자 ID 중복확인을 해주세요");
				$("#vndrMngrId_"+idx).focus();
				return false;
			};
		});
		if(isDup){
			return false;
		}

		return true;
	}


	// 사업자번호 validation
	_vendor.bizNoValidation = function(){

		if(abc.text.allNull($("input[name=bizNo1]").val())){
			alert("사업자번호를 입력하세요");
			$("#bizNo1").focus();
			return false;
		}

		if(abc.text.allNull($("input[name=bizNo2]").val())){
			alert("사업자번호를 입력하세요");
			$("#bizNo2").focus();
			return false;
		}

		if(abc.text.allNull($("input[name=bizNo3]").val())){
			alert("사업자번호를 입력하세요");
			$("#bizNo3").focus();
			return false;
		}
		return true;
	}

	_vendor.chargeMdDisplay = function(){
		var cnt = 1;
		$("input[id^=chkChannel_]").each(function() {
			if(this.checked){
				$("#tr_"+$(this).val()).css("display","");
				cnt++;
			}else{
				$("#tr_"+$(this).val()).css("display","none");
			}
		});
		$("#thMdRowspan").attr("rowspan", cnt);
		if(cnt > 1){
			$("#thMdDefault").css("display","none");
		}else{
			$("#thMdDefault").css("display","");
		}
	}

	//입점사 대표담당자 정보
	_vendor.managerInfo = function(){

		var params = {"managerIndex" : $("#managerIndex").val()};
		$.ajax({
			type :"get",
			url : "/vendor/info/vendor-manager-add",
			dataType: "html",
			data: params
		})
		.done(function(data){
			$("#vendorManagerAddDiv").append($(data).fadeIn());
		})
		.fail(function(e){
			console.log("e :" + JSON.stringify(e));
		});
	}

	//담당자ID중복확인
	_vendor.loginIdDup = function(idx){
		var loginId = $("#vndrMngrId_"+idx).val();

		if(loginId.length <= 2 || loginId.length >= 20){
			alert("아이디는 3자이상 20자 이하로 등록해 주세요.");
			$("#vndrMngrId_"+idx).focus();
			return false;
		}

		var dupCnt= 0;
		$("[id^=vndrMngrId_]").each(function(i){
			if($(this).val() == loginId){
				dupCnt++;
			}
		});


		if(dupCnt > 1){
			$("#vndrMngrIdSuccess_"+idx).val("N");
			$("#vndrMngrId_"+idx).focus();
			alert("중복 id가 있습니다.");
		}else{
			$.ajax({
				type :"post",
				url : "/noacl/read-chekLoginId",
				data : {"loginId" : loginId},
			})
			.done(function(data){
				var resultCnt = data.resultCnt;

				if(resultCnt > 0){
					$("#vndrMngrIdSuccess_"+idx).val("N");
					$("#vndrMngrId_"+idx).focus();
					alert("중복 id가 있습니다.");

				}else{
					$("#vndrMngrIdSuccess_"+idx).val("Y");
					alert("해당 id는 사용 가능한 id입니다.");
				}
			})
			.fail(function(e){

			});
		}


	}

	_vendor.parentAreaRemove = function(el) {

		$(el).parent().remove();

	}

	_vendor.validateOnlyNumber = function(el){
		abc.text.validateOnlyNumber(el);

		if($(el).val() < 0 || $(el).val() > 100){
			alert("1~100까지의 숫자만 입력가능합니다");
			$(el).val("");
			$(el).focus();
		}
	}

})();