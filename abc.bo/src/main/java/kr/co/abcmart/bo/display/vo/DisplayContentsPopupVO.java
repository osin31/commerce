package kr.co.abcmart.bo.display.vo;

import java.io.Serializable;

import kr.co.abcmart.common.request.FileUpload;
import lombok.Data;

@Data
public class DisplayContentsPopupVO implements Serializable {

	private String contTypeCode;

	private String contTypeCodeName;

	private String ctgrNo;

	private String dispTmplNo;

	private String dispPageNo;

	private String deviceTypeName;

	private String status;

	private java.lang.Integer dispTmplCornerSeq;

	private java.lang.Integer ctgrCornerNameSeq;

	private java.lang.Integer dispTmplCornerSetSeq;

	private java.lang.Integer contTypeSeq;

	private java.lang.Integer dispCornerNameSeq;

	private java.lang.Integer dispPageCornerNameSeq;

	private int[] delSeq;

	private FileUpload imageUpload;

	private FileUpload movieUpload;

	private String altrnText;
}
