<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.cc.EntitlementRoleAccessStatus; com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<div id="tableContainer" class="tableContainer">
    <table border="0" cellpadding="0" cellspacing="0" class="scrollTable tablesorter" id="tablesorter">
        <thead class="fixedHeader">
        <tr>
            <th>Entitlement Role</th>
            <th>Status</th>
            <th>Last Status Change</th>
            <th>Action</th>
            <th>Revoke</th>
        </tr>
        </thead>
        <tbody class="scrollContent">
        <g:each in="${entitlementRoles}" var="workerEntitlementRole">
            <tr>
                <td>${workerEntitlementRole.entitlementRole.toTreeString()}</td>
                <td>${workerEntitlementRole?.status} ${(workerEntitlementRole?.currentNode) ? ('(' + workerEntitlementRole?.currentNode + ')') : ''}</td>
                <td>
                    ${workerEntitlementRole?.lastStatusChange?.myFormat()}
                </td>
                <g:if test="${worker instanceof Contractor}">
                    <g:set var="actionPermission" value="${Permission.UPDATE_CONTRACTOR_ACCESS}"/>
                    <g:set var="revokePermission" value="${Permission.REQUEST_CONTRACTOR_REVOKE}"/>
                </g:if>
                <g:else>
                    <g:set var="actionPermission" value="${Permission.UPDATE_EMPLOYEE_ACCESS}"/>
                    <g:set var="revokePermission" value="${Permission.REQUEST_EMPLOYEE_REVOKE}"/>
                </g:else>
                <td>
                    <g:remoteLink controller="workerEntitlementRole" action="workerRoleHistoryItemsPopup"
                                  class="detailbutton viewLocationButton"
                                  onSuccess="showPopupWithResponseAndWidth('accessStatusPopup',e,550); scrollWorkerRoleHistoryItemsTable();"
                                  params="[workerEntitlementRoleId: workerEntitlementRole.id]">
                        <span>View&nbsp;</span>
                    </g:remoteLink>
                </td>
                <td>
                    <g:if test="${(workerEntitlementRole.status == EntitlementRoleAccessStatus.active && (care.hasPermission(permission: revokePermission, worker: worker, location: workerEntitlementRole.entitlementRole.location)))}">
                        <g:remoteLink controller="workerEntitlementRole" action="revokeJustificationPopup"
                                      class="detailbutton viewLocationButton"
                                      onSuccess="showPopupWithResponseAndWidth('accessStatusPopup',e,350);"
                                      params="[workerEntitlementRoleId: workerEntitlementRole.id]">
                            <span>Revoke</span>
                        </g:remoteLink>
                    </g:if>
                    <g:elseif
                            test="${(workerEntitlementRole.status in [EntitlementRoleAccessStatus.revoked, EntitlementRoleAccessStatus.pendingRevocation] && care.hasPermission(permission: revokePermission, worker: worker, location: workerEntitlementRole.entitlementRole.location))}">
                        <a class="department-disabled">
                            <span>Revoke</span></a>
                    </g:elseif>
                    <g:else>
                        <g:remoteLink controller="workerEntitlementRole" action="revokeJustificationPopup"
                                      class="detailbutton viewLocationButton"
                                      onSuccess="showPopupWithResponseAndWidth('accessStatusPopup',e,350);"
                                      params="[workerEntitlementRoleId: workerEntitlementRole.id, isCancelled: true]">
                            <span>Revoke</span>
                        </g:remoteLink>
                    </g:else>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div id="accessStatusPopup" style="display:none;"></div>
</div>
<script type="text/javascript">
    function scrollWorkerRoleHistoryItemsTable() {
        if (jQuery("#workerRoleHistoryItemHistory>table tr").size() > 7) {
            jQuery("#workerRoleHistoryItemHistory>table").Scrollable(250, 520)
        }
    }

    jQuery(document).ready(function () {
        if (jQuery("#tablesorter tr").size() > 1) {
            jQuery("#tablesorter").tablesorter({
                textExtraction:myTextExtraction,
                sortList:[
                    [3, 1]
                ],
                headers:{0:{sorter:false}, 5:{sorter:false}}
            });


            if (jQuery("#tablesorter tr").size() > 10) {
                jQuery("#tablesorter").Scrollable(205, 792);
            }
        }
        jQuery.each(jQuery('#tablesorter tbody tr'), function () {
            var status = jQuery(this).children().eq(1).text();
            status = jQuery.trim(status);
            var className;
            switch (status) {
                case 'Active':
                    className = 'green';
                    break;
                case 'Terminated for Cause':
                    className = 'red';
                    break;
                case 'Pending Termination':
                    className = 'red';
                    break;
                default:
                    className = 'yellow';
            }
            jQuery(this).children().eq(1).addClass(className);
        });
    });
</script>