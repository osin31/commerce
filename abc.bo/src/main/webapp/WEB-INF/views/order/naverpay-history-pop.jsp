<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function(){
		abc.biz.order.naverpay.history.init();
	})
	
	<%-- DataSearch(Row) End 이벤트--%>
	function listSheet_OnRowSearchEnd(row){
		var paymentMeans = listSheet.GetRowData(row).primaryPayMeans;
		
		if(paymentMeans === abc.biz.order.const.NAVERPAY_PAYMENT_MEAN_CARD){
			var info = abc.biz.order.const.NAVERPAY_PAYMENT_CARDCODE[listSheet.GetRowData(row).cardCorpCode] +"카드 ";
			info += listSheet.GetRowData(row).cardNo;
			console.log(listSheet.GetRowData(row).cardNo)
			if(listSheet.GetRowData(row).cardInstCount > 0 )
				info += " ("+listSheet.GetRowData(row).cardInstCount+"개월)"
			else
				info += " (일시불)";
			listSheet.SetCellValue(row, "paymentInfo", info);
		}
		else if(paymentMeans === abc.biz.order.const.NAVERPAY_PAYMENT_MEAN_BANK){
			var info = abc.biz.order.const.NAVERPAY_PAYMENT_BANKCODE[listSheet.GetRowData(row).bankCorpCode] +"카드 ";
			info += listSheet.GetRowData(row).bankAccountNo;
			listSheet.SetCellValue(row, "paymentInfo", info);
		}
		
	}
	
	var _naverpayHistory = abc.object.createNestedObject(window,"abc.biz.order.naverpay.history");
	
	_naverpayHistory.init = function() {
		createIBSheet2(document.getElementById("listGrid"), "listSheet", "100%", "370px");

		// Grid 초기화 기본/헤더/컬럼선언
		var initSheet = {};
		var pageCount = $('#pageCount').val();
		
		initSheet.Cfg = {SearchMode:smServerPaging2, Page:pageCount, MaxSort:1};
		initSheet.HeaderMode = {Sort:1, ColMove:0, ColResize:1};
		
		initSheet.Cols = [
			{Header:"원결제번호", 		Type:"Text",	SaveName:"paymentId",			Width: 10, Align:"Center", 	Edit:0}
			, {Header:"결제승인유형", 	Type:"Combo",	SaveName:"admissionTypeCode",	Width: 10, Align:"Center" ,  ComboText:"승인|전체취소|부분취소", ComboCode:"01|03|04",	Edit:0}
			, {Header:"카드사/은행",	Type:"Text",	SaveName:"paymentInfo",			Width: 20, Align:"Center", 	Edit:0}			
			, {Header:"",	Type:"Text",	SaveName:"primaryPayMeans",	 Hidden:1}
			, {Header:"",	Type:"Text",	SaveName:"cardCorpCode", Hidden:1}
			, {Header:"",	Type:"Text",	SaveName:"cardNo", Hidden:1}
			, {Header:"",	Type:"Text",	SaveName:"cardInstCount", Hidden:1}
			, {Header:"",	Type:"Text",	SaveName:"bankCorpCode", Hidden:1}
			, {Header:"",	Type:"Text",	SaveName:"bankAccountNo", Hidden:1}
			, {Header:"결제/취소금액", Type:"Float",	SaveName:"totalPayAmount",		Width: 10, Align:"Center", 	"Format": "#,### 원", Edit:0}
			, {Header:"결제/취소일시", Type:"Text",		SaveName:"admissionYmdt",		Width: 10, Align:"Center", 	Format:abc.consts.DEFAULT_DATETIME_PATTERN, Edit:0}
		]
		
		
		// Grid 초기화
		IBS_InitSheet(listSheet , initSheet);
		// Grid 건수 정보 표시
		listSheet.SetCountPosition(3);
		// Grid 페이지 네비게이션 버튼 표시
// 		listSheet.SetPagingPosition(2);
		// Grid width 자동 조절
		listSheet.FitColWidth();
		// Grid 마지막 컬럼을 All 너비에 맞춘다.
		listSheet.SetExtendLastCol(1);
		
		_naverpayHistory.doAction();
	}
	
	_naverpayHistory.doAction = function(){
		var naverpayList = ${naverpayList};
		if(naverpayList.code = abc.biz.order.const.NAVERPAY_SUCESS){
			var sheetData = {"data":[]};
			sheetData.data = naverpayList.body.list;
			console.log(sheetData.data);
			listSheet.LoadSearchData(sheetData, {
	            Sync: 1
	        });
		} else {
			alert("ERROR");
			listSheet.LoadSearchData(sheetData, {
	            Sync: 1
	        });
		}
	}
</script>
<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>네이버페이 결제내역</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">[주문번호 <span>${orderId}</span>] 네이버페이 결제내역</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div style="width:100%; height:429px;" id="listGrid">
					
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/order/abcmart.order.const.js<%=_DP_REV%>">
</script>
</html>