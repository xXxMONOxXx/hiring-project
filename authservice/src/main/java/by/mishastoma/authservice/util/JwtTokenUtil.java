package by.mishastoma.authservice.util;

import by.mishastoma.authservice.dto.DecryptResponse;
import by.mishastoma.authservice.exception.InvalidTokenException;
import by.mishastoma.authservice.exception.WrongRoleException;
import by.mishastoma.authservice.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String ID_CLAIM = "user_id";
    private static final String USERNAME_CLAIM = "username";

    public String generateJwtToken(CustomUserDetails user) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + jwtExpiration);
        return Jwts.builder()
                .claim(AUTHORITIES_CLAIM, user.getUserAuthoritiesAsStringList())
                .claim(USERNAME_CLAIM, user.getUsername())
                .claim(ID_CLAIM, user.getId())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void validateJwtToken(String token, String role) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            DecryptResponse decrypt = decrypt(token);
            if (decrypt.getAuthorities() == null || !decrypt.getAuthorities().contains(role)) {
                throw new WrongRoleException("Invalid role, expected " + role);
            }
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    public DecryptResponse decrypt(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return DecryptResponse.builder()
                    .id(claims.get(ID_CLAIM, Long.class))
                    .username(claims.get(USERNAME_CLAIM, String.class))
                    .authorities(claims.get(AUTHORITIES_CLAIM, List.class))
                    .build();
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }
}
