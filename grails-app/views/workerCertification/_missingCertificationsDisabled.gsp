<g:if test="${missingCertifications.size()>0}">
        <div id="miss1" style="margin:0;">
            <div id="miss">
                <span>Missing/Expired Certifications</span>
            </div>
        </div>
        <div id="background" style="margin:0;">
            <div id="soc">
                <g:each in="${missingCertifications}" var="missingCertification" status="i">
                       ${missingCertification}
                    <g:if test="${i < missingCertifications.size() -1}">&nbsp;|</g:if>
                </g:each>
            </div>
            <div id="background-bot"></div>
        </div>
</g:if>
<div id="certification-history-popup" style="display:none;">
</div>
