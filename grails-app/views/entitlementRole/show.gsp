<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'entitlementRole.label', default: 'Entitlement Role')}"/>
    <title>Show Entitlement Role</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    %{--<g:render template="/permission/listButton" model="[permission: Permission.READ_ENTITLEMENT_ROLE, label: 'Entitlement Role List']"/>--}%
    %{--<g:render template="/permission/createButton" model="[permission: Permission.CREATE_ENTITLEMENT_ROLE, label: 'New Entitlement Role']"/>--}%
</div>
<div class="body">
    <h1>Show Entitlement Role</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlementRole.name.label" default="Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: entitlementRole, field: "name")}</td>

            </tr>

            %{--<tr class="prop">--}%
            %{--<td valign="top" class="name"><g:message code="entitlementRole.isExposed.label" default="Is Exposed"/></td>--}%

            %{--<td valign="top" class="value">${(entitlementRole?.isExposed) ? 'Yes' : 'No'}</td>--}%

            %{--</tr>--}%

            %{--<tr class="prop">--}%
            %{--<td valign="top" class="name"><g:message code="entitlementRole.status.label" default="Status"/></td>--}%

            %{--<td valign="top" class="value">${entitlementRole?.status?.encodeAsHTML()}</td>--}%

            %{--</tr>--}%

            <tr class="prop">
                <td valign="top" class="name">Standards</td>

                <td valign="top" class="value">${entitlementRole?.standards}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name">Types</td>

                <td valign="top" class="value">${entitlementRole?.types}</td>

            </tr>
            <tr class="prop">
                <td valign="top" class="name">Required Certifications</td>
                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${entitlementRole.requiredCertifications}" var="requiredCertification">
                            <li><g:link controller="certification" action="show" id="${requiredCertification.id}">${requiredCertification?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">Gatekeepers</td>
                <g:set var="gatekeepers" value="${entitlementRole.gatekeepers.tokenize(':')}"/>
                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each var="gatekeeper" in="${gatekeepers}">
                                <li>${gatekeeper}</li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="buttons">
        <g:form method="post">
            <g:hiddenField name="id" value="${entitlementRole?.id}"/>
            <g:render template="/permission/editButton" model="[permission: Permission.UPDATE_ENTITLEMENT_ROLE]"/>
        </g:form>
    </div>
</div>
</body>
</html>
