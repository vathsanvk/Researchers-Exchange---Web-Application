<%@include file="header.jsp" %>
<section id="signup_form">
    <section>
        <form action="userController" method="post">
            <input type="hidden" name="action" value="create" />
            <label>Name *</label>
            <input type="text" name="name" required/> <br><br>
            <label>Email *</label>
            <input type="email" name="email" required/> <br><br>
            <label>Password *</label>
            <input type="password" name="password" required/> <br><br>
            <label>Confirm Password *</label>
            <input type="password" name="confirm_password" required /> <br>

            <input type="submit" value="Create Account" id="signup_button">
            <br>

        </form>
        
        <br>
        <p>${msg}</p>

    </section>
</section>
<%@include file="footer.jsp" %>