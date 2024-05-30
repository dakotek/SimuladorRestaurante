package es.metrica.mar24.SimuladorRestaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.mar24.SimuladorRestaurante.entities.User;

import java.util.Optional;
 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
} 