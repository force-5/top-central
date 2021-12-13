<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Vendor</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Vendor List</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_VENDOR)}">
                <span class="menuButton"><g:link class="create" action="create">New Vendor</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled" style="color:gray;">New Vendor</a></span>
            </g:else>
        </div>
        <div class="body">
            <h1>Show Vendor</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">Company Name:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'companyName')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Address Line1:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'addressLine1')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Address Line2:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'addressLine2')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">City:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'city')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">State:</td>

                            <td valign="top" class="value">${stateMap.get(vendorInstance?.state)}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Zip Code:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'zipCode')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Phone:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'phone')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Notes:</td>

                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: 'notes')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Master Agreement Expiration Date:</td>

                            <td valign="top" class="value">
                                <g:formatDate date="${vendorInstance.agreementExpirationDate}" format="MM/dd/yyyy"/>
                            </td>
                        </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form action="edit" id="${vendorInstance?.id}">
                    <g:if test="${care.hasPermission(permission: Permission.UPDATE_VENDOR)}">
                        <span class="button"><input type="submit" class="edit editVendorLink" value="Edit"/></span>
                    </g:if>
                    <g:else>
                        <span class="button"><input type="button" class="edit" style="color:gray;" value="Edit"/></span>
                    </g:else>
                    <g:if test="${care.hasPermission(permission: Permission.DELETE_VENDOR)}">
                        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                    </g:if>
                    <g:else>
                        <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
                    </g:else>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
