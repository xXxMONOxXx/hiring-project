package by.mishastoma.companyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Long companyId;
    private Long orderPositionId;
    private ApplicationStatusResponse status;
}
