<table class="tablesorter" id="tablesorter">
    <thead>
    <tr>
        <th>Workflow Type</th>
        <th>Date Created</th>
        <th>Worker</th>
        <th>Location</th>
        <th>Current Node</th>
        <th>Select</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${tasks}" status="i" var="task">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
            <td><g:link action="getUserResponse" id="${task.id}">${task?.workflowType}</g:link></td>
            <td><g:link action="getUserResponse"
                        id="${task.id}">${task?.dateCreated?.myDateTimeFormat()}</g:link></td>
            <td><g:link action="getUserResponse" id="${task.id}">${task?.worker}</g:link></td>
            <td><g:link action="getUserResponse"
                        id="${task.id}">${task?.entitlementRole?.toTreeString()}</g:link></td>
            <td><g:link action="getUserResponse" id="${task.id}">${task?.nodeName}</g:link></td>
            <td><g:checkBox name="taskIds" checked="false" value="${task?.id}"
                            class="${task.abbreviatedCodeForGroupResponse}"/></td>
        </tr>
    </g:each>
    </tbody>
</table>

<script type="text/javascript">
    jQuery(document).ready(function () {
        if (jQuery("#tablesorter tr").size() > 1) {
            jQuery("#tablesorter").tablesorter({textExtraction:myTextExtraction, sortList:[
                [1, 1]
            ], headers:{
                5:{
                    sorter:false
                }}});
        }
        jQuery('#groupResponse').submit(function () {
            if (jQuery(':checkbox:checked').length < 1) {
                alert("Please select any one check-box");
                return false;
            }
        });
        jQuery('#filterButton').click(function () {
            jQuery.post("${createLink(controller:'centralWorkflowTask', action:'filterDialog')}",
                    { ajax:'true'}, function (htmlText) {
                        jQuery('#filterDialog').html(htmlText);
                    });
            showModalDialog('filterDialog', true);
        });
        jQuery(':checkbox').change(function () {
            if (jQuery(':checked').length < 1) {
                jQuery(':checkbox').removeAttr('disabled');
            } else {
                jQuery(':checkbox').attr('disabled', 'true');
                jQuery("." + jQuery(this).attr("class")).removeAttr('disabled');
            }
        });
    });
</script>