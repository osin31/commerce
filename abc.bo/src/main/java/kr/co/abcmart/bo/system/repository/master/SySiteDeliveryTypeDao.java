package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.system.model.master.SySiteDeliveryType;
import kr.co.abcmart.bo.system.repository.master.base.BaseSySiteDeliveryTypeDao;

@Mapper
public interface SySiteDeliveryTypeDao extends BaseSySiteDeliveryTypeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSySiteDeliveryTypeDao 클래스에 구현 되어있습니다.
	 * BaseSySiteDeliveryTypeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SySiteDeliveryType selectByPrimaryKey(SySiteDeliveryType sySiteDeliveryType) throws Exception;

	public List<SySiteDeliveryType> selectBySiteNo(@Param("siteNo") String siteNo, @Param("isUse") boolean isUse);

	public void deleteBySiteNo(String siteNo);

	public void insertDeliveryType(SySiteDeliveryType sySiteDeliveryType);

}
