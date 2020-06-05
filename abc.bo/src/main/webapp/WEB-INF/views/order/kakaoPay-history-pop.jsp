<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>
<script type="text/javascript">
	$(function() {
		
		abc.biz.order.info.tab.kakaoPay.initKakaoPayGrid();  		// abc 자사 상품
		
	});
	
	// 결제정보 영역
	function kakaoPaySheet_OnRowSearchEnd(row){ 
		var paymentMethodTypeView 	= kakaoPaySheet.GetCellValue(row, "paymentMethodTypeView");
		var cardBinView 			= kakaoPaySheet.GetCellValue(row, "cardBinView");
		var installMonthView 		= kakaoPaySheet.GetCellValue(row, "installMonthView");
		var cardCorpNameView 		= kakaoPaySheet.GetCellValue(row, "cardCorpNameView");
		var interestFreeInstallView = kakaoPaySheet.GetCellValue(row, "interestFreeInstallView");
		
		var payInfo = "-";
		var installMonthText = "";
		var installMonth = installMonthView <= 1 ? "일시불" : installMonthView ; 
		
		if ( paymentMethodTypeView ==  "CARD") { // 카드
			if (interestFreeInstallView = "Y") { // 무이자 
				installMonthText = "(무이자)" ;
			}else { // 유이자 
				installMonthText = "("+ installMonth +")" ;
			}
			payInfo = cardCorpNameView + " " + cardBinView +" "+ installMonthText ; 
			
		}else if (paymentMethodTypeView ==  "MONEY") { // 
			payInfo = "카카오페이";
		}
		kakaoPaySheet.SetCellValue(row, "payInfo", payInfo);
		kakaoPaySheet.SetCellValue(row, "status", ""); // HTML로 재조합하여 데이터를 조회하면 기본 상태가 "수정"으로 인식되어, 조회 시 강제로 빈값 처리
	}
	
</script>
<body class="window-body">
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
		<input type="hidden" id="orderNo" name="orderNo" value='${orderNo}'>
		<input type="hidden" id="siteNo" name="siteNo" value='${siteNo}'>
		<input type="hidden" id="tid" name="tid"  value='${tid}'>
	</form>
	<div class="window-wrap">
		<div class="window-title">
			<h1>카카오페이 결제내역</h1>
		</div>
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">[주문번호 <span>${orderNo}</span>] 카카오페이 결제내역</h3>
				</div>
			</div>
			<!-- E : content-header -->

			<!-- S : ibsheet-wrap -->
			<div class="ibsheet-wrap">
				<div style="width:100%; height:429px;" id="kakaoPayGrid">
				</div>
			</div>
			<!-- E : ibsheet-wrap -->
		</div>
	</div>
</body>
<script src="/static/common/js/biz/order/abcmart.order.info.tab.kakaopay.js<%=_DP_REV%>"></script>
</html>