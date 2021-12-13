<div id="add_contractor_cert" style="">
    <div id="head-add_contractor">Create Vendor</div>
    <div class="contractor_cert-dept">
        <div style="margin:0 30px;">
            <g:render template="/vendor/createVendorForm" model="[instance:instance, stateMap:stateMap]"/>
        </div>
    </div>
</div>
<div id="close-add_contractor"><img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/></div>

<script type="text/javascript">
    initializePopup('note_vendor');
</script>