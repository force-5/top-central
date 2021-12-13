
<%@ page import="com.force5solutions.care.cc.CentralWorkflowTaskTemplate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="contractor" />
        <g:set var="entityName" value="${message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div>
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div>
            <h1><g:message code="default.list.label" args="[entityName]" />
                <span style="float: right;color: #666666;">Show: <g:select from="[10, 25, 50, 100, 'Unlimited']"
                                                                           name="rowCount"
                                                                           id="rowCount" value="${max}"/></span>
            </h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="centralWorkflowTaskTemplatesTable"
                          model="[filteredTemplateList: filteredTemplateList, filteredTemplateListCount: filteredTemplateListCount, offset: offset, max: max, order: order, sort: sort]"/>
            </div>
        </div>
    </body>
</html>
