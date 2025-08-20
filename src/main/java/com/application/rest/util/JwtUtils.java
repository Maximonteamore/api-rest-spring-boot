package com.application.rest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("{security.jwt.key.private}")
    private String privateKey;

    @Value("{security.jwt.user.generator}")
    private String userGenerator;


    //metodo para crear un token, el Authentication de aca saco el usuario y las autorizaiones.
    public String createToken(Authentication authentication) {

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);//encripto con HMAC256 mi calve secreta,codifico el token.

        //obtengo username auntenticado para el principal y el authorities para el  security context holder
        String username = authentication.getPrincipal().toString();//obtengo el nombre del usuario authenticado.

        //convierto en string el GrantedAuthority y lo separo por coma con joining por que las autorizaciones podrian ser (read,write,create,etc)
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)//obtengo el permiso lo devuelvo como string
                .collect(Collectors.joining(","));//obtengo los permiso y lo separo por coma.

        //genero el token con jwt,lo codifico.
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)//usuario que genera el token.
                .withSubject(username)//el sujeto ah quien se genera el token al usuario que se esta autenticando.
                .withClaim("authorities", authorities)//genero el claim lo que va en el payload.
                .withIssuedAt(new Date())//la fecha en que se genera el token-
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))//lo genero en milisegundo y le agrego 30 minutos = 1800000 milisegundos.
                .withJWTId(UUID.randomUUID().toString())//genero id random
                .withNotBefore(new Date(System.currentTimeMillis()))//apartir de que momento este token va hacer valido,lo pongo en milisegundo referenciando este momento se puede modificar y decir en que momento es valido.
                .sign(algorithm);//le pongo la firma, le mando el algoritmo de codigificacion.

        return jwtToken;
    }

    //devuelvo le metodo decodificado con decodedjwt recibe un token.
    public DecodedJWT validateToken(String token) {
        try {
            //encriptacion
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            //decodifico
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token); //verifico si es valido el token y lo guardo en decodedjwt para retornarlo.
            return decodedJWT;//devuevo el token decodificado

        } catch (JWTVerificationException exception) {

            throw new JWTVerificationException("Token invalido, not Authorized");
        }
    }

    //devuelvo el usuario que esta dentro del token.
    public String extractUsername(DecodedJWT decodedJWT){
        return  decodedJWT.getSubject().toString();
    }

    //obtengo el claim "nombre" y o retorno,obtengo del payload,recibo token decodificado y el nombre.
    public Claim getSpecificClaim(DecodedJWT decodedJWT,String claimName){
        return decodedJWT.getClaim(claimName);
    }

    //funcion que devuelve un map de string devolviendo todos los claims del token.
    public Map<String,Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }



}
