<div id="add_contractor_cert">
    <div id="head-add_contractor" title="add_title">Add Entitlement Role</div>
    <div class="contractor_cert-dept1 clearfix">
        <div id='container_id' style="display:block;">
            <g:if test="${worker}">
                <care:locationTree worker="${worker}"/>
            </g:if>
        </div>
        <div id="workerRoleHistoryItemArea" style="display:none;">
            <g:render template="/workerEntitlementRole/accessJustification" model="['worker':worker]"/>
        </div>
        <div id="close-button" class="department1">
            <g:if test="${!closeWithoutPageRefresh}">
                <input type="button" class="button"  id="addLocationButton" value="Close" onclick="jQuery.modal.close(); location.reload();"/>
            </g:if>
            <g:else>
                <input type="button" class="button" value="Close" onclick="jQuery.modal.close();"/>
            </g:else>
        </div>

    </div>
    <div id="close-add_contractor">
        <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
    </div>
</div>