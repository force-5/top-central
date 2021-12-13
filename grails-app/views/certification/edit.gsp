<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Edit Certification</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Certification List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Certification</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Certification</h1>
            <g:hasErrors bean="${certificationInstance}">
                <div class="errors">
                    Please enter valid values in the required fields
                </div>
            </g:hasErrors>
            <g:form name="editCertificationForm" action="update" method="post" id="${certificationInstance?.id}">
                <div class="dialog">
                    <table>
                        <tbody>
                        <tr>
                            <td>
                                <div id="right-top1">
                                    <div class="certification-form">
                                        <g:render template="/certification/certificationForm"
                                                model="[certificationInstance:certificationInstance]"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input type="submit" class="save" id="updateCertificationButton" value="Update"/></span>
                    <g:if test="${care.hasPermission(permission: Permission.DELETE_CERTIFICATION)}">
                         <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                     </g:if>
                     <g:else>
                         <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
                     </g:else>
                </div>
            </g:form>
        </div>
    </div>
    <div class="requiredIndicator">
        &nbsp;<span style="color:red;">*</span> indicates a required field</div>
</div>
<div id="addNotificationPeriod" style="display:none;">
    <g:render template="/certification/addNotificationPeriod" model="[certificationInstance: certificationInstance]"/>
</div>
</body>
</html>

