package maingroup.vipcarserver.websocket.websockethandlers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import maingroup.vipcarserver.dtos.driverhomescreendtos.PingResponseDto;
import maingroup.vipcarserver.dtos.driverhomescreendtos.SocketHomeScreenRequestDto;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.Map;

public class DriverHomeScreenWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int counter = 0;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            System.out.println("UserId from WebSocket session: " + userId);
        } else {
            System.out.println("UserId is not present in WebSocket session");
        }
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws IOException {
        try {
            // Check if the incoming message is a ping message
            if (isPingMessage(message.getPayload())) {
                // Processing ping message
                processPingMessage(session);
            } else {
                // Processing a regular message
                processRegularMessage(session, message.getPayload());
            }
        } catch (JsonProcessingException e) {
            // Send an error message in case of incorrect JSON format or other processing error
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new SocketHomeScreenRequestDto("error", "Error processing JSON message"))));
        } catch (Exception e) {
            // Sending an error message in case of an exception during message processing
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new SocketHomeScreenRequestDto("error", "Error processing message"))));
        }
    }

    // Method to check if the message is a ping message
    private boolean isPingMessage(String payload) throws JsonProcessingException {
        Map<String, Object> incomingMessage = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
        return incomingMessage.containsKey("ping") && Boolean.TRUE.equals(incomingMessage.get("ping"));
    }

    // Method for processing ping messages
    private void processPingMessage(WebSocketSession session) throws IOException {
        // Reply to a ping message with a pong message
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new PingResponseDto("pong"))));
    }

    // Method for processing regular messages
    private void processRegularMessage(WebSocketSession session, String payload) throws IOException {
        SocketHomeScreenRequestDto incomingSocketHomeScreenRequestDto;
        try {
            // Convert incoming JSON message to SocketHomeScreenRequestDto object
            incomingSocketHomeScreenRequestDto = objectMapper.readValue(payload, SocketHomeScreenRequestDto.class);

            // Збільшення лічильника (для дебагу) видалити в майбутньому
            counter++;

            // Processing an incoming message and preparing a reply
            SocketHomeScreenRequestDto responseDto = processIncomingDto(incomingSocketHomeScreenRequestDto);

            // Sending a reply message in JSON format
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDto)));
        } catch (Exception e) {
            // Sending an error message in case of an exception during message processing
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new SocketHomeScreenRequestDto("error", "Error processing message"))));
        }
    }

    // Method for processing incoming DTO and preparing a response
    private SocketHomeScreenRequestDto processIncomingDto(SocketHomeScreenRequestDto incomingDto) {
        // Here you can add logic for processing incoming data
        // In this example, we simply return a response with the modified content
        return new SocketHomeScreenRequestDto(incomingDto.getDriverLocationLatitude() + " " + counter, incomingDto.getDriverLocationLongitude() + " " + counter);
    }
}