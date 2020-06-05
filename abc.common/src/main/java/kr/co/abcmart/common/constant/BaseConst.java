package kr.co.abcmart.common.constant;

import java.util.ArrayList;
import java.util.List;

public class BaseConst {

	public static final String L_PAREN = "(";
	public static final String R_PAREN = ")";
	public static final String L_BRACE = "{";
	public static final String R_BRACE = "}";
	public static final String L_BRACKET = "[";
	public static final String R_BRACKET = "]";
	public static final String MASKING = "*";
	public static final String HYPHEN = "-";

	/** value = Y */
	public static final String BOOLEAN_TRUE = "Y";

	/** value = N */
	public static final String BOOLEAN_FALSE = "N";

	/** value = S */
	public static final String RESULT_SUCCESS = "S";

	/** value = F */
	public static final String RESULT_FAIL = "F";

	/** value = C */
	public static final String LEAVE_RESULT = "C";

	/** number pattern - 세자리마다 콤마 표시 */
	public static final String DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT = "###,###";

	/** pattern = yyyy.MM.dd */
	public static final String DEFAULT_DATE_PATTERN = "yyyy.MM.dd";

	/** pattern = yyyy-MM-dd */
	public static final String DEFAULT_DATE_PATTERN_ISO_DATE = "yyyy-MM-dd";

	/** pattern = yyyyMMdd */
	public static final String DEFAULT_DATE_PATTERN_NOT_CHARACTERS = "yyyyMMdd";

	/** pattern = yyyyMM */
	public static final String DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS = "yyyyMM";

	/** pattern = yyyy-MM-dd hh:mm:ss */
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy.MM.dd HH:mm:ss";

	/** pattern = yyyyMMddhhmmss */
	public static final String DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS = "yyyyMMddHHmmss";

	/** pattern = YYYY-MM-dd hh:mm:ss:SSS */
	public static final String DEFAULT_DATETIME_MILI_PATTERN = "YYYY.MM.dd HH:mm:ss:SSS";

	/** pattern = YYYYMMddhhmmssSSS */
	public static final String DEFAULT_DATETIME_MILI_PATTERN_NOT_CHARACTERS = "YYYYMMddHHmmssSSS";

	/** 2999-12-31 */
	public static final String DEFAULT_END_DATE_NOT_CHARACTERS = "29991231";

	/** 2999-12-31 */
	public static final String DEFAULT_END_DATE = "2999.12.31";

	/** 235959 */
	public static final String DEFAULT_END_TIME_NOT_CHARACTERS = "235959";

	/** 23:59:59 */
	public static final String DEFAULT_END_TIME = "23:59:59";

	/** 2999-12-31 23:59:59 */
	public static final String DEFAULT_END_DATETIME = "2999.12.31 23:59:59";

	/** value = UTF-8 */
	public static final String DEFAULT_CHARSET_UTF_8 = "UTF-8";

	/** value = Content-Type */
	public static final String CONTENT_TYPE = "Content-Type";

	/** value = local */
	public static final String PROFILE_LOCAL = "local";

	/** value = dev */
	public static final String PROFILE_DEV = "dev";

	/** value = stage */
	public static final String PROFILE_STAGE = "stage";

	/** value = 401 */
	public static final int HTTP_STATUS_UNAUTHORIZED = 401; // 권한 없음 . 인증 안됨

	public static final String _USER_SESSION_KEY = "_USER_SESSION_KEY_";

	public static final String SNS_CSRF_STATE_SESSION_KEY = "_SNS_CSRF_STATE_SESSION_KEY_";

	public final static String PASSWORD_VALID_SESSION_KEY = "PASSWORD_VALID_SESSION_KEY";

	public static final String SNS_LOCATION_TYPE_SESSION_KEY = "_SNS_LOCATION_TYPE_SESSION_KEY";

	// wiselog cookie key - value : 회원순번
	public static final String WISELOG_COOKIE_KEY = "UID";

