/**
 * 
 */
package kr.co.abcmart.bo.cmm.vo;

import java.io.Serializable;

import kr.co.abcmart.common.request.FileUpload;
import lombok.Data;

/**
 * @Desc :
 * @FileName : EditorImageVo.java
 * @Project : abc.bo
 * @Date : 2019. 7. 2.
 * @Author : Kimyounghyun
 */
@Data
public class EditorImageVo implements Serializable {

	private static final long serialVersionUID = 8568418078464749497L;

	private FileUpload upload;
	private String CKEditorFuncNum;
	private String imageUrl;
	private boolean fromPromotion;

}
