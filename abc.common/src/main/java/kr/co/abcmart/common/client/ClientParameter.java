package kr.co.abcmart.common.client;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/***
 * Net Parameter 정보를 builder 형식으로 처리한다.
 * 
 * @author 장진철 zerocooldog
 */

@Slf4j
public class ClientParameter {

	@Getter
	private List<BasicNameValuePair> nameValuePair = new ArrayList();

	@Getter
	private String url;

	@Getter
	private Map<String, String> header;

	@Getter
	private Class<?> clazz;

	@Getter
	private Object parameterObject;

	@Getter
	@Setter
	private MethodType method = MethodType.POST;

	@Getter
	@Setter
	private String contentType;

	@Getter
	private Charset responseEncoding;

	public static class Builder {

		private List<BasicNameValuePair> nameValuePair = new ArrayList();

		private String url;

		private Map<String, String> header = new HashMap<>();

		private Class<?> clazz;

		private Object parameterObject;

		private MethodType method = MethodType.POST;

		private String contentType;

		private Charset responseEncoding;

		public Builder() {
		}

		public Builder(String url) {
			this.url = url;
		}

		public Builder(String url, Class<?> clazz) {
			this.url = url;
			this.clazz = clazz;
		}

		public Builder target(String url, Class<?> clazz) {
			this.url = url;
			this.clazz = clazz;
			return this;
		}

		public Builder header(Map<String, String> header) {
			this.header = header;
			return this;
		}

		public Builder addHeader(String headerName, String headerValue) {
			this.header.put(headerName, headerValue);
			return this;
		}

		public Builder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder responseEncoding(String encoding) {

			if (encoding != null) {
				encoding = encoding.toUpperCase();
			}

			this.responseEncoding = Charset.forName(encoding);
			return this;
		}

		public Builder method(MethodType method) {
			this.method = method;
			return this;
		}

		public Builder method(String method) {

			if (method != null) {

				if ("get".equalsIgnoreCase(method)) {
					this.method = MethodType.GET;
				} else if ("post".equalsIgnoreCase(method)) {
					this.method = MethodType.POST;
				} else if ("put".equalsIgnoreCase(method)) {
					this.method = MethodType.PUT;
				} else if ("delete".equalsIgnoreCase(method)) {
					this.method = MethodType.DELETE;
				}
			}

			return this;
		}

		public Builder parameterObject(Object params) {
			this.parameterObject = params;
			return this;
		}

		public Builder parameter(List<BasicNameValuePair> parameters) {
			this.nameValuePair = parameters;
			return this;
		}

		public Builder addParameter(String key, BigDecimal value) {
			addParameter(key, value.toString());
			return this;
		}

		public Builder addParameter(String key, long value) {
			addParameter(key, Long.toString(value));
			return this;
		}

		public Builder addParameter(String key, float value) {
			addParameter(key, Float.toString(value));
			return this;
		}

		public Builder addParameter(String key, double value) {
			addParameter(key, Double.toString(value));
			return this;
		}

		public Builder addParameter(String key, int value) {
			addParameter(key, Integer.toString(value));
			return this;
		}

		public Builder addParameter(String key, char value) {
			addParameter(key, Character.toString(value));
			return this;
		}

		public Builder addParameter(String key, boolean value) {
			addParameter(key, Boolean.toString(value));
			return this;
		}

		public Builder addParameter(String key, List<?> values) {

			if (values != null) {

				for (Object value : values) {

					if (value instanceof Integer) {
						addParameter(key, (Integer) value);
					} else if (value instanceof Long) {
						addParameter(key, (Long) value);
					} else if (value instanceof Double) {
						addParameter(key, (Double) value);
					} else if (value instanceof Float) {
						addParameter(key, (Float) value);
					} else if (value instanceof BigDecimal) {
						addParameter(key, (BigDecimal) value);
					} else if (value instanceof Boolean) {
						addParameter(key, (Boolean) value);
					} else if (value instanceof Character) {
						addParameter(key, (Character) value);
					} else {
						addParameter(key, (String) value);
					}
				}
			}

			return this;
		}

		public Builder addParameter(String key, String value) {
			nameValuePair.add(new BasicNameValuePair(key, value));
			return this;

		}

		public ClientParameter build() {
			return new ClientParameter(this);
		}
	}

	private ClientParameter(Builder builder) {

		this.nameValuePair = builder.nameValuePair;
		this.url = builder.url;
		this.header = builder.header;
		this.clazz = builder.clazz;
		this.parameterObject = builder.parameterObject;
		this.method = builder.method;
		this.contentType = builder.contentType;
		this.responseEncoding = builder.responseEncoding;

	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(String url) {
		return new Builder(url);
	}

	public static Builder builder(String url, Class<?> clazz) {
		return new Builder(url, clazz);
	}
}
