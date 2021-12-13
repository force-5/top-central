<%@ page import="com.force5solutions.care.common.CareConstants" %>
<div id="wrapper">
    <h1>TOP Workflow Supervisor Approval</h1>
    <br/>

    <div>
        <span>
            Hello <care:fullName slid="${session.loggedUser}"/>,
            <br/>
            <br/>
            <b>${task.worker.toString()}</b> is asking to be added to the following <b>${task.workerEntitlementRole}</b>. This request requires your approval and business justification.
            <br/>
            <br/>
        </span>
    </div>

    <div style="margin:0 50px;font-size:14px;">
        <g:form action="sendUserResponse" method="post" enctype="multipart/form-data">
            <div style="font-size:14px;">
                <g:if test="${task.actions}">
                    Select Action &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
                        class="asterisk">*</span>
                    <span><g:select class="listbox" style="padding:0;width:150px;" name="userAction"
                                    from="${task.actions}"/></span>
                </g:if>
            </div>
            <br/><br/>

            <div id="explanation-error" class="error-status" style="text-align:center; display:none;">
                <span>Business Justification can not be left blank.</span></div>

            <div>
                <span>Select Business Justification:</span><span class="asterisk">*</span> <care:cannedResponse
                    taskDescription="${CareConstants.CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_SUPERVISOR_APPROVAL}"
                    targetId="accessJustification"/><br>
                <span>Enter Business Justification:</span><span class="asterisk">*</span>
                <g:render template="accessJustificationMarginLeft15"/>
                <input type="hidden" name="id" value="${task.id}"/>
                <g:render template="/centralWorkflowTask/attachment"/>
            </div>
            <script type="text/javascript">
            </script>

            <div style="text-align:center;">
                <input class="button" type="submit" value="Submit"/>
            </div>
        </g:form>
    </div>

    <h1>Worker Information</h1>
    <table>
        <tr><td>Name</td> <td>${task.worker.toString()}</td></tr>
        <tr><td>SLID</td> <td>${task.worker.slid}</td></tr>
        <tr><td>Worker #</td> <td>${task.worker.workerNumber}</td></tr>
        <tr><td>Phone</td> <td>${task.worker.phone}</td></tr>
    </table>

    <h1>Worker Supervisor Information</h1>
    <table>
        <tr><td>Name</td> <td>${task?.worker?.supervisor?.toString()}</td></tr>
        <tr><td>SLID</td> <td>${task?.worker?.supervisor?.slid}</td></tr>
        <tr><td>Phone</td> <td>${task?.worker?.supervisor?.phone}</td></tr>
    </table>
    <br/>

    <h1>Certification Information</h1>

    <div class="in-panel">
        <g:render template="/workerCertification/certificationTableForWorkflow"
                  model="[workerCertifications: task.worker.currentCertifications, worker: task.worker]"/>
    </div>

    <div id="missingCertification_div">
        <care:missingCertificationsForWorkflow worker="${task.worker}"/>
    </div>

    <h1>Comments</h1><br/>
    <g:render template="taskHistoryTable" model="[workflowVOs: workflowVOs]"/><br/>
</div>
<script type="text/javascript">
    jQuery(document).ready(function () {
        jQuery('#actionDate').datetimepicker({
            ampm: true
        });
        jQuery('.cannedSelectBox').focus();
        jQuery('form').submit(function () {
            return isStatusChangeCommentEmpty('accessJustification', 'explanation-error')
        });
    });
</script>