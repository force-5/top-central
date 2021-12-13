<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'entitlementPolicy.label', default: 'Entitlement Policy')}"/>
    <title>Entitlement Policy List</title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/createButton"
              model="[permission: Permission.CREATE_ENTITLEMENT_POLICY, label: 'New Entitlement Policy']"/>
    <span class="menuButton"><a class="create"
                                href="${createLink(action: 'unapprovedEntitlementPolicies')}">Unapproved Entitlement Policies</a>
    </span>
</div>

<div class="body">
    <h1>Entitlement Policy List
        <span style="float: right;color: #666666;">Show: <g:select from="[10, 25, 50, 100, 'Unlimited']"
                                                                   name="rowCount"
                                                                   id="rowCount" value="${max}"/></span>
    </h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div id="list" class="list" style="padding-top: 10px;">
        <g:render template="entitlementPoliciesTable"
                  model="[entitlementPolicyList: entitlementPolicyList, entitlementPolicyTotal: entitlementPolicyTotal, offset: offset, max: max, order: order, sort: sort]"/>
    </div>

</div>
</body>
</html>
