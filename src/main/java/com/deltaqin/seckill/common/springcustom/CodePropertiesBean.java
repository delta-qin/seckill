package com.deltaqin.seckill.common.springcustom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author deltaqin
 * @date 2021/6/13 上午10:02
 */
@Configuration
@Slf4j
public class CodePropertiesBean implements EnvironmentAware, BeanPostProcessor {
    private Environment environment;

    private static CodePropertiesBean propertiesBean = new CodePropertiesBean();

    public CodePropertiesBean() {
        log.info("Construct EnvironmentProperties");
        //propertiesBean = this;
    }

    public static CodePropertiesBean getInstance() {
        return propertiesBean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public String[] getActiveProfiles() {
        return this.environment.getActiveProfiles();
    }

    public String[] getDefaultProfiles() {
        return this.environment.getDefaultProfiles();
    }

    public boolean acceptsProfiles(String... profiles) {
        return this.environment.acceptsProfiles(profiles);
    }

    public boolean containsProperty(String key) {
        return this.environment.containsProperty(key);
    }

    public String getProperty(String key) {
        //System.out.println(this.environment);
        return this.environment.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return this.environment.getProperty(key, defaultValue);
    }

    public <T> T getProperty(String key, Class<T> targetType) {
        return this.environment.getProperty(key, targetType);
    }

    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return this.environment.getProperty(key, targetType, defaultValue);
    }

    public String getRequiredProperty(String key) {
        return this.environment.getRequiredProperty(key);
    }

    public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return this.environment.getRequiredProperty(key, targetType);
    }

    public String resolvePlaceholders(String text) {
        return this.environment.resolvePlaceholders(text);
    }

    public String resolveRequiredPlaceholders(String text) {
        return this.environment.resolveRequiredPlaceholders(text);
    }
}
