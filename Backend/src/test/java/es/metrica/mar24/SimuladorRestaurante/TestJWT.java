package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import org.springframework.security.core.userdetails.UserDetailsService;

import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.jwt.JwtAuthenticationFilter;
import es.metrica.mar24.SimuladorRestaurante.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TestJWT {

	private User user;
	private JwtService jwtService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
    	jwtService = new JwtService();
        user = new User();
        user.setEmail("user@email.com");
        
        userDetailsService = mock(UserDetailsService.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
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

    @Test
    void testGetTokenFromRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token");

        String token = jwtAuthenticationFilter.getTokenFromRequest(request);

        assertEquals("token", token);
    }
}
