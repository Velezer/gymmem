package ariefsyaifu.gymmem.payment.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "credit_cart_id", nullable = false)
    public String creditCardId;

    @Column(name = "user_id", nullable = false)
    public String userId;

    @Column(name = "subscription_id", nullable = false)
    public String subscriptionId;

    @Column(name = "amount", nullable = false)
    public BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status status;

    public enum Status {
        PROCESSING,
        PAID,
        FAILED
    }

}
