<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Create Business Unit Requester</title>
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
            <h1>Create Business Unit Requester</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${instance}">
                <div class="errors">
                    <g:if test="${instance?.errors?.allErrors?.any{((it.code=='businessUnitRequester.person.unique.error'))}}">
                        <g:message code="businessUnitRequester.person.unique.error"/><br/>
                        <g:if test="${instance?.errors?.allErrors?.size() > 1}">
                            <g:message code="blank.field.message"/>
                        </g:if>
                    </g:if>
                    <g:else>
                        <g:message code="blank.field.message"/>
                    </g:else>
                </div>
            </g:hasErrors>
            <g:form name="businessUnitRequesterForm" method="post" action="save">
                <g:render template="/businessUnitRequester/BURForm" model="[instance:instance]"/>
                <div class="buttons">
                    <span class="button"><input type="submit" class="save" id="submitBusinessUnitRequester" value="Save"/></span>
                    <span class="button"><g:actionSubmit class="delete" value="Cancel" action="list"/></span>
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
