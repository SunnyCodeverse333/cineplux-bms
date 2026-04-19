package org.cfs.bms2.service;

import jakarta.transaction.Transactional;
import org.cfs.bms2.entity.User;
import org.cfs.bms2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("custom loadBy Username is called...");
        User user =userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Invalid Email")
        );
        //we need to return UserDetails type
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        Collection<GrantedAuthority>authorities = user.getRoles().stream()
                .map(role -> {
                            String roleName = role.getName().startsWith("ROLE_") ?
                                    role.getName() : "ROLE_" + role.getName();
                            return new SimpleGrantedAuthority(roleName);
                        }).collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false) // 👈 FIXED
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
