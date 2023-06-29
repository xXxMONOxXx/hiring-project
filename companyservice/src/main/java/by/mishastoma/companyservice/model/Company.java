package by.mishastoma.companyservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "companies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    private String companyName;
    private String description;
    private Boolean isBlocked;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_employees",
            joinColumns = @JoinColumn(
                    name = "company_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "employee_id",
                    referencedColumnName = "id"
            )
    )
    private List<Employee> employees;
}
