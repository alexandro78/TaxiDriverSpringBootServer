package maingroup.vipcarserver.websocket;

import maingroup.vipcarserver.services.UserDetailsServiceImpl;
import maingroup.vipcarserver.utils.JwtTokenUtil;
import maingroup.vipcarserver.websocket.websockethandlers.DriverHomeScreenWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketHttpHandshakeInterceptor handshakeInterceptor = new WebSocketHttpHandshakeInterceptor(jwtTokenUtil, userDetailsServiceImpl);

        // Register WebSocketHandler with adding interceptor for authentication
        registry.addHandler(new DriverHomeScreenWebSocketHandler(), "/websocket")
                .addInterceptors(handshakeInterceptor);
    }
}



