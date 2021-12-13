<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.ldap.Permission; com.force5solutions.care.cc.Worker" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>

    <title>Workflow Report By SLID</title>
</head>
<body>
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
    <span class="menuButton">
        <g:link action="access" controller="workerEntitlementRole" params="[id: worker.id]"
                class="list">Access</g:link>
    </span>
    <span class="menuButton">
        <g:if test="${care.hasPermission(permission: Permission.READ_EMPLOYEE_CERTIFICATION, worker: Worker.findById(worker?.id)) || care.hasPermission(permission: Permission.READ_CONTRACTOR_CERTIFICATION, worker: Worker.findById(worker?.id))}">
            <span class="menuButton"><g:link action="certification" controller="workerCertification" params="[id: worker.id]"
                    class="list">Certification</g:link></span>
        </g:if>
        <g:else>
            <span class="menuButton"><a class="list-disabled  employeeCertificationLink">Certification</a></span>
        </g:else>
    </span>
</div>
<g:render template="workflowReportBySlid" model="[worker: worker, slid: slid, selectedCentralWorkflowTasks: selectedCentralWorkflowTasks]"/>
</body>
</html>
