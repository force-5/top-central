<div class="form-section1" style="width:370px; margin:auto;">
    <g:formRemote name="createLocationForm" onSuccess="saveLocation(e)"
            method="post" url="${[controller:'location', action:'saveLocationWithId']}">
        <div style="width:370px; height:250px; overflow:auto; padding-right:5px;">

            <g:render template="/location/editForm"
                    model="['locationInstance':locationInstance,
    'locationTypeInstance': locationTypeInstance,
    'remainingCertifications': remainingCertifications,
    'inheritedCertifications': inheritedCertifications,
    'requiredCertifications': requiredCertifications]"/>
            <br/>
            <br/>
            <br/>
        </div>
        <div id="editButtons" style="text-align:center;">
            <input type="submit" class="button" id="newCompanyCreation" value="Save"/>
            <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>
    </g:formRemote>
</div>
<script type="text/javascript">
    jQuery("div[title='create_title']").text("Create New ${locationTypeInstance?.type}");
</script>