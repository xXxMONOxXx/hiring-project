package by.mishastoma.companyservice.exception;

public class AuthServiceNotAvailableException extends RuntimeException{

    public AuthServiceNotAvailableException(){
        super("Auth service not available");
    }
}
