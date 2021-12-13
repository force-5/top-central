<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Select Access Grant/Revoke Request by Feed</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div class="body">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list" style="font-size:12px; padding-top:20px;">
        <h3>Access Grant/Revoke Request by Feed</h3>
        <g:form action="initiateFeedWorkflow" method='post'>
            <p style="padding-top:20px;">Please enter employee SLID: <g:textField name="slid" id='slid'/></p>
            <p>Please select entitlement role : <g:select name='entitlementRole' from="${entitlementRoles}" optionKey="id" optionValue="name" noSelection="['':'(Select One)']"/></p>
            <p>Please select the type of workflow to initiate:
                <g:radio name="workflowRequest" value="Grant"/><span>Grant Access</span>
                <g:radio name="workflowRequest" value="Revoke"/><span>Revoke Access</span></p>
            <div class="buttons">
                <span class="button"><g:actionSubmit class="save" action="initiateFeedWorkflow" value="OK"/></span>
            </div>
        </g:form>
    </div>
</div>
</body>
</html>
