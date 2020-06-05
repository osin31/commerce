package kr.co.abcmart.common.request;

import java.util.Collection;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.ui.Model;

public interface IParameter extends IGetter {
	
	Model addAttribute(String attributeName, @Nullable Object attributeValue);

	Model addAttribute(Object attributeValue);

	Model addAllAttributes(Collection<?> attributeValues);

	Model addAllAttributes(Map<String, ?> attributes);

	Model mergeAttributes(Map<String, ?> attributes);

	boolean containsAttribute(String attributeName);

	Map<String, Object> asMap();
	
}
