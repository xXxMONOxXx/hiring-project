package by.mishastoma.customerservice.dto;

import by.mishastoma.customerservice.validation.CompanyExistsConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class ReviewRequest {

    @NotNull(message = "Review must contain company id")
    @CompanyExistsConstraint
    private Long companyId;
    @Size(max = 512, message = "Review must be less than 512 characters")
    private String review;
    @Min(value = 0, message = "Minimum for ration is 0")
    @Max(value = 10, message = "Maximum for ration is 10")
    @NotNull(message = "Review must contain ration")
    private Integer rating;
}
