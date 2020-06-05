package kr.co.abcmart.zconfiguration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.TemplateException;

@Configuration
public class EmailConfig {
	
    
	/***
	 * freeMarker를 이용한 템플릿을 작성할 때 사용한다.
	 * 해당 프로젝트에서는 메일 템플릿을 위해 사용한다.
	 * @return
	 * @throws TemplateException 
	 * @throws IOException 
	 */
    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfig() throws IOException, TemplateException {
        
    	FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        
        return bean;
    }
}