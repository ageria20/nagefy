package ageria.nagefy.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "appointments_treatments",
            joinColumns = @JoinColumn(name = "treatments_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    private List<Treatment> treatmentsList;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;


    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Cash cash;



    public Appointment(User userFromDB, List<Treatment> treatmentsFromDB, Staff staffFromDB, LocalDateTime startDate, LocalDateTime endDate) {
    this.user = userFromDB;
    this.treatmentsList = treatmentsFromDB;
    this.staff = staffFromDB;
    this.startTime = startDate;
    this.endTime = endDate;

    }
}
