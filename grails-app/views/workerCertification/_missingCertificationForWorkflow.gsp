<g:if test="${missingCertifications.size()>0}">
    <h1>Missing / Expired Certification Information</h1>
    <table>
        <div>
            <g:each in="${missingCertifications}" var="missingCertification" status="i">
                <tr><td>${missingCertification}</td></tr>
            </g:each>
        </div>
    </table>
</g:if>

