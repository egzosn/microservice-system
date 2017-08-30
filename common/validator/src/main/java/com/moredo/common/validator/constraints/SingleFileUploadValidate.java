package com.moredo.common.validator.constraints;

import com.moredo.common.validator.constraintvalidators.SingleFileUploadValidateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target( { METHOD, FIELD, ANNOTATION_TYPE,PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = SingleFileUploadValidateImpl.class)
/**
 *  @author  Linjie
 *  验证上传文件的正确性，可验证大小是否合适，文件格式是否正确等。
 *  可直接设置参数值:size 和 contentTypes 两个参数
 *  size 单位为KB, 默认为 0 ，代表不限制，contentTypes 默认为空数组，代表不限制。
 *  如:
 *  size = 1000000l
 *  contentTypes = {"image/png","image/jpg"}
 *
 *  也可以采用配置文件(useConfig)，配置文件和size,contentTypes 为互斥，useConfig优先
 */
public @interface SingleFileUploadValidate {

    /**
     * @return
     */
    long size()  default  0l;
    String[] contentTypes() default {};

    /**
     * useConfig ，字符串类型，与配置文件参数名的前缀对应，
     * 比如前缀foods ,那么对应的配置就是:
     * foods_allow_size_kb
     * foods_allow_content_type
     * 这个配置文件在WEB-INF/classes 目录下
     * @return
     */
    String useConfig() default "";
    boolean allowEmpty() default false;
    String message() default "文件不存在！";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
