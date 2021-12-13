<%@ page import="com.force5solutions.care.ldap.Permission;" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Edit Vendor</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Vendor List</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Vendor</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${vendorInstance}">
                <div class="errors">
                    Please enter valid values in the required fields
                </div>
            </g:hasErrors>
            <g:form name="vendorUpdateForm" method="post" action="update" id="${vendorInstance?.id}">
                <input type="hidden" name="version" value="${vendorInstance?.version}"/>
                <g:render template="/vendor/vendorForm" model="[vendorInstance:vendorInstance]"/>
                <div class="buttons">
                    <span class="button"><input type="submit" class="save" id="updateVendorButton" value="Update"/></span>
                    <g:if test="${care.hasPermission(permission: Permission.DELETE_VENDOR)}">
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
