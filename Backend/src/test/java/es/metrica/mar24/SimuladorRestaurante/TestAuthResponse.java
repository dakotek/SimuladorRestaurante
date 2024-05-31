package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import es.metrica.mar24.SimuladorRestaurante.controller.AuthControllers.AuthResponse;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TestAuthResponse{
	
	 	@Test
	    void testDefaultConstructor() {
	        AuthResponse authResponse = new AuthResponse();
	        assertEquals(null, authResponse.getToken());
	    }

	    @Test
	    void testParameterizedConstructor() {
	        AuthResponse authResponse = new AuthResponse("testToken");
	        assertEquals("testToken", authResponse.getToken());
	    }

	    @Test
	    void testSetToken() {
	        String token = "testToken";
	        AuthResponse authResponse = new AuthResponse();
	        authResponse.setToken(token);
	        assertEquals("testToken", authResponse.getToken());
	    }

	    @Test
	    void testBuilder() {
	        String token = "testToken";
	        AuthResponse authResponse = AuthResponse.builder()
	                                                 .token(token)
	                                                 .build();
	        assertEquals("testToken", authResponse.getToken());
	    }
	
}