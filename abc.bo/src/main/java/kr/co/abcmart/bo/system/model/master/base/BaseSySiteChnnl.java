package kr.co.abcmart.bo.system.model.master.base;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class BaseSySiteChnnl extends BaseBean implements Serializable {

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 채널번호
	 */
	private String chnnlNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 사이트번호
	 */
	private String siteNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 채널명
	 */
	private String chnnlName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 정렬순번
	 */
	private java.lang.Integer sortSeq;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 판매가능여부
	 */
	private String sellPsbltYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 업체사용여부
	 */
	private String vndrUseYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 사용여부
	 */
	private String useYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 업체번호
	 */
	private String vndrNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 내부관리정보
	 */
	private String insdMgmtInfoText;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 채널상품 구분 번호
	 */
	private String chnnlPrdtGbnNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 검색영역노출여부
	 */
	private String srchRelmExpsrYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 등록자번호
	 */
	private String rgsterNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 등록일시
	 */
	private java.sql.Timestamp rgstDtm;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 수정자번호
	 */
	private String moderNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 수정일시
	 */
	private java.sql.Timestamp modDtm;

}
