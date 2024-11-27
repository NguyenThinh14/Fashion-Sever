package com.beginwork.fashionserver.constant;

import com.beginwork.fashionserver.exception.JWTException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class JwtHelper {

    private  static  final  String SECRET_KEY = "PcMeipJ/aP/c5TOJ9XY8SE1c6ZjfsAFTm7IJyLKOTU7q3lTHFRNYuZb7awkGfZPE\n";

    public String generateToken(String email)  {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("Nguyen Thinh ")
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader ,payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();

        } catch (Exception e) {
            log.error("Cannot create jwt token",e);
            throw new JWTException(e.getMessage());
        }



    }

    public boolean validateToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();


            boolean result =  signedJWT.verify(verifier);
            return result && expirationDate.after(new Date());


        } catch (JOSEException e) {
            log.error("Cannot verify token",e.getMessage());

            throw new JWTException(e.getMessage());
        } catch (ParseException e) {
            log.error("Cannot parse token",e.getMessage());

            throw new JWTException(e.getMessage());
        }

    }
}
