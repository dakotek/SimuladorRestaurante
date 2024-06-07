package es.metrica.mar24.SimuladorRestaurante.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long client;

    @Column(name = "cook_id")
    private Long cook;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String recipe;

    public Order() {
    }

    public Order(Long client, Long cook, OrderStatus status, String recipe) {
        this.client = client;
        this.cook = cook;
        this.status = status;
        this.recipe = recipe;
    }

    public void setId(long id) {
		this.id = id;
		
	}
	
	public Long getId() {
		return id;
		
	}

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Long getCook() {
        return cook;
    }

    public void setCook(Long cook) {
        this.cook = cook;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public static class OrderBuilder {
        private long client;
        private long cook;
        private OrderStatus status;
        private String recipe;

        public OrderBuilder client(long client) {
            this.client = client;
            return this;
        }

        public OrderBuilder cook(long cook) {
            this.cook = cook;
            return this;
        }

        public OrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder recipe(String recipe) {
            this.recipe = recipe;
            return this;
        }

        public Order build() {
            return new Order(client, cook, status, recipe);
        }
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

}
