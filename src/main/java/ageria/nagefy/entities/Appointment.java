package ageria.nagefy.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
        @JoinTable(name = "appointments_treatments",
            joinColumns = @JoinColumn(name = "treatments_id"),
            inverseJoinColumns = @JoinColumn(name = "treatments_id"))
    private List<Treatment> treatmentsList;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

   @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private boolean cancelled;
    @Column(name = "cancelled_reason")
    private String cancelledReason;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private double total;

    public Appointment(User userFromDB, List<Treatment> treatmentsFromDB, Staff staffFromDB, String paymentMethod, LocalDateTime startDate, LocalDateTime endDate, boolean cancelled, double prices) {
    this.user = userFromDB;
    this.treatmentsList = treatmentsFromDB;
    this.staff = staffFromDB;
    this.paymentMethod = paymentMethod;
    this.startTime = startDate;
    this.endTime = endDate;
    this.cancelled = cancelled;
    this.total = prices;

    }
}
