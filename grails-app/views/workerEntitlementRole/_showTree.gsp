<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.cc.EntitlementRoleAccessStatus; com.force5solutions.care.common.EntitlementStatus; com.force5solutions.care.ldap.Permission;" %>
<div id="workerRoleHistoryItemArea" style="display:none;">
    <g:render template="/workerEntitlementRole/accessJustification" model="['worker':worker]"/>
</div>
<div id="entitlementRolesTree">
    <div id="breadcrumb" class="breadcrumb">
        <g:each var="p" in="${breadcrumbs}">
            <g:remoteLink
                    controller="workerEntitlementRole"
                    action="getLocationTree"
                    id="${p.id}"
                    rel="${p.id}"
                    class="location-${p.id}"
                    update="container_id"
                    params="[workerId: worker.id]"
                    menuLevel="${p.locationType.type.replaceAll(' ', '')}">
                <span>${p}</span>
            </g:remoteLink> &gt;
        </g:each>
    </div>
    <br/>
    <ul id="children" class="location-children-list">
        <g:if test="${location?.isBusinessUnit()}">
            <g:each var="entitlementRole" in="${location.entitlementRoles.findAll{!it.deletedFromAps}.sort{it.name}}">
                <li>
                    <g:if test="${(entitlementRole.status==EntitlementStatus.ACTIVE)}">
                        <a style="font-weight:bold;">${entitlementRole.name}</a>
                        <span id="addThisLink">
                            <g:if test="${!(worker.entitlementRoles && (entitlementRole?.id in worker.entitlementRoles.findAll{!(it.status in [EntitlementRoleAccessStatus.revoked, EntitlementRoleAccessStatus.rejected, EntitlementRoleAccessStatus.timeOut])}*.entitlementRole?.id))}">
                                &nbsp;&nbsp;-&nbsp;&nbsp;
                                <g:if test="${worker instanceof Contractor}">
                                    <g:set var="permission" value="${Permission.CREATE_CONTRACTOR_ACCESS}"/>
                                </g:if>
                                <g:else>
                                    <g:set var="permission" value="${Permission.CREATE_EMPLOYEE_ACCESS}"/>
                                </g:else>
                                <g:if test="${(care.hasPermission(permission: permission, worker: worker, location: entitlementRole.location))}">
                                    <a href="#" onclick="addEntitlementRoleToworker('${worker.id}', '${entitlementRole.id}', '${entitlementRole.name}');" rel="${entitlementRole.id}" class="location-add-${entitlementRole.id}">
                                        <span style="font-size:14px;">Add</span>
                                    </a>
                                </g:if>
                                <g:else>
                                    <span style="color:gray;">Add</span>
                                </g:else>
                            </g:if>
                            <g:else>
                                &nbsp;&nbsp;<img src="${createLinkTo(dir: 'images', file: 'tickmark.jpg')}" height="12" alt="Tick Mark"/>
                            </g:else>
                        </span>
                    </g:if>
                    <g:else>
                        <a style="font-weight:bold;color:red;">${entitlementRole.name}</a>
                    </g:else>
                </li>
            </g:each>
        </g:if>
        <g:else>
            <g:each var="p" in="${location?.childLocations?.sort{it.name}}">
                <li>
                    <g:if test="${(p.childLocations.size()<1 && !p.entitlementRoles)}">
                        <a rel="${p.id}" menuLevel="${p.locationType.type.replaceAll(' ', '')}">
                            <span><strong>${p}</strong></span>
                        </a>
                    </g:if>
                    <g:else>
                        <g:remoteLink
                                controller="workerEntitlementRole"
                                action="getLocationTree"
                                id="${p.id}"
                                rel="${p.id}"
                                class="location-${p.id}"
                                update="container_id"
                                params="[workerId: worker.id]"
                                menuLevel="${p.locationType.type.replaceAll(' ', '')}">
                            <span>${p}</span>
                        </g:remoteLink>
                    </g:else>
                </li>
            </g:each>
        </g:else>

    </ul>
</div>