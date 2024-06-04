package es.metrica.mar24.SimuladorRestaurante;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.repositories.OrderRepository;
import es.metrica.mar24.SimuladorRestaurante.services.OrderService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testSaveOrder() {
        Order order = new Order();

        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.saveOrder(order);

        assertNotNull(savedOrder);
        assertEquals(order, savedOrder);
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Optional<Order> retrievedOrder = orderService.getOrderById(1L);

        assertTrue(retrievedOrder.isPresent());
        assertEquals(order, retrievedOrder.get());
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> retrievedOrders = orderService.getAllOrders();

        assertEquals(2, retrievedOrders.size());
        assertEquals(orders, retrievedOrders);
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }
}
