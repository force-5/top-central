<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.force5solutions.care.ldap.Permission;" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Edit Business Unit Requester</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Business Unit Requester List</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Business Unit Requester</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${instance}">
                <div class="errors">
                    Please enter valid values in the required fields
                </div>
            </g:hasErrors>
            <g:form name="editBusinessUnitRequesterForm" method="post" id="${instance?.id}" action="update">
                <g:render template="/businessUnitRequester/BURForm" model="[instance:instance]"/>
                <div class="buttons">
                    <g:if test="${ConfigurationHolder.config.isEmployeeEditable != 'false'}">
                        <span class="button"><input type="submit" class="save" id="editBusinessUnitRequesterButton" value="Update"/></span>
                        <g:if test="${care.hasPermission(permission: Permission.DELETE_BUR)}">
                            <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                        </g:if>
                        <g:else>
                            <span class="button"><input type="button" class="delete" style="color:gray;" value="Delete"/></span>
                        </g:else>
                    </g:if>
                </div>
            </g:form>
        </div>
    </div>
    <div class="requiredIndicator">
        &nbsp;<span style="color:red;">*</span> indicates a required field</div>
</div>
<script type="text/javascript">
    jQuery('div#submit-button').css('visibility', 'hidden');
</script>
</body>
</html>
