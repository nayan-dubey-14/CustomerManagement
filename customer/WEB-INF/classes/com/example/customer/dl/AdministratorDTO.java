package com.example.customer.dl;
public class AdministratorDTO implements Comparable<AdministratorDTO>,java.io.Serializable
{
private String username;
private String password;
public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}
public boolean equals(Object other)
{
if(!(other instanceof AdministratorDTO)) return false;
AdministratorDTO adminDTO=(AdministratorDTO)other;
return this.username.equalsIgnoreCase(adminDTO.getUsername());
}
public int hashCode()
{
return this.username.hashCode();
}
public int compareTo(AdministratorDTO adminDTO)
{
return this.username.compareToIgnoreCase(adminDTO.getUsername());
}
}