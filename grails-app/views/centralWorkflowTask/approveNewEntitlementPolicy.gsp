<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>TOP Human Task</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.4.4.min.js')}"></script>
    <script type="text/javascript">jQuery.noConflict();</script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-ui-1.8.13.custom.min-new.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-ui-timepicker-addon.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir: 'js', file: 'jQuery.tablesorter.js')}"></script>
    <style type="text/css">
    .scrollTable {
        width: 700px;
    }
    </style>
</head>
<body>
<br/>
<div id="wrapper">
    <h1>TOP Human Task</h1>
    <br/>

    <g:if test="${tasks}">
        <g:render template="approveNewEntitlementPolicyGroupResponse" model="[tasks: tasks, task: task]"/>
    </g:if>
    <g:else>
        <g:render template="approveNewEntitlementPolicyResponse" model="[task: task]"/>
    </g:else>
</div>
</body>
</html>
