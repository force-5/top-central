<div class="dashboarddemo2-box26">
    <div class="text-pdng-btm" style="text-align:center;">
        <span class="header-text-23 sub-header-text2 ">Outstanding Requests</span>
    </div>
    <div class="clearfix" style="text-align:center;width:100%;">
        <img width="90%" src="${createLink(controller: 'chart', action: 'stackedBarChart', params: [width: 370, height: 260, workerType: workerType, viewType: viewType])}"/>
    </div>
    <table align="center" style="padding-top:9px;" class="dashboarddemo2-pie-chart-text" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td height="32"><strong>Action Requests</strong></td>
            <td style="text-align:center;"><strong>Outstanding<br/>Requests</strong></td>
            <td style="text-align:center;"><strong>Completed<br/>Requests</strong></td>
        </tr>
        <g:each in="${actionRequestVOs}" var="actionRequestVO" status="i">
            <tr bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}">
                <td align="left" class="dashboarddemo2-calander-cell-brd6">${actionRequestVO.requestName.minus('CONTRACTOR')}</td>
                <td style="text-align:center;" class="dashboarddemo2-calander-cell-brd6">
                    <g:if test="${actionRequestVO.outstandingRequests != '0'}">
                        <span class="blue-text-link">
                            <care:conditionalLink class="linkWithNormalFont" controller='centralWorkflowTask' action='showIncompleteTasks' params="[type: actionRequestVO.requestName, showResultsFromLastBatch: 'true', workerType: workerType, viewType: viewType]">${actionRequestVO.outstandingRequests}</care:conditionalLink>
                        </span>
                    </g:if>
                    <g:else>
                        <span class="without-link">${actionRequestVO.outstandingRequests}</span>
                    </g:else>
                </td>
                <td style="text-align:center;" class="dashboarddemo2-calander-cell-brd6">
                    <div class="without-link" align="center">
                        <span>${actionRequestVO.completedRequests}</span>
                    </div>
                </td>
            </tr>
        </g:each>
    </table>
</div>
