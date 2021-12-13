<%@ page import="com.force5solutions.care.common.CustomPropertyType; java.text.SimpleDateFormat;" %>
<div id="tabcontent">
    <ul>
        <g:each in="${customPropertyValues}" var="customPropertyValue">
            <input type="hidden" name="customPropertyId" value="${customPropertyValue.customProperty.id}">
            <li>
                <label>${customPropertyValue?.customProperty?.name}
                    <g:if test="${customPropertyValue?.customProperty?.isRequired}"><span class="asterisk" style="float:none; margin-left:0px;">*</span></g:if>
                </label>
                <g:if test="${customPropertyValue?.customProperty?.propertyType==CustomPropertyType.STRING}">
                    <span><input type="text" size="48" maxlength="${customPropertyValue?.customProperty?.size}" name="customPropertyValue${customPropertyValue?.customProperty?.id}" value="${customPropertyValue?.value}"
                            id="custom-property-textbox-${customPropertyValue.customProperty.id}">
                        <g:if test="${customPropertyValue?.customProperty?.size}">(Max ${customPropertyValue?.customProperty?.size} Characters)</g:if>
                    </span>
                </g:if>
                <g:if test="${customPropertyValue?.customProperty?.propertyType==CustomPropertyType.NUMBER}">
                    <span><input type="text" size="48" name="customPropertyValue${customPropertyValue?.customProperty?.id}" value="${customPropertyValue?.value}"
                            id="custom-property-textbox-${customPropertyValue.customProperty.id}">
                        <g:if test="${customPropertyValue?.customProperty?.size}">(Max Value : ${customPropertyValue?.customProperty?.size} )</g:if>
                    </span>
                </g:if>
                <g:if test="${customPropertyValue?.customProperty?.propertyType==CustomPropertyType.DATE}">
                    <span id="custom-property-textbox-${customPropertyValue.customProperty.id}" style="width:303px;margin-left:6px;">
                        <calendar:datePicker name="customPropertyValue${customPropertyValue?.customProperty?.id}"
                                value="${customPropertyValue.value ? new Date(customPropertyValue.value): null}"/></span>
                </g:if>
                <g:if test="${customPropertyValue?.customProperty?.propertyType==CustomPropertyType.BOOLEAN}">
                    <span>
                        <g:checkBox name="customPropertyValue${customPropertyValue?.customProperty?.id}" value="${customPropertyValue?.customProperty?.isRequired?'1':'0'}"/>
                    </span>
                </g:if>
            </li>
        </g:each>
    </ul>
</div>
