<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Access Justification</title>
</head>

<body>
<br>

<div id="wrapper">
</div>

<div id="right-panel">
    <h2 class="heading-orange">Access Justification</h2>

    <div class="continueBox"
         style="text-align:left;padding-left:15px;"><h3>Please provide a Justification for your access to each role you have requested.</h3>
    </div>
    <g:if test="${flash.message}">
        <br/>

        <div style="color:red;padding-left:15px; background:#FFFFFF; font-weight: bold;">${flash.message}</div>
    </g:if>
    <br/>
    <g:form name="roleRequestForm" method="post">
        <g:hiddenField name="workerId" value="${worker.id}"/>
        <g:hiddenField name="slid" value="${worker.slid}"/>
        <g:each in="${entitlementRoles}" var="entitlementRole" status="index">
            <g:hiddenField name="entitlementRoleIds" value="${entitlementRole.id}"/>
            <div id="roleRequestForm-${index}" class="roleRequestForm" style="margin-bottom:20px;">
                <g:render template="/accessRequest/requiredCertifications"
                          model="[entitlementRole: entitlementRole, worker: worker]"/>
            </div>
        </g:each>
        <div class="continueBox">
            <h3>Click Submit to Complete Access Request.</h3>
            <h3>A Notification will be sent to your Supervisor ${worker.supervisor ? '(' + worker.supervisor.firstMiddleLastName + ')' : ''} for Approval.</h3>
        </div>
        <div class="continueSubmit">
            <g:actionSubmit value="Submit" class="continueBtn" action="processRequest"/>
            <br/>
            <g:actionSubmit value="&lt; Go Back" class="back" action="getAccessInfo"/>
        </div>
    </g:form>
</div>
<script type="text/javascript">
    jQuery('.accessJustificationDummy').click(function () {
        jQuery(this).hide();
        jQuery(this).next().focus();
    })
</script>
</body>
</html>