package by.mishastoma.companyservice.dto;

import by.mishastoma.companyservice.validation.CompanyExistsConstraint;
import by.mishastoma.companyservice.validation.EmployeeExistsConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmployeeRequest {

    @NotNull(message = "Needs company id")
    @CompanyExistsConstraint
    private Long companyId;
    @EmployeeExistsConstraint
    private Long employeeId;
    private Boolean approved;
}
