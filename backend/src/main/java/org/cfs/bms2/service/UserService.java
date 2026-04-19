package org.cfs.bms2.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.dto.RegisterRequest;
import org.cfs.bms2.entity.Role;
import org.cfs.bms2.entity.User;
import org.cfs.bms2.repository.RoleRepository;
import org.cfs.bms2.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    //Normal user
    // Rename registerUser → register, and return User
    public User register(RegisterRequest request) {

        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Couldn't find the user role..."));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();

        return userRepository.save(user); // return the saved user
    }

    // Also fix registerAdmin to return User and assign the role
    public User registerAdmin(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Couldn't find the admin role..."));

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(adminRole)) // ← this line was MISSING!
                .build();

        return userRepository.save(user);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User Not found "));
    }



}
