package by.mishastoma.companyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmployeeResponse {
    private Long companyId;
    private Long employeeId;
    private Boolean approved;
}
