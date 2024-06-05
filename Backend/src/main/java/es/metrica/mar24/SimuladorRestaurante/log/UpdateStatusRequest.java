package es.metrica.mar24.SimuladorRestaurante.log;

import es.metrica.mar24.SimuladorRestaurante.entities.OrderStatus;

public class UpdateStatusRequest {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public static UpdateStatusRequestBuilder builder() {
        return new UpdateStatusRequestBuilder();
    }

    public static class UpdateStatusRequestBuilder {
        private OrderStatus status;

        public UpdateStatusRequestBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public UpdateStatusRequest build() {
            UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
            updateStatusRequest.setStatus(this.status);
            return updateStatusRequest;
        }
    }
}
