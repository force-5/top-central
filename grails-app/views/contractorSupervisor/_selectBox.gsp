<option class="vendor-2" value="-2">(Select One)</option>
<option class="vendor-1" value="-1">(Add one)</option>
<g:each in="${supervisors.sort{it?.toString()?.toLowerCase()}}" var="supervisor">
    <g:if test="${supervisor.id == selectedSupervisorId}">
        <option class="vendor${supervisor.vendor?.id}" selected="" value="${supervisor.id}">${supervisor}</option>
    </g:if>
    <g:else>
        <option class="vendor${supervisor.vendor?.id}" value="${supervisor.id}">${supervisor}</option>
    </g:else>
</g:each>