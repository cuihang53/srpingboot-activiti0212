//package com.activiti.config.security;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.CompressionCodecs;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtTokenManager implements TokenManager{
//    @Value("${token.expire}")
//    private long tokenExpiration = 3600;
//    @Value("${token.key}")
//    private String tokenSignKey;
//
//    @Override
//    public String createToken(String username) {
//        String token = Jwts.builder().setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
//                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
//        return token;
//    }
//
//    @Override
//    public String getUserFromToken(String token) {
//        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
//        return user;
//    }
//
//    @Override
//    public void removeToken(String token) {
//        //jwttoken无需删除，客户端扔掉即可。
//    }
//
//}
