package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 100)
    private String username;

    @Column(nullable = false,length = 100)
    private String password;

    @Column(nullable = false,length = 10)
    private String role;

}
