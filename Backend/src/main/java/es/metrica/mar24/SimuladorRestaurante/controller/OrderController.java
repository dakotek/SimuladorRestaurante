package es.metrica.mar24.SimuladorRestaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.log.UpdateStatusRequest;
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
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        Order updatedOrder = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getPendingOrders() {
        List<Order> pendingOrders = orderService.getPendingOrders();
        return ResponseEntity.ok(pendingOrders);
    }
    
    @GetMapping("/ready")
    public ResponseEntity<List<Order>> getReadyOrders() {
        List<Order> readyOrders = orderService.getReadyOrders();
        return ResponseEntity.ok(readyOrders);
    }
    
    @GetMapping("/collected")
    public ResponseEntity<List<Order>> getCollecetedOrders() {
        List<Order> collectedOrders = orderService.getCollectedOrders();
        return ResponseEntity.ok(collectedOrders);
    }

    @GetMapping("/non-cancelled")
    public ResponseEntity<List<Order>> getNonCancelledOrders() {
        List<Order> nonCancelledOrders = orderService.getNonCancelledOrders();
        return ResponseEntity.ok(nonCancelledOrders);
    }
    
    @PostMapping("/assign-cook")
    public String assignCookToRecipe(@RequestParam Long orderId, @RequestParam Long cookId) {
        try {
            Order order = orderService.assignCookToRecipe(orderId, cookId);
            return "Cook assigned: " + order.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    
    @GetMapping("/in-preparation/cook/{cookId}")
    public String getOrderInPreparationByCook(@PathVariable Long cookId) {
        Optional<Order> order = orderService.getOrderInPreparationByCook(cookId);
        
        if (order.isPresent()) {
            return "Order found: " + order.get().toString();
        } else {
            return "Order not found";
        }
    }
}