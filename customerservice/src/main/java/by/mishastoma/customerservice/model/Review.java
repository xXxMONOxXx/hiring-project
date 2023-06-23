package by.mishastoma.customerservice.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "reviews")
@IdClass(ReviewId.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Id
    @Column(name = "company_id", nullable = false)
    private Long companyId;
    private String review;
    private Integer rating;
}
