/***
 * 관리자 업무와 관련된 함수 정의.
 *
 */
(function() {
	var _admin = abc.object.createNestedObject(window,"abc.biz.vendor.admin");

	$("select#authNo option[value='ROLE_20003']").remove();

	/*************************************************************************
	 *								관리자 목록
	 *************************************************************************/

	/**
	 * 관리자 목록 초기 세팅
	 */
	_admin.initAdminSheet = function() {
		//
		createIBSheet2(document.getElementById("adminGrid"), "adminSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"사용자번호", 		Type:"Text",		SaveName:"adminNo",				Width: 10, Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 5,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"권한 적용 시스템", 	Type:"Text",		SaveName:"authApplySystemType",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"권한 그룹명", 		Type:"Text",		SaveName:"authName",			Width: 20, Align:"Left", 	Edit:0, Sort:0}
			, {Header:"관리자", 			Type:"Text",		SaveName:"listDisplayName",		Width: 25, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer", Sort:0}
			, {Header:"이메일", 			Type:"Text",		SaveName:"listEmail",			Width: 25, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"휴대폰번호", 		Type:"Text",		SaveName:"listHdphnNoText",		Width: 25, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"개인정보 취급여부", 	Type:"Combo",		SaveName:"memberInfoMgmtYn",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사용여부", 			Type:"Combo",		SaveName:"useYn",				Width: 15, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"작성자", 			Type:"Text",		SaveName:"listRgstName",		Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"작성일시", 			Type:"Date",		SaveName:"rgstDtm",				Width: 30, Align:"Center", 	Edit:0, Format:"YmdHms"}
		];

		// Grid 초기화
		IBS_InitSheet(adminSheet , initSheet);
		// Grid 건수 정보 표시
		adminSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		adminSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		adminSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		adminSheet.SetExtendLastCol(1);


		adminSheet.InitDataCombo(0 , "memberInfoMgmtYn" , "가능|불가능" , "Y|N");
		adminSheet.InitDataCombo(0 , "useYn" , "사용|사용안함" , "Y|N");
	}

	/**
	 * 관리자 목록 Action관리
	 */
	_admin.adminDoAction = function(sAction){
		switch(sAction) {
			// 조회
			case "search" :
				var searchKey = $("#searchKey option:selected").val();
				var searchValue = $("#searchValue").val();

				if(!abc.text.allNull(searchValue) && abc.text.allNull(searchKey)){
					alert("검색어 구분값을 선택해주세요.");
					$("#searchKey").focus();
					return ;
				}

				var pageCount = $('#pageCount').val();
				var param = { url : "/vendor/admin/read-list"
					, onePageRow : pageCount
					, CPage : this._CPage
					, subparam : FormQueryStringEnc(document.frmSearch)
					, sheet : "adminSheet" };

				DataSearchPaging(param);

				break;
		}
	}

	/**
	 * 검색어 조건 체크
	 */
	_admin.searchConditionSet = function(searchKey){
		var searchValue = $("#searchValue").val();

		if(searchKey == "searchLoginId"){
			$("#loginId").val(searchValue);
		}else if(searchKey == "searchAdminName"){
			$("#adminName").val(searchValue);
		}else if(searchKey == "searchEmail"){
			$("#emailAddrText").val(searchValue);
		}else if(searchKey == "searchPhoneNum"){
			$("#hdphnNoText").val(searchValue);
		}
	}

	/**
	 * 권한 그룹 변경 팝업 호출
	 */
	_admin.authGroupUpdate = function(){
		var adminNoStr;
		var authSystem;
		var arrayAdminNo = [];
		var arrayAuthSystem = [];
		var sRow = adminSheet.FindCheckedRow("updateCheck");

		//ROW 체크
		if(sRow == ""){
			alert("권한그룹을 변경할 관리자를 선택해 주세요.");
			return false;
		}

		//그리드에서 체크된 Row의 관리자아이디와 권한적용시스템 값을 배열로 담는다.
		for(var i=1; i <= adminSheet.RowCount(); i++){
			if(adminSheet.GetCellValue(i, "updateCheck") == "1"){
				arrayAuthSystem.push(adminSheet.GetCellValue(i, "authApplySystemType"));
				arrayAdminNo.push(adminSheet.GetCellValue(i, "adminNo"));
			}
		}

		//권한 적용 시스템의 배열의 값들 중 다른 값이 존재할 경우 예외처리한다.
		for(var i=0; i < arrayAuthSystem.length; i++){
			if(i == 0){
				authSystem = arrayAuthSystem[i];
			}

			if(authSystem != arrayAuthSystem[i]){
				alert("권한 적용시스템이 다른 관리자가 있습니다.");
				return false;
			}
		}

		//배열로 만들어진 관리자 아이디값을 문자열로 변환
		for(var i=0; i < arrayAdminNo.length; i++){
			if(i == 0){
				adminNoStr = arrayAdminNo[i];
			}else{
				adminNoStr += "," + arrayAdminNo[i];
			}
		}

		var url = "/vendor/admin/update-admin-authority-pop";
		var params = {}
		params.adminNoStr = adminNoStr;

		abc.open.popup({
			winname :	"adminAuthorityPop",
			url 	:	url,
			width 	:	645,
			height	:	590,
			params	:	params

		});
	}

	/**
	 * 검색어 키 값 변경 시 이벤트
	 */
	_admin.searchValueSet = function(){
		var searchKey = $("#searchKey option:selected").val();

		//검색어 값을 초기화 후 포커스를 이동
//		$("#searchValue").val('');
		$("#searchValue").focus();

		//검색어와 관련된 hidden값을 초기화
		$("#loginId").val('');
		$("#adminName").val('');
		$("#emailAddrText").val('');
		$("#hdphnNoText").val('');

		if(searchKey == "hdphnNoText"){
			$("#searchValue").attr("onkeyup", "window.abc.text.validateOnlyNumber(this)");
		}else{
			$("#searchValue").removeAttr("onkeyup");
		}
	}

	/*************************************************************************
	 *								관리자 상세
	 *************************************************************************/

	/**
	 * 화면 초기 세팅
	 */
	_admin.AdminDetailInitSet = function(){
		// 이메일 초기 세팅
		var defaultEmailVal = $("#selEmail option:selected").val();

		$("#emailAddrDtl").val(defaultEmailVal);
		$("#emailAddrDtl").attr("readonly", true);
		$("#emailAddrDtl").attr("disabled", false);
	}

	/**
	 * 이메일 변경 시 세팅
	 */
	_admin.changeEmail = function(selEmailVal){

		if(selEmailVal == ""){
			$("#emailAddrDtl").val("");
			$("#emailAddrDtl").attr("readonly", false);
			$("#emailAddrDtl").attr("disabled", false);
			$("#emailAddrDtl").focus();
		}else{
			$("#emailAddrDtl").val(selEmailVal);
			$("#emailAddrDtl").attr("readonly", true);
			$("#emailAddrDtl").attr("disabled", false);
		}
	}

	/**
	 * 권한그룹명 변경 시 권한적용시스템 설정
	 */
	_admin.authGroupChange = function(authNo){

		$.ajax({
			type :"post",
			url : "/vendor/admin/read-authority",
			data : {"authNo" : authNo},
		})
		.done(function(data){
			if(authNo != ""){
				var authSystemType = data[0].authApplySystemType;

				if(authSystemType == "B"){
					$("#authSystemType").text("BO");
				}else{
					$("#authSystemType").text("PO");
				}

			}else{
				$("#authSystemType").text("");
			}
		})
		.fail(function(e){

		});
	}

	/**
	 * 관리자 아이디 키 입력 체크
	 */
	_admin.keyCheck = function(obj){
		// 중복확인 이후 아이디 변경이 발생할 수 있기 때문에 키입력 시 마다 값을 변경
		$("#confirmLoginId").val("");
		abc.text.validateOnlyAlphabetNum(obj);
	}

	/**
	 * 관리자 아이디 중복 체크
	 */
	_admin.checkLoginId = function(){

		var loginId = $("#loginId").val();

		if(loginId.length <= 2 || loginId.length >= 20){
			alert("아이디는 3자이상 20자 이하로 등록해 주세요.");
			$("#loginId").focus();
			return false;
		}

		$.ajax({
			type :"post",
			url : "/noacl/read-chekLoginId",
			data : {"loginId" : loginId},
		})
		.done(function(data){
			var resultCnt = data.resultCnt;

			if(resultCnt > 0){
				$("#loginId").focus();
				$("#confirmLoginId").val(resultCnt);
				alert("중복 id가 있습니다.");
			}else{
				$("#confirmLoginId").val(resultCnt);
				alert("해당 id는 사용 가능한 id입니다.");
			}
		})
		.fail(function(e){

		});
	}

	/**
	 * 접근 허용 아이피 추가
	 */
	_admin.addAccessIpText = function(){
		var cHtml = "";
		var maxCnt = 8;
		var addAccessIpVal = $("#accessIp").val();
		var cnt = $(".item-list > li > span").length;
		var itemList = $(".item-list");
		var itemListObj = $(".item-list > li > span");
		var regIpExp = /^([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\.([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3}$/;


		if(cnt > maxCnt){
			alert("접근 허용 아이피는 N개까지 추가가 가능합니다.");
			return false;
		}

		if(addAccessIpVal == ""){
			alert("접근 허용 아이피를 입력하세요.");
			$("#accessIp").focus();
			return false;
		}

		if(!regIpExp.test(addAccessIpVal)){
			alert("유효하지 않은 IP형식 입니다.");
			$("#accessIp").focus();
			return false;
		}

		for(var i=0; i < cnt; i++){
			if(addAccessIpVal == $(".item-list > li > span").eq(i).text()){
				alert("동일한 아이피가 이미 등록되어 있습니다.");
				return false;
			}
		}

		cHtml += '<li>';
		cHtml += '<input type="hidden" name="accessIpTexts" value="'+ addAccessIpVal +'">';
		cHtml += '<span class="subject">' + addAccessIpVal + '</span>';
		cHtml += '<button type="button" class="btn-item-del">';
		cHtml += '<span class="ico ico-item-del"><span class="offscreen">IP 삭제</span></span>';
		cHtml += '</button>';
		cHtml += '</li>';

		itemList.append(cHtml);
	}

	/**
	 * 관리자 등록
	 */
	_admin.joinAdmin = function(){
		// 권한 그룹명 유효성 검사
		var authGroup = $("#authNo option:selected").val();
		if(authGroup == ""){
			alert("권한그룹명을 선택해야 합니다.");
			$("#authNo").focus();
			return false;
		}

		// 관리자 아이디 유효성 검사
		var loginId = $("#loginId").val();
		var confirmLoginId = $("#confirmLoginId").val();

		if(loginId == ""){
			alert("관리자 id를 입력해 주세요.");
			$("#loginId").focus();
			return false;
		}

		if(confirmLoginId == ""){
			alert("관리자 id 중복확인을 해야합니다.");
			return false;
		}else if(confirmLoginId > 0){
			alert("중복 id는 사용할 수 없습니다.");
			return false;
		}

		// 관리자명 유효성 검사
		var adminName = $("#adminName").val();
		if(adminName == ""){
			alert("관리자 이름을 입력하세요.");
			$("#adminName").focus();
			return false;
		}

		// 이메일 유효성 체크
		var emailAddrText;
		var emailAddr = $("#emailAddr").val();
		var emailAddrDtl = $("#emailAddrDtl").val();

		if(emailAddr == ""){
			alert("이메일을 입력하세요.");
			$("#emailAddr").focus();
			return false;
		}

		if(emailAddrDtl == ""){
			alert("이메일 상세주소를 입력하세요.");
			$("#emailAddrDtl").focus();
			return false;
		}

		emailAddrText = emailAddr + "@" + emailAddrDtl;
		$("#emailAddrText").val(emailAddrText);

		if(!abc.text.isEmail(emailAddrText)){
			alert("정확한 이메일 주소를 입력하세요.");
			return false;
		}

		// 전화번호 유효성 체크
		var hdphnNoText = $("#hdphnNoText").val();

		if(hdphnNoText == ""){
			alert("휴대폰 번호를 입력해 주세요.");
			$("#hdphnNoText").focus();
			return false;
		}

		if(!abc.text.isPhoneNum(hdphnNoText)){
			alert("정확한 핸드폰번호를 입력해 주세요.");
			return false;
		}

		// 접근 허용 아이피 유효성 체크
		var ipItems;
		var ipItemCnt = $(".item-list > li > span").length;

		if(ipItemCnt < 1){
			alert("접근 허용 아이피는 1개 이상 등록되야 합니다.");
			return false;
		}

		if(confirm("저장하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/vendor/admin/create",
				data: $("#adminForm").serialize()
			})
			.done(function(data){
				if(data.result == "Y"){
					alert("저장되었습니다.");
				}
				opener.abc.biz.vendor.admin.adminDoAction('search');
				window.self.close();
			})
			.fail(function(e){

			});
		}
	}

	/*************************************************************************
	 *							관리자 권한 그룹 변경
	 *************************************************************************/

	/**
	 * 관리자 권한그룹 변경 팝업 초기세팅
	 */
	_admin.initAuthoritySheet = function() {
		// Grid 객체 생성 //
		createIBSheet2(document.getElementById("authorityGrid"), "adminAuthoritySheet", "100%", "420px");

		// Grid 초기화 기본/헤더/컬럼선언 //
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount,  MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"" ,		 		Type:"Status",		SaveName:"status",				Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"사용자번호", 		Type:"Text",		SaveName:"adminNo",				Width: 0,  Align:"Center", 	Edit:0, Hidden:1}
			, {Header:"",				Type:"CheckBox",	SaveName:"updateCheck",			Width: 6,  Align:"Center",	Edit:1, Sort:0}
			, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"권한 그룹명", 		Type:"Text",		SaveName:"authName",			Width: 20, Align:"Left", 	Edit:0, Sort:0}
			, {Header:"권한적용 시스템", 	Type:"Text",		SaveName:"authApplySystemType",	Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"관리자", 			Type:"Text",		SaveName:"adminName",			Width: 20, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"개인정보\n취급여부", 	Type:"Text",		SaveName:"memberInfoMgmtYn",	Width: 12, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"사용여부", 			Type:"Text",		SaveName:"useYn",				Width: 12, Align:"Center", 	Edit:0, Sort:0}
		];

		// Grid 초기화 //
		IBS_InitSheet(adminAuthoritySheet , initSheet);

		// Grid 건수 정보 표시 //
		adminAuthoritySheet.SetCountPosition(3);

		// Grid 페이지 네비게이션 버튼 표시 //
		adminAuthoritySheet.SetPagingPosition(2);

		// Grid width 자동 조절 //
		adminAuthoritySheet.FitColWidth();

		// Grid 마지막 컬럼을 All 너비에 맞춘다. //
		adminAuthoritySheet.SetExtendLastCol(1);

		this.adminAuthorithDoAction('search');
	}

	/**
	 * 관리자 권한그룹 변경 체크
	 */
	_admin.checkAuthGroup = function(){

		var authNo = $("#authNo option:selected").val();
		var sRow = adminAuthoritySheet.FindCheckedRow("updateCheck");

		//콤보박스 값 체크
		if(authNo == ""){
			alert("변경할 권한 그룹명을 선택해 주세요.");
			return false;
		}

		//ROW 체크
		if(sRow == ""){
			alert("권한 그룹을 변경할 관리자를 선택해 주세요.");
			return false;
		}

		return true;
	}

	/**
	 * 관리자 변경그룹 팝업 Action 관리
	 */
	_admin.adminAuthorithDoAction = function(sAction){
		switch(sAction) {

		case "search" :
			var pageCount = $('#pageCount').val();

			var param = { url : "/vendor/admin/read-list"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "adminAuthoritySheet" };

			DataSearchPaging(param);

			break;
		case "update" :
			var authGroupNm = $("#authNo option:selected").text();

			if(!this.checkAuthGroup()){
				return false;
			}

			if(confirm("[" + authGroupNm + "]" + "으로 변경하시겠습니까? ")){
				var param = { url:"/vendor/admin/update-admin-authority"
					, subparam:FormQueryStringEnc(document.frmSearch)
					, sheet:"adminAuthoritySheet"};

				DataSave(param);
			}

			break;
		}
	}

	/*************************************************************************
	 *							관리자 상세 - 기본정보
	 *************************************************************************/

	/**
	 * 탭 클릭 이벤트
	 */
	_admin.tabHandler = function(event, ui) {
		if (ui.newTab.prevObject.attr('id') === "tabLogin") {
			if (typeof channelSheet === "undefined") {
				abc.biz.vendor.admin.initLoginSheet();
			}
		}else if(ui.newTab.prevObject.attr('id') === "tabHistory"){
			if (typeof channelSheet === "undefined") {
				abc.biz.vendor.admin.initChangeHistorySheet();
				$("a[name^=per1Month]").trigger("click");
				changeSheet.RemoveAll();
			}
		}
	}

	/**
	 * 관리자 기본정보 데이터 세팅
	 */
	_admin.initAdminDetailData = function(){
		var batchErrorAlertYn = $("#batchErrorVal").val();
		var memberInfoYn = $("#memberInfoVal").val();
		var longUnusedYn = $("#longUnusedVal").val();
		var pswdDscordYn = $("#pswdDscordVal").val();
		var authNo = $("#authVal").val();
		var useYn = $("#useVal").val();

		//권한그룹명
		$("#authNo").val(authNo).attr("selected", "selected");

		// 배치에러 SMS발송여부
		if(batchErrorAlertYn == "Y"){
			$("#batchErrorAlertYn").prop('checked', true);
		}else{
			$("#batchErrorAlertYn").prop('checked', false);
		}

		// 개인정보 취급여부
		if(memberInfoYn == "Y"){
			$("#memberInfoMgmtY").prop('checked', true);
		}else{
			$("#memberInfoMgmtN").prop('checked', true);
		}

		// 장기미사용 여부
		if(longUnusedYn == "Y"){
			$("#longUnusedY").prop('checked', true);
		}else{
			$("#longUnusedN").prop('checked', true);
		}

		// 비밀번호 불일치 블록 여부
		if(pswdDscordYn == "Y"){
			$("#pswdDscordY").prop('checked', true);
		}else{
			$("#pswdDscordN").prop('checked', true);
		}

		// 사용여부
		if(useYn == "Y"){
			$("#useY").prop('checked', true);
		}else{
			$("#useN").prop('checked', true);
		}
	}

	/**
	 * 화면 로드 시 이메일 셋팅
	 */
	_admin.detailEmailSet = function(emailTxt){
		var emailCnt = 0;
		var email = emailTxt.split("@");
		var emailAddr = email[0];
		var emailAddrDtl = email[1];
		$("#emailAddr").val(emailAddr);

		//콤보박스 내에 이메일 존재 여부를 확인
		$("#selEmail option").each(function(){
			if(this.value == emailAddrDtl){
				emailCnt++;
			}
		});

		if(emailCnt > 0){
			$("#emailAddrDtl").val(emailAddrDtl);
			$("#emailAddrDtl").attr("readonly", true);
			$("#emailAddrDtl").attr("disabled", false);
			$("#selEmail").val(emailAddrDtl).attr("selected", "selected");
		}else{
			$("#emailAddrDtl").val(emailAddrDtl);
			$("#emailAddrDtl").attr("readonly", false);
			$("#emailAddrDtl").attr("disabled", false);
			$("#selEmail").val("").attr("selected", "selected");
		}
	}

	/**
	 * 비밀번호 초기화
	 */
	_admin.pswdReset = function(){
		//패스워드 초기화 시 문자 전송을 위해 이름, 전화번호를 체크 및 세팅
		var adminName = $("#adminName").val();
		var adminNo = $("input[name=adminNo]", document.adminDetailForm).val();
		
		var hdphnNoText = $("#hdphnNoText").val();

		if(abc.text.allNull(adminName)){
			alert("관리자 이름을 입력하세요.");
			$("#adminName").focus();
			return false;
		}
		
		if(abc.text.allNull(hdphnNoText)){
			alert("전화번호 입력은 필수 입니다.");
			$("#hdphnNoText").focus();
			return false;
		}
		
		if(!abc.text.isPhoneNum(hdphnNoText)){
			alert("정확한 핸드폰번호를 입력해 주세요.");
			return false;
		}

		if(confirm("비밀번호를 초기화 하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/vendor/admin/update-pswd-reset",
				data : { "adminNo" : adminNo, "hdphnNoText" : hdphnNoText, "adminName" : adminName}
			})
			.done(function(data){
				if(data.result == "Y"){
					alert("저장하였습니다.");
					location.reload();
				}
			})
			.fail(function(e){
				alert("저장에 실패하였습니다.");
				location.reload();
			});
		}
	}

	/**
	 * 관리자 상세데이터 저장
	 */
	_admin.updateAdmin = function(){

		var authGroup = $("#authNo option:selected").val();
		if(authGroup == ""){
			alert("권한그룹명을 선택해야 합니다.");
			$("#authNo").focus();
			return false;
		}

		// 관리자명 유효성 검사
		var adminName = $("#adminName").val();
		if(adminName == ""){
			alert("관리자 이름을 입력하세요.");
			$("#adminName").focus();
			return false;
		}

		// 이메일 유효성 체크
		var emailAddrText;
		var emailAddr = $("#emailAddr").val();
		var emailAddrDtl = $("#emailAddrDtl").val();

		if(emailAddr == ""){
			alert("이메일을 입력하세요.");
			$("#emailAddr").focus();
			return false;
		}

		if(emailAddrDtl == ""){
			alert("이메일 상세주소를 입력하세요.");
			$("#emailAddrDtl").focus();
			return false;
		}

		emailAddrText = emailAddr + "@" + emailAddrDtl;
		$("#emailAddrText").val(emailAddrText);

		if(!abc.text.isEmail(emailAddrText)){
			alert("정확한 이메일 주소를 입력하세요.");
			return false;
		}

		// 전화번호 유효성 체크
		var hdphnNoText = $("#hdphnNoText").val();

		if(hdphnNoText == ""){
			alert("휴대폰 번호를 입력해 주세요.");
			$("#hdphnNoText").focus();
			return false;
		}

		if(!abc.text.isPhoneNum(hdphnNoText)){
			alert("정확한 핸드폰번호를 입력해 주세요.");
			return false;
		}

		if(confirm("저장하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/vendor/admin/update",
				data: $("#adminDetailForm").serialize()
			})
			.done(function(data){
				if(data.result == "Y"){
					alert("저장하였습니다.");

					opener.abc.biz.vendor.admin._CPage = opener.adminSheet.GetCurrentPage();
					opener.abc.biz.vendor.admin.adminDoAction('search');
					opener.abc.biz.vendor.admin._CPage = 1;

					location.reload();
				}
			})
			.fail(function(e){
				alert("저장에 실패하였습니다.");
				location.reload();
			});
		}
	}

	/**
	 * 관리자 목록 Action관리
	 */
	_admin.DetailDoAction = function(sAction){
		switch(sAction) {
			// 로그인 이력 조회
			case "loginSearch" :
				var pageCount = $('#loginPageCount').val();

				var param = { url : "/vendor/admin/read-admin-use-history"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.loginHistoryForm)
					, sheet : "loginSheet" };

				DataSearchPaging(param);

				break;
			case "changeSearch" :
				var pageCount = $('#changePageCount').val();

				var param = { url : "/vendor/admin/read-admin-change-history"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.changeHistoryForm)
					, sheet : "changeSheet" };

				DataSearchPaging(param);

				break;
		}
	}

	/*************************************************************************
	 *							관리자 상세 - 로그인이력
	 *************************************************************************/
	/**
	 * 로그인 이력 그리드
	 */
	_admin.initLoginSheet = function() {
		// Grid 객체 생성 //
		createIBSheet2(document.getElementById("loginGrid"), "loginSheet", "100%", "420px");

		// Grid 초기화 기본/헤더/컬럼선언 //
		var initSheet = {};
		var pageCount = $('#loginPageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount,  MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"" ,		 				Type:"Status",		SaveName:"status",					Width: 0,  Align:"", 		Edit:0, Hidden:1}
			, {Header:"관리자 인증 히스토리 고유번호", 	Type:"Text",		SaveName:"useHistSeq",				Width: 15, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"세션 아이디", 				Type:"Text",		SaveName:"sessionId",				Width: 20, Align:"Left", 	Edit:0, Sort:0}
			, {Header:"인증유형", 					Type:"Text",		SaveName:"loginFailRsnCodeName",	Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"접속 IP", 					Type:"Text",		SaveName:"accessIpText",			Width: 10, Align:"Center", 	Edit:0, Sort:0}
			, {Header:"발생 일시", 				Type:"Text",		SaveName:"rgstDtm",					Width: 14, Align:"Center", 	Edit:0, Format:"YmdHms"}
		];

		// Grid 초기화 //
		IBS_InitSheet(loginSheet , initSheet);

		// Grid 건수 정보 표시 //
		loginSheet.SetCountPosition(3);

		// Grid 페이지 네비게이션 버튼 표시 //
		loginSheet.SetPagingPosition(2);

		// Grid width 자동 조절 //
		loginSheet.FitColWidth();

		// Grid 마지막 컬럼을 All 너비에 맞춘다. //
		loginSheet.SetExtendLastCol(1);

		abc.biz.vendor.admin.DetailDoAction('loginSearch');
	}

	/*************************************************************************
	 *							관리자 상세 - 변경이력
	 *************************************************************************/

	/**
	 * 변경 이력 그리드
	 */
	_admin.initChangeHistorySheet = function() {

		// Grid 객체 생성 //
		createIBSheet2(document.getElementById("changeGrid"), "changeSheet", "100%", "420px");

		// Grid 초기화 기본/헤더/컬럼선언 //
		var initSheet = {};
		var pageCount = $('#changePageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount,  MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheet.Cols = [
			  {Header:"" ,		 				Type:"Status",		SaveName:"status",				Width: 0,  	Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"번호", 					Type:"Seq",			SaveName:"",					Width: 5, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"구분", 					Type:"Text",		SaveName:"chngFieldName",		Width: 10, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"변경내용(변경 전 -> 변경후)", 	Type:"Text",		SaveName:"chngFieldValue",		Width: 25, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"수정자", 					Type:"Text",		SaveName:"rgsterName",			Width: 10, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"수정일시", 					Type:"Text",		SaveName:"rgstDtm",				Width: 15, 	Align:"Center", 	Edit:0, Format:"YmdHms"}
		];

		// Grid 초기화
		IBS_InitSheet(changeSheet , initSheet);

		// Grid 건수 정보 표시
		changeSheet.SetCountPosition(3);

		// Grid 페이지 네비게이션 버튼 표시
		changeSheet.SetPagingPosition(2);

		// Grid width 자동 조절
		changeSheet.FitColWidth();

		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		changeSheet.SetExtendLastCol(1);

		abc.biz.vendor.admin.DetailDoAction('changeSearch');
	}

	/*************************************************************************
	 *						관리자 상세 - 본인계정접속(상세)
	 *************************************************************************/

	/**
	 * 관리자 정보 저장
	 */
	_admin.changeDetailInfo = function(){

		// 비밀번호 유효성 체크
		if(!window.abc.checkPassword()){
			return false;
		}

		// 비밀번호 확인 유효성 체크
		if(!window.abc.checkConfirmPassword()){
			return false;
		}

		// 이메일 유효성 체크
		var emailAddrText;
		var emailAddr = $("#emailAddr").val();
		var emailAddrDtl = $("#emailAddrDtl").val();

		if(emailAddr == ""){
			alert("이메일을 입력하세요.");
			$("#emailAddr").focus();
			return false;
		}

		if(emailAddrDtl == ""){
			alert("이메일 상세주소를 입력하세요.");
			$("#emailAddrDtl").focus();
			return false;
		}

		emailAddrText = emailAddr + "@" + emailAddrDtl;
		$("#emailAddrText").val(emailAddrText);

		if(!abc.text.isEmail(emailAddrText)){
			alert("정확한 이메일 주소를 입력하세요.");
			return false;
		}

		// 전화번호 유효성 체크
		var hdphnNoText = $("#hdphnNoText").val();

		if(hdphnNoText == ""){
			alert("휴대폰 번호를 입력해 주세요.");
			$("#hdphnNoText").focus();
			return false;
		}

		if(!abc.text.isPhoneNum(hdphnNoText)){
			alert("정확한 핸드폰번호를 입력해 주세요.");
			return false;
		}

		if(confirm("저장하시겠습니까?")){
			$.ajax({
				type :"post",
				url : "/noacl/update-account",
				data: $("#adminDetailForm").serialize()
			})
			.done(function(data){
				if(data.result > 0){
					alert(data.Message);
					window.self.close();
				}else{
					alert(data.Message);
				}
			})
		}
	}

	/*************************************************************************
	 *							관리자 조회 - 공통 팝업
	 *************************************************************************/

	/**
	 * 변경 이력 그리드
	 */
	_admin.initPopAdminList = function() {

		// Grid 객체 생성 //
		createIBSheet2(document.getElementById("adminGrid"), "adminSheet", "100%", "429px");

		// Grid 초기화 기본/헤더/컬럼선언 //
		var initSheet = {};
		var pageCount = $('#pageCount').val();

		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount,  MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};
		initSheet.Cols = [
			  {Header:"" ,			Type:"Status",		SaveName:"",				Width: 0,  	Align:"", 			Edit:0, Hidden:1, Sort:0}
			, {Header:"",			Type:"CheckBox",	SaveName:"popChk",			Width: 10, 	Align:"Center", 	Edit:1, Sort:0}
			, {Header:"관리자명",		Type:"Text",		SaveName:"adminName",		Width: 30, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"관리자ID",		Type:"Text",		SaveName:"loginId",			Width: 30, 	Align:"Center", 	Edit:0, Sort:0}
			, {Header:"관리자ID",		Type:"Text",		SaveName:"adminNo",			Width: 30, 	Align:"Center", 	Edit:0, Sort:0}
		];

		// Grid 초기화
		IBS_InitSheet(adminSheet , initSheet);

		// Grid 건수 정보 표시
		adminSheet.SetCountPosition(3);

		// Grid 페이지 네비게이션 버튼 표시
		adminSheet.SetPagingPosition(2);

		// Grid width 자동 조절
		adminSheet.FitColWidth();

		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		adminSheet.SetExtendLastCol(1);
	}

	_admin.popUpAdminSearch = function() {
		var loginId = $("#loginId").val();
		var adminName = $("#adminName").val();

		if(abc.text.allNull(loginId) && abc.text.allNull(adminName)){
			alert('관리자명 또는 관리자ID 입력은 필수입니다.');
			return false;
		}

		var pageCount = $('#pageCount').val();
		var param = { url : "/noacl/read-list-pop"
				, onePageRow : pageCount
				, subparam : FormQueryStringEnc(document.frmSearch)
				, sheet : "adminSheet" };

		DataSearchPaging(param);
	}
	
	/**
	 * 오프라인 매장 조회 콜백
	 */
	_admin.aconnectStoreSearchCallback = function(obj) {
		var storeNo = obj[0].storeNo;
		var storeName = obj[0].storeName;
		var html;
		//추가 전 기존에 추가되어 있는 매장이 있을 경우 삭제.
		$("#storeTr").find("span").remove();

		html = "<span class='text'>storeName : <span>" + storeName + "</span>, storeId : <span>" + storeNo + "</span></span>";
		$("#storeTr").prepend(html);
		$("#storeNo").val(storeNo);
	}

})();