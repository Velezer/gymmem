package ariefsyaifu.gymmem.user.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    public String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    public User user;

    /**
     * for comparison
     */
    @Column(name = "encrypted_access_token", columnDefinition = "text")
    public String encryptedAccessToken;

    /**
     * for finding
     */
    @Column(name = "refresh_token", unique = true, columnDefinition = "text")
    public String refreshToken;

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

}
