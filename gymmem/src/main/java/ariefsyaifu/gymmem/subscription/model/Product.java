package ariefsyaifu.gymmem.subscription.model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "price", nullable = false)
    public BigDecimal price;

    /**
     * days of week using number
     * start from 1 and end at 7
     * example=1,2,3,4,5,6,7
     */
    @Column(name = "schedule_days_of_week", length = 14)
    public String scheduleDaysOfWeek;

    @Column(name = "times_of_meeting")
    public Integer timesOfMeeting;

    @Column(name = "minutes_of_duration")
    public Short minutesOfDuration;

    @Column(name = "description")
    public String description;

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

}
