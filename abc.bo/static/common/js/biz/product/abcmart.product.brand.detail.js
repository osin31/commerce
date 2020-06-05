(function() {

	var _detail = abc.object.createNestedObject(window,"abc.biz.product.brand.detail");

	var initSheetBrandStyle = {};		// 브랜드 스타일 IBSheet
	var initSheetDetailVisual = {};		// 브랜드 상품상세 IBSheet
	var initSheetMallBrandPagePcALL = {};	// 브랜드 페이지(통합몰) PC IBSheet
	var initSheetMallBrandPagePc10001 = {};	// 브랜드 페이지(abcmart) PC IBSheet
	var initSheetMallBrandPagePc10002 = {};	// 브랜드 페이지(GS) PC IBSheet
	var initSheetMallBrandPagePc10004 = {};	// 브랜드 페이지(Kids) PC IBSheet
	var initSheetMallBrandPageMoALL = {};	// 브랜드 페이지(통합몰) MO IBSheet
	var initSheetMallBrandPageMo10001 = {};	// 브랜드 페이지(abcmart) PC IBSheet
	var initSheetMallBrandPageMo10002 = {};	// 브랜드 페이지(GS) PC IBSheet
	var initSheetMallBrandPageMo10004 = {};	// 브랜드 페이지(Kids) PC IBSheet
	var initSheetOtsBrandPagePc = {};	// 브랜드 페이지(OTS) PC IBSheet
	var initSheetOtsBrandPageMo = {};	// 브랜드 페이지(OTS) MO IBSheet

	var sheetSortSeq = "";
	var evtSheet = "";
	var eventFlag = "";

	/**
	 * 초기화
	 */
	_detail.init = function(){

		_detail.sheet.init();
		_detail.sheet.event();
		_detail.event();

		_detail.initView();
		_detail.initData();

	}

	_detail.sheet = {};
	_detail.sheet.init = function(){
		// ====================================================================================================
		// 브랜드 스타일

		initSheetBrandStyle.Cfg = {Sort:1, ColMove:0, ColResize:1};
		initSheetBrandStyle.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetBrandStyle.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드스타일번호",	Type:"Text",		SaveName:"brandStyleSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드 스타일명",	Type:"Text",		SaveName:"brandStyleName",	MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 120,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"등록상품수",		Type:"Int",			SaveName:"productCount",	MinWidth: 100,	Align:"Center",	Edit:0, Cursor:"Pointer", DefaultValue:"0" },
			{ Header:"대상상품 관리",	Type:"Button",		SaveName:"manageProduct",	MinWidth: 100,	Align:"Center",	Edit:1, Cursor:"Pointer", DefaultValue:"대상상품 관리" }
		];

		createIBSheet2(document.getElementById("brand-style-list"), "brandStyleList", "100%", "500px");
		IBS_InitSheet(brandStyleList , initSheetBrandStyle);

		brandStyleList.SetCountPosition(3);
		brandStyleList.SetPagingPosition(2);
		brandStyleList.FitColWidth();
		brandStyleList.SetExtendLastCol(1);

		_detail.sheet.brandStyleList = brandStyleList;


		// ====================================================================================================
		// 상품상세 비주얼
		initSheetDetailVisual.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetDetailVisual.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetDetailVisual.Cols = [
			{ Header:"",				Type:"Status",			SaveName:"status",				Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",				Type:"CheckBox",		SaveName:"checked",				MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",			Type:"Text",			SaveName:"brandNo",				Width: 10,		Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드배너번호",		Type:"Int",				SaveName:"brandBannerSeq",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
			{ Header:"이미지명" ,			Type:"Text",			SaveName:"imageName",			Width: 30,		Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"이미지경로" ,			Type:"Text",			SaveName:"imagePathText",		Width: 30,		Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"이미지URL" ,		Type:"Text",			SaveName:"imageUrl",			Width: 30,		Align:"Center",	Edit:0,	Cursor:"Pointer", Hidden:1 },
			{ Header:"이미지" ,			Type:"Image",			SaveName:"imagePreview",		MinWidth: 350,	ImgWidth : 350, ImgHeight : 100,	Align:"Center",	Edit:0,	Cursor:"Pointer" },
			{ Header:"파일업로드" ,			Type:"Html",			SaveName:"imageView",			MinWidth: 80,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"제목" ,				Type:"Text",			SaveName:"titleText",			MinWidth: 250,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",			Type:"Combo",			SaveName:"dispYn",				MinWidth: 120,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",		ComboCode : "Y|N" },
			{ Header:"상품수",			Type:"Int",				SaveName:"productCount",		MinWidth: 100,	Align:"Center",	Edit:0, Cursor:"Pointer" },
			{ Header:"대상상품 관리",		Type:"Button",			SaveName:"manageProduct",		MinWidth: 100,	Align:"Center",	Edit:1, Cursor:"Pointer" }
		];

		createIBSheet2(document.getElementById("detail-visual-list"), "detailVisualList", "100%", "425px");
		IBS_InitSheet(detailVisualList , initSheetDetailVisual);

		detailVisualList.SetCountPosition(3);
		detailVisualList.SetPagingPosition(2);
		detailVisualList.FitColWidth();
		detailVisualList.SetExtendLastCol(1);

		_detail.sheet.detailVisualList = detailVisualList;


		// ====================================================================================================
		// 브랜드 페이지 상단 비주얼 PC (통합몰)
		createIBSheet2(document.getElementById("mall-brand-page-visual-pc-ALL-list"), "mallBrandPageVisualPcALLList", "100%", "300px");

		initSheetMallBrandPagePcALL.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPagePcALL.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPagePcALL.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10000", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer"},
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualPcALLList , initSheetMallBrandPagePcALL);

		mallBrandPageVisualPcALLList.SetCountPosition(3);
		mallBrandPageVisualPcALLList.SetPagingPosition(2);
		mallBrandPageVisualPcALLList.FitColWidth();
		mallBrandPageVisualPcALLList.SetExtendLastCol(1);

		_detail.sheet.mallBrandPagePcALL = mallBrandPageVisualPcALLList;

		// 브랜드 페이지 상단 비주얼 PC (abcmart)
		createIBSheet2(document.getElementById("mall-brand-page-visual-pc-10001-list"), "mallBrandPageVisualPc10001List", "100%", "300px");

		initSheetMallBrandPagePc10001.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPagePc10001.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPagePc10001.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10000", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer"},
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualPc10001List , initSheetMallBrandPagePc10001);

		mallBrandPageVisualPc10001List.SetCountPosition(3);
		mallBrandPageVisualPc10001List.SetPagingPosition(2);
		mallBrandPageVisualPc10001List.FitColWidth();
		mallBrandPageVisualPc10001List.SetExtendLastCol(1);

		_detail.sheet.mallBrandPagePc10001 = mallBrandPageVisualPc10001List;


		// 브랜드 페이지 상단 비주얼 PC (gs)
		createIBSheet2(document.getElementById("mall-brand-page-visual-pc-10002-list"), "mallBrandPageVisualPc10002List", "100%", "300px");

		initSheetMallBrandPagePc10002.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPagePc10002.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPagePc10002.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10000", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer"},
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualPc10002List , initSheetMallBrandPagePc10002);

		mallBrandPageVisualPc10002List.SetCountPosition(3);
		mallBrandPageVisualPc10002List.SetPagingPosition(2);
		mallBrandPageVisualPc10002List.FitColWidth();
		mallBrandPageVisualPc10002List.SetExtendLastCol(1);

		_detail.sheet.mallBrandPagePc10002 = mallBrandPageVisualPc10002List;

		// 브랜드 페이지 상단 비주얼 PC (kids)
		createIBSheet2(document.getElementById("mall-brand-page-visual-pc-10004-list"), "mallBrandPageVisualPc10004List", "100%", "300px");

		initSheetMallBrandPagePc10004.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPagePc10004.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPagePc10004.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10000", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer"},
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualPc10004List , initSheetMallBrandPagePc10004);

		mallBrandPageVisualPc10004List.SetCountPosition(3);
		mallBrandPageVisualPc10004List.SetPagingPosition(2);
		mallBrandPageVisualPc10004List.FitColWidth();
		mallBrandPageVisualPc10004List.SetExtendLastCol(1);

		_detail.sheet.mallBrandPagePc10004 = mallBrandPageVisualPc10004List;


		// ====================================================================================================
		// 브랜드 페이지 상단 비주얼 MOBILE (통합몰)
		createIBSheet2(document.getElementById("mall-brand-page-visual-mo-ALL-list"), "mallBrandPageVisualMoALLList", "100%", "300px");

		initSheetMallBrandPageMoALL.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPageMoALL.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPageMoALL.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10001", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualMoALLList , initSheetMallBrandPageMoALL);

		mallBrandPageVisualMoALLList.SetCountPosition(3);
		mallBrandPageVisualMoALLList.SetPagingPosition(2);
		mallBrandPageVisualMoALLList.FitColWidth();
		mallBrandPageVisualMoALLList.SetExtendLastCol(1);

		_detail.sheet.mallBrandPageMoALL = mallBrandPageVisualMoALLList;

		// 브랜드 페이지 상단 비주얼 MOBILE (abc-mart)
		createIBSheet2(document.getElementById("mall-brand-page-visual-mo-10001-list"), "mallBrandPageVisualMo10001List", "100%", "300px");

		initSheetMallBrandPageMo10001.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPageMo10001.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPageMo10001.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10001", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualMo10001List , initSheetMallBrandPageMo10001);

		mallBrandPageVisualMo10001List.SetCountPosition(3);
		mallBrandPageVisualMo10001List.SetPagingPosition(2);
		mallBrandPageVisualMo10001List.FitColWidth();
		mallBrandPageVisualMo10001List.SetExtendLastCol(1);

		_detail.sheet.mallBrandPageMo10001 = mallBrandPageVisualMo10001List;

		// 브랜드 페이지 상단 비주얼 MOBILE (gs)
		createIBSheet2(document.getElementById("mall-brand-page-visual-mo-10002-list"), "mallBrandPageVisualMo10002List", "100%", "300px");

		initSheetMallBrandPageMo10002.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPageMo10002.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPageMo10002.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10001", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualMo10002List , initSheetMallBrandPageMo10002);

		mallBrandPageVisualMo10002List.SetCountPosition(3);
		mallBrandPageVisualMo10002List.SetPagingPosition(2);
		mallBrandPageVisualMo10002List.FitColWidth();
		mallBrandPageVisualMo10002List.SetExtendLastCol(1);

		_detail.sheet.mallBrandPageMo10002 = mallBrandPageVisualMo10002List;

		// 브랜드 페이지 상단 비주얼 MOBILE (kids)
		createIBSheet2(document.getElementById("mall-brand-page-visual-mo-10004-list"), "mallBrandPageVisualMo10004List", "100%", "300px");

		initSheetMallBrandPageMo10004.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetMallBrandPageMo10004.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetMallBrandPageMo10004.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10001", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(mallBrandPageVisualMo10004List , initSheetMallBrandPageMo10004);

		mallBrandPageVisualMo10004List.SetCountPosition(3);
		mallBrandPageVisualMo10004List.SetPagingPosition(2);
		mallBrandPageVisualMo10004List.FitColWidth();
		mallBrandPageVisualMo10004List.SetExtendLastCol(1);

		_detail.sheet.mallBrandPageMo10004 = mallBrandPageVisualMo10004List;

		// ====================================================================================================
		// 브랜드 페이지 상단 비주얼 PC (OTS)
		createIBSheet2(document.getElementById("ots-brand-page-visual-pc-list"), "otsBrandPageVisualPcList", "100%", "400px");

		initSheetOtsBrandPagePc.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetOtsBrandPagePc.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetOtsBrandPagePc.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10000", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(otsBrandPageVisualPcList , initSheetOtsBrandPagePc);

		otsBrandPageVisualPcList.SetCountPosition(3);
		otsBrandPageVisualPcList.SetPagingPosition(2);
		otsBrandPageVisualPcList.FitColWidth();
		otsBrandPageVisualPcList.SetExtendLastCol(1);

		_detail.sheet.otsBrandPagePc = otsBrandPageVisualPcList;

		// ====================================================================================================
		// 브랜드 페이지 상단 비주얼 MOBILE (OTS)
		createIBSheet2(document.getElementById("ots-brand-page-visual-mo-list"), "otsBrandPageVisualMoList", "100%", "400px");

		var initSheetOtsBrandPageMo = {};

		initSheetOtsBrandPageMo.Cfg = {"AutoFitColWidth": "resize", "DeferredVScroll":1};
		initSheetOtsBrandPageMo.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		initSheetOtsBrandPageMo.Cols = [
			{ Header:"",			Type:"Status",		SaveName:"status",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"",			Type:"CheckBox",	SaveName:"checked",			MinWidth: 50,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"브랜드번호",		Type:"Text",		SaveName:"brandNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"브랜드페이지번호",	Type:"Text",		SaveName:"brandPageSeq",	Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"전시구분",		Type:"Text",		SaveName:"dispGbnType",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"T", Hidden:1 },
			{ Header:"디바이스번호",	Type:"Text",		SaveName:"deviceCode",		Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", DefaultValue:"10001", Hidden:1 },
			{ Header:"사이트번호",		Type:"Text",		SaveName:"siteNo",			Width: 10,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"채널번호",		Type:"Text",		SaveName:"chnnlNo",			Width: 70,		Align:"Center",	Edit:1,	Cursor:"Pointer", Hidden:1 },
			{ Header:"노출순서",		Type:"Int",			SaveName:"sortSeq",			MinWidth: 60,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"구분",			Type:"Combo",		SaveName:"fileType",		MinWidth: 80,	Align:"Center",	Edit:0,	Cursor:"Pointer", ComboText : "이미지|동영상",	ComboCode : "I|M" },
			{ Header:"제목",			Type:"Text",		SaveName:"titleText",		MinWidth: 150,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시 이미지",		Type:"Image",		SaveName:"imageView",		MinWidth: 150,	ImgWidth : 100, ImgHeight : 100, DefaultValue:"/static/images/common/no_image.png",	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"연결 URL정보",	Type:"Text",		SaveName:"imageUrl",		MinWidth: 400,	Align:"Center",	Edit:1, Cursor:"Pointer" },
			{ Header:"전시여부",		Type:"Combo",		SaveName:"dispYn",			MinWidth: 100,	Align:"Center",	Edit:1,	Cursor:"Pointer", ComboText : "전시|전시안함",	ComboCode : "Y|N" },
			{ Header:"전시시작일",		Type:"Date",		SaveName:"dispStartYmd",	MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" },
			{ Header:"전시종료일",		Type:"Date",		SaveName:"dispEndYmd",		MinWidth: 120,	Align:"Center",	Edit:1, Cursor:"Pointer", Format:"Ymd" }
		];

		IBS_InitSheet(otsBrandPageVisualMoList , initSheetOtsBrandPageMo);

		otsBrandPageVisualMoList.SetCountPosition(3);
		otsBrandPageVisualMoList.SetPagingPosition(2);
		otsBrandPageVisualMoList.FitColWidth();
		otsBrandPageVisualMoList.SetExtendLastCol(1);

		_detail.sheet.otsBrandPageMo = otsBrandPageVisualMoList;

	}

	/**
	 * IBSheet 이벤트
	 */
	_detail.sheet.event = function(){

		// 스타일 대상상품 관리 클릭
		brandStyleList_OnClick = function(row, col, value, cellx, celly, cellw, cellh) {
			var checkStyleSeq = brandStyleList.GetCellValue(row,"brandStyleSeq");
			var checkSortSeq = brandStyleList.GetCellValue(row,"sortSeq");

			//대상상품 관리
			if(col == brandStyleList.SaveNameCol('manageProduct')) {
				if(checkSortSeq == "") {
					alert("번호 등록 후 대상상품 관리가 가능합니다.");
				} else {
					var sseq = brandStyleList.GetCellValue(row,"sortSeq");
					var vno = brandStyleList.GetCellValue(row,"brandNo");
					var vsseq = brandStyleList.GetCellValue(row,"brandStyleSeq");
					var vbname = $("input[name=brandName]").val();
					evtSheet = "styleSheet";

					$("#sBrandNo").val(vno);
					$("#sBrandStyleSeq").val(vsseq);
					$("#sBrandName").val(vbname);

					//var callback = "abc.biz.product.brand.detail.callbackSheet";
					//abc.productSearchPopup(true, callback);

					var data = brandStyleList.GetRowData(row);

					var option = {
						    name : '스타일대상상품',    		// 상품관리명
						    count : data.productCount,		// 대상상품전체갯수
						    searchFormId : 'styleForm',		// 조회 조건 FORM ID
						    searchUrl : '/product/brand/style/product/list',    // 조회 URI. 공통유틸에서 POST를 이용하여 통신함
						    saveAdditionalParamFormId : 'styleForm',    // 저장 시 전달 할 파라미터가 설정된 FORM ID
						    saveUrl : '/product/brand/style/sheet/product/save',    // 저장 URI. API에서 POST를 이용하여 통신함
						    returnFn : 'abc.biz.product.brand.detail.doAction',		// 완료시 부모창에 호출되는 메소드
						    returnFnVal : 'readBrandStyleList',						// 호출되는 메소드의 값
						    isHideSortSeq : false    // 노출순서 숨김 여부
					};
					abc.targetProductManagementPopup(option);    // 대상상품관리 팝업창 호출
				}
			}
		}

		// 비주얼 대상상품 관리 클릭
		detailVisualList_OnClick = function(row, col, value, cellx, celly, cellw, cellh) {
			var checkBannerSeq = detailVisualList.GetCellValue(row,"brandBannerSeq");
			var checkStatus = detailVisualList.GetCellValue(row,"status");

			//대상상품 관리
			if(col == detailVisualList.SaveNameCol('manageProduct')) {

				if(detailVisualList.GetCellEditable(row, col)) {
					// 비활성화가 아닌 경우에만 동작

					if(checkBannerSeq == "") {
						alert("상품상세 저장 후 대상상품 관리가 가능합니다.");
					} else {
						if(checkStatus == "I") {
							alert("저장 후 대상상품 관리가 가능합니다.");
						} else {
							if(detailVisualList.GetCellValue(row,"imagePreview") === "") {
								alert("이미지를 등록하셔야 대상상품 관리가 가능합니다.");
							} else if(detailVisualList.GetCellValue(row,"titleText") === "") {
								alert("제목을 입력하셔야 대상상품 관리가 가능합니다.");
							} else {
								var vno = detailVisualList.GetCellValue(row,"brandNo");
								var vbbs = detailVisualList.GetCellValue(row,"brandBannerSeq");
								var vbname = $("input[name=brandName]").val();
								evtSheet = "styleSheet";

								$("#vBrandNo").val(vno);
								$("#vBrandBannerSeq").val(vbbs);
								$("#vBrandName").val(vbname);

								evtSheet = "visualSheet";

								var data = detailVisualList.GetRowData(row);

								var option = {
										name : '브랜드 상품상세',    		// 상품관리명
										count : data.productCount,		// 대상상품전체갯수
										searchFormId : 'visualForm',		// 조회 조건 FORM ID
										searchUrl : '/product/brand/visual/product/list',    // 조회 URI. 공통유틸에서 POST를 이용하여 통신함
										saveAdditionalParamFormId : 'visualForm',    // 저장 시 전달 할 파라미터가 설정된 FORM ID
										saveUrl : '/product/brand/visual/sheet/product/save',   // 저장 URI. API에서 POST를 이용하여 통신함
										returnFn : 'abc.biz.product.brand.detail.doAction',		// 완료시 부모창에 호출되는 메소드
										returnFnVal : 'readDetailVisualList',					// 호출되는 메소드의 값
										isHideSortSeq : false    // 노출순서 숨김 여부
								};
								abc.targetProductManagementPopup(option);    // 대상상품관리 팝업창 호출
							}
						}
					}

				}
			}
		}

		// 상세 비주얼
		detailVisualList_OnRowSearchEnd = function(row) {
			var imageUrl = detailVisualList.GetCellValue(row,"imageUrl");
			var htmlImageView = $($("#file-upload-tmpl").html().trim());

			htmlImageView.find("form").attr("id", "form-brand-upload"+row);
			htmlImageView.find("input").attr("id", "fileUpload"+row);

			detailVisualList.SetCellValue(row, "imageView", htmlImageView.html());
		}

	}

	_detail.event = function() {

		new abc.biz.product.utils.processImage({
			file: '#file-brand-image-color',
			name: '#brdPathText',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#pc-upload-file-ALL',
			name: '#pc-altrn-text-ALL',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#pc-upload-file-10001',
			name: '#pc-altrn-text-10001',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#pc-upload-file-10002',
			name: '#pc-altrn-text-10002',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#pc-upload-file-10004',
			name: '#pc-altrn-text-10004',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#mo-upload-file-ALL',
			name: '#mo-altrn-text-ALL',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#mo-upload-file-10001',
			name: '#mo-altrn-text-10001',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#mo-upload-file-10002',
			name: '#mo-altrn-text-10002',
			allow : 'jpg, jpeg, png, gif, bmp'
		});

		new abc.biz.product.utils.processImage({
			file: '#mo-upload-file-10004',
			name: '#mo-altrn-text-10004',
			allow : 'jpg, jpeg, png, gif, bmp'
		});


		// 탭변경시 조건체크 이벤트
		$("#brandInfoTab").on("tabsbeforeactivate", function(event, ui) {
			if($("#brandInfoStatus").val() != "U"){
				alert("브랜드 기본정보를 저장해 주세요");
				event.preventDefault();
			}

			var siteNo = $("input[name=siteNo]:checked").val();
			if(ui.newPanel.attr('id') === 'tabBrandStlye') { // 브랜드 스타일
				//brandStyleList.FitColWidth();
			} else if (ui.newPanel.attr('id') === 'tabBrandProductDetail') { // 브랜드 상품상세
				if (siteNo == "10001") {
					alert('사이트 구분이 OST일 때는 등록할 수 없습니다.');
					event.preventDefault();
				}
				//mallBrandPageVisualPcList.FitColWidth();
				//mallBrandPageVisualMoList.FitColWidth();
			} else if(ui.newPanel.attr('id') === 'tabBrandPageArt') { // 브랜드 페이지(통합몰)
				//detailVisualList.FitColWidth();
			} else if(ui.newPanel.attr('id') === 'tabBrandPageOts') { // 브랜드 페이지(OTS)
				if (siteNo == "10000") {
					alert('사이트 구분이 통합몰일 때는 등록할 수 없습니다.');
					event.preventDefault();
				}
				//otsBrandPageVisualPcList.FitColWidth();
				//otsBrandPageVisualMoList.FitColWidth();
			}
		});

		// 기본이동채널
		$("input[name=siteNo]").change(function(){
			if($(this).val() == '10001'){
				$("input[name=dfltMvmnChnnl]").each(function(index, item){
					$(this).prop('checked', false);
				});
			}else{
				var orgDfltMvmnChnnl = $("#orgDfltMvmnChnnl").val();
				$("input[name='dfltMvmnChnnl'][value="+ orgDfltMvmnChnnl +"]").prop('checked', true);
			}
		});

		$("input[name=dfltMvmnChnnl]").click(function(){
			var siteNo = $("input:radio[name=siteNo]:checked").val();

			if(siteNo == '10001'){
				$(this).prop('checked', false);
			}
		});

		/**
		 * 삭제 클릭
		 */
		$("[data-delete-item=delete-checked]").on('click', function(e) {
			var listType = $(this).data('type');
			var list = '';
			var deleteList = '';
			var seq = '';
			var actionName = '';
			eventFlag = "del";

			if(listType == 'brandStyleList') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/style/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo, "Quest" : 0};
				if(confirm("삭제하시겠습니까?")) {
					brandStyleList.DoSave(url, param);
					_detail.doAction("readBrandStyleList");		// 브랜드 스타일 조회
				}
			} else if(listType == 'detailVisualList') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/visual/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo, "Quest" : 0};
				if(confirm("삭제하시겠습니까?")) {
					detailVisualList.DoSave(url, param);
					_detail.doAction("readDetailVisualList");	// 브랜드 상세 비주얼 조회
				}
			} else if(listType == 'mallBrandPageVisualPcALLList') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualPcALLList.DoSave(url, param);
				_detail.doAction("readMallBrandPagePcALL");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC - all)
			} else if(listType == 'mallBrandPageVisualPc10001List') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualPc10001List.DoSave(url, param);
				_detail.doAction("readMallBrandPagePc10001");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC - abcmart)
			} else if(listType == 'mallBrandPageVisualPc10002List') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualPc10002List.DoSave(url, param);
				_detail.doAction("readMallBrandPagePc10002");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC - GS)
			} else if(listType == 'mallBrandPageVisualPc10004List') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualPc10004List.DoSave(url, param);
				_detail.doAction("readMallBrandPagePc10004");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC - 10004)
			}
			else if(listType == 'mallBrandPageVisualMoALLList') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualMoALLList.DoSave(url, param);
				_detail.doAction("readMallBrandPageMoALL");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE - all)
			}
			else if(listType == 'mallBrandPageVisualMo10001List') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualMo10001List.DoSave(url, param);
				_detail.doAction("readMallBrandPageMo10001");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE - abcmart)
			}
			else if(listType == 'mallBrandPageVisualMo10002List') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualMo10002List.DoSave(url, param);
				_detail.doAction("readMallBrandPageMo10002");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE - gs)
			}
			else if(listType == 'mallBrandPageVisualMo10004List') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				mallBrandPageVisualMo10004List.DoSave(url, param);
				_detail.doAction("readMallBrandPageMo10004");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE - kids)
			} else if(listType == 'otsBrandPageVisualPcList') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				otsBrandPageVisualPcList.DoSave(url, param);
				_detail.doAction("readOtsBrandPagePc");		// OTS 브랜드 페이지 상단 비주얼 전시정보(PC)
			} else if(listType == 'otsBrandPageVisualMoList') {
				var brandNo = $("#brand-no").val();
				var url = "/product/brand/page/delete";
				var param = { "Param" : "paramBrandNo=" + brandNo };
				otsBrandPageVisualMoList.DoSave(url, param);
				_detail.doAction("readOtsBrandPageMo");		// OTS 브랜드 페이지 상단 비주얼 전시정보(MOBILE)
			}

			eventFlag = "";
		});

		// 브랜드 스타일 순서저장 이벤트
		$("[data-brand-style-item=apply-sort-seq]").click(function(e) {
			_detail.doAction("saveAsyncBrandStyleList");
			_detail.doAction("readBrandStyleList");
		});

		// 브랜드 페이지 순서변경 이벤트
		$("[data-brand-page-item=apply-page-seq]").click(function(e) {
			var siteNo = $(this).attr("data-site");
			var deviceCode = $(this).attr("data-device");
			var scopeChnnlNo = $(this).attr('data-chnnl');

			if(siteNo == "10000" && deviceCode == "10000"){
				_detail.doAction("saveAsyncBrandPageMp"+scopeChnnlNo+"List");
				_detail.doAction("readMallBrandPagePc"+scopeChnnlNo);	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC)
			}else if(siteNo == "10000" && deviceCode == "10001"){
				_detail.doAction("saveAsyncBrandPageMm"+scopeChnnlNo+"List");
				_detail.doAction("readMallBrandPageMo"+scopeChnnlNo);	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE)
			}else if(siteNo == "10001" && deviceCode == "10000"){
				_detail.doAction("saveAsyncBrandPageOpList");
				_detail.doAction("readOtsBrandPagePc");		// OTS 브랜드 페이지 상단 비주얼 전시정보(PC)
			}else if(siteNo == "10001" && deviceCode == "10001"){
				_detail.doAction("saveAsyncBrandPageOmList");
				_detail.doAction("readOtsBrandPageMo");		// OTS 브랜드 페이지 상단 비주얼 전시정보(MOBILE)
			}
		});

		// 브랜드 스타일 행추가 이벤트
		$("[data-related-sheet]").click(function(e) {
			e.preventDefault();

			var sheetName = $(this).attr("data-related-sheet"); // 행 추가 대상 시트 이름 획득
			var sheet = eval(sheetName);	// 시트 객체 획득

			var insertRowNumber = sheet.RowCount() + 1;
			var initialRowData = {};	// 추가될 행 데이터
			var rowOption = { Add : 1 };	// 행추가 옵션 정보. 신규 행이 생성되도록 설정

			var params = {
				  contTypeCode:''
				, contTypeCodeName:''
				, brandNo:$("#brand-no").val()
				, sheetName:sheetName
			};

			var disabledRowName = null;

			if(sheetName == "brandStyleList") {	// 브랜드 스타일 행추가

				$.ajax({
					type :"post",
					url : "/product/brand/style/addRow",
					dataType : "json",
					data : params
				}).done(function(data){
					console.log("data > " + JSON.stringify(data));
					_detail.doAction("readBrandStyleList");		// 브랜드 스타일 조회
				}).fail(function(e){
					alert("행 추가에 실패하였습니다.");
				});

			} else if(sheetName == "detailVisualList") {	// 브랜드 상품상세 추가
				var tmpl = "";
				tmpl += "<div>";
				tmpl += "	<span class='file-wrap'>";
				tmpl += "		<span class='btn-box'>";
				tmpl += "		<form id='form-brand-upload"+insertRowNumber+"'>";
				tmpl += "			<input type='file' id='fileUpload"+insertRowNumber+"' name='fileUpload' title='첨부파일 추가' data-to='upload'/>";
				tmpl += "			<label for='fileUpload"+insertRowNumber+"'>찾아보기</label>";
				tmpl += "		</form>";
				tmpl += "		</span>";
				tmpl += "	</span>";
				tmpl += "</div>";

				initialRowData = {
					"brandNo"			: $("#brand-no").val() == "" ? "" : $("#brand-no").val(),
					"brandBannerSeq"	: insertRowNumber == 0 ? 1 : insertRowNumber,	// 브랜드번호
					"dispYn"			: "Y",	// 상품관리 버튼
					"productCount"		: "0",	// 상품수
					//"imageView"			: tmpl.html(),
					"imageView"			: $(tmpl).html(),
					"manageProduct"		: "대상 상품관리"
				};
				disabledRowName = "manageProduct";	// 대상상품관리 버튼 비활성화
			} else if(sheetName == "mallBrandPageVisualPcALLList" || sheetName == "mallBrandPageVisualPc10001List"
					|| sheetName == "mallBrandPageVisualPc10002List" || sheetName == "mallBrandPageVisualPc10004List") {	// 브랜드 페이지 비주얼 전시(통합몰) PC
				var setType = $(this).attr("data-set-type"); // 데이터 셋 타입
				var scopeChnnlNo = $(this).attr("data-chnnl");

				params.setType = setType;
				params.deviceCode = "10000";
				params.siteNo = "10000";
				params.chnnlNo = scopeChnnlNo;
				params.contTypeCodeName = setType;
				if(setType == "I"){
					params.contTypeCode = '10002';
					params.height = 715;
					params.winname = "mallbrandpagepcimage"
				} else {
					params.contTypeCode = '10003';
					params.height = 510;
					params.winname = "mallbrandpagepcvideo"
				}

				var options = {
					winname :	params.winname,
					method	: 	"get",
					url 	:	"/product/brand/page/setFile",
					title 	:	params.contTypeCodeName,
					width 	:	855,
					height	:	params.height,
					params	:	params,
				};
				abc.open.popup(options);
			} else if(sheetName == "mallBrandPageVisualMoALLList" || sheetName == "mallBrandPageVisualMo10001List"
				|| sheetName == "mallBrandPageVisualMo10002List" || sheetName == "mallBrandPageVisualMo10004List") {	// 브랜드 페이지 비주얼 전시(통합몰) MO
				var setType = $(this).attr("data-set-type"); // 데이터 셋 타입
				var scopeChnnlNo = $(this).attr("data-chnnl");

				params.setType = setType;
				params.deviceCode = "10001";
				params.siteNo = "10000";
				params.chnnlNo = scopeChnnlNo;
				params.contTypeCodeName = setType;
				if(setType == "I"){
					params.contTypeCode = '10002';
					params.height = 715;
					params.winname = "mallbrandpagemoimage"
				} else {
					params.contTypeCode = '10003';
					params.height = 510;
					params.winname = "mallbrandpagemovideo"
				}

				var options = {
					winname :	params.winname,
					method	: 	"get",
					url 	:	"/product/brand/page/setFile",
					title 	:	params.contTypeCodeName,
					width 	:	855,
					height	:	params.height,
					params	:	params,
				};

				abc.open.popup(options);
			} else if(sheetName == "otsBrandPageVisualPcList") {	// 브랜드 페이지 비주얼 전시(OTS) PC
				var setType = $(this).attr("data-set-type"); // 데이터 셋 타입

				params.setType = setType;
				params.deviceCode = "10000";
				params.siteNo = "10001";
				params.chnnlNo = "10003";
				params.contTypeCodeName = setType;
				if(setType == "I"){
					params.contTypeCode = '10002';
					params.height = 715;
					params.winname = "otsbrandpagepcimage"
				} else {
					params.contTypeCode = '10003';
					params.height = 510;
					params.winname = "otsbrandpagepcvideo"
				}

				var options = {
					winname :	params.winname,
					method	: 	"get",
					url 	:	"/product/brand/page/setFile",
					title 	:	params.contTypeCodeName,
					width 	:	855,
					height	:	params.height,
					params	:	params,
				};

				abc.open.popup(options);
			} else if(sheetName == "otsBrandPageVisualMoList") {	// 브랜드 페이지 비주얼 전시(OTS) Mobile
				var setType = $(this).attr("data-set-type"); // 데이터 셋 타입

				params.setType = setType;
				params.deviceCode = "10001";
				params.siteNo = "10001";
				params.chnnlNo = "10003";
				params.contTypeCodeName = setType;
				if(setType == "I"){
					params.contTypeCode = '10002';
					params.height = 715;
					params.winname = "otsbrandpagemoimage"
				} else {
					params.contTypeCode = '10003';
					params.height = 510;
					params.winname = "otsbrandpagemovideo"
				}

				var options = {
					winname :	params.winname,
					method	: 	"get",
					url 	:	"/product/brand/page/setFile",
					title 	:	params.contTypeCodeName,
					width 	:	855,
					height	:	params.height,
					params	:	params,
				};

				abc.open.popup(options);
			}

			if(Object.keys(initialRowData).length != 0) {
				sheet.SetRowData(insertRowNumber, initialRowData, rowOption);
			}
			if(disabledRowName != null) {
				// 특정 row 비활성화 처리
				sheet.SetCellEditable(insertRowNumber, disabledRowName, false);
			}
		});

		// 브랜드 동영상 정보 토글 이벤트 (URL 입력 또는 직접 업로드)
		$("[data-brand-video-type]").click(function() {
			var type = $(this).attr("data-brand-video-type");
			if(type == "url") {
				// URL 입력인 경우
				$("[data-brand-video-toggle=url]").show();
				$("[data-brand-video-toggle=upload]").hide();
			} else {
				// 직접 업로드인 경우
				$("[data-brand-video-toggle=url]").hide();
				$("[data-brand-video-toggle=upload]").show();
			}
		});

		//============================ 이미지 첨부 시 파일정보 노출 이벤트

		// 대표이미지(color) 이미지 첨부 시 파일정보 노출
		$("[data-brand-image-color=file]").change(function(e) {
			// 파일정보 읽어오기
			if(this.files && this.files[0]){
				var image = new Image();
				var fileReader = new FileReader();
				fileReader.onload = function(e){
					$(image).attr("src", e.target.result);
				}
				fileReader.readAsDataURL(this.files[0]);
				$("[data-brand-image-color=image-area]").html("");	// 기존 노출 이미지 제거
				$("[data-brand-image-color=image-area]").append(image);	// 이미지 노출
				$("[data-brand-image-color=file-name]").html("");	// 첨부파일명 제거
				$("[data-brand-image-color=file-name]").text(this.files[0].name);	// 첨부파일명 출력
				$("[data-brand-image-color=remove]").show();	// 이미지 삭제 링크 보이기
			}
		});

		// 대표이미지(gray) 이미지 첨부 시 파일정보 노출
		$("[data-brand-image-gray=file]").change(function(e) {
			// 파일정보 읽어오기
			if(this.files && this.files[0]){
				var image = new Image();
				var fileReader = new FileReader();
				fileReader.onload = function(e){
					$(image).attr("src", e.target.result);
				}
				fileReader.readAsDataURL(this.files[0]);
				$("[data-brand-image-gray=image-area]").html("");	// 기존 노출 이미지 제거
				$("[data-brand-image-gray=image-area]").append(image);	// 이미지 노출
				$("[data-brand-image-gray=file-name]").html();	// 첨부파일명 제거
				$("[data-brand-image-gray=file-name]").text(this.files[0].name);	// 첨부파일명 출력
				$("[data-brand-image-gray=remove]").show();	// 이미지 삭제 링크 보이기
			}
		});

		// 브랜드 동영상 첨부 시 파일정보 노출
		$("[data-brand-video-item=file]").change(function(e) {
			// 파일정보 읽어오기
			if(this.files && this.files[0]){
				$("[data-brand-video-item=video-file-name]").html();	// 첨부파일명 제거
				$("[data-brand-video-item=video-file-name]").text(this.files[0].name);	// 첨부파일명 출력
				$("[data-brand-video-item=video-remove]").show();	// 이미지 삭제 링크 보이기
			}
		});

		// 브랜드 동영상 정보 미리보기 첨부이미지 변경 이벤트
		$("[data-brand-video-item=image]").change(function(e) {
			// 파일정보 읽어오기
			var _self = this;

			if(_self.files && _self.files[0]){
				var image = new Image();
				var fileReader = new FileReader();
				fileReader.onload = function(e){
					$(image).attr("src", e.target.result);
					$(image).attr("style", "max-width:500px;max-height:500px;");

					// mime type 검사
					var applyMimeType = ".image/jpeg.image/gif.image/png";	// 허용가능한 MIME 형식
					var fileResult = fileReader.result;
					var attachFileMimeType = fileResult.split(';')[0].split(":")[1].toLowerCase();	// 첨부파일 MIME 형식

					if(applyMimeType.indexOf(attachFileMimeType) == -1) {
						console.log("지원하지 않는 MIME 유형. " + attachFileMimeType);
						alert("등록가능한 확장자명은 jpg, gif, png 입니다.");
						$(_self).val("");
						$("[data-brand-video-item=image-file-name]").text("");
						$("[data-brand-video-item=image-area]").html("");
						$("[data-brand-video-item=image-remove]").hide();
						return;
					}

					$("[data-brand-video-item=image-area]").html("");	// 기존 노출 이미지 제거
					$("[data-brand-video-item=image-area]").append(image);	// 이미지 노출
					$("[data-brand-video-item=image-file-name]").html();	// 첨부파일명 제거
					$("[data-brand-video-item=image-file-name]").text(_self.files[0].name);	// 첨부파일명 출력
					$("[data-brand-video-item=image-remove]").show();	// 이미지 삭제 링크 보이기
				}
				fileReader.readAsDataURL(this.files[0]);
			}
		});
		//==============================================

		//============================== 이미지 삭제버튼 시 이벤트
		$("[data-brand-image-color=remove]").click(function(e) {
			// 필드 클리어
			$("[data-brand-image-color=file]").val("");
			$("[data-brand-image-color=image-area]").html("");	// 기존 노출 이미지 제거
			$("[data-brand-image-color=file-name]").html("");	// 첨부파일명 제거
			$("[data-brand-image-color=remove]").hide();	// 이미지 삭제 링크 보이기
		});
		$("[data-brand-image-gray=remove]").click(function(e) {
			// 필드 클리어
			$("[data-brand-image-gray=file]").val("");
			$("[data-brand-image-gray=image-area]").html("");	// 기존 노출 이미지 제거
			$("[data-brand-image-gray=file-name]").html("");	// 첨부파일명 제거
			$("[data-brand-image-gray=remove]").hide();	// 이미지 삭제 링크 보이기
		});
		$("[data-brand-video-item=video-remove]").click(function(e) {
			$("[data-brand-video-item=file]").text("");	// 첨부파일 제거
			$("[data-brand-video-item=video-file-name]").text("");	// 파일명 제거
			$("[data-brand-video-item=video-remove]").hide();	// 삭제아이콘 숨기기
		});
		$("[data-brand-video-item=image-remove]").click(function(e) {
			$("[data-brand-video-item=image]").text("");	// 첨부파일 제거
			$("[data-brand-video-item=image-file-name]").text("");	// 파일명 제거
			$("[data-brand-video-item=image-area]").html("");	// 미리보기 제거
			$("[data-brand-video-item=image-remove]").hide();	// 삭제아이콘 숨기기
		});
		//=================================================

		// 상세 비주얼 내 이미지 첨부 이벤트
		$("#detail-visual-list").on("change", "[data-to=upload]", function(e) {
			console.log("상세비주얼 이미지 변경 이벤트.");
			var selectedIndex = detailVisualList.GetSelectRow(); // return selcted row index
			_detail.fileUpload(
				this,
				"/product/brand/visual/upload/banner",
				function(data) {
					console.log("저장 후 작업내용.......");
					if(data.length == 1) {
						var htmlImageView = $($("#file-upload-tmpl").html().trim());
						//htmlImageView.find("img").attr("src", data[0].fileUrl);
						var updateRowData = {
							imagePreview	: data[0].fileUrl,
							imageName		: data[0].fileName,
							imagePathText	: data[0].filePath,
							imageUrl		: data[0].fileUrl,
							imageView		: htmlImageView.html()
						};
						detailVisualList.SetRowData(selectedIndex, updateRowData); // update row data
						// 아래는 업데이트 내용 확인
						console.log("======= 업데이트 데이터 확인=======");
						console.log(detailVisualList.GetRowData(detailVisualList.GetSelectRow()));
						console.log("===========================");
					} else {
						alert("등록 중 오류가 발생하였습니다. 다시 시도하여 주십시오.");
					}
				},
				function(e){
					console.log("저장 후 작업내용.......");
				}
			);
		});

		// 브랜드 스타일 목록갯수 변경 이벤트
		$("#page-count-brand-style").change(function() {
			_detail.doAction("readBrandStyleList");
		});

		// 브랜드 통합몰 PC all 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-pc-ALL-top").change(function() {
			_detail.doAction("readMallBrandPagePcALL");
		});

		// 브랜드 통합몰 PC abcmart 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-pc-10001-top").change(function() {
			_detail.doAction("readMallBrandPagePc10001");
		});

		// 브랜드 통합몰 PC gs 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-pc-10002-top").change(function() {
			_detail.doAction("readMallBrandPagePc10002");
		});

		// 브랜드 통합몰 PC kids 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-pc-10004-top").change(function() {
			_detail.doAction("readMallBrandPagePc10004");
		});

		// 브랜드 통합몰 MO all 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-mo-ALL-top").change(function() {
			_detail.doAction("readMallBrandPageMoALL");
		});

		// 브랜드 통합몰 MO abcmart 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-mo-10001-top").change(function() {
			_detail.doAction("readMallBrandPageMo10001");
		});

		// 브랜드 통합몰 MO gs 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-mo-10002-top").change(function() {
			_detail.doAction("readMallBrandPageMo10002");
		});

		// 브랜드 통합몰 MO kids 목록갯수 변경 이벤트
		$("#page-count-brand-page-art-mo-10004-top").change(function() {
			_detail.doAction("readMallBrandPageMo10004");
		});

		// 브랜드 OTS PC 목록갯수 변경 이벤트
		$("#page-count-brand-page-ots-pc-top").change(function() {
			_detail.doAction("readOtsBrandPagePc");
		});

		// 브랜드 OTS MO 목록갯수 변경 이벤트
		$("#page-count-brand-page-ots-mo-top").change(function() {
			_detail.doAction("readOtsBrandPageMo");
		});

		//TO_DO 확인 필요
		// 브랜드 페이지(통합몰) 전시여부 이벤트
		$('.disp-yn').off().on('click', function() {
			var prefix = '#' + (this.id.startsWith('pc') ? 'pc' : 'mo') + '-';
			var scopeChnnlNo = $(this).attr('id').split('-').pop();

			if('Y' == this.value) {
				$(prefix + 'brand-page-tbody-' + scopeChnnlNo +' tr:not(:first)').show();
				$('input[name$="CnnctnType"]:checked').click();
				$(this).closest('ul').find('[data-role="datepicker"]').prop('disabled', false);
			} else {
				$(prefix + 'brand-page-tbody-' + scopeChnnlNo +' tr:not(:first)').hide();
				$(this).closest('ul').find('[data-role="datepicker"]').prop('disabled', true).val('');
			}
			$(this).closest('td').find('input[name="dispYn"]').val(this.value);
		});

		// 브랜드 페이지(통합몰) 링크연결 방법 이벤트
		$('input[name$="CnnctnType"]').off().on('click', function(){
			var prefix = '#' + (this.id.startsWith('pc') ? 'pc' : 'mo') + '-';
			var scopeChnnlNo = $(this).attr('id').split('-').pop();

			$(prefix + 'link-info-u-tr-' + scopeChnnlNo).hide();
			$(prefix + 'link-info-m-tr-' + scopeChnnlNo).hide();

			switch (this.value) {
			case "U":
				$(prefix + 'link-info-u-tr-' + scopeChnnlNo).show();
				break;
			case "M":
				$(prefix + 'link-info-m-tr-' + scopeChnnlNo).show();
				break;
			case "N":
				break;
			default:
				break;
			}

			$(this).closest('tbody').find('input[name="cnnctnType"]').val(this.value);
		});

		// 브랜드 저장 이벤트
		$("#save").click(function(e) {
			e.preventDefault();
			
			var brandInfoTab = $("#brandInfoTab").tabs("option", "active");
			
			switch (brandInfoTab) {
			case 0:
				_detail.brandBaseInfoSave();
				break;
			case 1:
				_detail.setStyleList();
				break;
			case 2:
				_detail.setVisualList();
				break;
			case 3:
				_detail.setBrandPageArt();
				break;
			case 4:
				_detail.setBrandPageOts();
				break;
			default:
				break;
			}
		});

		// 브랜드 스타일 저장후 알럿
		brandStyleList_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response) {
			alert(Msg);
			_detail.doAction("readBrandStyleList");		// 브랜드 스타일 조회
		}

		// 브랜드 상품상세 저장후 알럿
		detailVisualList_OnSaveEnd = function(Code, Msg, StCode, StMsg, Response) {
			alert(Msg);
			_detail.doAction("readDetailVisualList");	// 브랜드 상세 비주얼 조회
		}


		// 브랜드 프로모션 배너 정보 전시여부 활성화
		$('.disp-yn:checked').click();
	}

	//기본정보 저장
	_detail.brandBaseInfoSave = function(){
		var type = $("#brandInfoStatus").val() == abc.consts.CRUD_C ? "save" : "modify";
		var urlPostfix = "/" + type;
		var additionalParam = {
				"type" : type
			};

		$.form("#form-brand").submit({
			url : "/product/brand" + urlPostfix,
			type : "POST",
			dataType : "json",
			data : $.paramObject(additionalParam),
			success : function(data) {
				console.log("브랜드 기본정보 저장 성공");
				console.log(data);

				if(data != null && data.resultCd == 'Y') {
					var msg = "브랜드가 수정되었습니다.";
					if($("#brandInfoStatus").val() == abc.consts.CRUD_C){
						msg = "브랜드가 등록되었습니다.";
					}
					alert(msg);
					location.href = "/product/brand/detail?brandNo="+data.brand.brandNo;
				}else if(data != null && data.resultCd == 'N') {
					alert(data.resultMsg);
				}else {
					alert("등록 중 문제가 발생하였습니다.\n다시 시도하여 주십시오.");
				}
			},
			error : function(e) {
				if(e != null && e.message != null && e.message != "") {
					alert(e.message);
				}
				console.log(e);
			}
		});
	}

	// 브랜드 페이지 변경사항 저장(통합몰)
	_detail.setBrandPageArt = function(){
		var brandPrdtArtDispYn = $("input:radio[name=brandPrdtArtDispYn]:checked").val();
		var resultType = false;
		
		if(!confirm("저장하시겠습니까?")){
			return false;
		}
		
		if(brandPrdtArtDispYn == "Y"){
			_detail.beforeSave(mallBrandPageVisualPcALLList, 'saveAsyncBrandPageMpALLList');
			_detail.beforeSave(mallBrandPageVisualMoALLList, 'saveAsyncBrandPageMmALLList');
			resultType = _detail.promotion('ALL');
			if(resultType){
				alert("저장하였습니다.");
			}
		}else{
			_detail.beforeSave(mallBrandPageVisualPc10001List, 'saveAsyncBrandPageMp10001List');
			_detail.beforeSave(mallBrandPageVisualMo10001List, 'saveAsyncBrandPageMm10001List');
			resultType = _detail.promotion('10001');
			if(!resultType){
				return;
			}
			_detail.beforeSave(mallBrandPageVisualPc10002List, 'saveAsyncBrandPageMp10002List');
			_detail.beforeSave(mallBrandPageVisualMo10002List, 'saveAsyncBrandPageMm10002List');
			resultType = _detail.promotion('10002');
			if(!resultType){
				return;
			}
			_detail.beforeSave(mallBrandPageVisualPc10004List, 'saveAsyncBrandPageMp10004List');
			_detail.beforeSave(mallBrandPageVisualMo10004List, 'saveAsyncBrandPageMm10004List');
			resultType = _detail.promotion('10004');
			if(!resultType){
				return;
			}
			alert("저장하였습니다.");
		}


	}

	// 브랜드 페이지 변경사항 저장(ots)
	_detail.setBrandPageOts = function(){
		var cnt = 0;
		cnt +=_detail.beforeSave(otsBrandPageVisualPcList, 'saveAsyncBrandPageOpList');
		cnt += _detail.beforeSave(otsBrandPageVisualMoList, 'saveAsyncBrandPageOmList');
		if(cnt > 0 ){
			alert("저장하였습니다.");
		}else{
			alert("변경사항이 없습니다.");
		}
	}

	/**
	 * 화면 초기화 작업
	 */
	_detail.initView = function() {
		$("[data-brand-video-toggle=upload]").hide(); // 브랜드 비디오 직접등록 UI 숨기기

		$(".ico-fdel").hide();	// 모든 첨부파일 삭제 아이콘 숨기기
		if($("[data-brand-image-color=file-name]").text() != "") {
			$("[data-brand-image-color=remove]").show();
		}
		if($("[data-brand-image-gray=file-name]").text() != "") {
			$("[data-brand-image-gray=remove]").show();
		}

		// A-rt브랜드 상품 전시여부에 따른 브랜드페이지 노출
		var brandPrdtArtDispYn = $("input:radio[name=brandPrdtArtDispYn]:checked").val();
		if(brandPrdtArtDispYn == 'Y'){
			$("#brandPrdtArtDispYTab").show();
			$("#brandPrdtArtDispNTab").hide();
		}else{
			$("#brandPrdtArtDispYTab").hide();
			$("#brandPrdtArtDispNTab").show();
		}
		
		//한글
		$(document).on("input", "#korInitialText", function(event){
			if(!(event.keyCode >=37 && event.keyCode<=40)){
				var inputVal = $(this).val();
				$("#"+this.id).val(inputVal.replace(/[^(ㄱ-ㅎ)]/gi,''));
			}
		});
		//영문
		$(document).on("input", "#engInitialText", function(event){
			if(!(event.keyCode >=37 && event.keyCode<=40)){
				var inputVal = $(this).val();
				$("#"+this.id).val(inputVal.replace(/[^(a-zA-Z)]/gi,''));
			}
		});
		//숫자
		$(document).on("input", "#etcInitialText", function(event){
			abc.text.validateOnlyNumber(this);
		});

	}

	/**
	 * 목록 조회
	 */
	_detail.initData = function() {
		_detail.doAction("readBrandStyleList");		// 브랜드 스타일 조회
		_detail.doAction("readDetailVisualList");	// 브랜드 상세 비주얼 조회
		_detail.doAction("readMallBrandPagePcALL");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC)
		_detail.doAction("readMallBrandPagePc10001");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC- abcmart)
		_detail.doAction("readMallBrandPagePc10002");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC- gs)
		_detail.doAction("readMallBrandPagePc10004");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(PC- kids)
		_detail.doAction("readMallBrandPageMoALL");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE)
		_detail.doAction("readMallBrandPageMo10001");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE- abcmart)
		_detail.doAction("readMallBrandPageMo10002");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE- gs)
		_detail.doAction("readMallBrandPageMo10004");	// 통합몰 브랜드 페이지 상단 비주얼 전시정보(MOBILE- kids)
		_detail.doAction("readOtsBrandPagePc");		// OTS 브랜드 페이지 상단 비주얼 전시정보(PC)
		_detail.doAction("readOtsBrandPageMo");		// OTS 브랜드 페이지 상단 비주얼 전시정보(MOBILE)
	}

	/**
	 * 파일 업로드. HTML에 이미지 전송을 위함 별도 form태그를 사용하여 서버에 업로드.
	 * me : this
	 * url : 목적지
	 * callbackSuccess : 성공 시 callback 함수
	 * callbackFailure : 실패 시 callback 함수
	 */
	_detail.fileUpload = function(me, url, callbackSuccess, callbackFailure) {
		var formId = $(me).parent().attr("id");
		var submitForm = $.form("#"+formId);

		//console.log("submitForm > " + submitForm);

		var option = {
				url : url,
				type : "POST",
				dataType : "json",
				beforeSend : function() {
					console.log("이미지를 전송합니다.");
				},
				success : function(data) {
					console.log("이미지 저장 성공");
					console.log(data);
					if(typeof callbackSuccess === "function") {
						callbackSuccess(data);
					}
				},
				error : function(e){
					console.log("이미지 저장 실패");
					console.log(e);
					if(typeof callbackFailure === "function") {
						callbackFailure(e);
					}
				},
				complete : function(e) {
					//uploadForm.empty();	// 폼 객체 내부 초기화
					console.log("이미지 업로드 폼을 초기화 하였습니다.");
				}
			};

		submitForm.submit(option);
	}

	/**
	 * 대상상품갱신
	 */
	_detail.callbackSheet = function(d) {
		console.log("callbackSheet Data >>>>> " + JSON.stringify(d) + " / " + d.length + " / " + d[0].prdtNo);
		var brandNo = $("#brand-no").val();
		var prdtNo = [];
		var url = "";

		if(evtSheet == "styleSheet") {
			url = "/product/brand/style/product/save";
		} else if(evtSheet == "visualSheet") {
			url = "/product/brand/visual/product/save";
		}

		var params = {"brandNo" : brandNo, "sortSeq" : sheetSortSeq};

		$.each(d, function(k, v){
			prdtNo.push(v.prdtNo);
		});
		params.prdtNo = prdtNo;

		console.log("params > " + JSON.stringify(params));

		$.ajax({
			type :"get",
			url : url,
			dataType : "json",
			data : params
		})
		request.done(function(data){
			console.log("data > " + JSON.stringify(data));
		})
		request.fail(function(e){
			alert("대상상품 갱신에 실패하였습니다.");
		});

		if(evtSheet == "styleSheet") {
			_detail.doAction("readBrandStyleList");		// 브랜드 스타일 조회
		} else if(evtSheet == "visualSheet") {
			_detail.doAction("readDetailVisualList");	// 브랜드 상세 비주얼 조회
		}

		sheetSortSeq = "";
		evtSheet = "";

	}

	/**
	 * 프로모션배너 저장
	 */
	_detail.promotion = function(targetChnnlNo) {
		var resultType = false;

		// 링크연결 방법 연결안함이 아닐 때 랜딩 URL 세팅
		$('input[name="cnnctnType"]').each(function(index, item){
			var chnnlNo = $(this).parent().find('input[name="chnnlNo"]').val();
			if(chnnlNo != targetChnnlNo){
				return true;
			}
			var prefix = $(this).parent().find('input[name="deviceCode"]').val() == '10000' ? 'pc' : 'mo';
			var linkTargetType = '';
			var linkInfo = '';
			switch (this.value) {
			case 'U': // URL 입력
				linkTargetType = $('#' + prefix + '-link-target-type-' + targetChnnlNo).val();
				linkInfo = $('#' + prefix + '-link-info-u-' + targetChnnlNo).val();
				break;
			case 'M': // 이미지맵
				linkTargetType = 'S'
				linkInfo = $('#' + prefix + '-link-info-m-'+ targetChnnlNo).val();
				break;
			case 'N':
				break;
			default:
				break;
			}
			$(this).parent().find('input[name="linkTargetType"]').val(linkTargetType);
			$(this).parent().find('input[name="linkInfo"]').val(linkInfo);
		});

		$.form("#form-brand-mall-"+targetChnnlNo).submit({
			url			: "/product/brand/page/promotion/save",
			async		: false,
			type		: "POST",
			dataType	: "json",
			enctype		: "multipart/form-data",
			success		: function(data) {
				resultType = data.success;
			},
			error		: function(e) {
				if(e) {
					if(e.message) {
						alert(e.message);
					}
					if (e.fieldName) {
						// PC, Mobile 판단
						var fieldName = e.fieldName.split('-');
						var prefix = fieldName[0];
						var name = fieldName[1];
						var tabId = "";

						switch (targetChnnlNo) {
						case '10001': // URL 입력
							tabId = "a_tabAbcMart";
							break;
						case '10002': // 이미지맵
							tabId = "a_tabGs";
							break;
						case '10004':
							tabId = "a_tabKids";
							break;
						default:
							break;
						}
						if(tabId != ""){
							$("#"+tabId).trigger('click');
						}

						if('linkInfo' != name) {
							$('#' + prefix + '-brand-page-tbody-'+ targetChnnlNo).find('[name=' + name + ']').focus();
						} else {
							$('#' + prefix + '-link-info-' + $('input[name="' + prefix + 'CnnctnType"]:checked').val().toLowerCase() + '-' + targetChnnlNo).focus();
						}
					}
				}
			}
		});
		return resultType;
	}

	/**
	 * 스타일 리스트 저장
	 */
	_detail.setStyleList = function() {
		var styleChangeFlag = false;
		var	list = brandStyleList.ExportData({"Type":"json"}).data;

		$.each(list, function(i, v) {
			if(v.status == 'U' || v.status == 'I') {
				styleChangeFlag = true;
			}
		});

		if(styleChangeFlag) {
			_detail.doAction("saveAsyncBrandStyleList");
		}else{
			alert("변경된 사항이 없습니다.")
		}
	}

	/**
	 * 비주얼 리스트 저장
	 */
	_detail.setVisualList = function() {
		var visualChangeFlag = false;
		var	list = detailVisualList.ExportData({"Type":"json"}).data;

		$.each(list, function(i, v) {
			if(v.status == 'U' || v.status == 'I') {
				visualChangeFlag = true;
			}
		});

		// 브랜드비주얼 검증추가
		var isValid = true;
		var invalidMessage = "";
		$.each(list, function(i, v) {
			if(v.imagePreview === "") {
				isValid = false;
				invalidMessage = "브랜드 비주얼 이미지는 필수입니다.";
			}
			if(v.titleText === "") {
				isValid = false;
				invalidMessage = "브랜드 비주얼 제목은 필수입니다.";
			}
		});

		if(isValid === false) {
			alert(invalidMessage);
		} else if(visualChangeFlag) {
			_detail.doAction("saveAsyncDetailVisualList");
		}

		return isValid;
	}

	/**
	 * IBSheet 처리 이벤트
	 */
	_detail.doAction = function(sAction) {
		if(sAction.startsWith('read')) {
			_detail.readAction(sAction);
		} else if(sAction.startsWith('save')) {
			_detail.saveAction(sAction);
		} else {
			console.error(sAction);
		}
	}

	/**
	 * IBSheet 조회액션
	 */
	_detail.readAction = function(sAction) {
		var paramBrandNo = $("#brand-no").val() == "" ? "0" : $("#brand-no").val();
		var doAction = true;
		var param = {};
		switch (sAction) {
		case "readBrandStyleList":
			// 브랜드 스타일 조회
			param = {
				url 		: "/product/brand/style",
				onePageRow 	: $("#page-count-brand-style").val(),
				subparam 	: "brandNo=" + paramBrandNo,
				sheet 		: "brandStyleList"
			};
			break;
		case "readDetailVisualList" :
			// 상세비주얼 조회
			param = {
				url			: "/product/brand/visual/list",
				subparam	: "brandNo=" + paramBrandNo,
				sheet		: "detailVisualList"
			};
			break;
		case "readMallBrandPagePcALL" :
			// 통합몰 브랜드 상단비주얼 조회(PC)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-pc-ALL-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10000&dispGbnType=T&chnnlNo=ALL",
				sheet		: "mallBrandPageVisualPcALLList"
			};
			break;
		case "readMallBrandPagePc10001" :
			// 통합몰 브랜드 상단비주얼 조회(PC-abcmart)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-pc-10001-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10000&dispGbnType=T&chnnlNo=10001",
				sheet		: "mallBrandPageVisualPc10001List"
			};
			break;
		case "readMallBrandPagePc10002" :
			// 통합몰 브랜드 상단비주얼 조회(PC-gs)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-pc-10002-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10000&dispGbnType=T&chnnlNo=10002",
				sheet		: "mallBrandPageVisualPc10002List"
			};
			break;
		case "readMallBrandPagePc10004" :
			// 통합몰 브랜드 상단비주얼 조회(PC-kids)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-pc-10004-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10000&dispGbnType=T&chnnlNo=10004",
				sheet		: "mallBrandPageVisualPc10004List"
			};
			break;
		case "readMallBrandPageMoALL" :
			// 통합몰 브랜드 상단비주얼 조회(MOBILE)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-mo-ALL-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10001&dispGbnType=T&chnnlNo=ALL",
				sheet		: "mallBrandPageVisualMoALLList"
			};
			break;
		case "readMallBrandPageMo10001" :
			// 통합몰 브랜드 상단비주얼 조회(Mo-abcmart)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-mo-10001-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10001&dispGbnType=T&chnnlNo=10001",
				sheet		: "mallBrandPageVisualMo10001List"
			};
			break;
		case "readMallBrandPageMo10002" :
			// 통합몰 브랜드 상단비주얼 조회(Mo-gs)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-mo-10002-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10001&dispGbnType=T&chnnlNo=10002",
				sheet		: "mallBrandPageVisualMo10002List"
			};
			break;
		case "readMallBrandPageMo10004" :
			// 통합몰 브랜드 상단비주얼 조회(Mo-kids)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-art-mo-10004-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10000&deviceCode=10001&dispGbnType=T&chnnlNo=10004",
				sheet		: "mallBrandPageVisualMo10004List"
			};
			break;
		case "readOtsBrandPagePc" :
			// OTS 브랜드 상단비주얼 조회(PC)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-ots-pc-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10001&deviceCode=10000&dispGbnType=T",
				sheet		: "otsBrandPageVisualPcList"
			};
			break;
		case "readOtsBrandPageMo" :
			// OTS 브랜드 상단비주얼 조회(MOBILE)
			param = {
				url			: "/product/brand/page/visual",
				onePageRow 	: $("#page-count-brand-page-ots-mo-top").val(),
				subparam	: "brandNo=" + paramBrandNo + "&siteNo=10001&deviceCode=10001&dispGbnType=T",
				sheet		: "otsBrandPageVisualMoList"
			};
			break;
		default :
			doAction = false;
			console.log("인자 값이 잘못되었습니다." + sAction);
		}
		if(doAction) {
			DataSearchPaging(param);
		}
	}


	/**
	 * IBSheet 저장액션
	 */
	_detail.saveAction = function(sAction) {
		var brandNo = $("#brand-no").val();
		var param = { "Param" : "paramBrandNo=" + brandNo , "Quest" : 0};
		switch (sAction) {
		case "saveAsyncBrandStyleList" :
			var url = "/product/brand/style/save";
			param.Quest = 0;
			console.log("브랜드 스타일 등록. 파라미터 브랜드번호 : " + brandNo);
			brandStyleList.DoSave(url, param);
			eventFlag = "";
			break;
		case "saveAsyncDetailVisualList" :
			var url = "/product/brand/visual/save";
			param.Quest = 0;
			console.log("브랜드 비주얼 등록. 파라미터 브랜드번호 : " + brandNo);
			detailVisualList.DoSave(url, param);
			eventFlag = "";
			break;
		case "saveAsyncBrandPageMpALLList" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(PC art) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualPcALLList.DoSave(url, param);
			break;
		case "saveAsyncBrandPageMp10001List" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(PC abcmart) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualPc10001List.DoSave(url, param);
			break;
		case "saveAsyncBrandPageMp10002List" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(PC gs) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualPc10002List.DoSave(url, param);
			break;
		case "saveAsyncBrandPageMp10004List" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(PC kids) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualPc10004List.DoSave(url, param);
			break;
		case "saveAsyncBrandPageMmALLList" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(MO art) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualMoALLList.DoSave(url, param);
			break;
		case "saveAsyncBrandPageMm10001List" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(MO abcmart) 등록. 파라미터 브랜드번호 : " + brandNo);
			console.log(mallBrandPageVisualMo10001List.DoSave(url, param));
			break;
		case "saveAsyncBrandPageMm10002List" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(MO gs) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualMo10002List.DoSave(url, param);
			break;
		case "saveAsyncBrandPageMm10004List" :
			var url = "/product/brand/page/save";
			console.log("브랜드 통합몰 전시정보(MO kids) 등록. 파라미터 브랜드번호 : " + brandNo);
			mallBrandPageVisualMo10004List.DoSave(url, param);
			break;
		case "saveAsyncBrandPageOpList" :
			var url = "/product/brand/sortChange/save";
			console.log("브랜드 OTS 전시정보(PC) 등록. 파라미터 브랜드번호 : " + brandNo);
			otsBrandPageVisualPcList.DoSave(url, param);
			break;
		case "saveAsyncBrandPageOmList" :
			var url = "/product/brand/sortChange/save";
			console.log("브랜드 OTS 전시정보(PC) 등록. 파라미터 브랜드번호 : " + brandNo);
			otsBrandPageVisualMoList.DoSave(url, param);
			break;
		default :
			console.log("인자 값이 잘못되었습니다.");
		}
	}

	/**
	 * 브랜드 정보 저장 전 ibsheet 변동사항 저장
	 */
	_detail.beforeSave = function(sheetName, saveActionName) {
		var visualChangeFlag = false;
		var cnt = 0;
		var	list = sheetName.ExportData({"Type":"json"}).data;

		$.each(list, function(i, v) {
			if(v.status == 'U' || v.status == 'I') {
				visualChangeFlag = true;
				cnt++;
			}
		});

		if(visualChangeFlag) {
			_detail.doAction(saveActionName);
		}
		return cnt;
	}

	$(function() {
		_detail.init();
	});

})();
