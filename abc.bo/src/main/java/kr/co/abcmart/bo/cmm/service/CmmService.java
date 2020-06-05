package kr.co.abcmart.bo.cmm.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.exception.UnSupportedContentTypeException;
import kr.co.abcmart.bo.cmm.vo.EditorImageVo;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmmService {

	private static String EDITOR_FILE_PATH = UtilsText.concat(Const.UPLOAD_FILE_PATH, Const.IMG_SRC_EDITOR,
			UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

	/**
	 * @Desc : 에디터 이미지 저장(sftp 업로드) 처리
	 * @Method Name : imageUpload
	 * @Date : 2019. 7. 30.
	 * @Author : Kimyounghyun
	 * @param editorImageVo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> imageUpload(EditorImageVo editorImageVo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		FileUpload fileUpload = editorImageVo.getUpload();

		String contentType = fileUpload.getContentType();
		if (!contentType.contains("image")) {
			throw new UnSupportedContentTypeException("이미지형식만 업로드 할 수 있습니다.");
		}

		String fileName = fileUpload.getOrgFileName();
		String uploadFileName = "";
		if(editorImageVo.isFromPromotion()){
			uploadFileName = fileName;
		}else {
			uploadFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
					FilenameUtils.getExtension(fileName));
		}

		try {
			UtilsSftp.upload(EDITOR_FILE_PATH, uploadFileName, fileUpload.getMultiPartFile().getInputStream());
			editorImageVo.setImageUrl(UtilsText.concat(Const.URL_IMG_HTTPS, EDITOR_FILE_PATH, "/", uploadFileName));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("파일저장에 실패하였습니다.");
		}

		result.put("uploaded", 1);
		result.put("fileName", uploadFileName);
		result.put("url", editorImageVo.getImageUrl());

		return result;
	}

}
