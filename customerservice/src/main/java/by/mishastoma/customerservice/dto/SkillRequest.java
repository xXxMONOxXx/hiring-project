package by.mishastoma.customerservice.dto;

import by.mishastoma.customerservice.validation.SkillExistsConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequest {
    @NotNull(message = "Skill must have id")
    @SkillExistsConstraint
    private Long id;
}
