package org.cfs.bms2.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false , unique = true)
    private String email;
    @Column(nullable = false , unique = true)
    private String username;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String password;

    //user and role have many to many relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns =@JoinColumn(name="user_id"),
            inverseJoinColumns =@JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }




}
