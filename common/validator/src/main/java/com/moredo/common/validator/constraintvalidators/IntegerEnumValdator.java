package com.moredo.common.validator.constraintvalidators;


import com.moredo.common.validator.constraints.IntegerEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.ArrayUtils.contains;

/**
 * Created by xs on 2016/11/16.
 */
public class IntegerEnumValdator implements ConstraintValidator<IntegerEnum, Integer> {
    private int[] type;
    @Override
    public void initialize(IntegerEnum constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null  || type == null || type.length == 0){
            return true;
        }
        return contains(type, value);
    }

}