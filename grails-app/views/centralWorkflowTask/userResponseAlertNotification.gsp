<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Care Alert Human Task</title>
    <style type="text/css">
    .scrollTable {
        width: 700px;
    }
    </style>
</head>
<body>
<br/>
<div id="wrapper">
    <h1>Care Alert Human Task</h1>
    <br/>
<div style="margin:0 50px;font-size:14px;">
    <g:form action="sendUserResponse" method="post" enctype="multipart/form-data">
        <div style="font-size:14px;">
            <span style="float:left;">Please confirm the notification receipt for <b>${task.workerCertification}</b> by <b>${task.worker}'s</b> &nbsp;&nbsp;&nbsp;&nbsp;</span>
            <g:if test="${task.actions}">
                <span><g:select class="listbox" style="padding:0;width:150px;" name="userAction" from="${task.actions}"/></span>
            </g:if>
        </div>
        <br/><br/>

        <div>
            <span>Additional note, if any:</span>
            <textarea style="width:350px; height:120px; margin-left: 60px;" id="accessJustification" name="accessJustification">${accessJustification}</textarea>
            <input type="hidden" name="id" value="${task.id}"/>
            <g:render template="/centralWorkflowTask/attachment"/>
        </div>
        <div style="text-align:center;">
            <input class="button" type="submit" value="Submit"/>
        </div>
        </div>
    </g:form>
</div>
</body>
</html>
