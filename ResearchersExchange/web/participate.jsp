

<%@ include file="header.jsp" %>



<nav id="menu">
    <ul>
        <li>Coins (<span class="count"><c:out value="${theUser.coins}" /></span>) </li>
        <li>Participants (<span class="count"><c:out value="${theUser.participants}" /></span>) </li>
        <li>Participation (<span class="count"><c:out value="${theUser.participation}" /></span>) </li>
        <li><br></li>
        <li><a href="userController?action=home">Home</a></li>
        <li><a href="studyController?action=participate">Participate</a></li>
        <li><a href="studyController?action=studies">My Studies</a></li>
        <li><a href="studyController?action=recommend">Recommend</a></li>
        <li><a href="studyController?action=contact">Contact</a></li>
    </ul>


</nav>


<section class="participate">
    <h3><span id="studies">Studies</span></h3>

    <table id="studies_table" >
        <tr>
            <th>Study Name</th>
            <th>Image</th>		
            <th>Question</th>
            <th>Action</th>
        </tr>
        <c:forEach var="item" items="${openStudies}" >
            <tr>
                <td><c:out value="${item.name}" /></td>
             
                <td><img src="<c:out value='${item.getImageURL()}' />" alt="studyImage"></td>
                <td><c:out value="${item.question}" /></td>
                <td><form action="studyController" method="post">

                        <c:if test="${item.creatorEmail != theUser.email}">
                            <input type="hidden" name="studyCode" value="<c:out value='${item.code}' />" />
                            <input type="hidden" name="action" value="participate" />
                            <input type="submit" id="participate_button"
                                   value="Participate" /></form></td>
                        </c:if>
            </tr>
        </c:forEach>


    </table>

</section>




<%@ include file="footer.jsp" %>