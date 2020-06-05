<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	(function() {
		var _consts = abc.object.createNestedObject(window,"abc.biz.claim.consts");
		
		/**
		 * 클레임 구분 코드
		 */
		_consts.CLM_GBN_CODE_CANCEL		= "${CommonCode.CLM_GBN_CODE_CANCEL}"; // 취소
		_consts.CLM_GBN_CODE_EXCHANGE	= "${CommonCode.CLM_GBN_CODE_EXCHANGE}"; // 교환
		_consts.CLM_GBN_CODE_RETURN		= "${CommonCode.CLM_GBN_CODE_RETURN}"; // 반품
		
		/**
		 * 회원 유형 코드
		 */
		_consts.MEMBER_TYPE_CODE		= "${CommonCode.MEMBER_TYPE_CODE}";		 // 회원 유형 코드 이름
		_consts.MEMBER_TYPE_ONLINE		= "${CommonCode.MEMBER_TYPE_ONLINE}";	 // 일반 회원
		_consts.MEMBER_TYPE_MEMBERSHIP	= "${CommonCode.MEMBER_TYPE_MEMBERSHIP}"; // 멤버쉽 회원
		_consts.MEMBER_TYPE_NONMEMBER	= "${CommonCode.MEMBER_TYPE_NONMEMBER}";  // 비 회원
		
		/**
		 * 클레임 불가 사유 코드(임시)
		 */
		_consts.CLM_IMPSBLT_RSN_CODE_WRONG_DLVY		= "10000" // 상품 오배송
		_consts.CLM_IMPSBLT_RSN_CODE_PRDT_DEFECT	= "10001" // 상품 불량
		_consts.CLM_IMPSBLT_RSN_CODE_DISCUSS_IMPSBL	= "10002" // 심의 불가
		
		/**
		 * 택배사 코드
		 */
		_consts.LOGIS_VNDR_CODE_DEAHAN	= "10000"; // CJ대한통운
		_consts.LOGIS_VNDR_CODE_POST	= "10001"; // 우체국택배
		
	})();
</script>