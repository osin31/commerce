package kr.co.abcmart.bo.board.model.master;

import java.util.List;

import kr.co.abcmart.bo.board.model.master.base.BaseBdCorprBoard;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdCorprBoard extends BaseBdCorprBoard {

	private String aswrStatName;
	private java.sql.Timestamp aswrDtm;
	private String searchDateKey;
	private String searchKey;
	private String searchValue;
	private String inqryName;
	private String inqryLoginId;
	private String aswrName;
	private String aswrLoginId;
	private String toDate;
	private String fromDate;
	private String vndrName;
	private String atchFileGbnType;
	private String atchFileName;
	private String inqryGbn;
	private String inqrySubject;
	private String aswrSubject;
	private String authApplySystemType;

	/**
	 * 대시보드 건수 조회용
	 */
	private int totalCnt;

	private Integer notcAtchFileCnt;
	private List<String> fileNameArray;
	private int[] removeAtchFileSeq;
	private FileUpload[] upload_file;

	private BdCorprBoardAttachFile[] attachFiles;
	private List<String> corprBoardSeqArr;

	/**
	 * 
	 * @Desc : 작성자 정보(마스킹)
	 * @Method Name : getMaskingRsterInfo
	 * @Date : 2019. 3. 13.
	 * @Author : ansuk
	 * @return
	 */
	public String getMaskingInqryInfo() {
		String rsterInfo = "";
		if (null != this.inqryLoginId || null != this.inqryName) {
			rsterInfo = UtilsText.concat(UtilsMasking.loginId(this.inqryLoginId), Const.L_PAREN,
					UtilsMasking.userName(this.inqryName), Const.R_PAREN);
		}
		return rsterInfo;
	}

	/**
	 * 
	 * @Desc : 답변자 정보(마스킹)
	 * @Method Name : getMaskingAswrInfo
	 * @Date : 2019. 3. 13.
	 * @Author : ansuk
	 * @return
	 */
	public String getMaskingAswrInfo() {
		String rsterInfo = "";
		if (null != this.aswrLoginId || null != this.aswrName) {
			rsterInfo = UtilsText.concat(UtilsMasking.loginId(this.aswrLoginId), Const.L_PAREN,
					UtilsMasking.userName(this.aswrName), Const.R_PAREN);
		}
		return rsterInfo;
	}

}
