package es.metrica.mar24.SimuladorRestaurante;

import es.metrica.mar24.SimuladorRestaurante.controller.OrderController;
import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;
import es.metrica.mar24.SimuladorRestaurante.log.UpdateStatusRequest;
import es.metrica.mar24.SimuladorRestaurante.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TestOrderComtroller {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(orders);

        List<Order> result = orderController.getAllOrders();
        assertEquals(2, result.size());
    }

    @Test
    void getOrderById() {
        Order order = new Order();
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderController.getOrderById(1L);
        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void createOrder() {
        Order order = new Order();
        when(orderService.saveOrder(order)).thenReturn(order);

        Order result = orderController.createOrder(order);
        assertEquals(order, result);
    }

    @Test
    void deleteUser() {
        doNothing().when(orderService).deleteOrder(1L);

        orderController.deleteUser(1L);
        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void updateOrder() {
        Order order = new Order(1L, 2L, OrderStatus.PENDING, "12345");
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        Order updatedOrderDetails = new Order(3L, 4L, OrderStatus.READY, "newRecipe");
        ResponseEntity<Order> response = orderController.updateOrder(1L, updatedOrderDetails);

        assertEquals(ResponseEntity.ok(order), response);
        verify(orderService, times(1)).saveOrder(order);
    }

    @Test
    void updateOrder_NotFound() {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        Order updatedOrderDetails = new Order(3L, 4L, OrderStatus.READY, "newRecipe");
        ResponseEntity<Order> response = orderController.updateOrder(1L, updatedOrderDetails);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void updateOrderStatus() {
        Order order = new Order(1L, 2L, OrderStatus.PENDING, "12345");
        when(orderService.updateOrderStatus(1L, OrderStatus.READY)).thenReturn(order);

        UpdateStatusRequest request = UpdateStatusRequest.builder()
                                                      .status(OrderStatus.READY)
                                                      .build();
    
        ResponseEntity<Order> response = orderController.updateOrderStatus(1L, request);

        assertEquals(ResponseEntity.ok(order), response);
    }

    @Test
    void getPendingOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getPendingOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getPendingOrders();
        assertEquals(ResponseEntity.ok(orders), response);
    }

    @Test
    void getReadyOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getReadyOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getReadyOrders();
        assertEquals(ResponseEntity.ok(orders), response);
    }

    @Test
    void getCollectedOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getCollectedOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getCollecetedOrders();
        assertEquals(ResponseEntity.ok(orders), response);
    }

    @Test
    void getNonCancelledOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getNonCancelledOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getNonCancelledOrders();
        assertEquals(ResponseEntity.ok(orders), response);
    }

    @Test
    void assignCookToRecipe() {
        Order order = new Order(1L, 2L, OrderStatus.PENDING, "12345");
        when(orderService.assignCookToRecipe(1L, 2L)).thenReturn(order);

        String response = orderController.assignCookToRecipe(1L, 2L);
        assertEquals("Cook assigned: " + order.toString(), response);
    }

    @Test
    void assignCookToRecipe_IllegalArgumentException() {
        when(orderService.assignCookToRecipe(1L, 2L)).thenThrow(new IllegalArgumentException("Invalid order or cook ID"));

        String response = orderController.assignCookToRecipe(1L, 2L);
        assertEquals("Invalid order or cook ID", response);
    }

    @Test
    void getOrderInPreparationByCook() {
        Order order = new Order(1L, 2L, OrderStatus.IN_PREPARATION, "12345");
        when(orderService.getOrderInPreparationByCook(2L)).thenReturn(Optional.of(order));

        String response = orderController.getOrderInPreparationByCook(2L);
        assertEquals("Order found: " + order.toString(), response);
    }

    @Test
    void getOrderInPreparationByCook_NotFound() {
        when(orderService.getOrderInPreparationByCook(2L)).thenReturn(Optional.empty());

        String response = orderController.getOrderInPreparationByCook(2L);
        assertEquals("Order not found", response);
    }
}
