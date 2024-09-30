package ageria.nagefy.repositories;

import ageria.nagefy.entities.Catogory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CategoriesRepository extends JpaRepository<Catogory, UUID> {
}
