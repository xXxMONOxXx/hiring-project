package by.mishastoma.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean isBlocked;
}
