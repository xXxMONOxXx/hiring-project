package by.mishastoma.companyservice.exception;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException(String message){
        super(message);
    }
}
