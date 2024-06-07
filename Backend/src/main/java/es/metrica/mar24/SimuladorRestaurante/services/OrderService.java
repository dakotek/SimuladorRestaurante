package es.metrica.mar24.SimuladorRestaurante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;
import es.metrica.mar24.SimuladorRestaurante.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(OrderStatus.PENDING);
    }
    
    public List<Order> getReadyOrders() {
        return orderRepository.findByStatus(OrderStatus.READY);
    }

    public List<Order> getNonCancelledOrders() {
        return orderRepository.findByStatusNot(OrderStatus.CANCELLED);
    }
    
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

	public List<Order> getCollectedOrders() {
		return orderRepository.findByStatusNot(OrderStatus.COLLECTED);
	}
	
	public Order assignCookToRecipe(Long orderId, Long cookId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setCook(cookId);
            orderRepository.save(order); 
            return order;
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }
	
	public Optional<Order> getOrderInPreparationByCook(Long cookId) {
        return orderRepository.findByCookAndStatus(cookId, OrderStatus.IN_PREPARATION);
    }
}
