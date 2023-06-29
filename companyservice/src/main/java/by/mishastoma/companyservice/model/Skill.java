package by.mishastoma.companyservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "skills")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String skill;
}
