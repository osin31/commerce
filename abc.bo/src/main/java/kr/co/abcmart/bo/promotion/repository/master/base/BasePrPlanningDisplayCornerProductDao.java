package kr.co.abcmart.bo.promotion.repository.master.base;

import java.util.List;
import java.lang.Object;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCornerProduct;


/**
 * ※ 절대 수정 금지. 해당 파일은 code generator 작동 시 내용을 전부 덮어 씌우게 됩니다. 
 * 
 */

public interface BasePrPlanningDisplayCornerProductDao {
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public List<PrPlanningDisplayCornerProduct> select(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int insert(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int update(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct) throws Exception;
	
	 /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int delete(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct) throws Exception;


}
