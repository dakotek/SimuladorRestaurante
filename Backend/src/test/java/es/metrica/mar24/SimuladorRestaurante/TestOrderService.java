package es.metrica.mar24.SimuladorRestaurante;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;
import es.metrica.mar24.SimuladorRestaurante.repositories.OrderRepository;
import es.metrica.mar24.SimuladorRestaurante.services.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestOrderService {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order1;
    private Order order2;
    private Order orderInPreparation;

    @BeforeEach
    public void setUp() {
        order1 = new Order(1L, 1L, OrderStatus.PENDING, "Recipe 1");
        order1.setId(1L);

        order2 = new Order(2L, 2L, OrderStatus.READY, "Recipe 2");
        order2.setId(2L);

        orderInPreparation = new Order(3L, 1L, OrderStatus.IN_PREPARATION, "Recipe 3");
        orderInPreparation.setId(3L);
    }

    @Test
    void testSaveOrder() {
        when(orderRepository.save(order1)).thenReturn(order1);
        Order savedOrder = orderService.saveOrder(order1);
        assertEquals(order1, savedOrder);
        verify(orderRepository, times(1)).save(order1);
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        Optional<Order> foundOrder = orderService.getOrderById(1L);
        assertTrue(foundOrder.isPresent());
        assertEquals(order1, foundOrder.get());
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        List<Order> allOrders = orderService.getAllOrders();
        assertEquals(2, allOrders.size());
        assertTrue(allOrders.contains(order1));
        assertTrue(allOrders.contains(order2));
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetPendingOrders() {
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(Arrays.asList(order1));
        List<Order> pendingOrders = orderService.getPendingOrders();
        assertEquals(1, pendingOrders.size());
        assertEquals(OrderStatus.PENDING, pendingOrders.get(0).getStatus());
    }

    @Test
    void testGetReadyOrders() {
        when(orderRepository.findByStatus(OrderStatus.READY)).thenReturn(Arrays.asList(order2));
        List<Order> readyOrders = orderService.getReadyOrders();
        assertEquals(1, readyOrders.size());
        assertEquals(OrderStatus.READY, readyOrders.get(0).getStatus());
    }

    @Test
    void testGetNonCancelledOrders() {
        when(orderRepository.findByStatusNot(OrderStatus.CANCELLED)).thenReturn(Arrays.asList(order1, order2));
        List<Order> nonCancelledOrders = orderService.getNonCancelledOrders();
        assertEquals(2, nonCancelledOrders.size());
        assertTrue(nonCancelledOrders.stream().noneMatch(order -> order.getStatus() == OrderStatus.CANCELLED));
    }

    @Test
    void testUpdateOrderStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        Order updatedOrder = orderService.updateOrderStatus(1L, OrderStatus.READY);
        assertEquals(OrderStatus.READY, updatedOrder.getStatus());
    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() {
        when(orderRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrderStatus(3L, OrderStatus.READY);
        });
        assertEquals("Order not found", thrown.getMessage());
    }

    @Test
    void testGetCollectedOrders() {
        when(orderRepository.findByStatusNot(OrderStatus.COLLECTED)).thenReturn(Arrays.asList(order1, order2));
        List<Order> collectedOrders = orderService.getCollectedOrders();
        assertEquals(2, collectedOrders.size());
        assertTrue(collectedOrders.stream().noneMatch(order -> order.getStatus() == OrderStatus.COLLECTED));
    }

    @Test
    void testAssignCookToRecipe() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        Order assignedOrder = orderService.assignCookToRecipe(1L, 3L);
        assertEquals(3L, assignedOrder.getCook());
    }

    @Test
    void testAssignCookToRecipe_OrderNotFound() {
        when(orderRepository.findById(3L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderService.assignCookToRecipe(3L, 3L);
        });
        assertEquals("Order not found", thrown.getMessage());
    }

    @Test
    void testGetOrderInPreparationByCook() {
        when(orderRepository.findByCookAndStatus(1L, OrderStatus.IN_PREPARATION)).thenReturn(Optional.of(orderInPreparation));

        Optional<Order> order = orderService.getOrderInPreparationByCook(1L);
        assertTrue(order.isPresent());
        assertEquals(OrderStatus.IN_PREPARATION, order.get().getStatus());
    }
}
