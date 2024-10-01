package ageria.nagefy.repositories;

import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, UUID> {

    boolean existsByStaffAndStartTime(Staff staff, LocalDateTime startTime);
    List<Appointment> findByUserId(UUID id);
}
