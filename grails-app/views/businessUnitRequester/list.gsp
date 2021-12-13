<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Business Unit Requester List</title>
</head>

<body>
<br/>

<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir: '')}">Home</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_BUR)}">
                <span class="menuButton"><g:link class="create createBurLink"
                                                 action="create">New Business Unit Requester</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled createBurLink">New Business Unit Requester</a></span>
            </g:else>
        </div>

        <div class="body">
            <h1>Business Unit Requester List
                <span style="float: right;color: #666666;">Show: <g:select
                        from="[10, 25, 50, 100, 'Unlimited']"
                        name="rowCount"
                        id="rowCount" value="${max}"/></span></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="businessUnitRequestersTable"
                          model="[burInstanceList: burInstanceList, burInstanceTotal: burInstanceTotal, offset: offset, max: max, order: order, sort: sort]"/>
            </div>

        </div>
    </div>
</div>
</body>
</html>
