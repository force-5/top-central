<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Access Request Form</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'treeview.css')}"/>
    <script type="text/javascript" src="../js/treeview-cookie.js"></script>
    <script type="text/javascript" src="../js/treeview.js"></script>
    <script type="text/javascript">
        jQuery(document).ready(function() {
            jQuery("#red > ul").treeview({
                animated: "fast",
//                collapsed: true,
                unique: false,
//                persist: "cookie",
                toggle: function() {
//                    window.console && console.log("%o was toggled", this);
                }
            });
        });
    </script>
</head>
<body>
<br>
<div id="wrapper">
</div>
<div id="right-panel">
    <div>
        <h2 class="heading-orange">Access Request Form</h2>
        <care:accessRequestWorkerInfo worker="${worker}"/>
        <g:form name="addEntitlementRoles" onsubmit="addEntitlementRoleIds();" method="post">
            <g:if test="${flash.message}">
                <div style="color:red; font-weight: bold; font-size:16px; text-align:center;">${flash.message}</div>
            </g:if>
            <div class="grayBox"><h3 class="heading">Request Entitlement Roles</h3>
                <p class="selectext">Select roles in the left box, read the descriptions below and click "Add" to add them to your request.</p>
                <div class="clearfix nortxt">
                    <g:hiddenField name="workerId" value="${worker.id}"/>
                    <g:each in="${accessJustifications}" var="accessJustification">
                        <g:hiddenField name="${accessJustification.key}" value="${accessJustification.value}"/>
                    </g:each>
                    <care:selectEntitlementRoles worker="${worker}" selected="${entitlementRoles}"/>
                </div>
            </div>
            <div id="entitlementRolesDescriptionTable">
                <ul>
                    <li style="display:none;" class="head"><span>Entitlement Role</span><label>Description</label></li>
                    <g:each in="${allEntitlementRoles}" var="entitlementRole" status="i">
                        <li style="display: none;" class="${entitlementRole.id} border">
                            <span>${entitlementRole}</span>
                            <label>
                                ${entitlementRole.notes}
                                <g:if test="${entitlementRole.isCip()}">
                                    <a href="${ConfigurationHolder.config.EAMSUrl}" target="_blank" style="color:#990000;text-decoration: underline;">
                                       This is a CIP role, please request using EAMS
                                    </a>
                                </g:if>
                            </label>
                        </li>
                    </g:each>
                </ul>
            </div>
            <div class="continueSubmit">
                <g:actionSubmit value="Continue" class="continueBtn" action="roleRequestForms"/>
                <br/>
                <g:actionSubmit value="&lt; Go Back" class="back" action="publicAccessRequest"/>
            </div>
        </g:form>
    </div>
</div>
</body>
</html>