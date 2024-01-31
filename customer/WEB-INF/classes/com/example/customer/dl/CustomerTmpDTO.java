package com.example.customer.dl;
import java.lang.*;
public class CustomerTmpDTO implements java.io.Serializable
{
private String uuid;
private String first_name;
private String last_name;
private String street;
private String address;
private String city;
private String state;
private String email;
private String phone;
public CustomerTmpDTO()
{
this.uuid="";
this.first_name="";
this.last_name="";
this.street="";
this.address="";
this.city="";
this.state="";
this.email="";
this.phone="";
}
public void setUuid(String uuid)
{
this.uuid=uuid;
}
public String getUuid()
{
return this.uuid;
}
public void setFirst_Name(String first_name)
{
this.first_name=first_name;
}
public String getFirst_Name()
{
return this.first_name;
}
public void setLast_Name(String last_name)
{
this.last_name=last_name;
}
public String getLast_Name()
{
return this.last_name;
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
public void setPhone(String phone)
{
this.phone=phone;
}
public String getPhone()
{
return this.phone;
}
}