package com.bootx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public final class JWTUtils {

    // 主题
    public static final String SUBJECT = "RookieLi";

    // 秘钥
    public static final String SECRETKEY = "hanjiayang5211314119950130blackboy198710061130";

    // 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 7;

    public static String create(String id, Map<String,Object> map){
        JwtBuilder builder= Jwts.builder()
                .id(id)
                .subject(SUBJECT)
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(SECRETKEY.getBytes()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRE));
        for (String key: map.keySet()) {
            builder.claim(key,map.get(key));
        }
        return builder.compact();
    }

    public static Claims parseToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRETKEY.getBytes());
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getId(String token){
        try {
            Claims claims = parseToken(token);
            return claims.get("jti").toString();
        }catch (Exception ignored){
        }
        return null;
    }
}
