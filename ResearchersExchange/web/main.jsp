<%-- 
    Document   : main
    Created on : Sep 19, 2015, 9:17:56 PM
    Author     : Srivathsan
--%>

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


<section class="main">
    <h3>How it Works</h3>

    <p>This site was built to help researchers conduct their user studies</p>

    <p>1 participation = 1 coin</p>

    <p><b>To participate,</b> go to "Participate" section and choose a study to complete</p>

    <p><b>To get participants,</b> submit your study here to start getting Participations. Inorder to do so, you must have enough coins in Your account</p>

</section>






<%@ include file="footer.jsp" %>