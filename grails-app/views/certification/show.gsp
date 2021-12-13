<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Certification</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Certification List</g:link></span>
            <g:if test="${care.hasPermission(permission: Permission.CREATE_CERTIFICATION)}">
                <span class="menuButton"><g:link class="create" action="create">New Certification</g:link></span>
            </g:if>
            <g:else>
                <span class="menuButton"><a class="create-disabled">New Certification</a></span>
            </g:else>
        </div>
        <div class="body">
            <h1>Show Certification</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name">Name:</td>

                        <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: 'name')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Description:</td>

                        <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: 'description')}</td>

                    </tr>

                    <tr class="prop">
                        <g:if test="${certificationInstance?.quarterly}">
                            <td valign="top" class="name">Quarterly :</td>
                            <td valign="top" class="value">Yes</td>
                        </g:if>
                        <g:else>
                            <td valign="top" class="name">Period:</td>
                            <td valign="top" class="value">
                                ${fieldValue(bean: certificationInstance, field: 'period')} &nbsp; ${certificationInstance?.periodUnit?.encodeAsHTML()}
                            </td>
                        </g:else>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Expiration Offset (In Days):</td>

                        <td valign="top" class="value">
                            ${fieldValue(bean: certificationInstance, field: 'expirationOffset')}
                        </td>

                    </tr>

                    <tr class="prop">
                        <g:if test="${certificationInstance?.courses}">
                            <td valign="top" class="name">Courses:</td>

                            <td valign="top" class="value">${certificationInstance.courses?.join(', ')}</td>
                        </g:if>
                    </tr>
                    <g:if test="${certificationInstance.providerRequired}">
                        <tr class="prop">
                            <td valign="top" class="name">Providers:</td>

                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: 'providers')}</td>

                        </tr>
                    </g:if>
                    <g:if test="${certificationInstance.subTypeRequired}">

                        <tr class="prop">
                            <td valign="top" class="name">Sub Types:</td>

                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: 'subTypes')}</td>

                        </tr>
                    </g:if>
                    <g:if test="${certificationInstance.trainingRequired}">
                        <tr class="prop">
                            <td valign="top" class="name">Training Providers:</td>

                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: 'trainingProviders')}</td>

                        </tr>
                    </g:if>

                    <g:if test="${certificationInstance.testRequired}">
                        <tr class="prop">
                            <td valign="top" class="name">Test Providers:</td>

                            <td valign="top" class="value">${fieldValue(bean: certificationInstance, field: 'testProviders')}</td>

                        </tr>
                    </g:if>

                    <tr class="prop">
                        <td valign="top" class="name">Affidavit Required:</td>
                        <g:if test="${certificationInstance?.affidavitRequired}">
                            <td valign="top" class="value">Yes</td>
                        </g:if>
                        <g:else>
                            <td valign="top" class="value">No</td>
                        </g:else>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Notification Periods:</td>

                        <td valign="top" class="value">${certificationInstance.notificationPeriods?.join(', ')}</td>

                    </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form action="edit" id="${certificationInstance?.id}">
                    <g:if test="${care.hasPermission(permission: Permission.UPDATE_CERTIFICATION)}">
                        <span class="button"><input type="submit" class="edit editCertificationLink" value="Edit"/></span>
                    </g:if>
                    <g:else>
                        <span class="button"><input type="button" class="edit" style="color:gray;" value="Edit"/></span>
                    </g:else>
                    <g:if test="${care.hasPermission(permission: Permission.DELETE_CERTIFICATION)}">
                        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                    </g:if>
                    <g:else>
                        <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
                    </g:else>
                </g:form>
            </div>
        </div>
    </div>
    <versionable:showHistory object="${certificationInstance}"/>
</div>
</body>
</html>
