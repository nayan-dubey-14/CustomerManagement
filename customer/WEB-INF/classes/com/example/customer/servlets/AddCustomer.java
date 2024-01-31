//this servlet is responsible for adding a customer
package com.example.customer.servlets;
import com.example.customer.dl.*;
import com.example.customer.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import com.google.gson.*;
public class AddCustomer extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
//first we validate the user, if it invalid user then we redirect it to login window
String authorizationHeader=request.getHeader("Authorization");
if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) 
{
String token=authorizationHeader.substring(7); // Extract the token without "Bearer "
ValidateToken validateToken=new ValidateToken();
if (!validateToken.validateJwtToken(token)) 
{
response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
return;
} 
} 
else 
{
response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
return;
}
PrintWriter printWriter=response.getWriter();
response.setContentType("application/json");
BufferedReader bufferedReader=request.getReader();
StringBuffer buffer=new StringBuffer();
String tmp;
while(true)
{
tmp=bufferedReader.readLine();
if(tmp==null) break;
buffer.append(tmp);
}
String jsonString=buffer.toString();
Gson gson=new Gson();
//checking for email pattern if it invalid then we return the error
CustomerDTO customerDTO=gson.fromJson(jsonString,CustomerDTO.class);
String emailPattern="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
Pattern pattern1=Pattern.compile(emailPattern);
Matcher matcher1=pattern1.matcher(customerDTO.getEmail());
if(matcher1.matches()==false)
{
jsonString=gson.toJson("Invalid email id");
printWriter.print(jsonString);
printWriter.flush();
return;
}
CustomerDAO customer=new CustomerDAO();
//if email pattern is correct then we check for its existstence,if its exits then we should not add it because email is unique
if(customer.emailExists(customerDTO.getEmail())==true) 
{
jsonString=gson.toJson("Email Id exists");
printWriter.print(jsonString);
printWriter.flush();
return;
}
//generating random id and adding customer to database
String customerId=UUID.randomUUID().toString();
customerDTO.setCustomerId(customerId);
customer.addCustomer(customerDTO);
jsonString=gson.toJson("Customer Added");
printWriter.print(jsonString);
printWriter.flush();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
}