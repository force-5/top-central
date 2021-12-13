<%@ page import="com.force5solutions.care.*" %>

<div class="error-status" style="">
    <g:hasErrors>
        <g:renderErrors bean="${locationInstance}"/>
    </g:hasErrors>
</div>

<input type="hidden" name="parent" value="${locationInstance?.parent}"/>
<input type="hidden" name="locationType" value="${locationInstance?.locationType}"/>

<div class="namerowbig2 clearfix"><span>Name</span>
    <label style="width:210px;" class="value ${hasErrors(bean: locationInstance, field: 'name', 'errors')}">
        <input type="text" class="inp"  id="name" name="name"
                value="${fieldValue(bean: locationInstance, field: 'name')}"/>
    </label>
</div>

<div class="clearfix"/>

<div class="clearfix"><span style="float:left;">Required Certifications</span>
    <span style="float:right;width:280px">
        <ui:multiSelect
                name="requiredCertifications"
                from="${remainingCertifications?.sort{it.toString().toLowerCase()}}"
                value="${requiredCertifications?.sort{it.toString().toLowerCase()}}"
                noSelection="['':'(Select One)']"
                class="listbox1 required-certi-multi-select auto-resize"/>
    </span>
</div>
<div  class="clearfix"><span style="float:left;">Required Sponsor Certifications</span>
    <span style="float:right;">
        <ui:multiSelect
                name="sponsorCertifications"
                from="${remainingSponsorCertifications?.sort{it.toString().toLowerCase()}}"
                value="${sponsorCertifications?.sort{it.toString().toLowerCase()}}"
                noSelection="['':'(Select One)']"
                class="listbox1 required-certi-multi-select auto-resize"/>
    </span>
</div>

<div class="clearfix"><span style="float:left;">Inherited Certifications</span></div>

<div  class="clearfix" style="padding-top:20px;text-align:left;">
    <strong>${inheritedCertifications ? (inheritedCertifications?.sort()*.toString().join(', ')) : '(No Certifications inherited)'}</strong>
</div>
<br/>
<div  class="clearfix"><span style="float:left;">Inherited Sponsor Certifications</span></div>
<div  class="clearfix" style="padding-top:20px;text-align:left;">
    <strong>${inheritedSponsorCertifications ? (inheritedSponsorCertifications?.sort()*.toString().join(', ')) : '(No Certifications inherited)'}</strong>
</div>
<br/>
<div class="namerowbig2 clearfix"><span>Notes</span>
    <textarea name="notes" class="area" style="width:237px;height:50px;padding-right:5px;" rows="3">${fieldValue(bean: locationInstance, field: 'notes')}</textarea>
</div>


<script type="text/javascript">
    jQuery(document).ready(function() {
    <g:if test="${!locationTypeInstance?.isEditable}">
        jQuery('input#name').attr("value", "${locationTypeInstance?.type}");
        jQuery('input#name').attr("readonly", "true");
    </g:if>
        jQuery('input:text:first:visible').focus();
    });
</script>