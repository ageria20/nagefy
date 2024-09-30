package ageria.nagefy.repositories;


import ageria.nagefy.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountsRepoditory extends JpaRepository<Discount, UUID> {
}
