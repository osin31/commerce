package kr.co.abcmart.bo.product.vo;

import kr.co.abcmart.common.request.FileUpload;
import lombok.Data;

/**
 * @Desc : 에디터 파일 업로드 객체
 * @FileName : PdProductEditorUploadVO.java
 * @Project : abc.bo
 * @Date : 2019. 8. 7.
 * @Author : tennessee
 */
@Data
public class PdProductEditorUploadVO {

	/** 파일객체 */
	private FileUpload upload;

}
