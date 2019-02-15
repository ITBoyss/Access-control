package com.yanqiancloud.control.sysmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

/**
 * @ProjectName: cloud-base
 * @Description: jwt配置类
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/26 9:06
 * @Version: 1.0.0
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * token对应响应头属性
     */
    public final String TOKEN_HEADER = "Authorization";

    /**
     * token前缀
     */
    public final String TOKEN_PREFIX = "Bearer ";

    /**
     * 要拦截的url,多个url用逗号隔开,ant pattern
     */
    private String url;

    /**
     * JWT的签发主体
     */
    private String iss;

    /**
     * JWT的主体，即它的所有人
     */
    private String sub;

    /**
     * JWT的接收对象
     */
    private String aud;

    /**
     * 是一个时间戳，代表这个JWT的过期时间
     */
    private Long exp;

    /**
     * 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
     */
    private String nbf;

    /**
     * 是一个时间戳，代表这个JWT的签发时间
     */
    private String iat;

    /**
     * 是JWT的唯一标识
     */
    private String jti;

    /**
     * token采用的加密算法
     */
    private String alg = "HS256";

    /**
     * token类型
     */
    private String typ = "JWT";

    /**
     * 私钥
     */
    private String secret;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getNbf() {
        return nbf;
    }

    public void setNbf(String nbf) {
        this.nbf = nbf;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getJti() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
