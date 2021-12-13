<div id="add_contractor_cert" style="">
    <div id="head-add_contractor">Create Business Unit Requester</div>
    <div class="contractor_cert-dept">
        <div style="margin:0 50px;">
            <g:render template="/businessUnitRequester/createBURForm" model="[instance:instance, fail: fail]"/>
        </div>
    </div>
</div>
<div id="close-add_contractor"><img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/></div>
<script type="text/javascript">
    initializePopup('note_businessUnitRequester');
</script>