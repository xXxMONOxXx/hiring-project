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
public class OrderPositionResponse {
    private Long id;
    private String requirements;
    private List<ApplicationResponse> applications;
}
