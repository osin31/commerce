package kr.co.abcmart.common.client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.client.util.HttpAsyncClientUtils;

import kr.co.abcmart.common.Common;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetClient extends Common {

	private CloseableHttpAsyncClient client;
	private String url;
	private Map<String, String> header;
	private Class<?> clazz;
	private Object parameterObject;
	private List<BasicNameValuePair> nameValuePair;
	private MethodType method;
	private String contentType;
	private Charset responseEncoding = StandardCharsets.UTF_8;

	public NetClient(ClientParameter parameter) {

		if (parameter != null) {

			this.url = parameter.getUrl();
			this.header = parameter.getHeader();
			this.clazz = parameter.getClazz();
			this.parameterObject = parameter.getParameterObject();
			this.method = parameter.getMethod();
			this.nameValuePair = parameter.getNameValuePair();
			this.contentType = parameter.getContentType();
			this.responseEncoding = parameter.getResponseEncoding() == null ? StandardCharsets.UTF_8
					: parameter.getResponseEncoding();
		}

		if (url.indexOf("https") > -1 || url.indexOf("HTTPS") > -1) {
			try {
				TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] certificate, String authType) {
						return true;
					}
				};
				SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

				this.client = HttpAsyncClients.custom()
						.setSSLHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
						.setSSLContext(sslContext).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.client = HttpAsyncClients.custom().build();
		}

	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public NetResult<?> call() {

		NetResult<?> restResult = new NetResult<>();
		HttpRequestBase request = null;
		String queryString = "";

		try {

			client.start();

			if (nameValuePair != null && !nameValuePair.isEmpty()) {
				queryString = URLEncodedUtils.format(nameValuePair, BaseConst.DEFAULT_CHARSET_UTF_8);
			}

			switch (method) {
			case GET:
				request = new HttpGet(concatQueryString(url, queryString));
				break;
			case POST:
				request = new HttpPost(concatQueryString(url, queryString));
				String parameter = UtilsText.getObjectMapper().writeValueAsString(parameterObject);
				request.setHeader("Content-Type", "application/json");

				if (!"{}".equals(parameter)) {
					StringEntity entity = new StringEntity(parameter, responseEncoding.displayName());
					((HttpPost) request).setEntity(entity);
				}

				break;
			case PUT:
				request = new HttpPut(concatQueryString(url, queryString));
				break;
			case DELETE:
				request = new HttpDelete(concatQueryString(url, queryString));
				break;
			}

			if (header != null) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					if (request != null) {
						request.setHeader(entry.getKey(), entry.getValue());
					}
				}
			}

			if (request != null && !UtilsText.isBlank(contentType)) {
				request.setHeader("Content-Type", contentType);
			}

			Future<HttpResponse> future = client.execute(request, null);
			HttpResponse response = future.get();

			int statusCode = response.getStatusLine().getStatusCode();

			restResult.setStatus(statusCode);
			restResult.setMessage(response.getStatusLine().getReasonPhrase());

			if (statusCode == HttpStatus.SC_OK) {
				Object o = null;

				if (!clazz.isAssignableFrom(String.class)) {
					o = UtilsText.getObjectMapper().readValue(response.getEntity().getContent(), clazz);
				} else {
					o = IOUtils.toString(response.getEntity().getContent(), responseEncoding);
				}

				restResult.setData(clazz, o);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof java.net.ConnectException || e.getMessage().indexOf("java.net.ConnectException") > -1) {
				restResult.setStatus(HttpStatus.SC_BAD_REQUEST);
			} else {
				restResult.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			}
			restResult.setMessage(e.getMessage());
			restResult.setData(HashMap.class, new HashMap<>());

		} finally {
			HttpAsyncClientUtils.closeQuietly(client);
		}

		return restResult;

	}

	private String concatQueryString(String url, String queryString) {

		if (UtilsText.isBlank(queryString)) {
			return url;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(url);

		if (url.indexOf('?') == -1) {
			sb.append("?");
		}

		if (!url.endsWith("&")) {
			sb.append("&");
		}

		sb.append(queryString);

		return sb.toString();

	}

	public static void main(String[] args) {

		SampleREST vo = new SampleREST();
		vo.setId("zzzzzzzzzzzzzz");

//		RESTParameter restParameter = RESTParameter.build()
//										.setTarget("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class)
//										.setParameterObject(vo)
//										.setMethod(MethodType.GET)
//										.addParameter("18", "181818")
//										.addParameter("28", 252525)
//										.addParameter("ff", Arrays.asList("a","b","c"));

		ClientParameter restParameter = ClientParameter.builder()
				.target("http://localbo.a-rt.kr:9200/schedule/resume-trigger", SampleREST.class)
//										.parameterObject(vo)
				.method(MethodType.GET).addParameter("18", "181818").addParameter("28", 252525)
//										.addParameter("ff", Arrays.asList("a","b","c"));
				.build();
		NetClient rest = new NetClient(restParameter);

		NetResult<?> result = rest.call();
//		SampleREST sample = result.getData();

//		SampleREST sample = REST.POST(restParameter,true);
//		SampleREST sample = REST.POST("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class,true);

//		SampleREST sample = result.getData();

//		System.out.println("######### " +sample);
////
//		System.out.println(result.getResult());
		System.out.println(result.getStatus());
		System.out.println(result.getMessage());

//		if(sample != null) {
//			System.out.println(sample.getId());
//			System.out.println(sample.getUserId());
//			System.out.println(sample.getTitle());
//			System.out.println(sample.getBody());			
//		}

	}
}