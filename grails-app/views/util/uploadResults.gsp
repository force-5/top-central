<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Upload Excel Results</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="body">
            <h1>Upload Excel Results</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <br/>
                <h2>SUCCESS:</h2>
                <g:each in="${results['success']}" var="successMessage">
                    ${successMessage}<br/>
                </g:each>
                <br/><br/>
                <h2>ERRORS:</h2>
                <g:each in="${results['failure']}" var="failureMessage">
                    ${failureMessage}<br/>
                </g:each>
            </div>
        </div>
    </div>
</div>
</body>
</html>
