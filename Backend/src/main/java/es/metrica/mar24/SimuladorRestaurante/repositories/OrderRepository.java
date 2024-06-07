package es.metrica.mar24.SimuladorRestaurante.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByStatusNot(OrderStatus status);
    
    Order findByCookAndStatus(Long cookId, OrderStatus status);
}
