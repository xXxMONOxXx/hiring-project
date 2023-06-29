package by.mishastoma.companyservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Invalid username")
    @Size(min = 3, message = "Username must contain at least 3 characters")
    @Size(max = 32, message = "Username must be less than 32 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password must contain at least 5 characters")
    @Size(max = 32, message = "Password must be less than 72 characters")
    private String password;
    @NotBlank(message = "Firstname is required")
    @Size(min = 3, message = "Firstname must contain at least 2 characters")
    @Size(max = 32, message = "Firstname must be less than 128 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Firstname must start with big letter and" +
            " shouldn't contain anything except letters")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    @Size(min = 3, message = "Lastname must contain at least 2 characters")
    @Size(max = 32, message = "Lastname must be less than 128 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Lastname must start with big letter and" +
            " shouldn't contain anything except letters")
    private String lastname;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d", message = "Phone number must contain only numbers")
    @Size(min = 4, message = "Phone number must be at least 4 characters long")
    @Size(max = 15, message = "Phone number must be less than 15 characters")
    private String phone;
    @NotBlank(message = "Email is required")
    @Size(max = 256, message = "Email maximum size is 256")
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Invalid email")
    private String email;
    @Past(message = "User must have been born")
    @NotBlank(message = "Birthdate is required")
    private Date birthdate;
    private List<SkillRequest> skills;
}
