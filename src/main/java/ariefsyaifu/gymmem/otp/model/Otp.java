package ariefsyaifu.gymmem.otp.model;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "otp")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "key", unique = true)
    public String key;

    @Column(name = "value")
    public String value;

    @Column(name = "expired_at", nullable = false)
    public Instant expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public Type type;

    public enum Type {
        VERIFY_EMAIL,
        PAYMENT,
        RESET_PASSWORD,
    }

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

}
