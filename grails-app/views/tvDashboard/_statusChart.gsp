<%@ page import="com.force5solutions.care.common.CareConstants; com.force5solutions.care.cc.WorkerStatus" %>
<div class="dashboarddemo2-box26">
    <div class="sub-header-text2 text-pdng-btm2">Access Status Summary</div>
        <table width="100%" class="worker-by-status" border="0" cellspacing="0" cellpadding="0">
            <g:each in="${workerStatusVOs}" var="workerStatusVO" status="i">
                <tr>
                    <td style="vertical-align:middle;width:25%;" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}"
                            class="dashboarddemo2-calander-cell-brd6">${workerStatusVO.status}</td>
                    <td style="vertical-align:middle;width:8%;" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}" width="34"
                            class="dashboarddemo2-calander-cell-brd6">
                            <g:if test="${workerStatusVO.count > 0}">
                        <span class="blue-text-link"><care:conditionalLink class="linkWithNormalFont" controller="${(workerType && workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR) ? 'contractor' : 'employee')}" action="filterList" params="[filterByStatus:workerStatusVO.status]">
                                    ${workerStatusVO.count}</care:conditionalLink></span>
                            </g:if>
                            <g:else>
                                <span class="without-link">${workerStatusVO.count}</span>
                            </g:else>
                    </td>
                    <td style="vertical-align:middle;width:65%;">
                            <g:set value="${ workerStatusVO.status.equals(WorkerStatus.INACTIVE.name) ? 'yellow' : (workerStatusVO.status.equals(WorkerStatus.TERMINATED.name) ? 'red' : (workerStatusVO.status.equals(WorkerStatus.UNASSIGNED.name) ? 'black' : 'green'))}" var="color"/>
                            <div class="dashboarddemo2-worker-expiration-bar dashboarddemo2-worker-expiration-${color}"
                                    style="height:22px;width: ${(total) ? ((workerStatusVO.count / total) * 100) : 0}%;"></div>
                    </td>
                </tr>
            </g:each>
        </table>
</div>
