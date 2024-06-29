package maingroup.vipcarserver.services;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.UserRegistrationDto;
import maingroup.vipcarserver.entities.CitySector;
import maingroup.vipcarserver.entities.Role;
import maingroup.vipcarserver.entities.User;
import maingroup.vipcarserver.repositories.RoleRepository;
import maingroup.vipcarserver.repositories.UserRepository;
import maingroup.vipcarserver.repositories.calculatetriproutedistance.CitySectorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

//part of the code concerning registration of new users//////
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CitySectorRepository citySectorRepository;

    public User registerNewUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Користувач з цим email вже існує в системі");
        }

        User newUser = new User();
        newUser.setFirstName(userRegistrationDto.getFirstName());
        newUser.setLastName(userRegistrationDto.getLastName());
        newUser.setEmail(userRegistrationDto.getEmail());
        newUser.setPhone(userRegistrationDto.getPhone());
        newUser.setBirthDate(userRegistrationDto.getBirthDate());
        newUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        // Assign default role or based on business logic
        Role userRole = roleRepository.findByRoleName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        newUser.setRoles(Collections.singleton(userRole));

        // Find the city sector by name
        CitySector citySector = citySectorRepository.findBySityName("Дніпро")
                .orElseThrow(() -> new RuntimeException("City sector not found"));
        newUser.setCitySector(citySector);

        return userRepository.save(newUser);
    }


    /////Method to extract user roles to add them to the jwt token///////////
    public Set<Role> findRolesByEmail(String email) {
        // Get user ID by email
        Long userId = userRepository.findUserIdByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Получаем роли по ID пользователя
        return roleRepository.findRolesByUserId(userId);
    }
    ////////////////////////////////////////////////////////////////////////////////////


    // Method for retrieving user ID by email//////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    public Long findUserIdByEmail(String email) {
        return userRepository.findUserIdByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    ////////////////////////////////////////////////////////////////////////////////////


//    public User registerNewUser(UserRegistrationDto userRegistrationDto) {
//        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
//            throw new RuntimeException("Користувач з цим email вже існує в системі");
//        }
//
//        User newUser = new User();
//        newUser.setFirstName(userRegistrationDto.getFirstName());
//        newUser.setLastName(userRegistrationDto.getLastName());
//        newUser.setEmail(userRegistrationDto.getEmail());
//        newUser.setPhone(userRegistrationDto.getPhone());
//        newUser.setBirthDate(userRegistrationDto.getBirthDate());
//        newUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
//
//        // Assign default role or based on business logic
//        Role userRole = roleRepository.findByRoleName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
//        newUser.setRoles(Collections.singleton(userRole));
//
//        return userRepository.save(newUser);
//    }
}
