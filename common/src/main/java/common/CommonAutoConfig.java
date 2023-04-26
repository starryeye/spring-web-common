package common;

import common.interceptor.CommonInterceptorConfig;
import common.response.CommonControllerAdvice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "common", havingValue = "on")
public class CommonAutoConfig {

    @Bean
    public CommonControllerAdvice commonControllerAdvice() {
        return new CommonControllerAdvice();
    }

    @Bean
    public CommonInterceptorConfig commonInterceptorConfig() {
        return new CommonInterceptorConfig();
    }
}
