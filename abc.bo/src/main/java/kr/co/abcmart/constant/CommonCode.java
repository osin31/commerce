/**
 *
 */
package kr.co.abcmart.constant;

/**
 * @Desc :
 * @FileName : CommonCode.java
 * @Project : abc.bo
 * @Date : 2019. 3. 22.
 * @Author : Kimyounghyun
 */
public class CommonCode extends kr.co.abcmart.common.constant.BaseCommonCode {

	public static final String DUP_LOGIN_ID = "dupLoginId";

	// 1:1문의 답변 스크립트 코드
	public static final String ASWR_CNSL_TYPE_CODE = "ASWR_CNSL_TYPE_CODE";
	public static final String ASWR_CNSL_TYPE_DTL_CODE = "ASWR_CNSL_TYPE_DTL_CODE";

	// 상품 매장 등급 코드
	public static final String TIER_FLAG_CODE = "TIER_FLAG_CODE";
	
	// 상품 대기조회 처리구분 코드
	public static final String PROC_GUBN_CODE = "PROC_GUBN_CODE";
	public static final String PROC_GUBN_CODE_NOTDEL_CONFIRM = "10000"; //미출확정
	public static final String PROC_GUBN_CODE_REDELIVERY = "10001";		//재배송
	public static final String PROC_GUBN_CODE_CHECK_REQUIRE = "10002";	//확인필요
	

}
