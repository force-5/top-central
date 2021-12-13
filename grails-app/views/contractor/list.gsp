<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder; com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Contractor List</title>
</head>

<body>
<br/>

<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" controller="contractor"
                                             action="addContractor">Add Contractor</g:link></span>
            <g:if test="${(ConfigurationHolder.config.isContractorEditable == 'true')}">
                <g:if test="${care.hasPermission(permission: Permission.CREATE_CONTRACTOR_PROFILE)}">
                    <span class="menuButton"><g:link class="create createContractorLink" controller="contractor"
                                                     action="create">New Contractor</g:link></span>
                </g:if>
                <g:else>
                    <span class="menuButton"><a class="create-disabled createContractorLink">New Contractor</a></span>
                </g:else>
            </g:if>
        </div>

        <div class="body">
            <h1>Contractor List
                <a href="#" class="filterbutton" id="filterButton"><span>Filter</span></a>
                <g:if test="${session.filterContractorCommand}">
                    <g:link class="filterbutton" controller="contractor" action="showAllContractor">
                        <span>Show All</span></g:link>
                </g:if>
                <span style="float: right;color: #666666;">Show: <g:select
                        from="[10, 25, 50, 100, 'Unlimited']"
                        name="rowCount"
                        id="rowCount" value="${max}"/></span>
            </h1>

            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="contractorsTable"
                          model="[contractorInstanceList: contractorInstanceList, contractorInstanceTotal: contractorInstanceTotal, offset: offset, max: max, order: order, sort: sort]"/>

            </div>
        </div>

        <div id="filterDialog" class="popupWindowContractorFilter">
        </div>

    </div>
</div>
</body>
</html>
