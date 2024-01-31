package com.example.customer.servlets;
import com.example.customer.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
public class GetCustomerById extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
String customerId=request.getParameter("customerId");
PrintWriter printWriter=response.getWriter();
response.setContentType("application/json");
Gson gson=new Gson();
String jsonString;
CustomerDAO customer=new CustomerDAO();
CustomerDTO customerDTO=null;
try
{
customerDTO=customer.getByCustomerId(customerId);
}catch(DAOException daoException)
{
jsonString=gson.toJson("Invalid");
printWriter.print(jsonString);
printWriter.flush();
return;
}
jsonString=gson.toJson(customerDTO);
printWriter.print(jsonString);
printWriter.flush();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
}