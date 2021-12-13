<%@ page import="com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Edit Supervisor</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Supervisor List</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Supervisor</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/shared/errors" model="[instance: supervisor]"/>
            <g:form name="editSupervisorForm" method="post" action="update" id="${supervisor?.id}">
                <g:render template="/contractorSupervisor/supervisorForm"/>
                               
                <div class="buttons">
                    <span class="button"><input type="submit" id="editSupervisorButton" class="save" value="Update"/></span>
                    <g:if test="${care.hasPermission(permission: Permission.DELETE_CONTRACTOR_SUP)}">
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
            &nbsp;<span style="color:red;">*</span> indicates a required field </div>
</div>
</body>
</html>
