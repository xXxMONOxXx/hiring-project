package by.mishastoma.companyservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "reviews")
@IdClass(CompanyEmployeeId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmployee {

    @Id
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Id
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    private Boolean approved;
}
