package kr.co.abcmart.bo.noacl.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @Desc : 파일 다운로드
 * @FileName : DownloadVO.java
 * @Project : abc.bo
 * @Date : 2019. 3. 6.
 * @Author : kiowa
 */
@Data
public class DownloadVO implements Serializable {

	private static final long serialVersionUID = 1862741944074691065L;

	/**
	 * 다운로드 파일 명
	 */
	private String downLoadFileName;

	/**
	 * 다운로드 파일 패스 + 저장 파일명
	 */
	private String atchFilePathText;

}
