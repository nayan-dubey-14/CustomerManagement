package com.example.customer.servlets;
import com.example.customer.dl.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
public class Login extends HttpServlet
{
private static String secret="yourSecretKeyadfasdfasfasfasfasfafwqerqwerqwerdsafafdfvwefqw";
private static String generateJwtToken(String loginId) 
{
//this is the expiration time of token i.e. 1hr
long expirationTimeMillis=System.currentTimeMillis()+3600000;
Key k=getSigningKey();
//this will generate the token and return it
String str=Jwts.builder().setSubject(loginId).setIssuedAt(new Date()).setExpiration(new Date(expirationTimeMillis)).signWith(k).compact();
return str;
}
private static Key getSigningKey() 
{
byte[] keyBytes=secret.getBytes(StandardCharsets.UTF_8);
return Keys.hmacShaKeyFor(keyBytes);
}
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception e)
{
//do nothing
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
BufferedReader br=request.getReader();
String s;
StringBuffer sb=new StringBuffer();
while(true)
{
s=br.readLine();
if(s==null) break;
sb.append(s);
}
String jsonString=sb.toString();
Gson gson=new Gson();
AdministratorDTO admin=gson.fromJson(jsonString,AdministratorDTO.class);
PrintWriter pw=response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
//validating username and password
try
{
AdministratorDAO administrator=new AdministratorDAO();
AdministratorDTO administratorDTO=administrator.getByUsername(admin.getUsername());
if(administratorDTO.getPassword().equals(admin.getPassword())==false)
{
jsonString=gson.toJson("Invalid username/password");
pw.print(jsonString);
pw.flush();
return;
}
//generating JWT token and sending it as response
String token=generateJwtToken(admin.getUsername());
pw.print(token);
pw.flush();
}catch(DAOException daoException)
{
jsonString=gson.toJson("Invalid username/password");
pw.print(jsonString);
pw.flush();
return;
}
}catch(Exception exception)
{
try
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception e)
{
//do nothing
}
}
}
}