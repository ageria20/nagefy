package ageria.nagefy.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="cash")
public class Cash {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    private double total;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

    public Cash(Appointment appointment, String paymentMethod, LocalDateTime createdAt, double total ){
        this.appointment = appointment;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.total = total;
    }

}
