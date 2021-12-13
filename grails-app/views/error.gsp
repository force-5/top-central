<%@ page import="grails.util.GrailsUtil; org.codehaus.groovy.grails.commons.GrailsApplication" %>
<html>
<head>
    <title>CARE : Error Page</title>
    <meta name="layout" content="contractor"/>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <h1><strong>Oops .. there is an error</strong></h1>
        <div>
            <h2>Looks like there is some problem serving your request. This can be because of various reasons such as:</h2>
            <br/>
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# You are trying to access a page which does not exist.</h3>
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# You do-not have necessary rights to access the page you are trying to reach.</h3>
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;# We are experiencing some problem at our server side.</h3>
            <br/>
            <g:if test="${GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION}">

                <br/><h3>Following stack trace is not displayed in production enviroment</h3>

                <h2>
                    <div class="message">
                        <strong>Message:</strong> ${exception?.message?.encodeAsHTML()} <br/>
                        <strong>Caused by:</strong> ${exception?.cause?.message?.encodeAsHTML()} <br/>
                        <strong>Class:</strong> ${exception?.className} <br/>
                        <strong>At Line:</strong> [${exception?.lineNumber}] <br/>
                        <strong>Code Snippet:</strong><br/>
                        <div class="snippet">
                            <g:each var="cs" in="${exception?.codeSnippet}">
                                ${cs?.encodeAsHTML()}<br/>
                            </g:each>
                        </div>
                    </div>
                    <h2>Stack Trace</h2>
                    <div>
                        <pre><g:each in="${exception?.stackTraceLines}">${it?.encodeAsHTML()}<br/></g:each></pre>
                    </div>
                </h2>
            </g:if>
        </div>
    </div>
</div>
</body>
</html>