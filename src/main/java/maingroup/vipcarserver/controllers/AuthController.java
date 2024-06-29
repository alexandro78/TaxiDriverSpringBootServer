package maingroup.vipcarserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.UserAuthDto;
import maingroup.vipcarserver.entities.Role;
import maingroup.vipcarserver.services.UserDetailsServiceImpl;
import maingroup.vipcarserver.services.UserService;
import maingroup.vipcarserver.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

//part of the code concerning login (identification/authorization of users on the server//////
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    //Dependency injection for token generation on successful authorization//
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserAuthDto userAuthDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuthDto.getEmail(),
                            userAuthDto.getPassword()
                    )
            );

            // Getting user roles
            Set<Role> userRoles = userService.findRolesByEmail(userAuthDto.getEmail());
            // Getting userId by email
            Long userId = userService.findUserIdByEmail(userAuthDto.getEmail());

            //Generating a token////
            final String token = jwtTokenUtil.generateToken(userAuthDto.getEmail(), userRoles, userId);
            //////////////////////

            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
