//this servlet is responsible for fetching all records with pagination,searching,sorting
package com.example.customer.servlets;
import com.example.customer.dl.*;
import com.example.customer.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.Function;
import java.util.Comparator;
import java.io.*;
import java.math.*;
import com.google.gson.*;
public class GetAllCustomer extends HttpServlet 
{
//this comparator is responsible for sorting
private Comparator<CustomerDTO> getComparatorByField(String sortBy) 
{
switch(sortBy) 
{
case "firstName":
return Comparator.comparing(CustomerDTO::getFirstName);
case "lastName":
return Comparator.comparing(CustomerDTO::getLastName);
case "street":
return Comparator.comparing(CustomerDTO::getStreet);
case "address":
return Comparator.comparing(CustomerDTO::getAddress);
case "city":
return Comparator.comparing(CustomerDTO::getCity);
case "state":
return Comparator.comparing(CustomerDTO::getState);
case "email":
return Comparator.comparing(CustomerDTO::getEmail);
case "phoneNumber":
return Comparator.comparing(CustomerDTO::getPhoneNumber);
default:
return Comparator.comparing(CustomerDTO::getCustomerId);
}
}
//this method is used to search the text
private boolean isMatch(CustomerDTO customerDTO, String searchField,String searchText)
{
if(searchText==null || searchText.isEmpty()) 
{
return true; 
}
String searchLowerCase=searchText.toLowerCase();
if(searchField.equals("firstName")) 
{
return customerDTO.getFirstName().toLowerCase().contains(searchLowerCase);
}
if(searchField.equals("lastName")) 
{
return customerDTO.getLastName().toLowerCase().contains(searchLowerCase);
}
if(searchField.equals("street")) 
{
return customerDTO.getStreet().toLowerCase().contains(searchLowerCase);
}
if(searchField.equals("address")) 
{
return customerDTO.getAddress().toLowerCase().contains(searchLowerCase);
} 
if(searchField.equals("city")) 
{
return customerDTO.getCity().toLowerCase().contains(searchLowerCase);
}
if(searchField.equals("state")) 
{
return customerDTO.getState().toLowerCase().contains(searchLowerCase);
}
if(searchField.equals("email"))
{
return customerDTO.getEmail().toLowerCase().contains(searchLowerCase);
}
if(searchField.equals("phoneNumber")) 
{
return customerDTO.getPhoneNumber().toLowerCase().contains(searchLowerCase);
}
else
{
return false;
}
}

public void doGet(HttpServletRequest request,HttpServletResponse response)
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
//we fetch all the parameters that is coming along with request
PrintWriter printWriter=response.getWriter();
response.setContentType("application/json");
int page=Integer.parseInt(request.getParameter("page"));
int pageSize=Integer.parseInt(request.getParameter("pageSize"));
String sortBy=request.getParameter("sortBy");
String sortOrder=request.getParameter("sortOrder");
String searchField=request.getParameter("searchField");
String searchText=request.getParameter("searchText");
CustomerDAO customer=new CustomerDAO();
List<CustomerDTO> customerList=customer.getAll();

//if both factor of searching present then we search the records according to searchText from all records
if(searchField!=null && searchText!=null)
{
List<CustomerDTO> filteredList=customerList.stream().filter(cst -> isMatch(cst,searchField,searchText)).collect(Collectors.toList());
customerList=filteredList;
}
//here we do the pagination 
int totalCustomers=customerList.size();
int totalPages=(int)Math.ceil((double)totalCustomers/pageSize);
int startIdx=(page-1)*pageSize;
int endIdx=Math.min(startIdx+pageSize, totalCustomers);
List<CustomerDTO> paginatedCustomers=customerList.subList(startIdx, endIdx);
//if both factor of sorting present,then we sort according to sortBy field
if (sortBy!=null && sortOrder!=null) 
{
Collections.sort(paginatedCustomers,getComparatorByField(sortBy));
//sortOrder is equal to desc then we reverse the order
if ("desc".equalsIgnoreCase(sortOrder)) 
{
Collections.reverse(paginatedCustomers);
}
} 
ResponseDTO responseDTO=new ResponseDTO(paginatedCustomers,page,totalPages);
Gson gson=new Gson();
String jsonString=gson.toJson(responseDTO);
printWriter.print(jsonString);
printWriter.flush();
}catch(Exception exception)
{
try
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception  e)
{
}
}
}
}