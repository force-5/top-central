<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Certification List</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_CERTIFICATION)}">
                <span class="menuButton"><g:link class="create createCertificationLink" action="create">New Certification</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled createCertificationLink">New Certification</a></span>
            </g:else>
        </div>
        <div class="body">
            <h1>Certification List
                <span style="float: right;color: #666666;">Show: <g:select from="[10, 25, 50, 100, 'Unlimited']"
                                                                           name="rowCount"
                                                                           id="rowCount" value="${max}"/></span>
            </h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="certificationsTable"
                          model="[certificationInstanceList: certificationInstanceList, certificationInstanceTotal: certificationInstanceTotal, offset: offset, max: max, order: order, sort: sort]"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
