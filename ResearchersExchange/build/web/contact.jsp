<%-- 
    Document   : main
    Created on : Sep 19, 2015, 9:17:56 PM
    Author     : Srivathsan
--%>

<%@ include file="header.jsp" %>



<h3 id="page_name">Contact</h3>



<a href="userController?action=main" id="back_to_page">&laquo;Back to the Main Page</a>

<section id="contact_form">
    <form action="studyController" method="post">
        <input type="hidden" name="action" value="confirmc" />
        <label>Name *</label>
        <input type="text" name="study_name" required /><br><br>
        <label>Email *</label>
        <input type="email" name="email" required/><br><br>
        <label>Message *</label>  
        <textarea name="message" required></textarea><br>
        <button type="submit"  id="submit_button">Submit</button>

    </form>

</section>






<%@ include file="footer.jsp" %>