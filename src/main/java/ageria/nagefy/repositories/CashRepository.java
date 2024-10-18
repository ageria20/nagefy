package ageria.nagefy.repositories;

import ageria.nagefy.entities.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CashRepository extends JpaRepository<Cash, UUID> {

    List<Cash> findByPaymentMethod(String paymentMethod);


}
