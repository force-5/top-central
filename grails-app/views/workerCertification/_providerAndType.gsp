<g:if test="${workerCertification.certification.providerRequired ||workerCertification.certification.subTypeRequired }">
    <div class="contract_cert clearfix">
        <g:if test="${workerCertification.certification.providerRequired}">
            <div class="certicomplete4">
                Provider<br/>
                <g:select
                        name="provider"
                        from="${workerCertification.certification.providers.split(',').sort{it.toLowerCase()}}"
                        value="${workerCertification.provider}"
                        noSelection="['':'(Select One)']"
                        class="listbox" style="width:130px;"/>
            </div>
        </g:if>
        <g:if test="${workerCertification.certification.subTypeRequired}">
            <div class="certitraning">
                Type<br/>

                <g:select
                        name="subType"
                        from="${workerCertification.certification.subTypes.split(',').sort{it.toLowerCase()}}"
                        value="${workerCertification.subType}"
                        noSelection="['':'(Select One)']"
                        class="listbox" style="width:130px;"/>
            </div>
        </g:if>
    </div>
</g:if>