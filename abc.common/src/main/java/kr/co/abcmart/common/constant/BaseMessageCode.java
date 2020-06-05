/**
 *
 */
package kr.co.abcmart.common.constant;

/**
 * @Desc : 카카오 알림톡, SMS, MMS, LMS에서 사용하는 템플릿코드 상수. 각 상수의 값은
 *       CM_MESSAGE_TEMPLATE.MESG_ID와 일치해야 한다. 카카오 알림톡에 메시지를 등록하고 그 고유값을 사용한다.
 * @FileName : BaseMessageCode.java
 * @Project : abc.common
 * @Date : 2019. 3. 22.
 * @Author : Kimyounghyun
 */
public class BaseMessageCode {

	// 샘플 - 주문완료
	public static final String ORDER_COMPLETE = "001";

	// 샘플 - 회원가입
	public static final String MEMBERSHIP_JOIN = "002";

	// 장기 미사용 회원 정보 파기 안내 알림톡(포인트 미소유 회원)
	public final static String DESTORY_LONG_UNUSED_USER_INFO = "ART1001";

	// 장기 미사용 회원 정보 파기 안내 알림톡 (포인트 소유 회원)
	public final static String DESTORY_LONG_UNUSED_USER_INFO_POINT = "ART1002";

	// 개인정보변경
	public final static String UN_SUB_SCRIBE = "ART1010";

	// 광고성수신동의,철회
	public final static String RECEIVE_ADVERTISING_AGREE = "ART1011";
	public final static String RECEIVE_ADVERTISING_RESULT = "ART1012";

	// 멤버십 포인트 사후적립
	public final static String LATED_SAVE_POINT = "ART1009";

	// 기프트카드 등록완료 알림톡 템플릿
	public static final String REGISTCARD_MESSAGE_TEMPLATE = "ART3002";

	// 기프트카드 충전완료 알림톡 템플릿
	public static final String CHARGECARD_MESSAGE_TEMPLATE = "ART3001";

	// 기프트카드 선물거절 알림톡 템플릿
	public static final String REFUSECARD_MESSAGE_TEMPLATE = "ART3003";

	// 배송지 변경 ART
	public static final String DLVY_CHANGE_MMS_ART = "ART2015";

	// 배송지 변경 OTS
	public static final String DLVY_CHANGE_MMS_OTS = "MC-03014-2";

	// 결제수단 변경 ART
	public static final String ORDER_METHOD_PAYMENT_MMS_ART = "ART2016";

	// 결제수단 변경 OTS
	public static final String ORDER_METHOD_PAYMENT_MMS_OTS = "MC-03012-2";

	// 결제완료 메세지
	public static final String MESG_ID_ORDER_ART = "ART2005";
	public static final String MESG_ID_ORDER_OTS = "MC-03004-2";

	// 입금대기 메세지
	public static final String MESG_ID_ORDER_CASH_ART = "ART2001";
	public static final String MESG_ID_ORDER_CASH_OTS = "MC-03001-2";

	// 회원가입 축아 메세지
	public static final String MEMBER_JOIN_ONLINE = "ART1003";
	public static final String MEMBER_JOIN_MEMBERSHIP = "ART1004";

	// 회원전환 축하 메세지
	public static final String MEMBER_CHANGE_ONLINE = "ART1005";
	public static final String MEMBER_CHANGE_MEMBERSHIP = "ART1006";

	// 문의 관련 답변 메세지
	public static final String INQUIRY_COMPLETED_ART = "ART1014";
	public static final String INQUIRY_COMPLETED_OTS = "OTSA002";

	// 휴대폰 인증번호 메세지
	public static final String CERT_NUMBER_HDPHN = "ART1013";

	// 개인정보 변경 메세지
	public static final String CHANGE_MEMBER_INFO = "ART1010";

	// 매장픽업 수령기간 갱신
	public static final String PICKUP_PSBLT_YMD_ART = "ART2024";
	public static final String PICKUP_PSBLT_YMD_OTS = "OTSA027";

	// 매장픽업 장기 미수령
	public final static String PICKUP_NON_RECEIVE_ABC = "ART2025";
	public final static String PICKUP_NON_RECEIVE_OTS = "OTSA028";

