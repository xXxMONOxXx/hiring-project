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

@Entity(name = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_skills",
            joinColumns = @JoinColumn(
                    name = "employee_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "skill_id",
                    referencedColumnName = "id"
            )
    )
    private List<Skill> skills;
}
