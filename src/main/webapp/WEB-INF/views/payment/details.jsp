<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" scope="request" value="Pay by Credit Card"/>

<%@ include file="../header.jsp" %>

<div class="page-action">
    Order Summary
</div>
<table>
    <thead>
    <tr>
        <th class="name-th">Item Name</th>
        <th class="type-th">Type</th>
        <th class="price-th">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}" varStatus="row">
        <tr>
            <td class="name-column">${item.name}</td>
            <td>${item.type}</td>
            <td id="itemPrice">${item.price}</td>
        </tr>
    </c:forEach>
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





<div class="page-action">
    Pay by Credit Card
</div>

<br>
<c:if test="${not empty validationMessage.errors}">
    <div id="resultsMessage" class="page-action error">
        There some were errors occured.
        <c:if test="${not empty validationMessage.errors['itemError']}">
            ${validationMessage.errors['itemError']}
        </c:if>
    </div>
</c:if>

<form id="payment_form" action="/payment/checkout" method="post">
    <div class="paymentFields" id="credit_card_type">
        <label for="fld_card_type">Card Type</label>
        <select id="fld_card_type" name="type">
            <option value="">Please choose</option>
            <option value="VISA">Visa</option>
            <option value="MASTERCARD">MasterCard</option>
            <option value="DISCOVER">Discover</option>
            <option value="AMEX">AMEX</option>
        </select>
    </div>
    <div id="credit_card_number_field" class="paymentFields">
        <label for="fld_credit_card_number">Card Number</label>
        <div class="controls">
            <input type="text" name="cardNumber" id="fld_credit_card_number" maxlength="16">
            <c:if test="${not empty validationMessage.errors['creditCard']}">
                <span class="text-error">${validationMessage.errors["creditCard"]}</span>
            </c:if>
        </div>
    </div>
    <div id="expiry_field" class="paymentFields">
        <label for="fld_expiry_month">Expiry Date</label>
        <div class="controls">
            <input type="text" name="expiryMonth" id="fld_expiry_month" placeholder="MM" maxlength="2"> /
            <input type="text" name="expiryYear" id="fld_expiry_year" placeholder="YYYY" maxlength="4">
            <c:if test="${not empty validationMessage.errors['expiry']}">
                <span class="text-error">${validationMessage.errors["expiry"]}</span>
            </c:if>
        </div>
    </div>
    <div id="csc_field" class="paymentFields">
        <label for="fld_csc">Security Code</label>
        <div class="controls">
            <input type="password" name="csc" id="fld_csc" placeholder="CVV" maxlength="4">
            <c:if test="${not empty validationMessage.errors['csc']}">
                <span class="text-error">${validationMessage.errors["csc"]}</span>
            </c:if>
        </div>
    </div>


    <div class="submit_button">
        <div class="controls">
            <button type="submit" id="payNow" name="payNow">Pay now</button>
        </div>
    </div>
</form>

<%--<span id="total-price-value">${totalPrice}</span>--%>

<%@ include file="../footer.jsp" %>
<script type="text/javascript" src="<c:url value='/scripts/js/payment_validator.js' />"></script>