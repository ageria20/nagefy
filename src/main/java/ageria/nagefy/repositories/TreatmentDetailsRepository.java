package ageria.nagefy.repositories;

import ageria.nagefy.entities.TreatmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TreatmentDetailsRepository extends JpaRepository<TreatmentDetail, UUID> {
}
