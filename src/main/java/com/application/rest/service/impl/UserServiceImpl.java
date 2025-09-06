package com.application.rest.service.impl;

import com.application.rest.controllers.dto.AuthCreateUserRequest;
import com.application.rest.controllers.dto.AuthLoginRequest;
import com.application.rest.controllers.dto.AuthResponse;
import com.application.rest.entities.RoleEntity;
import com.application.rest.entities.UserInfo;
import com.application.rest.repository.RoleRepository;
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
import java.util.Set;
import java.util.stream.Collectors;


@Service

//UserDetailsService interface de security para manejar usuarios
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override //metodo para spring busque a los usuarios resgistrado en la BD
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo user = userRepository.findByUsername(username);

        //GrantedAuthority es una interfaz que maneja los permisos y es la manera que spring security maneja los roles y permisos, creo una lista vacia de SimpleGrantedAuthority para luego agregarle los roles y los permisos.
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles() //agrego los roles del usuario a la lista authorityList ,de SimpleGrantedAuthority.
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))) );

        user.getRoles().stream()//uso getRoles para acceder a los permisos,lo convierto en un stream los recorro y agrego a la lista de authorityList los permisos del usuario.
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        return User.builder()  //construyo un usuariodetail con el usuario que recupero en la BD
                .username(user.getUsername())
                .password(user.getPassword())
                //.roles(user.getRoles().toString())
                .authorities(authorityList)
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

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) throws IllegalAccessException {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();

        //guardo en roleEntitySet los roles que coinsidan con las busqueda en la llamada al metodo findRoleEntitiesByRoleEnumIn que trae una lista de roles de la bd.
        Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        //controlo que los roles o rol existan para poder crear el usuario si no existe lanzo error
        if (roleEntitySet.isEmpty()){
            throw new IllegalAccessException("Los roles especificados no existen.");
        }

        UserInfo userInfo =  UserInfo.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntitySet)
                .build();

       UserInfo userCreated = userRepository.save(userInfo);//guardo usuario

        //agrego los roles y permiso a las lista.
        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();//lista de permisos que tiene el user para generar el token.

        //agrego los roles en authorityList.
        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userCreated.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(),userCreated.getPassword(),authorityList);

            String accessToken = jwtUtils.createToken(authentication);
            AuthResponse authResponse = new AuthResponse(userCreated.getUsername(),"User created successfully",accessToken,true);

       return  authResponse;
    }

}
