package by.mishastoma.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private Date birthdate;
}
