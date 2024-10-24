package ageria.nagefy.repositories;

import ageria.nagefy.entities.Appointment;
import ageria.nagefy.entities.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, UUID> {

    boolean existsByStaffAndStartTime(Staff staff, LocalDateTime startTime);
    List<Appointment> findByUserId(UUID id);

    Page<Appointment> findByStaffId(Pageable pageable, UUID id);
    Page<Appointment> findByUserId(Pageable pageable, UUID id);
    @Query("SELECT a FROM Appointment a WHERE a.staff = :staff AND a.startTime >= :startOfDay AND a.endTime <= :endOfDay")
    List<Appointment> findByStaffAndDate(@Param("staff") Staff staff, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay
    );


}
