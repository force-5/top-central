<meta name="layout" content="someLayoutThatDoesNotExist"/>

<div class="popupWindowTitle">Filter Central Workflow Tasks</div>
<br/>
<g:form action="filterList">
    <div class="form-section">
        <div class="namerowbig"><span>Workflow Type</span>
            <g:select class="listbox" style="width:150px;" name="workflowType" from="${centralWorkflowTypeList}"
                      optionKey="name" optionValue="name" value="${filterVO?.workflowType}"
                      noSelection="['':'(Select One)']"/>

        </div>

        <div class="namerowbig"><span>Worker</span>
            <g:select class="listbox" style="width:170px;" name="workerId" from="${workers}" value="${filterVO?.workerId}"
                      optionKey="id" noSelection="['':'(Select One)']"/>

        </div>
    </div>

    <div class="form-section">

        <div class="namerowbig"><span>Entitlement Policy</span>
            <g:select class="listbox" name="entitlementPolicyId" style="width:120px;" from="${entitlementPolicyList}"
                      optionKey="id" optionValue="name" value="${filterVO?.entitlementPolicyId}"
                      noSelection="['':'(Select One)']"/>

        </div>

        <div class="namerowbig"><span>Security Role</span>
            <g:select class="listbox" name="securityRoleId" style="width:120px;" from="${securityRolesList}"
                      optionKey="id" optionValue="name" value="${filterVO?.securityRoleId}"
                      noSelection="['':'(Select One)']"/>

        </div>
    </div>

    <div style="clear:both;text-align:center;">
        <br/>
        <br/>
        <input type="submit" class="button" value="Filter"/>
        <input type="button" class="button" value="Close" onclick="jQuery.modal.close();"/>
    </div>
</g:form>
