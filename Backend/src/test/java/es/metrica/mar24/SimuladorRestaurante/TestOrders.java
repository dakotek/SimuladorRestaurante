package es.metrica.mar24.SimuladorRestaurante;


import org.junit.jupiter.api.Test;

import es.metrica.mar24.SimuladorRestaurante.entities.Order;
import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;
import es.metrica.mar24.SimuladorRestaurante.entities.Rol;
import es.metrica.mar24.SimuladorRestaurante.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrders {

    @Test
    void testGettersAndSetters() {
        
        User client = User.builder()
                .id(1L)
                .username("client")
                .password("password")
                .email("client@example.com")
                .role(Rol.CLIENT)
                .build();

        User cook = User.builder()
                .id(2L)
                .username("cook")
                .password("password")
                .email("cook@example.com")
                .role(Rol.COOK)
                .build();

        Order order = Order.builder()
                .client(client)
                .cook(cook)
                .status(OrderStatus.PENDING)
                .recipe("https://www.themealdb.com/api/json/v1/1/lookup.php?i={meal_id}")
                .build();

        assertEquals(client, order.getClient());
        assertEquals(cook, order.getCook());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("https://www.themealdb.com/api/json/v1/1/lookup.php?i={meal_id}", order.getRecipe());

        User newClient = User.builder()
                .id(3L)
                .username("newClient")
                .password("newPassword")
                .email("newclient@example.com")
                .role(Rol.CLIENT)
                .build();
        order.setClient(newClient);

        User newCook = User.builder()
                .id(4L)
                .username("newCook")
                .password("newPassword")
                .email("newcook@example.com")
                .role(Rol.COOK)
                .build();
        order.setCook(newCook);

        order.setStatus(OrderStatus.READY);
        order.setRecipe("newRecipe");

        assertEquals(newClient, order.getClient());
        assertEquals(newCook, order.getCook());
        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals("newRecipe", order.getRecipe());
    }

    @Test
    void testConstructor() {
      
        User client = User.builder()
                .id(1L)
                .username("client")
                .password("password")
                .email("client@example.com")
                .role(Rol.CLIENT)
                .build();

        User cook = User.builder()
                .id(2L)
                .username("cook")
                .password("password")
                .email("cook@example.com")
                .role(Rol.COOK)
                .build();

        Order order = new Order(client, cook, OrderStatus.PENDING, "https://www.themealdb.com/api/json/v1/1/lookup.php?i={meal_id}");

        assertEquals(client, order.getClient());
        assertEquals(cook, order.getCook());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("https://www.themealdb.com/api/json/v1/1/lookup.php?i={meal_id}", order.getRecipe());
    }
}
