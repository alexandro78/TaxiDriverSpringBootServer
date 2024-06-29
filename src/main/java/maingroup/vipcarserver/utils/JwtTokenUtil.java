package maingroup.vipcarserver.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.entities.Role;
import maingroup.vipcarserver.services.UserDetailsServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.builder;

///class for generating jwt token and authorization by jwt token////
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    //
    private final String secret = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    //Method of generating a new jwt token //////////////
    public String generateToken(String username, Set<Role> roles, Long userId) throws JsonProcessingException {
        return createToken(username, roles, userId);
    }

    ///Method of passing arguments to the generateToken() method////////////
    private String createToken(String username, Set<Role> roles, Long userId) throws JsonProcessingException {

        String rolesString = roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(","));


        return builder()
                .subject(username)
                .claims()
                .add("role", rolesString)
                .add("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 864000004)) //+ 1000 * 60 * 60 * 10//
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    ///Method of obtaining secret key signature for jwt token/////
    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //////responsible for token verification in requests, token identification/authorization by token///
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }


    /////extract the roles from the token
    public List<String> extractRoles(String token) {
        String rolesString = extractClaim(token, claims -> claims.get("role", String.class));
        return Arrays.asList(rolesString.split(","));
    }
    ////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    public Long extractUserId(String token) {
        try {
            Number userIdNumber = extractClaim(token, claims -> claims.get("userId", Number.class));
            return userIdNumber != null ? userIdNumber.longValue() : null;
        } catch (Exception e) {
            System.out.println("Error extracting userId: " + e.toString());
            return null;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
}
