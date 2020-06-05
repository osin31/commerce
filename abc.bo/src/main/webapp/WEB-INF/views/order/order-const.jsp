<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	(function() {
		var _consts = abc.object.createNestedObject(window,"abc.order.consts");
		
		// 매출취소구분
		_consts.SALES_CNCL_GBN_TYPE_SALE				= "${CommonCode.SALES_CNCL_GBN_TYPE_SALE}";	// 매출                    
		_consts.SALES_CNCL_GBN_TYPE_CANCEL				= "${CommonCode.SALES_CNCL_GBN_TYPE_CANCEL	}";	// 취소                    
		_consts.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE		= "${CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE}";	// 교환매출             
		_consts.SALES_CNCL_GBN_TYPE_SALE_EXCHANGE_CANCEL	= "${CommonCode.SALES_CNCL_GBN_TYPE_SALE_EXCHANGE_CANCEL}";// 교환취소             

		// 주문상품상태코드
		_consts.ORDER_PRDT_STAT_CODE_TEMP						= "${CommonCode.ORDER_PRDT_STAT_CODE_TEMP	}";		// 임시주문                                                     
		_consts.ORDER_PRDT_STAT_CODE_STAND_BY					= "${CommonCode.ORDER_PRDT_STAT_CODE_STAND_BY}";	// 입금대기                                            
		_consts.ORDER_PRDT_STAT_CODE_COMPLETE					= "${CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE}";	// 결제완료                                             
		_consts.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION		= "${CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION}";// 상품준비중                
		_consts.ORDER_PRDT_STAT_CODE_PRODUCT_DELIVERY			= "${CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_DELIVERY}";// 상품출고                           
		_consts.ORDER_PRDT_STAT_CODE_DELIVERY_ING				= "${CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_ING}";// 배송중                                        
		_consts.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH 	= "${CommonCode.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH}"; // 픽업준비완료 
		_consts.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH			= "${CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH}";// 배송완료                              
		_consts.ORDER_PRDT_STAT_CODE_BUY_CONFIRM				= "${CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM}";// 구매확정                                     
		_consts.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT				= "${CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT}";// 취소접수                                   
		_consts.ORDER_PRDT_STAT_CODE_REFUND_ACCEPT				= "${CommonCode.ORDER_PRDT_STAT_CODE_REFUND_ACCEPT}";// 환수/환불접수                         
		_consts.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE			= "${CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE	}";// 취소완료                              

		_consts.STOCK_GBN_CODE_AI = "${CommonCode.STOCK_GBN_CODE_AI}";		 // 온라인물류
		_consts.STOCK_GBN_CODE_AW ="${CommonCode.STOCK_GBN_CODE_AW}";	// 스마트물류
		_consts.STOCK_GBN_CODE_AS ="${CommonCode.STOCK_GBN_CODE_AS}";		// 오프라인매장
		_consts.STOCK_GBN_CODE_VD = "${CommonCode.STOCK_GBN_CODE_VD}";		 // 입점사
		
		
		_consts.DISCOUNT_GBN_COUPON = "COUPON";		 //  쿠폰
		_consts.DISCOUNT_GBN_PROMOTION = "PROMO";		 //  프로모션
		
		_consts.PYMNT_MEANS_CODE_CARD				= "${CommonCode.PYMNT_MEANS_CODE_CARD}";		 // "10000"; // 신용카드/체크카드
		_consts.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT	= "${CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT}";		 //  "10001"; // 무통장입금(가상계좌)
		_consts.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER	= "${CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER}";		 //  "10002"; // 실시간계좌이체
		_consts.PYMNT_MEANS_CODE_HANDPHONE			= "${CommonCode.PYMNT_MEANS_CODE_HANDPHONE}";		 // "10003"; // 휴대폰결제
		_consts.PYMNT_MEANS_CODE_NAVER_PAY			= "${CommonCode.PYMNT_MEANS_CODE_NAVER_PAY}";		 //  "10004"; // 네이버페이
		_consts.PYMNT_MEANS_CODE_KAKAO_PAY			= "${CommonCode.PYMNT_MEANS_CODE_KAKAO_PAY}";		 // "10005"; // 카카오페이
		_consts.PYMNT_MEANS_CODE_MOBILE_GIFT		= "${CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT}";		 // "10006"; // 모바일상품권
		_consts.PYMNT_MEANS_CODE_BUY_POINT			= "${CommonCode.PYMNT_MEANS_CODE_BUY_POINT}";		 //  "10007"; // 구매포인트
		_consts.PYMNT_MEANS_CODE_EVENT_POINT		= "${CommonCode.PYMNT_MEANS_CODE_EVENT_POINT}";		 //  "10008"; // 이벤트포인트
		
		_consts.DLVY_TYPE_CODE_NORMAL_LOGISTICS	 = "${CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS}";		 //  "10000"; // 일반택배
		_consts.DLVY_TYPE_CODE_CONVENIENCE_PICKUP  = "${CommonCode.DLVY_TYPE_CODE_CONVENIENCE_PICKUP}";		 //   "10001"; // 편의점픽업
		_consts.DLVY_TYPE_CODE_STORE_PICKUP		  = "${CommonCode.DLVY_TYPE_CODE_STORE_PICKUP}";		 //  "10002"; // 매장픽업
		
		// 주문 상태 코드 
		_consts.ORDER_STAT_CODE_TEMP				= "${CommonCode.ORDER_STAT_CODE_TEMP}";		 //  "10000"; // 임시주문
		_consts.ORDER_STAT_CODE_STAND_BY			= "${CommonCode.ORDER_STAT_CODE_STAND_BY}";		 //  "10001"; // 입금대기
		_consts.ORDER_STAT_CODE_COMPLETE			= "${CommonCode.ORDER_STAT_CODE_COMPLETE}";		 //   "10002"; // 결제완료
		_consts.ORDER_STAT_CODE_CANCEL_ACCEPT		= "${CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT}";		 //  "10003"; // 전체취소접수
		_consts.ORDER_STAT_CODE_REFUND_ACCEPT		= "${CommonCode.ORDER_STAT_CODE_REFUND_ACCEPT}";		 //  "10004"; // 환수/환불접수
		_consts.ORDER_STAT_CODE_CANCEL_COMPLETE		= "${CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE}";		 //   "10005"; // 전체취소완료

		_consts.MEMBER_TYPE_CODE_ONLINE		= "${CommonCode.MEMBER_TYPE_CODE_ONLINE}";		 //  "10000"; // 온라인회원
		_consts.MEMBER_TYPE_CODE_MEMBERSHIP	= "${CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP}";		 //  "10001"; // 멤버쉽회원
		_consts.MEMBER_TYPE_CODE_NONMEMBER	= "${CommonCode.MEMBER_TYPE_CODE_NONMEMBER}";		 //  "10002"; // 비회원
		
		_consts.PRDT_TYPE_CODE_ONOFFLINE	= "${CommonCode.PRDT_TYPE_CODE_ONOFFLINE}";		 //  "10000"; // 온오프라인상품
		_consts.PRDT_TYPE_CODE_ONLY_ONLINE	= "${CommonCode.PRDT_TYPE_CODE_ONLY_ONLINE}";		 //  "10001"; // 온라인전용상품
		_consts.PRDT_TYPE_CODE_ONLY_OFFLINE	= "${CommonCode.PRDT_TYPE_CODE_ONLY_OFFLINE}";		 //  "10002"; // 오프라인전용상품
		_consts.PRDT_TYPE_CODE_GIFT			= "${CommonCode.PRDT_TYPE_CODE_GIFT}";		 //  "10003"; // 사은품
		_consts.PRDT_TYPE_CODE_DELIVERY		= "${CommonCode.PRDT_TYPE_CODE_DELIVERY}";		 //  "10004"; // 배송비
		
	})();
</script>