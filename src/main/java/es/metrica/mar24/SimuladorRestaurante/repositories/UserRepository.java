package es.metrica.mar24.SimuladorRestaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.mar24.SimuladorRestaurante.entities.UserEntity;

import java.util.Optional;
 
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}