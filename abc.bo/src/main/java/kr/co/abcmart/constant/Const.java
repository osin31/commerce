package kr.co.abcmart.constant;

import java.io.File;

import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.util.UtilsText;

public class Const extends BaseConst {

	public static final String URL_WWW_HTTPS = Config.getString("url.www.https");
	public static final String URL_IMG_HTTPS = Config.getString("url.images.https");
	public static final String URL_IMG_UPLOAD_PATH = UtilsText.concat(URL_IMG_HTTPS,
			Config.getString("url.images.upload.path"));

	public static final String SERVICE_DOMAIN_ART_FO = Config.getString("service.domain.art.fo");
	public static final String SERVICE_DOMAIN_ART_MO = Config.getString("service.domain.art.mo");
	public static final String SERVICE_DOMAIN_ABC_FO = Config.getString("service.domain.abc.fo");
	public static final String SERVICE_DOMAIN_ABC_MO = Config.getString("service.domain.abc.mo");
	public static final String SERVICE_DOMAIN_GS_FO = Config.getString("service.domain.gs.fo");
	public static final String SERVICE_DOMAIN_GS_MO = Config.getString("service.domain.gs.mo");
	public static final String SERVICE_DOMAIN_OTS_FO = Config.getString("service.domain.ots.fo");
	public static final String SERVICE_DOMAIN_OTS_MO = Config.getString("service.domain.ots.mo");
	public static final String SERVICE_DOMAIN_KIDS_FO = Config.getString("service.domain.kids.fo");
	public static final String SERVICE_DOMAIN_KIDS_MO = Config.getString("service.domain.kids.mo");
	public static final String SERVICE_DOMAIN_BO = Config.getString("service.domain.bo");

	public static final String LOGIN_DOMAIN_BO = Config.getString("login.domain.bo");
//	public static final String LOGIN_DOMAIN_PO = Config.getString("login.domain.po");

	// 서버에 업로드 할 경로 지정.
	public static final String UPLOAD_FILE_PATH = UtilsText.concat(Config.getString("url.images.upload.path"));

	// FO, MO 서비스 url
	public static final String URL_WWW_ART_FO_HTTPS = Config.getString("preview.fo.url");
	public static final String URL_WWW_ART_MO_HTTPS = Config.getString("preview.mo.url");
	public static final String URL_WWW_OTS_FO_HTTPS = Config.getString("preview.ots.fo.url");
	public static final String URL_WWW_OTS_MO_HTTPS = Config.getString("preview.ots.mo.url");

	// File 경로 구분자
	public static final String FILE_SEPARATOR = File.separator;

	public static final String ROLE_ADMIN_GROUP = "ROLE_10000"; // 총괄관리자그룹코드
	public static final String ROLE_ADMIN = "ROLE_20000";
	public static final String ACONNECT_AUTH_NO = "ROLE_20003"; // AConnect 권한 번호
	public static final String ROLE_CALL_CENTER = "ROLE_20004";
	public static final String ROLE_VENDER_GROUP = "ROLE_30000"; // 업체그룹코드
	public static final String ROLE_VENDER = "ROLE_40000";

	public static final String BATCH_SERVER_URL = Config.getString("batch.server.url");

	// 관리자 공지 첨부파일 허용 확장자
	public static final String ADMIN_NOTICE_FILE_EXT = "doc|docx|xls|xlsx|jpg|jpeg|zip";
	// 관리자 공지 첨부파일 max 갯수
	public static final int ADMIN_NOTICE_FILE_MAX_CNT = 3;
	// 관리자 공지 첨부파일 max 사이즈(개당)
	public static final int ADMIN_NOTICE_FILE_MAX_MB = 10;

	// 협력게시판 첨부파일 허용 확장자
	public static final String COWORK_FILE_EXT = "doc|docx|xls|xlsx|jpg|jpeg|zip";
	// 협력게시판 첨부파일 max 갯수
	public static final int COWORK_FILE_MAX_CNT = 3;
	// 협력게시판 첨부파일 max 사이즈(개당)
	public static final int COWORK_FILE_MAX_MB = 10;

	// 어드민계정(로그인시 validation 체크 안함)
	public static final String[] ADMIN_ID = { "sysdev", "sysvd" }; // sysop는 잠시 제외

	// 로컬 파일 저장 경로 설정
	public static final String LOCAL_UPLOAD_FILE_PATH = UtilsText.concat("d:\\", File.separator, "upload",
			File.separator, "multi");

	// 조건 날짜 : 관리자 비밀번호 변경 기한
	public static final String ADMIN_PSWD_RENEW_CONDITION = "ADMIN_PSWD_RENEW_CONDITION";

	// 웹진 유형 - 카탈로그
	public static final String WEBZINE_TYPE_CATALOGUE = "C";
	// 웹진 유형 - 동영상
	public static final String WEBZINE_TYPE_MOVIE = "M";
	// 웹진 유형 - 기사
	public static final String WEBZINE_TYPE_NEWS = "N";
	// 웹진 유형 - OTS magazine
	public static final String WEBZINE_TYPE_OTS_MAGAZINE = "O";
	// 웹진 유형 - OTS styling
	public static final String WEBZINE_TYPE_OTS_STYLING = "S";
	// 웹진 유형 - OTS EXCLUSIVE
	public static final String WEBZINE_TYPE_OTS_EXCLUSIVE = "E";

