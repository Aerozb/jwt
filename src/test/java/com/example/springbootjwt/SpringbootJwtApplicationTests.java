package com.example.springbootjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

//@SpringBootTest
class SpringbootJwtApplicationTests {

    Algorithm algorithm = Algorithm.HMAC256("miyao");

    @Test
    void createJwt() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 600);

        /*
         * header可以不写有默认值
         * payload 通常⽤来存放⽤⼾信息
         * signature 是前两个合起来的签名值
         *
         * withClaim()添加自定义声明值。
         */
        String token = JWT.create().
                withClaim("userId", 12).//payload
                withClaim("name", "nick").//payload
                withExpiresAt(instance.getTime()).//指定令牌的过期时间
                sign(algorithm);//签名，密钥⾃⼰记住
        System.out.println(token);
    }

    /**
     * 令牌验证:根据令牌和签名解析数据
     */
    @Test
    void verifyJwt() {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoibmljayIsImV4cCI6MTYzNzgxMjczNiwidXNlcklkIjoxMn1._bQvPRx2rdj1knUICCYdb0-tnFUsKrZcR-2uxQiNqpQ");// 验证并获取解码后的token
        System.out.println(verify.getClaims()); // {name="nick", exp=1637812736, userId=12}
        System.out.println(verify.getClaim("userId")); // 12
        System.out.println(verify.getClaim("name")); // "nick"
    }
}
