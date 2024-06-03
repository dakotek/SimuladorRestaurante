package es.metrica.mar24.SimuladorRestaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
