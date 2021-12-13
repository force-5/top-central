<div class="dashboarddemo2-worker-expiration">
    <div class="dashboarddemo2-box-container13">
        <div class="dashboarddemo2-box" style="width:100%">
            <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
            <div class="dashboarddemo2-box25">
                <div class="dashboarddemo2-worker-expiration-header">

                    <table width="100%" class="dashboarddemo2-worker-expiration-table-content" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td colspan="12"><div align="center" class="padding-top1">
                                <g:formRemote name="expirationOutlookForm" method="get"
                                        url="${[controller:'dashboard',action:'workerExpirationOutlook', params: [workerType: workerType, viewType: viewType]]}" update="workerExpirationOutlook">
                                    <span class="dashboarddemo2-worker-expiration-header">Certification Expiration Outlook &nbsp;&nbsp;
                                    <g:select style="width:140px;" from="${certifications?.sort{it.toString().toLowerCase()}}"
                                            optionKey="id" optionValue="name" class='listbox'
                                            value="${selectedCertification?.id}" name="selectedCertification"
                                            noSelection="['':'(All Certifications)']" onchange="jQuery('#goButtonWorkerExpiration').click(); return false;"/>
                                        <span>
                                            <input id="goButtonWorkerExpiration" style="display: none;" type="submit"/>
                                        </span>
                                    </span>
                                </g:formRemote>
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="12" height="43">
                                <img height="39" style="margin-left:25px;"
                                        src="${createLink(controller: 'chart', action: 'area', params: [data: expirationOutlookVOs*.expirationCount, width: 747, height: 39, key: UUID.randomUUID()])}"/>
                            </td></tr>
                        <tr>
                            <g:each in="${expirationOutlookVOs.month}" var="month">
                                <td class="day-text" valign="middle">${month}</td>
                            </g:each>
                        </tr>
                    </table>

                </div>
                <div class="dashboarddemo2-img3"></div><div class="dashboarddemo2-img4"></div></div>
        </div>
    </div>
</div>
