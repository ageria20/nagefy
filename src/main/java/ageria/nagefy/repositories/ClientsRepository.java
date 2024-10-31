package ageria.nagefy.repositories;

import ageria.nagefy.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientsRepository extends JpaRepository<Client, UUID> {

    boolean existsByEmail(String email);
    Client findByEmail(String email);
}