package edu.kent.babelpages.lib.security;

import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {
    private final SecurityProperties securityProperties;

    public JWTService(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public Authentication extractAuthentication(HttpServletRequest request) {
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(tokenHeader == null || !tokenHeader.startsWith(securityProperties.getJwtHeaderPrefix())) return null;

        String token = tokenHeader.replace(securityProperties.getJwtHeaderPrefix(), "").trim();
        var claims = Jwts.parserBuilder().
                setSigningKey(securityProperties.getJwtSecret().getBytes())
                .deserializeJsonWith(new JacksonDeserializer<>(Map.of("user", UserInfoDTO.class)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        if(!claims.getIssuer().equals(securityProperties.getJwtIssuer())) return null;
        var user = claims.get("user", UserInfoDTO.class);

        if(user == null) return null;

        return UsernamePasswordAuthenticationToken.authenticated(user, user.getId(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }

    public String createToken(UserInfoDTO userInfoDTO) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(securityProperties.getJwtSecret().getBytes()))
                .serializeToJsonWith(new JacksonSerializer<>())
                .setIssuedAt(toDate(LocalDate.now()))
                .setExpiration(toDate(LocalDate.now().plusDays(7)))
                .setIssuer(securityProperties.getJwtIssuer())
                .setSubject(userInfoDTO.getId().toString())
                .addClaims(Map.of("user", userInfoDTO))
                .compact();
    }

    // convert from LocalDate to legacy Date for Jwts builder
    public static Date toDate(LocalDate date){
        return Date.from(date.atStartOfDay(ZoneOffset.UTC).toInstant());
    }
}
