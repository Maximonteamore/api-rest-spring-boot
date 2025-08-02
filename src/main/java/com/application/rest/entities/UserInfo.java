package com.application.rest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserInfo {

    @Id
    private Integer id;

    @Column(nullable = false,unique = true,length = 30)
    private String username;

    @Column(nullable = false,length = 70)
    private String password;

    @Column(nullable = false,length = 10)
    private String role;

}
