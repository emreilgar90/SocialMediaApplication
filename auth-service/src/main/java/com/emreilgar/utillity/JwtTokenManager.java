package com.emreilgar.utillity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.emreilgar.exception.AuthManagerException;
import com.emreilgar.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    @Value("${jwt.secretKey")
    String secretKey;

    @Value("${jwt.secretKey")
    String audience;

    @Value("${jwt.secretKey")
    String issuer;

    public String createToken(long id){
        String token= null;
        Date date = new Date(System.currentTimeMillis()+(1000*60*5));
        try{
            token= JWT.create().withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id",id)
                    .sign(Algorithm.HMAC256(secretKey));

        }catch (Exception e){
             System.out.println(e.getMessage());
        }
        return token;
    }

    public Optional<Long> getUserId(String token){
        try {
            Algorithm algorithm= Algorithm.HMAC256(secretKey);
            JWTVerifier verifier= JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withAudience(audience)
                    .build();
            DecodedJWT decodedJWT= verifier.verify(token);
            if(decodedJWT==null){
                throw new AuthManagerException(ErrorType.INVALID_TOKEN);

            }
            Long id= decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception e){
            return Optional.empty();

        }
    }
}
