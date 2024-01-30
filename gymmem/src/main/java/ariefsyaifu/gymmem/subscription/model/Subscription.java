package ariefsyaifu.gymmem.subscription.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "subscription", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "product_id" })
})
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "user_id", nullable = false)
    public String userId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @Column(name = "amount", nullable = false)
    public BigDecimal amount;

    @Column(name = "remaining_times_of_meeting")
    public Integer remainingTimesOfMeeting;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status status;

    public enum Status {
        PENDING,
        ACTIVE,
        INACTIVE,
    }

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

}
