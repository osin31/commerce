package kr.co.abcmart.bo.system.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SySiteChnnlImage;
import kr.co.abcmart.bo.system.repository.master.base.BaseSySiteChnnlImageDao;

@Mapper
public interface SySiteChnnlImageDao extends BaseSySiteChnnlImageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSySiteChnnlImageDao 클래스에 구현 되어있습니다.
	 * BaseSySiteChnnlImageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SySiteChnnlImage selectByPrimaryKey(SySiteChnnlImage sySiteChnnlImage) throws Exception;

	public void insertSiteChnnlImg(SySiteChnnlImage sySiteChnnlImage) throws Exception;

	public void deleteChnnlImg(SySiteChnnlImage sySiteChnnlImage) throws Exception;

}
