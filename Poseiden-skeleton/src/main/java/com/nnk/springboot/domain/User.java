package com.nnk.springboot.domain;

import com.nnk.springboot.validator.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Size(max = 125)
    private String username;
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    @Size(max = 125)
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125)
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

}
