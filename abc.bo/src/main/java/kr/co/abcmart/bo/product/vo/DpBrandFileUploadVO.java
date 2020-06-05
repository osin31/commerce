package kr.co.abcmart.bo.product.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DpBrandFileUploadVO implements Serializable {

	private String brandNo;

	private String contTypeCode;

	private String contTypeCodeName;

	private String deviceTypeName;

	private String status;

	private String sheetName;

}
