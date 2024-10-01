package ageria.nagefy.repositories;

import ageria.nagefy.entities.TreatmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TreatmentScheduleRepository extends JpaRepository<TreatmentSchedule, UUID> {
}
