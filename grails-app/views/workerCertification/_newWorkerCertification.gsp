<g:form name="addWorkerCertificationForm" controller="workerCertification" action="saveUpdateWorkerCertification"
        method="post" enctype="multipart/form-data">
    <div id="add_contractor_cert">
        <div id="head-add_contractor">Add Worker Certification</div>
        <div class="contractor_cert-dept">
            <div class="contract_cert">

                <div class="certicomplete" style="padding-left:10px;">
                    Certification<br/>
                    <g:hiddenField name="id" value="${worker?.id}"/>
                    <span>
                        <select class="auto-resize listbox" style="width:130px;"
                                onchange="updateNewCertificationFormWithUrl(this.value);"
                                name="certificationId" id="certificationId">
                            <option value="">(Select One)</option>
                            <g:each in="${certifications?.sort{it.toString().toLowerCase()}}" var="certification">
                                <g:if test="${params.certificationId && certification.id == params.certificationId?.toLong()}">
                                    <option selected="" value="${certification.id}" title="${certification.description}">${certification}</option>
                                </g:if>
                                <g:else>
                                    <option value="${certification.id}" title="${certification.description}">${certification}</option>
                                </g:else>
                            </g:each>
                        </select>
                    </span>
                </div>
                <g:if test="${workerCertification.certification?.id}">
                    <div class="datecomplete1">
                        Date Completed<br/>
                        <calendar:datePicker name="dateCompleted" id="dateCompleted"
                                value="${workerCertification?.dateCompleted}"/>
                    </div>
                </g:if>
                <g:else>
                    <div class="datecomplete1">
                        <br/>
                        <input type="text" class="inp2" style="visibility:hidden;width:70px;"/>
                    </div>
                </g:else>
            </div>
            <g:if test="${workerCertification.certification?.id}">
                <g:render template="/workerCertification/workerCertificationForm"
                        model="[workerCertification:workerCertification]"/>
            </g:if>
            <div class="department1">
                <g:if test="${workerCertification.certification?.id}">
                    <input type="submit" id="addWorkerCertificationSubmitButton" class="button" value="Submit"/>
                </g:if>
                <input type="button" class="button simplemodal-close" value="Cancel" onclick="jQuery.modal.close();"/>
            </div>

        </div>
        <div id="close-add_contractor">
            <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
        </div>
    </div>
</g:form>
<script type="text/javascript">
    jQuery('select option:selected').each(function(i) {
        jQuery(this).parent('select').attr('title', jQuery(this).text());
    });
    jQuery("select option").each(function(i) {
        if (!this.title) {
            this.title = this.text;
        }
    });
</script>

