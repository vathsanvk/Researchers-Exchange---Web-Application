<%@include file="header.jsp" %>


<section id="login_form">

    <form action="userController" method="post">
        <input type="hidden" name="action" value="login">
        <label >Email Address *</label>
        <input type="email" name="email" required/> <br><br>
        <label >Password *</label>
        <input type="password" name="password" required/><br>
        <label>&nbsp;</label>
        <input type="submit" value="Log in" id="login_button" >
        <br>

    </form>

    <a href="userController?action=signup" id="sign_up_link">Sign up for a new account</a>
    <br>
    <br>
     <a href="userController?action=forgotp" id="sign_up_link">Forgot Password?</a>
    
    <p>${msg}</p>

</section>
<%@include file="footer.jsp" %>