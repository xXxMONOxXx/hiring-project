package by.mishastoma.authservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Getter
    private static String CUSTOMER_ROLE;
    @Getter
    private static String COMPANY_ROLE;
    @Getter
    private static String EMPLOYEE_ROLE;

    @Value("${customer.role}")
    public void setCustomerRole(final String customerRole) {
        CUSTOMER_ROLE = customerRole;
    }

    @Value("${company.role}")
    public void setCompanyRole(final String companyRole) {
        COMPANY_ROLE = companyRole;
    }

    @Value("${employee.role}")
    public void setEmployeeRole(final String employeeRole) {
        EMPLOYEE_ROLE = employeeRole;
    }
}
