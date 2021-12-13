<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Business Unit</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Business Units</g:link></span>
        </div>
        <div class="body">
            <h1>Create Business Unit</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${businessUnitInstance}">
                <div class="errors">
                    <g:renderErrors bean="${businessUnitInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form action="save" method="post">
                <div class="dialog">
                    <table>
                        <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="name"><g:message code="businessUnit.name.label" default="Name"/></label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: businessUnitInstance, field: 'name', 'errors')}">
                                <g:textField name="name" value="${businessUnitInstance?.name}"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
