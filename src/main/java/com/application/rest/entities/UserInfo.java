package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    //@Column(nullable = false,length = 10)
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)//con EAGER le digo que carge todos los roles,cascade le digo que si guardo un usuario guarde los roles asociados.
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

}
