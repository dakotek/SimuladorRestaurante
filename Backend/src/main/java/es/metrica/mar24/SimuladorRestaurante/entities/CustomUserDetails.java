package es.metrica.mar24.SimuladorRestaurante.entities;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    String getEmail();
}