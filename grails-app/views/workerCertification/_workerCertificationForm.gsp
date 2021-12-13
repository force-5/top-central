<g:render template="/workerCertification/providerAndType"
        model="[workerCertification:workerCertification]"/>
<g:if test="${workerCertification.certification.testRequired || workerCertification.certification.trainingRequired}">
    <div class="contract_cert clearfix">
        <g:if test="${workerCertification.certification.trainingRequired}">
            <g:render template="/workerCertification/trainingProviders"
                    model="[workerCertification:workerCertification]"/>
        </g:if>
        <g:if test="${workerCertification.certification.testRequired}">
            <g:render template="/workerCertification/testProviders"
                    model="[workerCertification:workerCertification]"/>
        </g:if>
    </div>
</g:if>
<g:if test="${workerCertification.certification.affidavitRequired}">
    <g:render template="/workerCertification/affidavit"
            model="[workerCertification:workerCertification]"/>
</g:if>
