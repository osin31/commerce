package kr.co.abcmart.bo.vendor.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.vo.VdVendorProductAllNoticeSearchVO;
import kr.co.abcmart.bo.vendor.model.master.VdVendorProductAllNotice;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorProductAllNoticeDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface VdVendorProductAllNoticeDao extends BaseVdVendorProductAllNoticeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorProductAllNoticeDao 클래스에 구현
	 * 되어있습니다. BaseVdVendorProductAllNoticeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public VdVendorProductAllNotice selectByPrimaryKey(VdVendorProductAllNotice vdVendorProductAllNotice)
			throws Exception;

	/**
	 * @Desc : 상품전체공지 다건 조회
	 * @Method Name : selectProductAllNotice
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<VdVendorProductAllNotice> selectProductAllNotice(
			Pageable<VdVendorProductAllNoticeSearchVO, VdVendorProductAllNotice> pageable) throws Exception;

	/**
	 * @Desc : 상품전체공지 갯수
	 * @Method Name : selectProductAllNoticeCount
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectProductAllNoticeCount(
			Pageable<VdVendorProductAllNoticeSearchVO, VdVendorProductAllNotice> pageable) throws Exception;

	/**
	 * @Desc : 상품전체공지 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param vdVendorProductAllNotice
	 * @return
	 */
	public int insertWithSelectKey(VdVendorProductAllNotice vdVendorProductAllNotice) throws Exception;

	/**
	 * @Desc : 상품전체공지 상세조회
	 * @Method Name : searchByPrimaryKey
	 * @Date : 2019. 4. 16.
	 * @Author : hsjhsj19
	 * @param vdVendorProductAllNotice
	 * @return
	 */
	public VdVendorProductAllNotice searchByPrimaryKey(VdVendorProductAllNotice vdVendorProductAllNotice)
			throws Exception;

}
