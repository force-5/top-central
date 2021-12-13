<div class="dashboarddemo2-box-container14">
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box26">
            <div class="dashboarddemo2-worker-expiration4">
                <div class="dashboarddemo2-worker-expiration-header2" align="center">
                    <div class="text-pdng-btm" align="center">
                        <span class="header-text-23 sub-header-text2 ">Outstanding Requests</span>
                    </div>
                    <div>
                        <img width="370" height="260"
                                src="${createLink(controller: 'chart', action: 'stackedBarChart', params: [width: 370, height: 260, workerType: workerType, viewType: viewType])}"/>
                    </div>

                    <div class="table2" style="padding-top:9px;">
                        <table width="303" align="center" class="dashboarddemo2-pie-chart-text" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="32" align="center"><strong>Action Requests</strong></td>
                                <td align="center"><strong>Outstanding<br/>Requests</strong></td>
                                <td align="center"><strong>Completed<br/>Requests</strong></td>
                            </tr>
                            <g:each in="${actionRequestVOs}" var="actionRequestVO" status="i">
                                <tr bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}">
                                    <td align="left" class="dashboarddemo2-calander-cell-brd6">${actionRequestVO.requestName.toString().minus('CONTRACTOR ')}</td>
                                    <td align="center" class="dashboarddemo2-calander-cell-brd6">

                                        <div align="center">
                                            <g:if test="${actionRequestVO.outstandingRequests != '0'}">
                                                <span class="blue-text-link">
                                                    <care:conditionalLink class="linkWithNormalFont" controller='centralWorkflowTask' action='showIncompleteTasks' params="[type: actionRequestVO.requestName, showResultsFromLastBatch: 'true', workerType: workerType, viewType: viewType]">${actionRequestVO.outstandingRequests}</care:conditionalLink>
                                                </span>
                                            </g:if>
                                            <g:else>
                                                <span class="without-link">${actionRequestVO.outstandingRequests}</span>
                                            </g:else>
                                        </div></td>
                                    <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                        <div class="without-link" align="center">
                                            <span>${actionRequestVO.completedRequests}</span>
                                        </div>
                                    </td>
                                </tr>
                            </g:each>
                        </table>
                    </div>
                    <br/>
                </div>
            </div>
            <div class="dashboarddemo2-img3"></div><div class="dashboarddemo2-img4"></div></div>
    </div>
</div>
