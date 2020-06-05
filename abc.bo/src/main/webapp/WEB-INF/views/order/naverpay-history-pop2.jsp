<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		
		abc.biz.order.info.tab.naverPay.initNaverPayGrid();  		
		
	});
	
	// 결제정보 영역
	function naverPaySheet_OnRowSearchEnd(row){ 
		var paymentMeans = naverPaySheet.GetCellValue(row, "primaryPayMeans");
		
		
		
		var paymentInfo ; 
		
		console.log ("paymentMeans" , paymentMeans ) ; 
		console.log ("cardNo" , cardNo ) ; 
		console.log ("cardCorpCode" , cardCorpCode ) ; 
		console.log ("cardInstCount" , cardInstCount ) ; 
		console.log ("bankCorpCode" , bankCorpCode ) ; 
		console.log ("bankAccountNo" , bankAccountNo ) ; 
		
		

		if(paymentMeans == abc.biz.order.const.NAVERPAY_PAYMENT_MEAN_CARD){
			var cardNo = naverPaySheet.GetCellValue(row, "cardNo");
			var cardCorpCode = naverPaySheet.GetCellValue(row, "cardCorpCode");
			var cardInstCount = naverPaySheet.GetCellValue(row, "cardInstCount");
			
			paymentInfo = abc.biz.order.const.NAVERPAY_PAYMENT_CARDCODE[cardCorpCode] +"카드 ";
			paymentInfo += cardNo;
			if(cardInstCount > 0 ){
				paymentInfo += " ("+cardInstCount+"개월)"
			}else{
				paymentInfo += " (일시불)";
			}
			
		}else if(paymentMeans == abc.biz.order.const.NAVERPAY_PAYMENT_MEAN_BANK ){
			var bankCorpCode = naverPaySheet.GetCellValue(row, "bankCorpCode");
			var bankAccountNo = naverPaySheet.GetCellValue(row, "bankAccountNo");
			paymentInfo = abc.biz.order.const.NAVERPAY_PAYMENT_BANKCODE[bankCorpCode] ;
			paymentInfo += bankAccountNo;
		}
		
		naverPaySheet.SetCellValue(row, "paymentInfo", paymentInfo);
		naverPaySheet.SetCellValue(row, "status", ""); // HTML로 재조합하여 데이터를 조회하면 기본 상태가 "수정"으로 인식되어, 조회 시 강제로 빈값 처리
	}
	
</script>
<body class="window-body">
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="hidden" id="siteNo" name="siteNo" value='${siteNo}'>
		<input type="hidden" id="orderNo" name="orderNo" value='${orderNo}'>
		<input type="text" id="paymentId" name="paymentId"  value='${paymentId}'>
	</form>
	<div class="window-wrap">
		<div class="window-title">
			<h1>네이버페이 결제내역</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">[주문번호 <span>${orderNo}</span>] 네이버페이 결제내역</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div style="width:100%; height:429px;" id="naverPayGrid">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/order/abcmart.order.info.tab.naverpay.js<%=_DP_REV%>"></script>
<script src="/static/common/js/biz/order/abcmart.order.const.js<%=_DP_REV%>"></script>
</html>