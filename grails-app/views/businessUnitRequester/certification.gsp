<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="contractor"/>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>TOP By Force 5 : Certification</title>
</head>
<body>
<care:recentStatus workerId="${worker?.id}"/>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:if test="${worker instanceof Contractor}">
        <g:set var="permission" value="${Permission.READ_CONTRACTOR_PROFILE}"/>
    </g:if>
    <g:else>
        <g:set var="permission" value="${Permission.READ_EMPLOYEE_PROFILE}"/>
    </g:else>
    <g:if test="${care.hasPermission(permission: permission, worker: worker)}">
        <span class="menuButton"><g:link action="show" id="${worker.id}" class="list">Profile</g:link></span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">Profile</a></span>
    </g:else>
</div>
<g:render template="/workerCertification/sharedCertificationBox" model="[workerCertification: workerCertification, worker: worker]"/>
</body>
</html>