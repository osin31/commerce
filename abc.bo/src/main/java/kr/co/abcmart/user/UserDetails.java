package kr.co.abcmart.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsArray;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetails extends BaseBean implements org.springframework.security.core.userdetails.UserDetails {

	private static final long serialVersionUID = -5457867512353558248L;

	private boolean isLogin;
	private String loginId;
	private String password;
	private String name;
	private String salt;
	private String adminNo;
	private List<SyAdminAuthority> adminAuthorities;
	private String vndrNo;
	private short vndrMngrNo;
	private String storeNo;
	private String telNoText;
	private String hdphnNoText;
	private String emailAddrText;
	private String memberInfoMgmtYn;
	private java.sql.Timestamp lastLoginDtm;
	private String lastLoginIpText;
	private java.lang.Short loginFailCount;
	private java.sql.Timestamp pswdChngDtm;
	private String pswdInitYn;
	private String condtnTermValue;
	private String inqryGbnType;
	private String upAuthNo;
	private String authApplySystemType;
	private String longUnusedYn;

	public UserDetails(SyAdmin syAdmin) {
		setSyAdmin(syAdmin);
	}

	public void setSyAdmin(SyAdmin syAdmin) {

		// 값이 들어오지 않으면 false 처리 한다.
		if (syAdmin == null) {
			this.isLogin = false;
			syAdmin = new SyAdmin();
		} else {
			this.isLogin = true;
		}

		this.adminNo = syAdmin.getAdminNo();
		this.loginId = syAdmin.getLoginId();
		this.password = syAdmin.getPswdText();
		this.name = syAdmin.getAdminName();
		this.salt = syAdmin.getPswdSaltText();
		this.adminAuthorities = syAdmin.getAdminAuthorities();
		this.loginFailCount = syAdmin.getLoginFailCount();
		this.hdphnNoText = syAdmin.getHdphnNoText();
		this.memberInfoMgmtYn = syAdmin.getMemberInfoMgmtYn();
	}

	/***
	 * 사용자 권한 등록
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		if (isLogin()) {

			if (!UtilsArray.isEmpty(getAdminAuthorities())) {

				for (SyAdminAuthority syAdminAuthority : getAdminAuthorities()) {
					authorities.add(new SimpleGrantedAuthority(syAdminAuthority.getAuthNo()));
				}
			}

			authorities.add(new SimpleGrantedAuthority(Const.IS_AUTHENTICATED_FULLY));
		} else {
			authorities.add(new SimpleGrantedAuthority(Const.IS_AUTHENTICATED_ANONYMOUSLY));
		}
		return authorities;

	}

	/**
	 * 로그인 여부 설정 로그인이 false 이면 UserDetails필드값을 초기화 한다.
	 *
	 * @param isLogin
	 */
	public void setLogin(boolean isLogin) {

		if (isLogin) {
			this.isLogin = isLogin;
		} else {
			setSyAdmin(null);
		}
	}

	public boolean isLogin() {
		return isLogin;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getSalt() {
		return salt;
	}

	public List<SyAdminAuthority> getAdminAuthorities() {
		return adminAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	public void setUsername(String name) {
		this.name = name;
	}

	public String getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}

	/**
	 * 계정 만료 여부
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 계정 잠김 여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 암호 만료 여부
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 계정 사용 여부
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 로그인 처리 이후 비밀번호,slat 값 필요 없으니 지움
	 */
	public void eraseCredentials() {
		this.password = null;
		this.salt = null;
	}

	public java.sql.Timestamp getLastLoginDtm() {
		return lastLoginDtm;
	}

	public void setLastLoginDtm(java.sql.Timestamp lastLoginDtm) {
		this.lastLoginDtm = lastLoginDtm;
	}

	public String getLastLoginIpText() {
		return lastLoginIpText;
	}

	public void setLastLoginIpText(String lastLoginIpText) {
		this.lastLoginIpText = lastLoginIpText;
	}

	public String getVndrNo() {
		return vndrNo;
	}

	public void setVndrNo(String vndrNo) {
		this.vndrNo = vndrNo;
	}

	public short getVndrMngrNo() {
		return vndrMngrNo;
	}

	public void setVndrMngrNo(short vndrMngrNo) {
		this.vndrMngrNo = vndrMngrNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getTelNoText() {
		return telNoText;
	}

	public void setTelNoText(String telNoText) {
		this.telNoText = telNoText;
	}

	public String getHdphnNoText() {
		return hdphnNoText;
	}

	public void setHdphnNoText(String hdphnNoText) {
		this.hdphnNoText = hdphnNoText;
	}

	public String getEmailAddrText() {
		return emailAddrText;
	}

	public void setEmailAddrText(String emailAddrText) {
		this.emailAddrText = emailAddrText;
	}

	public String getMemberInfoMgmtYn() {
		return memberInfoMgmtYn;
	}

	public void setMemberInfoMgmtYn(String memberInfoMgmtYn) {
		this.memberInfoMgmtYn = memberInfoMgmtYn;
	}

	public java.lang.Short getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(java.lang.Short loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public java.sql.Timestamp getPswdChngDtm() {
		return pswdChngDtm;
	}

	public void setPswdChngDtm(java.sql.Timestamp pswdChngDtm) {
		this.pswdChngDtm = pswdChngDtm;
	}

	public String getPswdInitYn() {
		return pswdInitYn;
	}

	public void setPswdInitYn(String pswdInitYn) {
		this.pswdInitYn = pswdInitYn;
	}

	public String getCondtnTermValue() {
		return condtnTermValue;
	}

	public void setCondtnTermValue(String condtnTermValue) {
		this.condtnTermValue = condtnTermValue;
	}

	public String getInqryGbnType() {
		return inqryGbnType;
	}

	public void setInqryGbnType(String inqryGbnType) {
		this.inqryGbnType = inqryGbnType;
	}

	public String getUpAuthNo() {
		return upAuthNo;
	}

	public void setUpAuthNo(String upAuthNo) {
		this.upAuthNo = upAuthNo;
	}

	public String getAuthApplySystemType() {
		return authApplySystemType;
	}

	public void setAuthApplySystemType(String authApplySystemType) {
		this.authApplySystemType = authApplySystemType;
	}

	public String getLongUnusedYn() {
		return longUnusedYn;
	}

	public void setLongUnusedYn(String longUnusedYn) {
		this.longUnusedYn = longUnusedYn;
	}

}
