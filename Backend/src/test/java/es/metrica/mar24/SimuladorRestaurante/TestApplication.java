package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TestApplication {

	@Autowired
    private ApplicationContext context;
	
	@Test
    void executeMainTest() {
        String[] args = {};
        SimuladorRestauranteApplication.main(args);
        assertNotNull(context);
    }
}
