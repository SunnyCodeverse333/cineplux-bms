package org.cfs.bms2.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private Long id;       // 👈 Add this field!
    private String username;
    private List<String> roles;
}