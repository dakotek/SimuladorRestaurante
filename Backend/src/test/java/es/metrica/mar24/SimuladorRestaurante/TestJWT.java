package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.jwt.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TestJWT {

	private User user;
	private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        user = new User();
        user.setEmail("user@email.com");
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.getToken(user);

        assertNotNull(token);
        String emailFromToken = jwtService.getEmailFromToken(token);
        assertEquals(user.getEmail(), emailFromToken);
    }

    @Test
    void testGetEmailFromToken() {
        String token = jwtService.getToken(user);

        String email = jwtService.getEmailFromToken(token);
        assertEquals(user.getEmail(), email);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.getToken(user);

        boolean isValid = jwtService.isTokenValid(token, user);
        assertTrue(isValid);
    }
    
    @Test
    void testIsTokenExpired() {
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // Expirado hace 1 hora
                .signWith(jwtService.getKey(), SignatureAlgorithm.HS256)
                .compact();

        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> jwtService.getAllClaims(token));       
    }
}
