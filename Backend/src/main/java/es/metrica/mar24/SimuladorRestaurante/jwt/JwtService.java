package es.metrica.mar24.SimuladorRestaurante.jwt;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Clave secreta para firmar y verificar tokens JWT
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Genera un token JWT para el usuario dado.
     *
     * @param user los detalles del usuario para incluir en el token
     * @return el token JWT generado
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        // Construye el token JWT con los detalles del usuario y las reclamaciones adicionales
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 horas de expiración
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtiene la clave secreta como instancia de Key
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el nombre de usuario del token JWT dado.
     *
     * @param token el token JWT
     * @return el nombre de usuario extraído del token
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si el token JWT dado es válido para el usuario dado.
     *
     * @param token el token JWT
     * @param userDetails los detalles del usuario a comparar con el token
     * @return true si el token es válido para el usuario dado, false de lo contrario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Obtiene todas las reclamaciones del token JWT dado
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtiene una reclamación específica del token JWT utilizando una función de resolución de reclamaciones
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtiene la fecha de expiración del token JWT dado
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Verifica si el token JWT dado ha expirado
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
