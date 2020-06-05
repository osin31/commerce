package kr.co.abcmart.common.util;

import java.net.MalformedURLException;
import java.net.URL;

import kr.co.abcmart.common.client.ClientParameter;
import kr.co.abcmart.common.client.MethodType;
import kr.co.abcmart.common.client.NetClient;
import kr.co.abcmart.common.client.NetResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsNet {

	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다.
	 * @Method Name : send
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * @param clientParameter
	 * @return NetResult<?> or NetResult<생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : NetResult<SampleREST>
	 * 
	 * 	 ex)
	 * 		ClientParameter restParameter = ClientParameter.builder()
	 *			.target("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.method(MethodType.GET)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *
	 *      또는
	 *      
	 * 		ClientParameter restParameter = ClientParameter.builder("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.method(MethodType.GET)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *      
	 *      
	 *      NetResult<String> netResult = UtilsNet.send(restParameter);
	 *      if(netResult != null) {
	 *      	netResult.getStatus();
	 *      	netResult.getType();
	 *      	netResult.getMessage();
	 *			SampleREST sample = netResult.getData();
	 *		}
	 */
	public static <U> NetResult<U> send(ClientParameter clientParameter) {
		
		NetClient rest = new NetClient(clientParameter);
		@SuppressWarnings("unchecked")
		NetResult<U> result = (NetResult<U>)rest.call();
		
		return result;
	}
	
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다.
	 * @Method Name : send
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @param method httpMethod
	 * @param clazz 응답 데이터 매핑 클래스.
	 * @return NetResult<?> or NetResult<생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : NetResult<SampleREST>
	 * 
	 * 	 ex)
	 *      NetResult<SampleREST> netResult = UtilsNet.send("https://jsonplaceholder.typicode.com/posts/42", "post", SampleREST.class);
	 *      if(netResult != null) {
	 *      	netResult.getStatus();
	 *      	netResult.getType();
	 *      	netResult.getMessage();
	 *			SampleREST sample = netResult.getData();
	 *		}
	 */
	public static <U> NetResult<U> send(String url, String method, Class<?> clazz) {

		NetClient rest = new NetClient(
							ClientParameter
								.builder(url,clazz)
								.method(method)
							.build()
						);
		
		@SuppressWarnings("unchecked")
		NetResult<U> result = (NetResult<U>)rest.call();
		
		return result;
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 get 이다.
	 * @Method Name : send
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @param clazz 응답 데이터 매핑 클래스.
	 * @return NetResult<?> or NetResult<생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : NetResult<SampleREST>
	 * 
	 * 	 ex)
	 *      NetResult<SampleREST> netResult = UtilsNet.send("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
	 *      if(netResult != null) {
	 *      	netResult.getStatus();
	 *      	netResult.getType();
	 *      	netResult.getMessage();
	 *			SampleREST sample = netResult.getData();
	 *		}
	 */
	public static <U> NetResult<U> send(String url, Class<?> clazz) {
		return send(url, MethodType.GET.name(), clazz);
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 get 이다.
	 * @Method Name : send
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @param clazz 응답 데이터 매핑 클래스.
	 * @return clazz param 값으로  보낸 응답 데이터 매핑 클래스.
	 * 
	 * 	 ex)
	 *      SampleREST sampleRest = UtilsNet.get("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U get(String url, Class<?> clazz) {
		return UtilsNet.send(url, MethodType.GET.name(), clazz).getData();
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 문자열로 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 get 이다.
	 * @Method Name : send
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @return String
	 * 
	 * 	 ex)
	 *      String resultText = UtilsNet.get("https://jsonplaceholder.typicode.com/posts/42");
	 *      if(sampleRest != null) {
	 *      	log.debug("resultText {}", resultText);
	 *		}
	 */
	public static String get(String url) {
		return get(url, String.class);
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 get 이다.
	 * @Method Name : get
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * @param clientParameter
	 * @return 생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : SampleREST
	 * 
	 * 	 ex)
	 * 		ClientParameter restParameter = ClientParameter.builder()
	 *			.target("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *
	 *      또는
	 *      
	 * 		ClientParameter restParameter = ClientParameter.builder("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *      
	 *      SampleREST sampleRest = UtilsNet.get(restParameter);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static String get(ClientParameter clientParameter) {
		return UtilsNet.send(clientParameter).getData();
	}
	
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 post 이다.
	 * @Method Name : post
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @param clazz 응답 데이터 매핑 클래스.
	 * @return clazz param 값으로  보낸 응답 데이터 매핑 클래스.
	 * 
	 * 	 ex)
	 *      SampleREST sampleRest = UtilsNet.post("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U post(String url, Class<?> clazz) {
		return UtilsNet.send(url, MethodType.POST.name(), clazz).getData();
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 post 이다.
	 * @Method Name : post
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @return String
	 * 
	 * 	 ex)
	 *      String resultText = UtilsNet.post("https://jsonplaceholder.typicode.com/posts/42");
	 *      if(resultText != null) {
	 *      	log.debug("resultText {}", resultText);
	 *		}
	 */
	public static String post(String url) {
		return post(url, String.class);
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 post 이다.
	 * @Method Name : post
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * @param clientParameter
	 * @return 생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : SampleREST
	 * 
	 * 	 ex)
	 * 		ClientParameter restParameter = ClientParameter.builder()
	 *			.target("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *
	 *      또는
	 *      
	 * 		ClientParameter restParameter = ClientParameter.builder("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *      
	 *      SampleREST sampleRest = UtilsNet.post(restParameter);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U post(ClientParameter clientParameter) {
		return UtilsNet.send(clientParameter).getData();
	}

	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 put 이다.
	 * @Method Name : put
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @param clazz 응답 데이터 매핑 클래스.
	 * @return clazz param 값으로  보낸 응답 데이터 매핑 클래스.
	 * 
	 * 	 ex)
	 *      SampleREST sampleRest = UtilsNet.put("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U put(String url, Class<?> clazz) {
		return UtilsNet.send(url, MethodType.PUT.name(), clazz).getData();
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 put 이다.
	 * @Method Name : put
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @return String
	 * 
	 * 	 ex)
	 *      String resultText = UtilsNet.put("https://jsonplaceholder.typicode.com/posts/42");
	 *      if(resultText != null) {
	 *      	log.debug("resultText {}", resultText);
	 *		}
	 */
	public static String put(String url) {
		return put(url, String.class);
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 put 이다.
	 * @Method Name : put
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * @param clientParameter
	 * @return 생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : SampleREST
	 * 
	 * 	 ex)
	 * 		ClientParameter restParameter = ClientParameter.builder()
	 *			.target("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *
	 *      또는
	 *      
	 * 		ClientParameter restParameter = ClientParameter.builder("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *      
	 *      SampleREST sampleRest = UtilsNet.put(restParameter);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U put(ClientParameter clientParameter) {
		clientParameter.setMethod(MethodType.PUT);
		return UtilsNet.send(clientParameter).getData();
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 delete 이다.
	 * @Method Name : delete
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @param clazz 응답 데이터 매핑 클래스.
	 * @return clazz param 값으로  보낸 응답 데이터 매핑 클래스.
	 * 
	 * 	 ex)
	 *      SampleREST sampleRest = UtilsNet.delete("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U delete(String url, Class<?> clazz) {
		return UtilsNet.send(url, MethodType.DELETE.name(), clazz).getData();
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 delete 이다.
	 * @Method Name : delete
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * 
	 * @param url 외부 호출 URL
	 * @return String
	 * 
	 * 	 ex)
	 *      String resultText = UtilsNet.delete("https://jsonplaceholder.typicode.com/posts/42");
	 *      if(resultText != null) {
	 *      	log.debug("resultText {}", resultText);
	 *		}
	 */
	public static String delete(String url) {
		return delete(url, String.class);
	}
	
	/***
	 * 
	 * @Desc      	: 외부 URL을 호출 한다. 데이터 뿐만 아니라 응답 코드, 응답 메세지 등을 리턴 받는다. HttpMethod 기본 값은 delete 이다.
	 * @Method Name : delete
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: 장진철 (zerocooldog)
	 * @param clientParameter
	 * @return 생성자 ClientParameter.builder(url,클래스.class) 및 target(url,클래스.class) 로 보낸 클래스> , 샘플 : SampleREST
	 * 
	 * 	 ex)
	 * 		ClientParameter restParameter = ClientParameter.builder()
	 *			.target("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *
	 *      또는
	 *      
	 * 		ClientParameter restParameter = ClientParameter.builder("https://jsonplaceholder.typicode.com/posts/42", String.class)
	 *			.addParameter("18", "181818")
	 *			.addParameter("28", 252525)
	 *			.addParameter("ff", Arrays.asList("a","b","c"));
	 *		.build();
	 *      
	 *      SampleREST sampleRest = UtilsNet.delete(restParameter);
	 *      if(sampleRest != null) {
	 *      	log.debug("SampleREST id {}",sample.getId());
	 *			log.debug("SampleREST title {}",sample.getTitle());
	 *			log.debug("SampleREST body {}",sample.getBody());
	 *		}
	 */
	public static <U> U delete(ClientParameter clientParameter) {
		clientParameter.setMethod(MethodType.DELETE);
		return UtilsNet.send(clientParameter).getData();
	}
	
	/**
	 * 
	 * @Desc      	: URL 정보를 URL 객체로 변환한다.
	 * @Method Name : getURL
	 * @Date  	  	: 2019. 2. 21.
	 * @Author    	: zerocooldog
	 * @param urlString url ex) "http://www.art-kr.com/sample" or "http://www.art-kr.com"
	 * @return URL
	 */
	public static URL getURL(String urlString) {
        try {
			return new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        return null;
    }
	
}
