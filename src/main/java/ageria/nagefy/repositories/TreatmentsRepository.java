package ageria.nagefy.repositories;

import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TreatmentsRepository extends JpaRepository<Treatment, UUID> {

    @Query("SELECT t FROM Treatment t WHERE LOWER(t.name) LIKE LOWER(CONCAT(:name, '%'))")
    List<Treatment> findTreatmentsByName(@Param("name") String name);

}
