<div id="terminateForCause" class="popupWindow clearfix" style="text-align:center;">
    <g:form name="terminateForCauseForm" controller="workerEntitlementRole" action="terminateForCause" method="post" enctype="multipart/form-data">
        <g:hiddenField name="id" value="${worker?.id}"/>
        <h3>Warning</h3>
        <br/>
        <div align="justify" class="clearfix">
            Are you sure you wish to set the Personnel Access to Terminated For Cause?
            This action cannot be reversed. It will set the Access Status for all the
            Personnel Entitlement Roles to Terminated For Cause.
            Please check "I understand" and click on OK if you wish to proceed.
        </div>
        <br/>
        <div style="text-align:center;" class="clearfix">
            <strong>Comment :</strong><span class="asterisk">*</span>
            <textarea name="terminateForCauseWorkerRoleHistoryItem" id="terminateForCauseWorkerRoleHistoryItem" class="area deprt1" style="width:180px; height:50px;"></textarea>
            <g:render template="attachment" model="[containerDivId:'terminateForCauseAttachments']"/>
        </div>
        <div id="iagreediv" style="text-align:center; padding-bottom:10px;">
            <input type="checkbox" id="iagree"/>
            <strong>I understand</strong>
        </div>
        <div class="clearfix">
            <input type="submit" class="button" id="terminateForCauseSubmitButton" name="okButton" value="OK" onclick="return validateTerminateForCauseForm()">&nbsp;&nbsp;
            <input type="button" class="button" name="cancelButton" value="Cancel" onclick="jQuery.modal.close();">
        </div>
    </g:form>

</div>
