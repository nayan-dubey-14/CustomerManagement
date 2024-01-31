package com.example.customer.dl;
import java.sql.*;
public class AdministratorDAO
{
public AdministratorDTO getByUsername(String username) throws DAOException
{
try
{
if(username==null) 
{
throw new DAOException("Invalid username");
}
Class.forName("com.mysql.cj.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/customerdb","customer","customer");
PreparedStatement preparedStatement=connection.prepareStatement("select * from administrator where uname=?");
preparedStatement.setString(1,username);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid username");
}
String userName=resultSet.getString("uname");
if(userName.equals(username)==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid username");
}
AdministratorDTO administratorDTO=new AdministratorDTO();
administratorDTO.setUsername(userName);
administratorDTO.setPassword(resultSet.getString("pwd"));
resultSet.close();
preparedStatement.close();
connection.close();
return administratorDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}