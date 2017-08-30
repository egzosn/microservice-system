package com.moredo.common.validator.constraintvalidators;

import com.moredo.common.validator.constraints.StringEnumValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang.ArrayUtils.contains;

/**
 * Created by linjie on 15-1-26.
 */
public class StringEnumValidateImpl implements ConstraintValidator<StringEnumValidate, String> {
    private String[] type;

    private boolean allowNull = false;

    @Override
    public void initialize(StringEnumValidate stringEnumValidate) {
        this.type = stringEnumValidate.type();
        this.allowNull = stringEnumValidate.allowNull();
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (type == null || type.length == 0 || (string == null && allowNull)) {
            return true;
        }
        return contains(type, string);
    }
}