	/** abcmart site logo */
	public static final String DEFAULT_ABCMART_LOGO_IMAGE_URL = "https://image.abcmart.co.kr/nexti/images/abcmart/etc/logo_kcp_abcmart100_33.gif";

	/**
	 * 익명 사용자
	 */
	public static final String IS_AUTHENTICATED_ANONYMOUSLY = "IS_AUTHENTICATED_ANONYMOUSLY";

	/**
	 * 인증된 사용자
	 */
	public static final String IS_AUTHENTICATED_FULLY = "IS_AUTHENTICATED_FULLY";

	/**
	 * remember 사용자
	 */
	public static final String IS_AUTHENTICATED_REMEMBERED = "IS_AUTHENTICATED_REMEMBERED";

	public static final String DEFAULT_REMEMBER_PARAMETER = "remember_me";

	public static final String DEFAULT_REMEMBER_COOKIE = "__abc_rmck__";

	// 시스템 관리자 ADMIN_NO
	public static final String SYSTEM_ADMIN_NO = "SY00000000"; // 시스템 관리자

	// 비회원 회원 번호 MEMBER_NO
	public static final String NON_MEMBER_NO = "MB00000000"; // 비회원 회원번호

	public static final String CRUD_I = "I";
	public static final String CRUD_C = "C";
	public static final String CRUD_R = "R";
	public static final String CRUD_U = "U";
	public static final String CRUD_D = "D";

	public static final String IBSHEET_PAGING_PARAM_IBPAGE = "ibpage";
	public static final String IBSHEET_PAGING_PARAM_ONEPAGEROW = "onepagerow";
	public static final String IBSHEET_PAGING_PARAM_IBORDERBY = "iborderby";

	public static final String PAGING_PARAM_PAGE_NUM = "pageNum";
	public static final String PAGING_PARAM_ROWSPERPAGE = "rowsPerPage";
	public static final String PAGING_PARAM_SORT = "sort";

	public final static String MEMBERSHIP_POINT_LIST = "MEMBERSHIP_POINT_LIST"; // 멤버십 포인트 내역
	public final static String EMP_LIMIT_ATM_LIST = "EMP_LIMIT_ATM_LIST"; // 임직원 사용한도 내역

	// 휴대폰 인증번호 자릿수
	public static final int CERTIFY_NUMBER_DIGIT = 6;

	// 결제업체
	public final static String PAYMENT_VENDOR_NAME_KCP = "KCP";
	public final static String PAYMENT_VENDOR_NAME_NAVER = "NAVER";
	public final static String PAYMENT_VENDOR_NAME_KAKAO = "KAKAO";

	// KCP 전송용 주문번호 구분 접두사 값
	public final static String KCP_ORDERIDXX_PREFIX_ORDER = "ORDER-"; // 클레임용
	public final static String KCP_ORDERIDXX_PREFIX_CLAIM = "CLAIM-"; // 클레임용
	public final static String KCP_ORDERIDXX_PREFIX_AS = "AS-"; // AS용
	public final static String KCP_ORDERIDXX_PREFIX_GIFTCARD = "GIFTCARD-"; // 기프트카드

	// 로그인 타입
	public static final String LOGIN_TYPE_MEMBER = "member";
	public static final String LOGIN_TYPE_GUEST = "guest";

	// 비회원
	public static final String IS_AUTHENTICATED_GUEST = "IS_AUTHENTICATED_GUEST";

	// 고객응대 관리 첨부파일 구분 값
	public static final String COUNSEL_INQRY_FILE_GBN_TYPE = "Q";
	public static final String COUNSEL_ASWR_FILE_GBN_TYPE = "A";

	// 장바구니구분
	public static final String CART_TYPE_NOMAL = "B";
	public static final String CART_TYPE_DIRECT = "D";

