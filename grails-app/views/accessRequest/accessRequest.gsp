<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Access Request</title>
</head>
<body>
<br>
<div id="wrapper">
</div>
<div id="right-panel">
    <div class="care-request-container">
        <div class="header-text">Employee Self Serve Access Request</div>
        <div id="access-request-error" class="error-status" style="text-align:center;display:none;">
            <span style="color:red; background:#FFFFFF;">Please enter valid values in the fields</span>
        </div>
            <ul>
                <li class="li-name">Employee #<br/><span class="li-text2">or</span></li>
                <li class="li-input"><input type="text" id="workerNumber" name="workerNumber"/></li>
                <li class="clr"></li>
            </ul>

            <ul>
                <li class="li-name">Employee SLID</li>
                <li class="li-input"><input type="text" id="employeeSlid" name="employeeSlid"/></li>
                <li class="clr"></li>
            </ul>

            <ul>
                <li class="li-name">Business Unit Requester SLID</li>
                <li class="li-input"><input type="text" name="businessUnitRequesterSlid"/></li>
                <li class="clr"></li>
            </ul>
            <br/>
            <ul class="location-con" id="selectedLocationsList">
            </ul>
            <div style="text-align:center; clear:both;">
                <input type="submit" class="button" value="Submit" id="requestSubmitMainButton" onclick="jQuery(this).attr('disabled','disabled').css('color', '#999');populatePopup('${createLink(controller:"accessRequest", action:'populatePopup')}');"/>
            </div>
    </div>
</div>

<div id="add_entitlementRole_tree" style="display:none;">
    <g:render template="/workerEntitlementRole/addEntitlementRole" model="['closeWithoutPageRefresh':false]"/>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('input[name=businessUnitRequesterSlid]').val('');
        jQuery('input[name=employeeSlid]').val('');
        jQuery('input[name=workerNumber]').val('');
    });
</script>
</body>
</html>