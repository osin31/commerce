package kr.co.abcmart.bo.product.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.BdProductReviewImage;
import kr.co.abcmart.bo.product.repository.master.base.BaseBdProductReviewImageDao;

@Mapper
public interface BdProductReviewImageDao extends BaseBdProductReviewImageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdProductReviewImageDao 클래스에 구현 되어있습니다.
	 * BaseBdProductReviewImageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public BdProductReviewImage selectByPrimaryKey(BdProductReviewImage bdProductReviewImage) throws Exception;

	public int deleteImg(BdProductReviewImage bdProductReviewImage) throws Exception;

}
