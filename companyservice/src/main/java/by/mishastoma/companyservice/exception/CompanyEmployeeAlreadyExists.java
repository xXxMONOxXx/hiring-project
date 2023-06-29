package by.mishastoma.companyservice.exception;

public class CompanyEmployeeAlreadyExists extends RuntimeException {
    public CompanyEmployeeAlreadyExists(String message) {
        super(message);
    }
}
