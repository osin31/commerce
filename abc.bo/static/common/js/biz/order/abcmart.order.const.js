/***
 * 주문 목록 관련 함수 정의 
 * 
 */
(function() {
	var _const = abc.object.createNestedObject(window,"abc.biz.order.const");
	
	/**
	 * 네이버페이 START
	 */
	
	_const.NAVERPAY_SUCESS = "Success"; //성공
	_const.NAVERPAY_INVALIDMERCHANT = "InvalidMerchant";
	_const.NAVERPAY_REQUIRECONDITION = "RequireCondition";
	_const.NAVERPAY_TIMECONDITIONERROR = "TimeConditionError";
	
	
	_const.NAVERPAY_PAYMENT_MEAN_CARD = "CARD";
	_const.NAVERPAY_PAYMENT_MEAN_BANK = "BANK";
	
	_const.NAVERPAY_PAYMENT_CARDCODE = {"C0" : "신한",
			"C1" : "비씨",
			"C2" : "광주",
			"C3" : "KB국민",
			"C4" : "NH",
			"C5" : "롯데",
			"C6" : "산업",
			"C7" : "삼성",
			"C8" : "수협",
			"C9" : "씨티",
			"CA" : "외환",
			"CB" : "우리",
			"CC" : "전북",
			"CD" : "제주",
			"CF" : "하나-외환",
			"CH" : "현대"};
	
	_const.NAVERPAY_PAYMENT_BANKCODE = {
			"002":"산업은행",
			"003":"기업은행",
			"004":"국민은행",
			"005":"외환은행",
			"007":"수협",
			"011":"농협",
			"020":"우리은행",
			"023":"SC제일은행",
			"027":"씨티은행",
			"031":"대구은행",
			"032":"부산은행",
			"034":"광주은행",
			"035":"제주은행",
			"037":"전북은행",
			"039":"경남은행",
			"045":"새마을금고",
			"048":"신협",
			"071":"우체국",
			"081":"KEB-하나은행",
			"088":"신한은행",
			"209":"동양증권",
			"218":"현대증권",
			"230":"미래에셋",
			"238":"미래에셋대우",
			"240":"삼성증권",
			"243":"한국투자증권",
			"247":"우리투자증권",
			"262":"하이투자증권",
			"263":"HMC투자증권",
			"266":"SK증권",
			"267":"대신증권",
			"269":"한화증권",
			"270":"하나대투증권",
			"278":"신한금융투자",
			"279":"동부증권",
			"280":"유진투자증권",
			"287":"메리츠증권",
			"291":"신영증권"};
	/**
	 * 네이버페이 END
	 */
	
	
	/**
	 * 관리자 메모
	 */
	_const.ORDER="ORDER";
	_const.CLAIM="CLAIM";
	_const.AS="AS";
})();