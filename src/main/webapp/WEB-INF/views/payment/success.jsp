<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" scope="request" value="Pay by Credit Card"/>

<%@ include file="../header.jsp" %>

<script type="text/javascript">
    $(function () {
        new Survey().showSurvey(new SurveyPopUp());
    })
</script>

<div class="page-action">
    Thank you for purchasing from Freewheelers!
</div>

<%@ include file="../footer.jsp" %>