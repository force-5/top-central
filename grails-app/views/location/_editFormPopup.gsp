<div class="form-section1" style="width:370px; margin:auto;">
    <g:formRemote name="editLocationForm" onSuccess="updateLocationTree(e)"
            method="post" url="${[controller:'location', action:'updateLocationWithId']}">
        <input type="hidden" name="id" value="${locationInstance?.id}"/>
        <div style="width:370px; height:250px; overflow:auto;padding-right:5px;">
            <g:render template="/location/editForm"
                    model="['locationInstance':locationInstance,
    'locationTypeInstance': locationTypeInstance,
    'remainingCertifications': remainingCertifications,
    'remainingSponsorCertifications': remainingSponsorCertifications,
    'sponsorCertifications': sponsorCertifications,
    'inheritedCertifications': inheritedCertifications,
    'inheritedSponsorCertifications': inheritedSponsorCertifications,
    'requiredCertifications': requiredCertifications]"/>
            <br/>
            <br/>
            <br/>
        </div>
        <div id="editButtons" style="text-align:center;">
            <input type="submit" class="button" value="Update"/>
            <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>
    </g:formRemote>
</div>
<script type="text/javascript">
    jQuery("div[title='edit_title']").text("Edit ${locationInstance?.name} (${locationTypeInstance?.type})");
</script>