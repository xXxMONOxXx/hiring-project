package by.mishastoma.customerservice.dto;

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
public class OrderPositionRequest {
    @NotBlank(message = "Requirements are required")
    @Size(max = 32, message = "Requirements must be less than 512 characters")
    private String requirements;

    @NotNull(message = "Order id must be presented")
    private Long orderId;
}
