<div id="filterCcGatekeeperCertification" class="popupWindow" style="text-align:center;width:250px;">
    <div class="popupWindowTitleForAccessFilter">Filter Entitlement Roles</div>

    <g:form controller="workerEntitlementRole" action="access">
        <g:hiddenField name="id" value="${worker?.id}"/>
        <br/>
        <div class="popupWindowTextForAccessFilter"  style="clear:both;">
        <span style="float:left; display:block;">Certifications</span>
      <span style="float:right; width:150px;">
      <g:select width="150px" name="filterByCertification" from="${certifications}"
                    optionKey="id" value="${filterByCertification}"
                    noSelection="['':'(Select One)']" style="width:150px;" class="listbox"/>
            </span>

        </div>
        <br/><br/>
        <input type="submit" class="button" name="okButton" value="OK">&nbsp;&nbsp;
        <input type="button" class="button" value="Cancel" onclick="jQuery.modal.close();"/>
    </g:form>
</div>
