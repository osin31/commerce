package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.repository.master.base.BaseSySiteChnnlDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SySiteChnnlDao extends BaseSySiteChnnlDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSySiteChnnlDao 클래스에 구현 되어있습니다.
	 * BaseSySiteChnnlDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SySiteChnnl selectByPrimaryKey(SySiteChnnl sySiteChnnl) throws Exception;

	public List<SySiteChnnl> selectChannelForPaging(Pageable<SySiteChnnl, SySiteChnnl> pageable) throws Exception;

	public Integer selectChannelForPagingCount(Pageable<SySiteChnnl, SySiteChnnl> pageable) throws Exception;

	public Integer selectMaxChannelNo() throws Exception;

	public void updateChannel(SySiteChnnl sySiteChnnl);

	public void insertChannel(SySiteChnnl sySiteChnnl);

	public List<SySiteChnnl> selectUseChannelList();

	public List<SySiteChnnl> selectChannelList(@Param("siteNo") String siteNo, @Param("isUse") boolean isUse);

	public List<SySiteChnnl> selectVendorUseChannelList();

	public String selectSiteNo(@Param("chnnlNo") String chnnlNo) throws Exception;

	public SySiteChnnl selectChnnlDetailInfo(SySiteChnnl sySiteChnnl) throws Exception;

	public List<SySiteChnnl> selectSearchDisplayChannel(SySiteChnnl sySiteChnnl) throws Exception;

	public Integer selectChannelProdNo(String chnnlPrdtGbnNo) throws Exception;

}