	// 입금지연(무통장결제)
	public final static String PAYMENT_DELAY_ABC = "ART2002";
	public final static String PAYMENT_DELAY_OTS = "OTSA006";

	// 입금지연 주문취소(무통장결제)
	public final static String PAYMENT_DELAY_CANCLE_ABC = "ART2003";
	public final static String PAYMENT_DELAY_CANCLE_OTS = "OTSA007";

	// 쿠폰 소멸예정 안내
	public final static String MEMBER_COUPON_EXPIRE_ABC = "ART1008";
	public final static String MEMBER_COUPON_EXPIRE_OTS = "OTSA001";

	/** 상품 재입고 */
	public final static String PRODUCT_RESTOCK_ABC = "ART1015";
	public final static String PRODUCT_RESTOCK_OTS = "OTSA003";

	/** 상품 재입고 */
	public final static String PRODUCT_ENTRY_ABC = "ART1016";
	public final static String PRODUCT_ENTRY_OTS = "OTSA004";

	// 심의접수 메세지 ID
	public static final String MESG_ID_REVIEW_ACCEPT_BY_CLIENT_ART = "ART4009";
	public static final String MESG_ID_REVIEW_ACCEPT_BY_CLIENT_OTS = "OTSA039";

	// 수선접수 메세지 ID
	public static final String MESG_ID_REPAIR_ACCEPT_BY_CLIENT_ART = "ART4013";
	public static final String MESG_ID_REPAIR_ACCEPT_BY_CLIENT_OTS = "OTSA043";

	// 교환 접수(자사, 입점 상품 공통) - 고객접수 - 메세지 ID
	public static final String MESG_ID_EXCHANGE_CLAIM_ACCEPT_BY_CLIENT_ART = "ART4001";
	public static final String MESG_ID_EXCHANGE_CLAIM_ACCEPT_BY_CLIENT_OTS = "OTSA031";

	// 반품 접수(자사, 입점 상품 공통) - 고객접수 - 메세지 ID
	public static final String MESG_ID_RETURN_CLAIM_ACCEPT_BY_CLIENT_ART = "ART4005";
	public static final String MESG_ID_RETURN_CLAIM_ACCEPT_BY_CLIENT_OTS = "OTSA035";

	// 심의 접수와 동시에 접수완료 - BO접수 - 메세지ID
	public static final String MESG_ID_REVIEW_ACCEPT_BY_ADMIN_ART = "ART4010";
	public static final String MESG_ID_REVIEW_ACCEPT_BY_ADMIN_OTS = "OTSA040";
	// 수선 접수와 동시에 접수완료 - BO접수 - 메세지ID
	public static final String MESG_ID_REPAIR_ACCEPT_BY_ADMIN_ART = "ART4014";
	public static final String MESG_ID_REPAIR_ACCEPT_BY_ADMIN_OTS = "OTSA044";

	// 심의 상품 수령 완료 - 메세지ID
	public static final String MESG_ID_AS_REVIEW_RECEIPT_ART = "ART4011";
	public static final String MESG_ID_AS_REVIEW_RECEIPT_OTS = "OTSA041";

	// 수선 상품 수령 완료 - 메세지ID
	public static final String MESG_ID_AS_REPAIR_RECEIPT_ART = "ART4017";
	public static final String MESG_ID_AS_REPAIR_RECEIPT_OTS = "OTSA047";

	// AS 불가 반송 - 메세지ID
	public static final String MESG_ID_AS_IMPSBLT_SEND_BACK_ART = "ART4012";
	public static final String MESG_ID_AS_IMPSBLT_SEND_BACK_OTS = "OTSA042";

	// AS 비용 발생 - 메세지ID
	public static final String MESG_ID_AS_REPAIR_AMT_OCCURRENCE_ART = "ART4016";
	public static final String MESG_ID_AS_REPAIR_AMT_OCCURRENCE_OTS = "OTSA046";

	// 수선 배송 처리 - 메세지ID
	public static final String MESG_ID_AS_REPAIR_DLVY_PROC_ART = "ART4018";
	public static final String MESG_ID_AS_REPAIR_DLVY_PROC_OTS = "OTSA048";

