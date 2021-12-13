<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Edit Custom Tag</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">CustomTag List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New CustomTag</g:link></span>
        </div>
        <div class="body">
            <h1>Edit CustomTag</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${customTagInstance}">
                <div class="errors">
                    <g:renderErrors bean="${customTagInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form method="post">
                <input type="hidden" name="id" value="${customTagInstance?.id}"/>
                <input type="hidden" name="version" value="${customTagInstance?.version}"/>
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customTagInstance, field: 'name', 'errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean: customTagInstance, field: 'name')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="displayValue">Display Value:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customTagInstance, field: 'displayValue', 'errors')}">
                                    <input type="text" id="displayValue" name="displayValue" value="${fieldValue(bean: customTagInstance, field: 'displayValue')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value">Value:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customTagInstance, field: 'value', 'errors')}">
                                    <textarea rows="5" cols="40" name="value">${fieldValue(bean: customTagInstance, field: 'value')}</textarea>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dummyData">Dummy Data:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customTagInstance, field: 'dummyData', 'errors')}">
                                    <textarea rows="5" cols="40" name="dummyData">${fieldValue(bean: customTagInstance, field: 'dummyData')}</textarea>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
