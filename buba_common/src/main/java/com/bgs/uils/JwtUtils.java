package com.bgs.uils;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 生成token 以及校验相关方法
 */
public class JwtUtils {
    private static final String JWT_PAYLOAD_USER_KEY="user";

    /**
     * 私钥加密 token
     * @param userInfo 载荷中的数据
     * @param privateKey 私钥
     * @param expire 过期时间， 单位 分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire){
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toString(userInfo))
                .setId(createJTI())
                .setExpiration(new Date(expire))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 公钥解析token
     * @param token 用户请求中的token
     * @param publicKey  公钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey){
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    private static String createJTI(){
        return new String(Base64.getDecoder().decode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 获取 token 中 用信息
     * @param token
     * @param publicKey
     * @param userType
     * @param <T>
     * @return
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey,Class<T> userType){
        Jws<Claims> claimsJws = parserToken(token,publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload();
        claims.setId(body.getId());
        claims.setUserInfo(JsonUtils.toBean(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token 中载荷的令牌
     * @param token 用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey){
        Jws<Claims> claimsJws = parserToken(token,publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
}

