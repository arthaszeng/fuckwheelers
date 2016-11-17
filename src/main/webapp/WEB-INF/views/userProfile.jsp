<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="User Profile"/>
<%@ include file="header.jsp" %>
<%@ taglib prefix="togglz" uri="http://togglz.org/taglib" %>

<div class="page-action" id="detail-header">
    Your details
<togglz:feature name="EDIT_DETAIL">
    <button type="button" id="edit-detail">Edit</button>
    <button type="submit" id="save-detail" >Save</button>
    <button type="button" id="cancel-edit">Cancel</button>
</togglz:feature>

</div>

<div id="user-details">
    User Name : ${account.account_name}<br/>
    Email Address: ${account.email_address}<br/>
    Phone Number: ${account.phone_number} <br/>
    Street One: ${address.street_one}<br/>
    <c:if test="${not empty address.street_two}">
        Street Two: ${address.street_two}<br/>
    </c:if>
    City: ${address.city}<br/>
    <c:if test="${not empty address.state}">
        State: ${address.state}<br/>
    </c:if>
    Post Code : ${address.post_code}<br/>
    Country : ${country.name}<br/>

</div>

<form id="profile_form" action="/userProfile/update" method="post" hidden="hidden">
    <div id="phone_number_field">
        <label for="fld_phone_number">Phone Number<span class="attention">*</span></label>
        <div class="controls">
            <input type="text" id="fld_phone_number" placeholder="555-123456" value="${account.phone_number}"
                   name="phone_number">
            <span class="text-error">Please enter a phone number</span>
        </div>
    </div>

    <div id="country_field">
        <label for="fld_country" class="attention">Country</label>
        <div class="controls">
            <select id="fld_country" name="country_id" onchange="validateStateMandatory()">
                <c:forEach var="countryItem" items="${countryOptions}" varStatus="row">
                    <option value=<c:out value="${countryItem.country_id}"/>
                                    ${countryItem.country_id eq country.country_id ? 'selected' : ''}>
                        <c:out value="${countryItem.name}"/>
                    </option>
                </c:forEach>
            </select>
            <span class="text-error empty-error">Please choose a country</span>
        </div>
        <div id="country_not_supported_msg">
            If your country is not listed then we don't ship there. Please check back later.
        </div>
    </div>

    <div id="street_one_field">
        <label for="fld_street_one" class="attention">Street 1</label>
        <div class="controls">
            <br/>
            <input type="text" id="fld_street_one" placeholder="street line 1" name="street_one" value="${address.street_one}">
            <span class="text-error empty-error">Street name cannot be empty</span>
            <span class="text-error invalid-error">Please enter valid Street name</span>
        </div>
    </div>

    <div id="street_two_field">
        <label for="fld_street_two">Street 2</label>
        <div class="controls">
            <input type="text" id="fld_street_two" placeholder="street line 2" name="street_two" value="${address.street_two}">
            <span class="text-error invalid-error">Please enter valid Street name</span>
        </div>
    </div>
    <div id="city_field">
        <label for="fld_city" class="attention">City</label>
        <div class="controls">
            <input type="text" id="fld_city" placeholder="city name" name="city" value="${address.city}" >
            <span class="text-error empty-error">City cannot be empty</span>
            <span class="text-error invalid-error">Please enter valid City name</span>
        </div>
    </div>

    <div id="state_field">
        <label for="fld_state">State/Province</label>
        <div class="controls">
            <input type="text" id="fld_state" placeholder="state name" name="state" value="${address.state}">
            <span class="text-error empty-error">State name cannot be empty</span>
            <span class="text-error invalid-error">Please enter valid State name</span>
        </div>
    </div>

    <div id="post_code_field">
        <label for="fld_post_code" class="attention">Post Code</label>
        <div class="controls">
            <input type="text" id="fld_post_code" placeholder="post code" name="post_code" value="${address.post_code}">
            <span class="text-error empty-error">Post code cannot be empty</span>
            <span class="text-error invalid-error">Please enter valid Post code</span>
        </div>
    </div>
</form>

<div class="page-action">Your Orders</div>
<table class="table user-table">
    <thead>
    <tr>
        <th class="name-th">Name</th>
        <th class="description-th">Description</th>
        <th class="type-th">Type</th>
        <th class="price-th">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}" varStatus="row">
        <tr>
            <td><c:out value="${item.name}"/></td>
            <td><c:out value="${item.description}"/></td>
            <td><c:out value="${item.type}"/></td>
            <td><c:out value="${item.price}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="footer.jsp" %>
<script type="text/javascript" src="<c:url value='/scripts/js/user_profile.js' />"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/validator.js' />" ></script>



