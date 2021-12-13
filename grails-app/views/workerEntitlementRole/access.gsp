<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="contractor"/>

    <title>TOP By Force 5 : Access Page</title>
</head>
<body>
<care:recentStatus workerId="${worker?.id}"/>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <g:if test="${worker instanceof Contractor}">
        <g:set var="permission" value="${Permission.READ_CONTRACTOR_PROFILE}"/>
    </g:if>
    <g:else>
        <g:set var="permission" value="${Permission.READ_EMPLOYEE_PROFILE}"/>
    </g:else>
    <g:if test="${care.hasPermission(permission: permission, worker: worker)}">
        <span class="menuButton"><g:link action="show" controller="personnel" params="[id: worker.id]"
                class="create">Profile</g:link></span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">Profile</a></span>
    </g:else>
    <g:if test="${worker instanceof Contractor}">
        <g:set var="permission" value="${Permission.READ_CONTRACTOR_CERTIFICATION}"/>
    </g:if>
    <g:else>
        <g:set var="permission" value="${Permission.READ_EMPLOYEE_CERTIFICATION}"/>
    </g:else>
    <g:if test="${care.hasPermission(permission: permission, worker: worker)}">
        <span class="menuButton">
            <g:link action="certification" controller="workerCertification" params="[id: worker.id]" class="list">
                Certification
            </g:link>
        </span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="list-disabled">Certification</a></span>
    </g:else>

    <g:if test="${care.hasPermission(permission: Permission.READ_MANAGE_WORKFLOW, worker: worker)}">
        <span class="menuButton">
            <g:if test="${worker instanceof Contractor}">
                <g:set value="${worker.id}" var="id"/>
            </g:if>
            <g:else>
                <g:set value="${worker.slid}" var="id"/>
            </g:else>
            <g:link controller="worker" action="workflowReportBySlidOrId" params="[id: id]" class="list">
                Workflows
            </g:link>
        </span>
    </g:if>
    <g:else>
        <span class="menuButton"><a class="create-disabled">Workflows</a></span>
    </g:else>
</div>
<div id="wrapper">
    <div id="right-panel">
        <div align="center"><b>${flash.message}</b></div>
        <div id="right-top1">
            <div id="contractor">
                <span class="font12-bold">
                    ${worker.name}
                </span>
            </div>
            <div>
                <div class="in-panel">
                    <div class="aceess-page-buttons">
                        <div class="namerowbig">
                            <g:if test="${worker?.terminateForCause}">
                                <a class="department-disabled">
                                    <span>Add&nbsp;Entitlement&nbsp;Roles</span>
                                </a>
                                <a class="department-disabled">
                                    <span>Filter</span>
                                </a>
                            </g:if>
                            <g:else>
                                <g:if test="${worker instanceof Contractor}">
                                    <g:set var="permission" value="${Permission.CREATE_CONTRACTOR_ACCESS}"/>
                                </g:if>
                                <g:else>
                                    <g:set var="permission" value="${Permission.CREATE_EMPLOYEE_ACCESS}"/>
                                </g:else>
                                <g:if test="${((care.hasPermission(permission: Permission.CAN_ADD_CONTRACTOR_LOCATION)) || (care.hasPermission(permission: permission, worker: worker)))}">
                                    <a href="#" class="department-enabled addWorkerEntitlementRoleLink" id="addWorkerEntitlementRole" rel="addWorkerEntitlementRole" onclick="showModalDialogWithPosition('add_entitlementRole_tree', true);
                                    return false;">
                                        <span>Add&nbsp;Entitlement&nbsp;Roles</span></a>
                                </g:if>
                                <g:else>
                                    <a class="department-disabled addWorkerEntitlementRoleLink">
                                        <span>Add&nbsp;Entitlement&nbsp;Roles</span></a>
                                </g:else>
                                <span>&nbsp;&nbsp;</span>

                                <g:if test="${params.filterByCertification}">
                                    <g:link class="department-enabled" controller="workerEntitlementRole" action="showAllEntitlementRole" id="${worker?.id}">
                                        <span>Show&nbsp;All</span></g:link>
                                </g:if>
                                <g:else>
                                    <a class="department-enabled" onclick="showModalDialog('filterCcGatekeeperCertification', true);">
                                        <span>Filter</span></a>
                                </g:else>
                                <span>&nbsp;&nbsp;</span>

                            %{--<g:if test="${entitlementRoles}">--}%
                            %{--<g:if test="${care.hasPermission(permission: Permission.REQUEST_EMPLOYEE_TERMINATE, worker: worker)}">--}%
                            %{--<a href="#" class="department terminateForCauseAnchor" style="color:red;float:right;" rel="terminateForCauselink"--}%
                            %{--onclick="return showTerminateForCauseDialog('terminateForCause')">--}%
                            %{--<span>Terminate&nbsp;For&nbsp;Cause</span></a>--}%
                            %{--</g:if>--}%
                            %{--<g:else>--}%
                            %{--<a style="float:right;" class="department-disabled  terminateForCauseAnchor">--}%
                            %{--<span>Terminate&nbsp;For&nbsp;Cause</span></a>--}%
                            %{--</g:else>--}%
                            %{--</g:if>--}%
                            </g:else>
                        </div>

                    </div>
                </div>
                <div class="in-panel" id="access_table">
                    <g:render template='/workerEntitlementRole/accessTable' model="[worker: worker, entitlementRoles: entitlementRoles]"/>
                </div>
                <div id="missingCertification_div">
                    <care:missingCertifications worker="${worker}"/>
                </div>
                <div id="add_entitlementRole_tree" style="display:none;">
                    <g:render template="/workerEntitlementRole/addEntitlementRole" model="[worker: worker, entitlementRoles: entitlementRoles]"/>
                </div>
                <g:render template="/workerEntitlementRole/filter" model="[worker: worker, certifications:certifications]"/>
                <g:render template="/workerEntitlementRole/terminateForCause" model="[worker: worker]"/>
            </div>
        </div>

    </div>
</div>

</body>
</html>
