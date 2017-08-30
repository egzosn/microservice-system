package com.moredo.common.validator.constraints;

import com.moredo.common.validator.constraintvalidators.MultipartFilesUploadValidateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ZaoSheng on 2015/7/2.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = MultipartFilesUploadValidateImpl.class)
/**
 *  @author Linjie
 *  多文件
 *  验证上传文件的正确性，可验证大小是否合适，文件格式是否正确等。
 *  可直接设置参数值:size 和 contentTypes 两个参数
 *  size 单位为KB, 默认为 0 ，代表不限制，contentTypes 默认为空数组，代表不限制。
 *  如:
 *  size = 1000000l
 *  contentTypes = {"image/png","image/jpg"}
 *
 *  也可以采用配置文件(useConfig)，配置文件和size,contentTypes 为互斥，useConfig优先
 */
public @interface MultipartFilesUploadValidate {

    /**
     * @return
     */
    long totalSize() default 0l; //

    /**
     * 单文件大小
     *
     * @return
     */
    long singleSize() default 0l;

    /**
     * 文件类型
     *
     * @return
     */
    String[] contentTypes() default {};

    /**
     * 总大小是否允许为空，也就是允许不上传
     *
     * @return
     */
    boolean allowEmpty() default false;

    /**
     * 单个文件大小是否允许为空，因为多文件的时候，有时候上传3个文件，但其中一个空
     * 如果该值为 true，则不允许空文件上传
     *
     * @return
     */
    boolean allowSingleEmpty() default false;

    /**
     * 使用配置文件，如果该值不为空，则该值当作前缀去配置文件中查找对应的配置
     * 若配置文件不存在，或没有配置该值，则默认使用默认配置或输入的配置
     *
     * @return
     */
    String useConfig() default "";

    /**
     * 返回的消息
     *
     * @return
     */
    String message() default "no pictrue you say jb";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
