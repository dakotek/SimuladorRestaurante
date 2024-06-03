package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.metrica.mar24.SimuladorRestaurante.controller.AuthControllers.AuthResponse;
import es.metrica.mar24.SimuladorRestaurante.controller.AuthControllers.AuthService;
import es.metrica.mar24.SimuladorRestaurante.entities.CustomUserDetails;
import es.metrica.mar24.SimuladorRestaurante.entities.Rol;
import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.jwt.JwtService;
import es.metrica.mar24.SimuladorRestaurante.log.LoginRequest;
import es.metrica.mar24.SimuladorRestaurante.log.RegisterRequest;
import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TestAuthController {

    @Mock
    private UserRepository userRepository; 

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .email("testuser@example.com")
                .role(Rol.CLIENT)
                .build();
    }

    @Test
    void testLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@example.com");
        loginRequest.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.getToken(any(Map.class), any(CustomUserDetails.class))).thenReturn("token");

        AuthResponse authResponse = authService.login(loginRequest);

        assertNotNull(authResponse);
        assertEquals("token", authResponse.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(2)).findByEmail(anyString());
        verify(jwtService).getToken(any(Map.class), any(CustomUserDetails.class));
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("newuser@example.com");
        registerRequest.setRole("CLIENT");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.getToken(any(User.class))).thenReturn("new-token");

        AuthResponse authResponse = authService.register(registerRequest);

        assertNotNull(authResponse);
        assertEquals("new-token", authResponse.getToken());

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(anyString());
        verify(jwtService).getToken(any(User.class));
    }
}
