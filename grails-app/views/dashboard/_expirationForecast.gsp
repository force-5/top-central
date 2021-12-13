<%@ page import="com.force5solutions.care.common.CareConstants" %>
<div class="dashboarddemo2-box-container14">
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box26">
            <div class="dashboarddemo2-worker-expiration4">
                <g:formRemote name="expirationForecastForm" method="get"
                        url="${[controller:'dashboard',action:'expirationForecast', params: [workerType: workerType]]}" update="workerExpirationChart">
                    <div class="work-by-status">
                        <div style="text-align:left; font-size:15px;" class="sub-header-text2 text-pdng-btm">Certification Expiration Forecast for <span>
                            <g:select style="width:120px;" from="${certifications?.sort{it.toString().toLowerCase()}}"
                                    optionKey="id" optionValue="name" class='listbox'
                                    value="${selectedCertification?.id}" name="selectedCertification"
                                    noSelection="['':'(All Certifications)']" onchange="jQuery('#goButtonExpirationForecast').click(); return false;"/>
                        </span>
                            <span style="font-size:12px;">&nbsp;On</span>
                            <span class="work-by-status-input" style="float:none;">
                                <calendar:datePicker name="expirationForecast"
                                        id="expirationForecast" value="${selectedDate}"/>
                            </span>
                            <span>
                                <input id="goButtonExpirationForecast" style="width:17px; height:16px; border:0px;vertical-align:top;"
                                        type="image" src="${createLinkTo(dir: 'images', file: 'go.jpg')}"/>
                            </span>
                        </div>
                    </div>
                </g:formRemote>
                <table width="100%" class="worker-by-status clr" border="0" cellspacing="0" cellpadding="0">
                    <g:each in="${expirationForecastVOs}" var="expirationForecastVO" status="i">
                        <tr>
                            <td style="vertical-align:middle;" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}" width="54"
                                    class="dashboarddemo2-calander-cell-brd6">${expirationForecastVO.period}</td>
                            <td style="vertical-align:middle;" width="34" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}" class="dashboarddemo2-calander-cell-brd6">
                                <div align="center">
                                    <g:if test="${expirationForecastVO.workerIds}">
                                        <span class="blue-text-link">
                                            <care:conditionalLink class="linkWithNormalFont" controller="${(workerType && workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR) ? 'contractor' : 'employee')}" action="filterList"
                                                    params="[byPeriod: expirationForecastVO.period, selectedCertification: expirationForecastVO.certificationId, workerType: workerType, viewType: viewType]">
                                                ${expirationForecastVO.workerIds.size()}</care:conditionalLink>
                                        </span>
                                    </g:if>
                                    <g:else>
                                        <span class="without-link">${expirationForecastVO.workerIds.size()}</span>
                                    </g:else>
                                </div>
                            </td>
                            <td style="vertical-align:middle;">
                                <div style="display:inline;float:left;">
                                    <div class="dashboarddemo2-worker-expiration-red" style="height:22px;  width: ${(total) ? ((expirationForecastVO.workerIds.size() / total) * 100) : 0}%;">
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </g:each>
                </table><br/>
            </div>
            <div class="dashboarddemo2-img3"></div><div class="dashboarddemo2-img4"></div></div>
    </div>
</div>
