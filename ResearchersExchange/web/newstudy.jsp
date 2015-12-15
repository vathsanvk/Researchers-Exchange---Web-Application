<%-- 
    Document   : main
    Created on : Sep 19, 2015, 9:17:56 PM
    Author     : Srivathsan
--%>

<%@ include file="header.jsp" %>



<h3 id="page_name">Adding a study</h3>



<a href="userController?action=main" id="back_to_page">&laquo;Back to the Main Page</a>

<section id="newstudy_form">
    <form action="studyController" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="add" />
        <label>Study Name *</label>
        <input type="text" name="study_name" required /><br>
        <label>Question Text *</label>
        <input type="text" name="question_text" required/><br>
        <label>Image *</label>
        <input type="file" name="image_file" accept="image/*" required/>
        <br>
        <br>
        <label># Participants *</label>  
        <input type="number" min = "0" name="participants" required/><br>
        <label>Description *</label>  
        <textarea name="description" required></textarea><br>
        
        <button type="submit"  id="submit_button">Submit</button>

    </form>

</section>






<%@ include file="footer.jsp" %>