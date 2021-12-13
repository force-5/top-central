<div id="custom-property-${customProperty?.name}">
    <strong>
        <a href="#" onclick="editCustomPropertyValue('custom-property-${customProperty?.name}',
                '${customProperty?.name}',
                '${customProperty?.propertyType}',
                '${customProperty?.isRequired}',
                '${customProperty?.id}', '${customProperty?.size}', '${customProperty?.name}')">
            ${customProperty}</a>
    </strong>
    &nbsp;&nbsp;<a onclick="removeCustomProperty(this);">
    <img src="${createLinkTo(dir: 'images', file: ('cross.gif'))}"/>
</a><br/>
    <input type="hidden" value="${customProperty?.name}" name="propertyName"/>
    <input type="hidden" value="${customProperty?.propertyType}" name="customPropertyType"/>
    <input type="hidden" value="${customProperty?.isRequired ? 1 : 0}" name="isCustomPropertyRequired"/>
    <input type="hidden" value="${customProperty?.size}" name="customPropertySize"/>
</div>
