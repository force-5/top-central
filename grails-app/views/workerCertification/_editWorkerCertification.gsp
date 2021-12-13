<g:form id="formEditContractor"
        controller="workerCertification" action="saveUpdateWorkerCertification"
        id="${worker?.id}" method="post" enctype="multipart/form-data">
    <g:hiddenField name="workerCertificationId" value="${workerCertification?.id}"/>
    <g:hiddenField name="certificationId" value="${workerCertification.certification?.id}"/>
    <div id="add_contractor_cert">
        <div id="head-add_contractor">
    <g:if test="${workerCertification?.id}">
        Edit Worker Certification
    </g:if>
    <g:else>
        Renew ${workerCertification?.certification}
    </g:else>
    </div>
<div class="contractor_cert-dept clearfix" >
<div class="contract_cert clearfix">

<div class="certicomplete">
    Certification<br/>
    <h1>${workerCertification.certification}</h1>
                </div>
    <div class="datecomplete2">
        Date Completed<br/>
        <calendar:datePicker name="dateCompleted" id="dateCompleted"
                value="${workerCertification?.dateCompleted}"/>

    </div>
    </div>
    <g:render template="/workerCertification/workerCertificationForm"
            model="[workerCertification:workerCertification]"/>
    <g:if test="${!workerCertification.id}">
        <div class="department6">
            <input type="submit" class="button" style="float:left;" value="Submit"/>
            <input type="button" class="button simplemodal-close" style="float:left;" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>
    </g:if>
    <g:else>
        <div class="department5">
            <g:remoteLink value="Show History" class="buttona2"
                    controller="workerCertification" action="workerCertificationHistory"
                    id="${worker?.id}" params="[workerCertificationId:workerCertification?.id]"
                    onSuccess="showCertificationHistoryPopup(e,'certification-history-popup' );"><span>Show&nbsp;History</span></g:remoteLink>

            <div>
                <input type="submit" class="button" style="float:left;" value="Submit"/>
            </div>
            <input type="button" class="button simplemodal-close" style="float:left;" value="Cancel" onclick="jQuery.modal.close();"/>
        </div>
    </g:else>

</g:form>
</div>

<div id="close-add_contractor">
    <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
</div>
</div>
<script type="text/javascript">
    jQuery('select option:selected').each(function(i) {
        jQuery(this).parent('select').attr('title', jQuery(this).text());
    });
    jQuery("select option").each(function(i) {
        this.title = this.text;
    });
</script>
