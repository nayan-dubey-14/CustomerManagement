<!DOCTYPE HTML>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>Customer Application</title>
<link rel='stylesheet' type='text/css' href='/customer/css/styles.css'>
</head>
<body>
<script>
function validateForm(form)
{
var valid=true;
var firstFocus;
var firstName=form.firstName.value.trim();
var firstNameErrorSection=document.getElementById("firstNameErrorSection");
firstNameErrorSection.innerHTML='';
if(firstName.length==0)
{
firstNameErrorSection.innerHTML='required';
firstFocus=form.name;
valid=false;
}
var lastName=form.lastName.value.trim();
var lastNameErrorSection=document.getElementById("lastNameErrorSection");
lastNameErrorSection.innerHTML='';
if(lastName.length==0)
{
lastNameErrorSection.innerHTML='required';
if(firstFocus==null) firstFocus=form.lastName;
valid=false;
}
var street=form.street.value.trim();
var streetErrorSection=document.getElementById("streetErrorSection");
streetErrorSection.innerHTML="";
if(street.length==0) 
{
streetErrorSection.innerHTML="required";
if (firstFocus==null) firstFocus=form.street;
valid=false;
}
var address=form.address.value.trim();
var addressErrorSection=document.getElementById("addressErrorSection");
addressErrorSection.innerHTML="";
if(address.length==0) 
{
addressErrorSection.innerHTML="required";
if(firstFocus==null) firstFocus=form.address;
valid=false;
}
var city = form.city.value.trim();
var cityErrorSection = document.getElementById("cityErrorSection");
cityErrorSection.innerHTML="";
if(city.length==0) 
{
cityErrorSection.innerHTML="required";
if (firstFocus==null) firstFocus =form.city;
valid=false;
}
var state=form.state.value.trim();
var stateErrorSection=document.getElementById("stateErrorSection");
stateErrorSection.innerHTML="";
if (state.length==0) 
{
stateErrorSection.innerHTML = "required";
if(firstFocus==null) firstFocus=form.state;
valid=false;
}
var email=form.email.value.trim();
var emailErrorSection=document.getElementById("emailErrorSection");
emailErrorSection.innerHTML="";
if(email.length==0) 
{
emailErrorSection.innerHTML="required";
if (firstFocus==null) firstFocus=form.email;
valid=false;
}
var phoneNumber=form.phoneNumber.value.trim();
var phoneNumberErrorSection=document.getElementById( "phoneNumberErrorSection");
phoneNumberErrorSection.innerHTML="";
if(phoneNumber.length==0) 
{
phoneNumberErrorSection.innerHTML="required";
if (firstFocus==null) firstFocus=form.phoneNumber;
valid=false;
}
if(firstFocus!=null) firstFocus.focus;
return valid;
}
function cancelButton()
{
document.getElementById('cancelButton').submit();
}
function addCustomer()
{
var emailErrorSection=document.getElementById('emailErrorSection');
emailErrorSection.innerHTML="";
var tmp=validateForm(document.getElementById('detailsForm'));
if(tmp==false) return;
var firstName=document.getElementById('firstName').value;
var lastName=document.getElementById('lastName').value;
var street=document.getElementById('street').value;
var address=document.getElementById('address').value;
var city=document.getElementById('city').value;
var state=document.getElementById('state').value;
var email=document.getElementById('email').value;
var phoneNumber=document.getElementById('phoneNumber').value;
var customer={
"firstName":firstName,
"lastName":lastName,
"street":street,
"address":address,
"city":city,
"state":state,
"email":email,
"phoneNumber":phoneNumber
};
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var response=JSON.parse(this.responseText);
if(response=="Customer Added")
{
alert('Customer Added');
window.location='index.jsp';
}
else if(response=="Email Id exists")
{
emailErrorSection.innerHTML='Email exists';
}
}
else
{
alert('some error');
}
}
};
const token=localStorage.getItem('jwtToken');
xmlHttpRequest.open('POST','addCustomer',true);
xmlHttpRequest.setRequestHeader("Content-Type","application/json");
xmlHttpRequest.setRequestHeader("Authorization","Bearer "+token);
xmlHttpRequest.send(JSON.stringify(customer));
}
function checkValidateEntry()
{
const token=localStorage.getItem('jwtToken');
if(token==null || token.length==0) window.location="LoginForm.jsp";
}
window.addEventListener('load',checkValidateEntry);
</script>
<h1>Add Customer</h1>
<form  id='cancelButton' action='/customer/index.jsp'>
</form>
<form id='detailsForm'>
<table>
<tr>
<td>First Name</td>
<td>
<input type='text' id='firstName' name='firstName' size=51 maxlength=50>
<span id='firstNameErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>Last Name</td>
<td>
<input type='text' id='lastName' name='lastName' size=51 maxlength=50>
<span id='lastNameErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>Street</td>
<td>
<input type='text' id='street' name='street' size=51 maxlength=100>
<span id='streetErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>Address</td>
<td>
<input type='text' id='address' name='address' size=51 maxlength=100>
<span id='addressErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>City</td>
<td>
<input type='text' id='city' name='city' size=51 maxlength=50>
<span id='cityErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>State</td>
<td>
<input type='text' id='state' name='state' size=51 maxlength=50>
<span id='stateErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>Email</td>
<td>
<input type='email' id='email' name='email' size=51 maxlength=50>
<span id='emailErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td>Phone Number</td>
<td>
<input type='tel' id='phoneNumber' name='phoneNumber' size=16 maxlength=15>
<span id='phoneNumberErrorSection' style='color:red'></span><br>
</td>
</tr>
<tr>
<td><button type='button' onclick='addCustomer()'>Add</button>
<button type='button' onclick='cancelButton()'>Cancel</button></td>
</tr>
</table>
</form>
</body>
</html>