<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>Show HrInfo</title>
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
            <span class="menuButton"><g:link class="create" action="create">New HrInfo</g:link></span>
        </div>
        <div class="body">
            <h1>Show HrInfo</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name">Id:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'id')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SLID:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'slid')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Worker Number:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'workerNumber')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVPERNR:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_PERNR')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Supervisor SLID:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'supervisorSlid')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVFULLNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_FULL_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVFIRSTNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_FIRST_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVLASTNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_LAST_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVEMAIL:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_EMAIL')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVORGUNITNUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_ORGUNIT_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVORGUNITDESC:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPV_ORGUNIT_DESC')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVPERNR:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_PERNR')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVSLIDID:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_SLID_ID')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVFULLNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_FULL_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVFIRSTNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_FIRST_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVLASTNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_LAST_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVEMAIL:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_EMAIL')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVORGUNITNUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_ORGUNIT_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">SUPVSUPVORGUNITDESC:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'SUPVSUPV_ORGUNIT_DESC')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ATTR 1 FIELDTEXT:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ATTR1_FIELD_TEXT')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ATTR 2 FIELDCODE:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ATTR2_FIELD_CODE')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ATTR 2 FIELDTEXT:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ATTR2_FIELD_TEXT')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ATTR 3 FIELDCODE:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ATTR3_FIELD_CODE')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ATTR 3 FIELDTEXT:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ATTR3_FIELD_TEXT')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">BUILDING:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'BUILDING')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">CELLPHONENUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'CELL_PHONE_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">COSTCTRDESC:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'COST_CTR_DESC')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">COSTCTRNUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'COST_CTR_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">EMAIL:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'EMAIL')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">FIRSTNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'FIRST_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">FULLNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'FULL_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">INSERTDT:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'INSERT_DT')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">KNOWNAS:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'KNOWN_AS')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">LASTNAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'LAST_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">MAILSTOP:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'MAILSTOP')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">MANAGERFLAG:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'MANAGER_FLAG')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">MANAGERORGFLAG:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'MANAGER_ORG_FLAG')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">MIDDLENAME:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'MIDDLE_NAME')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">OFFICEPHONENUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'OFFICE_PHONE_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">OFFICEPHONEPRE:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'OFFICE_PHONE_PRE')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ORGUNITDESC:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ORGUNIT_DESC')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ORGUNITNUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ORGUNIT_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PAGERNUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PAGER_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSONENTITLEMENTROLE:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERSON_ENTITLEMENT_ROLE')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSONSTATUS:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERSON_STATUS')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSONTYPE:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERSON_TYPE')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSAREADESC:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERS_AREA_DESC')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSAREANUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERS_AREA_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSSUBAREADESC:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERS_SUBAREA_DESC')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">PERSSUBAREANUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'PERS_SUBAREA_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">POSITIONTITLE:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'POSITION_TITLE')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">ROOMNUM:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'ROOM_NUM')}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">Pernr:</td>

                        <td valign="top" class="value">${fieldValue(bean: hrInfoInstance, field: 'pernr')}</td>

                    </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${hrInfoInstance?.id}"/>
                    <span class="button"><g:actionSubmit class="edit" value="Edit"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
