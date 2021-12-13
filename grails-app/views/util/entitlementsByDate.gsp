<%@ page import="com.force5solutions.care.ldap.Permission;" %><html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Entitlements</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
</div>
<br/>
<g:form action="showEntitlementsByDate">
    <div class="namerowbig"><span>Entitlement Role : &nbsp;&nbsp;</span>
        <g:select class="listbox" name="id" from="${entitlementRoles}" optionKey="id" optionValue="name" noSelection="['':'(Select One)']"/>
    </div>
    <div class="namerowbig"><span>Date : &nbsp;&nbsp;</span>
        <calendar:datePicker name="date" showTime="true" dateFormat="%m/%d/%Y %H:%M:%S"/>
    </div>
    <div><g:submitButton name="submit" value="Submit"/></div>
</g:form>

</body>
</html>
