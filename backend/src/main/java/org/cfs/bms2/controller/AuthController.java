package org.cfs.bms2.controller;


import jakarta.validation.Valid;
import org.cfs.bms2.dto.LoginRequest;
import org.cfs.bms2.dto.RegisterRequest;
import org.cfs.bms2.entity.User;
import org.cfs.bms2.service.JwtService;
import org.cfs.bms2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("user/register")
    private ResponseEntity<User> registerUser(@Valid @RequestBody RegisterRequest userRequest){
//   .ok() ---->   equivalent to wrting new ResponseEntity<>(user, 200);
        return ResponseEntity.ok(userService.register(userRequest));
    }
    @PostMapping("admin/register")
    private ResponseEntity<User> registerAdmin(@Valid @RequestBody RegisterRequest userRequest){
//   .ok() ---->   equivalent to wrting new ResponseEntity<>(user, 200);
        return ResponseEntity.ok(userService.registerAdmin(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String ,Object>> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User dbUser = userService.getUserRepository()
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(userDetails.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("id", dbUser.getId());
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("username", userDetails.getUsername());
        response.put("roles",
                userDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .toList()
        );
        response.put("Expire time", jwtService.getJwtExpirationTime());

        System.out.println("User logged in " + userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    private ResponseEntity<List<User>> getAllUsers(){
//   .ok() ---->   equivalent to wrting new ResponseEntity<>(user, 200);
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/admin/{id}")
    private ResponseEntity<User> getUserById(@PathVariable Long id){
//   .ok() ---->   equivalent to wrting new ResponseEntity<>(user, 200);
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
