package kr.co.abcmart.common.request;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;

import kr.co.abcmart.common.log.Log;
import kr.co.abcmart.common.util.UtilsText;

public abstract class BaseParameter extends Log {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Model model;

	public final String CLASS_ARRAY_PATTERN = "\\[[0-9]*?\\]";
	public final String PARAMETER_MAP_CLASSNAME = "kr.co.abcmart.common.request.ParameterMap";
	public final String UPLOAD_FILE_CLASSNAME = FileUpload.class.getName();

	private final List<String> arrayPrimitive = Arrays.asList("[I", "[J", "[D", "[Z", "[F", "[B",
			"[Ljava.lang.String;");
	private final List<String> numericType = Arrays.asList("int", "long", "double", "float", "short",
			"java.lang.Integer", "java.lang.Long", "java.lang.Double", "java.lang.Float", "java.lang.Short");

	public boolean isNumericType(String type) {
		return numericType.contains(type);
	}

	public boolean isContentTypeJson() {

		String contentType = request.getContentType();

		if (UtilsText.isBlank(contentType)) {
			return false;
		}
		return request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE);
	}

	public ArrayParameter getArrayParameter(String className) {

		ArrayParameter ap = new ArrayParameter();
		String array = UtilsText.matcherGroup(CLASS_ARRAY_PATTERN, className, 0);

		if (!UtilsText.isBlank(array)) {
			ap.setArray(true);
			if (!"[]".equals(array)) {
				ap.setCount(0);
			}
		}

		return ap;
	}

	public Class<?> getArrayClass(String className) throws ClassNotFoundException {
		return getArrayClass(null, className);
	}

	public Class<?> getArrayClass(String pkgName, String className) throws ClassNotFoundException {
		String name = null;
		switch (className) {
		case "java.lang.String":
			name = "java.lang.String";
			break;
		case "[Ljava.lang.String;":
			name = "java.lang.String";
			break;
		case "java.lang.Integer":
			name = "java.lang.Integer";
			break;
		case "integer":
			name = "java.lang.Integer";
			break;
		case "Integer":
			name = "java.lang.Integer";
			break;
		case "I":
			name = "java.lang.Integer";
			break;
		case "[I":
			name = "java.lang.Integer";
			break;
		case "java.lang.Boolean":
			name = "java.lang.Boolean";
			break;
		case "boolean":
			name = "java.lang.Boolean";
			break;
		case "Boolean":
			name = "java.lang.Boolean";
			break;
		case "Z":
			name = "java.lang.Boolean";
			break;
		case "[Z":
			name = "java.lang.Boolean";
			break;
		case "java.lang.Byte":
			name = "java.lang.Byte";
			break;
		case "Byte":
			name = "java.lang.Byte";
			break;
		case "byte":
			name = "java.lang.Byte";
			break;
		case "B":
			name = "java.lang.Byte";
			break;
		case "[B":
			name = "java.lang.Byte";
			break;
		case "Character":
			name = "java.lang.Character";
			break;
		case "java.lang.Character":
			name = "java.lang.Character";
			break;
		case "char":
			name = "java.lang.Character";
			break;
		case "C":
			name = "java.lang.Character";
			break;
		case "[C":
			name = "java.lang.Character";
			break;
		case "java.lang.Double":
			name = "java.lang.Double";
			break;
		case "Double":
			name = "java.lang.Double";
			break;
		case "double":
			name = "java.lang.Double";
			break;
		case "D":
			name = "java.lang.Double";
			break;
		case "[D":
			name = "java.lang.Double";
			break;
		case "java.lang.Float":
			name = "java.lang.Float";
			break;
		case "Float":
			name = "java.lang.Float";
			break;
		case "float":
			name = "java.lang.Float";
			break;
		case "F":
			name = "java.lang.Float";
			break;
		case "[F":
			name = "java.lang.Float";
			break;
		case "java.lang.Long":
			name = "java.lang.Long";
			break;
		case "Long":
			name = "java.lang.Long";
			break;
		case "long":
			name = "java.lang.Long";
			break;
		case "J":
			name = "java.lang.Long";
			break;
		case "[J":
			name = "java.lang.Long";
			break;
		case "java.lang.Short":
			name = "java.lang.Short";
			break;
		case "Short":
			name = "java.lang.Short";
			break;
		case "short":
			name = "java.lang.Short";
			break;
		case "S":
			name = "java.lang.Short";
			break;
		case "[S":
			name = "java.lang.Short";
			break;
		case "?":
			name = PARAMETER_MAP_CLASSNAME;
			break;
		default:
			name = pkgName;
			break;
		}

		return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
	}

	public boolean isPrimitiveArray(String sig) {
		return arrayPrimitive.contains(sig);
	}

	public abstract void populateParameter(String className) throws Exception;

}

class ArrayParameter {

	private int count;
	private boolean isArray;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
}
