package maingroup.vipcarserver.controllers;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.driverhomescreendtos.UpdateCarArrivalRadiusRequestDto;
import maingroup.vipcarserver.dtos.driverhomescreendtos.UpdateCarArrivalRadiusResponseDto;
import maingroup.vipcarserver.entities.Setting;
import maingroup.vipcarserver.entities.User;
import maingroup.vipcarserver.entities.Driver;
import maingroup.vipcarserver.entities.Role;
import maingroup.vipcarserver.repositories.SettingRepository;
import maingroup.vipcarserver.repositories.DriverRepository;
import maingroup.vipcarserver.repositories.UserRepository;
import maingroup.vipcarserver.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/driver-home-screen")
public class DriverHomeScreenController {
    private final SettingRepository settingRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(Authentication authentication, HttpServletRequest request) {
        try {

            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            Set<Role> userRoles = userService.findRolesByEmail(email);


            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("email", user.getEmail());
            userInfo.put("phone", user.getPhone());
            userInfo.put("rating", user.getRating());
            userInfo.put("roles", userRoles.stream().map(Role::getRoleName).collect(Collectors.toList()));

            Optional<Driver> vipDriver = driverRepository.findByUserId(user.getId());
            vipDriver.ifPresent(driver -> {
                Map<String, Object> driverInfo = new HashMap<>();
                driverInfo.put("driverId", driver.getId());
                driverInfo.put("carArrivalRadius", driver.getCarArrivalRadius());
                userInfo.put("driver", driverInfo);
            });


            Optional<Setting> optionsSetting = Optional.ofNullable(user.getSetting());
            optionsSetting.ifPresent(setting -> {
                Map<String, Object> settingDetails = new HashMap<>();
                settingDetails.put("option1", setting.getOption1());
                settingDetails.put("option2", setting.getOption2());
                userInfo.put("optionsSettings", settingDetails);
            });


            return ResponseEntity.ok().body(userInfo);
        } catch (
                BadCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/update-car-arrival-radius")
    public ResponseEntity<?> updateCarArrivalRadius(@RequestBody UpdateCarArrivalRadiusRequestDto updateCarArrivalRadiusRequestDto, Authentication authentication) {
        try {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            Driver driver = driverRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Driver profile not found for user ID: " + user.getId()));

            driver.setCarArrivalRadius(updateCarArrivalRadiusRequestDto.getCarArrivalRadius());
            driverRepository.save(driver);

            UpdateCarArrivalRadiusResponseDto responseDto = new UpdateCarArrivalRadiusResponseDto(updateCarArrivalRadiusRequestDto.getCarArrivalRadius());

            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating car arrival radius: " + e.getMessage());
        }
    }
}