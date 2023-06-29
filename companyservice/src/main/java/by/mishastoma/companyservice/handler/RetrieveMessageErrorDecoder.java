package by.mishastoma.companyservice.handler;

import by.mishastoma.companyservice.dto.ExceptionResponse;
import by.mishastoma.companyservice.exception.AuthServiceNotAvailableException;
import by.mishastoma.companyservice.exception.NotValidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetrieveMessageErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionResponse message;
        try (InputStream bodyIs = response.body().asInputStream()) {
            message = objectMapper.readValue(bodyIs, ExceptionResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        return switch (response.status()) {
            case 400 -> new NotValidTokenException(String.join(", ", message.getErrors()));
            case 500, 503 -> new AuthServiceNotAvailableException();
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
