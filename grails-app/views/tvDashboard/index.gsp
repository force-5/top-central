<%@ page import="com.force5solutions.care.common.CareConstants; org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>TV Dashboard</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'tvDashboard.css')}"/>
    <g:javascript library="jquery"/>
    <script type="text/javascript">jQuery.noConflict();</script>
    <g:if test="${!session.loggedUser}">
        <g:set var='reloadInterval' value="${ConfigurationHolder.config.reloadPublicDashboardTimeInterval ? ConfigurationHolder.config.reloadPublicDashboardTimeInterval.toString().toLong() : 900000}"/>
        <script type="text/javascript">
            jQuery(document).ready(function() {
                setTimeout(function() {
                    location.reload();
                }, (${reloadInterval}));
            });
        </script>
    </g:if>
</head>

<body>
<div id="Tv-container">
    <div id="Tv-wrapper" class="clearfix">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
            <tr>
                <td valign="top"><div id="scc-Section" style="color:#ffffff;font-size:60px;text-align:center;">SCC <br/> CIP <br/><span class="emp-txt">${(workerType && workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR) ? 'Contractor' : 'Employee')}</span><br/><span class="metrics-txt">Metrics</span><br/><span class="date-txt">${new Date()}</span></div></td>
                <td class="tvRightPanel" valign="top">
                    <div id="workerExpirationOutlook" style="padding:0 0 20px">
                        <care:workerExpirationOutlook media='tv' workerType="${workerType}" viewType='${viewType}'/>
                    </div>
                    <table border="0" cellpadding="0" cellspacing="0" align="center">
                        <tr>
                            <td width="49%" valign="top">
                                <care:expirationForecast media='tv' workerType="${workerType}" viewType='${viewType}'/>
                                <div style="clear:both;height:20px;"></div>
                                <care:workerStatusChart media='tv' workerType="${workerType}" viewType='${viewType}'/>
                                <br/>
                            </td>
                            <td width="2%">&nbsp;</td>
                            <td valign="top">
                                <care:outstandingRequestsChart media='tv' workerType="${workerType}" viewType='${viewType}'/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td style="text-align:right;vertical-align:bottom;height:25px"><img src="${resource(dir: 'images', file: 'footer-txt.png')}" alt="force5"/></td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </div>
</div>
</body>
</html>
