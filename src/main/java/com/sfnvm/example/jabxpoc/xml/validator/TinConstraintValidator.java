package com.sfnvm.example.jabxpoc.xml.validator;

import com.sfnvm.example.jabxpoc.util.RegexCommon;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TinConstraintValidator implements ConstraintValidator<TinConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) return true;
        return RegexCommon.validTin(value);
    }
}
