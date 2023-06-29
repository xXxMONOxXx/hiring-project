package by.mishastoma.customerservice.dto;

import by.mishastoma.customerservice.validation.OrderStatusExistsConstraint;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Integer price;
    @Valid
    private List<SkillRequest> skills;
    @OrderStatusExistsConstraint
    private Long statusId;
}
