package kr.co.abcmart.bo.product.repository.master.base;

import java.util.List;

import kr.co.abcmart.bo.product.model.master.DpBrandVisual;

public interface BaseDpBrandVisualDao {
	/**
	 * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
	 */
	public List<DpBrandVisual> select(DpBrandVisual dpBrandVisual) throws Exception;

	/**
	 * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
	 */
	public int insert(DpBrandVisual dpBrandVisual) throws Exception;

	/**
	 * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
	 */
	public int update(DpBrandVisual dpBrandVisual) throws Exception;

	/**
	 * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
	 */
	public int delete(DpBrandVisual dpBrandVisual) throws Exception;

}
