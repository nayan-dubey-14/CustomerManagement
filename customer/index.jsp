<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Customer Table</title>
<style>
table{
width:100%;
border-collapse:collapse;
margin-top:20px;
}
th,td{
border:1px solid #dddddd;
text-align:left;
padding: 8px;
}
th{
background-color: #f2f2f2;
}
</style>
</head>
<body>
<h1 style='text-align:center'>Customer Table</h1>
<div>
<form>
<select id="selectField" name="selectField">
<option value="-1">Search By</option>
<option value="firstName">First Name</option>
<option value="lastName">Last Name</option>
<option value="street">Street</option>
<option value="address">Address</option>
<option value="city">City</option>
<option value="state">State</option>
<option value="email">Email</option>
<option value="phoneNumber">Phone Number</option>
</select>
<input type="text" id="searchText" name="searchText">
<button type="button" onclick='fetchCustomers()'>Search</button>
<a href='/customer/syncList'><button type='button' style='float:right'>Sync</button></a>
<a href='/customer/CustomerAddForm.jsp'><button type='button' style='float:right;margin-right:10px'>Add</button></a>
</form>
</div>
<table id="customerTable">
<thead>
<tr>
<th onclick="sortTable('firstName')">First Name</th>
<th onclick="sortTable('lastName')">Last Name</th>
<th onclick="sortTable('street')">Street</th>
<th onclick="sortTable('address')">Address</th>
<th onclick="sortTable('city')">City</th>
<th onclick="sortTable('state')">State</th>
<th onclick="sortTable('email')">Email</th>
<th onclick="sortTable('phoneNumber')">Phone No.</th>
<th >Delete</th>
<th >Edit</th>
</tr>
</thead>
<tbody id="tableBody"></tbody>
</table>
<div id="pagination"></div>
<script>
let currentPage=1;
const pageSize=2;
let searchText=document.getElementById('searchText');
searchText.addEventListener('input',function(event){
if(event.target.value.length==0) fetchCustomers();
});

function fetchCustomers(sortBy,sortOrder) 
{
const token = localStorage.getItem('jwtToken');
let searchField=document.getElementById("selectField").value;
let searchText=document.getElementById("searchText").value;
let url="/customer/getAllCustomer?page="+currentPage+"&pageSize="+pageSize;
if(sortBy && sortOrder) url+="&sortBy="+sortBy+"&sortOrder="+sortOrder;
if(searchField && searchText && searchField!="-1") url+="&searchField="+searchField+"&searchText="+searchText;
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
console.log(this.responseText);
var data=JSON.parse(this.responseText);
displayCustomers(data.customerList);
displayPagination(data);
}
else
{
window.location='LoginForm.jsp';
}
}
};
xmlHttpRequest.open('GET',url,true);
xmlHttpRequest.setRequestHeader("Authorization","Bearer "+token);
xmlHttpRequest.setRequestHeader("Content-Type","application/json");
xmlHttpRequest.send();
}

function displayCustomers(customers) {
const tableBody=document.getElementById('tableBody');
tableBody.innerHTML='';
customers.forEach(customer=>{
const row=document.createElement('tr');
const fieldsToDisplay=['firstName','lastName','street','address','city','state','email','phoneNumber','delete','edit'];
fieldsToDisplay.forEach(fieldName=>{
const cell=document.createElement('td');
if(fieldName=='delete')
{
cell.innerHTML="<a href='/customer/deleteCustomer?customerId="+customer.customerId+"'><img src='/customer/images/delete.png'></a>";
}
if(fieldName=='edit')
{
cell.innerHTML="<a href='/customer/CustomerUpdateForm.jsp?customerId="+customer.customerId+"'><img src='/customer/images/edit.png'></a>";
}
if(fieldName!='delete' && fieldName!='edit')
{
cell.innerHTML=customer[fieldName];
}  
row.appendChild(cell);
});
tableBody.appendChild(row);
});
}

function displayPagination(data)
{
const pagination=document.getElementById('pagination');
pagination.innerHTML='';
var totalPages=data.totalPages;
for(let i=1;i<=totalPages;i++)
{
const pageButton=document.createElement('button');
pageButton.textContent=i;
pageButton.addEventListener('click',()=>{
currentPage=i;
fetchCustomers();
});
pagination.appendChild(pageButton);
}
}

function sortTable(sortBy) 
{
let sortOrder;
const orderVariables={
firstName: 'firstNameOrder',
lastName: 'lastNameOrder',
street: 'streetOrder',
address: 'addressOrder',
city: 'cityOrder',
state: 'stateOrder',
email: 'emailOrder',
phoneNumber: 'phoneNumberOrder'
};
const orderVariable=orderVariables[sortBy];
if(orderVariable) 
{
if(window[orderVariable]==="asc") 
{
window[orderVariable]="desc";
} 
else 
{
window[orderVariable]="asc";
}
sortOrder=window[orderVariable];
}
fetchCustomers(sortBy, sortOrder);
}
fetchCustomers();
</script>
</body>
</html>