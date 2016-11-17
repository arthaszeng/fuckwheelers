<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" scope="request" value="Survey Report"/>

<%@ include file="../header.jsp" %>

<div id="surveyReport">

    <div class="nps-summary">
        <div class="net-promoter-score">Net Promoter Score is
            <span id="npsScore">
                <fmt:formatNumber type="number" pattern="###.##" value="${npsReport.npsScore}"/>
            </span>
        </div>

        <div class="total-response">Total Responses:
            <span id="totalResponse">${npsReport.total}</span>
        </div>
    </div>

    <div class="nps-percentage-visual">
        <%--commenting out white space otherwise css breaks http://stackoverflow.com/questions/19038799/why-is-there-an-unexplainable-gap-between-these-div-elements --%>
        <div id="detractorBlock" class="detractor visual-block"
             style="width: ${npsReport.detractorsPercentage - 0.5}%"></div><!--
        --><div id="passiveBlock" class="passive visual-block"
                style="width: ${npsReport.passivesPercentage - 0.5}%"></div><!--
        --><div id="promoterBlock" class="promoter visual-block"
             style="width: ${npsReport.promotersPercentage - 0.5}%"></div>
    </div>

    <div class="nps-percentage-breakdown">
        <div class="nps-example">
            <div class="detractor square"></div>
            <div id="detractorPercentage">
                <fmt:formatNumber type="number" pattern="###.##" value="${npsReport.detractorsPercentage}"/>% Detractors
            </div>
        </div>

        <div class="nps-example">
            <div class="passive square"></div>
            <div id="passivePercentage">
                <fmt:formatNumber type="number" pattern="###.##" value="${npsReport.passivesPercentage}"/>% Passives
            </div>
        </div>

        <div class="nps-example">
            <div class="promoter square"></div>
            <div id="promoterPercentage">
                <fmt:formatNumber type="number" pattern="###.##" value="${npsReport.promotersPercentage}"/>% Promoters
            </div>
        </div>

    </div>

</div>

<div class="survey-comments" id="surveyComments">
    <div class="comments-wrapper">
        <div class="comments-block detractor"></div>
        <ul id="detractorComments">
            <c:forEach var="comment" items="${surveyComments.detractorComments}">
                <li>${comment}</li>
            </c:forEach>
        </ul>
    </div>

    <div class="comments-wrapper">
        <div class="comments-block passive"></div>
        <ul id="passiveComments">
            <c:forEach var="comment" items="${surveyComments.passiveComments}">
                <li>${comment}</li>
            </c:forEach>
        </ul>
    </div>

    <div class="comments-wrapper">
        <div class="comments-block promoter"></div>
        <ul id="promoterComments">
            <c:forEach var="comment" items="${surveyComments.promoterComments}">
                <li>${comment}</li>
            </c:forEach>
        </ul>
    </div>
</div>

<%@ include file="../footer.jsp" %>
