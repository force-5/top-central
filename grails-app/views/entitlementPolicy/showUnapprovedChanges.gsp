<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName" value="${message(code: 'entitlementPolicy.label', default: 'Entitlement Policy')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:render template="/permission/listButton" model="[permission: Permission.READ_ENTITLEMENT_POLICY, label: 'Entitlement Policy List']"/>
    <g:render template="/permission/createButton" model="[permission: Permission.CREATE_ENTITLEMENT_POLICY, label: 'New Entitlement Policy']"/>
    <span class="menuButton"><a class="create" href="${createLink(action:'unapprovedEntitlementPolicies')}">Unapproved Entitlement Policies</a></span>
</div>
<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlementPolicy.name.label" default="Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: entitlementPolicy, field: "name")}</td>

            </tr>
            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlementPolicy.standards.label" default="Standards"/></td>

                <td valign="top" class="value">
                    <ul>
                        <g:each in="${entitlementPolicy.standards}" var="standard">
                            <li>${standard}</li>
                        </g:each>
                    </ul>
            </tr>
            <tr class="prop">
                <td valign="top" class="name"><g:message code="entitlementPolicy.customProperties.label" default="Custom Properties"/></td>
                <td valign="top" class="value">
                    <ul>
                        <g:each in="${entitlementPolicy.customProperties}" var="customProperty">
                            <li>${customProperty}</li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">Required Certifications</td>
                <td valign="top" class="value">
                    <ul>
                        <g:each in="${entitlementPolicy?.requiredCertifications}" var="requiredCertification">
                            <li>${requiredCertification}</li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">Certifications for Employees</td>
                <td valign="top" class="value">
                    <ul>
                        <g:each in="${entitlementPolicy?.requiredCertificationsForEmployee}" var="requiredCertification">
                            <li>${requiredCertification}</li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">Certifications for Contractors</td>
                <td valign="top" class="value">
                    <ul>
                        <g:each in="${entitlementPolicy?.requiredCertificationsForContractor}" var="requiredCertification">
                            <li>${requiredCertification}</li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="buttons">
        <g:form method="post">
            <g:hiddenField name="id" value="${entitlementPolicy?.id}"/>
            <g:render template="/permission/editButton" model="[permission: Permission.UPDATE_ENTITLEMENT_POLICY]"/>
            <span class="button">
                <g:actionSubmit class="save" action="resubmitApprovalRequest" value="Re-submit"/></span>
        </g:form>
    </div>

    <versionable:showUnapprovedChanges object="${entitlementPolicy}"/>
</div>
</body>
</html>
