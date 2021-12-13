<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Supervisor List</title>
</head>

<body>
<br/>

<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir: '')}">Home</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_CONTRACTOR_SUP)}">
                <span class="menuButton"><g:link class="create createSupervisorLink"
                                                 action="create">New Supervisor</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled createSupervisorLink">New Supervisor</a></span>
            </g:else>
        </div>

        <div class="body">
            <h1>Supervisor List
                <span style="float: right;color: #666666;">Show: <g:select from="[10, 25, 50, 100, 'Unlimited']"
                                                                           name="rowCount"
                                                                           id="rowCount" value="${max}"/></span></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="contractorSupervisorsTable"
                          model="[supervisors: supervisors, supervisorsCount: supervisorsCount, offset: offset, max: max, order: order, sort: sort]"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
