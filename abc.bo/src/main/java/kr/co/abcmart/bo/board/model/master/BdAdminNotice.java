package kr.co.abcmart.bo.board.model.master;

import java.util.List;

import kr.co.abcmart.bo.board.model.master.base.BaseBdAdminNotice;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class BdAdminNotice extends BaseBdAdminNotice {
	private String loginId;

	private String adminName;

	private Integer notcAtchFileCnt;

	private List<String> fileNameArray;

	private String nowTime;

	private int[] removeAtchFileSeq;

	private FileUpload[] upload_file;

	private BdAdminNoticeAttachFile[] attachFiles;

	private String[] vndrNo;

	private String vndrNoForDashboard;

	private String moderId;

	private String moderName;

	// 미리 보기 여부
	private String preViewYN;

	// 메인팝업 호출여부
	private boolean mainPopup;

	public String getDispRegstName() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(this.getLoginId(), Const.L_PAREN, this.getAdminName(), Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.loginId(this.getLoginId()), Const.L_PAREN,
					UtilsMasking.userName(this.getAdminName()), Const.R_PAREN);
		}
	}

	public String getDispModerName() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(this.getModerId(), Const.L_PAREN, this.getModerName(), Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.loginId(this.getModerId()), Const.L_PAREN,
					UtilsMasking.userName(this.getModerName()), Const.R_PAREN);
		}
	}

	public String getListDispRegstName() {
		return UtilsText.concat(UtilsMasking.loginId(this.getLoginId()), Const.L_PAREN,
				UtilsMasking.userName(this.getAdminName()), Const.R_PAREN);
	}
}