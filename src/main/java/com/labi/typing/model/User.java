package com.labi.typing.model;

import com.labi.typing.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_user")
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @Column(nullable = false, unique = true) @Length(min = 3, max = 20)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false) @Length(min = 6)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String email, String password,  UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
