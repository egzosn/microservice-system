package com.moredo.common.validator.constraintvalidators;

import com.moredo.common.validator.constraints.QQ;
import com.moredo.common.validator.plug.Regx;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QQValidator implements ConstraintValidator<QQ, String> {
    private String message;
    private int errorCode;

    public void initialize(QQ annotation) {
        this.message = annotation.message();
        this.errorCode = annotation.erroCode();

    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        if (value.matches(Regx.QQ) || value.matches(Regx.PHONE)) {
            return true;
        }
        return false;


    }

}
