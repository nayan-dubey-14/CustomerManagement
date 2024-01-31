package com.example.customer.dl;
import java.lang.*;
public class CustomerDTO implements java.io.Serializable,Comparable<CustomerDTO>
{
private String customerId;
private String firstName;
private String lastName;
private String street;
private String address;
private String city;
private String state;
private String email;
private String phoneNumber;
public CustomerDTO()
{
this.customerId="";
this.firstName="";
this.lastName="";
this.street="";
this.address="";
this.city="";
this.state="";
this.email="";
this.phoneNumber="";
}
public void setCustomerId(String customerId)
{
this.customerId=customerId;
}
public String getCustomerId()
{
return this.customerId;
}
public void setFirstName(String firstName)
{
this.firstName=firstName;
}
public String getFirstName()
{
return this.firstName;
}
public void setLastName(String lastName)
{
this.lastName=lastName;
}
public String getLastName()
{
return this.lastName;
}
public void setStreet(String street)
{
this.street=street;
}
public String getStreet()
{
return this.street;
}
public void setAddress(String address)
{
this.address=address;
}
public String getAddress()
{
return this.address;
}
public void setCity(String city)
{
this.city=city;
}
public String getCity()
{
return this.city;
}
public void setState(String state)
{
this.state=state;
}
public String getState()
{
return this.state;
}
public void setEmail(String email)
{
this.email=email;
}
public String getEmail()
{
return this.email;
}
public void setPhoneNumber(String phoneNumber)
{
this.phoneNumber=phoneNumber;
}
public String getPhoneNumber()
{
return this.phoneNumber;
}
public boolean equals(Object tmp)
{
if(!(tmp instanceof CustomerDTO)) return false;
CustomerDTO c=(CustomerDTO)tmp;
return this.customerId.equals(c.getCustomerId());
}
public int hashCode()
{
return this.customerId.hashCode();
}
public int compareTo(CustomerDTO c)
{
return this.customerId.compareTo(c.getCustomerId());
}
}