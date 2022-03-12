package com.axess.ccuser;

import com.axess.ccuser.model.CCUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtTokenUtils {

    //PRIVATE_KEY for signatrue
    private static final String PRIVATE_KEY = "jupter";
    private static final String ISS = "Yaoli";

    //expire 1 hour
    private static final long EXPIRATION_ONE_HOUR = 3600L;
    //expire 1 day
    private static final long EXPIRATION_ONE_DAY = 604800L;

    /**
     * Generate Token
     * @param user
     * @return
     */
    public static String createToken(CCUser user, int expireTimeType){
        //expire time
        long expireTime = 0;
        if (expireTimeType == 0){
            expireTime = EXPIRATION_ONE_HOUR;
        }else {
            expireTime = EXPIRATION_ONE_DAY;
        }

        //Jwt头
        Map<String,Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256");
        Map<String,Object> claims = new HashMap<>();
        //自定义有效载荷部分
        claims.put("id",user.getCcNumber());
        claims.put("account",user.getUserId());

        return Jwts.builder()
                .setIssuer(ISS)
                .setHeader(header)
                //validate load
                .setClaims(claims)
                //sign time
                .setIssuedAt(new Date())
                //set the expire time
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000))
                //Sign using HS256 algorithm, PRIVATE_KEY is the signature key
                .signWith(SignatureAlgorithm.HS256,PRIVATE_KEY)
                .compact();
    }

    /**
     * Verify Token and return a User object with only id, account and type
     * @param token
     * @return
     */
    public static CCUser checkToken(String token){
        //After parsing the token, extract the value from the payload
        CCUser user = new CCUser();
        Claims claims  = getClaimsFromToken(token);
        if(Objects.isNull(claims)){
            return null;
        }
        Long id = Long.parseLong(String.valueOf(getClaimsFromToken(token).get("id"))) ;
        String account = (String) getClaimsFromToken(token).get("account");
        //Encapsulated as User object

        user.setCcNumber(id);
        user.setUserId(account);

        return user;
    }

    /**
     * get payload
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    //Set decryption private key
                    .setSigningKey(PRIVATE_KEY)
                    //Incoming Token
                    .parseClaimsJws(token)
                    //get payload class
                    .getBody();
        }catch (Exception e){
            return null;
        }
        return claims;
    }

    public static void main(String[] args) throws InterruptedException {
        CCUser ccUser = new CCUser();
        ccUser.setUserId("123123");
        ccUser.setCcNumber(123L);
        String token = createToken(ccUser,0);
        //Thread.sleep(2000);
        CCUser ccUser1 =checkToken(token);
        System.out.println(ccUser1.equals(ccUser));
        Thread.sleep(9000);
        CCUser ccUser2 =checkToken(token);
        System.out.println(ccUser1.equals(ccUser2));
    }

}
