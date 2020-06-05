package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyAuthorityMenu;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyAuthorityMenu extends BaseSyAuthorityMenu {

	private static final long serialVersionUID = 8757941144710640029L;

	/***
	 * 리소스 URL Pattern
	 */
	private String rsrcUrl;

	/***
	 * 리소스 형식 : HttpMethod -> POST,GET
	 */
	private String rsrcType;

	/***
	 * 리소스 URL 순서 정렬. 해당 값에 따라 URL권한 체크 순서가 바뀐다.
	 */
	private int sortSeq;

	private String status;
	private String haveChild;
	private String level;
	private String authApplySystemType;
	private String menuGbnType;
	private String menuName;
	private int useYnCode;

}
