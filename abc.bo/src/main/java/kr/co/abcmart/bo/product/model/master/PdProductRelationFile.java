package kr.co.abcmart.bo.product.model.master;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductRelationFile;
import kr.co.abcmart.bo.product.service.ProductFileService;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품관련파일
 * @FileName : PdProductRelationFile.java
 * @Project : abc.bo
 * @Date : 2019. 3. 28.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductRelationFile extends BasePdProductRelationFile
		implements Validator, Comparable<PdProductRelationFile> {

	/** 파일유형. 이미지 */
	public static final String FILE_TYPE_IMAGE = "I";
	/** 파일유형. 동영상 */
	public static final String FILE_TYPE_MEDIA = "M";
	/** 파일유형. 3D */
	public static final String FILE_TYPE_3D = "D";

	/** 첨부이미지파일 */
	@JsonIgnore
	private FileUpload uploadFileImage;
	/** 첨부이미지파일명 */
	private String uploadFileNameImage = "";
	/** 첨부동영상파일 */
	@JsonIgnore
	private FileUpload uploadFileVideo;
	/** 첨부동영상파일명 */
	private String uploadFileNameVideo = "";

	/** 허용가능한 확장자 - 이미지 */
	private static final String[] ALLOW_EXTENSION_IMAGE_TYPE_1 = { "jpg", "jpeg", "png", "gif", "bmp" };
	/** 허용가능한 확장자 - 동영상 */
	private static final String[] ALLOW_EXTENSION_VIDEO_TYPE_1 = { "mp4", "webm" };

	@Override
	public void validate() throws ValidatorException {
		if (UtilsObject.isEmpty(this.getPrdtRltnFileSeq()) || this.getPrdtRltnFileSeq().compareTo(0) <= 0) {
//			this.validationMessage("product.valid.product.relationFile.prdtRltnFileSeq"); // 상품관련파일순번
			validationFieldMessage("product.valid.product.relationFile.prdtRltnFileSeq",
					"productRelationFile.uploadFileImage");
		}
		if (UtilsText.isBlank(this.getDispPostnType())) {
//			this.validationMessage("product.valid.product.relationFile.dispPostnType"); // 전시위치
			validationFieldMessage("product.valid.product.relationFile.dispPostnType",
					"productRelationFile.uploadFileImage");
		}
		if (UtilsObject.isNotEmpty(this.getPrdtRltnFileSeq()) && this.getPrdtRltnFileSeq().compareTo(1) == 0) {
			// 대표이미지
			this.validateFileTypeImageForRepresentation();
		} else if (this.isInput()) {
			if (UtilsText.isNotBlank(this.getFileType())) {
				// 화면입력필드가 입력된 경우 검증 수행

				switch (this.getFileType()) {
				case FILE_TYPE_IMAGE:
					this.validateFileTypeImage();
					break;
				case FILE_TYPE_MEDIA:
					this.validateFileTypeMedia();
					break;
				case FILE_TYPE_3D:
					this.validateFileType3d();
					break;
				default:
//					this.validationMessage("product.valid.product.relationFile.fileType"); // 파일유형
					validationFieldMessage("product.valid.product.relationFile.fileType",
							"productRelationFile.uploadFileImage");
				}
			} else {
//				this.validationMessage("product.valid.product.relationFile.fileType"); // 파일유형
				validationFieldMessage("product.valid.product.relationFile.fileType",
						"productRelationFile.uploadFileImage");
			}
		}
	}

	/**
	 * @Desc : 대표이미지 검증
	 * @Method Name : validateFileTypeImageForRepresentation
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 */
	private void validateFileTypeImageForRepresentation() {
		// 이미지
		if (this.isAttachedFile(this.getUploadFileImage())) {
			// 파일이 첨부된 경우. (신규등록 또는 수정등록)
			if (!this.getUploadFileImage().isAllowExtAndMimeType(ALLOW_EXTENSION_IMAGE_TYPE_1)) {
//				this.validationMessage(
//						"product.valid.product.relationFile.image.representation.uploadFileImage.extension");
				validationFieldMessage(
						"product.valid.product.relationFile.image.representation.uploadFileImage.extension",
						"productRelationFile.uploadFileImage");
			}
		} else if (UtilsText.isBlank(this.getUploadFileNameImage())) {
			// 파일이 첨부되지 않았고, 기존에 등록된 이미지명이 없는 경우
//			this.validationMessage("product.valid.product.relationFile.image.representation.uploadFileImage");
			validationFieldMessage("product.valid.product.relationFile.image.representation.uploadFileImage",
					"productRelationFile.uploadFileImage");
		}
		// 첨부이미지 대체텍스트
		if (UtilsText.isNotBlank(this.getAltrnText())) {
			if (this.getAltrnText().length() > 33) {
//				this.validationMessage("product.valid.product.relationFile.image.representation.altrnText.length");
				validationFieldMessage("product.valid.product.relationFile.image.representation.altrnText.length",
						"productRelationFile.uploadFileImage");
			}
		}
		// 정렬순서
		if (UtilsObject.isEmpty(this.getSortSeq()) || this.getSortSeq().compareTo(0) <= 0) {
//			this.validationMessage("product.valid.product.relationFile.image.representation.sortSeq");
			validationFieldMessage("product.valid.product.relationFile.image.representation.sortSeq",
					"productRelationFile.uploadFileImage");
		}
	}

	/**
	 * @Desc : 이미지 유형 검증
	 * @Method Name : validateFileTypeImage
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	private void validateFileTypeImage() {
		// 이미지
		if (this.isUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())) {
			if (this.isAttachedFile(this.getUploadFileImage())) {
				if (!this.getUploadFileImage().isAllowExtAndMimeType(ALLOW_EXTENSION_IMAGE_TYPE_1)) {
//					this.validationMessage("product.valid.product.relationFile.image.uploadFileImage.extension");
					validationFieldMessage("product.valid.product.relationFile.image.uploadFileImage.extension",
							"productRelationFile.uploadFileImage");
				}
			}
		} else {
//			this.validationMessage("product.valid.product.relationFile.image.uploadFileImage");
			validationFieldMessage("product.valid.product.relationFile.image.uploadFileImage",
					"productRelationFile.uploadFileImage");
		}
		// 첨부이미지 대체텍스트
		if (UtilsText.isNotBlank(this.getAltrnText())) {
			if (this.getAltrnText().length() > 33) {
//			this.validationMessage("product.valid.product.relationFile.image.altrnText.length");
				validationFieldMessage("product.valid.product.relationFile.image.altrnText.length",
						"productRelationFile.uploadFileImage");
			}
		}
		// 정렬순서
		if (UtilsObject.isEmpty(this.getSortSeq()) || this.getSortSeq().compareTo(0) <= 0) {
//			this.validationMessage("product.valid.product.relationFile.image.sortSeq");
			validationFieldMessage("product.valid.product.relationFile.image.sortSeq",
					"productRelationFile.uploadFileImage");
		}
	}

	/**
	 * @Desc : 미디어유형 검증
	 * @Method Name : validateFileTypeMedia
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	private void validateFileTypeMedia() {
		// 유형
		if (UtilsText.isBlank(this.getUploadYn())) {
//			this.validationMessage("product.valid.product.relationFile.media.url.uploadYn");
			validationFieldMessage("product.valid.product.relationFile.media.url.uploadYn",
					"productRelationFile.uploadFileImage");
		}
		switch (this.getUploadYn()) {
		case Const.BOOLEAN_FALSE:
			// 효과이미지 중 URL 유형인 경우
			// 이미지
			if (this.isUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())) {
				// 해당 영역이 사용되었는지 확인
				if (this.isAttachedFile(this.getUploadFileImage())) {
					// 이미지가 첨부되었으므로, 파일 검증 수행.
					if (!this.getUploadFileImage().isAllowExtAndMimeType(ALLOW_EXTENSION_IMAGE_TYPE_1)) {
//						this.validationMessage(
//								"product.valid.product.relationFile.media.url.uploadFileImage.extension");
						validationFieldMessage("product.valid.product.relationFile.media.url.uploadFileImage.extension",
								"productRelationFile.uploadFileImage");
					}
				}
			} else {
//				this.validationMessage("product.valid.product.relationFile.media.url.uploadFileImage");
				validationFieldMessage("product.valid.product.relationFile.media.url.uploadFileImage",
						"productRelationFile.uploadFileImage");
			}
			// 동영상명
			if (UtilsText.isEmpty(this.getLinkInfo())) {
//				this.validationMessage("product.valid.product.relationFile.media.url.movieName");
				validationFieldMessage("product.valid.product.relationFile.media.url.movieName",
						"productRelationFile.linkInfo");
			} else if (this.getLinkInfo().length() > 166) {
//				this.validationMessage("product.valid.product.relationFile.media.url.movieName.length");
				validationFieldMessage("product.valid.product.relationFile.media.url.movieName.length",
						"productRelationFile.linkInfo");
			}
			// 동영상경로
			if (UtilsText.isEmpty(this.getMovieUrl())) {
//				this.validationMessage("product.valid.product.relationFile.media.url.movieUrl");
				validationFieldMessage("product.valid.product.relationFile.media.url.movieUrl",
						"productRelationFile.movieUrl");
			} else if (UtilsText.getByteLength(getMovieUrl()) > 180) {
//				this.validationMessage("product.valid.product.relationFile.media.url.movieUrl.length");
				validationFieldMessage("product.valid.product.relationFile.media.url.movieUrl.length",
						"productRelationFile.movieUrl");
			}
			// 정렬순서
			if (UtilsObject.isEmpty(this.getSortSeq()) || this.getSortSeq().compareTo(0) <= 0) {
//				this.validationMessage("product.valid.product.relationFile.media.url.sortSeq");
				validationFieldMessage("product.valid.product.relationFile.media.url.sortSeq",
						"productRelationFile.sortSeq");
			}
			break;
		case Const.BOOLEAN_TRUE:
			// 효과이미지 중 직접업로드인 경우
			// 동영상
			if (this.isUseFileField(this.getUploadFileVideo(), this.getUploadFileNameVideo())) {
				// 해당 영역이 사용되었는지 확인
				if (this.isAttachedFile(this.getUploadFileVideo())) {
					// 이미지가 첨부되었으므로, 파일 검증 수행.
					if (!this.getUploadFileVideo().isAllowExtAndMimeType(ALLOW_EXTENSION_VIDEO_TYPE_1)) {
//						this.validationMessage(
//								"product.valid.product.relationFile.media.upload.uploadFileVideo.extension");
						validationFieldMessage(
								"product.valid.product.relationFile.media.upload.uploadFileVideo.extension",
								"productRelationFile.uploadFileVideo");
					}
				}
			} else {
//				this.validationMessage("product.valid.product.relationFile.media.upload.uploadFileVideo");
				validationFieldMessage("product.valid.product.relationFile.media.upload.uploadFileVideo",
						"productRelationFile.uploadFileVideo");

			}
			// 미리보기이미지
			if (this.isUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())) {
				// 해당 영역이 사용되었는지 확인
				if (this.isAttachedFile(this.getUploadFileImage())) {
					// 이미지가 첨부되었으므로, 파일 검증 수행.
					if (!this.getUploadFileImage().isAllowExtAndMimeType(ALLOW_EXTENSION_IMAGE_TYPE_1)) {
//						this.validationMessage(
//								"product.valid.product.relationFile.media.upload.uploadFileImage.extension");
						validationFieldMessage(
								"product.valid.product.relationFile.media.upload.uploadFileImage.extension",
								"productRelationFile.uploadFileImage");
					}
				}
			} else {
//				this.validationMessage("product.valid.product.relationFile.media.upload.uploadFileImage");
				validationFieldMessage("product.valid.product.relationFile.media.upload.uploadFileImage",
						"productRelationFile.uploadFileImage");
			}
			// 동영상명
			if (UtilsText.isBlank(this.getLinkInfo())) {
//				this.validationMessage("product.valid.product.relationFile.media.url.movieName");
				validationFieldMessage("product.valid.product.relationFile.media.url.movieName",
						"productRelationFile.linkInfo");
			} else if (this.getLinkInfo().length() > 166) {
//				this.validationMessage("product.valid.product.relationFile.media.url.movieName.length");
				validationFieldMessage("product.valid.product.relationFile.media.url.movieName.length",
						"productRelationFile.linkInfo");
			}
			// 정렬순서
			if (UtilsObject.isEmpty(this.getSortSeq()) || this.getSortSeq().compareTo(0) <= 0) {
//				this.validationMessage("product.valid.product.relationFile.media.upload.sortSeq");
				validationFieldMessage("product.valid.product.relationFile.media.upload.sortSeq",
						"productRelationFile.sortSeq");
			}
			break;
		}
	}

	/**
	 * @Desc : 3D유형 검증
	 * @Method Name : validateFileType3d
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	private void validateFileType3d() {
		// 3D 관련 항목이 모두 입력안된경우
		// 3D 제목
		if (UtilsText.isBlank(this.getImageName())) {
//			this.validationMessage("product.valid.product.relationFile.3d.imageName");
			validationFieldMessage("product.valid.product.relationFile.3d.imageName", "productRelationFile.imageName");
		} else if (this.getImageName().length() > 33) {
//			this.validationMessage("product.valid.product.relationFile.3d.imageName.length");
			validationFieldMessage("product.valid.product.relationFile.3d.imageName.length",
					"productRelationFile.imageName");
		}
		// 3D URL
		if (UtilsText.isBlank(this.getLinkInfo())) {
//			this.validationMessage("product.valid.product.relationFile.3d.linkInfo");
			validationFieldMessage("product.valid.product.relationFile.3d.linkInfo", "productRelationFile.linkInfo");
		} else if (this.getLinkInfo().length() > 166) {
//			this.validationMessage("product.valid.product.relationFile.3d.linkInfo.length");
			validationFieldMessage("product.valid.product.relationFile.3d.linkInfo.length",
					"productRelationFile.linkInfo");
		}
		// 정렬순서
		if (UtilsObject.isEmpty(this.getSortSeq()) || this.getSortSeq().compareTo(0) <= 0) {
//			this.validationMessage("product.valid.product.relationFile.3d.sortSeq");
			validationFieldMessage("product.valid.product.relationFile.3d.sortSeq", "productRelationFile.sortSeq");
		}
	}

	/**
	 * @Desc : 입력필드 중 데이터가 입력되었는지 유무를 판단
	 * @Method Name : isInput
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 */
	public boolean isInput() {
		boolean result = false;

		if (UtilsText.isNotBlank(this.getFileType())) {
			switch (this.getFileType()) {
			case FILE_TYPE_IMAGE:
				result = this.isInputAboutFileTypeImage();
				break;
			case FILE_TYPE_MEDIA:
				result = this.isInputAboutFileTypeMedia();
				break;
			case FILE_TYPE_3D:
				result = this.isInputAboutFileType3d();
				break;
			}
		}
		return result;
	}

	/**
	 * @Desc : 이미지 유형 입력필드 검사. 하나라도 입력된 경우 참을 반환
	 * @Method Name : isInputAboutFileTypeImage
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isInputAboutFileTypeImage() {
		return !this.isNotInputAboutFileTypeImage();
	}

	/**
	 * @Desc : 이미지 유형 입력필드 검사. 모두 입력되지 않은 경우 참을 반환
	 * @Method Name : isNotInputAboutFileTypeImage
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isNotInputAboutFileTypeImage() {
		boolean result = false;
		if (this.isNotUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())
				&& (UtilsText.isBlank(this.getAltrnText()) && UtilsObject.isEmpty(this.getSortSeq()))) {
			// 첨부파일 없음
			result = true;
		}
		return result;
	}

	/**
	 * @Desc : 미디어 유형 입력필드 검사. 하나라도 입력된 경우 참을 반환
	 * @Method Name : isInputAboutFileTypeMedia
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isInputAboutFileTypeMedia() {
		return !this.isNotInputAboutFileTypeMedia();
	}

	/**
	 * @Desc : 미디어 유형 입력필드 검사. 모두 입력되지 않은 경우 참을 반환
	 * @Method Name : isNotInputAboutFileTypeMedia
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isNotInputAboutFileTypeMedia() {
		boolean result = false;
		switch (this.getUploadYn()) {
		case Const.BOOLEAN_FALSE:
			// 효과이미지 중 URL 유형인 경우
			if (this.isNotUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())
					&& UtilsText.isBlank(this.getLinkInfo()) && UtilsObject.isEmpty(this.getMovieUrl())
					&& UtilsObject.isEmpty(this.getSortSeq())) {
				result = true;
			}
			break;
		case Const.BOOLEAN_TRUE:
			// 효과이미지 중 직접업로드인 경우
			if (UtilsText.isBlank(this.getLinkInfo())
					&& this.isNotUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())
					&& this.isNotUseFileField(this.getUploadFileVideo(), this.getUploadFileNameVideo())
					&& UtilsObject.isEmpty(this.getSortSeq())) {
				result = true;
			}
			break;
		}
		return result;

	}

	/**
	 * @Desc : 첨부파일이 있는 경우 참을 반환
	 * @Method Name : isUseUploadFile
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 * @param uploadFile
	 * @return
	 */
	private boolean isAttachedFile(FileUpload uploadFile) {
		return UtilsObject.isNotEmpty(uploadFile) && uploadFile.isFileItem();
	}

	/**
	 * @Desc : 첨부파일이 없는 경우 참을 반환
	 * @Method Name : isNotUseUploadFile
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 * @param uploadFile
	 * @return
	 */
	private boolean isNotAttachedFile(FileUpload uploadFile) {
		return !this.isAttachedFile(uploadFile);
	}

	/**
	 * @Desc : 업로드파일 영역이 사용되었는지 체크. 사용하는 경우 참을 반환
	 * @Method Name : isUseUploadFile
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 * @param uploadFile
	 * @param uploadedFileName
	 * @return
	 */
	private boolean isUseFileField(FileUpload uploadFile, String uploadedFileName) {
		boolean result = false;

		if (this.isAttachedFile(uploadFile)) {
			result = true;
		} else if (UtilsText.isNotBlank(uploadedFileName)) {
			result = true;
		}
		return result;
	}

	/**
	 * @Desc : 업로드파일 영역이 사용되었는지 체크. 사용하지 않는 경우 참을 반환
	 * @Method Name : isNotUseUploadFile
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 * @param uploadFile
	 * @param uploadedFileName
	 * @return
	 */
	private boolean isNotUseFileField(FileUpload uploadFile, String uploadedFileName) {
		return !this.isUseFileField(uploadFile, uploadedFileName);
	}

	/**
	 * @Desc : 3D 유형 입력필드 값 검사. 하나라도 입력된 경우 참을 반환
	 * @Method Name : isInputAboutFileType3d
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isInputAboutFileType3d() {
		return !this.isNotInputAboutFileType3d();
	}

	/**
	 * @Desc : 3D 유형 입력필드 값 검사. 모두 입력되지 않은 경우 참을 반환
	 * @Method Name : isNotInputAboutFileType3d
	 * @Date : 2019. 4. 30.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isNotInputAboutFileType3d() {
		return UtilsText.isBlank(this.getImageName()) && UtilsText.isBlank(this.getLinkInfo())
				&& UtilsObject.isEmpty(this.getSortSeq());
	}

	/**
	 * @Desc : 첨부파일 경로 설정
	 * @Method Name : setUploadFilePath
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param uploadFileImage
	 * @param uploadFileVideo
	 */
	public void setUploadFilePath(Map<String, String> uploadFileImage, Map<String, String> uploadFileVideo) {
		// 업로드 이미지 경로 기록
		this.setUploadFilePathImage(uploadFileImage);
		// 업로드 동영상 경로 기록
		this.setUploadFilePathVideo(uploadFileVideo);
	}

	/**
	 * @Desc : 이미지 파일 경로 설정
	 * @Method Name : setImageFilePath
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param uploadFileImage
	 */
	public void setUploadFilePathImage(Map<String, String> uploadFileImage) {
		if (!UtilsObject.isEmpty(uploadFileImage)) {
			this.setImageName(uploadFileImage.get(ProductFileService.KEY_FILE_NAME));
			this.setImagePathText(uploadFileImage.get(ProductFileService.KEY_FILE_PATH));
			this.setImageUrl(uploadFileImage.get(ProductFileService.KEY_FILE_URL));

			if (UtilsText.isBlank(this.getAltrnText())) {
				// 대체텍스트가 없는 경우, 파일명으로 지정
				this.setAltrnText(uploadFileImage.get(ProductFileService.KEY_FILE_NAME));
			}
		}
	}

	/**
	 * @Desc : 비디오 파일 경로 설정
	 * @Method Name : setVideoFilePath
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param uploadFileVideo
	 */
	public void setUploadFilePathVideo(Map<String, String> uploadFileVideo) {
		if (!UtilsObject.isEmpty(uploadFileVideo)) {
			if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getUploadYn())) {
				// 직접업로드인 경우
				this.setMovieName(uploadFileVideo.get(ProductFileService.KEY_FILE_NAME));
				this.setMoviePathText(uploadFileVideo.get(ProductFileService.KEY_FILE_PATH));
				this.setMovieUrl(uploadFileVideo.get(ProductFileService.KEY_FILE_URL));
			}
		}
	}

	/**
	 * @Desc : 첨부파일 갱신 수행여부를 반환. 참인 경우 첨부파일 갱신 필요
	 * @Method Name : isFileUpdate
	 * @Date : 2019. 5. 8.
	 * @Author : tennessee
	 * @return
	 */
	public boolean isUseFile() {
		return !this.isNotUseFile();
	}

	/**
	 * @Desc : 첨부파일 갱신 수행여부를 반환. 참인 경우 첨부파일 갱신 불필요
	 * @Method Name : isNotFileUpdate
	 * @Date : 2019. 5. 8.
	 * @Author : tennessee
	 * @return
	 */
	public boolean isNotUseFile() {
		boolean result = false;

		if (UtilsText.isNotBlank(this.getFileType())) {
			switch (this.getFileType()) {
			case FILE_TYPE_IMAGE:
				result = this.isNotUseFileAboutFileTypeImage();
				break;
			case FILE_TYPE_MEDIA:
				result = this.isNotUseFileAboutFileTypeMedia();
				break;
			}
		}
		return result;
	}

	/**
	 * @Desc : 이미지 파일유형에 대한 첨부파일 갱신 수행여부를 반환. 참인 경우 첨부파일 갱신 불필요
	 * @Method Name : isNotFileUpdateAboutFileTypeImage
	 * @Date : 2019. 5. 8.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isNotUseFileAboutFileTypeImage() {
		boolean result = false;
		if (this.isNotUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())) {
			// 첨부파일 없음
			result = true;
		}
		return result;
	}

	/**
	 * @Desc : 동영상 파일유형에 대한 첨부파일 갱신 수행여부를 반환. 참인 경우 첨부파일 갱신 불필요
	 * @Method Name : isNotFileUpdateAboutFileTypeMedia
	 * @Date : 2019. 5. 8.
	 * @Author : tennessee
	 * @return
	 */
	private boolean isNotUseFileAboutFileTypeMedia() {
		boolean result = false;
		switch (this.getUploadYn()) {
		case Const.BOOLEAN_FALSE:
			// 효과이미지 중 URL 유형인 경우
			if (this.isNotUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())) {
				result = true;
			}
			break;
		case Const.BOOLEAN_TRUE:
			// 효과이미지 중 직접업로드인 경우
			if (UtilsText.isBlank(this.getMovieName())
					&& this.isNotUseFileField(this.getUploadFileImage(), this.getUploadFileNameImage())
					&& this.isNotUseFileField(this.getUploadFileVideo(), this.getUploadFileNameVideo())) {
				result = true;
			}
			break;
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("serial")
	@Override
	public int compareTo(PdProductRelationFile target) {
		Method[] methods = this.getClass().getMethods();
		List<String> compareMethodNames = new ArrayList<String>() {
			{
				// 상품번호. 비교대상 제외
				// 상품관련파일순번. 비교대상 제외
				// 전시위치. 비교대상 제외
				// 파일유형. 비교대상 제외
//				add("getImageName"); // 이미지명
//				add("getImagePathText"); // 이미지경로
				add("getImageUrl"); // 이미지URL
				add("getAltrnText"); // 대체텍스트
				// 연결유형. 비교대상 제외.
				// 링크Target. 비교대상 제외
				add("getLinkInfo"); // 링크정보
				add("getUploadYn"); // 업로드여부
//				add("getMovieName"); // 동영상명
//				add("getMoviePathText"); // 동영상경로
				add("getMovieUrl"); // 동영상URL
				add("getSortSeq"); // 정렬순번
			}
		};

		int compareCountTotal = compareMethodNames.size(); // 검사 대상 항목 총 갯수
		int compareCountEqual = 0; // 동일 항목 갯수

		for (String compareMethodName : compareMethodNames) {
			// 비교대상 메서드 목록 기준으로 검색
			for (Method method : methods) {
				if (UtilsText.equals(compareMethodName, method.getName())) {

					try {

						Object sourceValue = null;
						Object targetValue = null;

						sourceValue = method.invoke(this); // 소스 메서드 값 추출
						targetValue = method.invoke(target); // 타겟 메서드 값 추출

						if (UtilsObject.isEmpty(sourceValue) && UtilsObject.isEmpty(targetValue)) {
							// 동일함으로 판단
							compareCountEqual++;
						} else {
							if (sourceValue instanceof String) {
								if (UtilsText.equals((String) sourceValue, (String) targetValue)) {
									compareCountEqual++;
								}
							} else if (sourceValue instanceof Integer) {
								if (UtilsObject.isEmpty(sourceValue)) {
									if (UtilsObject.isEmpty(targetValue) || ((Integer) targetValue).intValue() == 0) {
										compareCountEqual++;
									}
								} else {
									if (((Integer) sourceValue).compareTo((Integer) targetValue) == 0) {
										compareCountEqual++;
									}
								}
							} else {
								log.error("알 수 없는 필드유형입니다. sourceMethod : {}\tsourceValue : {}\ttargetValue : {}",
										method.getName(), sourceValue, targetValue);
							}
						}

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						log.error("메서드 수행 중 오류 발생. {}", e.getMessage());
						log.error("{}", e);
					}
				}
			}
		}

		return compareCountTotal - compareCountEqual;
	}

}
