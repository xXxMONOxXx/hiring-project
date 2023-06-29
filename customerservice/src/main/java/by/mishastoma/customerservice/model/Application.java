package by.mishastoma.customerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "applications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private Long companyId;
    @Column(name = "order_position_id")
    private Long orderPositionId;
    @OneToMany
    private List<Message> messages;
    @ManyToOne
    private ApplicationStatus status;
}
