package es.metrica.mar24.SimuladorRestaurante;

import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;
import es.metrica.mar24.SimuladorRestaurante.log.UpdateStatusRequest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestUpdateStatusReques {

    @Test
    void testGetStatus() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus(OrderStatus.IN_PREPARATION);
        assertEquals(OrderStatus.IN_PREPARATION, request.getStatus());
    }

    @Test
    void testSetStatus() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus(OrderStatus.COLLECTED);
        assertEquals(OrderStatus.COLLECTED, request.getStatus());
    }

    @Test
    void testBuilder() {
        UpdateStatusRequest request = UpdateStatusRequest.builder()
                .status(OrderStatus.IN_PREPARATION)
                .build();
        assertEquals(OrderStatus.IN_PREPARATION, request.getStatus());
    }

    @Test
    void testBuilderSetStatus() {
        UpdateStatusRequest.UpdateStatusRequestBuilder builder = new UpdateStatusRequest.UpdateStatusRequestBuilder();
        builder.status(OrderStatus.IN_PREPARATION);
        UpdateStatusRequest request = builder.build();
        assertEquals(OrderStatus.IN_PREPARATION, request.getStatus());
    }
}
