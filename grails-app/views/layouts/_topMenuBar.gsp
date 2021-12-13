<%@ page import="com.force5solutions.care.ldap.Permission" %>
<br/>
<div class="dashboarddemo2-top-nav">
    <ul id="sddm">
        <g:if test="${care.hasPermission(permission: Permission.CAN_ACCESS_PERSONNEL_MENU)}">
            <li><a onmouseover="mopen('m3')" onmouseout="mclosetime()">Personnel</a>
                <div class="submenu" id="m3" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn1.png'))}"/></div>
                    <div class="submenu-content1">
                        <div class="submenu-content3">
                            <g:if test="${care.hasPermission(permission: Permission.READ_CONTRACTOR_PROFILE, ignorePermissionLevel: 'true')}">
                                <g:link class="contractorListLink" controller="contractor" action="list">Contractors</g:link>
                            </g:if>
                            <g:else>
                                <a class="contractorListLink" style="color: gray;">Contractors</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_EMPLOYEE_PROFILE, ignorePermissionLevel: 'true')}">
                                <g:link class="employeeListLink" controller="employee" action="listEmployees">Employees</g:link>
                            </g:if>
                            <g:else>
                                <a class="employeeListLink" style="color: gray;">Employees</a>
                            </g:else>
                        </div>
                    </div>
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn2.png'))}"/></div>
                </div>
            </li>
        </g:if>
        <g:else>
            <li><a class="contractorListLink"><span style="color:gray">Personnel</span></a></li>
        </g:else>
        <g:if test="${care.hasPermission(permission: Permission.CAN_ACCESS_REPORTS_MENU)}">
            <li><a onmouseover="mopen('m4')" onmouseout="mclosetime()">Reports</a>

                <div class="submenu" id="m4" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn1.png'))}"></div>

                    <div class="submenu-content1">
                        <div class="submenu-content3">
                            <g:link class="reportsListLink" controller="report" action="index">Reports</g:link>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:link class="dashboardLink" controller="dashboard" action="index">Dashboard</g:link>
                        </div>
                    </div>

                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn2.png'))}"/></div>
                </div></li>
        </g:if>
        <g:else>
            <li><a class="reportsListLink"><span style="color:gray">Reports</span></a></li>
        </g:else>
        <g:if test="${care.hasPermission(permission: Permission.CAN_ACCESS_CONTACTS_MENU)}">
            <li><a onmouseover="mopen('m1')" onmouseout="mclosetime()">Contacts</a>
                <div class="submenu" id="m1" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn1.png'))}"></div>
                    <div class="submenu-content1">
                        <div class="submenu-content2">
                            <g:if test="${care.hasPermission(permission: Permission.READ_VENDOR)}">
                                <g:link class="vendorListLink" controller="vendor" action="list">Vendors</g:link>
                            </g:if>
                            <g:else>
                                <a class="vendorListLink" style="color: gray;">Vendors</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_CONTRACTOR_SUP)}">
                                <g:link class="contractorSupervisorListLink" controller="contractorSupervisor" action="list">Contractor Supervisors</g:link>
                            </g:if>
                            <g:else>
                                <a class="contractorSupervisorListLink" style="color: gray;">Contractor Supervisors</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_EMPLOYEE_SUP)}">
                                <g:link class="employeeSupervisorListLink" controller="employeeSupervisor" action="list">Employee Supervisors</g:link>
                            </g:if>
                            <g:else>
                                <a class="employeeSupervisorListLink" style="color: gray;">Employee Supervisors</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_BUR)}">
                                <g:link class="burListLink" controller="businessUnitRequester" action="list">Business Unit Requesters</g:link>
                            </g:if>
                            <g:else>
                                <a class="burListLink" style="color: gray;">Business Unit Requesters</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                        </div>
                    </div>
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn2.png'))}"/></div>
                </div></li>
        </g:if>
        <g:else>
            <li><a><span style="color:gray">Contacts</span></a></li>
        </g:else>
        <g:if test="${care.hasPermission(permission: Permission.CAN_ACCESS_ADMIN_MENU)}">
            <li><a onmouseover="mopen('m2')" onmouseout="mclosetime()">Admin</a>
                <div class="submenu" id="m2" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn1.png'))}"/></div>
                    <div class="submenu-content1">
                        <div class="submenu-content3">
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_COURSE)}">
                                <g:link class="courseListLink" controller="course" action="list">Courses</g:link>
                            </g:if>
                            <g:else>
                                <a class="courseListLink" style="color: gray;">Courses</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_CERTIFICATION)}">
                                <g:link class="certificationListLink" controller="certification" action="list">Certifications</g:link>
                            </g:if>
                            <g:else>
                                <a class="certificationListLink" style="color: gray;">Certifications</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_LOCATION)}">
                                <g:link class="locationListLink" controller="location" action="newTree">Organizational Tree</g:link>
                            </g:if>
                            <g:else>
                                <a class="locationListLink" style="color: gray;">Organizational Tree</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_SECURITY_ROLE)}">
                                <g:link class="securityRoleListLink" controller="securityRole" action="list">Security Roles</g:link>
                            </g:if>
                            <g:else>
                                <a class="securityRoleListLink" style="color: gray;">Security Roles</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">

                            <g:if test="${care.hasPermission(permission: Permission.READ_MESSAGE_TEMPLATE)}">
                                <g:link class="messageTemplateListLink" controller="centralMessageTemplate" action="list">Message Templates</g:link>
                            </g:if>
                            <g:else>
                                <a class="messageTemplateListLink" style="color: gray;">Message Templates</a>
                            </g:else>

                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_ALERT)}">
                                <g:link class="alertListLink" controller="alert" action="list">Alerts</g:link>
                            </g:if>
                            <g:else>
                                <a class="alertListLink" style="color: gray;">Alerts</a>
                            </g:else>

                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_BUSINESS_UNIT)}">
                                <g:link class="businessUnitsListLink" controller="businessUnit" action="list">Business Units</g:link>
                            </g:if>
                            <g:else>
                                <a class="businessUnitsListLink" style="color: gray;">Business Units</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_ENTITLEMENT_POLICY)}">
                                <g:link class="businessUnitsListLink" controller="entitlementPolicy" action="list">Entitlement Policies</g:link>
                            </g:if>
                            <g:else>
                                <a class="businessUnitsListLink" style="color: gray;">Entitlement Policies</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                            <g:if test="${care.hasPermission(permission: Permission.READ_MANAGE_WORKFLOW)}">
                                <g:link class="manageWorkflowLink" controller="workflow" action="index">Manage Workflows</g:link>
                            </g:if>
                            <g:else>
                                <a class="manageWorkflowLink" style="color: gray;">Manage Workflows</a>
                            </g:else>
                            <hr style="width:80px;position:relative;margin:0px auto;">
                        </div>
                    </div>
                    <div><img src="${createLinkTo(dir: 'images', file: ('drp-dwn2.png'))}"/></div>
                </div></li>
        </g:if>
        <g:else>
            <li><a><span style="color:gray">Admin</span></a></li>
        </g:else>
        <care:inbox/>
    </ul>
    <a href="../data/APS_Training.pdf" target="_blank" class="helpLink">User Guide</a>
</div>
