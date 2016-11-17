<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="Login"/>
<%@ include file="header.jsp" %>

    <c:choose>
        <c:when test="${not empty error}">
            <div id="loginError" class="page-action error">
                Your login attempt was not successful, try again.<br /> Caused :
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:when>
        <c:otherwise>
            <div id="TrailBlazers - Login" class="page-action">
                Login with Username and Password
            </div>
        </c:otherwise>
    </c:choose>

	<form id="login_form" name='f' onsubmit="return validateLoginForm();" action="<c:url value='j_spring_security_check' />" method="post">
        <div id="user_field">
            <label>Username/Email</label>
            <div class="controls">
                <input type='text' id="fld_user" name='j_username' placeholder="Username or Email"></td>
            </div>
            <span class="text-error">Must enter a username or email.</span>
        </div>

        <div id="password_field">
            <label>Password</label>
            <div class="controls">
                <input type="password" id="fld_password" name="j_password" placeholder="Password">
            </div>
            <span class="text-error">Must enter a password.</span>
        </div>

        <div>
            <div class="controls">
                <button type="submit" name="submit">Sign in</button>
            </div>
        </div>

	</form>

<%@ include file="footer.jsp" %>
<script type="text/javascript" src="<c:url value='/scripts/js/login_validator.js' />" ></script>