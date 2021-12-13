<%@ page import="com.force5solutions.care.ldap.SecurityRole; com.force5solutions.care.cc.CentralApplicationRole; com.force5solutions.care.workflow.CentralWorkflowTaskType; com.force5solutions.care.cc.CentralWorkflowTaskTemplate" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <g:set var="entityName"
           value="${message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <script type="text/javascript">
        function updateMultiSelectBox(selectBoxName) {
            jQuery(function() {
                jQuery("#" + selectBoxName + "-select option").each(function(i) {
                    this.value = this.text;
                });
                jQuery('input[name=' + selectBoxName + ']').each(function() {
                    var valueToUse = jQuery(this).parent().text().trim()
                    jQuery(this).val(valueToUse)
                })
            });
        }
    </script>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
<h1><g:message code="default.create.label" args="[entityName]"/></h1>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:hasErrors bean="${centralWorkflowTaskTemplateInstance}">
    <div class="errors">
        <g:renderErrors bean="${centralWorkflowTaskTemplateInstance}" as="list"/>
    </div>
</g:hasErrors>
<g:form action="save" method="post">
<div class="dialog">
<table>
<tbody>
<tr class="prop">
    <td valign="top" class="name">
        <label for="templateName">ID</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'messageTemplate', 'errors')}">
        <g:textField name="templateName"
                     value="${centralWorkflowTaskTemplateInstance.id}" style="width:600px;"/>
    </td>
</tr>
%{----}%
<tr class="prop">
    <td valign="top" class="name">
        <label for="messageTemplate.id"><g:message
                code="centralWorkflowTaskTemplate.messageTemplate.label"
                default="Message Template"/></label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'messageTemplate', 'errors')}">
        <g:select name="messageTemplate.id"
                  from="${com.force5solutions.care.common.CentralMessageTemplate.list()}" optionKey="id"
                  value="${centralWorkflowTaskTemplateInstance?.messageTemplate?.id}"
                  noSelection="['':'(Select One)']" style="width:300px;" class="listbox"/>
    </td>
</tr>
%{----}%
<tr class="prop">
    <td valign="top" class="name">
        <label for="responseForm"><g:message code="centralWorkflowTaskTemplate.responseForm.label"
                                             default="Response Form"/></label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'responseForm', 'errors')}">
        <g:textField name="responseForm" value="${centralWorkflowTaskTemplateInstance?.responseForm}"
                     style="width:290px;"/><br/>
        <span>Ex: approveNewEntitlementPolicy, supervisorResponse, accessVerificationTask</span>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="period"><g:message code="centralWorkflowTaskTemplate.period.label"
                                       default="Period"/></label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'period', 'errors')}">
        <g:textField name="period"
                     value="${fieldValue(bean: centralWorkflowTaskTemplateInstance, field: 'period')}"
                     style="width:290px;"/>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="periodUnit"><g:message code="centralWorkflowTaskTemplate.periodUnit.label"
                                           default="Period Unit"/></label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'periodUnit', 'errors')}">
        <g:select name="periodUnit" from="${com.force5solutions.care.cc.PeriodUnit?.values()}"
                  value="${centralWorkflowTaskTemplateInstance?.periodUnit}" noSelection="['':'(Select One)']"
                  style="width:300px;" class="listbox"/>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="workflowTaskType"><g:message
                code="centralWorkflowTaskTemplate.workflowTaskType.label"
                default="Workflow Task Type"/></label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'workflowTaskType', 'errors')}">
        <g:select border="1px;" name="workflowTaskType" from="${CentralWorkflowTaskType?.values()}"
                  noSelection="['':'(Select One)']" value="${centralWorkflowTaskTemplateInstance?.workflowTaskType}" style="width:300px;" class="listbox"/>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="actions">Actions</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'actions', 'errors')}">
        <g:select name="actions" from="${['APPROVE', 'REJECT', 'CONFIRM']}" multiple="yes" size="3"
                  value="${centralWorkflowTaskTemplateInstance?.actions}" style="width:300px;border: 1px solid #000;"/>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="actorSlids">Actor Slids</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'actorSlids', 'errors')}">
        <g:textField name="actorSlids"
                     value="${centralWorkflowTaskTemplateInstance?.actorSlids}" style="width:290px;"/>
    </td>
</tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="toNotificationSlids">to Notification Slids</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'toNotificationSlids', 'errors')}">
        <g:textField name="toNotificationSlids"
                     value="${centralWorkflowTaskTemplateInstance?.toNotificationSlids}" style="width:290px;"/>
    </td>
</tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="toNotificationEmails">to Notification Emails</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'toNotificationEmails', 'errors')}">
        <g:textField name="toNotificationEmails"
                     value="${centralWorkflowTaskTemplateInstance?.toNotificationEmails}" style="width:290px;"/>
    </td>
</tr>

 <tr class="prop">
    <td valign="top" class="name">
        <label for="ccNotificationSlids">cc Notification Slids</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'ccNotificationSlids', 'errors')}">
        <g:textField name="ccNotificationSlids"
                     value="${centralWorkflowTaskTemplateInstance?.ccNotificationSlids}" style="width:290px;"/>
    </td>
</tr>


 <tr class="prop">
    <td valign="top" class="name">
        <label for="ccNotificationEmails">cc Notification Emails</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'ccNotificationEmails', 'errors')}">
        <g:textField name="ccNotificationEmails"
                     value="${centralWorkflowTaskTemplateInstance?.ccNotificationEmails}" style="width:290px;"/>
    </td>
</tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="actorSecurityRoles">Actor Security Roles</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'actorSecurityRoles', 'errors')}">
        <div style="width:300px;">
            <ui:multiSelect name="actorSecurityRoles" from="${SecurityRole.list()}"
                            noSelection="['':'(Select One)']"
                            class="listbox" style="width:300px;"
                            multiple="yes" optionKey="id" size="1"
                            value="${centralWorkflowTaskTemplateInstance?.actorSecurityRoles}"/>
        </div>
    </td>
</tr>
<tr class="prop">
    <td valign="top" class="name">
        <label for="actorApplicationRoles">Actor Application Roles</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'actorApplicationRoles', 'errors')}">
        <div style="width:300px;">
            <ui:multiSelect name="actorApplicationRoles" from="${CentralApplicationRole.list()}"
                            noSelection="['':'(Select One)']"
                            class="listbox" style="width:300px;"
                            multiple="yes" size="1"
                            value="${centralWorkflowTaskTemplateInstance?.actorApplicationRoles}"/>
        </div>
         <script type="text/javascript">
            updateMultiSelectBox('actorApplicationRoles')
        </script>
    </td>
</tr>
<tr class="prop">
    <td valign="top" class="name">
        <label for="toNotificationApplicationRoles">to Notification Application Roles</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'toNotificationApplicationRoles', 'errors')}">
        <div style="width:300px;">
            <ui:multiSelect name="toNotificationApplicationRoles" from="${CentralApplicationRole.list()}"
                            noSelection="['':'(Select One)']"
                            class="listbox" style="width:300px;"
                            multiple="yes" size="1"
                            value="${centralWorkflowTaskTemplateInstance?.toNotificationApplicationRoles}"/>
        </div>
         <script type="text/javascript">
            updateMultiSelectBox('toNotificationApplicationRoles')
        </script>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="ccNotificationApplicationRoles">cc Notification Application Roles</label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'ccNotificationApplicationRoles', 'errors')}">
        <div style="width:300px;">
            <ui:multiSelect name="ccNotificationApplicationRoles" from="${CentralApplicationRole.list()}"
                            noSelection="['':'(Select One)']"
                            class="listbox" style="width:300px;"
                            multiple="yes" size="1"
                            value="${centralWorkflowTaskTemplateInstance?.ccNotificationApplicationRoles}"/>
        </div>
         <script type="text/javascript">
            updateMultiSelectBox('ccNotificationApplicationRoles')
        </script>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="escalationTemplate.id"><g:message
                code="centralWorkflowTaskTemplate.escalationTemplate.label"
                default="Escalation Template"/></label>
    </td>
    <td valign="top"
        class="value ${hasErrors(bean: centralWorkflowTaskTemplateInstance, field: 'escalationTemplate', 'errors')}">
        <g:select name="escalationTemplate.id" style="width: 610px;"
                  from="${com.force5solutions.care.cc.CentralWorkflowTaskTemplate.list()}"
                  optionKey="id" optionValue="id" noSelection="['':'(Select One)']" value="${centralWorkflowTaskTemplateInstance?.escalationTemplate?.id}" class="listbox"/>
    </td>
</tr>

</tbody>
</table>
</div>

<div class="buttons">
    <span class="button"><g:submitButton name="create" class="save"
                                         value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
    <span class="button"> <g:actionSubmit class="save" value="Cancel" action="list"/></span>
</div>
</g:form>
</div>
</body>
</html>
