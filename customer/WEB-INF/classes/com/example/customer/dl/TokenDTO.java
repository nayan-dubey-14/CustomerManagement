package com.example.customer.dl;
import java.lang.*;
public class TokenDTO implements java.io.Serializable
{
private String access_token;
public void setAccess_Token(String access_token)
{
this.access_token=access_token;
}
public String getAccess_Token()
{
return this.access_token;
}
}