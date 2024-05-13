package com.serene.platform.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.serene.platform.common.exception.SessionExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: JWT工具类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:56
 */
@Component
public class JwtUtils {

    /**
     * 密钥
     */
    @Value("${platform-config.system.tokenSecret}")
    private String secret;


    /**
     * 默认超时时间 30分钟
     */
    private final Long JWT_DEFAULT_EXPIRE_SECONDS = 30 * 60L;


    /**
     * 获取超时时间
     *
     * @param validSpan 时长，单位 秒
     * @return
     */
    private Date getExpireTime(long validSpan) {

        // 生成JWT过期时间
        long nowMilliSecond = System.currentTimeMillis();
        if (validSpan < 0) {
            validSpan = JWT_DEFAULT_EXPIRE_SECONDS;
        }
        long expMilliSecond = nowMilliSecond + validSpan * 1000;
        Date exp = new Date(expMilliSecond);
        return exp;
    }

    /**
     * 生成带主题的令牌
     *
     * @param subject   主题
     * @param validSpan 有效时长，单位秒
     * @return jwt令牌
     */
    public String generateTokenWithSubject(String subject, long validSpan) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Date expireTime = getExpireTime(validSpan);

        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(expireTime)
                .sign(algorithm);
        return token;

    }

    /**
     * 验证令牌
     *
     * @param token
     * @return
     */
    public void verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        try {
            verifier.verify(token);
        } catch (Exception ex) {
            throw new SessionExpiredException("令牌无效或过期，请重新登录");
        }
    }


    /**
     * 解码令牌
     *
     * @param token
     * @return
     */
    public DecodedJWT decode(String token) {
        return JWT.decode(token);
    }

    /**
     * 获取主题
     *
     * @param token 令牌
     * @return 主题
     */
    public String getSubject(String token) {
        return decode(token).getSubject();
    }

}
