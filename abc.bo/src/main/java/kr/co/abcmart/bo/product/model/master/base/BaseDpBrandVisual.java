package kr.co.abcmart.bo.product.model.master.base;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class BaseDpBrandVisual extends BaseBean implements Serializable {

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드번호
	 */
	private String brandNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드스타일순번
	 */
	private java.lang.Integer brandBannerSeq;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드스타일명
	 */
	private String titleText;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드스타일명
	 */
	private String imageName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드스타일명
	 */
	private String imagePathText;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드스타일명
	 */
	private String imageUrl;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드스타일명
	 */
	private String altrnText;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 전시여부
	 */
	private String dispYn;

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
