<%@ page import="com.force5solutions.care.feed.FeedOperation; com.force5solutions.care.feed.FeedReportMessageType" %>
<div class="dashboarddemo2-box-container14">
    <div class="dashboarddemo2-box" style="margin-bottom:20px;">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box26 padding-btm">
            <div class="dashboarddemo2-worker-expiration4">
                <div class="dashboarddemo2-worker-expiration-header2 sub-header-text2" align="center" style="text-align:left;">

                %{--<g:if test="${feedInfoVOs}">--}%
                %{--<div>--}%
                %{----}%
                %{--<img width="350" height="245"--}%
                %{--src="${createLink(controller: 'chart', action: 'pie', params: [labels: feedInfoVOs*.name, counts: feedInfoVOs*.percentage, width: 350, height: 245])}"/>--}%
                %{--</div>--}%
                %{--</g:if>--}%
                    <g:formRemote name="feedExceptionForm" method="get"
                            url="${[controller:'dashboard',action:'feedInformationByDate']}" update="feedExceptionsDiv">
                        <div style="padding-left:40px;padding-bottom:9px;">Feed Information On
                            <span class="work-by-status-input" style="float:none;">
                                <calendar:datePicker name="feedExceptionsDate"
                                        id="feedExceptionsDate" value="${selectedDate}"/>
                            </span>
                            <span>
                                <input style="width:17px; height:16px; border:0px;vertical-align:top;"
                                        type="image" src="${createLinkTo(dir: 'images', file: 'go.jpg')}"/>
                            </span>
                        </div>
                    </g:formRemote>
                    <div class="table1">
                        <table width="100%" align="center" class="dashboarddemo2-pie-chart-text" border="0" cellspacing="0" cellpadding="3">
                            <tr>
                                <td align="center" width="25%" class="day-text">Feed</td>
                                <td align="center" class="day-text">Processed</td>
                                <td align="center" width="10%" class="day-text">Errors</td>
                                <td align="center" class="day-text">Exceptions</td>
                                <td align="center" width="11%" class="day-text">Start Time</td>
                                <td align="center" width="11%" class="day-text">End Time</td>
                            </tr>
                            <g:each in="${feedInfoVOs}" var="feedInfoVO" status='i'>
                                <g:set var="validId" value="${feedInfoVO.feedRunId}"/>
                                <tr bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}">
                                    <g:if test="${validId}">
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div class="blue-text-link" align="center">
                                                <care:conditionalLink controller="feedRun" action="show" id="${feedInfoVO.feedRunId}" class='linkWithNormalFont'>
                                                    ${feedInfoVO.name}</care:conditionalLink>
                                            </div>
                                        </td>

                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <g:if test="${!feedInfoVO.processedCount.equals('0')}">
                                                <div class="blue-text-link" align="center">
                                                    <care:conditionalLink class="linkWithNormalFont" controller="feedRun" action="showDetails" id="${feedInfoVO.feedRunId}" params="[type: FeedReportMessageType.INFO.name()]">
                                                        ${feedInfoVO.processedCount}</care:conditionalLink>
                                                </div>
                                            </g:if>
                                            <g:else>
                                                <div align="center">
                                                    <strong>${feedInfoVO.processedCount}</strong>
                                                </div>
                                            </g:else>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <g:if test="${!feedInfoVO.errorCount.equals('0')}">
                                                <div class="blue-text-link" align="center">
                                                    <care:conditionalLink class="linkWithNormalFont" controller="feedRun" action="showDetails" id="${feedInfoVO.feedRunId}" params="[type: FeedReportMessageType.ERROR.name()]">
                                                        ${feedInfoVO.errorCount}</care:conditionalLink>
                                                </div>
                                            </g:if>
                                            <g:else>
                                                <div align="center">
                                                    <strong>${feedInfoVO.errorCount}</strong>
                                                </div>
                                            </g:else>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <g:if test="${!feedInfoVO.exceptionCount.equals('0')}">
                                                <div class="blue-text-link" align="center">
                                                    <care:conditionalLink class="linkWithNormalFont" controller="feedRun" action="showDetails" id="${feedInfoVO.feedRunId}" params="[type: FeedReportMessageType.EXCEPTION.name()]">
                                                        ${feedInfoVO.exceptionCount}</care:conditionalLink>
                                                </div>
                                            </g:if>
                                            <g:else>
                                                <div align="center">
                                                    <strong>${feedInfoVO.exceptionCount}</strong>
                                                </div>
                                            </g:else>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.startTime}</strong>
                                            </div>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.endTime}</strong>
                                            </div>
                                        </td>
                                    </g:if>
                                    <g:else>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                ${feedInfoVO.name}
                                            </div>
                                        </td>

                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.processedCount}</strong>
                                            </div>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.errorCount}</strong>
                                            </div>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.exceptionCount}</strong>
                                            </div>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.startTime}</strong>
                                            </div>
                                        </td>
                                        <td align="center" class="dashboarddemo2-calander-cell-brd6">
                                            <div align="center">
                                                <strong>${feedInfoVO.endTime}</strong>
                                            </div>
                                        </td>
                                    </g:else>
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
