package by.mishastoma.customerservice.handler;

import by.mishastoma.customerservice.dto.ExceptionResponse;
import by.mishastoma.customerservice.exception.ApplicationNotFoundException;
import by.mishastoma.customerservice.exception.AuthServiceNotAvailableException;
import by.mishastoma.customerservice.exception.ForbiddenException;
import by.mishastoma.customerservice.exception.InvalidResponseSignatureException;
import by.mishastoma.customerservice.exception.MessageNotFoundException;
import by.mishastoma.customerservice.exception.NotValidTokenException;
import by.mishastoma.customerservice.exception.OrderDoesNotContainSpecificFieldException;
import by.mishastoma.customerservice.exception.OrderNotFoundException;
import by.mishastoma.customerservice.exception.OrderPositionNotFoundException;
import by.mishastoma.customerservice.exception.ReviewNotFoundException;
import by.mishastoma.customerservice.exception.StatusNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(AuthServiceNotAvailableException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionResponse handleNoCustomerFound(AuthServiceNotAvailableException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(NotValidTokenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ExceptionResponse handleInvalidToken(NotValidTokenException ex) {
        log.info(ex.getMessage());
        return ExceptionResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse orderNotFound(OrderNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ExceptionResponse forbidden(ForbiddenException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse messageNotFound(MessageNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(OrderDoesNotContainSpecificFieldException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse orderIsInvalid(OrderDoesNotContainSpecificFieldException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse reviewNotFound(ReviewNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(StatusNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse statusNotFound(StatusNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(OrderPositionNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse orderPositionNotFound(OrderPositionNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidResponseSignatureException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidResponseFromService(InvalidResponseSignatureException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse applicationNotFound(ApplicationNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidRequest(MethodArgumentNotValidException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(ex.getBindingResult().getFieldErrors().stream().
                        map(DefaultMessageSourceResolvable::getDefaultMessage).toList())
                .build();
    }
}
