package kr.co.abcmart.bo.display.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseDpChnnlSearchWord extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 채널검색어순번
     */
	private java.lang.Long chnnlSrchWordSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 사이트번호
     */
	private String siteNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 채널번호
     */
	private String chnnlNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 검색어
     */
	private String srchWordText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 연결유형
     */
	private String cnnctnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 연결URL
     */
	private String cnnctnUrl;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 전시여부
     */
	private String dispYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정렬순서
     */
	private java.lang.Integer sortSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록자번호
     */
	private String rgsterNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록일시
     */
	private java.sql.Timestamp rgstDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수정자번호
     */
	private String moderNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수정일시
     */
	private java.sql.Timestamp modDtm;
	
}
