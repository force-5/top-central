<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>SNMP Alert List</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
        </div>
        <div class="body">
            <h1>SNMP Alert List</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                    <tr>
                        <g:sortableColumn property="id" title="Id"/>

                        <g:sortableColumn property="exceptionName" title="Exception Type"/>

                        <g:sortableColumn property="errorString" title="Error"/>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${snmpAlerts}" status="i" var="snmpAlert">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="showAlert" id="${snmpAlert.id}">${fieldValue(bean: snmpAlert, field: 'id')}</g:link></td>
                            <td><g:link action="showAlert" id="${snmpAlert.id}">${fieldValue(bean: snmpAlert, field: 'exceptionMessage')}</g:link></td>
                            <td><g:link action="showAlert" id="${snmpAlert.id}">${fieldValue(bean: snmpAlert, field: 'errorDescription')}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${snmpAlertTotal}"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
