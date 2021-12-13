<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Create Certification</title>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><g:link class="home" url="${createLink(uri: '/')}">Home</g:link></span>
            <span class="menuButton"><g:link class="list" action="list">Certification List</g:link></span>

        </div>
        <div class="body">
            <h1>Create Certification</h1>
            <g:hasErrors bean="${certificationInstance}">
                <div class="errors">
                    Please enter valid values in the required fields
                </div>
            </g:hasErrors>
            <g:form name="certificationForm" action="save" method="post">
                <div class="dialog">
                    <table>
                        <tbody>
                        <tr>
                            <td>
                                <div id="right-top1">
                                    <div class="certification-form">
                                        <g:render template="/certification/certificationForm"
                                                model="[certificationInstance:certificationInstance]"/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>


                <div class="buttons">
                    <span class="button"><input type="submit" class="save" id="submitCertification" value="Save"/></span>
                    <span class="button"><g:actionSubmit class="delete" value="Cancel" action="list"/></span>

                </div>
            </g:form>
        </div>
    </div>
    <div class="requiredIndicator">
        &nbsp;<span style="color:red;">*</span> indicates a required field</div>
</div>
<div id="addNotificationPeriod" style="display:none;">
    <g:render template="/certification/addNotificationPeriod" model="[certificationInstance: certificationInstance]"/>
</div>
</body>
</html>
