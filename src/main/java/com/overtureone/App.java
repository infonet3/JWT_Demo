package com.overtureone;

import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.*;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;
import java.util.*;

/**
 * Demo App
 *
 */
public class App
{
    public static void main( String[] args )
    {

        //Create and sign a token
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");

            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("owner", "Mr. Jones");
            token = JWT.create()
                    .withHeader(headerClaims)
                    .withClaim("name", 123)
                    .withIssuer("auth0")
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            System.out.println(exception.toString());
        }

        //Verify a Token
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }

        //Decode a Token
        try {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println(jwt.getHeader());
            System.out.println(jwt.getPayload());

            Claim claim = jwt.getHeaderClaim("owner");
            System.out.println(claim.asString());

            Claim claim2 = jwt.getClaim("name");
            System.out.println(claim2.asInt());
        } catch (JWTDecodeException exception){
            System.out.println(exception.toString());
        }

    }
}
