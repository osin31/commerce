package kr.co.abcmart.bo.system.vo;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString 
public enum HistoryFieldsName {
	defalut("defalut",""),
	authName("authName","권한 그룹명"),
	authApplySystemType("authApplySystemType","권한적용시스템"),
	useYn("useYn","사용여부"),
	adminName("adminName", "관리자명"),
	memberInfoMgmtYn("memberInfoMgmtYn", "개인정보 취급여부"),
	siteNo("siteName", "문의내역"),
	accessIpText("accessIpText", "접근허용아이피"),
	pswdText("pswdText", "비밀번호");
	

	private String code;
	private String desc;

	private HistoryFieldsName( String code, String desc ) {
		this.code = code;
		this.desc = desc;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public static HistoryFieldsName getByCode( String code ) {
		for ( HistoryFieldsName type : values()) {
			if( type.code.equals( code ) ) {
				return type;
			}
		}
		return HistoryFieldsName.defalut;
	//	throw new IllegalArgumentException( "HistoryFieldsName No Data Found.." );
	}
}
