package kr.co.abcmart.common.request.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.co.abcmart.common.constant.BaseConst;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterDateTimeFormat {

	String value() default BaseConst.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS;

}
