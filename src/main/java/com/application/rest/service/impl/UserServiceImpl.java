package com.application.rest.service.impl;



import com.application.rest.entities.UserInfo;
import com.application.rest.repository.UserRepository;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

//UserDetailsService interface de security para manejar usuarios
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override //metodo para spring busque a los usuarios resgistrado en la BD
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);

        return User.builder()  //construyo un usuariodetail con el usuario que recupero en la BD
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }
}
