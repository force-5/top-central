<div id="supervisorList" class="namerow">
    <span>Supervisor</span><span class="asterisk">*</span>
    <span style="${hasErrors(bean: worker, field: 'supervisor', '#border: 1px solid red;')}float:right;">
        <select class="auto-resize listbox ${hasErrors(bean: worker, field: 'supervisor', 'errors')}"
                style="width:125px;" name="supervisor" id="supervisor">
            <option value="-2">(Select One)</option>
            <g:each in="${supervisors}" var="supervisor">
                <g:if test="${supervisor.id==worker?.supervisor?.toLong()}">
                    <option selected value="${supervisor.id}">${supervisor}</option>
                </g:if>
                <g:else>
                    <option value="${supervisor.id}">${supervisor}</option>
                </g:else>
            </g:each>
        </select>
    </span>
</div>