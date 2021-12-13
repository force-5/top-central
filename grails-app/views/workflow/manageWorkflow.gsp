<%@ page import="groovy.time.TimeCategory" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Manage Workflow Screen</title></head>
<body>
<div class="body">
    <h1 class="headingWorkflow">Workflow Management</h1>
    <g:form action="terminateWorkflowTasks">
    <div class="clearfix workflowTabletop">
        <g:submitButton name="terminate" value="Terminate Selected" class="terminateBtn"/> |  &nbsp; <a class="clearCheckboxes" href="javascript:void(0);">Clear All Selections</a>
            <g:if test="${!isFiltered}">
        <a href="#" class="filterbutton" id="filterButton" style="float:right;"><span>Filter</span></a>
    </g:if>
        <g:else>
            <g:link action="showAllWorkflowTasks" style="float:right;" class="filterbutton"><span>Show All</span></g:link>
        </g:else>
    </div>
        <div class="list clearfix" style="background:#f5f5f5; border:1px solid #ccc;padding:5px 10px 10px;">
<table cellpadding="0" cellspacing="0" border="0" style="border:none">
                <thead >
                <tr>
                    <th width="20" height="25" class="workflowTableHead"></th>
                    <th width="200" class="workflowTableHead">Name</th>
                    <th width="150" class="workflowTableHead">SLID</th>
                    <th width="150" class="workflowTableHead">Workflow</th>
                    <th width="250" class="workflowTableHead">Node</th>
                    <th width="300" class="workflowTableHead">Time in Wait</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${taskList}" status="i" var="task">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td class="workflowTableleft" >
                            <g:if test="${task?.workflowType?.toString()?.startsWith('ACCESS') || task?.workflowType?.toString()?.startsWith('CONTRACTOR')}">
                                <g:checkBox value="${task?.workflowGuid}" name="selectedWorkflowGuids" checked="false"/>
                            </g:if>
                        </td>
                        <td>${task?.worker}</td>
                        <td>${task?.worker?.slid}</td>
                        <td>${task?.workflowType}</td>
                        <td>${task?.nodeName}</td>
                        <td>${TimeCategory.minus(new Date(), task?.lastUpdated)}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
       <div class="clearfix workflowTabletop">
        <g:submitButton name="terminate" value="Terminate Selected" class="terminateBtn"/> | &nbsp;  <a class="clearCheckboxes" href="javascript:void(0);">Clear All Selections</a>
    </div>
    </g:form>

    <div id="filterDialog" class="popupWindowContractorFilter clearfix">
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery(".clearCheckboxes").click(function() {
            jQuery('input[type="checkbox"]').removeAttr('checked');
        });
        jQuery("#filter").click(function() {
            jQuery('input[type="checkbox"]').removeAttr('checked');
        });
        jQuery('#filterButton').click(function() {
            jQuery.post("${createLink(action:'filterWorkflowDialog')}",
            { ajax: 'true'}, function(htmlText) {
                jQuery('#filterDialog').html(htmlText);
            });
            showModalDialog('filterDialog', true);
        });
    });
</script>
</body>
</html>