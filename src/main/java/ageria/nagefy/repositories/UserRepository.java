package ageria.nagefy.repositories;

import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    User findByEmail(String email);

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT(:name, '%'))")
    List<Client> findClientsByName(@Param("name") String name);

}
