package kr.co.abcmart.bo.settlement.repository.master.base;

import java.util.List;
import java.lang.Object;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationMemo;


/**
 * ※ 절대 수정 금지. 해당 파일은 code generator 작동 시 내용을 전부 덮어 씌우게 됩니다. 
 * 
 */

public interface BaseSeExactCalculationMemoDao {
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public List<SeExactCalculationMemo> select(SeExactCalculationMemo seExactCalculationMemo) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int insert(SeExactCalculationMemo seExactCalculationMemo) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int update(SeExactCalculationMemo seExactCalculationMemo) throws Exception;
	
	 /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int delete(SeExactCalculationMemo seExactCalculationMemo) throws Exception;


}
