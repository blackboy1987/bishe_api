package com.bootx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Map;

/**
 * @author Administrator
 */
public class JWTUtils {
    public static SecretKey key = Jwts.SIG.HS256.key().build();
    public static String create(String id, Map<String,Object> map){
        return Jwts.builder().id(id).claims(map).subject("Joe").signWith(key).compact();
    }

    public static Claims parseToken(String token){
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static String getKey(String token,String key){
        try {
            Claims claims = parseToken(token);
            return claims.get(key).toString();
        }catch (Exception ignored){
        }
        return null;
    }
}
