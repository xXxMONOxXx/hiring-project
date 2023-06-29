package by.mishastoma.companyservice.exception;

public class ApplicationAlreadyExists extends RuntimeException {
    public ApplicationAlreadyExists(){
        super("Application already exists");
    }
}
