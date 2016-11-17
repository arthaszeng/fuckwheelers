<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" scope="request" value="Create Account"/>

<%@ include file="../header.jsp" %>

    <div class="page-action">
        Create a new account
    </div>

    <c:if test="${not empty validationMessage.errors}">
        <div id="resultsMessage" class="page-action error">
            There were errors. Please check your input.
        </div>
    </c:if>

	<form id="register_form" action="/account/create" method="post" onsubmit="return validateRegistrationForm();">
        <div id="email_field">
            <label for="fld_email" class="attention">Email</label>
            <div class="controls">
                <input type="text" id="fld_email" placeholder="somebody@something.com" name="email">
                <span class="text-error empty-error">Email cannot be empty</span>
                <span class="text-error invalid-error">Please enter a valid email</span>
            </div>
        </div>

        <div id="password_field">
            <label for="fld_password" class="attention">Password</label>
            <div class="controls">
                <input type="password" id="fld_password" placeholder="secret password" name="password">
                <a class="tooltip">
                    <img  width="20px;" src="<c:url value='/images/question.png' />">
                    <span class="tooltiptext" style="text-align: left">
                         Password must contain:</br>
                         between 8 and 20 characters, and at least</br>
                         1 number,</br>
                         1 lowercase letter,</br>
                         1 uppercase letter,</br>
                         and 1 special character.</span>
                </a>
                <span class="text-error empty-error">Password cannot be empty</span>
                <span class="text-error invalid-error">Please enter valid password</span>
            </div>
        </div>

        <div id="confirmedPassword_field">
            <label for="fld_confirmedPassword" class="attention">Confirm Password</label>
            <div class="controls">
                <input type="password" id="fld_confirmedPassword" placeholder="confirm password" name="confirmedPassword">
                <span class="text-error invalid-error">Passwords do not match</span>
            </div>
        </div>

        <div id="name_field">
            <label for="fld_name" class="attention">Username</label>
            <div class="controls">
                <input type="text" id="fld_name" placeholder="username" name="name">
                <span class="text-error empty-error">Name cannot be empty</span>
                <span class="text-error invalid-error" id="duplicate_username">This username already exists</span>
            </div>
        </div>

        <div id="phone_number_field">
            <label for="fld_phone_number" class="attention">Phone Number</label>
            <div class="controls">
                <input type="text" id="fld_phone_number" placeholder="555-123456" name="phone_number">
                <span class="text-error empty-error">Phone number cannot be empty</span>
                <span class="text-error invalid-error">Please enter valid phone number</span>
            </div>
        </div>

        <div id="country_field">
            <label for="fld_country" class="attention">Country</label>
            <div class="controls">
                <select id="fld_country" name="country_id" onchange="validateStateMandatory()">
                    <option value="">Please choose</option>
                    <c:forEach var="country" items="${validationMessage.countries}" varStatus="row">
                        <option value=<c:out value="${country.country_id}"/>><c:out value="${country.name}"/></option>
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
                <input type="text" id="fld_street_one" placeholder="street line 1" name="street_one">
                <span class="text-error empty-error">Street name cannot be empty</span>
                <span class="text-error invalid-error">Please enter valid Street name</span>
            </div>
        </div>

        <div id="street_two_field">
            <label for="fld_street_two">Street 2</label>
            <div class="controls">
                <input type="text" id="fld_street_two" placeholder="street line 2" name="street_two">
                <span class="text-error invalid-error">Please enter valid Street name</span>
            </div>
        </div>
        <div id="city_field">
            <label for="fld_city" class="attention">City</label>
            <div class="controls">
                <input type="text" id="fld_city" placeholder="city name" name="city">
                <span class="text-error empty-error">City cannot be empty</span>
                <span class="text-error invalid-error">Please enter valid City name</span>
            </div>
        </div>

        <div id="state_field">
            <label for="fld_state">State/Province</label>
            <div class="controls">
                <input type="text" id="fld_state" placeholder="state name" name="state">
                <span class="text-error empty-error">State name cannot be empty</span>
                <span class="text-error invalid-error">Please enter valid State name</span>
            </div>
        </div>

        <div id="post_code_field">
            <label for="fld_post_code" class="attention">Post Code</label>
            <div class="controls">
                <input type="text" id="fld_post_code" placeholder="post code" name="post_code">
                <span class="text-error empty-error">Post code cannot be empty</span>
                <span class="text-error invalid-error">Please enter valid Post code</span>
            </div>
        </div>

        <div id="agreement_field">
            <input type="checkbox" id="fld_agreement" name="isAgree" value="true"> I agree to the Freewheelers' <a href="/termsAndConditions.html" target="_blank" class="header-link">Terms & Conditions</a>
            <span class="text-error empty-error">Please agree to the Terms & Conditions</span>
        </div>

        <div class="submit_button">
            <div>
            <div class="controls">
                <button type="submit" id="createAccount" value="Submit" onclick="showRegisterErrorMessage()">Create Account</button>
            </div>
            </div>
        </div>


	</form>

<%@ include file="../footer.jsp" %>
<script type="text/javascript" src="<c:url value='/scripts/js/validator.js' />" ></script>
<script type="text/javascript" src="<c:url value='/scripts/js/create_account.js' />" ></script>


