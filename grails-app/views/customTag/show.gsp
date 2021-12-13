<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Show Custom Tag</title>
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
            <h1>Show CustomTag</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>

                            <td valign="top" class="value">${fieldValue(bean: customTagInstance, field: 'id')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>

                            <td valign="top" class="value">${fieldValue(bean: customTagInstance, field: 'name')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Display Value:</td>

                            <td valign="top" class="value">${fieldValue(bean: customTagInstance, field: 'displayValue')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Value:</td>

                            <td valign="top" class="value">${fieldValue(bean: customTagInstance, field: 'value')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Dummy Data:</td>

                            <td valign="top" class="value">${fieldValue(bean: customTagInstance, field: 'dummyData')}</td>

                        </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${customTagInstance?.id}"/>
                    <span class="button"><g:actionSubmit class="edit" value="Edit"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
