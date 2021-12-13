<div style="width:550px;">
    <div id="popup-head550" style="line-height:20px; height:55px;">Worker Entitlement Role History</div>
    <div class="popup-bg550">
        <div id="alert-popup">
            <div style="width:540px;padding-left:5px;">
                <g:render template="/workerEntitlementRole/workerRoleHistoryItems" model="[workflowVOs: workflowVOs]"/>
                <div class="department3">
                    <input type="button" class="button" value="Close" onclick="jQuery.modal.close();"/>
                </div>
            </div>

        </div>
    </div>
    <div id="close-add_contractor"><img src="${createLinkTo(dir: 'images', file: 'popup-btm-550.gif')}"/></div>
</div>
