<!DOCTYPE HTML>
<head lang='en'>
<meta charset='utf-8'>
<title>Customer</title>
<link rel='stylesheet' type='text/css' href='/customer/css/loginStyles.css'>
<script>
function validateForm(form)
{
var username=form.username.value.trim();
var focusFlag;
var usernameErrorSection=document.getElementById('usernameErrorSection');
usernameErrorSection.innerHTML='';
var result=true;
if(username.length==0)
{
if(result==true) result=false;
if(focusFlag==null) focusFlag=form.username;
usernameErrorSection.innerHTML='required';
}
var password=form.password.value.trim();
var passwordErrorSection=document.getElementById('passwordErrorSection');
passwordErrorSection.innerHTML='';
if(password.length==0)
{
if(result==true) result=false;
if(focusFlag==null) focusFlag=form.password;
passwordErrorSection.innerHTML='required';
}
if(focusFlag!=null) focusFlag.focus();
return result;
}
function doLogin()
{
var errorSpan=document.getElementById('error');
errorSpan.innerHTML='';
var tmp=validateForm(document.getElementById('detailsForm'));
if(tmp==false) return;
var username=document.getElementById('username').value;
var password=document.getElementById('password').value;
var administrator={
"username":username,
"password":password
};
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var response=this.responseText;
if(response=="Invalid username/password") errorSpan.innerHTML=response;
else
{
localStorage.setItem("jwtToken",response);
window.location="index.jsp";
}
}
else
{
alert('Some problem has occured');
}
}
};
xmlHttpRequest.open('POST','login',true);
xmlHttpRequest.setRequestHeader("Content-Type","application/json");
xmlHttpRequest.send(JSON.stringify(administrator));
}
</script>
</head>
<body>
<div class='down-container'>
<center>
<h1><b>Login</b></h1>
<form id='detailsForm'>
<table>
<tr><span class='error' id='error'></span><br><tr>
<tr><td>Username:&nbsp;<input type='text' id='username' name='username' size=20 maxlength=15></td>
<td><span id='usernameErrorSection' class='error'></span><br><br></td></tr>
<tr><td>Password:&nbsp;<input type='password' id='password' name='password' size=20 maxlength=15></td>
<td><span id='passwordErrorSection' class='error'></span><br><br></td></tr>
</table><br>
<button type='button' onclick='doLogin()'>Login</button>
</form>
</center>
</div>
</body>
</html>