	// 수선 불가 반송 - 메세지ID
	public static final String MESG_ID_AS_REPAIR_IMPSBLT_SEND_BACK_ART = "ART4012";
	public static final String MESG_ID_AS_REPAIR_IMPSBLT_SEND_BACK_OTS = "MC-04020-2";

	// 주문 전체 쉬소 - 메세지 ID
	public static final String MESG_ID_ORDER_ALL_CANCEL_ART = "ART2017";
	public static final String MESG_ID_ORDER_ALL_CANCEL_OTS = "OTSA020";

	// 주문 부분 쉬소 - 메세지 ID
	public static final String MESG_ID_ORDER_PART_CANCEL_ART = "ART2018";
	public static final String MESG_ID_ORDER_PART_CANCEL_OTS = "OTSA021";

	// 교환 접수(자사, 입점 상품 공통) - BO접수 - 메세지 ID
	public static final String MESG_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_ART = "ART4002";
	public static final String MESG_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_OTS = "OTSA032";

	// 교환 접수완료(자사, 입점 상품 공통) - 수거지시 - 메세지 ID
	public static final String MESG_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_ART = "ART4003";
	public static final String MESG_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_OTS = "OTSA033";

	// 교환 상품 발송완료 - 메세지 ID
	public static final String MESG_ID_EXCHANGE_DLVY_COMPLETE_ART = "ART4004";
	public static final String MESG_ID_EXCHANGE_DLVY_COMPLETE_OTS = "OTSA034";

	// 반품 접수(자사, 입점 상품 공통) - BO접수 - 메세지 ID
	public static final String MESG_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_ART = "ART4006";
	public static final String MESG_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_OTS = "OTSA036";

	// 반품 접수완료(자사, 입점 상품 공통) - 수거지시 - 메세지 ID
	public static final String MESG_ID_RETURN_CLAIM_ACCEPT_COMPLETE_ART = "ART4007";
	public static final String MESG_ID_RETURN_CLAIM_ACCEPT_COMPLETE_OTS = "OTSA037";

	// 반품처리 완료 - 메세지 ID
	public static final String MESG_ID_RETURN_PROC_COMPLETE_ART = "ART4008";
	public static final String MESG_ID_RETURN_PROC_COMPLETE_OTS = "OTSA038";

	// 환수금액 발생 - 메세지 ID
	public static final String MESG_ID_REDEMP_AMT_OCCURRENCE_ART = "ART4019";
	public static final String MESG_ID_REDEMP_AMT_OCCURRENCE_OTS = "OTSA049";

	// 관리자비밀번호 초기화 - 메세지 ID
	public static final String MESG_ID_SYADMIN_PW_RESET = "MA-01003";

	// 매장픽업 재고 없음 (미출) - 메세지 ID
	public static final String MESG_ID_PICKUP_STORE_OUT_OF_STOCK = "ART2026";

	// 일반택배/편의점 픽업 출고시
	public static final String MESG_ID_DLVY_RELEASE_ART = "ART2019";
	public static final String MESG_ID_DLVY_RELEASE_OTS = "OTSA022";

	// 일반 택배 배송 완료시
	public static final String MESG_ID_DLVY_LOGISTICS_COMPLETED_ART = "ART2020";
	public static final String MESG_ID_DLVY_LOGISTICS_COMPLETED_OTS = "OTSA023";

	// 편의점에 상품 도착시
	public static final String MESG_ID_CONVENIENCE_PICKUP_FINISH_ART = "ART2021";
	public static final String MESG_ID_CONVENIENCE_PICKUP_FINISH_OTS = "OTSA024";

	// 매장픽업 준비 완료
	public static final String MESG_ID_STORE_PICKUP_PREPARATION_FINISH_ART = "ART2023";
	public static final String MESG_ID_STORE_PICKUP_PREPARATION_FINISH_OTS = "OTSA026";

	// 매장픽업 수령 완료
	public static final String MESG_ID_STORE_PICKUP_DELIVERY_FINISH_ART = "ART2027";
	public static final String MESG_ID_STORE_PICKUP_DELIVERY_FINISH_OTS = "OTSA030";

	// 매장픽업 매장 미출
	public static final String MESG_ID_STORE_PICKUP_DELIVERY_CANCEL_ART = "ART2026";
	public static final String MESG_ID_STORE_PICKUP_DELIVERY_CANCEL_OTS = "OTSA029";

}
