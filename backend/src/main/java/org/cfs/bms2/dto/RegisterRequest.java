package org.cfs.bms2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterRequest {

    @NotBlank
    private String username;

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    @NotBlank
    private String phone;
}
