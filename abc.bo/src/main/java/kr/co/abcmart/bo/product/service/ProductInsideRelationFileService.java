package kr.co.abcmart.bo.product.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductRelationFile;
import kr.co.abcmart.bo.product.repository.master.PdProductRelationFileDao;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 연관 파일 서비스
 * @FileName : ProductInsideRelationFileService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 14.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideRelationFileService {

	@Autowired
	private PdProductRelationFileDao productRelationFileDao;

	@Autowired
	private ProductFileService productFileService;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 *
	 * @Desc : 상품 연관 파일 등록
	 * @Method Name : registProductRelationFile
	 * @Date : 2019. 4. 26.
	 * @Author : tennessee
	 * @param product
	 * @param isNew
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		// 상품 이미지 등록

		Map<String, String> uploadFileImage = null;
		Map<String, String> uploadFileVideo = null;

		if (UtilsArray.isNotEmpty(product.getProductRelationFile())) {

			PdProductRelationFile criteria = new PdProductRelationFile();
			criteria.setPrdtNo(product.getPrdtNo());
			List<PdProductRelationFile> stored = this.productRelationFileDao.select(criteria);
			Integer maxSortSeq = this.getMaxSortSeqInList(product.getProductRelationFile());

			this.productRelationFileDao.delete3dByPrdtNo(product.getPrdtNo()); // 3D VIEW는 모두 삭제(재등록)

			for (PdProductRelationFile productRelationFile : product.getProductRelationFile()) {
				// 파일 업로드

				if (productRelationFile.isInput()) {
					uploadFileImage = null;
					uploadFileVideo = null;

					productRelationFile.setPrdtNo(product.getPrdtNo());

					switch (productRelationFile.getFileType()) {
					case PdProductRelationFile.FILE_TYPE_IMAGE:
						if (productRelationFile.isUseFile()) {
							uploadFileImage = this.productFileService.uploadFile(
									productRelationFile.getUploadFileImage(),
									Arrays.asList(Const.IMG_SRC_PRODUCT_PRODUCT_PREFIX, UtilsDate.today("yyyy"),
											UtilsDate.today("MM")));
							productRelationFile.setUploadFilePathImage(uploadFileImage); // 첨부파일 경로 설정

						}
						break;
					case PdProductRelationFile.FILE_TYPE_MEDIA:
						if (productRelationFile.isUseFile()) {
							uploadFileImage = this.productFileService.uploadFile(
									productRelationFile.getUploadFileImage(),
									Arrays.asList("product", UtilsDate.today("yyyy"), UtilsDate.today("MM")));
							uploadFileVideo = this.productFileService.uploadFile(
									productRelationFile.getUploadFileVideo(),
									Arrays.asList("product", UtilsDate.today("yyyy"), UtilsDate.today("MM")));
							productRelationFile.setUploadFilePath(uploadFileImage, uploadFileVideo); // 첨부파일 경로 설정
						}
						break;
					case PdProductRelationFile.FILE_TYPE_3D:
						productRelationFile.setImagePathText("");
						productRelationFile.setImageUrl("");
						productRelationFile.setAltrnText("");
						break;
					default:
						log.debug("알 수 없는 유형입니다. {}", productRelationFile.getFileType());
						throw new Exception("알 수 없는 유형입니다. " + productRelationFile.getFileType());
					}

					if (UtilsObject.isEmpty(productRelationFile.getSortSeq())) {
						// 노출순서가 없는 경우, 입력된 노출순서에서 제일 큰 숫자에서 1을 더함
						maxSortSeq = Integer.sum(maxSortSeq, 1);
						productRelationFile.setSortSeq(maxSortSeq);
					}

					if (UtilsCollection.isEmpty(stored)) {
						// 기존 첨부파일이 없는 경우
						this.productReflectionService.setUserInfo(productRelationFile); // 사용자 정보 입력
						this.productRelationFileDao.insert(productRelationFile);
					} else {
						// 기존 첨부파일이 있는 경우
						boolean found = false;

						for (PdProductRelationFile item : stored) {
							found = false; // flag init.

							if (Integer.compare(item.getPrdtRltnFileSeq(),
									productRelationFile.getPrdtRltnFileSeq()) == 0) {
								// 기존 등록된 파일이 있는 경우
								found = true;
								break;
							}
						}

						this.productReflectionService.setUserInfo(productRelationFile); // 사용자 정보 입력
						if (found) {
							// 기존 등록된 파일이 있는 경우
							if (productRelationFile.getPrdtRltnFileSeq() == 22
									|| productRelationFile.getPrdtRltnFileSeq() == 32) {
								if (UtilsText.equals(productRelationFile.getUploadYn(), Const.BOOLEAN_FALSE)) {
									productRelationFile.setMovieName("");
									productRelationFile.setMoviePathText("");
								}
							}

							this.productRelationFileDao.update(productRelationFile);
						} else {
							// 기존 등록된 파일이 없는 경우
							this.productRelationFileDao.insert(productRelationFile);
						}
					}
				}
			}
			// 상품관련파일순번 이외 파일 모두 삭제
			this.productRelationFileDao.deleteAnothersByPrdtNo(product.getPrdtNo(),
					Arrays.asList(product.getProductRelationFile()));
		} else {
			// 상품번호에 해당하는 모든 관련파일 삭제
			this.productRelationFileDao.deleteByPrdtNo(product.getPrdtNo());
		}
	}

	/**
	 * @Desc : 상품정보에 해당하는 연관파일정보를 조회
	 * @Method Name : searchProductRelationFileAndMovie
	 * @Date : 2019. 4. 5.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<PdProductRelationFile> searchProductRelationFile(String prdtNo) throws Exception {
		PdProductRelationFile criteria = new PdProductRelationFile();
		criteria.setPrdtNo(prdtNo);
		return this.productRelationFileDao.select(criteria);
	}

	/**
	 * @Desc : 상품정보에 해당하는 연관파일정보를 조회하여 상품객체 내에 설정
	 * @Method Name : setProductRelationFiles
	 * @Date : 2019. 4. 5.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setProductRelationFiles(PdProduct product) throws Exception {
		if (!UtilsObject.isEmpty(product) && UtilsText.isNotBlank(product.getPrdtNo())) {
			List<PdProductRelationFile> result = this.searchProductRelationFile(product.getPrdtNo());
			product.setProductRelationFile(result.toArray(new PdProductRelationFile[result.size()]));
		}
	}

	/**
	 * @Desc : 주어진 배열에서 정렬순서가 높은 값을 반환
	 * @Method Name : getMaxSortSeqInList
	 * @Date : 2019. 4. 26.
	 * @Author : tennessee
	 * @param productRelationFiles
	 * @return
	 * @throws Exception
	 */
	private Integer getMaxSortSeqInList(PdProductRelationFile[] productRelationFiles) throws Exception {
		Integer result = 0;

		if (UtilsArray.isNotEmpty(productRelationFiles)) {
			for (PdProductRelationFile item : productRelationFiles) {
				if (UtilsObject.isEmpty(item.getPrdtRltnFileSeq())) {
					continue;
				}
				if (UtilsObject.isNotEmpty(item.getSortSeq()) && item.getSortSeq().compareTo(result) == -1) {
					result = item.getSortSeq().intValue();
				}
			}
		}

		return result;
	}

	/**
	 * @Desc : 최근 등록 된 내용과 현재 내용을 비교하여 다른 항목 갯수를 반환
	 * @Method Name : isPossibleAutoApproval
	 * @Date : 2019. 5. 9.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public boolean isPossibleAutoApproval(PdProduct product) throws Exception {
		PdProductRelationFile criteriaForOrigin = new PdProductRelationFile();
		criteriaForOrigin.setPrdtNo(product.getPrdtNo());
		List<PdProductRelationFile> origin = this.productRelationFileDao.select(criteriaForOrigin);

		if (UtilsCollection.isEmpty(origin) && UtilsArray.isEmpty(product.getProductRelationFile())) {
			return true;
		}

		int differCount = 0;

		if (origin.size() > 0 && UtilsArray.isEmpty(product.getProductRelationFile())) {
			return false;
		}

		for (PdProductRelationFile source : product.getProductRelationFile()) {
			if (UtilsObject.isNotEmpty(source.getUploadFileImage())
					&& UtilsObject.isNotEmpty(source.getUploadFileVideo())) {
				// 첨부된 파일이 있는 경우, 변동사항이 있음으로 판단
				differCount = 1;
				break;
			} else if (source.isInput()) {
				// 신규입력 또는 수정인 경우
				for (PdProductRelationFile target : origin) {
					// 상품관련파일순번 기준으로 비교
					if (UtilsNumber.compare(source.getPrdtRltnFileSeq(), target.getPrdtRltnFileSeq()) == 0) {
						switch (source.getFileType()) {
						case PdProductRelationFile.FILE_TYPE_IMAGE:
							if (!UtilsText.equals(source.getAltrnText(), target.getAltrnText())) {
								differCount++;
							}
							if (UtilsNumber.compare(source.getSortSeq(), target.getSortSeq()) != 0) {
								differCount++;
							}
							break;
						case PdProductRelationFile.FILE_TYPE_MEDIA:
							if (!UtilsText.equals(source.getLinkInfo(), target.getLinkInfo())) {
								differCount++;
							}
							if (!UtilsText.equals(source.getAltrnText(), target.getAltrnText())) {
								differCount++;
							}
							if (UtilsText.equals(source.getUploadYn(), Const.BOOLEAN_FALSE)) {
								// URL입력인 경우, 동영상주소 비교
								if (!UtilsText.equals(source.getMovieUrl(), target.getMovieUrl())) {
									differCount++;
								}
							}
							break;
						case PdProductRelationFile.FILE_TYPE_3D:
							if (!UtilsText.equals(source.getImageName(), target.getImageName())) {
								differCount++;
							}
							if (!UtilsText.equals(source.getLinkInfo(), target.getLinkInfo())) {
								differCount++;
							}
							break;
						default:
							log.debug("알 수 없는 유형입니다. {}", source.getFileType());
							throw new Exception("알 수 없는 유형입니다. " + source.getFileType());
						}
						break;
					} else {

					}
				}
			} else {
				// 삭제된 경우, 체크 안함
			}
		}
		return differCount == 0 ? true : false;
	}

	/**
	 * @Desc : 상품 한 건에 대한 대표이미지 조회
	 * @Method Name : getProductTitleImageByPrdtNo
	 * @Date : 2019. 6. 24.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public PdProductRelationFile getProductTitleImageByPrdtNo(String prdtNo) throws Exception {
		return this.productRelationFileDao.selectProductTitleImageByPrdtNo(prdtNo);
	}

}
