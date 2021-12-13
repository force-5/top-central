<div class="dashboarddemo2-box26">
        <table width="100%" class="dashboarddemo2-worker-expiration-table-content" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="12" style="text-align:center;">
                        <span class="dashboarddemo2-worker-expiration-header">Certification Expiration Outlook
                        </span>
                </td>
            </tr>
            <tr>
                <td colspan="12" >
                    <img width="100%"  src="${createLink(controller: 'chart', action: 'area', params: [data: expirationOutlookVOs*.expirationCount, width: 747, height: 39, key: UUID.randomUUID()])}"/>
                </td></tr>
            <tr>
                <g:each in="${expirationOutlookVOs.month}" var="month">
                    <td class="day-text" valign="middle">${month}</td>
                </g:each>
            </tr>
        </table>
</div>
