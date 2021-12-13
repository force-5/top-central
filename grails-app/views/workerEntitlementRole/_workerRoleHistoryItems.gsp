<div id="workerRoleHistoryItemHistory" class="workerRoleHistoryItem-history">
    <table border="0" cellpadding="0" cellspacing="0" width="450" class="scrollTable tablesorter">
        <thead>
        <tr>

            <th>Date</th>
            <th>Actor SLID</th>
            <th>Node Name</th>
            <th>Response</th>
            <th>Status</th>
            <th>Documents</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${workflowVOs}" status="i" var="workflowVO">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${workflowVO?.taskCreated?.myDateTimeFormat()}</td>
                <td>${workflowVO?.actorSlid}</td>
                <td>${workflowVO?.nodeName}</td>
                <td>${workflowVO?.response}</td>
                <td>${workflowVO?.status}</td>
                <td>
                    <a href="#" class="filterbutton" onclick="documentPopUp('${workflowVO?.system}', '${workflowVO?.taskId}', '${workflowVO?.message}');">
                        <span>${workflowVO?.documentsCount}</span>
                    </a>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
<div id="supportDocuments"></div>
<script type="text/javascript">
    if (jQuery("#workerRoleHistoryItemHistory>table tr").size() > 1) {
        jQuery("#workerRoleHistoryItemHistory>table").tablesorter({textExtraction: myTextExtraction});
    }
    function documentPopUp(system, taskId, message) {
        jQuery.post("${createLink(action:'workflowTaskDocuments')}",
        { ajax: 'true', taskId:taskId}, function(htmlText) {
                    jQuery('#supportDocuments').html(htmlText);
                });
        showModalDialog('supportDocuments', true);
    }

</script>