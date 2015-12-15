<%-- 
    Document   : main
    Created on : Sep 19, 2015, 9:17:56 PM
    Author     : Srivathsan
--%>

<%@ include file="header.jsp" %>



<h3 id="page_name">Editing a study</h3>



<a href="userController?action=main" id="back_to_page">&laquo;Back to the Main Page</a>

<section id="newstudy_form">
    <form action="studyController" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update" />  
        <input type="hidden" name="studyCode" value="<c:out value='${study.code}' />" />  
        <label>Study Name *</label>
        <input type="text" name="study_name" value="<c:out value='${study.name}' />" required /><br>
        <label>Question Text *</label>
        <input type="text" name="question_text" value="<c:out value='${study.question}' />" required/><br><br>

        <label>Image *</label>
        <div id="edit_study">

            <img src="<c:out value='${study.getImageURL()}' />" id="edit_study_image" alt="Edit"/>
            <input id="image_file" type="file" name="image_file" accept="image/*"  onchange="loadFile(event)" />
        </div>

        <br>
        <br>
        <label># Participants *</label>  
        <input type="number" min="0" name="participants" value="<c:out value='${study.requestedParticipants}' />" required/><br>
        <label>Description *</label>  
        <textarea name="description"  required><c:out value='${study.description}' /></textarea><br>
        <button type="submit"  id="submit_button">Update</button>

    </form>

</section>

<script>

    var loadFile = function (event) {
        var output = document.getElementById('edit_study_image');
        output.src = URL.createObjectURL(event.target.files[0]);
    };
</script>






<%@ include file="footer.jsp" %>