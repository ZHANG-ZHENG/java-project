package com;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtExample {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 生成JWT
    public static String generateJwt() {
    	//System.out.println(Encoders.BASE64.encode(key.getEncoded()));
    	//Key key2 = Keys.hmacShaKeyFor(key.getEncoded());
		Claims claims = Jwts.claims().setSubject("userclaims");
		claims.put("userId", "userId123");
		claims.put("appType", "appType123");
			
        String jwt = Jwts.builder()
                //.setSubject("user123")
        		.setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key)
                .compact();

        return jwt;
    }

    // 验证JWT
    public static void validateJwt(String jwt) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);

            Claims body = claimsJws.getBody();

            System.out.println("Subject: " + body.getSubject());
            System.out.println("Issued At: " + body.getIssuedAt());
            System.out.println("Expiration: " + body.getExpiration());
            System.out.println("userId: " + body.get("userId", String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 生成JWT
        String jwt = generateJwt();
        System.out.println("Generated JWT: " + jwt);

        // 验证JWT
        validateJwt(jwt);
    }
}
