package es.metrica.mar24.SimuladorRestaurante.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;


@Configuration
public class AplicationConfig {

    private final UserRepository userRepository;

    /**
     * Constructor que inyecta el repositorio de usuarios.
     *
     * @param userRepository el repositorio de usuarios
     */
    public AplicationConfig(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    /**
     * Define el bean AuthenticationManager que se encarga de gestionar el proceso de autenticación.
     *
     * @param config la configuración de autenticación
     * @return el AuthenticationManager
     * @throws Exception si ocurre un error al obtener el AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define el bean AuthenticationProvider que utiliza DaoAuthenticationProvider
     * para la autenticación basada en los datos del usuario en la base de datos.
     *
     * @return el AuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Define el bean PasswordEncoder que utiliza BCryptPasswordEncoder para la codificación de contraseñas.
     *
     * @return el PasswordEncoder configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define el bean UserDetailsService que busca un usuario por nombre de usuario
     * en el repositorio y lanza una excepción si el usuario no es encontrado.
     *
     * @return el UserDetailsService configurado
     */
    @Bean
    public UserDetailsService userDetailService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
