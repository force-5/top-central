<%@ page import="com.force5solutions.care.common.CareConstants; org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="contractor"/>
    <title>TOP Dashboard</title>
    <script type="text/javascript" src="${createLinkTo(dir: 'js', file: 'jquery-ui.min.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir: 'js', file: 'jMonthCalendar.js')}"></script>
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
    <style type="text/css">
    table {
        border: 0px solid #CCCCCC;
        width: 100%;
    }

    td, th {
        font: 10px / 12px verdana, arial, helvetica, sans-serif;
        padding: 0px;
        text-align: justify;
        vertical-align: none;
    }

    </style>
</head>

<body>
<span id="ajax_spinner" style="display: none;position:absolute; top:40%; left:50%; z-index:3000;">
    <img src="${createLinkTo(dir: 'images', file: 'spinner.gif')}"/>
</span>
<g:if test="${viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_SUPERVISOR)}">
    <g:set var="supervisorView" value="${true}"/>
</g:if>
<g:else>
    <g:set var="supervisorView" value="${false}"/>
</g:else>
<g:if test="${ConfigurationHolder.config.showContractorDashboard.toString().equalsIgnoreCase('true')}">
    <div id="dashBoardTab" class="clearfix" style="padding-top:18px;">
        <ul>
            <li><a class="${workerType.equals(CareConstants.WORKER_TYPE_EMPLOYEE) ? 'current' : ''}" href="${createLink(action: 'employeeDashboard', params: [viewType: viewType])}">Employee</a></li>
            <li><a class="${workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR) ? 'current' : ''}" href="${createLink(action: 'contractorDashboard', params: [viewType: viewType])}">Contractor</a></li>
        </ul>
    </div>
</g:if>
<div id="dashboarddemo2-wrapper">
    <g:if test="${canSeeGlobalAndSupervisorMode}">
        <div style="padding-top:10px; padding-left:5px;">
            <g:form name="viewTypeForm" action="${workerType.equalsIgnoreCase(CareConstants.WORKER_TYPE_EMPLOYEE) ? 'employeeDashboard' : 'contractorDashboard'}" controller='dashboard'>
                <g:radio value="${CareConstants.VIEW_TYPE_GLOBAL}" name="viewType" id="viewTypeGlobal" checked='${!supervisorView}'/> Global Level View
                <g:radio value="${CareConstants.VIEW_TYPE_SUPERVISOR}" name="viewType" id="viewTypeSupervisor" checked='${supervisorView}'/> Supervisor Level View
            </g:form>
        </div>
    </g:if>
    <br/>
    <g:set var="beforeCertificationsExpirations" value="${new Date()}"/>
    <care:certificationExpirations workerType='${workerType}' viewType='${viewType}'/>
    <g:set var="afterCertificationsExpirations" value="${new Date()}"/>

    <div id="newsAndNotes">
        <g:set var="beforeNewsAndNotes" value="${new Date()}"/>
        <care:newsAndNotes/>
    </div>
    <care:systemStatistics viewType='${viewType}'/>
    <g:set var="afterNewsAndNotes" value="${new Date()}"/>

    <div style="clear:both;"></div>
    <div id="workerExpirationOutlook">
        <g:set var="beforeWorkerExpirationOutlook" value="${new Date()}"/>
        <care:workerExpirationOutlook workerType='${workerType}' viewType='${viewType}'/>
        <g:set var="afterWorkerExpirationOutlook" value="${new Date()}"/>
    </div>
    <div style="clear:both;"></div>
    <div class="right-container">
        <div id="workerExpirationChart">
            <g:set var="beforeExpirationForecast" value="${new Date()}"/>
            <care:expirationForecast workerType='${workerType}' viewType='${viewType}'/>
            <g:set var="afterExpirationForecast" value="${new Date()}"/>
        </div>
        <div style="clear:both;"></div>
        <g:set var="beforeWorkerStatusChart" value="${new Date()}"/>
        <care:workerStatusChart workerType='${workerType}' viewType='${viewType}'/>
        <g:set var="afterWorkerStatusChart" value="${new Date()}"/>
        <div style="clear:both;"></div>
        <div id="feedExceptionsDiv">
            <g:set var="beforeFeedInformation" value="${new Date()}"/>
            <g:if test="${!supervisorView}">
                <care:getFeedInformationByDate workerType='${workerType}'/>
            </g:if>
            <g:set var="afterFeedInformation" value="${new Date()}"/>
        </div>
        <div class="clr"></div>
        <div style="clear:both;"></div>
    </div>
    <div class="left-container">
        <div id='actionRequestsDiv'>
            <g:set var="beforeOutstandingRequestsChart" value="${new Date()}"/>
            <care:outstandingRequestsChart workerType='${workerType}' viewType='${viewType}'/>
            <g:set var="afterOutstandingRequestsChart" value="${new Date()}"/>
        </div>
        <div style="clear:both;"></div>
        <g:set var="beforeActionRequestsResponseTimes" value="${new Date()}"/>
        <div style="padding-bottom:10px;">
            <g:if test="${!supervisorView}">
                <care:actionRequestsResponseTimes workerType='${workerType}'/>
            </g:if>
        </div>
        <g:set var="afterActionRequestsResponseTimes" value="${new Date()}"/>
        <div style="clear:both;"></div>
        <g:set var="beforeRss" value="${new Date()}"/>
        <div id=rssFeedDiv>
            <g:if test="${!supervisorView}">
                <care:rssFeed/>
            </g:if>
        </div>
        <g:set var="afterRss" value="${new Date()}"/>
    </div>
</div>
<%
    System.out.println "Time for certifications Expiration Widget : ${((afterCertificationsExpirations - beforeCertificationsExpirations) / 1000)} seconds."
    System.out.println "Time for News and Notes Widget : ${((afterNewsAndNotes - beforeNewsAndNotes) / 1000)} seconds."
    System.out.println "Time for Worker Expiration Outlook Widget : ${((afterWorkerExpirationOutlook - beforeWorkerExpirationOutlook) / 1000)} seconds."
    System.out.println "Time for Expiration forecast Widget : ${((afterExpirationForecast - beforeExpirationForecast) / 1000)} seconds."
    System.out.println "Time for Worker Status Chart Widget : ${((afterWorkerStatusChart - beforeWorkerStatusChart) / 1000)} seconds."
    System.out.println "Time for Feed Information Widget : ${((afterFeedInformation - beforeFeedInformation) / 1000)} seconds."
    System.out.println "Time for Outstanding Requests Widget : ${((afterOutstandingRequestsChart - beforeOutstandingRequestsChart) / 1000)} seconds."
    System.out.println "Time for Action Requests Response Times Widget : ${((afterActionRequestsResponseTimes - beforeActionRequestsResponseTimes) / 1000)} seconds."
    System.out.println "Time for RSS Widget : ${((afterRss - beforeRss) / 1000)} seconds."
%>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('#viewTypeGlobal').click(function() {
            jQuery("form#viewTypeForm").submit();
        });
        jQuery('#viewTypeSupervisor').click(function() {
            jQuery("form#viewTypeForm").submit();
        });
    });
</script>
</body>
</html>
