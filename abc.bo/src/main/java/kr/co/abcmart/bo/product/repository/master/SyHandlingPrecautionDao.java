package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.SyHandlingPrecaution;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.repository.master.base.BaseSyHandlingPrecautionDao;

@Mapper
public interface SyHandlingPrecautionDao extends BaseSyHandlingPrecautionDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyHandlingPrecautionDao 클래스에 구현 되어있습니다.
	 * BaseSyHandlingPrecautionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public SyHandlingPrecaution selectByPrimaryKey(SyHandlingPrecaution syHandlingPrecaution) throws Exception;

	/**
	 * @Desc : 취급주의사항 리스트 조회
	 * @Method Name : selectSyHandlingPrecautionById
	 * @Date : 2019. 5. 14.
	 * @Author : 이가영
	 * @param stdCtgrNo
	 * @return
	 */
	public List<SyHandlingPrecaution> selectSyHandlingPrecautionById(String stdCtgrNo) throws Exception;

	/**
	 * @Desc : 취급주의사항 등록
	 * @Method Name : insertSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 */
	public void insertSyHandlingPrecaution(SyHandlingPrecaution syHandlingPrecaution) throws Exception;

	/**
	 * @Desc : 취급주의사항 수정
	 * @Method Name : updateSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 */
	public void updateSyHandlingPrecaution(SyHandlingPrecaution syHandlingPrecaution) throws Exception;

	/**
	 * @Desc : 취급주의사항 삭제
	 * @Method Name : deleteSyHandlingPrecaution
	 * @Date : 2019. 5. 15.
	 * @Author : 이가영
	 * @param syHandlingPrecaution
	 * @throws Exception
	 */
	public void deleteSyHandlingPrecaution(SyStandardCategory syStandardCategory) throws Exception;

}
