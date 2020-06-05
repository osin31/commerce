package kr.co.abcmart.zconfiguration;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.zconfiguration.filter.CustomCharacterEncodingFilter;

@Configuration
public class EncodingConfig {
	/**
	 * reponse 결과를 화면에 출력 시 강제로 UTF-8로 설정
	 * 
	 * @return HttpMessageConverter
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName(BaseConst.DEFAULT_CHARSET_UTF_8));
	}

	@Bean
	@Order
	public CharacterEncodingFilter characterEncodingFilter() {

		CustomCharacterEncodingFilter characterEncodingFilter = new CustomCharacterEncodingFilter();
		characterEncodingFilter.setEncoding(BaseConst.DEFAULT_CHARSET_UTF_8);
		characterEncodingFilter.setForceEncoding(true);

		// 외부에서 전달해주는 파라메터가 /main/init으로 이동 하면 euc-kr로 파라메터 값을 받는다.
//		characterEncodingFilter.addUriToEncoding("/main/init", "euc-kr");
		return characterEncodingFilter;
	}
}
