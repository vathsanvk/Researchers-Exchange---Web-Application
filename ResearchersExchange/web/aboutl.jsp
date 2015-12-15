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
    <h3>About us</h3>

    <p>Researchers Exchange Participations is a platform for researchers 
        to exchange participations</p>

    <p>The aim of this platform is to encourage researchers participate in each others
        user studies. Moreover, recruiting serious participants has been always a burden on
        a researcher's shoulder, thus, this platform gives researchers the opportunity
        to do that effectively and in a suitable time.</p>




</section>






<%@ include file="footer.jsp" %>