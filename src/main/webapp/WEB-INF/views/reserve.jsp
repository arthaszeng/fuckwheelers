<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Reserve Item"/>
<%@ include file="header.jsp" %>

<script type="text/javascript">
    $(function () {
        new Survey().showSurvey(new SurveyPopUp());
    })
</script>

<div class="page-action">Item reserved !</div>

<table class="table cart-table">
    <thead>
    <tr>
        <th class="name-th">Name</th>
        <th class="price-th">Price</th>
        <th class="description-th">Description</th>
        <th class="type-th">Type</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${item.name}</td>
        <td>${item.price}</td>
        <td>${item.description}</td>
        <td>${item.type}</td>
    </tr>
    </tbody>
</table>

<%@ include file="footer.jsp" %>