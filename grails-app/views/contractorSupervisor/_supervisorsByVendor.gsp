<g:each in="${supervisors.sort{it?.toString()?.toLowerCase()}}" var="supervisor">
    <option class="vendor${supervisor.vendor?.id}" value="${supervisor.id}">${supervisor}</option>
</g:each>
