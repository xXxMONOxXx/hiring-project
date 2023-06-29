package by.mishastoma.companyservice.dto;

import by.mishastoma.companyservice.validation.ApplicationExistsConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @NotNull(message = "Order position id must be presnted")
    @ApplicationExistsConstraint
    private Long orderPositionId;
}
