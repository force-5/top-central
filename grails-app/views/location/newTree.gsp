<%@ page import="com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Organizational Tree</title>
    <link rel="stylesheet" href="${resource(dir: 'js/jquery.contextMenu', file: 'jquery.contextMenu.css')}"/>

    <script type="text/javascript" src="${createLinkTo(dir: 'js/jquery.contextMenu', file: 'jquery.contextMenu.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir: 'js', file: 'locationList.js')}"></script>

</head>
<body>
<br/>
<div>
    <g:each var="type" in="${locationTypes.sort{it.level}*.type.unique()}" status="counter">
        <ul id="myMenu${type.replaceAll(" ", "")}" class="contextMenu" style="width:auto;">
            <g:if test="${care.hasPermission(permission: Permission.READ_LOCATION)}">
                <li class="copy"><a href="#-1">Show</a></li>
            </g:if>
            <g:else>
                <li class="copy"><a style="font-weight:bold;color:gray;">Show</a></li>
            </g:else>
            <g:if test="${care.hasPermission(permission: Permission.UPDATE_LOCATION)}">
                <li class="copy"><a href="#-2">Edit</a></li>
            </g:if>
            <g:else>
                <li class="copy"><a style="font-weight:bold;color:gray;">Edit</a></li>
            </g:else>
            <g:if test="${counter!=0}">
                <g:if test="${care.hasPermission(permission: Permission.DELETE_LOCATION)}">
                    <li class="copy"><a href="#-3">Delete</a></li>
                </g:if>
                <g:else>
                    <li class="copy"><a style="font-weight:bold;color:gray;">Delete</a></li>
                </g:else>
            </g:if>
            <g:else>
                <li class="separator"></li>
            </g:else>
            <g:if test="${locationTypes.find{it.parent?.type==type}}">
                <li class="separator"></li>
            </g:if>
            <g:if test="${care.hasPermission(permission: ((counter!=0)? Permission.CREATE_LOCATION: Permission.CREATE_COMPANY_NODE))}">
                <g:each var="locationType" in="${locationTypes.findAll{it.parent?.type == type}}">
                    <li class="new"><a href="#${locationType.id}">Add New ${locationType.type}</a></li>
                </g:each>
            </g:if>
            <g:else>
                <g:each var="locationType" in="${locationTypes.findAll{it.parent?.type == type}}">
                    <li class="new"><a style="font-weight:bold;color:gray;">Add New ${locationType.type}</a></li>
                </g:each>
            </g:else>
        </ul>
    </g:each>
</div>

<div id="wrapper">
    <div id="right-panel">
        <div class="body">

            <h1>Organizational Tree</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div id='container_id' style="display:block;">
                <care:showTree id="${location?.id}"/>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
</script>

%{--Div used to show location starts here--}%
<div id="show_location" style="display:none;">
    <div id="add_contractor_cert">
        <div id="head-add_contractor" title="show_title">Show Location</div>
        <div class="contractor_cert-dept clearfix">
            <div class="contract_cert clearfix" id="show_location_content">
            </div>
            <div class="department1">
                <input type="button" class="button" value="Close" onclick="jQuery.modal.close();"/>
            </div>

        </div>
        <div id="close-add_contractor">
            <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
        </div>
    </div>
</div>
%{--Div used to show location stops here--}%

%{--Div used to edit location starts here--}%
<div id="edit_location" style="display:none">
    <div id="add_contractor_cert">
        <div id="head-add_contractor" title="edit_title">Edit Location</div>
        <div class="contractor_cert-dept clearfix" id="edit_location_content">
        </div>
        <div>
            <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
        </div>
    </div>
</div>
%{--Div used to edit location stops here--}%
%{--Div used to edit location starts here--}%
<div id="create_location" style="display:none">
    <div id="add_contractor_cert">
        <div id="head-add_contractor" title="create_title">Create Location</div>
        <div class="contractor_cert-dept clearfix" id="create_location_content">
        </div>
        <div>
            <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
        </div>
    </div>
</div>
%{--Div used to edit location stops here--}%

<div id="successful_operation" style="display:none;">&nbsp;</div>
</body>
</html>
