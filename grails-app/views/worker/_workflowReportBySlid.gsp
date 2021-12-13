<%@ page import="com.force5solutions.care.cc.Employee" %>
<div class="body">
    <g:if test="${worker instanceof Employee}">
        <h1>Workflows for ${worker.name} - (Employee SLID: ${slid})</h1>
    </g:if>
    <g:else>
        <h1>Workflows for ${worker.name} - (Contractor # : ${worker?.workerNumber})</h1>
    </g:else>

    <div class="list">
        <table>
            <thead>
            <tr>
                <th>Entitlement Role</th>
                <th>Workflow Type</th>
                <th>Date Created</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${selectedCentralWorkflowTasks}" status="i" var="task">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td><g:link action="workflowReport" id="${task?.workflowGuid}" params="[workerId: task?.worker?.id]">${task?.entitlementRole}</g:link></td>
                    <td><g:link action="workflowReport" id="${task?.workflowGuid}" params="[workerId: task?.worker?.id]">${task?.workflowType}</g:link></td>
                    <td><g:link action="workflowReport" id="${task?.workflowGuid}" params="[workerId: task?.worker?.id]">${task?.dateCreated.myDateTimeFormat()} (<prettytime:display date="${task?.dateCreated}"/>)</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
