/***
 * 입점관리 > 입점사 정보등록
 *
 */
(function() {

	var _vendorInfoPop = abc.object.createNestedObject(window,"abc.biz.vendor.pop");

	_vendorInfoPop.initvendorInfoPopSheet = function() {
		createIBSheet2(document.getElementById("vendorInfoPopGrid"), "vendorInfoPopSheet", "100%", "429px");
		var initvendorInfoPopSheet = {};
		var pageCount = $('#pageCount').val();

		initvendorInfoPopSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initvendorInfoPopSheet.HeaderMode = {Sort:0, ColMove:0, ColResize:1};
		initvendorInfoPopSheet.Cols = [
				  {Header:"", 				Type:"CheckBox",	SaveName:"chkData",		Width: 10, Align:"Center", Sort:0}
				, {Header:"번호", 			Type:"Seq",			SaveName:"",					Width: 15, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"입점사ID", 		Type:"Text",		SaveName:"vndrNo",				Width: 40, Align:"Center", 	Edit:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"입점업체명",		Type:"Text",		SaveName:"vndrName",			Width: 45, Align:"Center",	Edit:0, Sort:0, FontBold:1, FontUnderline:1, Cursor:"Pointer"}
				, {Header:"전시채널", 			Type:"Text",		SaveName:"chnnlNames",			Width: 80, Align:"Center", 	Edit:0, Sort:0}
				, {Header:"대표담당자명", 		Type:"Text",		SaveName:"vndrMngrName",		Width: 30, Align:"Center", 	Edit:0, Sort:0}
		];


		// Grid 초기화
		IBS_InitSheet(vendorInfoPopSheet, initvendorInfoPopSheet);
		// Grid 건수 정보 표시
		vendorInfoPopSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
		vendorInfoPopSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		vendorInfoPopSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		vendorInfoPopSheet.SetExtendLastCol(1);

	}

	/**
	 * 관리자 목록 Action관리
	 */
	_vendorInfoPop.doActionvendorInfoPop = function(sAction){
		switch(sAction) {
			// 조회
			case "search" :
				var pageCount = $('#pageCount').val();

				var param = { url : "/noacl/vendor-read-pop-list"
					, onePageRow : pageCount
					, subparam : FormQueryStringEnc(document.vendorForm)
					, sheet : "vendorInfoPopSheet" };

				DataSearchPaging(param);

				break;
		}
	}

	// 화면 이벤트
	_vendorInfoPop.event = function(){

		$('[name=vndrName]').val(_vendorInfoPop.parameter.getParameter("vndrName") != null ? decodeURI(_vendorInfoPop.parameter.getParameter("vndrName")) : "");

		// 초기화
		$("#vendorFormReset").on('click', function(){
			$('#vendorForm')[0].reset();
		});

		// 검색 버튼
		$("#vendorInfoSerch").on('click', function() {
			abc.biz.vendor.pop.doActionvendorInfoPop("search");
		});

		$("#pageCount").on('change', function() {
			abc.biz.vendor.pop.doActionvendorInfoPop("search");
		});

		$("#vendorSelectBtn").on('click', function() {

			var isMultiple = _vendorInfoPop.parameter.isMultiple();
			var checkCnt = vendorInfoPopSheet.CheckedRows("chkData");
			if (checkCnt == 0) {
				alert('업체를 선택하세요');
				return;
			}

			if (!isMultiple ) {
				if (checkCnt > 1) {
					alert('1개 이상 선택할 수 없습니다.');
					return;
				}
			}

			var arrayVndrNo = [];
			var arrayVndrName = [];
			for(var i=1; i <= vendorInfoPopSheet.RowCount(); i++){
				if(vendorInfoPopSheet.GetCellValue(i, "chkData") == "1"){
					arrayVndrNo.push(vendorInfoPopSheet.GetCellValue(i, "vndrNo"));
					arrayVndrName.push(vendorInfoPopSheet.GetCellValue(i, "vndrName"));
				}
			}

			var result = {
					arrayVndrNo : arrayVndrNo
				   ,arrayVndrName : arrayVndrName
			}

			// callback 실행
			var callback = _vendorInfoPop.parameter.getCallback();
			if(typeof callback === "function") {
				if(arrayVndrNo.length > 0) {
					callback(result);
					self.close();
				} else {
					alert("선택된 데이터가 없습니다.");
				}
			} else {
				alert("callback 함수를 찾을 수 없습니다.");
			}
		});
	}
	_vendorInfoPop.parameter = {};

	/**
	 * 다중선택여부 값 반환
	 */
	_vendorInfoPop.parameter.isMultiple = function() {
		var multiple = _vendorInfoPop.parameter.getParameter("multiple");
		return multiple === "true" ? true : false;
	}

	/**
	 * 콜백함수 반환
	 */
	_vendorInfoPop.parameter.getCallback = function() {
		var callback = _vendorInfoPop.parameter.getParameter("callback");
		var result = null;

		if(callback) {
			callback = "window.opener." + callback;
			result = new Function("return " + callback)();
		}
		return result;
	}

	/**
	 * QueryString에서 해당 key에 대한 값을 반환합니다.
	 */
	_vendorInfoPop.parameter.getParameter = function(findParamName) {
		var queryString = window.location.search.slice(1);
		var queryArray = queryString.split("&");
		var result = null;

		for(var i=0 ; i<queryArray.length ; i++) {
			var query = queryArray[i].split("=");
			if(query.length === 2) {
				if(query[0] === findParamName) {
					result = query[1];
					break;
				}
			} else {
				console.log("파라미터 key/value 형태가 올바르지 않습니다. " + queryArray[i]);
			}
		}
		return result;
	}
})();