package com.example.customer.dl;
import java.util.*;
import java.sql.*;
public class CustomerDAO
{
public List<CustomerDTO> getAll() throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select * from customer");
ResultSet resultSet=preparedStatement.executeQuery();
List<CustomerDTO> customerList=new ArrayList<>();
CustomerDTO customerDTO;
while(resultSet.next())
{
customerDTO=new CustomerDTO();
customerDTO.setCustomerId(resultSet.getString("customer_id"));
customerDTO.setFirstName(resultSet.getString("first_name"));
customerDTO.setLastName(resultSet.getString("last_name"));
customerDTO.setStreet(resultSet.getString("street"));
customerDTO.setAddress(resultSet.getString("address"));
customerDTO.setCity(resultSet.getString("city"));
customerDTO.setState(resultSet.getString("state"));
customerDTO.setEmail(resultSet.getString("email"));
customerDTO.setPhoneNumber(resultSet.getString("phone_number"));
customerList.add(customerDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
return customerList;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void addCustomer(CustomerDTO customerDTO) throws DAOException
{
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select first_name from customer where customer.email=?");
preparedStatement.setString(1,customerDTO.getEmail());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Email exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into customer(customer_id,first_name,last_name,street,address,city,state,email,phone_number) values(?,?,?,?,?,?,?,?,?)");
preparedStatement.setString(1,customerDTO.getCustomerId());
preparedStatement.setString(2,customerDTO.getFirstName());
preparedStatement.setString(3,customerDTO.getLastName());
preparedStatement.setString(4,customerDTO.getStreet());
preparedStatement.setString(5,customerDTO.getAddress());
preparedStatement.setString(6,customerDTO.getCity());
preparedStatement.setString(7,customerDTO.getState());
preparedStatement.setString(8,customerDTO.getEmail());
preparedStatement.setString(9,customerDTO.getPhoneNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void updateCustomer(CustomerDTO customerDTO) throws DAOException
{
try
{
String customerId=customerDTO.getCustomerId();
if(customerId==null) throw new DAOException("Invalid customer id");
customerId=customerId.trim();
if(customerId.length()==0) throw new DAOException("Invalid customer id");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select first_name from customer where customer.customer_id=?");
preparedStatement.setString(1,customerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid customer id");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select first_name from customer where customer.email=? && customer.customer_id!=?");
preparedStatement.setString(1,customerDTO.getEmail());
preparedStatement.setString(2,customerId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Email exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update customer set customer.first_name=?,customer.last_name=?,customer.street=?,customer.address=?,customer.city=?,customer.state=?,customer.email=?,customer.phone_number=? where customer.customer_id=?");
preparedStatement.setString(1,customerDTO.getFirstName());
preparedStatement.setString(2,customerDTO.getLastName());
preparedStatement.setString(3,customerDTO.getStreet());
preparedStatement.setString(4,customerDTO.getAddress());
preparedStatement.setString(5,customerDTO.getCity());
preparedStatement.setString(6,customerDTO.getState());
preparedStatement.setString(7,customerDTO.getEmail());
preparedStatement.setString(8,customerDTO.getPhoneNumber());
preparedStatement.setString(9,customerDTO.getCustomerId());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void deleteCustomer(String customerId) throws DAOException
{
try
{
if(customerId==null) throw new DAOException("Invalid customer id");
customerId=customerId.trim();
if(customerId.length()==0) throw new DAOException("Invalid customer id");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select first_name from customer where customer.customer_id=?");
preparedStatement.setString(1,customerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid customer id");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from customer where customer.customer_id=?");
preparedStatement.setString(1,customerId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public CustomerDTO getByCustomerId(String customerId) throws DAOException
{
try
{
if(customerId==null) throw new DAOException("Invalid customer id");
customerId=customerId.trim();
if(customerId.length()==0) throw new DAOException("Invalid customer id");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select * from customer where customer.customer_id=?");
preparedStatement.setString(1,customerId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid customer id");
}
CustomerDTO customerDTO=new CustomerDTO();
customerDTO.setCustomerId(resultSet.getString("customer_id"));
customerDTO.setFirstName(resultSet.getString("first_name"));
customerDTO.setLastName(resultSet.getString("Last_name"));
customerDTO.setStreet(resultSet.getString("street"));
customerDTO.setAddress(resultSet.getString("address"));
customerDTO.setCity(resultSet.getString("city"));
customerDTO.setState(resultSet.getString("state"));
customerDTO.setEmail(resultSet.getString("email"));
customerDTO.setPhoneNumber(resultSet.getString("phone_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return customerDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public CustomerDTO getByEmail(String email) throws DAOException
{
try
{
if(email==null) throw new DAOException("Invalid email");
email=email.trim();
if(email.length()==0) throw new DAOException("Invalid email");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select * from customer where customer.email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return null;
}
CustomerDTO customerDTO=new CustomerDTO();
customerDTO.setCustomerId(resultSet.getString("customer_id"));
customerDTO.setFirstName(resultSet.getString("first_name"));
customerDTO.setLastName(resultSet.getString("Last_name"));
customerDTO.setStreet(resultSet.getString("street"));
customerDTO.setAddress(resultSet.getString("address"));
customerDTO.setCity(resultSet.getString("city"));
customerDTO.setState(resultSet.getString("state"));
customerDTO.setEmail(resultSet.getString("email"));
customerDTO.setPhoneNumber(resultSet.getString("phone_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return customerDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public boolean emailExists(String email) throws DAOException
{
try
{
if(email==null) throw new DAOException("Length of email is zero");
email=email.trim();
if(email.length()==0) throw new DAOException("Length of email is zero");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select first_name from customer where customer.email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
boolean flag=false;
if(resultSet.next()==true) flag=true;
resultSet.close();
preparedStatement.close();
connection.close();
return flag;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public boolean customerIdExists(String customerId) throws DAOException
{
try
{
if(customerId==null) throw new DAOException("Length of customerId is zero");
customerId=customerId.trim();
if(customerId.length()==0) throw new DAOException("Length of customerId is zero");
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select first_name from customer where customer.customer_id=?");
preparedStatement.setString(1,customerId);
ResultSet resultSet=preparedStatement.executeQuery();
boolean flag=false;
if(resultSet.next()==true) flag=true;
resultSet.close();
preparedStatement.close();
connection.close();
return flag;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}