<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Vendor List</title>
</head>

<body>
<br/>

<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir: '')}">Home</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_VENDOR)}">
                <span class="menuButton"><g:link class="create createVendorLink"
                                                 action="create">New Vendor</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled createVendorLink">New Vendor</a></span>
            </g:else>
        </div>

        <div class="body">
            <h1>Vendor List
                <span style="float: right;color: #666666;">Show: <g:select from="[10, 25, 50, 100, 'Unlimited']"
                                                                           name="rowCount"
                                                                           id="rowCount" value="${max}"/></span>
            </h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="vendorsTable"
                          model="[vendorInstanceList: vendorInstanceList, vendorInstanceTotal: vendorInstanceTotal, offset: offset, max: max, 'stateMap': stateMap, order: order, sort: sort]"/>
            </div>

        </div>
    </div>
</div>
</body>
</html>
