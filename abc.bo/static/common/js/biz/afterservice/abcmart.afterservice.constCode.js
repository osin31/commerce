/***
 * AS 관련 상수 정의 
 * 
 */
(function() {
	var _constCode = abc.object.createNestedObject(window,"abc.biz.afterservice.constCode");
	
	
	
	_constCode.asStatCodeAccept          = "10000"; // A/S접수	            
	_constCode.asStatCodeAcceptCancel    = "10001"; // A/S접수취소          
	_constCode.asStatCodeWithdrawalAccept= "10002"; // A/S접수철회          
	_constCode.asStatCodeAcceptFinish    = "10003"; // A/S접수완료          
	_constCode.asStatCodePickupOrder     = "10004"; // 수거지시             
	_constCode.asStatCodeReceiveFinish   = "10005"; // 수령완료             
	_constCode.asStatCodeJudgeFinish 	 = "10006"; // 심의완료                
	_constCode.asStatCodeRepairFinish 	 = "10007"; // 수선완료               
	_constCode.asStatCodePaymentFequest  = "10008"; // 비용결제요청           
	_constCode.asStatCodePaymentFinish   = "10009"; // 비용결제완료           
	_constCode.asStatCodeShipping 		 = "10010"; // 배송중                   
	_constCode.asStatCodeAsFinish 		 = "10011"; // A/S완료                  
	_constCode.asStatCodeAsReject 		 = "10012"; // A/S불가                  
	                                                                                                                    
	_constCode.asPocTypeCodeRepairReject = "10000"; // 수선불가             
	_constCode.asPocTypeCodeRemark       = "10001"; // 유상 A/S                
	_constCode.asPocTypeCodeFree         = "10002"; // 무상 A/S                
	_constCode.asPocTypeDtlCodeChange	 = "10000"; // 교환	            
	_constCode.asPocTypeDtlCodeReturn	 = "10001"; // 반품	            
	_constCode.asPocTypeDtlCodeReject	 = "10002"; // 반송	 	          
	_constCode.asPocTypeDtlCodeRemarkAd  = "10003"; // 접착	  	            
	_constCode.asPocTypeDtlCodeRemarkSe  = "10004"; // 재봉	  	            
	_constCode.asPocTypeDtlCodeRemarkCh  = "10005"; // 갑보덧댐  	          
	_constCode.asPocTypeDtlCodeRemarkLc  = "10006"; // 라이닝덧댐	  	      
	_constCode.asPocTypeDtlCodeRemarkHc  = "10007"; // 힐컵지지대	  	      
	_constCode.asPocTypeDtlCodeRemarkPr  = "10008"; // 플라스틱교체(보강)  	
	_constCode.asPocTypeDtlCodeFree_ad	 = "10009"; // 접착	  	            
	_constCode.asPocTypeDtlCodeFree_se	 = "10010"; // 재봉	  	            
	_constCode.asPocTypeDtlCodeFree_ch	 = "10011"; // 갑보덧댐  	          
	_constCode.asPocTypeDtlCodeFree_lc	 = "10012"; // 라이닝덧댐  	        
	_constCode.asPocTypeDtlCodeFree_hc	 = "10013"; // 힐컵지지대	  	      
	_constCode.asPocTypeDtlCodeFree_pr	 = "10014"; // 플라스틱교체(보강)  	
 
	_constCode.asGbnCodeReview	  = "10000"; //심의 	      
	_constCode.asGbnCodeRepair	  = "10001"; //수선 	

     // 무상일떄 비용결제 및 비용결제 완료 스킵용	
	_constCode.asPocTypeCodeFreeSkip	 = "11111"; 
	
	_constCode.asStatCodeAcceptName= "A/S접수";            
	_constCode.asStatCodeAcceptCancelName= "A/S접수취소";         
	_constCode.asStatCodeWithdrawalAcceptName= "A/S접수철회";         
	_constCode.asStatCodeAcceptFinishName= "A/S접수완료";         
	_constCode.asStatCodePickupOrderName= "수거지시";         
	_constCode.asStatCodeReceiveFinishName= "수령완료";         
	_constCode.asStatCodeJudgeFinishName= "심의완료";            
	_constCode.asStatCodeRepairFinishName= "수선완료";           
	_constCode.asStatCodePaymentFequestName= "비용결제요청";           
	_constCode.asStatCodePaymentFinishName= "비용결제완료";           
	_constCode.asStatCodeShippingName= "A/S배송처리";             
	_constCode.asStatCodeAsFinishName= "A/S완료";             
	_constCode.asStatCodeAsRejectName= "A/S불가";    
	
	
	//클레임 구분 코드 : 교환
	_constCode.CLM_GBN_CODE_EXCHANGE	= "10001";
	//클레임 구분 코드 : 반품
	_constCode.CLM_GBN_CODE_RETURN	= "10002";
	
})();