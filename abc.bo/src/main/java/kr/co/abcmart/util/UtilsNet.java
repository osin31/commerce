package kr.co.abcmart.util;
/**
 * 
 * @Desc      : 외부 URL 접속 및 외부 통신의 별도의 로직이 필요할 경우 사용.
 * @FileName  : UtilsNet.java
 * @Project   : abc.bo
 * @Date  	  : 2019. 2. 21.
 * @Author    : 장진철 zerocooldog
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

 *      NetResult<SampleREST> netResult = UtilsNet.send("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
 *      if(netResult != null) {
 *      	netResult.getStatus();
 *      	netResult.getType();
 *      	netResult.getMessage();
 *			SampleREST sample = netResult.getData();
 *		}
 *
 *      SampleREST sampleRest = UtilsNet.get("https://jsonplaceholder.typicode.com/posts/42", SampleREST.class);
 *      if(sampleRest != null) {
 *      	log.debug("SampleREST id {}",sample.getId());
 *			log.debug("SampleREST title {}",sample.getTitle());
 *			log.debug("SampleREST body {}",sample.getBody());
 *		}
 *
 *      String resultText = UtilsNet.get("https://jsonplaceholder.typicode.com/posts/42");
 *      if(sampleRest != null) {
 *      	log.debug("resultText {}", resultText);
 *		}
 */
public class UtilsNet extends kr.co.abcmart.common.util.UtilsNet{

}
