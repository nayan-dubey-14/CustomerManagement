package com.example.customer.util;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.security.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
public class ValidateToken implements java.io.Serializable
{
public boolean validateJwtToken(String token) 
{
String secretKey="yourSecretKeyadfasdfasfasfasfasfafwqerqwerqwerdsafafdfvwefqw";
try 
{
//parsing and validating the token
Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build().parseClaimsJws(token).getBody();
long currentTimeMillis=System.currentTimeMillis();
//comparing both the tokens according to expiration time
//if upcoming token time is less then its generating time then we return true otherwise false;
if(currentTimeMillis<claims.getExpiration().getTime()) 
{
return true;
}
return false;
}catch(ExpiredJwtException e)
{
return false;
}catch (Exception e) 
{
return false;
}
}
}