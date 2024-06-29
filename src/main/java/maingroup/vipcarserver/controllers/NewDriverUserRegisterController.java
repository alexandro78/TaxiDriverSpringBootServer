package maingroup.vipcarserver.controllers;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.UserRegistrationDto;
import maingroup.vipcarserver.entities.Driver;
import maingroup.vipcarserver.entities.User;
import maingroup.vipcarserver.services.DriverService;
import maingroup.vipcarserver.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//part of the code concerning registration of new users//////
@RestController
@RequiredArgsConstructor
@RequestMapping("/new")
public class NewDriverUserRegisterController {

    private final UserService userService;
    private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserRegistrationDto userRegistrationDto
    ) {
        try {
            User user = userService.registerNewUser(userRegistrationDto);

            // Create a driver record linked to the newly created user
            Driver driver = driverService.createDriver(user.getId());

            return ResponseEntity.ok("User added successfully with ID: " + user.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
