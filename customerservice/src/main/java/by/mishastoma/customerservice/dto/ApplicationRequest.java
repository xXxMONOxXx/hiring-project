package by.mishastoma.customerservice.dto;

import by.mishastoma.customerservice.validation.ApplicationStatusExistsConstraint;
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

    @ApplicationStatusExistsConstraint
    @NotNull(message = "Application must contain application status")
    private Long statusId;
}