	// 적립경로
	public static final String SAVE_PATH_TYPE_ONLINE = "O";
	public static final String SAVE_PATH_TYPE_OFFLIN = "F";

	// 포인트 지급 관련 상수
	public static final String POINT_TYPE_USE = "use";
	public static final String POINT_TYPE_CANCEL = "cancel";
	public static final String POINT_TYPE_SAVE = "save";
	public static final String POINT_TYPE_SUBT = "subt";

	// 배송비 상품번호옵션번호
	public static final String ART_DLVY_PRDT_NO = "1900000000";
	public static final String ART_DLVY_PRDT_OPTN_NO = "001";
	public static final String VNDR_DLVY_PRDT_NO = "1900000001";
	public static final String VNDR_DLVY_PRDT_OPTN_NO = "001";

	// 이전회원 유형
	public static final String BEFORE_MEMBER_MEMBERSHIP = "M";
	public static final String BEFORE_MEMBER_ONLINE = "O";
	public static final String BEFORE_MEMBER_SNS = "S";

	// Mobile native ios 식별값
	public static final String APP_IOS = "APP_IOS";
	// Mobile native ios 지문인식 식별값
	public static final String APP_IOS_FINGER = "APP_IOS_T";
	// Mobile native ios 얼굴인식 식별값
	public static final String APP_IOS_FACE = "APP_IOS_F";

	// Mobile native aos 식별값
	public static final String APP_AOS = "APP_AOS";
	// Mobile native aos 지문인식 식별값
	public static final String APP_AOS_FINGER = "APP_AOS_T";

	// 고객응대관리 첨부파일 구분
	public static final String ATCH_FILE_GBN_TYPE_Q = "Q"; // 질문
	public static final String ATCH_FILE_GBN_TYPE_A = "A"; // 답변

	// 적립유형 코드 이름
	public static final String SAVE_TYPE_CODE_EARN_PURCHASE_NAME = "상품구매적립";
	public static final String SAVE_TYPE_CODE_EARN_FIRSTPURCHASE_NAME = "첫구매 적립";
	public static final String SAVE_TYPE_CODE_EARN_ANNIVERSARY_NAME = "기념일구매 적립";
	public static final String SAVE_TYPE_CODE_EARN_JOIN_NAME = "회원가입 적립";
	public static final String SAVE_TYPE_CODE_EARN_PRODUCTREVIEW_NAME = "상품후기 적립";
	public static final String SAVE_TYPE_CODE_EARN_EVENT_NAME = "이벤트 적립";
	public static final String SAVE_TYPE_CODE_EARN_AFTERPUCHASE_NAME = "사후 적립";
	public static final String SAVE_TYPE_CODE_EARN_OFFLINEPURCHASE_NAME = "오프라인구매 적립";
	public static final String SAVE_TYPE_CODE_EARN_MEMBER_NAME = "고객응대적립";
	public static final String SAVE_TYPE_CODE_DEDUCTION_USEPURCHASE_NAME = "구매사용차감";
	public static final String SAVE_TYPE_CODE_DEDUCTION_EXPIRATION_NAME = "만기소멸차감";
	public static final String SAVE_TYPE_CODE_DEDUCTION_MEMBER_NAME = "고객응대차감";
	public static final String SAVE_TYPE_CODE_EARN_ETC_NAME = "기타적립";
	public static final String SAVE_TYPE_CODE_DEDUCTION_ETC_NAME = "기타차감";

	// 인터페이스 인증 결과
	public static final String IF_RESULT_SUCCESS = "Success";
	public static final String IF_RESULT_FAIL = "Failure";

	// 임직원 인증 최대 횟수
	public static final int EMP_CERTIFY_NUMBER_LIMIT_COUNT = 3;

	// 알림톡 프로필키[A-RT, OTS]
	public static final String KKO_PROFILE_KEY_ART = "9d1cde68361c2979d449ceea357e1bde9ccf6554";
	public static final String KKO_PROFILE_KEY_OTS = "9917fd4b48cab69d318883ed58f2e6ab99b9982e";

