package kr.co.abcmart.bo.display.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpWebzineDetailImage;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpWebzineDetailImageDao;

@Mapper
public interface DpWebzineDetailImageDao extends BaseDpWebzineDetailImageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpWebzineDetailImageDao 클래스에 구현 되어있습니다.
	 * BaseDpWebzineDetailImageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpWebzineDetailImage selectByPrimaryKey(DpWebzineDetailImage dpWebzineDetailImage) throws Exception;

	/**
	 * 
	 * @Desc : 웹진 추가 이미지 등록
	 * @Method Name : insertDpWebzineDetailImage
	 * @Date : 2019. 4. 10.
	 * @Author : SANTA
	 * @param dpWebzineDetailImage
	 * @throws Exception
	 */
	public void insertDpWebzineDetailImage(DpWebzineDetailImage dpWebzineDetailImage) throws Exception;
}
