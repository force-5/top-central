<%@ page import="com.force5solutions.care.cc.CentralWorkflowTaskTemplate" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName"
           value="${message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.messageTemplate.label"
                                                         default="ID"/></td>

                <td valign="top" class="value">${centralWorkflowTaskTemplateInstance?.id}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.messageTemplate.label"
                                                         default="Message Template"/></td>

                <td valign="top" class="value"><g:link controller="centralMessageTemplate" action="show"
                                                       id="${centralWorkflowTaskTemplateInstance?.messageTemplate?.id}">${centralWorkflowTaskTemplateInstance?.messageTemplate?.encodeAsHTML()}</g:link></td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.responseForm.label"
                                                         default="Response Form"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "responseForm")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.period.label"
                                                         default="Period"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "period")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.periodUnit.label"
                                                         default="Period Unit"/></td>

                <td valign="top" class="value">${centralWorkflowTaskTemplateInstance?.periodUnit?.encodeAsHTML()}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.workflowTaskType.label"
                                                         default="Workflow Task Type"/></td>

                <td valign="top"
                    class="value">${centralWorkflowTaskTemplateInstance?.workflowTaskType?.name?.encodeAsHTML()}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.actions.label"
                                                         default="Actions"/></td>

                <td valign="top" class="value">${centralWorkflowTaskTemplateInstance?.actions?.join(", ")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.slids.label"
                                                         default="Actor Slids"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "actorSlids")}</td>

            </tr>



            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.slids.label"
                                                         default="to Notification Slids"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "toNotificationSlids")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.slids.label"
                                                         default="cc Notification Slids"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "ccNotificationSlids")}</td>

            </tr>
            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.slids.label"
                                                         default="to Notification Emails"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "toNotificationEmails")}</td>

            </tr>
            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.slids.label"
                                                         default="cc Notification Emails"/></td>

                <td valign="top"
                    class="value">${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: "ccNotificationEmails")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.escalationTemplate.label"
                                                         default="Escalation Template"/></td>

                <td valign="top" class="value"><g:link controller="centralWorkflowTaskTemplate" action="show"
                                                       id="${centralWorkflowTaskTemplateInstance?.escalationTemplate?.id}">${centralWorkflowTaskTemplateInstance?.escalationTemplate?.id?.encodeAsHTML()}</g:link></td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.applicationRoles.label"
                                                         default="Application Roles"/></td>

                <td valign="top"
                    class="value">${centralWorkflowTaskTemplateInstance?.actorApplicationRoles?.join(", ")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="centralWorkflowTaskTemplate.securityRoles.label"
                                                         default="Security Roles"/></td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${centralWorkflowTaskTemplateInstance.actorSecurityRoles}" var="s">
                            <li><g:link controller="securityRole" action="show"
                                        id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                </td>

            </tr>

              <tr class="prop">
                <td valign="top" class="name">Actor Application Roles</td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${centralWorkflowTaskTemplateInstance?.actorApplicationRoles}" var="s">
                            <li>${s?.encodeAsHTML()}</li>
                        </g:each>
                    </ul>
                </td>

            </tr>

             <tr class="prop">
                <td valign="top" class="name">to Notification Application Roles</td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${centralWorkflowTaskTemplateInstance?.toNotificationApplicationRoles}" var="s">
                            <li>${s?.encodeAsHTML()}</li>
                        </g:each>
                    </ul>
                </td>

            </tr>

             <tr class="prop">
                <td valign="top" class="name">cc Notification Application Roles</td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${centralWorkflowTaskTemplateInstance?.ccNotificationApplicationRoles}" var="s">
                            <li>${s?.encodeAsHTML()}</li>
                        </g:each>
                    </ul>
                </td>

            </tr>

            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${centralWorkflowTaskTemplateInstance?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
            <span class="button"><g:actionSubmit class="edit" action="cloneTemplate"
                                                 value="Clone Template"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
