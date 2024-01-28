package ariefsyaifu.gymmem.user.model;

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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "name")
    public String name;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public Status status;

    public enum Status {
        VERIFIED,
        UNVERIFIED
    }

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

}
