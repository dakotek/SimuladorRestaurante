package es.metrica.mar24.SimuladorRestaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.services.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/orders")
public class OrderController {
 
    @Autowired
    public OrderService orderService;
 
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
 
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
 
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }
 
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
 
    
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setClient(orderDetails.getClient());
            order.setCook(orderDetails.getCook());
            order.setStatus(orderDetails.getStatus());
            order.setRecipe(orderDetails.getRecipe());
            Order updatedOrder = orderService.saveOrder(order);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}