package ch.etml.pl.dao;

import ch.etml.pl.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {
    Optional<ClientEntity> findClientEntitiesByPrenomEquals(String prenom);
}
