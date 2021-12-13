<%@ page import="com.force5solutions.care.cc.TrainingTestStatus; com.force5solutions.care.*" %>

<div  class="contract_cert1">

    <div class="certitraning">
        Training Status<br/>

        <g:select
                name="trainingStatus"
                from="${TrainingTestStatus.values()*.toString()}"
                value="${workerCertification.trainingStatus?.status}"
                noSelection="['':'(Select One)']"
                onchange="updateTrainingDate(this)"
                class="listbox" style="width:130px;"/>
    </div>
    <div class="datecomplete">
        Last Status Date <br/>
        <input name="trainingDate" id="trainingDate" type="text" class="inp2" style="width:70px;" readonly
                value="${g.formatDate(format: 'MM/dd/yyyy', date: workerCertification.trainingStatus?.date)}" />

        <script type="text/javascript">
            var oldTrainingtDate=jQuery('input#trainingDate').val();
            var oldTrainingStatus=jQuery('select#trainingStatus').val();
            var newDate2="${g.formatDate(format: 'MM/dd/yyyy', date: new Date())}";
            function updateTrainingDate(x){
                if(oldTrainingStatus==x.value){
                    jQuery('input#trainingDate').attr('value',oldTrainingtDate);
                }else{
                    jQuery('input#trainingDate').attr('value',newDate2);
                }
            }
        </script>
        
    </div>
    <div class="certitraning">
        Training Provider<br/>
        <g:select
                name="trainingProvider"
                from="${workerCertification.certification.trainingProviders.split(',').sort{it.toLowerCase()}}"
                value="${workerCertification.trainingStatus?.provider}"
                noSelection="['':'(Select One)']"
                class="listbox" style="width:130px;"/>
    </div>
</div>
