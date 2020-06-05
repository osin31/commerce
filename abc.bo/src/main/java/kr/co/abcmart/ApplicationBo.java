package kr.co.abcmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;

import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.util.UtilsMail;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication(
		exclude = {
				DataSourceAutoConfiguration.class,
				DataSourceTransactionManagerAutoConfiguration.class,
				FreeMarkerAutoConfiguration.class
		}
)
@PropertySource(value = {
	"classpath:app-config.properties",
	"classpath:datasource.properties" 
}, ignoreResourceNotFound = true)

//transaction AOP
@ImportResource("classpath:transaction.xml")
public class ApplicationBo implements ApplicationRunner  {
	
	@Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private freemarker.template.Configuration freeMarkerConfig;
    
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBo.class, args);
	}

	/**
	 * 어플리케이션 구동 전 초기화 작업을 이곳에서 한다.
	 * 해당 영역은 java arguments값들을 처리 할 수 있도록 도와 준다.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {

		//email 유틸 설정
		UtilsMail.init(javaMailSender, freeMarkerConfig);
		
		log.debug("환경 변수 log.path {} ",Config.getString("log.path"));
		
	}
}