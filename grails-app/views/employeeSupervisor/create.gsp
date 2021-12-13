<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Create Supervisor</title>
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
            <h1>Create Supervisor</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${employeeSupervisor}">
                <div class="errors">
                    <g:if test="${employeeSupervisor?.errors?.allErrors?.any{((it.code=='employeeSupervisor.person.unique.error'))}}">
                        <g:message code="employeeSupervisor.person.unique.error"/><br/>
                        <g:if test="${employeeSupervisor?.errors?.allErrors?.size() > 1}">
                            <g:message code="blank.field.message"/>
                        </g:if>
                    </g:if>
                    <g:else>
                        <g:message code="blank.field.message"/>
                    </g:else>
                </div>
            </g:hasErrors>

            <g:form action="save" method="post">
                <g:render template="/employeeSupervisor/createEmployeeSupervisorForm"/>
                <div class="buttons">
                    <span class="button"><input type="submit" class="save" value="Save"/></span>
                    <span class="button"><g:actionSubmit class="delete" value="Cancel" action="list"/></span>
                </div>
            </g:form>
        </div>
    </div>
    <div class="requiredIndicator" style="padding-left:22%;">
        &nbsp;<span style="color:red;">*</span> indicates a required field</div>

</div>
</body>
</html>
