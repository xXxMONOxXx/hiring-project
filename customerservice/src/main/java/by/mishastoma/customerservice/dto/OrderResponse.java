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
public class OrderResponse {
    private Long id;
    private Integer price;
    private Long statusId;
    private List<SkillResponse> skills;
    private List<OrderPositionResponse> positions;
}
