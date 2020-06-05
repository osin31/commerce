(function() {

	var _object = abc.object.createNestedObject(window,"abc.biz.product.product.popup.exhibition");
	
	_object.resource = {
		title : "",
		name : "",
		prdtNo : "",
		sheet : {
			header : {
				exhibitionPage : [
					{ Header:"",				Type:"Status",	SaveName:"status",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",				Type:"Int",		SaveName:"sortSeq",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"사이트번호",		Type:"Text",	SaveName:"siteNo",			Width: 0,	Align:"Center",	Edit:0, Cursor:"Pointer", Hidden:1 },
					{ Header:"사이트구분",		Type:"Text",	SaveName:"siteName",		Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"채널번호",			Type:"Text",	SaveName:"chnnlNo",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"전시채널",			Type:"Text",	SaveName:"chnnlName",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"디바아스",			Type:"Text",	SaveName:"deviceType",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"전시페이지번호",		Type:"Text",	SaveName:"dispPageNo",		Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"전시페이지명",		Type:"Text",	SaveName:"dispPageName",	Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
//					{ Header:"전시경로(URL)",		Type:"Text",	SaveName:"url",				Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"전시상태",			Type:"Combo",	SaveName:"dispYn",			Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "전시|미전시",	ComboCode : "Y|N" },
					{ Header:"전시시작일",		Type:"Date",	SaveName:"dispStartDtm",			Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer" , Format:abc.consts.DEFAULT_DATETIME_PATTERN}
				],
				exhibitionPlanning : [
					{ Header:"",				Type:"Status",	SaveName:"status",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"번호",				Type:"Int",		SaveName:"sortSeq",			Width: 5,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"사이트번호",		Type:"Text",	SaveName:"siteNo",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"사이트구분",		Type:"Text",	SaveName:"siteName",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"채널번호",			Type:"Text",	SaveName:"chnnlNo",			Width: 0,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"전시채널",			Type:"Text",	SaveName:"chnnlName",		Width: 10,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"기획전 ID",			Type:"Text",	SaveName:"plndpNo",			Width: 10,	Align:"Center",	Edit:0, Cursor:"Pointer" },
					{ Header:"기획전명",			Type:"Text",	SaveName:"plndpName",		Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"기획전주소(PC)",	Type:"Text",	SaveName:"pcPlndpUrl",		Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"기획전주소(MC)",	Type:"Text",	SaveName:"mobilePlndpUrl",	Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
					{ Header:"기획전 시작일시",	Type:"Text",	SaveName:"plndpStartDtm",	Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
					{ Header:"기획전 종료일시",	Type:"Text",	SaveName:"plndpEndDtm",		Width: 20,	Align:"Center",	Edit:0,	Cursor:"Pointer" }
				]
			}
		}
	}
	
	/**
	 * 초기화
	 */
	_object.init = function() {
		console.log('init');
		
		_object.resource.title = abc.biz.product.utils.parameter.getDecodedParameter("title");
		_object.resource.name = abc.biz.product.utils.parameter.getDecodedParameter("name");
		_object.resource.prdtNo = abc.biz.product.utils.parameter.getDecodedParameter("prdtNo");
		$("#title").text(_object.resource.title);	// 타이틀 설정
		$("#name").text(_object.resource.name);	// 이름 설정
		
		_object.sheet.init();
		_object.sheet.event();
		_object.event();
	}
	
	_object.sheet = {};
	_object.sheet.init = function() {
		abc.biz.product.utils.grid.create(_object.sheet, "exhibition-page", "exhibitionPage", "100%", "300px", $('#page-count').val(), _object.resource.sheet.header.exhibitionPage);
		abc.biz.product.utils.grid.create(_object.sheet, "exhibition-planning", "exhibitionPlanning", "100%", "300px", $('#page-count').val(), _object.resource.sheet.header.exhibitionPlanning);
		
		_object.getList("exhibitionPage");
		_object.getList("exhibitionPlanning");
	}
	
	/**
	 * IBSheet 이벤트
	 */
	_object.sheet.event = function(){
	}
	
	/**
	 * 이벤트 바인딩
	 */
	_object.event = function() {
		// 페이지별 갯수 변경 이벤트
//		$("#page-count").change(function() {
//			_object.getList(_object.resource.type);
//		});
		
		// 확인버튼
		$("[data-a]").click(function(e) {
			var type = $(this).data("a");
			switch(type) {
			case "close":
				// 확인
				window.self.close();
				break;
			default:
				console.log("알 수 없는 유형입니다. " + type);
			}
		});
	}
	
	/**
	 * 목록 조회
	 */
	_object.getList = function(type) {
		var param = null;
		
		switch(type) {
		case "exhibitionPage" :
			// 상품전시페이지 정보
			param = {
				url : "/product/product/exhibition/page",
//				onePageRow : $("#page-count").val(),
				onePageRow : "50",
				subparam : "prdtNo=" + _object.resource.prdtNo,
				sheet : "exhibitionPage"
			};
			break;
		case "exhibitionPlanning" :
			// 기획전 전시정보
			param = {
				url : "/product/product/exhibition/planning",
//				onePageRow : $("#page-count").val(),
				onePageRow : "50",
				subparam : "prdtNo=" + _object.resource.prdtNo,
				sheet : "exhibitionPlanning"
		};
			break;
		default :
			console.log("지원하지 않는 유형입니다. " + type);
			return;
		}
		DataSearchPaging(param);
	}
	
	$(function() {
		_object.init();
	});
	
})();