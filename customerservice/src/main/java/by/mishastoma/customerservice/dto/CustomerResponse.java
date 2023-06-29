package by.mishastoma.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Boolean isBlocked;
    private List<OrderResponse> orders;
}
