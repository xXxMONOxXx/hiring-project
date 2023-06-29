package by.mishastoma.companyservice.handler;

import by.mishastoma.companyservice.config.FeignConfig;
import by.mishastoma.companyservice.exception.AuthServiceNotAvailableException;
import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthRetryer implements Retryer {
    private Integer attempt = 1;

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (FeignConfig.getRETRY_MAX_ATTEMPTS().equals(attempt)) {
            throw new AuthServiceNotAvailableException();
        }
        try {
            attempt++;
            Thread.sleep(FeignConfig.getRETRY_INTERVAL());
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new AuthRetryer();
    }
}
