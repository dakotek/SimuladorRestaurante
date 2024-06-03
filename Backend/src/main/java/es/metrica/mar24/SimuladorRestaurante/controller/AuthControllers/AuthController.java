package es.metrica.mar24.SimuladorRestaurante.controller.AuthControllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.metrica.mar24.SimuladorRestaurante.log.LoginRequest;
import es.metrica.mar24.SimuladorRestaurante.log.RegisterRequest;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Maneja la solicitud de inicio de sesión.
     *
     * @param request los datos de inicio de sesión proporcionados por el cliente
     * @return una respuesta HTTP que contiene los detalles de autenticación
     */
    @PostMapping(value = "/inicio-sesion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Maneja la solicitud de registro de usuario.
     *
     * @param request los datos de registro proporcionados por el cliente
     * @return una respuesta HTTP que contiene los detalles de autenticación
     */
    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
