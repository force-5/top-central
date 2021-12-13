<%@ page import="com.force5solutions.care.common.CareConstants" %>
<div class="roundCorner" style=" width:500px;">
    <div class="dotBorderTop"><strong>Revoke Acccess</strong></div>
    <div class="dotBorderRight clearfix" id="revokeJustificationDialog">
        <g:form name="revokeJustificationForm" controller="workerEntitlementRole" action="${isAccessWorkflowCancelled ? 'cancelAccessApprovalRequest' :'revokeAccess'}" method="post"
                enctype="multipart/form-data" onsubmit="jQuery('#revokeJustificationDialog').hide(); jQuery('#waitPopup').show();">
            <g:hiddenField name="id" value="${workerEntitlementRole?.id}"/>
            <ul class="containerTuple">
                <li><span class="left">Revocation Type: <span class="asterisk">*</span></span><span style="height:20px;" id='dateRadioButtons'><input id="ckb24h" type="radio" name="revocationType" value="24H"/> 24 Hours &nbsp; <input type="radio" name="revocationType" value="7D" id="ckb7d"/> 7 Days</span></li>
                <li><span class="left">Effective Date: <span class="asterisk">*</span></span><calendar:datePicker style="float:left" name="effectiveDate" id="effectiveDate"/></li>
                <li><span class="left">Effective Time (24 Hour): <span class="asterisk">*</span></span>Hours: <select name="selected_hour" id="selected_hour"><g:each in="${0..23}" var="hour"><option value="${hour}">${hour}</option></g:each></select> &nbsp; Minutes: <select name="selected_min" id="selected_min"><g:each in="${0..59}" var="min"><option value="${min}">${min}</option></g:each></select></li>
                <li><div><span class="left">Select Justification: <span class="asterisk">*</span></span><care:cannedResponse taskDescription="${CareConstants.CANNED_RESPONSE_CENTRAL_REVOKE_REQUEST_JUSTIFICATION}" targetId="revokeJustification"/><br>
                    <span class="left">Revoke Justification: <span class="asterisk">*</span></span><textarea name="revokeJustification" id="revokeJustification" class="area deprt1" style="width:280px; height:80px;"></textarea></div></li>
            </ul>

            <g:render template="attachment" model="[containerDivId:'revokeJustificationAttachments']"/>
            <div class="cleafix">
                <div style="text-align:center;">
                    <input type="submit" class="button" id="terminateForCauseSubmitButton" name="okButton" value="OK" onclick="return validateRevokeForm('ckb24h', 'ckb7d', 'effectiveDate', 'revokeJustification', 'change-status-error2')">&nbsp;&nbsp;
                    <input type="button" class="button" name="cancelButton" value="Cancel" onclick="jQuery.modal.close();">
                </div>
            </div>
        </g:form>
    </div>
    <div id="waitPopup" class="dotBorderRight clearfix" style="display:none; text-align:center;"><h2>Please wait...</h2></div>
    <div class="dotBorderBot"><label></label><span></span></div>
</div>

