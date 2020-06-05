package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.product.model.master.PdProductRelationFile;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductRelationFileDao;

@Mapper
public interface PdProductRelationFileDao extends BasePdProductRelationFileDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductRelationFileDao 클래스에 구현 되어있습니다.
	 * BasePdProductRelationFileDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PdProductRelationFile selectByPrimaryKey(PdProductRelationFile pdProductRelationFile) throws Exception;

	/**
	 * @Desc : 상품 한 건에 대한 대표이미지 조회
	 * @Method Name : selectProductTitleImageByPrdtNo
	 * @Date : 2019. 6. 24.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public PdProductRelationFile selectProductTitleImageByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품 번호에 대한 파일유형 3D 삭제
	 * @Method Name : delete3dByPrdtNo
	 * @Date : 2019. 7. 29.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer delete3dByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품 번호에 대한 모든 관련파일 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 9. 2.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품 번호에 해당하지 않는 관련파일 삭제
	 * @Method Name : deleteAnothersByPrdtNo
	 * @Date : 2019. 9. 2.
	 * @Author : tennessee
	 * @param productRelationFile
	 * @return
	 * @throws Exception
	 */
	public Integer deleteAnothersByPrdtNo(@Param("prdtNo") String prdtNo,
			@Param("productRelationFile") List<PdProductRelationFile> productRelationFile) throws Exception;

}
