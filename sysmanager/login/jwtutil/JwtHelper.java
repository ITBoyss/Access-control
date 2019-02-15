package com.yanqiancloud.control.sysmanager.login.jwtutil;

import com.yanqiancloud.control.sysmanager.config.JwtProperties;
import com.yanqiancloud.control.sysmanager.login.domian.SysUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.EnumSet;

/**
 * @ProjectName: cloud-base
 * @Description: jwt工具类
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/25 23:09
 * @Version: 1.0.0
 */
@Component
public class JwtHelper {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateJWT(Authentication authentication) {
        UserDetails details = (SysUserDetails) authentication.getPrincipal();
        String username = details.getUsername();
        String usercode = ((SysUserDetails) details).getUserCode();
        String email = ((SysUserDetails) details).getEmail();

        String alg = jwtProperties.getAlg();
        String signSecret = jwtProperties.getSecret();
        Long ttl = jwtProperties.getExp();
        EnumSet<SignatureAlgorithm> algorithms = EnumSet.allOf(SignatureAlgorithm.class);
        SignatureAlgorithm signatureAlgorithm = null;
        for (SignatureAlgorithm algorithm : algorithms) {
            if (StringUtils.equalsIgnoreCase(algorithm.getValue(), alg)) {
                signatureAlgorithm = algorithm;
            }
        }
        if (signatureAlgorithm == null) {
            throw new JwtException("Not Support IllegalSignatureAlgorithm：" + alg);
        }
        //生成签名密钥
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(signSecret);
        Key signKey = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());


        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", jwtProperties.getTyp())
                .claim("username", username)
                .claim("userCode",usercode)
                .claim("email", email)
                .setIssuer(jwtProperties.getIss())
                .setAudience(jwtProperties.getAud())
                .setId(jwtProperties.getJti())
                .setSubject(jwtProperties.getSub())
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, signKey);


        //添加Token过期时间
        if (ttl >= 0) {
            Long expMillis = System.currentTimeMillis() + ttl;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(new Date());
        }
        return jwtProperties.TOKEN_PREFIX + builder.compact();
    }

    public Claims parseJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperties.getSecret()))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
