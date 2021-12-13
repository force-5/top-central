<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <meta name="layout" content="contractor"/>
    <title>Create Vendor</title>
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
            <h1>Create Vendor</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/shared/errors" model="[instance: vendorInstance]"/>
            <g:form name="vendorForm" action="save" method="post">
                <g:render template="/vendor/vendorForm" model="[vendorInstance:vendorInstance]"/>
                <div class="buttons">
                    <span class="button"><input type="submit" class="save"  id="submitVendor" value="Save"/></span>
                    <span class="button"><g:actionSubmit class="delete" value="Cancel" action="list"/></span>

                </div>
            </g:form>
        </div>
</div>
    <div class="requiredIndicator">
        &nbsp;<span style="color:red;">*</span> indicates a required field </div>

</div>
</body>
</html>
