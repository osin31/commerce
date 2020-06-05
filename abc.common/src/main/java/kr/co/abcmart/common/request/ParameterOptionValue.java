package kr.co.abcmart.common.request;

import java.util.List;

import kr.co.abcmart.common.util.UtilsText;

public class ParameterOptionValue {

	//@ParameterOption 어노테이션이 선언된 변수 명
	private String fieldName;
	
	//파라메터를 가지고 올 필드명
	private String target;
	
	//파라메터 매핑 제외할 변수 명
	private List<String> exclude;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<String> getExclude() {
		return exclude;
	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}
	
	public String getParameterName() {
		return UtilsText.concat(fieldName,".",this.target);
	}
	
	public String getParameterName(String target) {
		return UtilsText.concat(fieldName,".",target);
	}
	
}
