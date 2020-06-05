/**
 *
 */
package kr.co.abcmart.common.constant;

/**
 * @Desc : 메일에서 사용하는 템픞릿코드 상수. 각 상수의 값은 CM_EMAIL_TEMPLATE.EMAIL_ID와 일치해야 한다.
 *
 * @FileName : BaseMailCode.java
 * @Project : abc.common
 * @Date : 2019. 3. 22.
 * @Author : Kimyounghyun
 */
public class BaseMailCode {

	public static final String ORDER_COMPLETE = "oc_001";

	public static final String MEMBER_CERTIFICATION_NUMBER = "EC-05003"; // 비밀번호 찾기 이메일 인증 메일 ID

	public static final String SY_VENDOR_ADMIN_PASSWORD = "EA-01002"; // 업체 등록시 관리자 비밀번호

	public static final String SY_ADMIN_PASSWORD = "EA-01004"; // 관리자 생성 비밀번호

	public static final String GIFTCARD_BUY = "EC-07001";

	public static final String SY_MEMBER_PASSWORD_RESET = "EC-05012"; // 회원 비밀번호 초기화

	public static final String INQUIRY_ASWR_COM = "EC-04001"; // 1:1문의 답변 메일 ID

	public static final String VOC_ASWR_COM = "EC-04002"; // 고객의소리 답변 메일 ID

	public static final String MEMBER_UNUSED_LEAVE_SITE = "EC-05008"; // 장기 미사용 탈퇴 메일 ID

	public static final String MEMBER_LEAVE_SITE = "EC-05006"; // 회원 탈퇴 메일 ID

	public static final String ONLINE_JOIN_CONGRATULATIONS = "EC-05001"; // 온라인회원 가입 축하 메일 ID

	public static final String MEMBERSHIP_JOIN_CONGRATULATIONS = "EC-05009"; // 멤버쉽회원 가입 축하 메일 ID

	public final static String MEMBER_DESTORY_LONG_UNUSED_USER_INFO = "EC-05007"; // 장기 미사용 회원 정보 파기 안내(email)

	public final static String ONLINE_TRANSITION_CONGRATULATIONS = "EC-05002"; // 온라인회원 전환 축하 메일 ID

	public final static String MEMBERSHIP_TRANSITION_CONGRATULATIONS = "EC-05010"; // 멤버쉽회원 전환 축하 메일 ID

	public final static String MEMBERSHIP_TRANSITION_ORG_MBSP = "EC-05010-1"; // 기존 멤버쉽 회원 > 멤버쉽회원 전환 축하 메일 ID

	// 결제수단 메일 변경 ART
	public static final String ORDER_METHOD_PAYMENT_EMAIL_ART = "EC-01005-1";

	// 결제수단 메일 변경 OTS
	public static final String ORDER_METHOD_PAYMENT_EMAIL_OTS = "EC-01005-2";

	// 배송지 변경 메일
	public static final String DLVY_CHANGE_EMAIL_ART = "EA-05001";

	// 입금대기 메일
	public static final String MAIL_ID_ORDER_CASH_ART = "EC-01006-1";
	public static final String MAIL_ID_ORDER_CASH_OTS = "EC-01006-2";
	// 결제완료 메일
	public static final String MAIL_ID_ORDER_ART = "EC-01001-1";
	public static final String MAIL_ID_ORDER_OTS = "EC-01001-2";

	// 기프트카드 결제취소 메일
	public static final String GIFTCARD_PAY_CANCEL = "EC-07002";

	// 배송 발송안내(상품출고)
	public static final String MAIL_ID_DLVY_PRODUCT_INFO_ART = "EC-02001-1";
	public static final String MAIL_ID_DLVY_PRODUCT_INFO_OTS = "EC-02001-2";

	// 배송완료(구매확정 안내)(일반택배)
	public static final String MAIL_ID_DLVY_LOGISTICS_COMPLETED_ART = "EC-02002-1";
	public static final String MAIL_ID_DLVY_LOGISTICS_COMPLETED_OTS = "EC-02002-2";

	// 매장픽업(편의점픽업)상품 수령가능일 안내 (픽업준비완료, 편의점, 매장)
	public static final String MAIL_ID_STORE_PICKUP_PREPARATION_FINISH_ART = "EC-02004-1";
	public static final String MAIL_ID_STORE_PICKUP_PREPARATION_FINISH_OTS = "EC-02004-2";

	// 매장픽업 상품 구매확정 요청(배송완료, 매장)
	public static final String MAIL_ID_STORE_PICKUP_DELIVERY_FINISH_ART = "EC-02005-1";
	public static final String MAIL_ID_STORE_PICKUP_DELIVERY_FINISH_OTS = "EC-02005-2";

	// 광고성 수신동의확인
	public final static String RECEIVE_ADVERTISING_AGREE = "EC-05011";

}
