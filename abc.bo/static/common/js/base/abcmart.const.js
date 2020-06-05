/***
 * constant(공통) 함수 정의.
 *
 */
(function() {

	var _consts = abc.object.createNestedObject(window,"abc.consts");

	_consts.BOOLEAN_TRUE		= "Y";
	_consts.BOOLEAN_FALSE		= "N";

	// int type의 최대값
	_consts.INT_MAX_VALUE = 2147483647;


	/**
	 * S = 인증 성공
	 * F = 인증 실패
	 * D = 접근 금지
	 * U = 인증 필요
	 */
	_consts.AUTH_S				= "S";
	_consts.AUTH_F				= "F";
	_consts.AUTH_D				= "D";
	_consts.AUTH_U				= "U";

	_consts.CRUD_C				= "C";
	_consts.CRUD_U				= "U";
	_consts.CRUD_D				= "D";
	
	_consts.AUTH_TYPE_BO 		= "BO";
	_consts.AUTH_TYPE_PO 		= "PO";

	// 비회원 회원번호
	_consts.NON_MEMBER_NO		= "MB00000000";

	//Sample
	_consts.SAMPLE_CATEGORY_ADIDAS	= "A001";
	_consts.SAMPLE_CATEGORY_NIKE 	= "A002";

	//Member
	_consts.MEMBER_CATEGORY_ADIDAS 	= "A001";
	_consts.MEMBER_CATEGORY_NIKE 	= "A002";

	_consts.DEFAULT_DATETIME_PATTERN = "yyyy.MM.dd HH:mm:ss";

	//일부 "~" 표시가 붙어야하는 날짜에서 사용하기 위해 추가
	_consts.ADD_WAVE_DATETIME_PATTERN = "~ yyyy.MM.dd HH:mm:ss";

	// 비밀번호 변경 레이어 패스
	_consts.ADMIN_ID = ["sysdev", "sysvd"]; // "sysop"

	// 권한그룹 코드
	_consts.ROLE_ADMIN_GROUP = "ROLE_10000"; // 총괄관리자그룹코드
	_consts.ROLE_ADMIN = "ROLE_20000";
	_consts.ROLE_VENDER_GROUP = "ROLE_30000"; // 업체그룹코드
	_consts.ROLE_VENDER = "ROLE_40000";

	// 고객응대관리 첨부파일관련 상수
	_consts.MEMBER_COUNSEL_FILE_EXT = "jpg,jpeg,gif,png,bmp";
	_consts.MEMBER_COUNSEL_FILE_MAX_CNT = 3;
	_consts.MEMBER_COUNSEL_FILE_MAX_MB = 10;

	//고객응대관리 답변 상태 코드
	_consts.ASWR_STAT_CODE_NO_ASWR	="10000";
	_consts.ASWR_STAT_CODE_HOLD_ASWR="10001";
	_consts.ASWR_STAT_CODE_COM_ASWR	="10002";
	//고객응대관리 상담 구분 코드
	_consts.CNSL_GBN_CODE_INQRY	= "10000";
	_consts.CNSL_GBN_CODE_VOC	= "10001";
	_consts.CNSL_GBN_CODE_CALL	= "10002";

	//약관 구분 코드
	_consts.TERMS_TYPE_CODE_TERMSOFUSE	= "10000";
	_consts.TERMS_TYPE_CODE_PRIVACY		= "10001";
	_consts.TERMS_TYPE_CODE_SIGNUP		= "10002";
	_consts.TERMS_TYPE_CODE_ORDER		= "10003";

	// 회원 부가정보 코드
	_consts.MEMBER_ADDINFO_SHOES = "10000";
	_consts.MEMBER_ADDINFO_POINT = "10001";
	_consts.MEMBER_ADDINFO_REFUND = "10002";
	_consts.MEMBER_ADDINFO_STORE = "10003";
	_consts.MEMBER_ADDINFO_EMP = "10004";

	//회원 유형코드
	_consts.MEMBER_TYPE_ONLINE = "10000";
	_consts.MEMBER_TYPE_TOTAL = "10001";
	_consts.MEMBER_TYPE_NONMEMBER = "10002";

	// 마케팅 활용동의
	_consts.MEMBER_MARKETING_SMS = "SMS";
	_consts.MEMBER_MARKETING_EMAIL = "이메일";
	_consts.MEMBER_MARKETING_APP = "App Push";

	// 쿠폰 최대 발급 개수
	_consts.MAX_PAY_QTY = "3";

	_consts.INQRY_GBN_TYPE_ADMIN = "A";
	_consts.INQRY_GBN_TYPE_CALLCENTER = "C";
	_consts.INQRY_GBN_TYPE_VENDOR = "V";

	// 답변알림유형 M(문자), E(이메일), N(발송안함)
	_consts.ASWR_ALERT_TYPE_SMS = "M";
	_consts.ASWR_ALERT_TYPE_MAIL = "E";
	_consts.ASWR_ALERT_TYPE_NON = "N";

	//첨부파일 답변, 질문 구분
	_consts.ATCH_FILE_GBN_TYPE_ASWR = "A";
	_consts.ATCH_FILE_GBN_TYPE_INQRY = "Q";

	// 인증 경로 코드 (아이디찾기, 비밀번호찾기, Draw이벤트, 환불계좌, 회원가입, 이전회원전환, 맴버십전환, 고객인증번호 발송)
	_consts.CRTFC_PATH_CODE_ID_SEARCH = "10000";
	_consts.CRTFC_PATH_CODE_PW_SEARCH = "10001";
	_consts.CRTFC_PATH_CODE_DRAW_EVENT = "10002";
	_consts.CRTFC_PATH_CODE_REFUND_ACCOUNT = "10003";
	_consts.CRTFC_PATH_CODE_MEMBER_JOIN = "10004";
	_consts.CRTFC_PATH_CODE_MEMBER_CHANGE = "10005";
	_consts.CRTFC_PATH_CODE_MEMBERSHIP_CHANGE = "10006";
	_consts.CRTFC_PATH_CODE_CUSTOMER_CERT = "10007";

	// app push관리 (전체, 안드로이드, ios)
	_consts.PUSH_MESSAGE_OSCODE_ALL = "ALL";
	_consts.PUSH_MESSAGE_OSCODE_ANDROID = "10000";
	_consts.PUSH_MESSAGE_OSCODE_IOS = "10001";

	// app push 발송관리(즉시, 예약, 테스트, 일반)
	_consts.CM_PUSH_MESSAGE_SEND_TYPE_I = "I";
	_consts.CM_PUSH_MESSAGE_SEND_TYPE_R = "R";
	_consts.CM_PUSH_SEND_GBN_TYPE_TEST  = "T";
	_consts.CM_PUSH_SEND_GBN_TYPE_REGULAR="R";

	// app push 대상자 관리 {파일업로드, APP 설치 회원, 전체}
	_consts.PUSH_SEND_TRGT_CODE_FILEUPLOAD = "10000";
	_consts.PUSH_SEND_TRGT_CODE_DOWNLOAD_APP ="10001";
	_consts.PUSH_SEND_TRGT_CODE_ALL = "10002";

	// app push 진행상태(대기, 준비, 진행, 완료)
	_consts.PUSH_ING_STAT_CODE_READY = "10000";
	_consts.PUSH_ING_STAT_CODE_WAITTING = "10001";
	_consts.PUSH_ING_STAT_CODE_PROGRESS = "10002";
	_consts.PUSH_ING_STAT_CODE_SUCCESS = "10003";

	// app push 첨부파일 상수.
	_consts.PUSH_TRGT_MEMBER_FILE_EXT = "xlsx|xls|xlsm|xlsm";
	_consts.PUSH_TRGT_MEMBER_FILE_MAX_CNT = 1;
	_consts.PUSH_TRGT_MEMBER_FILE_MAX_MB = 10;

	// 일별 월별 년별 검색 타입
	_consts.SEARCH_DATE_TYPE_DAY = "day"; // 일별
	_consts.SEARCH_DATE_TYPE_MONTH = "month"; // 월별
	_consts.SEARCH_DATE_TYPE_YEAR = "year"; // 년별
	
	//sweetTracker 배송정보 조회주소
	_consts.SWEETTRACKER_URL = 'http://info.sweettracker.co.kr/tracking/1';
	
	// 자사 CJ 대한 택배사 배송조회 URL  
	_consts.CJ_DOORTODOOR_DLVY_URL = 'https://www.doortodoor.co.kr/m/sub/doortodoor.do?fsp_action=PARC_ACT_002&fsp_cmd=retrieveInvNoACTM&invc_no=';
	
	// 택배사 코드 CJ
	_consts.DLVY_CODE_CJ = '04';
	
	//사이트 채널 번호
	_consts.SITE_CHNN_ART = '10000';
	_consts.SITE_CHNN_ABC = '10001';
	_consts.SITE_CHNN_GS = '10002';
	_consts.SITE_CHNN_OTS = '10003';
	_consts.SITE_CHNN_KIDS = '10004';

	// 채널별 카테고리 최대 LEVEL
	_consts.MAX_CTGR_LEVEL_ART  = 4;
	_consts.MAX_CTGR_LEVEL_ABC  = 2;
	_consts.MAX_CTGR_LEVEL_GS   = 3;
	_consts.MAX_CTGR_LEVEL_OTS  = 2;
	_consts.MAX_CTGR_LEVEL_KIDS = 2;
	
	_consts.ADMIN_SEND_MSG_TO_MEMBER = "관리자전송";
	
})();