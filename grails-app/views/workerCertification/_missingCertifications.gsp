<g:if test="${missingCertifications.size()>0}">
    <div id="miss1" style="margin:0;">
        <div id="miss">
            <span>Missing/Expired Certifications</span>
        </div>
    </div>
    <div id="background" style="margin:0;">
        <div id="soc">
            <g:each in="${missingCertifications}" var="missingCertification" status="i">
                <g:set var="wc" value="${worker.getRecentWorkerCertificationByCertificationId(missingCertification.id)}"/>
                <g:if test="${wc && !wc.dateCompleted}">
                    <g:remoteLink controller="workerCertification"
                            action="editWorkerCertification"
                            id="${worker?.id}"
                            params="[workerCertificationId:worker.getRecentWorkerCertificationByCertificationId(missingCertification.id)?.id]"
                            onSuccess="editWorkerCertification(e)">
                        ${missingCertification}
                    </g:remoteLink>
                </g:if>
                <g:else>
                    <a href="#" onclick='updateNewCertificationFormWithUrl("${missingCertification.id}"); return false;'>
                        ${missingCertification}
                    </a>
                </g:else>
                <g:if test="${i < missingCertifications.size() -1}">&nbsp;|</g:if>
            </g:each>
        </div>
        <div id="background-bot"></div>
    </div>
</g:if>
<div id="editWorkerCertificationDiv">
</div>
<div id="certification-history-popup" style="display:none;">
</div>

<script type="text/javascript">
    function updateNewCertificationFormWithUrl(cid) {
        jQuery.get("${createLink(controller:'workerCertification', action:'newWorkerCertification', id:worker.id)}",
        {certificationId:cid,ajax:true},
                function(editPageValue) {
                    jQuery('#editWorkerCertificationDiv').empty().html(editPageValue);
                    showModalDialog('editWorkerCertificationDiv', true);
                });
    }
</script>

