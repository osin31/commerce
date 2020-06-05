package kr.co.abcmart.common.request;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.abcmart.common.exception.FileMimeTypeMismatchException;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsFile;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUpload {

	private MultipartFile multiPartFile;

	private String fileName;

	private int index;

	public boolean isFileItem() {
		return (getSize() > 0);
	}

	public FileUpload(MultipartFile mif) {
		this.multiPartFile = mif;
	}

	public MultipartFile getMultiPartFile() {
		return multiPartFile;
	}

	public String getParameterName() {
		return multiPartFile.getName();
	}

	public String getOrgFileName() {
		String fileName = multiPartFile.getOriginalFilename();
		fileName = FilenameUtils.getName(fileName);

		return fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public long getSize() {
		return multiPartFile.getSize();
	}

	public String getContentType() {
		return multiPartFile.getContentType();
	}

	/**
	 * 파일 확장자 가져오기
	 * 
	 * @return String
	 */
	public String getExt() {

		String orgFileName = getOrgFileName();

		if (orgFileName != null) {
			if (orgFileName.endsWith(".tar.gz")) {
				return "tar.gz";
			} else if (orgFileName.endsWith(".tar.bz2")) {
				return "tar.bz2";
			} else {
				orgFileName = orgFileName.toLowerCase();
			}
		}

		return FilenameUtils.getExtension(orgFileName);
	}

	/**
	 * 업로드 파일 복사
	 * 
	 * @param path 파일 경로가 지정된 파일 객체
	 * @throws Exception
	 */
	public void transferTo(File path) throws Exception {
		multiPartFile.transferTo(path);
	}

	/**
	 * 업로드 파일 복사
	 * 
	 * @param path 파일 경로
	 * @throws Exception
	 */
	public void transferTo(String path) throws Exception {
		transferTo(new File(path));
	}

	/**
	 * 업로드 파일 복사
	 * 
	 * @param path     파일 경로
	 * @param fileName 파일 이름
	 * @throws Exception
	 */
	public void transferTo(String path, String fileName) throws Exception {
		transferTo(path, fileName, true);
	}

	/**
	 * 업로드 파일 복사
	 * 
	 * @param path     파일 경로
	 * @param fileName 파일 이름
	 * @param 없는       폴더 강제 생성
	 * @throws Exception
	 */
	public void transferTo(String path, String fileName, boolean mkdir) throws Exception {

		if (mkdir) {

			File pathFile = new File(path);
			if (!pathFile.isDirectory()) {
				pathFile.mkdirs();
			}
		}

		UtilsFile.copyInputStreamToFile(multiPartFile.getInputStream(),
				new File(UtilsText.concat(path, File.separator, fileName)));
	}

	/***
	 * 업로드 파일 확장자를 검증 하고 exception을 상황에 따라 처리 한다. 예외 처리 하지 않고 단순히 파일 형식 검증만 할 경우
	 * isAllowExtAndMimeType() 메소드를 이용한다. 파일 확장자 및 파일 mime type 까지 검사한다.
	 * 
	 * 
	 * @param exts
	 * @throws FileExistsException
	 * @throws FileMimeTypeMismatchException
	 */
	public void allowExtAndMimeType(String... exts) throws FileExistsException, FileMimeTypeMismatchException {

		String uploadFileExt = getExt();
		boolean isMimeType = true;

		if (!UtilsArray.contains(exts, uploadFileExt)) {
			throw new FileExistsException(
					"File extension mismatch. [file extention : ".concat(uploadFileExt).concat("]"));
		}

		for (String ext : exts) {

			String mimeType = MimeType.getMimeTypeAsFileExtention(ext);
			UtilsArray.contains(exts, uploadFileExt);

			if ((!UtilsText.isEmpty(mimeType) && !mimeType.equals(multiPartFile.getContentType()))) {
				isMimeType = false;
				break;
			}
		}

		if (isMimeType) {
			throw new FileMimeTypeMismatchException(
					"File mime type mismatch. [mime type : ".concat(multiPartFile.getContentType()).concat("]"));
		}
	}

	/***
	 * 업로드 파일 확장자를 검증 한다. 파일 확장자 및 파일 mime type 까지 검사한다.
	 * 
	 * @param exts
	 * @throws FileExistsException
	 * @throws FileMimeTypeMismatchException
	 */
	public boolean isAllowExtAndMimeType(String... exts) {

		boolean isAllow = true;
		try {
			allowExtAndMimeType(exts);
		} catch (Exception e) {
			e.printStackTrace();
			isAllow = false;
		}

		return isAllow;
	}

	/**
	 * @Desc : 파일 확장자를 지정한 확장자로 변경하여 저장
	 * @Method Name : transferWithConvertImage
	 * @Date : 2019. 3. 21.
	 * @Author : tennessee
	 * @param path                  저장경로
	 * @param givenFileName         저장할 파일 이름. ex) product-image
	 * @param convertImageExtension 변환할 파일 확장자. ex) jpg, png
	 * @param mkdir                 디렉토리 생성 여부
	 * @throws Exception
	 */
	public void transferWithConvertImage(String path, String givenFileName, String convertImageExtension)
			throws Exception {

		// 이미지 객체로 변환
		BufferedImage beforeBufferedImage = ImageIO.read(multiPartFile.getInputStream());
		BufferedImage convertedBufferedImage = new BufferedImage(beforeBufferedImage.getWidth(),
				beforeBufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		convertedBufferedImage.createGraphics().drawImage(beforeBufferedImage, 0, 0, Color.WHITE, null);

		// inputStream 획득
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(convertedBufferedImage, convertImageExtension, outputStream);
		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		String fileNameWithExtension = givenFileName + "." + convertImageExtension;

		if (UtilsText.isNotBlank(path)) {
			File pathFile = new File(path);
			if (!pathFile.isDirectory()) {
				pathFile.mkdirs();
			}
		}

		UtilsFile.copyInputStreamToFile(inputStream,
				new File(UtilsText.concat(path, File.separator, fileNameWithExtension)));
	}

}
