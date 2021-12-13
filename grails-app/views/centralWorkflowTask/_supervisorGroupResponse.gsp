<%@ page import="com.force5solutions.care.common.CareConstants" %>
<div id="wrapper">
    <h1>TOP Workflow Supervisor Approval</h1>
    <br/>
    <div>
        <span>
            Hello <care:fullName slid="${session.loggedUser}"/>,
<br/>
<br/>
The following workers are asking to be added to the roles. This request requires your approval and business justification.
<br/>
<br/>
</span>
  </div>
<div style="margin:0 50px;font-size:14px;">
    <g:form action="sendGroupResponse" method="post" enctype="multipart/form-data">
        <g:render template="groupResponseTable" model="[tasks: tasks]"/>
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
            <input class="button" type="submit" value="Submit">
        </div>
    </g:form>
</div>
<script type="text/javascript">
    jQuery(function () {
        jQuery('#actionDate').datetimepicker({
            ampm:true
        });
        jQuery('.cannedSelectBox').focus();
        jQuery('form').submit(function () {
            if (jQuery(':checkbox:checked').length < 1) {
                alert('Please select any one check-box');
                return false;
            } else {
                return isStatusChangeCommentEmpty('accessJustification', 'explanation-error')
            }
        });
        if (jQuery("#tablesorter tr").size() > 1) {
            jQuery("#tablesorter").tablesorter({textExtraction:myTextExtraction, sortList:[
                [1, 1]
            ], headers:{
                5:{
                    sorter:false
                }}});
        }
    });
</script>
