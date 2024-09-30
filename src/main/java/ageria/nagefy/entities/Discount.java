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
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String description;
    private double percentage;
    private LocalDateTime duration;


    public Discount(String description, LocalDateTime duration, double percentage) {
        this.description = description;
        this.percentage = percentage;
        this.duration = duration;
    }
}
