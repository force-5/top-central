<div id="add_contractor_cert" style="">
    <div id="head-add_contractor">Create Supervisor</div>
    <div class="contractor_cert-dept">
        <div style="margin:0 50px;">
            <g:render template="/contractorSupervisor/createSupervisorForm" model="[supervisor:supervisor, vendors: vendors]"/>
        </div>
    </div>
</div>
<div id="close-add_contractor"><img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/></div>

<script type="text/javascript">
    initializePopup('note_supervisor');
</script>