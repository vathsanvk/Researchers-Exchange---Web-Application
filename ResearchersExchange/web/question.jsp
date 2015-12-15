

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


<section class="question_section">
    <h3><span id="studies">Question</span></h3>


    <img src="<c:out value='${study.getImageURL()}' />" id="question_page_image" alt="Tree"/>


    <div id="question_select"><p><c:out value="${study.description}" /></p>

    
    </div>


    <div id="question_submit_div"> 
        <form action="studyController" method="post">

            <input type="hidden" name="action" value="answer" />
            <input type="hidden" name="studyCode" value="<c:out value='${study.code}' />" />
           
            <select id="question_ans_select" name="choice">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
            </select>
            <button type="submit" id="question_submit">Submit</button>    
        </form>
    </div>  
</section>





<%@ include file="footer.jsp" %>