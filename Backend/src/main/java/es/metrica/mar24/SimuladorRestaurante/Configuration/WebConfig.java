package es.metrica.mar24.SimuladorRestaurante.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig {

    /**
     * Define un bean que configura CORS para la aplicación.
     * 
     * @return una instancia de WebMvcConfigurer con la configuración CORS
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configura las reglas de CORS para la aplicación.
             *
             * @param registry el registro de CORS donde se añaden las configuraciones
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todas las rutas
                        .allowedOrigins("http://localhost:4200") // Permite solicitudes desde este origen
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Permite estos métodos HTTP
            }
        };
    }
}
