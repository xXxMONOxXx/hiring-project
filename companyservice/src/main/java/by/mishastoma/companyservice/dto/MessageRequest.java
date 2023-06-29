package by.mishastoma.companyservice.dto;

import by.mishastoma.companyservice.validation.ApplicationExistsConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    @NotBlank(message = "Message insides are required")
    @Size(max = 512, message = "Message must be less than 512 characters")
    private String message;
    @NotNull(message = "Message needs application id")
    @ApplicationExistsConstraint
    private Long applicationId;
    @NotNull(message = "Message requires token for validation")
    private String token;
}
