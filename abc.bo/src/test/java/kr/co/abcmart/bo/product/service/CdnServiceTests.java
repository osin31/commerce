package kr.co.abcmart.bo.product.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

//@SpringBootTest
@Slf4j
public class CdnServiceTests {

//	@Mock
//	HttpServletRequest request;
//
//	@Mock
//	HttpServletResponse response;

	public static final String CDN_API_DOMAIN = "https://openapi.kr.cdnetworks.com";
	public static final String CDN_API_METHOD = "/purge/rest/doPurge?";
	public static final String ABC_USER = "abcmartmobile@gmail.com";
	public static final String ABC_PASS = "Abcmart!2019";
	public static final String ABC_PAD = Config.getString("url.images.domain");

//	@Before
//	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//	}

	@Test
	public void test() throws Exception {
		send("/product/product/hqdefault_1560239729634.jpg", "item");
	}

	public void send(String path, String pathType) {

		String apiUrl = UtilsText.concat(CDN_API_DOMAIN, CDN_API_METHOD);
		String param = UtilsText.concat("user=", ABC_USER, "&pass=", ABC_PASS, "&pad=", "devimg.a-rt.com", "&type=",
				pathType, "&path=", path, "&output=", "json");

		log.debug("api url : {}", UtilsText.concat(apiUrl, param));

		CloseableHttpClient httpClient = HttpClients.createDefault();
		// get 메서드와 URL 설정
		HttpGet httpGet = new HttpGet(UtilsText.concat(apiUrl, param));

		// agent 정보 설정
		httpGet.addHeader("User-Agent", "Mozila/5.0");
		httpGet.addHeader("Content-type", "application/json");

		// get 요청
		CloseableHttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			log.debug("GET Response Status");
			log.debug("" + httpResponse.getStatusLine().getStatusCode());
			String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

			log.debug("result : {}", json);

			httpClient.close();
		} catch (Exception e) {
			log.error("cnd api error : {}", e);
		} finally {

		}

	}

}
