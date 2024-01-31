//this servlet is responsible for deleting a customer
package com.example.customer.servlets;
import com.example.customer.dl.*;
import com.example.customer.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.*;
import java.lang.*;
public class DeleteCustomer extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter printWriter=response.getWriter();
RequestDispatcher requestDispatcher=request.getRequestDispatcher("index.jsp");
String customerId=request.getParameter("customerId");
//checking customerId is vaild or not ,if it is invalid then we return 
CustomerDAO customer=new CustomerDAO();
try
{
if(customer.customerIdExists(customerId)==false)
{
requestDispatcher.forward(request,response);
}
}catch(DAOException daoException)
{
requestDispatcher.forward(request,response);
}
//deleting the customer from database
customer.deleteCustomer(customerId);
requestDispatcher.forward(request,response);
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
}