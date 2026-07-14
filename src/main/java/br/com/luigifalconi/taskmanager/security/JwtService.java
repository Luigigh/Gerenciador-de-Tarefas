package br.com.luigifalconi.taskmanager.security;


import br.com.luigifalconi.taskmanager.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.security.core.userdetails.UserDetails;

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

    public String extractUsername(String token){
        //Esse aqui controi o parser do token
        JwtParser parser = Jwts.parserBuilder()
        //Esse aqui controi a assinatura do token
            .setSigningKey(getSignKey())
            //Esse aqui controi o token
                .build();
        //Esse aqui controi o corpo do token com a assinatura, verificando se o JWT é valido, lê o payload e devolve o objeto CLAIMS
        Claims claims = parser.parseClaimsJws(token).getBody();
        //Esse aqui controi o assunto do token
        return claims.getSubject();
               
    }

    public boolean isTokenValid(String token, UserDetails userdetails){
        
        //Recebe o email extraido do token
        String email = extractUsername(token);

        //Compara o token do email como o do comparado se é expirado ou nao
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }


    public Date extractExpiration(String token){

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());

    }


}
