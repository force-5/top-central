<div class="body">
    <h1>Workflow table for GUID - ${workflowGUID} (${workflowType})</h1>
    <div class="list">
        <table>
            <thead>
            <tr>

                <th>Date</th>
                <th>System</th>
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
                    <td>${workflowVO?.system}</td>
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
    <div id="filterDialog" class="popupWindowContractorFilter"></div>
</div>
<script type="text/javascript">
    function documentPopUp(system, taskId, message) {
        jQuery.post("${createLink(controller:'worker', action:'workflowTaskDocuments')}",
        { ajax: 'true', system: system, taskId:taskId, message: message}, function(htmlText) {
            jQuery('#filterDialog').html(htmlText);
        });
        showModalDialog('filterDialog', true);
    }
</script>