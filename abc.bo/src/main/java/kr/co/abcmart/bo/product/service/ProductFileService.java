package kr.co.abcmart.bo.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsSftp;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 파일 서비스
 * @FileName : ProductFileService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 14.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductFileService {

	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_FILE_PATH = "filePath";
	public static final String KEY_FILE_URL = "fileUrl";

	/** 이미지 포멧 변경 확장자 */
	private final String FORCE_CONVERT_IMAGE_FILE_EXTENSION = "jpg";

	private final String CDN_API_DOMAIN = "https://openapi.kr.cdnetworks.com";
	private final String CDN_API_METHOD = "/purge/rest/doPurge?";
	private final String ABC_USER = "abcmartmobile@gmail.com";
	private final String ABC_PASS = "Abcmart!2019";
	private final String ABC_PAD = Config.getString("url.images.domain");

	/**
	 * @Desc : 파일 단일객체 저장 처리
	 * @Method Name : uploadFile
	 * @Date : 2019. 2. 18.
	 * @Author : tennessee
	 * @param uploadFile
	 * @param pathList
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> uploadFile(FileUpload uploadFile, List<String> pathList) throws Exception {
		Map<String, String> result = null;

		if (uploadFile != null && uploadFile.isFileItem()) {
			StringBuffer filePath = new StringBuffer();

			if (pathList != null) {
				pathList.forEach(path -> {
					filePath.append(UtilsText.concat("/", path));
				});
				filePath.insert(0, "art");
			}

//			String fileName = uploadFile.getOrgFileName();
//			String modifiedFileName = UtilsText.concat(String.valueOf(System.currentTimeMillis()), ".",
//					FilenameUtils.getExtension(fileName));
			String modifiedFileName = UtilsText.concat(this.createFileName(), ".",
					FilenameUtils.getExtension(uploadFile.getOrgFileName()));
			String filePathWithFileName = filePath.toString() + "/" + modifiedFileName;
			String fileUrlWithDomain = UtilsText.concat(Const.URL_IMG_HTTPS, "/", filePath.toString(), "/",
					modifiedFileName);

			try {
				UtilsSftp.upload(filePath.toString(), modifiedFileName, uploadFile.getMultiPartFile().getInputStream());
			} catch (Exception e) {
				log.error("{}", e);
				throw new Exception("파일저장에 실패하였습니다.");
			}

			result = new HashMap<String, String>();
			result.put(KEY_FILE_NAME, uploadFile.getOrgFileName());
			result.put(KEY_FILE_PATH, filePathWithFileName);
			result.put(KEY_FILE_URL, fileUrlWithDomain);

			// 이미지 캐시서버 초기화
			this.initCache(filePathWithFileName);
		} else {
			log.warn("파일 데이터가이 존재 하지 않습니다.");
		}

		return result;
	}

	/**
	 * @Desc : 파일 목록 객체 저장 처리
	 * @Method Name : uploadFiles
	 * @Date : 2019. 2. 18.
	 * @Author : tennessee
	 * @param uploadFiles
	 * @param pathList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> uploadFiles(List<FileUpload> uploadFiles, List<String> pathList) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		if (uploadFiles != null) {
			for (FileUpload uploadFile : uploadFiles) {
				result.add(this.uploadFile(uploadFile, pathList));
			}
		} else {
			log.warn("파일 데이터가 존재 하지 않습니다.");
		}

		return result;
	}

	/**
	 * @Desc : 주어진 이름으로 파일을 저장
	 * @Method Name : uploadFileGivenName
	 * @Date : 2019. 3. 21.
	 * @Author : tennessee
	 * @param uploadFile
	 * @param pathList
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> uploadFileGivenName(FileUpload uploadFile, List<String> pathList,
			String givenFileNameWithoutExtension) throws Exception {
		Map<String, String> result = null;

		if (uploadFile != null && uploadFile.isFileItem()) {
			StringBuffer filePath = new StringBuffer();

			if (pathList != null) {
				pathList.forEach(path -> {
					filePath.append(UtilsText.concat("/", path));
				});
				filePath.insert(0, "art");
			}

			String newFileNameWithExtension = UtilsText.concat(givenFileNameWithoutExtension, ".",
					FORCE_CONVERT_IMAGE_FILE_EXTENSION);

			uploadFile.transferWithConvertImage(UtilsText.concat(filePath.toString()), givenFileNameWithoutExtension,
					FORCE_CONVERT_IMAGE_FILE_EXTENSION);

			result = new HashMap<String, String>();
			// 실제 파일명 (이미지 포멧 변환됨)
			result.put(KEY_FILE_NAME, newFileNameWithExtension);
			// 등록된 파일경로 및 파일명
			result.put(KEY_FILE_PATH, UtilsText.concat(filePath.toString(), "/", newFileNameWithExtension));
			// 도메인이 제거된 파일URL
			result.put(KEY_FILE_URL,
					UtilsText.concat(Const.URL_IMG_HTTPS, "/", filePath.toString(), "/", newFileNameWithExtension));
		} else {
			log.warn("파일 데이터가이 존재 하지 않습니다.");
		}

		return result;
	}

	public boolean deleteUploadedFile(String path) {
		return true;
	}

	/**
	 * @Desc : 시간 정보가 포함된 파일명 반환. ex) image_110203050.jpg
	 * @Method Name : getFileName
	 * @Date : 2019. 2. 18.
	 * @Author : tennessee
	 * @param uploadFile
	 * @return
	 */
	@Deprecated
	private String getFileNameWithTimeInfo(FileUpload uploadFile) {
		return UtilsText.concat(uploadFile.getOrgFileName().split("\\.")[0], "_" + System.currentTimeMillis(), ".",
				uploadFile.getExt());
	}

	/**
	 * @Desc : 임의파일명 생성. 임의숫자5자리와 현재밀리초로 구성됨. ex) 00216_1565154933912
	 * @Method Name : createFileName
	 * @Date : 2019. 8. 7.
	 * @Author : tennessee
	 * @return
	 */
	private String createFileName() {
		return UtilsText.concat(this.createRandomNumber(), "_", String.valueOf(System.currentTimeMillis()));
	}

	/**
	 * @Desc : 임의숫자 5자리 생성 ex) 19850, 00216
	 * @Method Name : makeRandomNumber
	 * @Date : 2019. 8. 7.
	 * @Author : tennessee
	 * @return
	 */
	private String createRandomNumber() {
		int min = 1;
		int max = 99999;
		return String.format("%05d", ((int) (Math.random() * max) + min));
	}

	/**
	 * @Desc : CDN 캐시 초기화
	 * @Method Name : initCache
	 * @Date : 2019. 8. 30.
	 * @Author : tennessee
	 * @param path
	 * @throws Exception
	 */
	private void initCache(String path) throws Exception {
		if (UtilsText.isNotBlank(path)) {
			if (!path.startsWith("/")) {
				path = UtilsText.concat("/", path);
			}
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();

		String apiUrl = UtilsText.concat(CDN_API_DOMAIN, CDN_API_METHOD);
		String pathType = "item"; // 해당 대상만 flush.
		String param = UtilsText.concat("user=", ABC_USER, "&pass=", ABC_PASS, "&pad=", ABC_PAD, "&type=", pathType,
				"&path=", path, "&output=", "json");

		HttpGet httpGet = new HttpGet(UtilsText.concat(apiUrl, param));
		httpGet.addHeader("User-Agent", "Mozila/5.0");
		httpGet.addHeader("Content-type", "application/json");

		CloseableHttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			log.debug("CDN PURGE API INFO : {}", UtilsText.concat(apiUrl, param));
			log.debug("CDN PURGE API RESULT : {}", EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
		} catch (Exception e) {
			log.error("CDN PURGE API ERROR : {}", e);
		} finally {
			httpClient.close();

		}
	}

}
