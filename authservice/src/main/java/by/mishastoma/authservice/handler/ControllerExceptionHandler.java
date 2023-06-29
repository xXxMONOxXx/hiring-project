package by.mishastoma.authservice.handler;

import by.mishastoma.authservice.dto.ExceptionResponse;
import by.mishastoma.authservice.exception.CompanyNotFoundException;
import by.mishastoma.authservice.exception.CustomerNotFoundException;
import by.mishastoma.authservice.exception.EmployeeNotFoundException;
import by.mishastoma.authservice.exception.InvalidTokenException;
import by.mishastoma.authservice.exception.InvalidUserException;
import by.mishastoma.authservice.exception.LoginFailedException;
import by.mishastoma.authservice.exception.UsernameIsOccupiedException;
import by.mishastoma.authservice.exception.WrongRoleException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoCustomerFound(CustomerNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoCompanyFound(CompanyNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoEmployeeFound(EmployeeNotFoundException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameIsOccupiedException.class)
    public ExceptionResponse handleOccupiedUsername(UsernameIsOccupiedException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailedException.class)
    public ExceptionResponse loginFailed(LoginFailedException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public ExceptionResponse invalidToken(InvalidTokenException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(WrongRoleException.class)
    public ExceptionResponse wrongRole(WrongRoleException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserException.class)
    public ExceptionResponse wrongRole(InvalidUserException ex) {
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
