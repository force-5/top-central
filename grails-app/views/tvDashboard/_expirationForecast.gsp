<%@ page import="com.force5solutions.care.common.CareConstants" %>
<div class="dashboarddemo2-box26">
    <div class="sub-header-text2 text-pdng-btm2">Certification Expiration
    </div>
    <table width="100%" class="worker-by-status clr" border="0" cellspacing="0" cellpadding="0">
        <g:each in="${expirationForecastVOs}" var="expirationForecastVO" status="i">
            <tr>
                <td style="vertical-align:middle;width:20%" bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}"
                        class="dashboarddemo2-calander-cell-brd6">${expirationForecastVO.period}</td>
                <td style="vertical-align:middle; text-align:center; width:8%;"  bgcolor="${(i % 2) ? '#FFFFFF' : '#D1D3D4'}" class="dashboarddemo2-calander-cell-brd6">
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
                </td>
                <td style="vertical-align:middle;width:70%;">
                        <div class="dashboarddemo2-worker-expiration-red" style="height:22px;width: ${(total) ? ((expirationForecastVO.workerIds.size() / total) * 100) : 0}%;">
                    </div>
                </td>
            </tr>
        </g:each>
    </table>
</div>
