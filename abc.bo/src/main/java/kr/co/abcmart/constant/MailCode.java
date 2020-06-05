/**
 * 
 */
package kr.co.abcmart.constant;

/**
 * @Desc : BO, Backend PJT에서 사용함으로 상수선언은 BaseMailCode에 한다.
 * @FileName : MailCode.java
 * @Project : abc.bo
 * @Date : 2019. 3. 25.
 * @Author : Kimyounghyun
 */
public class MailCode extends kr.co.abcmart.common.constant.BaseMailCode {

	// 심의/수선 접수와 동시에 접수완료 - BO접수 - 이메일ID
	public static final String MAIL_ID_AS_ACCEPT_BY_ADMIN_ART = "EC-03009-1";
	public static final String MAIL_ID_AS_ACCEPT_BY_ADMIN_OTS = "EC-03009-2";

	// 수선 배송 처리 - 이메일ID
	public static final String MAIL_ID_AS_REPAIR_DLVY_PROC_ART = "EC-03010-1";
	public static final String MAIL_ID_AS_REPAIR_DLVY_PROC_OTS = "EC-03010-2";

	// 수선 불가 반송 - 이메일ID
	public static final String MAIL_ID_AS_REPAIR_IMPSBLT_SEND_BACK_ART = "EC-03006-1";
	public static final String MAIL_ID_AS_REPAIR_IMPSBLT_SEND_BACK_OTS = "EC-03006-2";

	// 주문 취소 - 메일ID
	public static final String MAIL_ID_ORDER_CANCEL_ART = "EC-01002-1";
	public static final String MAIL_ID_ORDER_CANCEL_OTS = "EC-01002-2";

	// 교환 접수(자사, 입점 상품 공통) - BO접수 - 메일 ID
	public static final String MAIL_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_ART = "EC-03001-1";
	public static final String MAIL_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_OTS = "EC-03001-2";

	// 교환 접수완료(자사, 입점 상품 공통) - 수거지시 - 메일 ID
	public static final String MAIL_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_ART = "EC-03007-1";
	public static final String MAIL_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_OTS = "EC-03007-2";

	// 교환 상품 발송완료 - 메일 ID
	public static final String MAIL_ID_EXCHANGE_DLVY_COMPLETE_ART = "EC-03004-1";
	public static final String MAIL_ID_EXCHANGE_DLVY_COMPLETE_OTS = "EC-03004-2";

	// 반품 접수(자사, 입점 상품 공통) - BO접수 - 메일 ID
	public static final String MAIL_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_ART = "EC-03002-1";
	public static final String MAIL_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_OTS = "EC-03002-2";

	// 반품 접수완료(자사, 입점 상품 공통) - 수거지시 - 메일 ID
	public static final String MAIL_ID_RETURN_CLAIM_ACCEPT_COMPLETE_ART = "EC-03008-1";
	public static final String MAIL_ID_RETURN_CLAIM_ACCEPT_COMPLETE_OTS = "EC-03008-2";

	// 반품처리 완료 - 메일 ID
	public static final String MAIL_ID_RETURN_PROC_COMPLETE_ART = "EC-03005-1";
	public static final String MAIL_ID_RETURN_PROC_COMPLETE_OTS = "EC-03005-2";

}
