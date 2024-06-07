package es.metrica.mar24.SimuladorRestaurante;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrders {

    @Mock
    private Order mockOrder;

    @InjectMocks
    private Order order;

    public TestOrders() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGettersAndSetters() {
        Long clientId = 1L;
        Long cookId = 2L;

        Order order = Order.builder()
                .client(clientId)
                .cook(cookId)
                .status(OrderStatus.PENDING)
                .recipe("12345")
                .build();

        assertEquals(clientId, order.getClient());
        assertEquals(cookId, order.getCook());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("12345", order.getRecipe());

        Long newClientId = 3L;
        Long newCookId = 4L;
        order.setClient(newClientId);
        order.setCook(newCookId);
        order.setStatus(OrderStatus.READY);
        order.setRecipe("newRecipe");

        assertEquals(newClientId, order.getClient());
        assertEquals(newCookId, order.getCook());
        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals("newRecipe", order.getRecipe());
    }

    @Test
    void testConstructor() {
        Long clientId = 1L;
        Long cookId = 2L;

        Order order = new Order(clientId, cookId, OrderStatus.PENDING, "12345");

        assertEquals(clientId, order.getClient());
        assertEquals(cookId, order.getCook());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("12345", order.getRecipe());
    }

    @Test
    void testBBDD_Mockito() {
        Long clientId = 1L;
        Long cookId = 2L;
        String recipe = "12345";
        OrderStatus status = OrderStatus.PENDING;

        when(mockOrder.getClient()).thenReturn(clientId);
        when(mockOrder.getCook()).thenReturn(cookId);
        when(mockOrder.getRecipe()).thenReturn(recipe);
        when(mockOrder.getStatus()).thenReturn(status);

        assertEquals(clientId, mockOrder.getClient());
        assertEquals(cookId, mockOrder.getCook());
        assertEquals(recipe, mockOrder.getRecipe());
        assertEquals(status, mockOrder.getStatus());

        Long newClientId = 3L;
        Long newCookId = 4L;
        String newRecipe = "newRecipe";
        OrderStatus newStatus = OrderStatus.READY;

        mockOrder.setClient(newClientId);
        mockOrder.setCook(newCookId);
        mockOrder.setRecipe(newRecipe);
        mockOrder.setStatus(newStatus);

        verify(mockOrder).setClient(newClientId);
        verify(mockOrder).setCook(newCookId);
        verify(mockOrder).setRecipe(newRecipe);
        verify(mockOrder).setStatus(newStatus);
    }
}
