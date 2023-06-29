package by.mishastoma.customerservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Getter
    private static Integer RETRY_MAX_ATTEMPTS;
    @Getter
    private static Integer RETRY_INTERVAL;

    @Value("${feign.retry.max.attempt}")
    public void setRetryMaxAttempts(final Integer retryMaxAttempts) {
        RETRY_MAX_ATTEMPTS = retryMaxAttempts;
    }

    @Value("${feign.retry.interval}")
    public void setRetryInterval(final Integer retryInterval) {
        RETRY_INTERVAL = retryInterval;
    }
}