	// 접근 디바이스 종류
	public static final String VIEWER_DEVICE_PC = "pc";
	public static final String VIEWER_DEVICE_MOBILE = "mobile";

	/** 제휴사정보 세션 키 */
	public static final String COOKIE_NAME_AFFILIATION_AFFLTS_CODE = "affiliation-affltsCode";

	// 개행 용 br태그 , \r\n
	public static final String STRING_HTML_BR_TAG = "<br/>";
	public static final String STRING_HTML_NEW_LINE = "\r\n";

	// 회원 수정 구분
	public static final String MOD_GBN_TYPE_J = "J"; // 가입
	public static final String MOD_GBN_TYPE_M = "M"; // 수정
	public static final String MOD_GBN_TYPE_O = "O"; // 탈퇴
	public static final String MOD_GBN_TYPE_L = "L"; // 장기 미사용 탈퇴

	// 회원 수정 항목 구분
	public static final String MEMBER_MOD_EMAIL = "이메일";
	public static final String MEMBER_MOD_PHONE = "휴대폰 번호";
	public static final String MEMBER_MOD_ANNIVERSARY = "기념일";
	public static final String MEMBER_MOD_MARKETING = "마케팅 정보 수신 동의";
	public static final String MEMBER_MOD_NIGHT_MARKETING = "야간 마케팅 정보 수신 동의";

	// 인터페이스 판매자 사번
	public static final String SALE_EMP_CD = "990072";

	// 시스템 관리 이미지 타입
	public static final String IMG_TYPE_CHNNL = "C";
	public static final String IMG_TYPE_GNB = "G";

	// 롤링배너 쿠키 이름
	public static final String COOKIE_NAME_ROLLING_BANNER = "not_today_for_rolling_";
	
	// 존재 하지 않는 상품의 경우
	public static final String NOT_FOUND_PRODUCT = "not-found-product";

	/**
	 * **********************************************************************************************
	 * 채널 정보 S
	 * **********************************************************************************************
	 */
	// ART
	public final static String CHANNEL_NO_ART = "10000";
	// ABC MART
	public final static String CHANNEL_NO_ABCMART = "10001";
	// GRAND STARGE
	public final static String CHANNEL_NO_GRANDSTAGE = "10002";
	// OTS
	public final static String CHANNEL_NO_OTS = "10003";
	// KIDS
	public final static String CHANNEL_NO_KIDS = "10004";
	/**
	 * ******************* 채널 정보E**********************************************
	 */

	/**
	 * 매장등급 중 ST (standard)
	 */
	@SuppressWarnings("serial")
	public static final List<String> DISP_FLAG_TEXT_OF_ST = new ArrayList<String>() {
		{
			add("04007004");
			add("04007005");
			add("04007006");
			add("04007007");
		}
	};

	/**
	 * 매장등급 중 MS (mega stage)
	 */
	@SuppressWarnings("serial")
	public static final List<String> DISP_FLAG_TEXT_OF_MS = new ArrayList<String>() {
		{
			add("04007003");
			add("04007004");
			add("04007005");
		}
	};

	/**
	 * 매장등급 중 GS (grand stage)
	 */
	@SuppressWarnings("serial")
	public static final List<String> DISP_FLAG_TEXT_OF_GS = new ArrayList<String>() {
		{
			add("04007002");
			add("04007003");
			add("04007004");
		}
	};

	/**
	 * 매장등급 중 MG (mega stage)
	 */
	@SuppressWarnings("serial")
	public static final List<String> DISP_FLAG_TEXT_OF_MG = new ArrayList<String>() {
		{
			add("04007009");
		}
	};

	public static final String ART_REMEMBER_COOKIE = "__art_rmck__";
	public static final String OTS_REMEMBER_COOKIE = "__ots_rmck__";
}
