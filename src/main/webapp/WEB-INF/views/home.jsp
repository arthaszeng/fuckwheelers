<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Home"/>
<%@ include file="header.jsp" %>
<%@ taglib prefix="togglz" uri="http://togglz.org/taglib" %>

<togglz:feature name="SHOPPING_CART">
    <h1 class="added-to-cart">${message}</h1>
</togglz:feature>


<table class="home-table">
    <thead>
    <tr>
        <th class="name-th">Name</th>
        <th class="description-th">Description</th>
        <th class="type-th">Type</th>
        <th class="quantity-th">Quantity</th>
        <th class="price-th">Price</th>
        <th class="button-th"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}" varStatus="row">
        <tr>
            <td><c:out value="${item.name}"/></td>
            <td><c:out value="${item.description}"/></td>
            <td><c:out value="${item.type}"/></td>
            <td><c:out value="${item.quantity}"/></td>
            <td id="itemPrice"><c:out value="${item.price}"/></td>
            <togglz:feature name="RESERVE_ITEM">
                <td>
                    <form:form action="reserve" method="post" modelAttribute="item">
                        <form:hidden path="itemId" value="${item.itemId}"/>
                        <button class="reserve-button" type="submit" name="reserve" id="reserve" value="Reserve Item">
                            Reserve Item
                        </button>
                    </form:form>
                </td>
            </togglz:feature>

            <togglz:feature name="SHOPPING_CART">
                <td>
                    <form:form action="/" method="post" modelAttribute="item">
                        <form:hidden path="itemId" value="${item.itemId}"/>
                        <button class="add-to-cart-button" type="submit" name="Add to cart" id="add-to-cart" value="Add to Cart">
                            Add to Cart
                        </button>
                    </form:form>
                </td>
            </togglz:feature>
        </tr>
    </c:forEach>

    </tbody>
</table>

<%@ include file="footer.jsp" %>