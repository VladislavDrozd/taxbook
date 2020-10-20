<html>
<body>
<h2>Hello World</h2>

<h2>LOGIN</h2>
<form action="login?action=login" method="post">
    Login name (email): <input name="name" type="text" /><br>
    Password: <input name="password" type="text"/><br>
    <input type="submit" value="Login" />
</form>

<h2>REGISTER</h2>
<form action="login?action=register" method="post">
    Name: <input name="name" type="text" /><br>
    Email: <input name="email" type="text"/><br>
    Phone: <input name="phone" type="text"/><br>
    TaxGroup: <input name="taxGroup" type="text" placeholder="1, 2, 3"/><br>
    Password: <input name="password" type="text"/><br>
    <input type="submit" value="Register" />
</form>

<h2>LOGOUT</h2>
<form action="login?action=logout" method="post">
    <input type="submit" value="Logout" />
</form>

</body>
</html>
