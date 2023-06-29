package by.mishastoma.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
    @NotBlank(message = "Company name is required")
    private String companyName;
    private String description;
    @NotBlank(message = "Username is required")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Invalid username, username must contain only letters and numbers")
    @Size(min = 3, message = "Username must contain at least 3 characters")
    @Size(max = 32, message = "Username must be less than 32 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password must contain at least 5 characters")
    @Size(max = 32, message = "Password must be less than 72 characters")
    private String password;
}
