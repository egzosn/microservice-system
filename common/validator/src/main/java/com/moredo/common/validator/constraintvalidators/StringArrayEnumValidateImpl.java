package com.moredo.common.validator.constraintvalidators;

import com.moredo.common.validator.constraints.StringArrayEnumValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang.ArrayUtils.contains;
import static org.apache.commons.lang.ArrayUtils.nullToEmpty;

/**
 * Created by linjie on 15-1-26.
 */
public class StringArrayEnumValidateImpl implements ConstraintValidator<StringArrayEnumValidate, String[]> {
    private String[] type;

    private int arrLength = 0;

    private boolean allowEmpty = false;

    @Override
    public void initialize(StringArrayEnumValidate stringEnumValidate) {
        this.type = stringEnumValidate.type();
        arrLength = stringEnumValidate.arrLength();
        allowEmpty = stringEnumValidate.allowEmpty();
    }

    @Override
    public boolean isValid(String[] arrs, ConstraintValidatorContext constraintValidatorContext) {

        // 类型不限制，长度也不限制，则直接返回
        if ((type == null || type.length == 0) && arrLength == 0) {
            return true;
        }
        arrs = nullToEmpty(arrs);

        // 是否允许空
        if (!allowEmpty &&  arrs.length == 0) {
            return false;
        }
        if (arrLength > 0 && arrs.length > arrLength) {
            // 长度如果超出 false
            return false;
        }
        // 不在类别中的 false
        for (String arr : arrs) {
            if (allowEmpty)
            if (!contains(type, arr)) {
                return false;
            }
        }
        return true;
    }

}
