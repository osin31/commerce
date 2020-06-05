package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductAddInfo;
import kr.co.abcmart.bo.product.repository.master.PdProductAddInfoDao;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 추가 정보 서비스
 * @FileName : ProductInsideAddInfoService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideAddInfoService {

	@Autowired
	private PdProductAddInfoDao productAddInfoDao;

	/**
	 * @Desc : 상품 추가정보 등록
	 * @Method Name : registProductAddInfo
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		if (UtilsArray.isNotEmpty(product.getProductAddInfo())) {
			// 기존 추가정보 모두 제거
			PdProductAddInfo productAddInfo = new PdProductAddInfo();
			productAddInfo.setPrdtNo(product.getPrdtNo());
			this.productAddInfoDao.deleteByForeignKeys(productAddInfo);
			int sortSeq = 0;

			// 추가정보 신규 등록
			for (PdProductAddInfo item : product.getProductAddInfo()) {
				item.setPrdtNo(product.getPrdtNo());
				item.setSortSeq(++sortSeq);
				item.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));

				if (UtilsText.equals("I", item.getAddInfoType())) {
					// 정보제공고시
					if (!UtilsText.equals(Const.BOOLEAN_TRUE, item.getReqYn())
							&& UtilsText.isBlank(item.getPrdtAddInfo())) {
						// 필수가 아닌 항목 중 비어있는 항목
						item.setPrdtAddInfo("");
					}
				} else if (UtilsText.equals("P", item.getAddInfoType())) {
					// 취급주의사항
					if (UtilsText.isBlank(item.getPrdtAddInfo())) {
						item.setPrdtAddInfo("");
					}
				} else if (UtilsText.equals("A", item.getAddInfoType())) {
					// 인증정보
					if (UtilsText.isBlank(item.getPrdtAddInfo())) {
						item.setPrdtAddInfo("");
					}
				}

				this.productAddInfoDao.insertWithSelectKey(item);
			}
		}
	}

	/**
	 * @Desc : 상품상세정보 조회
	 * @Method Name : searchProductAddInfo
	 * @Date : 2019. 4. 5.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<PdProductAddInfo> searchProductAddInfo(PdProduct product) throws Exception {
		return this.productAddInfoDao.selectWithProductInfoNotice(product.getPrdtNo());
	}

	/**
	 * @Desc : 상품정보 내에 상품상세정보 설정
	 * @Method Name : setProductAddInfo
	 * @Date : 2019. 4. 16.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setProductAddInfo(PdProduct product) throws Exception {
		List<PdProductAddInfo> result = this.searchProductAddInfo(product);
		if (UtilsCollection.isNotEmpty(result)) {
			product.setProductAddInfo(result.toArray(new PdProductAddInfo[result.size()]));
		}
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
		PdProductAddInfo criteriaForOrigin = new PdProductAddInfo();
		criteriaForOrigin.setPrdtNo(product.getPrdtNo());
		List<PdProductAddInfo> origin = this.productAddInfoDao.select(criteriaForOrigin);

		if (UtilsCollection.isEmpty(origin) && UtilsArray.isEmpty(product.getProductAddInfo())) {
			return true;
		}

		int differCount = 0;
		int listSizeCount = 0;

		listSizeCount += origin.size();

		if (origin.size() > 0 && UtilsArray.isEmpty(product.getProductAddInfo())) {
			return false;
		} else {
			listSizeCount -= product.getProductAddInfo().length;
		}

		for (PdProductAddInfo source : product.getProductAddInfo()) {
			for (PdProductAddInfo target : origin) {
				// 추가정보구분과 추가정보순번이 동일한 것끼리 비교
				if (UtilsText.equals(source.getAddInfoType(), target.getAddInfoType())) {
					if (UtilsObject.isNotEmpty(source.getPrdtAddInfoSeq())
							&& (UtilsNumber.compare(source.getPrdtAddInfoSeq(), target.getPrdtAddInfoSeq()) == 0)) {
						differCount += target.compareTo(source);
						break;
					}
				}
			}
		}
		return (differCount == 0 && listSizeCount == 0) ? true : false;
	}

	/**
	 * @Desc : 입점사 AS담당자 연락처 일괄변경
	 * @Method Name : setVendorAsMngrText
	 * @Date : 2019. 10. 23.
	 * @Author : tennessee
	 * @param vendor vndrNo, asMngtText 필드만 사용됨
	 * @return
	 * @throws Exception
	 */
	public Integer setVendorAsMngrText(VdVendor vendor) throws Exception {
		if (UtilsText.isBlank(vendor.getAsMngrText())) {
			vendor.setAsMngrText("");
		}
		return this.productAddInfoDao.updateVendorAsMngrText(vendor);
	}

}
