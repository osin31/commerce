package kr.co.abcmart.bo.board.model.master;

import java.util.List;

import kr.co.abcmart.bo.board.model.master.base.BaseBdMemberCounsel;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : BdMemberCounsel.java
 * @Project : abc.bo
 * @Date : 2019. 2. 12.
 * @Author : kiowa
 */
@Slf4j
@Data
public class BdMemberCounsel extends BaseBdMemberCounsel implements Validator {

	/**
	 * 답변상태
	 */
	private String aswrStatName;

	/**
	 * 문의유형(1depth > 2depth)
	 */
	private String cnslTypeName;

	/**
	 * 사이트명
	 */
	private String siteName;

	/**
	 * 업체명
	 */
	private String vndrName;

	/**
	 * 회원 정보 (로그인아이디(회원명))
	 */
	private String memberInfo;

	/**
	 * 답변자 정보(로그인아이디(답변자명))
	 */
	private String aswrInfo;

	/**
	 * 답변스크립트 상담유형 코드
	 */
	private String aswrCnslTypeCode;

	/**
	 * 답변스크립트 상담분류 코드
	 */
	private String aswrCnslTypeDtlCode;

	/**
	 * 답변스크립트 제목
	 */
	private String cnslScriptTitleText;

	// 업체 구분
	private String vndrGbnType;

	private String checkAtchFile;

	private String inquiryId;
	private String inquiryName;
	private String aswrId;
	private String aswrName;

	private String checkAswrDtm;

	private int[] removeAtchFileSeq;

	/**
	 * 상품명, 상품 정보
	 */
	private String prdtName;
	private String prdtColorCode;
	private String optnName;
	private String brandName;
	private String vndrPrdtNoText;
	private String mmnyPrdtYn;

	/**
	 * 문의 첨부 파일
	 */
	private List<BdMemberCounselAttachFile> inqryCounselAttachFiles;

	private FileUpload[] aswrUpLoadFile;

	/**
	 * 파일 업로드용
	 */
	@ParameterOption(target = "atchFileGbnType")
	private BdMemberCounselAttachFile[] aswrAtchFiles;

	/**
	 * 회원상세 문의 현황 용
	 */
	private int totalCnt;
	private int unasCnt;
	private int holdCnt;
	private int compCnt;
	private int onlineUnasCnt;
	private int onlineHoldCnt;
	private int offlineUnasCnt;
	private String viewType;
	private String memberTabInfo;
	private String rowNum;
	
	private String inqryDtmString;
	private String aswrDtmString;
	

	/**
	 * 답변 첨부 파일
	 */
	private List<BdMemberCounselAttachFile> aswrCounselAttachFiles;

	@Override
	public void validate() throws ValidatorException {
		if ("Y".equals(getVndrAssignYn()) && UtilsText.isBlank(getVndrNo())) {
			validationMessage("common.valid.variableisnull", Message.getMessage("cs.msg.vndrNo"));
		}
	}

	public String getMemberDetailInfo() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.inquiryId), Const.L_PAREN,
					UtilsMasking.userName(this.inquiryName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.inquiryId, Const.L_PAREN, this.inquiryName, Const.R_PAREN);
		}
		return maskingStr;
	}

	public String getAswrDetailInfo() {
		String maskingStr = "";
		if (this.aswrId == null || "".equals(this.aswrId)) {
			return "";
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.aswrId), Const.L_PAREN,
						UtilsMasking.userName(this.aswrName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.aswrId, Const.L_PAREN, this.aswrName, Const.R_PAREN);
			}
			return maskingStr;
		}
	}

	public String getMemberInfo() {
		return UtilsText.concat(UtilsMasking.loginId(this.inquiryId), Const.L_PAREN,
				UtilsMasking.userName(this.inquiryName), Const.R_PAREN);
	}

	public String getAswrInfo() {
		if (this.aswrId == null || "".equals(this.aswrId)) {
			return "";
		} else {
			return UtilsText.concat(UtilsMasking.loginId(this.aswrId), Const.L_PAREN,
					UtilsMasking.userName(this.aswrName), Const.R_PAREN);
		}
	}

}
