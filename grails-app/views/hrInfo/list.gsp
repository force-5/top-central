<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>HrInfo List</title>
</head>
<body>
<br/>
<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New HrInfo</g:link></span>
        </div>
        <div class="body">
            <h1>HrInfo List</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                    <tr>

                        <g:sortableColumn property="id" title="Id"/>

                        <g:sortableColumn property="slid" title="SLID"/>

                        <g:sortableColumn property="workerNumber" title="Worker Number"/>

                        <g:sortableColumn property="SUPV_PERNR" title="SUPVPERNR"/>

                        <g:sortableColumn property="supervisorSlid" title="Supervisor SLID"/>

                        <g:sortableColumn property="pernr" title="PERNR"/>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${hrInfoInstanceList}" status="i" var="hrInfoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td><g:link action="show" id="${hrInfoInstance.id}">${fieldValue(bean: hrInfoInstance, field: 'id')}</g:link></td>

                            <td>${fieldValue(bean: hrInfoInstance, field: 'slid')}</td>

                            <td>${fieldValue(bean: hrInfoInstance, field: 'workerNumber')}</td>

                            <td>${fieldValue(bean: hrInfoInstance, field: 'SUPV_PERNR')}</td>

                            <td>${fieldValue(bean: hrInfoInstance, field: 'supervisorSlid')}</td>

                            <td>${fieldValue(bean: hrInfoInstance, field: 'pernr')}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${hrInfoInstanceTotal}"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
