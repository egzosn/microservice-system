package com.moredo.common.validator.constraints;

import com.moredo.common.validator.constraintvalidators.IntegerEnumValdator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xs on 2016/11/16.
 * 使用这个注解约束表单的字段值，如 type = {1,2} ，那么值就只能是1 或2
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = IntegerEnumValdator.class)
public @interface IntegerEnum {
    int[] type() ;
    String message() default "validate failure";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
