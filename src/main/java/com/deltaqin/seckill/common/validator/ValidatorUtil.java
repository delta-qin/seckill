package com.deltaqin.seckill.common.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
//import javax.xml.validation.Validator;

/**
 * @author deltaqin
 * @date 2021/6/10 下午5:08
 */

@Component
public class ValidatorUtil implements InitializingBean {

    private Validator validator;

    public ValidationResult validate(Object bean) {
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> validateSet = validator.validate(bean);
        if (validateSet.size() > 0) {
            validationResult.setHasError(true);
            validateSet.forEach(item -> {
                String message = item.getMessage();
                String s = item.getPropertyPath().toString();
                validationResult.getErrMsg().put(s, message);
            });
        }
        return validationResult;
    }

    // 将后面要使用的 validator 先在工厂启动的时候当前bean属性赋值之后init的时候就放在
    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
