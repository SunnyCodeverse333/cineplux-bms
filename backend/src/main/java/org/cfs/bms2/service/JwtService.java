package org.cfs.bms2.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long jwtExpirationTime;

    //generate Token
    public String generateToken(String username){
        return generateToken(new HashMap<>() , username);
    }

    private String generateToken(HashMap<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(getSigningKey()) //This creates a signature using your secret key.If anyone changes token content, signature will no longer match
                .compact(); //xxxxx.yyyyy.zzzzz
    }

    private SecretKey getSigningKey() {
        byte[] bytes= secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUserName(String token){
        return extractClaim(token , claim ->claim.getSubject());
    }

    private <T> T extractClaim(String token, Function<Claims ,T>resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) ////  key used for verification
                .build() //now create the final parser object
                .parseSignedClaims(token) // //  verification happens HERE
                .getPayload();
    }
    public Date extractExpiration(String token){
        return extractClaim(token, claim -> claim.getExpiration()); // can also pass - claim::getExpiration
    }
    public boolean isTokenValid(String token , UserDetails userDetails){
            final String username =extractUserName(token);

            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


}
