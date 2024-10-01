package ageria.nagefy.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "payment_method")
    private String paymentMethod;
    private LocalDate date;

    private double quantity;
    private double total;

    public OrderDetail( User user, Product product, String paymentMethod, LocalDate date, double quantity, double total) {
        this.product = product;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.quantity = quantity;
        this.total = total;
    }

}
