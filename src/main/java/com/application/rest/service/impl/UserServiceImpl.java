package com.application.rest.service.impl;



import com.application.rest.controllers.dto.AuthCreateUser;
import com.application.rest.controllers.dto.AuthLoginRequest;
import com.application.rest.controllers.dto.AuthResponse;
import com.application.rest.entities.UserInfo;
import com.application.rest.repository.UserRepository;
import com.application.rest.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

//UserDetailsService interface de security para manejar usuarios
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override //metodo para spring busque a los usuarios resgistrado en la BD
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);

        return User.builder()  //construyo un usuariodetail con el usuario que recupero en la BD
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }

    //metodo para logear recibe el authLoginRequest que contiene el nombre y la contraseña
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){

        //recupero el usuario y el pw que me envina  AuthLoginRequest
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();


        Authentication authentication = this.authenticate(username,password);//autenticacion si las credenciales son correcta.
        SecurityContextHolder.getContext().setAuthentication(authentication);//guardo y lo envio el ibjeto autenticado al contex.

        String accesToken =jwtUtils.createToken(authentication);//devuelvo el token de acceso.

        //guardo el authresonse con sus campos correspondiente.
        AuthResponse authResponse = new AuthResponse(username, "User loged succesfuly",accesToken,true);

        return authResponse;

    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        //controlo que usuario exista
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        //controlo si no son iguales retorno error.
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        //devuelvo el objeto de autentacion, que es usado en la vvariable authentication del metodo loginUser.
        return new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(),userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUser authCreateUser){
        String username = authCreateUser.username();
        String password = authCreateUser.password();
        String role = authCreateUser.role();

        UserInfo userInfo =  UserInfo.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

       UserInfo userCreated = userRepository.save(userInfo);//guardo usuario

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();//lista de permisos que tiene el user para generar el token.
        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(),userCreated.getPassword(),authorityList);

            String accessToken = jwtUtils.createToken(authentication);
            AuthResponse authResponse = new AuthResponse(userCreated.getUsername(),"User created successfully",accessToken,true);

       return  authResponse;
    }

}
