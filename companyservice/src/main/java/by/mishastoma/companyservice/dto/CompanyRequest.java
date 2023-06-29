package by.mishastoma.companyservice.dto;

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
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Invalid username")
    @Size(min = 3, message = "Username must contain at least 3 characters")
    @Size(max = 32, message = "Username must be less than 32 characters")
    private String username;

    @Size(min = 3, message = "Password must contain at least 5 characters")
    @Size(max = 32, message = "Password must be less than 72 characters")
    private String password;

    @Size(min = 3, message = "Company name must contain at least 3 characters")
    @Size(max = 32, message = "Company name must be less than 32 characters")
    private String companyName;

    @Size(min = 3, message = "Company description must contain at least 3 characters")
    @Size(max = 32, message = "Company description must be less than 32 characters")
    private String description;
}
