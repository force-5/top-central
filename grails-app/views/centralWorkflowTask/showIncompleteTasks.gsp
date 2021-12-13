<%@ page import="com.force5solutions.care.workflow.CentralWorkflowType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Inbox</title>
    <style type="text/css">
        td{
            font-weight:bold;
            font-size:11px;
            padding:5px;
        }
    </style>
</head>
<body>
<br/>
<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
        </div>
        <div class="body">
            <h1>Incomplete Tasks For ${taskType}</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table class="tablesorter" id="tablesorter">
                    <thead>
                        <tr>
                            <th>Workflow Type</th>
                            <th>Date Created</th>
                            <th>${taskType == (CentralWorkflowType.ACCESS_VERIFICATION).toString() ? "Supervisor" : "Worker"}</th>
                            <th>Location</th>
                            <th>Current Node</th>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${tasks}" status="i" var="task">
                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                <td>${task?.workflowType}</td>
                                <td>${task?.dateCreated?.myDateTimeFormat()}</td>
                                <td>${task?.worker}</td>
                                <td>${task?.entitlementRole?.toTreeString()}</td>
                                <td>${task?.nodeName}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        if (jQuery("#tablesorter tr").size() > 1) {
            jQuery("#tablesorter").tablesorter({textExtraction: myTextExtraction, sortList: [[1,1]]});
        }
    });
</script>
</body>
</html>