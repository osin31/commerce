/*******************************************************************************
 * 공통코드 관리
 *
 */
(function() {

	var _commoncode = abc.object.createNestedObject(window,
			"abc.biz.system.commoncode");
	/**
	 * 공통코드 관리
	 */

	/**
	 * 코드그룹 그리드
	 */
	_commoncode.initGroupSheet = function() {
		// Grid 객체 생성
		createIBSheet2(document.getElementById("codeGroupGrid"), "codeGroupGridSheet");

		var initGroupSheet = {};
		initGroupSheet.Cfg = {SearchMode : smLazyLoad,	Page : 10,	DeferredVScroll : 1, MaxSort : 1};
		initGroupSheet.HeaderMode = {Sort : 1, ColMove : 0, ColResize : 1};
		initGroupSheet.Cols = [
			  {Header : "",			Type : "Status",SaveName : "status",		Width : 0,	Align : "",			Edit : 0,	Hidden : 1}
			, {Header : "정렬순서",	Type : "Int",	SaveName : "sortSeq",		Width : 12,	Align : "Center",	Edit : 0,	Sort : 1}
			, {Header : "코드그룹번호",	Type : "Text",	SaveName : "codeField",		Width : 38,	Align : "Left",		Edit : 0,	Sort : 1}
			, {Header : "코드그룹명",	Type : "Text",	SaveName : "codeName",		Width : 27,	Align : "Left",		Edit : 0,	Sort : 1}
			, {Header : "그룹구분",	Type : "Combo",	SaveName : "systemCodeYn",	Width : 20,	Align : "Center",	Edit : 0,	Sort : 1, ComboText : "시스템|일반",	ComboCode : "Y|N"}
			];
		// Grid 초기화
		IBS_InitSheet(codeGroupGridSheet, initGroupSheet);
		// Grid 건수 정보 표시
		codeGroupGridSheet.SetCountPosition(3);
		// Grid width 자동 조절
		codeGroupGridSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		codeGroupGridSheet.SetExtendLastCol(1);

		$("#sortSeq_hidden").val("1");
		this.doAction("readCodeGroup");
	}

	_commoncode.initUpDownCodeGridSheet = function() {
		// <%-- Grid 객체 생성 --%>
		createIBSheet2(document.getElementById("upDownCodeGrid"), "upDownCodeGridSheet");

		var pageCount = $("#pageCount").val();
		// <%-- Grid 초기화 기본/헤더/컬럼선언 --%>
		var initUpDownCodeGridSheet = {};

		initUpDownCodeGridSheet.Cfg = {SearchMode : smServerPaging2, Page : pageCount, DeferredVScroll : 1, MaxSort : 1, UseJsonAttribute : 1};
		initUpDownCodeGridSheet.HeaderMode = {Sort : 1, ColMove : 0,	ColResize : 1};
		initUpDownCodeGridSheet.Cols = [
			  {Header : "",			Type : "Status",SaveName : "status",		Width : 0,	Align : "",			Edit : 0,	Hidden : 1}
			, {Header : "",			Type : "Text",	SaveName : "codeField",		Width : 0,	Align : "",			Edit : 0,	Hidden : 1}
			, {Header : "코드번호",	Type : "Text",	SaveName : "codeDtlNo",		Width : 20,	Align : "Center",	Edit : 0,	Sort : 0,	FontBold:1, FontUnderline:1, Cursor:"Pointer"}
			, {Header : "상위코드명",	Type : "Text",	SaveName : "upCodeDtlName",	Width : 20,	Align : "Center",	Edit : 1,	Sort : 0}
			, {Header : "하위코드명",	Type : "Text",	SaveName : "codeDtlName",	Width : 20,	Align : "Center",	Edit : 1,	Sort : 0}
			, {Header : "정렬순서",	Type : "Int",	SaveName : "sortSeq",		Width : 20,	Align : "Center",	Edit : 1,	Sort : 0,	EditLen : 10}
			, {Header : "사용여부",	Type : "Combo",	SaveName : "useYn",			Width : 20,	Align : "Center",	Edit : 1,	Sort : 0,	ComboText : "사용|사용안함",	ComboCode : "Y|N"}
			];

		// <%-- Grid 초기화 --%>
		IBS_InitSheet(upDownCodeGridSheet, initUpDownCodeGridSheet);

		// <%-- Grid 건수 정보 표시 --%>
		upDownCodeGridSheet.SetCountPosition(3);

		// <%-- Grid 페이지 네비게이션 버튼 표시 --%>
		upDownCodeGridSheet.SetPagingPosition(2);

		// <%-- Grid width 자동 조절 --%>
		upDownCodeGridSheet.FitColWidth();

		// <%-- Grid 마지막 컬럼을 All 너비에 맞춘다. --%>
		upDownCodeGridSheet.SetExtendLastCol(1);

	}

	_commoncode.doAction = function(sAction) {
		switch (sAction) {
		// <%-- 조회 --%>
		case "readCodeGroup":
			var param = {
				url : "/system/commoncode/read-codegroup",
				onePageRow : 500,
				subparam : FormQueryStringEnc(document.frmSearch),
				sheet : "codeGroupGridSheet"
			};

			DataSearchPaging(param);

			break;

		// <%-- 코드그룹 등록 --%>
		case "createCodeGroup":
			$("#saveCheck").val("1");
			this.saveHide();
			break;

		// <%-- 저장버튼(save,update 분기) --%>
		case "saveCheck":
			var saveCheck = $("#saveCheck").val();
			if (saveCheck == "1") {
				if (this.mainValidate()) {
					if(abc.biz.system.commoncode.checkOverlapCodeField()){
						if (confirm("저장하시겠습니까?")) {
							this.createCodeGroup();
						}
					}
				} 
			} else {
				if (this.mainValidate()) {
					this.doAction("updateCodeGroup");
				}
			}

			break;

		// 코드그룹 삭제시 상,하위 코드 확인
		case "removeCheck":
			this.removeCheck();

			break;
		// 상위코드 삭제시 하위코드 확인
		case "removeUpCodeCheck":
			this.removeUpCodeCheck();

			break;

		//하위코드 삭제 체크
		case "removeDownCode":
			if(confirm("삭제하시겠습니까?")){
				this.removeDownCode();
			}

			break;

		// <%-- 상위코드 팝업 등록--%>
		case "createUpCodeForm":
			this.createUpCodeForm();

			break;

		// <%-- 하위코드 팝업 등록--%>
		case "createDownCodeForm":
			this.createDownCodeForm();

			break;

		// <%-- 수정 --%>
		case "updateCodeGroup":
			var useYn = $('input[name="useYn"]:checked').val();
			var systemCodeYn = $("#systemCodeYn").val();
			
			if (confirm("수정 하시겠습니까?")) {
				this.updateCodeGroup();
			}

			break;

		// <%-- 삭제 --%>
		case "removeCodeGroup":
			this.removeCodeGroup();
			
			break;

		// <%-- 상위 하위코드 조회 --%>
		case "readUpDownCode":
			var pageCount = $("#pageCount").val();

			var param = {
				url : "/system/commoncode/read-updowncode",
				onePageRow : pageCount,
				subparam : FormQueryStringEnc(document.frmSearch),
				sheet : "upDownCodeGridSheet"
			};
			DataSearchPaging(param);

			break;

		// <%-- 상위 하위 코드 수정 --%>
		case "updateUpDownCodeGrid":
			if (confirm("수정하시겠습니까?")) {
				var param = {
					url : "/system/commoncode/update-updown-codegrid",
					subparam : FormQueryStringEnc(document.detailSearch),
					sheet : "upDownCodeGridSheet"
				};
				DataSave(param);
			}
			break;

		case "upCodeCheck":
			if ($("#codeStatus").text() == "상위코드 등록") {
				if (this.upCodeValidate()) {
					this.createUpCodeDetail();
				} 
			} else {
				if (this.upCodeValidate()) {
					this.updateUpCodeDetail();
				} 
			}
			break;

		case "downCodeCheck":
			if ($("#codeStatus").text() == "하위코드 등록") {
				if (this.downCodeValidate()) {
					this.createDownCodeDetail();
				}
			} else {
				if (this.downCodeValidate) {
					this.updateDownCodeDetail();
				}
			}
			break;

		}
	}

	// <%-- 하위 코드 등록 팝업 --%>
	_commoncode.createDownCodeForm = function() {
		var codeField = $("#codeField_html").val();
		var url = "/system/commoncode/create-downcode-form?"; // 팝업창 페이지 URL
		var params = {};
		params.codeField = codeField, abc.open.popup({
			winname :	"codeDetailPopupForm",
			url : url,
			width : 600,
			height : 850,
			params : params

		});

	}

	// <%-- 등록시에 작성,수정 라인 가리기 --%>
	_commoncode.downCodeBottomHide = function() {
		$("#createCheck1").hide();
		$("#createCheck2").hide();
		$("#removeCheck").hide();
		$("input:radio[name='useYn'][value='Y']").prop('checked', true);
		$("#codeStatus").text("하위코드 등록");
		$("#codeDtlNo_td").text("코드번호 자동생성");

	}
	
	//코드필드 중복값 체크
	_commoncode.checkOverlapCodeField = function() {
		var filedData = {
			"codeField" : $("#codeField_html").val()
		};
		var rValue = true;
		$.ajax({
			type : "post",
			url : "/system/commoncode/check-overlap-codefield",
			data : filedData,
			async : false
		}).done(function(data) {
			console.log(data.count);
			if(data.count >= 1){
				alert("중복되는 코드필드입니다.");
				rValue = false;
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
		console.log(rValue);
		return rValue;
	}


	// <%--코드그룹 상세보기--%>
	_commoncode.readGroupDetail = function(Row, codeField) {
		var jData = {
			"codeField" : codeField,
			"sortSeq" : Row
		};
		$.ajax({
			type : "post",
			url : "/system/commoncode/read-codegroup-detail",
			data : jData
		}).done(function(data) {
			console.log(data[0]);
			if(abc.text.allNull(data[0].codeField)){
				return false;
			}
			
			$("#codeField_html").val(data[0].codeField);
			$("#codeName").val(data[0].codeName);
			$("#systemCodeYn").prop("disabled", true);
			$("#systemCodeYn").val(data[0].systemCodeYn);
			$("#noteText").val(data[0].noteText);
			$("#sortSeq").val(data[0].sortSeq);
			$("input:radio[name='useYn'][value="+ data[0].useYn + "]").prop('checked', true);
			$("#moderName").text(data[0].moderDetailDpName);
			$("#moderName").data("admin", data[0].moderNo);
			var mDtm = new Date(data[0].modDtm);
			$("#modDtm").text(mDtm.format());
			$("#rgsterName").text(data[0].rgsterDetailDpName);
			$("#rgsterName").data("admin", data[0].rgsterNo);
			var rDtm = new Date(data[0].rgstDtm)
			$("#rgstDtm").text(rDtm.format());
			
		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	// <%-- 삭제시 상위,하위코드 가지고 있는지 확인 --%>
	_commoncode.removeCheck = function() {
		var codeField = $("#codeField").val();
		var checkParam = {
			"codeField" : codeField
		};
		$.ajax({
			type : "post",
			url : "/system/commoncode/remove-check",
			data : checkParam
		}).done(function(data) {
			if (data == 0) {
				if (confirm("삭제된 코드는 복원할 수 없습니다. 삭제하시겠습니까?")) {
					_commoncode.doAction("removeCodeGroup");
					_commoncode.doAction("readCodeGroup");
				}
			} else {
				alert("상위,하위코드들을 가지고 있어 삭제가 불가능 합니다.");
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}

	// <%-- 삭제시 하위코드 가지고 있는지 확인 --%>
	_commoncode.removeUpCodeCheck = function() {
		var upCodeForm = $("#upCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/remove-upcodecheck",
			data : upCodeForm,
			async : false
		}).done(function(data) {
			if (data == 0) {
				if (confirm("삭제 하시겠습니까?")) {
					_commoncode.removeUpCode();
					opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
				}
			} else {
				alert("하위코드들을 가지고 있어 삭제가 불가능 합니다.");
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}

	//상위코드 삭제
	_commoncode.removeUpCode = function() {
		var upCodeForm = $("#upCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/remove-updowncode",
			async : false,
			data : upCodeForm
		}).done(function(data) {
			alert("삭제완료");
			opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
			window.self.close();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	//하위코드 삭제
	_commoncode.removeDownCode = function() {
		var downCodeForm = $("#downCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/remove-updowncode",
			async : false,
			data : downCodeForm
		}).done(function(data) {
			alert("삭제완료");
			opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
			window.self.close();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	// <%-- 코드그룹 수정--%>
	_commoncode.updateCodeGroup = function() {
		var codeField = $("#codeField_html").val();
		var codeName = $("#codeName").val();
		var noteText = $("#noteText").val();
		var sortSeq = $("#sortSeq").val();
		var useYn = $('input:radio[name="useYn"]:checked').val();
		var systemCodeYn = $("#systemCodeYn").val();

		$("#codeField").val(codeField);
		$("#sortSeq_hidden").val(sortSeq);
		$("#firstCheck").val("0");
		var param = {
			"codeField" : codeField,
			"codeName" : codeName,
			"noteText" : noteText,
			"sortSeq" : sortSeq,
			"useYn" : useYn,
			"systemCodeYn" : systemCodeYn
		};
		$.ajax({
			type : "post",
			url : "/system/commoncode/update-codegroup",
			async : false,
			data : param
		}).done(function(data) {
			alert("수정되었습니다.");
			_commoncode.doAction("readCodeGroup");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});

	}

	// <%-- 코드그룹 삭제--%>
	_commoncode.removeCodeGroup = function() {
		var codeField = $("#codeField_html").val();
		var systemCodeYn = $("#systemCodeYn").val();
		var deleteParam = {
			"codeField" : codeField,
			"systemCodeYn" : systemCodeYn
		};
		$.ajax({
			type : "post",
			url : "/system/commoncode/remove-codegroup",
			data : deleteParam,
			async : false
		}).done(function(data) {
			alert("삭제되었습니다");
			$("#firstCheck").val("1");
			$("#sortSeq_hidden").val("1");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	// <%-- 코드그룹 등록--%>
	_commoncode.createCodeGroup = function() {
		var codeField = $("#codeField_html").val();
		var codeName = $("#codeName").val();
		var systemCodeYn = $("#systemCodeYn").val();
		var noteText = $("#noteText").val();
		var useYn = $('input:radio[name="useYn"]:checked').val();
		var sortSeq = $("#sortSeq").val();

		$("#codeField").val(codeField);
		$("#sortSeq_hidden").val(sortSeq);
		$("#firstCheck").val("0");
		var saveParam = {
			"codeField" : codeField,
			"codeName" : codeName,
			"systemCodeYn" : systemCodeYn,
			"noteText" : noteText,
			"sortSeq" : sortSeq,
			"useYn" : useYn
		};
		$.ajax({
			type : "post",
			url : "/system/commoncode/create-codegroup",
			async : false,
			data : saveParam
		}).done(function(data) {
			alert("저장되었습니다.");
			_commoncode.doAction("readCodeGroup");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	// <%-- 상위 코드 등록 팝업 --%>
	_commoncode.createUpCodeForm = function() {
		var codeField = $("#codeField_html").val();
		var url = "/system/commoncode/create-upcode-form"; // 팝업창 페이지 URL
		var params = {};
		params.codeField = codeField, abc.open.popup({
			winname :	"codeDetailPopupForm",
			url : url,
			width : 600,
			height : 850,
			params : params

		});

	}
	

	// <%-- 하위코드 상세 수정 --%>
	_commoncode.updateDownCodeDetail = function() {
		var downCodeForm = $("#downCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/update-updowncode-detail",
			data : downCodeForm,
			async : false
		}).done(function(data) {
			alert("수정완료");
			opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
			window.self.close();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	// <%-- 하위코드 저장 --%>
	_commoncode.createDownCodeDetail = function() {
		var downCodeForm = $("#downCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/create-downcode-detail",
			data : downCodeForm,
			async : false
		}).done(function(data) {
			alert("저장되었습니다.");
			opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
			window.self.close();
		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}
	
	_commoncode.upDownCodeSystemHide = function(){
		$("#deleteCodeButton").hide();
		$("#saveCodeButton").hide();
		$("#fReset").hide();
	}

	// <%-- 상위,하위 코드 수정/상세 팝업 --%>
	_commoncode.updateUpDownCodeForm = function(codeDtlNo, upCodeDtlName) {
		var codeField = $("#codeField_html").val();
		var systemCodeYn = $("#systemCodeYn").val();
		var url = "/system/commoncode/update-updowncode-form"; // 팝업창 페이지 URL
		var params = {};
		params.codeField = codeField,
		params.codeDtlNo = codeDtlNo,
		params.upCodeDtlName = upCodeDtlName,
		params.systemCodeYn = systemCodeYn,
		
		abc.open.popup({
			winname :	"codeDetailPopupForm",
			url : url,
			width : 600,
			height : 850,
			params : params

		});
	}
	//상위코드 폼 reset
	_commoncode.upFormReload = function(){
		$.form("#upCodeForm").reset();
		$("#useYn_y").prop("checked", true);
	}
	
	//하위코드 폼  reset
	_commoncode.downFormReload = function(){
		$.form("#downCodeForm").reset();
		$("#useYn_y").prop("checked", true);
	}

	// <%-- 수정,작성란 가리기 --%>
	_commoncode.saveHide = function() {
		$.form("#frmSearch").reset();
		$("#moderName").text("");
		$("#modDtm").text("");
		$("#rgsterName").text("");
		$("#rgstDtm").text("");
		$("#systemCodeYn").prop("disabled", false);
		$("#systemCodeYn").val("N");
		$("#codeField_html").prop("readonly", false);
		$("input:radio[name='useYn'][value='Y']").prop('checked', true);
		$("#deleteHide").hide();
		_commoncode.selectSortSeq();
		// <%-- Grid 초기화 --%>
		upDownCodeGridSheet.RemoveAll();

		$("#upDownBtnArea").hide();
		$("#saveCheckValue1").hide();
		$("#saveCheckValue2").hide();
		$("#saveCodeGroupButton").show();
		$("#updateUpDownCodeButton").hide();
	}

	// <%-- 수정,작성란 보여주기 --%>
	_commoncode.saveShow = function() {
		$("#codeField_html").prop("readonly", true);
		$("#saveCheckValue1").show();
		$("#saveCheckValue2").show();
		$("#upDownBtnArea").show();
		$("#deleteHide").show();
	}

	// <%-- validation 체크 --%>
	_commoncode.mainValidate = function() {
		if(abc.text.allNull($("#codeField_html").val())) {
			alert("코드그룹번호를 입력해주세요.");
			$("#codeField_html").focus();

			return false;
		}
		if(abc.text.allNull($("#codeName").val())) {
			alert("코드그룹명을 입력해주세요.");
			$("#codeName").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#codeName").val(), 100)) {
			alert("코드그룹명은 100Byte를 넘기지 못합니다.");
			$("#codeName").focus();

			return false;
		}
		if(abc.text.allNull($("#sortSeq").val())) {
			alert("정렬순서를 입력해주세요.");
			$("#sortSeq").focus();

			return false;
		}else if (isNaN($("#sortSeq").val())) {
			alert("숫자만 입력해주세요");
			$("#sortSeq").val("");
			$("#sortSeq").focus();

			return false;
		}else if($("#sortSeq").val() == '0'){
			alert("정렬순서를 확인해주세요.");
			$("#sortSeq").val("");
			$("#sortSeq").focus();
			return false;
		}
		if(abc.text.isLimitLength($("#noteText").val(), 500)) {
			alert("설명은 500Byte를 넘기지 못합니다.");
			$("#noteText").focus();

			return false;
		}
		return true;
	}
	
	_commoncode.selectSortSeq = function(){
		var _data = {};
		$.ajax({
			type :"get",
			url : "/system/commoncode/read-max-sortseq",
			data : _data,
			async : false
		}).done(function(data){
			if(data.result == 1){
				$("#sortSeq").val(data.maxSortSeq);
			}else{
				alert("오류가 발생하였습니다."+data.Message);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	// <%-- validation 체크 --%>
	_commoncode.upCodeValidate = function() {
		if(abc.text.allNull($("#codeDtlName").val())) {
			alert("코드명을 입력해주세요.");
			$("#codeDtlName").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#codeDtlName").val(), 100)) {
			alert("코드명은 100Byte를 넘기지 못합니다.");
			$("#codeDtlName").focus();

			return false;
		}
		if(abc.text.allNull($("#sortSeq").val())) {
			alert("정렬순서를 입력해주세요.");
			$("#sortSeq").focus();

			return false;
		}else if (isNaN($("#sortSeq").val())) {
			alert("정렬순서는 숫자만 입력해주세요");
			$("#sortSeq").val("");
			$("#sortSeq").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo1").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo1").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo2").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo2").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo3").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo3").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo4").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo4").focus();
			
			return false;
		}
		if(abc.text.isLimitLength($("#addInfo5").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo5").focus();
			
			return false;
		}
		if(abc.text.isLimitLength($("#addInfo6").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo6").focus();
			
			return false;
		}

		return true;
	}

	// <%-- 등록시에 작성,수정자 라인 가리기 --%>
	_commoncode.upCodeBottomHide = function() {
		$("#createCheck1").hide();
		$("#createCheck2").hide();
		$("#removeCheck").hide();
		$("input:radio[name='useYn'][value='Y']").prop('checked', true);
		$("#moderName").text("");
		$("#modDtm").text("");
		$("#codeStatus").text("상위코드 등록");
		$("#codeDtlNo_td").text("코드번호 자동생성");

	}

	_commoncode.createUpCodeDetail = function() {
		var upCodeForm = $("#upCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/create-upcode-detail",
			async : false,
			data : upCodeForm
		}).done(function(data) {
			alert("저장되었습니다.");
			opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
			window.self.close();
		}).fail(function(jqXHR, textStatus, errorThrown) {
//			console.log(jqXHR, textStatus, errorThrown);
			alert("오류가 발생했습니다. - " + jqXHR.responseJSON.message);
		});
	}

	_commoncode.updateUpCodeDetail = function() {
		var upCodeForm = $("#upCodeForm").serialize();

		$.ajax({
			type : "post",
			url : "/system/commoncode/update-updowncode-detail",
			data : upCodeForm,
			async : false
		}).done(function(data) {
			alert("수정완료");
			opener.parent.abc.biz.system.commoncode.doAction("readUpDownCode");
			window.self.close();
		}).fail(function(e) {
			console.log("e :" + e);
		});
	}


	// <%-- 날짜데이터 변환 --%>
	_commoncode.getTimeStamp = function(dtm) {
		var d = new Date(dtm);
		var s =   this.leadingZeros(d.getFullYear(), 4) + '-'
				+ this.leadingZeros(d.getMonth() + 1, 2) + '-'
				+ this.leadingZeros(d.getDate(), 2) + ' ' +

				  this.leadingZeros(d.getHours(), 2) + ':'
				+ this.leadingZeros(d.getMinutes(), 2) + ':'
				+ this.leadingZeros(d.getSeconds(), 2);

		return s;
	}

	// <%-- 날짜데이터 변환 --%>
	_commoncode.leadingZeros = function(n, digits) {
		var zero = '';
		n = n.toString();

		if (n.length < digits) {
			for (i = 0; i < digits - n.length; i++) {
				zero += '0';
			}
		}
		return zero + n;
	}
	// <%-- validation 체크 --%>
	_commoncode.downCodeValidate = function() {
		if(abc.text.allNull($("#codeDtlName").val())) {
			alert("코드명을 입력해주세요.");
			$("#codeDtlName").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#codeDtlName").val(), 100)) {
			alert("코드명은 100Byte를 넘기지 못합니다.");
			$("#codeDtlName").focus();

			return false;
		}
		if(abc.text.allNull($("#sortSeq").val())) {
			alert("정렬순서를 입력해주세요.");
			$("#sortSeq").focus();

			return false;
		}else if (isNaN($("#sortSeq").val())) {
			alert("정렬순서는 숫자만 입력해주세요");
			$("#sortSeq").val("");
			$("#sortSeq").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo1").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo1").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo2").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo2").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo3").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo3").focus();

			return false;
		}
		if(abc.text.isLimitLength($("#addInfo4").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo4").focus();
			
			return false;
		}
		if(abc.text.isLimitLength($("#addInfo5").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo5").focus();
			
			return false;
		}
		if(abc.text.isLimitLength($("#addInfo6").val(), 100)) {
			alert("추가정보는 100Byte를 넘기지 못합니다.");
			$("#addInfo6").focus();
			
			return false;
		}

		return true;
	}

})();