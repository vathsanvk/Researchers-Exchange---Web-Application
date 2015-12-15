<%-- 
    Document   : main
    Created on : Sep 19, 2015, 9:17:56 PM
    Author     : Srivathsan
--%>

<%@ include file="header.jsp" %>



<h3 id="page_name">My Studies</h3>

<h3 id="add_new_study"><a href="studyController?action=new" >Add a new study</a></h3>

<a href="userController?action=main" id="back_to_page">&laquo;Back to the Main Page</a>

<section id="studies_section">


    <table id="my_studies_table" >
        <tr>
            <th>Study Name</th>
            <th>Requested Participants</th>		
            <th>Participations</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <c:forEach var="item" items="${myStudies}" >
            <tr>
                <td><c:out value="${item.name}" /></td>
                <td><c:out value="${item.requestedParticipants}" /></td>
                <td><c:out value="${item.numOfParticipants }" /></td>
                <c:if test="${item.status == 'open'}">
                <td><form action="studyController" method="post">
                        <input type="hidden" name="action" value="start" />
                        <input type="hidden" name="studyCode" value="<c:out value='${item.code}' />" />
                        <input type="hidden" name="status" value="closed" />
                        <button type="submit">Stop</button></form></td>
                        
                </c:if>
                <c:if test="${item.status == 'closed'}">
                <td><form action="studyController" method="post">
                        <input type="hidden" name="action" value="start" />
                        <input type="hidden" name="studyCode" value="<c:out value='${item.code}' />" />
                        <input type="hidden" name="status" value="open" />
                        <button type="submit">Start</button></form></td>
                        
                </c:if>
                <td><form action="studyController" method="post">
                        <input type="hidden" name="action" value="edit" />
                        <input type="hidden" name="studyCode" value="<c:out value='${item.code}' />" />
                        <button type="submit">Edit</button></form></td>

            </tr>
        </c:forEach>
       
            

    </table>

</section>






<%@ include file="footer.jsp" %>