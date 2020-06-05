package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductAddInfo;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductAddInfoDao;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;

@Mapper
public interface PdProductAddInfoDao extends BasePdProductAddInfoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductAddInfoDao 클래스에 구현 되어있습니다.
	 * BasePdProductAddInfoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PdProductAddInfo selectByPrimaryKey(PdProductAddInfo pdProductAddInfo) throws Exception;

	/**
	 * @Desc : 상품 추가정보 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productAddInfo
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductAddInfo productAddInfo) throws Exception;

	/**
	 * @Desc : 왜래키를 사용한 상품추가정보 삭제
	 * @Method Name : deleteByForeignKeys
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productAddInfo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByForeignKeys(PdProductAddInfo productAddInfo) throws Exception;

	/**
	 * @Desc : 상품추가정보 및 상품정보고시정보 조회
	 * @Method Name : selectWithProductInfoNotice
	 * @Date : 2019. 4. 16.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductAddInfo> selectWithProductInfoNotice(String prdtNo) throws Exception;

	/**
	 * @Desc : 입점사 AS담당자 연락처 일괄변경
	 * @Method Name : updateVendorAsMngrText
	 * @Date : 2019. 10. 23.
	 * @Author : tennessee
	 * @param vendor
	 * @param asMngrText
	 * @return
	 * @throws Exception
	 */
	public Integer updateVendorAsMngrText(VdVendor vendor) throws Exception;

}
