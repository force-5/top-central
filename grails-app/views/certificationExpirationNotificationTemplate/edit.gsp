
<%@ page import="com.force5solutions.care.cc.CentralWorkflowTaskTemplate; com.force5solutions.care.cc.CertificationExpirationNotificationTemplate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="contractor" />
        <g:set var="entityName" value="${message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    <style type="text/css">
        select{
            padding:0px;
            margin:0px;
            border: 1px solid #ccc;
        }
    </style>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${certificationExpirationNotificationTemplateInstance}">
            <div class="errors">
                <g:renderErrors bean="${certificationExpirationNotificationTemplateInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${certificationExpirationNotificationTemplateInstance?.id}" />
                <g:hiddenField name="version" value="${certificationExpirationNotificationTemplateInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="certification"><g:message code="certificationExpirationNotificationTemplate.certification.label" default="Certification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationExpirationNotificationTemplateInstance, field: 'certification', 'errors')}">
                                    <g:textField name="certification" value="${certificationExpirationNotificationTemplateInstance?.certification}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="notificationPeriodInDays"><g:message code="certificationExpirationNotificationTemplate.notificationPeriodInDays.label" default="Notification Period In Days" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationExpirationNotificationTemplateInstance, field: 'notificationPeriodInDays', 'errors')}">
                                    <g:textField name="notificationPeriodInDays" value="${certificationExpirationNotificationTemplateInstance?.notificationPeriodInDays}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="taskTemplateName"><g:message code="certificationExpirationNotificationTemplate.taskTemplateName.label" default="Task Template Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: certificationExpirationNotificationTemplateInstance, field: 'taskTemplateName', 'errors')}">
                                    <g:select name="taskTemplateName" value="${certificationExpirationNotificationTemplateInstance?.taskTemplateName}" from="${CentralWorkflowTaskTemplate.list()*.id}"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
