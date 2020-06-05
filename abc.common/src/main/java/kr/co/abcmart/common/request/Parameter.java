package kr.co.abcmart.common.request;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.excel.ExcelProcessor;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.exception.MultipartTypeException;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.EscapeXSS;
import kr.co.abcmart.common.request.annotation.ParameterDateTimeFormat;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.request.annotation.UnEscapeXSS;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsDevice;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import lombok.extern.slf4j.Slf4j;

/***
 * 
 * 본 클래스는 request로 전달 받은 파라메터 정보를 보다 쉽게 사용하고 Spring Controller에서 메소드 argument들을
 * 최소화 하기 위하여 만들어 졌다. 또한 xss를 바로 처리 할 수있게 기본 적용 되어 있다. ex)
 * 
 * - 정석대로 사용 할 경우. @RequestMapping("sample") public ModelAndView
 * sample(HttpServletRequest request, HttpServletResponse response,Model model){
 * 
 * jsp 페이지에 전달 할 객체 정보 추가. model.addttribute("key","value")
 * 
 * return new ModelAndView("/jsp/main"); }
 * 
 * - 변경 후 @RequestMapping("sample") public ModelAndView
 * sample(Parameter<SampleVO> parameter){
 *
 * jsp 페이지에 전달 할 객체 정보 추가. parameter.addttribute("key","value") return
 * forward("/jsp/main");
 *
 * }
 * 
 * 파라메터를 제네릭 형식으로 VO 또는 DTO 클래스를 통해 주입 받을 수 있으며 파일 업로드도 쉽게 할 수 있도록 도와 준다
 * 
 * parameter.getUploadFile(); 또는 parameter.getUploadFiles();
 * 
 * feign을 통해 rest통신을 할때 backend 서버로 파라메터를 보내야 할 경우 rest() 함수를 통해 바로 전달 해줄 수 있다.
 * parameter.rest();
 * 
 * 
 * @author 장진철(zerocooldog)
 *
 * @param <T>
 */
@Slf4j
public class Parameter<T> extends BaseParameter implements IParameter, IValidator {

	private T paramObject;

	private String paramJson;

	private MultiValueMap<String, Object> rest = new LinkedMultiValueMap<>();

	public Parameter() {
	}

	public Parameter(T baseObject) {
		this.paramObject = baseObject;
	}

