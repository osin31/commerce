package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmTermsDetailAddInfoDao;
import kr.co.abcmart.bo.cmm.model.master.CmTermsDetail;
import kr.co.abcmart.bo.cmm.model.master.CmTermsDetailAddInfo;

@Mapper
public interface CmTermsDetailAddInfoDao extends BaseCmTermsDetailAddInfoDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseCmTermsDetailAddInfoDao 클래스에 구현 되어있습니다.
     * BaseCmTermsDetailAddInfoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public CmTermsDetailAddInfo selectByPrimaryKey(CmTermsDetailAddInfo cmTermsDetailAddInfo) throws Exception;
	
	/**
	 * 개인정보취급방침 등록시 추가 정보 입력
	 * @param cmTermsDetailAddInfo
	 * @throws Exception
	 */
	public void insertPriAddInfo(CmTermsDetailAddInfo cmTermsDetailAddInfo)throws Exception;
	
	/**
	 * 개인정보 취급방침 상세 보기시 addInfo 리스트
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public List<CmTermsDetailAddInfo> selectPrivacyAddList(CmTermsDetail cmTermsDetail)throws Exception;
	
	/**
	 * 개인정보취급방침 삭제시에 추가정보 삭제
	 * @param cmTermsDetail
	 * @throws Exception
	 */
	public void deletePrivacyAddInfo(CmTermsDetailAddInfo cmTermsDetailAddInfo)throws Exception;
	
	/**
	 * 개인정보 취급방침 수정시에 추가정보 수정
	 * @param cmTermsDetailAddInfo
	 * @throws Exception
	 */
	public void updatePrivacyAddInfo(CmTermsDetailAddInfo cmTermsDetailAddInfo)throws Exception;
}