	// 업체구분
	public static final String VNDR_GBN_TYPE_C = "C"; // 자사
	public static final String VNDR_GBN_TYPE_V = "V"; // 입점사

	// 협력게시판 작성자 구분
	public static final String INQRY_GBN_TYPE_ADMIN = "A"; // ABCMart 관리자
	public static final String INQRY_GBN_TYPE_CALLCENTER = "C"; // 콜센터
	public static final String INQRY_GBN_TYPE_VENDOR = "V"; // 업체 관리자

	// 공통 휴일 코드
	public static final String HOLIDAY_COMMON = Config.getString("holiday.common");
	// 시스템 휴일 코드
	public static final String HOLIDAY_SYSTEM = Config.getString("holiday.standard");
	// 디폴트 휴일 코드
	public static final String HOLIDAY_DEFAULT = Config.getString("holiday.default");

	// 회원이력 필드 정보
	public static final String MEMBER_CHANGE_HISTORY_FIELDS = Config.getString("member.change.history.fields");
	// 회원기본정보
	public static final String MEMBER_CHANGE_HISTORY_BASIC_FIELDS = Config
			.getString("member.change.history.basic.fields");
	// 회원부가정보
	public static final String MEMBER_CHANGE_HISTORY_ADD_FIELDS = Config.getString("member.change.history.add.fields");

	// 사용자 권한 구분 - BO/PO
	public static final String AUTH_APPLY_SYSTEM_TYPE_BO = "B"; // BO 권한
	public static final String AUTH_APPLY_SYSTEM_TYPE_PO = "P"; // PO 권한

