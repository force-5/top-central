
<%@ page import="com.force5solutions.care.cc.CertificationExpirationNotificationTemplate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="contractor" />
        <g:set var="entityName" value="${message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="certification" title="${message(code: 'certificationExpirationNotificationTemplate.certification.label', default: 'Certification')}" />
                        
                            <g:sortableColumn property="notificationPeriodInDays" title="${message(code: 'certificationExpirationNotificationTemplate.notificationPeriodInDays.label', default: 'Notification Period In Days')}" />
                        
                            <g:sortableColumn property="taskTemplateName" title="${message(code: 'certificationExpirationNotificationTemplate.taskTemplateName.label', default: 'Task Template Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${certificationExpirationNotificationTemplateInstanceList}" status="i" var="certificationExpirationNotificationTemplateInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${certificationExpirationNotificationTemplateInstance.id}">${fieldValue(bean: certificationExpirationNotificationTemplateInstance, field: "certification")}</g:link></td>

                        
                            <td><g:link action="show" id="${certificationExpirationNotificationTemplateInstance.id}">${fieldValue(bean: certificationExpirationNotificationTemplateInstance, field: "notificationPeriodInDays")}</g:link></td>

                        
                            <td><g:link action="show" id="${certificationExpirationNotificationTemplateInstance.id}">${fieldValue(bean: certificationExpirationNotificationTemplateInstance, field: "taskTemplateName")}</g:link></td>

                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${certificationExpirationNotificationTemplateInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
