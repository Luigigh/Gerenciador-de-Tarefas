package br.com.luigifalconi.taskmanager.security;


import br.com.luigifalconi.taskmanager.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSignKey(){
        byte[] keyByte = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyByte);
    };


    //Esse metodo cria o token com os respectivos valores necessários.
    public String generateToken(User user){

        return Jwts.builder()

                //Esse é o assunto do token, vou usar o email do usuario
                .subject(user.getEmail())

                //Esse é a data de criação (em milisegundos)
                .issuedAt(new Date(System.currentTimeMillis()))

                //Esse é a data de expiração ''          ''
                .expiration(new Date(System.currentTimeMillis() + expiration))

                //Esse é a assinatura então uso o metodo que gera a assinatura
                .signWith(getSignKey())

                //Compacta tudo numa String
                .compact();


    }


}
