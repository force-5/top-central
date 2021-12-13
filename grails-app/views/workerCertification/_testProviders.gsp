<%@ page import="com.force5solutions.care.cc.TrainingTestStatus; com.force5solutions.care.*" %>

<div  class="contract_cert1">
    <div class="certitraning">
        Test Status<br/>
        <g:select
                name="testStatus"
                from="${TrainingTestStatus.values()*.toString()}"
                value="${workerCertification.testStatus?.status}"
                noSelection="['':'(Select One)']"
                onchange="updateTestDate(this)"
                class="listbox" style="width:130px;"/>

    </div>
    <div class="datecomplete">
        Last Status Date <br/>

        <input name="testDate" id="testDate" type="text" class="inp2" style="width:70px;" readonly
                value="${g.formatDate(format: 'MM/dd/yyyy', date: workerCertification.testStatus?.date)}" />

        <script type="text/javascript">
            var oldTestDate=jQuery('input#testDate').val();
            var oldTestStatus=jQuery('select#testStatus').val();
            var newDate1="${g.formatDate(format: 'MM/dd/yyyy', date: new Date())}";
            function updateTestDate(x){
                if(oldTestStatus==x.value){
                    jQuery('input#testDate').attr('value',oldTestDate);
                }else{
                    jQuery('input#testDate').attr('value',newDate1);
                }
            }
        </script>

    </div>
    <div class="certitraning">
        Test Provider<br/>
        <g:select
                name="testProvider"
                from="${workerCertification.certification.testProviders.split(',').sort{it.toLowerCase()}}"
                value="${workerCertification.testStatus?.provider}"
                noSelection="['':'(Select One)']"
                class="listbox" style="width:130px;"/>
    </div>
</div>
