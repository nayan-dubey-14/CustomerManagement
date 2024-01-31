package com.example.customer.dl;
import java.util.*;
import java.lang.*;
public class ResponseDTO implements java.io.Serializable
{
private List<CustomerDTO> customerList;
private int page;
private int totalPages;
public ResponseDTO(List<CustomerDTO> customerList,int page,int totalPages)
{
this.customerList=customerList;
this.page=page;
this.totalPages=totalPages;
}
}