	public Parameter(HttpServletRequest request, HttpServletResponse response) {

		this(request, response, null);

		try {
			// 제네릭타임을 제외한 클래스 이름
			populateParameter("?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	public Parameter(HttpServletRequest request, HttpServletResponse response, Model model) {
		super.request = request;
		super.response = response;
		super.model = model;
	}

	public T get() {
		return (T) paramObject;
	}

	public ParameterMap getParammeterMap() {
		return (ParameterMap) paramObject;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public MultiValueMap<String, Object> rest() {
		return rest;
	}

	@Override
	public Model addAttribute(String attributeName, Object attributeValue) {
		return model.addAttribute(attributeName, attributeValue);
	}

	@Override
	public Model addAttribute(Object attributeValue) {
		return model.addAttribute(attributeValue);
	}

	@Override
	public Model addAllAttributes(Collection<?> attributeValues) {
		return model.addAllAttributes(attributeValues);
	}

	@Override
	public Model addAllAttributes(Map<String, ?> attributes) {
		return model.addAllAttributes(attributes);
	}

	@Override
	public Model mergeAttributes(Map<String, ?> attributes) {
		return model.addAllAttributes(attributes);
	}

	@Override
	public boolean containsAttribute(String attributeName) {
		return model.containsAttribute(attributeName);
	}

	@Override
	public Map<String, Object> asMap() {
		return model.asMap();
	}

	@SuppressWarnings("unchecked")
	public void populateParameter(String className) throws Exception {

		Class<?> clazz = getArrayClass(className.replaceAll(CLASS_ARRAY_PATTERN, ""), className);
		Object object = null;

		// json타입일 경우 문자열 저장.
		if (isContentTypeJson()) {
			paramJson = request.getReader().lines().collect(Collectors.joining());
		}

		if (clazz.getName().equals(PARAMETER_MAP_CLASSNAME)) {
			object = setMap(clazz, className);
		} else {
			object = setBean(clazz, className);
		}

		this.paramObject = (T) object;
	}

	private Object setMap(Class<?> clazz, String className) throws Exception {

		ParameterMap parameterMap = new ParameterMap();
		parameterMap.setParameterMap(request.getParameterMap(), rest);

		if (isMultipartHttpServletRequest()) {
			MultipartHttpServletRequest multipart = getMultipartRequest();

			Iterator<String> names = multipart.getFileNames();

			while (names.hasNext()) {
				String name = names.next();
				List<MultipartFile> partFile = multipart.getFiles(name);

				rest.addAll(name, partFile != null ? partFile : null);
			}
		}

		return parameterMap;
	}

	private Object setBean(Class<?> clazz, String className) throws Exception {

		Object object = null;

		ArrayParameter ap = getArrayParameter(className);

		int parameterValuesLength = 0;
		if (clazz != null) {

			Enumeration<String> params = request.getParameterNames();

			while (params.hasMoreElements()) {
				String name = params.nextElement();
				int length = request.getParameterValues(name).length;

				if (parameterValuesLength < length) {
					parameterValuesLength = length;
				}
			}
		}

		object = (ap.isArray()) ? Array.newInstance(clazz, parameterValuesLength) : clazz.newInstance();

		if (isContentTypeJson()) {
			Object bean = UtilsText.getObjectMapper().readValue(paramJson, object.getClass());
			Field[] fields = FieldUtils.getAllFields(bean.getClass());

			for (Field field : fields) {
				rest.add(field.getName(), FieldUtils.getField(bean.getClass(), field.getName(), true));
			}

			return bean;
		} else {

			if (object.getClass().isArray()) {
				setArrayInstance(object, clazz, null);
			} else {
				setProperty(object, 0);
			}
		}

		return object;
	}

	public void setProperty(Object bean, int i) throws Exception {
		setProperty(bean, i, null, null);
	}

	public void setProperty(Object bean, int i, List<String> exclude, ParameterOptionValue parameterOptionValue)
			throws Exception {

		Field[] fields = null;

		try {
			fields = FieldUtils.getAllFields(bean.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Field field : fields) {

			String type = field.getType().getName();
			boolean isMultipart = (UPLOAD_FILE_CLASSNAME.equals(type.replace("[L", "").replace(";", ""))
					&& isMultipartHttpServletRequest());

			String parameterName = (parameterOptionValue != null)
					? parameterOptionValue.getParameterName(field.getName())
					: field.getName();

			ParameterOption paramOptionAnnotation = field.getAnnotation(ParameterOption.class);
			EscapeXSS escapeAnnotation = field.getAnnotation(EscapeXSS.class);
			UnEscapeXSS unEscapeAnnotation = field.getAnnotation(UnEscapeXSS.class);

			boolean isEscapeXSS = (escapeAnnotation != null) ? escapeAnnotation.value() : true;
			boolean isUnEscapeXSS = (unEscapeAnnotation != null) ? true : false;

			if (isUnEscapeXSS == true) {
				isEscapeXSS = false;
			}

			String[] parameters = request.getParameterValues(parameterName);

			if (paramOptionAnnotation != null) {

				ParameterOptionValue initParameterOptionValue = new ParameterOptionValue();
				initParameterOptionValue.setFieldName(field.getName());
				initParameterOptionValue.setTarget(paramOptionAnnotation.target());
				initParameterOptionValue.setExclude(Arrays.asList(paramOptionAnnotation.exclude()));

				// [Ljava.lang.String; [L,; 제거
				String beanClassName = field.getType().getName().replace("[L", "");
				beanClassName = beanClassName.replace(";", "");

				if (UPLOAD_FILE_CLASSNAME.equals(beanClassName)) {
					if (field.getType().isArray()) {

						FileUpload[] fileUploads = getUploadFiles(initParameterOptionValue.getParameterName());

						if (fileUploads == null || (fileUploads != null && fileUploads.length == 0)) {
							continue;
						}

						BeanUtils.setProperty(bean, field.getName(), fileUploads);
					} else {

						FileUpload fileUpload = getUploadFile(initParameterOptionValue.getParameterName());

						if (fileUpload == null) {
							continue;
						}

						BeanUtils.setProperty(bean, field.getName(), fileUpload);
					}

				} else {

					Class<?> clazz = Class.forName(beanClassName, true, Thread.currentThread().getContextClassLoader());

					if (field.getType().isArray()) {

						Object[] beanDataArray = createArray(clazz, initParameterOptionValue);

						if (beanDataArray == null || (beanDataArray != null && beanDataArray.length == 0)) {
							continue;
						}
						BeanUtils.setProperty(bean, field.getName(), beanDataArray);
					} else {
						Object beanData = create(clazz, initParameterOptionValue.getExclude(),
								initParameterOptionValue);
						if (beanData == null) {
							continue;
						}
						BeanUtils.setProperty(bean, field.getName(), beanData);
					}
				}

				continue;
			}

			if ((parameters != null && !isMultipart)) {

				if (exclude != null && exclude.contains(field.getName())) {
					continue;
				}

				String value = null;

				try {
					value = parameters[i];
				} catch (ArrayIndexOutOfBoundsException e) {
				}

				// rest 파라메터 전송용

				// 파일 업로드 객체 타입이고 파라메터 값이 아무것도 없으면 null로 설정
				if ((UtilsText.isBlank(value) || (value instanceof String))
						&& UPLOAD_FILE_CLASSNAME.equals(field.getType().getName())) {
					value = null;
				}

				if (!UtilsText.isBlank(value)) {

					value = UtilsText.escapeXss(value);
					if (!isEscapeXSS || isUnEscapeXSS) {
						value = UtilsText.unescapeXss(value);
					}
				}

				if (isPrimitiveArray(field.getType().getName())) {

					if (request.getParameterValues(parameterName) != null) {
						setPropertyToPrimitive(field, bean, parameterName, value, (!isEscapeXSS || isUnEscapeXSS));
					}

				} else {

					try {

						if ("java.util.List".equals(field.getType().getName())) {

							switch (field.getGenericType().getTypeName()) {
							case "java.util.List<java.lang.Integer>":
								BeanUtils.setProperty(bean, field.getName(), getIntList(parameterName));
								break;
							case "java.util.List<java.lang.Long>":
								BeanUtils.setProperty(bean, field.getName(), getLongList(parameterName));
								break;
							case "java.util.List<java.lang.Double>":
								BeanUtils.setProperty(bean, field.getName(), getDoubleList(parameterName));
								break;
							case "java.util.List<java.lang.Boolean>":
								BeanUtils.setProperty(bean, field.getName(), getBooleanList(parameterName));
								break;
							case "java.util.List<java.lang.Float>":
								BeanUtils.setProperty(bean, field.getName(), getFloatList(parameterName));
								break;
							case "java.util.List<java.lang.Short>":
								BeanUtils.setProperty(bean, field.getName(), getShortList(parameterName));
								break;
							case "java.util.List<java.lang.Byte>":
								BeanUtils.setProperty(bean, field.getName(), getByteList(parameterName));
								break;
							case "java.util.List<java.lang.BigDecimal>":
								BeanUtils.setProperty(bean, field.getName(), getBigDecimalList(parameterName));
								break;
							default:
								BeanUtils.setProperty(bean, field.getName(),
										Arrays.asList(getStringArray(parameterName, (isEscapeXSS))));
								break;
							}

						} else {

							if (value != null) {
								setPropertyToPrimitive(field, bean, parameterName, value,
										(!isEscapeXSS || isUnEscapeXSS));
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						log.warn("setProperty exception message = {}", e.getMessage());
					}
				}
				rest.add(parameterName, value);
			} else {

				if (isMultipart) {

					FileUpload fileUpload = null;
					FileUpload[] fileUploads = getUploadFiles(parameterName);

					if (field.getType().isArray()) {
						if (fileUploads != null) {
							BeanUtils.setProperty(bean, field.getName(), getUploadFiles(parameterName));
						}
					} else {

						if (fileUploads != null && fileUploads.length > 1) {
							try {
								fileUpload = getUploadFiles(parameterName)[i];
							} catch (Exception e) {
								fileUpload = null;
							}
						} else if (fileUploads != null) {
							if (parameterName.indexOf(".") > -1) {
								fileUpload = getUploadFiles(parameterName)[0];
							} else {
								fileUpload = getUploadFiles(field.getName())[0];
							}
						}
//						log.debug(
//								"parameterName : {}, parameters = {}, paramOptionAnnotation = {}, isMultipart : {}, tempUploadFile ={}",
//								parameterName, parameters, paramOptionAnnotation, isMultipart, tempUploadFile);
//						log.debug("bean = {}, ", bean);

						BeanUtils.setProperty(bean, field.getName(), fileUpload);
					}

					rest.add(parameterName,
							(fileUpload != null) ? (MultipartFile) fileUpload.getMultiPartFile() : null);
				}
			}
		}
	}

	private void setPropertyToPrimitive(Field field, Object bean, String parameterName, String value, boolean isEscape)
			throws Exception {

		String type = field.getType().getName();

		if ("[Ljava.lang.Integer;".equals(type) || "[I".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getIntArray(parameterName));
		} else if ("[Ljava.lang.Long;".equals(type) || "[J".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getLongArray(parameterName));
		} else if ("[Ljava.lang.Double;".equals(type) || "[D".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getDoubleArray(parameterName));
		} else if ("[Ljava.lang.Float;".equals(type) || "[F".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getFloatArray(parameterName));
		} else if ("[Ljava.lang.Boolean;".equals(type) || "[Z".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getBooleanArray(parameterName));
		} else if ("[Ljava.lang.Short;".equals(type) || "[S".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getShortArray(parameterName));
		} else if ("[Ljava.lang.Byte;".equals(type) || "[B".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getByteArray(parameterName));
		} else if ("[Ljava.lang.String;".equals(type) || "[B".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getStringArray(parameterName, isEscape));
		} else if ("[Ljava.lang.Character;".equals(type) || "[C".equals(type)) {
			BeanUtils.setProperty(bean, field.getName(), getCharArray(parameterName, isEscape));
		} else if ("java.sql.Timestamp".equals(type)) {
			if (!UtilsText.isBlank(value)) {
				BeanUtils.setProperty(bean, field.getName(), UtilsDate.getSqlTimeStamp(parseDate(field, value)));
			}
		} else if ("java.sql.Date".equals(type)) {
			if (!UtilsText.isBlank(value)) {
				BeanUtils.setProperty(bean, field.getName(), UtilsDate.getSqlDate(parseDate(field, value)));
			}
		} else if ("java.util.Date".equals(type)) {
			if (!UtilsText.isBlank(value)) {
				BeanUtils.setProperty(bean, field.getName(), parseDate(field, value));
			}
		} else {

			boolean isNumeric = isNumericType(type);

			if (isNumeric) {
				value = removeNumberTypeComma(value);
			}

			if (!UtilsText.isBlank(value)) {
				BeanUtils.setProperty(bean, field.getName(), value);
			}
		}
	}

	private Date parseDate(Field field, String value) {

		if (UtilsText.isBlank(value)) {
			return null;
		}

		String replaceValue = value.replaceAll("[^0-9]", "").trim();
		Date date = null;

		ParameterDateTimeFormat parameterDateTimeFormat = field.getAnnotation(ParameterDateTimeFormat.class);
		boolean isDateTimeFormat = (parameterDateTimeFormat != null) ? true : false;

		if (!isDateTimeFormat) {
			if (replaceValue.length() == 14) {
				date = UtilsDate.parseDate(replaceValue, BaseConst.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
			} else if (replaceValue.length() == 8) {
				date = UtilsDate.parseDate(replaceValue, BaseConst.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
			} else if (replaceValue.length() == 17) {
				date = UtilsDate.parseDate(replaceValue, BaseConst.DEFAULT_DATETIME_MILI_PATTERN_NOT_CHARACTERS);
			}
		} else {
			date = UtilsDate.parseDate(value, parameterDateTimeFormat.value());
		}

		return date;

	}

	private FileUpload getFileUpload(FileUpload fileUpload) {
		return (fileUpload != null) ? fileUpload : null;
	}

	private void setArrayInstance(Object object, Class<?> clazz, ParameterOptionValue parameterOptionValue)
			throws Exception {
		setArrayInstance(object, clazz, null, parameterOptionValue);
	}

	private void setArrayInstance(Object object, Class<?> clazz, List<String> exclude,
			ParameterOptionValue parameterOptionValue) throws Exception {

		int length = Array.getLength(object);
		for (int i = 0; i < length; i++) {
			Object o = clazz.newInstance();
			setProperty(o, i, exclude, parameterOptionValue);
			Array.set(object, i, o);
		}
	}

	public <U> U create(Class<?> clazz) throws Exception {
		return create(clazz, null);
	}

	@SuppressWarnings("unchecked")
	public <U> U create(Class<?> clazz, List<String> exclude) throws Exception {

		if (isContentTypeJson()) {
			U object = (U) UtilsText.getObjectMapper().readValue(paramJson, clazz);

			Field[] fields = null;

			try {
				fields = FieldUtils.getAllFields(clazz);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (exclude != null) {

				for (Field field : fields) {

					EscapeXSS escapeAnnotation = field.getAnnotation(EscapeXSS.class);
					UnEscapeXSS unEscapeAnnotation = field.getAnnotation(UnEscapeXSS.class);

					boolean isEscapeXSS = (escapeAnnotation != null) ? escapeAnnotation.value() : true;
					boolean isUnEscapeXSS = (unEscapeAnnotation != null) ? true : false;

					field.setAccessible(true);
					Object value = field.get(object);

					if (!exclude.contains(field.getName())) {

						if (value instanceof String) {
							if (value != null && (isEscapeXSS || !isUnEscapeXSS)) {
								value = UtilsText.escapeXss((String) value);
							}
						}
					} else {
						value = null;
					}

					BeanUtils.setProperty(object, field.getName(), value);

				}
			}
			return (U) object;
		}

		Object object = clazz.newInstance();
		setProperty(object, 0, exclude, null);

		return (U) object;
	}

	@SuppressWarnings("unchecked")
	public <U> U createArray(Class<?> clazz, String target) throws Exception {
		return (U) createArray(clazz, target, null);
	}

	/***
	 * 
	 * @Desc : contentType이 application/json일 경우에만 사용 한다.
	 * @Method Name : createArray
	 * @Date : 2019. 5. 14.
	 * @Author : 장진철(zerocooldog@zen9.co.kr
	 * @param clazz
	 * @param exclude
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <U> U createArray(Class<?> clazz) throws Exception {
		return (U) createArray(clazz, Arrays.asList());
	}

	/***
	 * 
	 * @param <U>
	 * @param <U>
	 * @Desc : contentType이 application/json일 경우에만 사용 한다.
	 * @Method Name : createArray
	 * @Date : 2019. 5. 14.
	 * @Author : user
	 * @param clazz
	 * @param exclude
	 * @return
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <U> U createArray(Class<?> clazz, List<String> exclude) throws Exception {
		return createArray(clazz, null, exclude);
	}

	@SuppressWarnings("unchecked")
	public <U> U createArray(Class<?> clazz, String target, List<String> exclude) throws Exception {

		// content type이 application/json 일 경우에 처리.
		if (isContentTypeJson()) {

			U[] objects = (U[]) UtilsText.getObjectMapper().readValue(paramJson, clazz);

			for (Object o : objects) {

				Field[] fields = null;

				try {
					fields = FieldUtils.getAllFields(o.getClass());
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (Field field : fields) {

					EscapeXSS escapeAnnotation = field.getAnnotation(EscapeXSS.class);
					UnEscapeXSS unEscapeAnnotation = field.getAnnotation(UnEscapeXSS.class);

					boolean isEscapeXSS = (escapeAnnotation != null) ? escapeAnnotation.value() : true;
					boolean isUnEscapeXSS = (unEscapeAnnotation != null) ? true : false;

					field.setAccessible(true);
					Object value = field.get(o);

					if (!exclude.contains(field.getName())) {

						if (value instanceof String) {
							if (value != null && (isEscapeXSS || !isUnEscapeXSS)) {
								value = UtilsText.escapeXss((String) value);
							}
						}
					} else {
						value = null;
					}

					BeanUtils.setProperty(o, field.getName(), value);

				}
			}

			return (U) objects;
		}

		String[] parameters = request.getParameterValues(target);
		int parametersLength = parameters != null ? parameters.length : 0;

		Object object = Array.newInstance(clazz, parametersLength);
		setArrayInstance(object, clazz, exclude, null);

		return (U) object;
	}

	@SuppressWarnings("unchecked")
	private <U> U create(Class<?> clazz, List<String> exclude, ParameterOptionValue parameterOptionValue)
			throws Exception {

		Object object = clazz.newInstance();
		setProperty(object, 0, exclude, parameterOptionValue);

		return (U) object;
	}

	@SuppressWarnings("unchecked")
	private <U> U createArray(Class<?> clazz, ParameterOptionValue parameterOptionValue) throws Exception {

		String[] parameters = request.getParameterValues(parameterOptionValue.getParameterName());

		int parametersLength = parameters != null ? parameters.length : 0;

		Object object = Array.newInstance(clazz, parametersLength);
		setArrayInstance(object, clazz, parameterOptionValue.getExclude(), parameterOptionValue);

		return (U) object;
	}

	@Override
	public String getString(String key) {
		return getString(key, null, true);
	}

	@Override
	public String getString(String key, boolean enableXss) {
		return getString(key, null, enableXss);
	}

	public String getString(String key, String defaultValue) {
		return getString(key, defaultValue, true);
	}

	@Override
	public String getString(String key, String defaultValue, boolean enableXss) {

		String value = null;
		if (enableXss) {
			value = UtilsText.escapeXss(request.getParameter(key));
		} else {
			value = request.getParameter(key);
		}

		if (UtilsText.isBlank(value)) {
			value = defaultValue;
		}

		return value;
	}

	@Override
	public String[] getStringArray(String key) {
		return getStringArray(key, true);
	}

	@Override
	public String[] getStringArray(String key, boolean enableXss) {

		String[] values = request.getParameterValues(key);
		String[] valuesNew = null;
		if (values != null) {

			int valuesLength = values.length;
			valuesNew = new String[valuesLength];

			for (int i = 0; i < valuesLength; i++) {
				if (enableXss) {
					valuesNew[i] = UtilsText.escapeXss(values[i]);
				} else {
					valuesNew[i] = values[i];
				}
			}
		}

		return valuesNew;
	}

	@Override
	public char getChar(String key) {
		return getChar(key, Character.MIN_VALUE, true);
	}

	@Override
	public char getChar(String key, boolean enableXss) {
		return getChar(key, Character.MIN_VALUE, enableXss);
	}

	@Override
	public char getChar(String key, char defaultValue) {
		return getChar(key, defaultValue, true);
	}

	@Override
	public char getChar(String key, char defaultValue, boolean enableXss) {

		String value = getString(key, String.valueOf(defaultValue), enableXss);

		if (UtilsText.isBlank(value)) {
			return Character.MIN_VALUE;
		}

		return value.charAt(0);
	}

	@Override
	public char[] getCharArray(String key) {
		return getCharArray(key, true);
	}

	@Override
	public char[] getCharArray(String key, boolean enableXss) {

		String[] values = request.getParameterValues(key);
		char[] valuesNew = null;
		if (values != null) {

			int valuesLength = values.length;
			valuesNew = new char[valuesLength];

			for (int i = 0; i < valuesLength; i++) {
				if (enableXss) {
					valuesNew[i] = UtilsText.escapeXss(values[i]).charAt(0);
				} else {
					valuesNew[i] = values[i].charAt(0);
				}
			}
		}

		return valuesNew;
	}

	@Override
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {

		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}

	@Override
	public boolean[] getBooleanArray(String key) {

		String[] array = getStringArray(key);
		boolean[] bool = null;

		if (array != null) {

			int arrayLength = array.length;

			bool = new boolean[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				String value = array[i];
				if (UtilsText.isBlank(value)) {
					bool[i] = false;
				} else {
					bool[i] = Boolean.parseBoolean(array[i]);
				}
			}
		}

		return bool;
	}

	public List<Boolean> getBooleanList(String key) {

		String[] array = getStringArray(key);
		List<Boolean> bool = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					bool.add(null);
				} else {
					bool.add(Boolean.parseBoolean(array[i]));
				}
			}
		}

		return bool;
	}

	@Override
	public byte getByte(String key) {
		return getByte(key, (byte) 0);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {

		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Byte.parseByte(getString(key));
	}

	@Override
	public byte[] getByteArray(String key) {

		String[] array = getStringArray(key);
		byte[] bytes = null;

		if (array != null) {

			int arrayLength = array.length;

			bytes = new byte[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				String value = array[i];
				if (UtilsText.isBlank(value)) {
					bytes[i] = (byte) 0;
				} else {
					bytes[i] = Byte.parseByte(array[i]);
				}
			}
		}

		return bytes;
	}

	public List<Byte> getByteList(String key) {

		String[] array = getStringArray(key);
		List<Byte> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(Byte.parseByte(removeNumberTypeComma(array[i])));
				}
			}
		}

		return numbers;
	}

	@Override
	public short getShort(String key) {
		return getShort(key, (short) 0);
	}

	@Override
	public short getShort(String key, short defaultValue) {

		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Short.parseShort(removeNumberTypeComma(getString(key)));
	}

	@Override
	public short[] getShortArray(String key) {

		String[] array = getStringArray(key);
		short[] numbers = null;

		if (array != null) {

			int arrayLength = array.length;

			numbers = new short[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers[i] = (short) 0;
				} else {
					numbers[i] = Short.parseShort(removeNumberTypeComma(array[i]));
				}
			}
		}

		return numbers;
	}

	public List<Short> getShortList(String key) {

		String[] array = getStringArray(key);
		List<Short> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(Short.parseShort(removeNumberTypeComma(array[i])));
				}
			}
		}

		return numbers;
	}

	@Override
	public int getInt(String key) {
		return getInt(key, 0);
	}

	@Override
	public int getInt(String key, int defaultValue) {

		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Integer.parseInt(removeNumberTypeComma(getString(key)));
	}

	@Override
	public int[] getIntArray(String key) {

		String[] array = getStringArray(key);
		int[] numbers = null;

		if (array != null) {

			int arrayLength = array.length;

			numbers = new int[arrayLength];
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers[i] = 0;
				} else {
					numbers[i] = Integer.parseInt(removeNumberTypeComma(array[i]));
				}
			}
		}

		return numbers;
	}

	public List<Integer> getIntList(String key) {

		String[] array = getStringArray(key);
		List<Integer> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(Integer.parseInt(removeNumberTypeComma(array[i])));
				}
			}
		}

		return numbers;
	}

	@Override
	public long getLong(String key) {
		return getLong(key, 0L);
	}

	@Override
	public long getLong(String key, long defaultValue) {

		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Long.parseLong(removeNumberTypeComma(getString(key)));

	}

	@Override
	public long[] getLongArray(String key) {

		String[] array = getStringArray(key);
		long[] numbers = null;

		if (array != null) {

			int arrayLength = array.length;

			numbers = new long[arrayLength];
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers[i] = 0l;
				} else {
					numbers[i] = Long.parseLong(removeNumberTypeComma(array[i]));
				}
			}
		}

		return numbers;
	}

	public List<Long> getLongList(String key) {

		String[] array = getStringArray(key);
		List<Long> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(Long.parseLong(removeNumberTypeComma(array[i])));
				}
			}
		}

		return numbers;
	}

	@Override
	public double getDouble(String key) {
		return getDouble(key, 0.0d);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Double.parseDouble(removeNumberTypeComma(getString(key)));
	}

	@Override
	public double[] getDoubleArray(String key) {

		String[] array = getStringArray(key);
		double[] numbers = null;

		if (array != null) {

			int arrayLength = array.length;

			numbers = new double[arrayLength];
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers[i] = 0.0d;
				} else {
					numbers[i] = Double.parseDouble(removeNumberTypeComma(array[i]));
				}
			}
		}

		return numbers;
	}

	public List<Double> getDoubleList(String key) {

		String[] array = getStringArray(key);
		List<Double> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(Double.parseDouble(removeNumberTypeComma(array[i])));
				}
			}
		}

		return numbers;
	}

	@Override
	public float getFloat(String key) {
		return getFloat(key, 0.0f);
	}

	@Override
	public float getFloat(String key, float defaultValue) {

		String value = getString(key);
		if (UtilsText.isBlank(value)) {
			return defaultValue;
		}
		return Float.parseFloat(removeNumberTypeComma(getString(key)));

	}

	@Override
	public float[] getFloatArray(String key) {

		String[] array = getStringArray(key);
		float[] numbers = null;

		if (array != null) {

			int arrayLength = array.length;

			numbers = new float[arrayLength];
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers[i] = 0.0f;
				} else {
					numbers[i] = Float.parseFloat(removeNumberTypeComma(array[i]));
				}
			}
		}
		return numbers;
	}

	public List<Float> getFloatList(String key) {

		String[] array = getStringArray(key);
		List<Float> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(Float.parseFloat(removeNumberTypeComma(array[i])));
				}
			}
		}

		return numbers;
	}

	public List<BigDecimal> getBigDecimalList(String key) {

		String[] array = getStringArray(key);
		List<BigDecimal> numbers = new ArrayList<>();

		if (array != null) {

			int arrayLength = array.length;
			for (int i = 0; i < arrayLength; i++) {

				String value = array[i];
				if (UtilsText.isBlank(value)) {
					numbers.add(null);
				} else {
					numbers.add(new BigDecimal(value));
				}
			}
		}

		return numbers;
	}

	/**
	 * security 권한 체크
	 * 
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role) {
		return getRequest().isUserInRole(role);
	}

	/**
	 * security 권한 체크
	 * 
	 * @param roles 다중 권한,
	 *              ex)hasAnyRole("ROLE_SAMPLE_MANAGER","ROLE_MEMBER_MANAGER")
	 * @return
	 */
	public boolean hasAnyRole(String... roles) {

		for (String role : roles) {

			if (hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	public boolean isMultipartHttpServletRequest() {
		return (request instanceof MultipartHttpServletRequest);
	}

	private MultipartHttpServletRequest getMultipartRequest() throws Exception {

		if (!isMultipartHttpServletRequest()) {
			throw new MultipartTypeException();
		}

		return (MultipartHttpServletRequest) request;

	}

	public FileUpload getUploadFile() throws Exception {
		return getUploadFile(null);
	}

	public FileUpload getUploadFile(String parameterName) throws Exception {
		try {
			MultipartHttpServletRequest multipart = getMultipartRequest();

			MultipartFile mif = multipart.getFile(parameterName);

			if (parameterName == null) {
				// 파라메터가 여러개일 경우 첫번째 것만 읽어온다.
				Iterator<String> names = multipart.getFileNames();
				while (names.hasNext()) {
					mif = multipart.getFile(names.next());
					break;
				}
			} else {
				mif = multipart.getFile(parameterName);
			}

			return (mif != null) ? new FileUpload(mif) : null;

		} catch (Exception e) {
			logger.warn("{}", e.getMessage());
		}

		return null;
	}

	public FileUpload[] getUploadFiles() throws Exception {
		return getUploadFiles(null);
	}

	public FileUpload[] getUploadFiles(String parameterName) throws Exception {
		try {
			MultipartHttpServletRequest multipart = getMultipartRequest();
			List<FileUpload> uploadFiles = new ArrayList<>();
			Iterator<String> names = multipart.getFileNames();

			if (parameterName == null) {
				int k = 0;
				while (names.hasNext()) {
					MultipartFile multipartFile = multipart.getFile(names.next());
					log.debug("multipartFile - {}, {}, {}", multipartFile.isEmpty(), multipartFile.getSize(),
							multipartFile.getName());
					FileUpload uploadFile = new FileUpload(multipart.getFile(names.next()));
					uploadFile.setIndex(k);
					uploadFiles.add(uploadFile);
					k++;
				}
			} else {
				List<MultipartFile> multiFiles = multipart.getFiles(parameterName);
				int multiFilesSize = multiFiles.size();

				for (int i = 0; i < multiFilesSize; i++) {
					MultipartFile multipartFile = multiFiles.get(i);
					log.debug("multipartFile - {}, {}, {}", multipartFile.isEmpty(), multipartFile.getSize(),
							multipartFile.getName());
					FileUpload uploadFile = new FileUpload(multipartFile);
					uploadFile.setIndex(i);
					uploadFiles.add(uploadFile);
				}
			}

			return (uploadFiles.size() > 0) ? (FileUpload[]) uploadFiles.toArray(new FileUpload[uploadFiles.size()])
					: null;
		} catch (Exception e) {
			logger.warn("{}", e.getMessage());
		}

		return null;
	}

	/***
	 * 템플릿 엑셀 파일을 다운로드.
	 * 
	 * @Desc : 템플릿 엑셀 파일을 다운로드. 데이터 없는 템플릿 엑셀 파일만 다운로드
	 * @Method Name : downloadExcelTemplate
	 * @Date : 2019. 3. 12.
	 * @Author : user
	 * @param path
	 * @throws Exception
	 */
	public void downloadExcelTemplate(String path) throws Exception {
		downloadExcelTemplate(path, null);
	}

	/***
	 * 데이터 출력을 할 엑셀 파일을 다운로드. 자동으로 .xlsx 확장자가 붙기 때문에 다운로드할 파일명에는 확장자를 붙이지 않는다. 미리
	 * 만들어 놓은 엑셀 파일을 이용하여 데이터 출력. 데이터가 삽입되는 row 및 cell은 수식 및 스타일 적용이 되지 않는다. 데이터 위에
	 * header 스타일 정도 변경이 필요할 경우 사용한다. ex) List<?> list = getExcelSampleData();
	 * ExcelValue excelValue = ExcelValue.builder(3,1)
	 * .columnNames(Arrays.asList("title","name","age","userId","sample","regDate"))
	 * .data(list) .build();
	 * parameter.downloadExcelTemplate("sample/excel/board2,excelValue)
	 * 
	 * @param path       엑셀 파일 위치. 확장자를 제거한 파일명 까지 포함.
	 * @param excelValue 엑셀 데이터를 출력하기 위해 필요한 정보들이 들어있는 ExcelValue 객체.
	 * @throws Exception
	 */
	public void downloadExcelTemplate(String path, ExcelValue excelValue) throws Exception {

		String fileName = "";
		String subPath = "";

		if (!UtilsText.isBlank(path)) {
			int lastIndex = path.lastIndexOf("/");

			if (lastIndex > -1) {
				subPath = path.substring(0, lastIndex);
				fileName = path.substring(lastIndex + 1, path.length());
			} else {
				fileName = path;
			}
		}

		fileName = fileName.concat(".xlsx");

		downloadExcelTemplate(
				getRequest().getServletContext().getRealPath("/WEB-INF/views".concat("/").concat(subPath)), fileName,
				excelValue);
	}

	/***
	 * 데이터 출력을 할 엑셀 파일을 다운로드. 자동으로 .xlsx 확장자가 붙기 때문에 다운로드할 파일명에는 확장자를 붙이지 않는다. 미리
	 * 만들어 놓은 엑셀 파일을 이용하여 데이터 출력. 데이터가 삽입되는 row 및 cell은 수식 및 스타일 적용이 되지 않는다. 데이터 위에
	 * header 스타일 정도 변경이 필요할 경우 사용한다. ex) List<?> list = getExcelSampleData();
	 * ExcelValue excelValue = ExcelValue.builder(3,1)
	 * .columnNames(Arrays.asList("title","name","age","userId","sample","regDate"))
	 * .data(list) .build();
	 * parameter.downloadExcelTemplate("d:\\directory","sample",excelValue)
	 * 
	 * @param filePath   엑셀 파일 위치.
	 * @param fileName   확장자를 제거한 파일명
	 * @param excelValue 엑셀 데이터를 출력하기 위해 필요한 정보들이 들어있는 ExcelValue 객체.
	 * @throws Exception
	 */
	public void downloadExcelTemplate(String filePath, String fileName, ExcelValue excelValue) throws Exception {

		if (!fileName.endsWith(".xlsx")) {
			fileName = fileName.concat(".xlsx");
		}

		Path path = Paths.get(UtilsText.concat(filePath.concat(File.separator), fileName));
		byte[] bytes = Files.readAllBytes(path);

		ByteArrayResource resource = new ByteArrayResource(bytes);
		try (InputStream is = new BufferedInputStream(resource.getInputStream())) {

			ExcelProcessor excelProcessor = new ExcelProcessor(new XSSFWorkbook(is));

			if (excelValue != null) {
				setExcelPorcessor(excelProcessor, excelValue, true);
			}

			getResponse().setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment;filename=".concat(URLEncoder.encode(fileName, BaseConst.DEFAULT_CHARSET_UTF_8)));
			excelProcessor.write(getResponse().getOutputStream());
			excelProcessor.dispose();

		} finally {
//			getResponse().flushBuffer();
		}
	}

	/***
	 * 데이터 삽입한 엑셀 파일을 다운로드. 자동으로 .xlsx 확장자가 붙기 때문에 다운로드할 파일명에는 확장자를 붙이지 않는다. ex)
	 * List<?> list = getExcelSampleData(); ExcelValue excelValue =
	 * ExcelValue.builder(3,1)
	 * .columnNames(Arrays.asList("title","name","age","userId","sample","regDate"))
	 * .data(list) .build(); parameter.downloadExcelTemplate("board",excelValue)
	 * 
	 * @param path       엑셀 파일 위치. 확장자를 제거한 파일명 까지 포함.
	 * @param excelValue 엑셀 데이터를 출력하기 위해 필요한 정보들이 들어있는 ExcelValue 객체.
	 * @throws Exception
	 */
	public void downloadExcel(String fileName, ExcelValue excelValue) throws Exception {

		ExcelProcessor excelProcessor = new ExcelProcessor();
		setExcelPorcessor(excelProcessor, excelValue, false);

		try {
			getResponse().setStatus(HttpStatus.OK.value());
			getResponse().setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="
					.concat(URLEncoder.encode(fileName, BaseConst.DEFAULT_CHARSET_UTF_8)).concat(".xlsx"));
			excelProcessor.write(getResponse().getOutputStream());
			excelProcessor.dispose();
		} finally {
			getResponse().flushBuffer();
		}
	}

	private void setExcelPorcessor(ExcelProcessor excelProcessor, ExcelValue excelValue, boolean template)
			throws Exception {

		if (excelValue.getExclude() != null) {
			excelProcessor.setExcludeColumn(excelValue.getExclude());
		}

		if (excelValue.getColumnNames() instanceof List) {
			excelProcessor.addColumnNames((List<String>) excelValue.getColumnNames());
		} else if (excelValue.getColumnNames() instanceof LinkedHashMap) {
			excelProcessor.addColumnNames((LinkedHashMap<String, String>) excelValue.getColumnNames());
		}

		excelProcessor.setTemplate(template);
		excelProcessor.setTopPosition(excelValue.getTopPosition());
		excelProcessor.setLeftPosition(excelValue.getLeftPosition());

		if (excelValue.getData() instanceof List) {
			excelProcessor.addData((List<?>) excelValue.getData());
		} else if (excelValue.getData() instanceof String[]) {
			excelProcessor.addData((String[]) excelValue.getData());
		} else if (excelValue.getData() instanceof int[]) {
			excelProcessor.addData((int[]) excelValue.getData());
		} else if (excelValue.getData() instanceof long[]) {
			excelProcessor.addData((long[]) excelValue.getData());
		} else if (excelValue.getData() instanceof float[]) {
			excelProcessor.addData((float[]) excelValue.getData());
		} else if (excelValue.getData() instanceof double[]) {
			excelProcessor.addData((double[]) excelValue.getData());
		} else {
			throw new Exception("해당 데이터 타입을 지원하지 않습니다.");
		}
	}

	public ResponseEntity<ByteArrayResource> download(FileDownload download) throws Exception {

		Path path = Paths.get(download.getFilePath());
		byte[] data = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename="
								.concat(URLEncoder.encode(download.getOrgFileName(), BaseConst.DEFAULT_CHARSET_UTF_8)))
				.contentType(download.getContentType()).contentLength(data.length).body(resource);

	}

	public ResponseEntity<ByteArrayResource> download(String filePath, String orgFileName) throws Exception {

		FileDownload download = new FileDownload();
		download.setFilePath(filePath);
		download.setOrgFileName(orgFileName);

		return download(download);
	}

	public ResponseEntity<ByteArrayResource> download(String contentType, String filePath, String orgFileName)
			throws Exception {

		FileDownload download = new FileDownload();
		download.setContentType(MediaType.parseMediaType(contentType));
		download.setFilePath(filePath);
		download.setOrgFileName(orgFileName);

		return download(download);
	}

	/***
	 * 모바일,pc,table 정보를 가지고 있는 Device 객체 가져온다.
	 * 
	 * @return
	 */
	public Device getDevice() {
		return UtilsDevice.getDevice(getRequest());
	}

	/**
	 * 접속한 사용자가 모바일 인지 확인
	 * 
	 * @return boolean
	 */
	public boolean isMobile() {
		return UtilsDevice.isMobile(getRequest());
	}

	/**
	 * 접속한 사용자가 PC 인지 확인
	 * 
	 * @return boolean
	 */
	public boolean isPc() {
		return UtilsDevice.isPc(getRequest());
	}

	/**
	 * 접속한 사용자가 Tablet 인지 확인
	 * 
	 * @return boolean
	 */
	public boolean isTablet() {
		return UtilsDevice.isTablet(getRequest());
	}

	public boolean isApp() {
		return UtilsDevice.isApp(getRequest());
	}

	public boolean isAppIOS() {
		return UtilsDevice.isAppIOS(getRequest());
	}

	public boolean isAppAOS() {
		return UtilsDevice.isAppAOS(getRequest());
	}

	public boolean isFace() {
		return UtilsDevice.isFace(getRequest());
	}

	public boolean isFinger() {
		return UtilsDevice.isFinger(getRequest());
	}

	/**
	 * 접속한 사용자가 IOS인지 ANDROID 인지 그 외 알수 없는 기기 인지 확인
	 * 
	 * @return boolean
	 */
	public DevicePlatform getDevicePlatform() {
		return getDevice().getDevicePlatform();
	}

	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다.
	 * 
	 * @param validator Validator를 구현한 클래스
	 * @throws ValidatorException
	 */
	@Override
	public void validate(Validator validator) throws ValidatorException {

		if (validator != null) {
			validator.validate();
		}
	}

	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다.
	 * 
	 * @param validator Validator를 구현한 클래스
	 * @throws ValidatorException
	 */
	@Override
	public void validate(Validator[] validators) throws ValidatorException {

		if (validators != null) {
			for (Validator validator : validators) {
				validator.validate();
			}
		}
	}

	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다.
	 * 
	 * @param validator Validator를 구현한 클래스
	 * @throws ValidatorException
	 */
	@Override
	public void validate() throws ValidatorException {

		if ((get() instanceof Validator)) {
			Validator validator = (Validator) get();
			validator.validate();
		} else if (get() instanceof Validator[]) {
			Validator[] validators = (Validator[]) get();

			for (Validator validator : validators) {
				validator.validate();
			}
		}
	}

}
