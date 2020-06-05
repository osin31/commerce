package kr.co.abcmart.bo.product.model.master;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * @Desc : 상품 매핑테이블기준 조회 시 반환 객체
 * @FileName : PdProductMapped.java
 * @Project : abc.bo
 * @Date : 2019. 11. 12.
 * @Author : tennessee
 */
public class PdProductMapped extends HashMap<String, Object> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
	}

}
