package by.mishastoma.companyservice.dto;

import by.mishastoma.companyservice.validation.SkillExistsConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequest {
    @SkillExistsConstraint
    private Long id;
}
