package kr.co.abcmart.bo.promotion.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPromotionTargetVendorDao;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetVendor;

@Mapper
public interface PrPromotionTargetVendorDao extends BasePrPromotionTargetVendorDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BasePrPromotionTargetVendorDao 클래스에 구현 되어있습니다.
     * BasePrPromotionTargetVendorDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public PrPromotionTargetVendor selectByPrimaryKey(PrPromotionTargetVendor prPromotionTargetVendor) throws Exception;

}
