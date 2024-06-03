package es.metrica.mar24.SimuladorRestaurante.controller.AuthControllers;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.metrica.mar24.SimuladorRestaurante.entities.Rol;
import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.jwt.JwtService;
import es.metrica.mar24.SimuladorRestaurante.log.LoginRequest;
import es.metrica.mar24.SimuladorRestaurante.log.RegisterRequest;
import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;


	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	/**
     * Maneja la solicitud de inicio de sesión.
     *
     * @param request los datos de inicio de sesión proporcionados por el cliente
     * @return una respuesta de autenticación que contiene el token JWT
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userRepository.findByEmail(request.getEmail()).orElseThrow();
        
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        User user = userOptional.orElseThrow();
        long userId = user.getId(); 
        String username = user.getUsername(); 
        String email = user.getEmail();
        Rol role = user.getRole();
        
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("email", email);
        claims.put("role", role);
        
        String token = jwtService.getToken(claims, user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Maneja la solicitud de registro de usuario.
     *
     * @param request los datos de registro proporcionados por el cliente
     * @return una respuesta de autenticación que contiene el token JWT
     */
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole().equals("COOK") ? Rol.COOK : Rol.CLIENT)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
