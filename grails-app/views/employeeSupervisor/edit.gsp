<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
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
            <g:render template="/shared/errors" model="[instance: employeeSupervisor]"/>
            <g:form method="post" action="update" id="${employeeSupervisor?.id}">
                <g:render template="/employeeSupervisor/createEmployeeSupervisorForm"/>

                <div class="buttons">
                    <g:if test="${ConfigurationHolder.config.isEmployeeEditable != 'false'}">
                        <span class="button"><input type="submit" class="save" value="Update"/></span>
                        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                    </g:if>
                </div>
            </g:form>
        </div>
    </div>
    <div class="requiredIndicator" style="padding-left:22%;">
        &nbsp;<span style="color:red;">*</span> indicates a required field</div>
</div>
</body>
</html>
