package ageria.nagefy.security;


import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.User;
import ageria.nagefy.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createUserToken(User user) {
        return Jwts.builder()
                .claim("role", user.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String createStaffToken(Staff staff) {
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis()))
                .claim("role", staff.getAuthorities().iterator().next().getAuthority())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365))
                .subject(String.valueOf(staff.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String createClientToken(Client client) {
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis()))
                .claim("role", client.getAuthorities().iterator().next().getAuthority())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365))
                .subject(String.valueOf(client.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex){
            throw new UnauthorizedException("PROBLEMS WITH TOKEN, TRY TO LOGIN");
        }
    }

    public String extractIdFromToken(String accessToken) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(accessToken).getPayload().getSubject();
    }
    public String extractRoleFromToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())) // Chiave segreta per verificare il token
                .build()
                .parseSignedClaims(accessToken) // Ottieni le informazioni del token
                .getPayload()
                .get("role", String.class); // Ottieni il ruolo
    }

}
