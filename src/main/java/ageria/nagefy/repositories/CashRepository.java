package ageria.nagefy.repositories;

import ageria.nagefy.entities.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface CashRepository extends JpaRepository<Cash, UUID> {

    List<Cash> findByPaymentMethod(String paymentMethod);

    @Query("SELECT c FROM Cash c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    List<Cash> findByDateRange(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    /*@Query("SELECT c FROM Cash c WHERE c.paymentMethod = :paymentMethod")*/

}
