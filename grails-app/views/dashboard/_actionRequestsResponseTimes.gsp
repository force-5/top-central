<div class="dashboarddemo2-box-container14">
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box26">
            <div class="dashboarddemo2-worker-expiration4">
                <div class="dashboarddemo2-worker-expiration-header2 sub-header-text2"><div class=" text-pdng-btm">&nbsp;&nbsp;&nbsp;Requests Response Times <span class="font-12px">(In Hours)</span></div>
                    <div class="table3">
                        <table width="94%" align="center" class="dashboarddemo2-pie-chart-text" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center" width="45%"><div align="center"><strong><br/>Action Requests</strong></div></td>
                                <td align="center"><div align="center"><strong>Avg Response Time</strong></div></td>
                                <td align="center"><div align="center"><strong>Shortest Response Time</strong></div></td>
                                <td align="center"><div align="center"><strong>Longest Response Time</strong></div></td>
                            </tr>
                            <g:each in="${actionRequestResponseTimeVOs}" var="actionRequestResponseTimeVO" status="i">
                                <tr bgcolor="${(i % 2) ? '#d1d3d4' : '#ffffff'}">
                                    <td style="text-align: left;" class="dashboarddemo2-calander-cell-brd6">${actionRequestResponseTimeVO.action}</td>
                                    <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                        <div class="without-link" align="center">${actionRequestResponseTimeVO.averageTime}</div>
                                    </td>
                                    <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                        <div class="without-link" align="center">${actionRequestResponseTimeVO.shortestTime}</div>
                                    </td>
                                    <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                        <div class="without-link" align="center">${actionRequestResponseTimeVO.longestTime}</div>
                                    </td>
                                </tr>
                            </g:each>
                        </table>
                    </div>
                    <br/>
                </div>
            </div>
            <div class="dashboarddemo2-img3"></div><div class="dashboarddemo2-img4"></div>
        </div>
    </div>
    <div class="clr"></div>
</div>
