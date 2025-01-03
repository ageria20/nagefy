package ageria.nagefy.repositories;

import ageria.nagefy.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {


    boolean existsByEmail(String email);
    Staff findByEmail(String email);
}
