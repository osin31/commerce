package kr.co.abcmart.common.request;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import kr.co.abcmart.common.request.annotation.SubDomainMapping;

public class SubDomainMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
       SubDomainMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, SubDomainMapping.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        SubDomainMapping methodAnnotation = AnnotationUtils.findAnnotation(method, SubDomainMapping.class);
        return createCondition(methodAnnotation);
    }

    private RequestCondition<?> createCondition(SubDomainMapping accessMapping) {
        return (accessMapping != null) ? new SubdomainRequestCondition(accessMapping.tld(), accessMapping.value()) : null;
    }

}