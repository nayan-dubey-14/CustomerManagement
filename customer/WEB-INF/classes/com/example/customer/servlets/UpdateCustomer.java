package com.example.customer.servlets;
import com.example.customer.dl.*;
import com.example.customer.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import com.google.gson.*;
public class UpdateCustomer extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
//first we validate the user, if it invalid user then we redirect it to login window
String authorizationHeader = request.getHeader("Authorization");
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
CustomerDTO customerDTO=gson.fromJson(jsonString,CustomerDTO.class);
CustomerDAO customer=new CustomerDAO();
try
{
if(customer.customerIdExists(customerDTO.getCustomerId())==false)
{
jsonString=gson.toJson("Invalid customer id");
printWriter.print(jsonString);
printWriter.flush();
return;
}
}catch(DAOException daoException)
{
jsonString=gson.toJson("Invalid customer id");
printWriter.print(jsonString);
printWriter.flush();
return;
}
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
CustomerDTO c;
boolean emailExists=false;
//checking for email id existence,if it exists and customer id is different then we return it
try
{
c=customer.getByEmail(customerDTO.getEmail());
if(c!=null && c.getCustomerId().equals(customerDTO.getCustomerId())==false) emailExists=true;
}catch(DAOException daoException)
{
jsonString=gson.toJson("Invalid email id");
printWriter.print(jsonString);
printWriter.flush();
return;
}
if(emailExists==true)
{
jsonString=gson.toJson("Email id exists");
printWriter.print(jsonString);
printWriter.flush();
return;
}
//updating the customer in database
customer.updateCustomer(customerDTO);
jsonString=gson.toJson("Customer Updated");
printWriter.print(jsonString);
printWriter.flush();
return;
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
}