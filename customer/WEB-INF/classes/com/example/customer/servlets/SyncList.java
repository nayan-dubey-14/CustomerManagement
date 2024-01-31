package com.example.customer.servlets;
import com.example.customer.dl.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.util.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
public class SyncList extends HttpServlet 
{
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
try 
{
//this servlet will place call to remote api and sync the records
//getting the remote token
String authToken=authenticate();
Gson gson=new Gson();
TokenDTO tokenDTO=gson.fromJson(authToken,TokenDTO.class);
//getting the customerList i.e. coming from remote api
String customerListResponse=getCustomerList(tokenDTO.getAccess_Token());
Type listType = new TypeToken<List<CustomerTmpDTO>>(){}.getType();
List<CustomerTmpDTO> customerTmpList=gson.fromJson(customerListResponse,listType);
//now iterating over list and add/update records to db
//comparing records over email because it is unique for each records
//this uniqueness is taken is by me ,we can consider fields according to us
CustomerDAO customerDAO=new CustomerDAO();
customerTmpList.forEach((customer)->{
try
{
CustomerDTO customerDTO=customerDAO.getByEmail(customer.getEmail());
if(customerDTO==null)
{
customerDTO=new CustomerDTO();
customerDTO.setCustomerId(customer.getUuid());
customerDTO.setFirstName(customer.getFirst_Name());
customerDTO.setLastName(customer.getLast_Name());
customerDTO.setStreet(customer.getStreet());
customerDTO.setAddress(customer.getAddress());
customerDTO.setCity(customer.getCity());
customerDTO.setState(customer.getState());
customerDTO.setEmail(customer.getEmail());
customerDTO.setPhoneNumber(customer.getPhone());
customerDAO.addCustomer(customerDTO);
}
else
{
customerDTO.setCustomerId(customer.getUuid());
customerDTO.setFirstName(customer.getFirst_Name());
customerDTO.setLastName(customer.getLast_Name());
customerDTO.setStreet(customer.getStreet());
customerDTO.setAddress(customer.getAddress());
customerDTO.setCity(customer.getCity());
customerDTO.setState(customer.getState());
customerDTO.setEmail(customer.getEmail());
customerDTO.setPhoneNumber(customer.getPhone());
customerDAO.updateCustomer(customerDTO);
}
}catch(DAOException daoException)
{
}
});
RequestDispatcher requestDispatcher=request.getRequestDispatcher("index.jsp");
requestDispatcher.forward(request,response);
}catch(Exception e)
{
e.printStackTrace();
response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
response.getWriter().write("Error: " +e.getMessage());
}
}
//this method place call to remote api and receive the token
private String authenticate() throws IOException 
{
String authUrl="https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
URL url=new URL(authUrl);
HttpURLConnection connection=(HttpURLConnection) url.openConnection();
connection.setRequestMethod("POST");
connection.setRequestProperty("Content-Type", "application/json");
connection.setDoOutput(true);
String requestBody="{\"login_id\" : \"test@sunbasedata.com\", \"password\" :\"Test@123\" }";
try(OutputStream os=connection.getOutputStream()) 
{
byte[] input = requestBody.getBytes("utf-8");
os.write(input, 0, input.length);
}
try(BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) 
{
StringBuilder response=new StringBuilder();
String responseLine=null;
while((responseLine=br.readLine())!=null) {
response.append(responseLine.trim());
}
return response.toString();
}
}
//this method call remote api and fetch the records
private String getCustomerList(String authToken) throws IOException 
{
String customerListUrl="https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
URL url=new URL(customerListUrl);
HttpURLConnection connection=(HttpURLConnection) url.openConnection();
connection.setRequestMethod("GET");
connection.setRequestProperty("Authorization", "Bearer " +authToken);
try(BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) 
{
StringBuilder response=new StringBuilder();
String responseLine=null;
while((responseLine=br.readLine())!=null) 
{
response.append(responseLine.trim());
}
return response.toString();
}
}
}