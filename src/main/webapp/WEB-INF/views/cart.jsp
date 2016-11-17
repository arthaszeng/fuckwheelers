<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Shopping Cart"/>
<%@ include file="header.jsp" %>
<%@ taglib prefix="togglz" uri="http://togglz.org/taglib" %>


<table class="table cart-table">
    <thead>
    <tr>
        <th class="name">Item Name</th>
        <th class="description">Description</th>
        <th class="type">Type</th>
        <th class="price">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty items}">
        <div class="no-item">
            No items in cart
        </div>
    </c:if>

    <c:if test="${not empty items}">
        <c:forEach var="item" items="${items}" varStatus="row">
            <tr>
                <td class="name">${item.name}</td>
                <td class="description">${item.description}</td>
                <td class="type">${item.type}</td>
                <td class="price" id="itemPrice">${item.price}</td>
                <td>
                    <form:form action="cart" method="post">
                        <input type="hidden" value="${item.itemId}" name="itemId" id="itemId">
                        <button class="cancel-button" type="submit" name="cancel" id="cancel" value="Cancel Item">
                            Cancel Item
                        </button>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
    </c:if>

    </tbody>
</table>

<div id="orderPrice">
    <dl>
        <dt>Subtotal</dt>
        <dd id="sub-total-price-value">${subTotal}</dd>
        <c:if test="${not empty vat}">
            <dt id="vat_label">VAT (${vatPercentage}%)</dt>
            <dd>${vat}</dd>
        </c:if>
    </dl>
    <hr>
    <dl id="totalPrice">
        <dt>Total</dt>
        <dd id="total-price-value" name="totalPrice">${totalPrice}</dd>
    </dl>
</div>


<c:if test="${not empty missingCountryMessage}">
    <div id="missingCountryMessage">
            ${missingCountryMessage}
    </div>
</c:if>
<table id="checkOutTable">
    <tbody>
    <tr>
        <td>
            <a href="<c:url value='/' />">
                <button>Continue Shopping</button>
            </a>
        </td>
        <td>
            <togglz:feature name="CREDIT_CARD_PAYMENT">
                <c:if test="${not empty items}">
                    <form:form action="payment" method="get">
                        <button id="checkOut" type="submit" name="checkOut" value="Check Out">Check Out</button>
                    </form:form>

                </c:if>
            </togglz:feature>
        </td>
    </tr>
    </tbody>
</table>
<%@ include file="footer.jsp" %>
