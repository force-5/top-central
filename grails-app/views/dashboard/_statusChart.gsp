<%@ page import="com.force5solutions.care.common.CareConstants; com.force5solutions.care.cc.WorkerStatus" %>
<div class="dashboarddemo2-box-container14">
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box26">
            <div class="dashboarddemo2-worker-expiration4">
                <div align="center" class="sub-header-text2 text-pdng-btm">Access Status Summary</div>
                <form action="" method="get">
                    <table width="100%" class="worker-by-status" border="0" cellspacing="0" cellpadding="0">
                        <g:each in="${workerStatusVOs}" var="workerStatusVO" status="i">

                            <tr>
                                <td style="vertical-align:middle;" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}" width="85"
                                        class="dashboarddemo2-calander-cell-brd6">${workerStatusVO.status}</td>
                                <td style="vertical-align:middle;" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}" width="34"
                                        class="dashboarddemo2-calander-cell-brd6">
                                    <div align="center">
                                        <g:if test="${workerStatusVO.count > 0}">
                                            <span class="blue-text-link">
                                                <care:conditionalLink class="linkWithNormalFont" controller="${(workerType && workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR )) ? 'contractor' : 'employee'}" action="filterList" params="[filterByStatus:workerStatusVO.status, viewType: viewType]">
                                                    ${workerStatusVO.count}
                                                </care:conditionalLink>
                                            </span>
                                        </g:if>
                                        <g:else>
                                            <span class="without-link">${workerStatusVO.count}</span>
                                        </g:else>
                                    </div></td>
                                <td style="vertical-align:middle;">
                                    <div style="height:22px;">
                                        <g:set value="${ workerStatusVO.status.equals(WorkerStatus.INACTIVE.name) ? 'yellow' : (workerStatusVO.status.equals(WorkerStatus.TERMINATED.name) ? 'red' : (workerStatusVO.status.equals(WorkerStatus.UNASSIGNED.name) ? 'black' : 'green'))}" var="color"/>
                                        <div class="dashboarddemo2-worker-expiration-bar dashboarddemo2-worker-expiration-${color}"
                                                style="height:22px;width: ${(total) ? ((workerStatusVO.count / total) * 100) : 0}%;"></div>
                                    </div>
                                </td>
                            </tr>
                        </g:each>
                    </table><br/>
                </form>

            </div>
            <div class="dashboarddemo2-img3"></div><div class="dashboarddemo2-img4"></div></div>
    </div>
</div>
