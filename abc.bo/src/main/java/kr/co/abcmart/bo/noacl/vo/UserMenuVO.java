package kr.co.abcmart.bo.noacl.vo;

import java.io.Serializable;
import java.util.List;

import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import lombok.Data;

@Data
public class UserMenuVO implements Serializable {

	private static final long serialVersionUID = 5685785598264916015L;

	private String menuNo;

	private String upMenuNo;

	private String menuGbnType;

	private String menuName;

	private String menuUrl;

	private String allPathMenuNo;

	private String menuGbnUpMenuNo;

	private String adminNo;

	private List<SyAdminAuthority> adminAuthorities;

	private String authMenuRedisKey;

	private int sortSeq;

	private int haveChild;

	private String authApplySystemType;

}
