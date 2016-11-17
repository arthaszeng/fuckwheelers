<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" scope="request" value="Survey"/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Survey</title>
    <link rel="stylesheet" href="<c:url value='/scripts/css/main.css' />" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.10.2.js' />"></script>
    <script type="text/javascript">
        $(function () {
            var closeButton = document.getElementById("closeButton");
            closeButton.onclick = function () {
                window.close();
            };
        });
    </script>
</head>
<body>