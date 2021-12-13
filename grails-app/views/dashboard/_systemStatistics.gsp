<%@ page import="com.force5solutions.care.ldap.Permission; com.force5solutions.care.common.CareConstants; com.force5solutions.care.common.CentralMessageTemplate; com.force5solutions.care.cc.*;" %>

<div class="dashboarddemo2-box-container1" style="padding-top:25px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="86%" height="23" align="center" class="header1"><div align="center">System Statistics</div></td>
        </tr>
    </table>
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box24">
            <g:if test="${viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_SUPERVISOR)}">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="89%" class="dashboarddemo2-calander-cell-brd5 pdng-td">Contractors</td>
                        <td width="11%" class="dashboarddemo2-calander-cell-brd11  ">
                            <div align="center">
                                <g:if test="${contractors}">
                                    <span class="blue-text-link">
                                        ${contractors}
                                    </span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${contractors}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td width="89%" class="dashboarddemo2-calander-cell-brd4 pdng-td">Employees</td>
                        <td width="11%" class="dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${employees}">
                                    <span class="blue-text-link">
                                        ${employees}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${employees}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td">Vendors</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${vendors}">
                                    <span class="blue-text-link">
                                        ${vendors}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${vendors}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td ">Contractor Supervisors</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${contractorsSup}">
                                    <span class="blue-text-link">
                                        ${contractorsSup}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${contractorsSup}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td ">Employee Supervisors</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${employeeSup}">
                                    <span class="blue-text-link">
                                        ${employeeSup}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${employeeSup}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4  pdng-td">Business Unit Requesters</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${bur}">
                                    <span class="blue-text-link">
                                        ${bur}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${bur}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td">Total Documents stored</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${dataFiles}">
                                    <span class="without-link">${dataFiles}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${dataFiles}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td ">Message Templates</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${centralMessageTemplates}">
                                    <span class="blue-text-link">
                                        ${centralMessageTemplates}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${centralMessageTemplates}</span>
                                </g:else>
                            </div></td>
                    </tr>
                </table>
            </g:if>
            <g:else>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="89%" class="dashboarddemo2-calander-cell-brd5 pdng-td">Contractors</td>
                        <td width="11%" class="dashboarddemo2-calander-cell-brd11  ">
                            <div align="center">
                                <g:if test="${contractors}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="contractor" action="list">
                                        ${contractors}
                                    </care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${contractors}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td width="89%" class="dashboarddemo2-calander-cell-brd4 pdng-td">Employees</td>
                        <td width="11%" class="dashboarddemo2-calander-cell-brd11 dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${employees}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="employee" action="list" params="[showAll: 'true']">
                                        ${employees}</care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${employees}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td">Vendors</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${vendors}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="vendor" action="list">
                                        ${vendors}</care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${vendors}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td ">Contractor Supervisors</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${contractorsSup}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="contractorSupervisor" action="list">
                                        ${contractorsSup}</care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${contractorsSup}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td ">Employee Supervisors</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${employeeSup}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="employeeSupervisor" action="list">
                                        ${employeeSup}</care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${employeeSup}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4  pdng-td">Business Unit Requesters</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${bur}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="businessUnitRequester" action="list">
                                        ${bur}</care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${bur}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td">Total Documents stored</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${dataFiles}">
                                    <span class="without-link">${dataFiles}</span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${dataFiles}</span>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td class="dashboarddemo2-calander-cell-brd4 pdng-td ">Message Templates</td>
                        <td class=" dashboarddemo2-calander-cell-brd11  dashboarddemo2-calander-cell-brd10">
                            <div align="center">
                                <g:if test="${centralMessageTemplates}">
                                    <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="centralMessageTemplate" action="list">
                                        ${centralMessageTemplates}</care:conditionalLink></span>
                                </g:if>
                                <g:else>
                                    <span class="without-link">${centralMessageTemplates}</span>
                                </g:else>
                            </div></td>
                    </tr>
                </table>
            </g:else>

            <div class="dashboarddemo2-img10"></div><div class="dashboarddemo2-img11"></div></div>
    </div>
</div>
