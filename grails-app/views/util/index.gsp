<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Utility Links</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div class="body">
    <h1>TOP Central</h1>
    <br/>
    <br/>
    <div class="list">
        <g:link controller="employee" action="workflowReport" id="latest" target="_blank"><span style="font-size:14px;">Workflow Report</span></g:link>
        <span style="font-size:14px;">
            <br/>(By default, shows the 'latest' triggered workflow's report. You can also put the workflow's GUID in the URL to see the report on any other workflow.)
        </span>
        <br/>
        <br/>
        <g:link controller="worker" action="workflowReportBySlidOrId" id="empslid-1" target="_blank"><span style="font-size:14px;">Workflow Report By SLID</span></g:link>
        <span style="font-size:14px;">
            <br/>(Please put the SLID of the employee in the URL for whom you want to see the workflow reports)
        </span>
        <br/>
        <br/>
        <g:link controller="feedRun" action="list" target="_blank"><span style="font-size:14px;">Feed Runs</span></g:link>
        <br/>
        <br/>
        <g:link action="selectDateTimeForEscalation" target="_blank"><span style="font-size:14px;">Select Date/Time for Escalation</span></g:link>
        <br/>
        <br/>
        <g:link action="list" controller="centralWorkflowTaskTemplate" target="_blank"><span style="font-size:14px;">Central Workflow Task Templates</span></g:link>
        <br/>
        <br/>
        <g:link action="kickOffJobs" target="_blank"><span style="font-size:14px;">Execute Job</span></g:link>
        <br/>
        <br/>
        <g:link action="uploadFixture" target="_blank"><span style="font-size:14px;">Upload/Execute Fixture</span></g:link>
        <br/>
        <br/>
        <g:link action="list" controller="certificationExpirationNotificationTemplate" target="_blank"><span style="font-size:14px;">Certification Expiration Notification Template</span></g:link>
        <br/>
        <br/>
        <g:link action="simulateCertificationExpirationNotification" target="_blank"><span style="font-size:14px;">**SIMULATE** Certification Expiration Notification</span></g:link>
        <br/>
        <br/>
        <g:link action="selectFeedWorkflow" target="_blank"><span style="font-size:14px;">Access Granted/Revoked by Feed Utility</span></g:link>

    </div>
</div>
</body>
</html>
