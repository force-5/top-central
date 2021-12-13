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
        <span class="menuButton"><g:link action="show" controller="personnel" params="[id: worker.id]"
                class="create">Profile</g:link></span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">Profile</a></span>
    </g:else>
    <g:if test="${worker instanceof Contractor}">
        <g:set var="permission" value="${Permission.READ_CONTRACTOR_ACCESS}"/>
    </g:if>
    <g:else>
        <g:set var="permission" value="${Permission.READ_EMPLOYEE_ACCESS}"/>
    </g:else>
    <g:if test="${care.hasPermission(permission: permission, worker: worker)}">
        <span class="menuButton"><g:link action="access" controller="workerEntitlementRole" params="[id: worker.id]"
                class="list">Access</g:link></span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">Access</a></span>
    </g:else>
    <g:if test="${care.hasPermission(permission: Permission.READ_MANAGE_WORKFLOW, worker: worker)}">
        <span class="menuButton">
            <g:if test="${worker instanceof Contractor}">
                <g:set value="${worker.id}" var="id"/>
            </g:if>
            <g:else>
                <g:set value="${worker.slid}" var="id"/>
            </g:else>
            <g:link controller="worker" action="workflowReportBySlidOrId" params="[id: id]" class="list">
                Workflows
            </g:link>
        </span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">Workflows</a></span>
    </g:else>
</div>
<g:render template="/workerCertification/sharedCertificationBox" model="[workerCertifications: workerCertifications, worker: worker]"/>
</body>
</html>