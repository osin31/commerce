package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyCodeDetailDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyCodeDetailDao extends BaseSyCodeDetailDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyCodeDetailDao 클래스에 구현 되어있습니다.
	 * BaseSyCodeDetailDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public SyCodeDetail selectByPrimaryKey(SyCodeDetail syCodeDetail) throws Exception;

	/**
	 * @Desc : 삭제시에 상위,하위코드가지는지 체크
	 * @Method Name : selectRemoveCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectRemoveCheck(SyCodeDetail param) throws Exception;

	/**
	 * @Desc : 상위코드 삭제시에 하위코드 가지는지 체크
	 * @Method Name : selectRemoveUpCodeCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectRemoveUpCodeCheck(SyCodeDetail param) throws Exception;

	/**
	 * @Desc : 상위,하위코드 삭제
	 * @Method Name : deleteUpDownCode
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void deleteUpDownCode(SyCodeDetail params) throws Exception;

	/**
	 * @Desc : 상위,하위코드 개수 체크
	 * @Method Name : selectCodeDetailListCount
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectCodeDetailListCount(Pageable<SyCodeDetail, SyCodeDetail> param) throws Exception;

	/**
	 * @Desc : 하위코드 등록시에 상위코드 목록검색
	 * @Method Name : selectUpCodeList
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> selectUpCodeList(SyCodeDetail param) throws Exception;

	/**
	 * @Desc : 상위코드인지 하위코드인지 체크하여 upCodeDtlName-->codeDtlName으로 변환
	 * @Method Name : upDownCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SyCodeDetail upDownCheck(SyCodeDetail param) throws Exception;

	/**
	 * @Desc : 상위,하위코드 상세조회
	 * @Method Name : selectUpDownDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SyCodeDetail selectUpDownDetail(SyCodeDetail param) throws Exception;

	/**
	 * @Desc : 상위,하위코드 리스트 그리드조회
	 * @Method Name : selectUpDownCode
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> selectUpDownCode(Pageable<SyCodeDetail, SyCodeDetail> params) throws Exception;

	/**
	 * @Desc : 상위하위코드 상세 수정
	 * @Method Name : updateUpDownCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @throws Exception
	 */
	public void updateUpDownCodeDetail(SyCodeDetail param) throws Exception;

	/**
	 * @Desc : 상위코드 등록
	 * @Method Name : insertUpCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void insertUpCodeDetail(SyCodeDetail params) throws Exception;

	/**
	 * @Desc : 하위코드 등록
	 * @Method Name : insertDownCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void insertDownCodeDetail(SyCodeDetail params) throws Exception;

	/**
	 * @Desc : 코드필드로 코드 번호와 이름 검색
	 * @Method Name : selectCodeNoName
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param codeField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> selectCodeNoName(String codeField) throws Exception;

	/**
	 * @Desc : 코드필드로 코드 번호와 이름 검색
	 * @Method Name : selectCodeDtlInfoList
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> selectCodeDtlInfoList(SyCodeDetail params) throws Exception;

	/**
	 * @Desc : 상위,하위코드 그리드에서 수정
	 * @Method Name : updateUpDownCodeGrid
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void updateUpDownCodeGrid(SyCodeDetail params) throws Exception;

	/**
	 * @Desc : 여러개의 코드정보를 가져올때 이용
	 * @Method Name : selectCodeListByGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 유성민
	 * @param codeFields
	 * @return
	 */
	public List<SyCodeDetail> selectCodeListByGroup(String[] codeFields) throws Exception;

	public List<SyCodeDetail> selectSysCodeDetialList(List<SyCodeDetail> list) throws Exception;

	/**
	 * @Desc : 사용중이지 않은 코드도 같이 조회
	 * @Method Name : selectAllCodeNoName
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param codeField
	 * @return
	 */
	public List<SyCodeDetail> selectAllCodeNoName(String codeField);

	/**
	 * @Desc : 사용중이지 않은 코드 여러개 같이 조회
	 * @Method Name : selectAllCodeListByGroup
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param codeFields
	 * @return
	 */
	public List<SyCodeDetail> selectAllCodeListByGroup(String[] codeFields);

	/**
	 * @Desc : 코드필드와 addInfo조회로 단일객체 반환
	 * @Method Name : selectCodeDetailByFiledAddInfo
	 * @Date : 2019. 9. 27.
	 * @Author : sic
	 * @param syCodeDetail
	 * @throws Exception
	 */
	public SyCodeDetail selectCodeDetailByFiledAddInfo(SyCodeDetail syCodeDetail) throws Exception;

}
