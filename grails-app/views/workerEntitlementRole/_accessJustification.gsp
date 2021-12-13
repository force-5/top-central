<%@ page import="com.force5solutions.care.common.CareConstants" %>
<div id="change-status-error2" class="error-status" style="margin-top:0; padding-top:0; display:none;"><span>Access Justification can not be left blank.</span></div>
<form id="accessRequestForm" name="accessRequestForm" enctype="multipart/form-data" method="post"
        action="${createLink(controller: 'workerEntitlementRole', action: 'addEntitlementRoleAccess')}"
        onsubmit="jQuery('#accessRequestFormDiv').hide(); jQuery('#accessRequestWaitPopup').show(); ">
    <g:hiddenField name="workerId" value="${worker?.id}"/>
    <g:hiddenField name="entitlementRoleId" value=""/>

    <div id="accessRequestFormDiv">
    <div style="padding-left:20px">
    <strong>Select Justification</strong> <span class="asterisk"> *</span> <care:cannedResponse taskDescription="${CareConstants.CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_JUSTIFICATION}" targetId="accessJustification"/><br>
    <strong>Access Justification</strong><span class="asterisk">*</span>
        <textarea name="accessJustification" id="accessJustification" class="area deprt1" style="width:180px; height:50px;"></textarea>
        <g:render template="/workerEntitlementRole/attachment" model="[containerDivId:'accessRequestAttachments']"/>
    </div>

    <div style="text-align:center;">
        <br>
        <input type="submit" class="button" id="accessJustificationButton" name="accessJustificationButton" value="OK"
                onclick="if(isWorkerRoleHistoryItemEmpty('accessJustification', 'change-status-error2')){return false}">&nbsp;&nbsp;
        <input type="button" class="button" name="cancelButton" value="Cancel" onclick="hideAccessJustificationWorkerRoleHistoryItem();">
    </div>
    <div id="crossImageUrl" style="display:none;">${resource(dir:'images', file:'tickmark.jpg')}</div>
</div>
    <div id="accessRequestWaitPopup" style="display:none; text-align:center;"><h2>Please wait...</h2></div>
</form>

<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('#accessRequestForm').ajaxForm({success: writeOutput});
    });

    function writeOutput(data) {
        jQuery('#accessRequestWaitPopup').hide();
        jQuery('#accessRequestFormDiv').show();
        var imgUrl = jQuery('#crossImageUrl').text();
        postLocationAdded(data,imgUrl);
    }
</script>