	public static final String[] HEADER_NAMES_FOR_CLIENT_IP = { "X-Forwarded-For", "Proxy-Client-IP",
			"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };

	// 사이트 구분
	public static final String SITE_NO_ART = "10000";
	public static final String SITE_NO_OTS = "10001";

	// 채널 구분
	public static final String CHNNL_NO_ART = "10000"; // ART
	public static final String CHNNL_NO_ABCMART = "10001"; // ABCMART
	public static final String CHNNL_NO_GRANDSTAGE = "10002"; // GRANDSTAGE
	public static final String CHNNL_NO_KIDS = "10004"; // KIDS
	public static final String CHNNL_NO_OTS = "10003"; // OTS

	// 채널 구분 Text
	public static final String CHNNL_TEXT_ART = "A-RT";
	public static final String CHNNL_TEXT_ABCMART = "ABC-MART";
	public static final String CHNNL_TEXT_GRANDSTAGE = "GRAND STAGE";
	public static final String CHNNL_TEXT_OTS = "ON THE SPOT";
	public static final String CHNNL_TEXT_KIDS = "ABC KIKDS";

	// 쿠폰 최대발급 수량 3
	public static final String COUPON_ISSUE_THREE = Config.getString("coupon.issue.three");
	// 쿠폰 최대발급 수량 5
	public static final String COUPON_ISSUE_FIVE = Config.getString("coupon.issue.five");

	// 전시 코너명 타입
	public static final String DISPLAY_CORNER_NAME_TEXT = "T";
	public static final String DISPLAY_CORNER_NAME_IMAGE = "I";

	// 이메일 발신자 정보
	public static final String SYS_SENDER_EMAIL_ADDRESS = Config.getString("sys.sender.email.address");
	public static final String SYS_SENDER_EMAIL_NAME = Config.getString("sys.sender.email.name");

	// 메세지 발신자 정보
	public static final String SYS_SENDER_MESSAGE_NUMBER = Config.getString("sys.sender.message.number");
	public static final String SYS_SENDER_MESSAGE_NAME = Config.getString("sys.sender.message.name");

	// OTS 용
	public static final String SYS_OTS_SENDER_MESSAGE_NUMBER = Config.getString("sys.ots.sender.message.number");
	public static final String SYS_OTS_SENDER_MESSAGE_NAME = Config.getString("sys.ots.sender.message.name");

	// app push 발송구분
	public static final String PUSH_SEND_GBN_TYPE_TEST = "T";
	public static final String PUSH_SEND_GBN_TYPE_REGULAR = "R";

	// 발송이력 상태 구분
	public static final String SEND_STAT_TYPE_SUCCESS = "S";
	public static final String SEND_STAT_TYPE_FAIL = "F";

	// 권한 적용 시스템
	public static final String AUTH_APPLY_SYSTEM_PO = "P";
	public static final String AUTH_APPLY_SYSTEM_BO = "B";

	// 스윗트래커 키값
	public static final String SWEETTRACKER_KEY_VALUE = Config.getString("sweet.dlvy.key");

	// 접근허용 아이피 체크 여부
	public static final String IS_CHECKED_ACCESS_IP = Config.getString("sys.access.ip.code");

	// 대시보드용 건수, 금액
	public static final String DASH_ORDER_CNT = "CNT";
	public static final String DASH_ORDER_AMT = "AMT";

	// 사이트 채널
	public static final String SITE_CHNNL_ART = "10000";
	public static final String SITE_CHNNL_ABC = "10001";
	public static final String SITE_CHNNL_GS = "10002";
	public static final String SITE_CHNNL_OTS = "10003";
	public static final String SITE_CHNNL_KIDS = "10004";

	// 채널별 카테고리 최대 LEVEL
	public static final int MAX_CTGR_LEVEL_ART = 4;
	public static final int MAX_CTGR_LEVEL_ABC = 2;
	public static final int MAX_CTGR_LEVEL_GS = 3;
	public static final int MAX_CTGR_LEVEL_OTS = 2;
	public static final int MAX_CTGR_LEVEL_KIDS = 2;

	/**
	 * **********************************************************************************************
	 * 이미지 서버 절대 경로 목록 S
	 * *************************************************************************
	 * 1.작성 형식
	 * **************************************************************************
	 * 1-1 : IMG_SRC_사용처 영문명
	 * ****************************************************************************
	 */
	public final static String IMG_SRC_AFTERSERVICE = "/afterservice/"; // AS이미지 업로드
	public final static String IMG_SRC_MEMBERCOUNSEL = "/membercounsel/"; // 1:1 문의 이미지 업로드
	public final static String IMG_SRC_EDITOR = "/editor/"; // Editor 이미지 업로드
	public final static String IMG_SRC_APP_PUSH = "/promotion/apppush/"; // appPush 이미지 업로드

	public final static String IMG_SRC_DISPLAY_ACONNECT = "/display/aconnect"; // A-connect 이미지 업로드
	public final static String IMG_SRC_DISPLAY_CONTENTS = "/display/contents"; // 전시 코너 이미지 업로드
	public final static String IMG_SRC_DISPLAY_WEBZINE = "/display/webzine"; // 전시 웹진 이미지 업로드
	public final static String IMG_SRC_DISPLAY_TEMPLATE = "/display/template"; // 전시 템플릿 이미지 업로드
	public final static String IMG_SRC_DISPLAY_POPUP = "/display/popup"; // 팝업 이미지 업로드
	public final static String IMG_SRC_DISPLAY_STORE = "/display/store"; // store 이미지 업로드

	public final static String IMG_SRC_PROMOTION_EVENT = "/promotion/event"; // event 이미지 업로드
	public final static String IMG_SRC_PROMOTION_CARDBENEFIT = "/promotion/cardbenefit"; // 카드사 혜택 이미지 업로드
	public final static String IMG_SRC_PROMOTION_PLANNINGDISPLAY = "/promotion/planningdisplay"; // 기획전 이미지 업로드

	public final static String IMG_SRC_PRODUCT_BRAND = "/product/brand"; // 브랜드 이미지 업로드
	public final static String IMG_SRC_PRODUCT_GIFTCARD = "/product/giftCard"; // 기프트카드 이미지 업로드
	public final static String IMG_SRC_PRODUCT_ICON = "/product/icon"; // 아이콘 이미지 업로드
	public final static String IMG_SRC_PRODUCT_SIZECHART = "/product/sizechart"; // 사이즈차트 이미지 업로드

	// 사은품 이미지 업로드 ex) /freegift/2019/11
	public final static String IMG_SRC_PRODUCT_FREEGIFT_PREFIX = "freegift";
	// 상품 이미지 업로드 ex) /product/2019/11
	public final static String IMG_SRC_PRODUCT_PRODUCT_PREFIX = "product";
	// 브랜드비주얼 이미지 업로드 ex) /product/brand
	public final static String IMG_SRC_PRODUCT_BRAND_PREFIX = "product/brand";
	// 상품전체공지 이미지 업로드 ex) /product_all_notice/201911
	public final static String IMG_SRC_PRODUCT_ALL_NOTICE_PREFIX = "product_all_notice";
	// 상품상세 이미지 업로드 ex) /product_detail/201911
	public final static String IMG_SRC_PRODUCT_DETAIL_PREFIX = "product_detail";

	// 채널 로고 이미지 업로드
	public final static String IMG_SRC_SYSTEM_SITE_LOGO = "system/site";

	/**
	 * **************** 이미지 서버 절대 경로 목록 E * ***************************************
	 */

	// KCP 결제 기취소 코드
	public static final String KCP_ALRDY_CANCEL_CODE_CARD = "8133";
	public static final String KCP_ALRDY_CANCEL_CODE_PART = "8150";
	public static final String KCP_ALRDY_CANCEL_CODE_ACCOUNT = "8729";
	public static final String KCP_ALRDY_CANCEL_CODE_HANDPHONE = "8233";
	
	// NAVER 결제 기취소 코드
	public static final String NAVER_ALRDY_CANCEL_CODE = "AlreadyCanceled";
	
	// KAKAO 결제 기취소 코드
	public static final String KAKAO_ALRDY_CANCEL_CODE = "-711";
	
	// 회원 관리자 메세지 발송시
	public static final String ADMIN_SEND_MSG_TO_MEMBER = "관리자전송";
	
}
