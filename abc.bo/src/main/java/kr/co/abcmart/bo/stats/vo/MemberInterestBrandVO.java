package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MemberInterestBrandVO extends BaseBean implements Serializable {

	private String noSeq;
	private String brandName;
	private String brandCount;

	private String sortColumn;
	private String sortType;

}
