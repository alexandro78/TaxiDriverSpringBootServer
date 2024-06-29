package maingroup.vipcarserver.websocket;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.services.UserDetailsServiceImpl;
import maingroup.vipcarserver.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WebSocketHttpHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {
        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtTokenUtil.extractUsername(token));
                if (jwtTokenUtil.isTokenValid(token, userDetails)) {
                    List<GrantedAuthority> authorities = jwtTokenUtil.extractRoles(token).stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());


                    // Extract userId from the token ///////////////////////////
                    Long userId = jwtTokenUtil.extractUserId(token);
                    if (userId == null) {
                        response.setStatusCode(HttpStatus.FORBIDDEN);
                        return false;
                    }
                    System.out.println("userId: " + userId);
                    /////////////////////////////////////////////////////////////


                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    attributes.put("userDetails", userDetails); // Store user details in WebSocket session attributes
                    attributes.put("userId", userId); // Сохраняем userId в атрибутах сессии

                    return true; // Continue with the handshake
                }
            } catch (Exception e) {
                // Log the exception or handle it as per your application's requirement
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return false;
            }
        }
        response.setStatusCode(HttpStatus.FORBIDDEN); // Unauthorized access
        return false;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, Exception exception) {
        SecurityContextHolder.clearContext(); // Clear context after the handshake
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
