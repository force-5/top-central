<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>Create HrInfo</title>
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
            <span class="menuButton"><g:link class="list" action="list">HrInfo List</g:link></span>
        </div>
        <div class="body">
            <h1>Create HrInfo</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${hrInfoInstance}">
                <div class="errors">
                    <g:renderErrors bean="${hrInfoInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form action="save" method="post">
                <div class="dialog">
                    <table>
                        <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="slid">SLID:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'slid', 'errors')}">
                                <input type="text" id="slid" name="slid" value="${fieldValue(bean: hrInfoInstance, field: 'slid')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="workerNumber">Worker Number:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'workerNumber', 'errors')}">
                                <input type="text" id="workerNumber" name="workerNumber" value="${fieldValue(bean: hrInfoInstance, field: 'workerNumber')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_PERNR">SUPVPERNR:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_PERNR', 'errors')}">
                                <input type="text" id="SUPV_PERNR" name="SUPV_PERNR" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_PERNR')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="supervisorSlid">Supervisor Slid:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'supervisorSlid', 'errors')}">
                                <input type="text" id="supervisorSlid" name="supervisorSlid" value="${fieldValue(bean: hrInfoInstance, field: 'supervisorSlid')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_FULL_NAME">SUPVFULLNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_FULL_NAME', 'errors')}">
                                <input type="text" id="SUPV_FULL_NAME" name="SUPV_FULL_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_FULL_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_FIRST_NAME">SUPVFIRSTNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_FIRST_NAME', 'errors')}">
                                <input type="text" id="SUPV_FIRST_NAME" name="SUPV_FIRST_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_FIRST_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_LAST_NAME">SUPVLASTNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_LAST_NAME', 'errors')}">
                                <input type="text" id="SUPV_LAST_NAME" name="SUPV_LAST_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_LAST_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_EMAIL">SUPVEMAIL:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_EMAIL', 'errors')}">
                                <input type="text" id="SUPV_EMAIL" name="SUPV_EMAIL" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_EMAIL')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_ORGUNIT_NUM">SUPVORGUNITNUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_ORGUNIT_NUM', 'errors')}">
                                <input type="text" id="SUPV_ORGUNIT_NUM" name="SUPV_ORGUNIT_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_ORGUNIT_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPV_ORGUNIT_DESC">SUPVORGUNITDESC:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPV_ORGUNIT_DESC', 'errors')}">
                                <input type="text" id="SUPV_ORGUNIT_DESC" name="SUPV_ORGUNIT_DESC" value="${fieldValue(bean: hrInfoInstance, field: 'SUPV_ORGUNIT_DESC')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_PERNR">SUPVSUPVPERNR:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_PERNR', 'errors')}">
                                <input type="text" id="SUPVSUPV_PERNR" name="SUPVSUPV_PERNR" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_PERNR')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_SLID_ID">SUPVSUPVSLIDID:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_SLID_ID', 'errors')}">
                                <input type="text" id="SUPVSUPV_SLID_ID" name="SUPVSUPV_SLID_ID" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_SLID_ID')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_FULL_NAME">SUPVSUPVFULLNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_FULL_NAME', 'errors')}">
                                <input type="text" id="SUPVSUPV_FULL_NAME" name="SUPVSUPV_FULL_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_FULL_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_FIRST_NAME">SUPVSUPVFIRSTNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_FIRST_NAME', 'errors')}">
                                <input type="text" id="SUPVSUPV_FIRST_NAME" name="SUPVSUPV_FIRST_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_FIRST_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_LAST_NAME">SUPVSUPVLASTNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_LAST_NAME', 'errors')}">
                                <input type="text" id="SUPVSUPV_LAST_NAME" name="SUPVSUPV_LAST_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_LAST_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_EMAIL">SUPVSUPVEMAIL:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_EMAIL', 'errors')}">
                                <input type="text" id="SUPVSUPV_EMAIL" name="SUPVSUPV_EMAIL" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_EMAIL')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_ORGUNIT_NUM">SUPVSUPVORGUNITNUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_ORGUNIT_NUM', 'errors')}">
                                <input type="text" id="SUPVSUPV_ORGUNIT_NUM" name="SUPVSUPV_ORGUNIT_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_ORGUNIT_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="SUPVSUPV_ORGUNIT_DESC">SUPVSUPVORGUNITDESC:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'SUPVSUPV_ORGUNIT_DESC', 'errors')}">
                                <input type="text" id="SUPVSUPV_ORGUNIT_DESC" name="SUPVSUPV_ORGUNIT_DESC" value="${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_ORGUNIT_DESC')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ATTR1_FIELD_TEXT">ATTR 1 FIELDTEXT:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ATTR1_FIELD_TEXT', 'errors')}">
                                <input type="text" id="ATTR1_FIELD_TEXT" name="ATTR1_FIELD_TEXT" value="${fieldValue(bean: hrInfoInstance, field: 'ATTR1_FIELD_TEXT')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ATTR2_FIELD_CODE">ATTR 2 FIELDCODE:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ATTR2_FIELD_CODE', 'errors')}">
                                <input type="text" id="ATTR2_FIELD_CODE" name="ATTR2_FIELD_CODE" value="${fieldValue(bean: hrInfoInstance, field: 'ATTR2_FIELD_CODE')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ATTR2_FIELD_TEXT">ATTR 2 FIELDTEXT:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ATTR2_FIELD_TEXT', 'errors')}">
                                <input type="text" id="ATTR2_FIELD_TEXT" name="ATTR2_FIELD_TEXT" value="${fieldValue(bean: hrInfoInstance, field: 'ATTR2_FIELD_TEXT')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ATTR3_FIELD_CODE">ATTR 3 FIELDCODE:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ATTR3_FIELD_CODE', 'errors')}">
                                <input type="text" id="ATTR3_FIELD_CODE" name="ATTR3_FIELD_CODE" value="${fieldValue(bean: hrInfoInstance, field: 'ATTR3_FIELD_CODE')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ATTR3_FIELD_TEXT">ATTR 3 FIELDTEXT:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ATTR3_FIELD_TEXT', 'errors')}">
                                <input type="text" id="ATTR3_FIELD_TEXT" name="ATTR3_FIELD_TEXT" value="${fieldValue(bean: hrInfoInstance, field: 'ATTR3_FIELD_TEXT')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="BUILDING">BUILDING:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'BUILDING', 'errors')}">
                                <input type="text" id="BUILDING" name="BUILDING" value="${fieldValue(bean: hrInfoInstance, field: 'BUILDING')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="CELL_PHONE_NUM">CELLPHONENUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'CELL_PHONE_NUM', 'errors')}">
                                <input type="text" id="CELL_PHONE_NUM" name="CELL_PHONE_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'CELL_PHONE_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="COST_CTR_DESC">COSTCTRDESC:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'COST_CTR_DESC', 'errors')}">
                                <input type="text" id="COST_CTR_DESC" name="COST_CTR_DESC" value="${fieldValue(bean: hrInfoInstance, field: 'COST_CTR_DESC')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="COST_CTR_NUM">COSTCTRNUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'COST_CTR_NUM', 'errors')}">
                                <input type="text" id="COST_CTR_NUM" name="COST_CTR_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'COST_CTR_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="EMAIL">EMAIL:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'EMAIL', 'errors')}">
                                <input type="text" id="EMAIL" name="EMAIL" value="${fieldValue(bean: hrInfoInstance, field: 'EMAIL')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="FIRST_NAME">FIRSTNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'FIRST_NAME', 'errors')}">
                                <input type="text" id="FIRST_NAME" name="FIRST_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'FIRST_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="FULL_NAME">FULLNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'FULL_NAME', 'errors')}">
                                <input type="text" id="FULL_NAME" name="FULL_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'FULL_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="INSERT_DT">INSERTDT:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'INSERT_DT', 'errors')}">
                                <g:datePicker name="INSERT_DT" value="${hrInfoInstance?.INSERT_DT}" precision="minute"></g:datePicker>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="KNOWN_AS">KNOWNAS:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'KNOWN_AS', 'errors')}">
                                <input type="text" id="KNOWN_AS" name="KNOWN_AS" value="${fieldValue(bean: hrInfoInstance, field: 'KNOWN_AS')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="LAST_NAME">LASTNAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'LAST_NAME', 'errors')}">
                                <input type="text" id="LAST_NAME" name="LAST_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'LAST_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="MAILSTOP">MAILSTOP:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'MAILSTOP', 'errors')}">
                                <input type="text" id="MAILSTOP" name="MAILSTOP" value="${fieldValue(bean: hrInfoInstance, field: 'MAILSTOP')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="MANAGER_FLAG">MANAGERFLAG:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'MANAGER_FLAG', 'errors')}">
                                <input type="text" id="MANAGER_FLAG" name="MANAGER_FLAG" value="${fieldValue(bean: hrInfoInstance, field: 'MANAGER_FLAG')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="MANAGER_ORG_FLAG">MANAGERORGFLAG:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'MANAGER_ORG_FLAG', 'errors')}">
                                <input type="text" id="MANAGER_ORG_FLAG" name="MANAGER_ORG_FLAG" value="${fieldValue(bean: hrInfoInstance, field: 'MANAGER_ORG_FLAG')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="MIDDLE_NAME">MIDDLENAME:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'MIDDLE_NAME', 'errors')}">
                                <input type="text" id="MIDDLE_NAME" name="MIDDLE_NAME" value="${fieldValue(bean: hrInfoInstance, field: 'MIDDLE_NAME')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="OFFICE_PHONE_NUM">OFFICEPHONENUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'OFFICE_PHONE_NUM', 'errors')}">
                                <input type="text" id="OFFICE_PHONE_NUM" name="OFFICE_PHONE_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'OFFICE_PHONE_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="OFFICE_PHONE_PRE">OFFICEPHONEPRE:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'OFFICE_PHONE_PRE', 'errors')}">
                                <input type="text" id="OFFICE_PHONE_PRE" name="OFFICE_PHONE_PRE" value="${fieldValue(bean: hrInfoInstance, field: 'OFFICE_PHONE_PRE')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ORGUNIT_DESC">ORGUNITDESC:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ORGUNIT_DESC', 'errors')}">
                                <input type="text" id="ORGUNIT_DESC" name="ORGUNIT_DESC" value="${fieldValue(bean: hrInfoInstance, field: 'ORGUNIT_DESC')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ORGUNIT_NUM">ORGUNITNUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ORGUNIT_NUM', 'errors')}">
                                <input type="text" id="ORGUNIT_NUM" name="ORGUNIT_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'ORGUNIT_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PAGER_NUM">PAGERNUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PAGER_NUM', 'errors')}">
                                <input type="text" id="PAGER_NUM" name="PAGER_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'PAGER_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERSON_ENTITLEMENT_ROLE">PERSONENTITLEMENTROLE:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERSON_ENTITLEMENT_ROLE', 'errors')}">
                                <input type="text" id="PERSON_ENTITLEMENT_ROLE" name="PERSON_ENTITLEMENT_ROLE" value="${fieldValue(bean: hrInfoInstance, field: 'PERSON_ENTITLEMENT_ROLE')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERSON_STATUS">PERSONSTATUS:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERSON_STATUS', 'errors')}">
                                <input type="text" id="PERSON_STATUS" name="PERSON_STATUS" value="${fieldValue(bean: hrInfoInstance, field: 'PERSON_STATUS')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERSON_TYPE">PERSONTYPE:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERSON_TYPE', 'errors')}">
                                <input type="text" id="PERSON_TYPE" name="PERSON_TYPE" value="${fieldValue(bean: hrInfoInstance, field: 'PERSON_TYPE')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERS_AREA_DESC">PERSAREADESC:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERS_AREA_DESC', 'errors')}">
                                <input type="text" id="PERS_AREA_DESC" name="PERS_AREA_DESC" value="${fieldValue(bean: hrInfoInstance, field: 'PERS_AREA_DESC')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERS_AREA_NUM">PERSAREANUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERS_AREA_NUM', 'errors')}">
                                <input type="text" id="PERS_AREA_NUM" name="PERS_AREA_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'PERS_AREA_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERS_SUBAREA_DESC">PERSSUBAREADESC:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERS_SUBAREA_DESC', 'errors')}">
                                <input type="text" id="PERS_SUBAREA_DESC" name="PERS_SUBAREA_DESC" value="${fieldValue(bean: hrInfoInstance, field: 'PERS_SUBAREA_DESC')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="PERS_SUBAREA_NUM">PERSSUBAREANUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'PERS_SUBAREA_NUM', 'errors')}">
                                <input type="text" id="PERS_SUBAREA_NUM" name="PERS_SUBAREA_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'PERS_SUBAREA_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="POSITION_TITLE">POSITIONTITLE:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'POSITION_TITLE', 'errors')}">
                                <input type="text" id="POSITION_TITLE" name="POSITION_TITLE" value="${fieldValue(bean: hrInfoInstance, field: 'POSITION_TITLE')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="ROOM_NUM">ROOMNUM:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'ROOM_NUM', 'errors')}">
                                <input type="text" id="ROOM_NUM" name="ROOM_NUM" value="${fieldValue(bean: hrInfoInstance, field: 'ROOM_NUM')}"/>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">
                                <label for="pernr">Pernr:</label>
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: hrInfoInstance, field: 'pernr', 'errors')}">
                                <input type="text" id="pernr" name="pernr" value="${fieldValue(bean: hrInfoInstance, field: 'pernr')}"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create"/></span>
                </div>
            </g:form>
        </div>
</body>
</html>
