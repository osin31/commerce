package kr.co.abcmart.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import kr.co.abcmart.common.request.MimeType;

/**
 * 문자열을 조작 할 때 쓰이는 유틸 클래스
 * 
 * @author zerocooldog@zen9.co.kr
 */
public class UtilsFile extends FileUtils {

	/***
	 * 파일 명으로 mime type 가져오기
	 * 
	 * @param filename 파일 명
	 * @return string mime type
	 */
	public static String getMimeTypeAsFileName(String filename) {
		return MimeType.getMimeTypeAsFileName(filename);
	}

	/***
	 * 파일 확장자로 mime type 가져오기
	 * 
	 * @param ext 파일 확장자
	 * @return string mime type
	 */
	public static String getMimeTypeAsFileExtention(String ext) {
		return MimeType.getMimeTypeAsFileExtention(ext);
	}

	/***
	 * 파일 확장자 허용 여부
	 * 
	 * @param fileName 파일 이름 ex) aaa.gif,/sdf/sdf/ad.gif
	 * @return boolean
	 */
	public static boolean isAllowExtention(String fileName) {
		return MimeType.isAllowExtention(fileName);
	}

	/***
	 * 파일 확장자 및 mime type을 추가 한다.
	 * 
	 * @param fileName 파일 이름 ex) aaa.gif,/sdf/sdf/ad.gif
	 * @param mimeType ex) application/octet-stream,application/vnd.ms-excel
	 * @param force    강제로 추가 할것인지 유무. 파일 확장자가 이미 존재 할 경우 덮어 씌운다.
	 * @return boolean
	 */
	public static boolean isAllowExtAndMimeType(String fileName, File File) {
		return MimeType.isAllowExtAndMimeType(fileName, File);

	}

	/***
	 * 파일 확장자 및 mime type을 추가 한다.
	 * 
	 * @param ext      파일 확장자 ex) gif, bmp, png
	 * @param mimeType ex) application/octet-stream,application/vnd.ms-excel
	 * @return boolean
	 */
	public static boolean addAllowExtAndMimeType(String ext, String mimeType) {
		return addAllowExtAndMimeType(ext, mimeType, false);
	}

	/***
	 * 파일 확장자 및 mime type을 추가 한다.
	 * 
	 * @param ext      파일 확장자 ex) gif, bmp, png
	 * @param mimeType ex) application/octet-stream,application/vnd.ms-excel
	 * @param force    강제로 추가 할것인지 유무. 파일 확장자가 이미 존재 할 경우 덮어 씌운다.
	 * @return boolean
	 */
	public static boolean addAllowExtAndMimeType(String ext, String mimeType, boolean force) {
		return MimeType.addAllowExtAndMimeType(ext, mimeType, force);
	}

	/**
	 * @Desc : 사용자 업로드 디렉토리 반환
	 * @Method Name : getHashCodeDirectory
	 * @Date : 2019. 7. 31.
	 * @Author : Kimyounghyun
	 * @param fileName
	 * @return
	 */
	public static String getHashCodeDirectory(String fileName) {
		String hashCodeDirectory = new Integer((Math.abs(fileName.hashCode() % 1000)) + 1000).toString();

		return hashCodeDirectory;
	}

	/**
	 * @Desc : 사용자 업로드 파일명 반화
	 * @Method Name : getUserUploadFilename
	 * @Date : 2019. 7. 31.
	 * @Author : Kimyounghyun
	 * @return
	 */
	public static String getUserUploadFilename() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
}
