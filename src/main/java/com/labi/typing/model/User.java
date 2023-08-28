package com.labi.typing.model;

import com.labi.typing.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
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

    @Column(nullable = false) @Length(min = 3, max = 20)
    private String username;

    @Column(nullable = false)
    @Email(message = "Invalid email address")
    private String email;

    @Column(nullable = false) @Length(min = 6, max = 255)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
