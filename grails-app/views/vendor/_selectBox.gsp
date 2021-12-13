<option value="-2">(Select One)</option>
<option value="-1">(Add one)</option>
<g:each in="${vendors?.sort{it?.toString()?.toLowerCase()}}" var="vendor">
    <g:if test="${vendor.id == selectedVendorId}">
        <option selected="" value="${vendor.id}">${vendor}</option>
    </g:if>
    <g:else>
        <option value="${vendor.id}">${vendor}</option>
    </g:else>
</g:each>