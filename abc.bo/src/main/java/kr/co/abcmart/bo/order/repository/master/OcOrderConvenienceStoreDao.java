package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderConvenienceStore;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderConvenienceStoreDao;

@Mapper
public interface OcOrderConvenienceStoreDao extends BaseOcOrderConvenienceStoreDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderConvenienceStoreDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderConvenienceStoreDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public OcOrderConvenienceStore selectByPrimaryKey(OcOrderConvenienceStore ocOrderConvenienceStore) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectConvenienceStore
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param cvs
	 * @return
	 */
	public List<OcOrderConvenienceStore> selectConvenienceStore(OcOrderConvenienceStore cvs) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : insertConvenienceStore
	 * @Date : 2019. 3. 27.
	 * @Author : flychani@3top.co.kr
	 * @param rcvrInfo
	 * @return
	 */
	public int insertConvenienceStore(OcOrderConvenienceStore rcvrInfo) throws Exception;

}
