package ageria.nagefy.entities;


import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {


    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;
    private double price;
    @Column(name = "available_quantity")
    private long availableQuantity;
    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Catogory category;


    public Product(String name, double price, long availableQuantity, Catogory category, String imgUrl ) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.imgUrl = imgUrl;
        this.category = category;
    }
}
