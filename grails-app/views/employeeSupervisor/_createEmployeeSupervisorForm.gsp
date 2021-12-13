<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder;" %>
<div class="dialog">
    <table>
        <tbody>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="firstName">First Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeSupervisor, field: 'firstName', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="firstName" name="firstName" value="${fieldValue(bean: employeeSupervisor, field: 'firstName')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="firstName" name="firstName" class="readOnlyField" value="${fieldValue(bean: employeeSupervisor, field: 'firstName')}" readonly="true"/>
                </g:else>
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="middleName">Middle Name</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeSupervisor, field: 'middleName', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="middleName" name="middleName" value="${fieldValue(bean: employeeSupervisor, field: 'middleName')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="middleName" name="middleName" class="readOnlyField" value="${fieldValue(bean: employeeSupervisor, field: 'middleName')}" readonly="true"/>
                </g:else>
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="lastName">Last Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeSupervisor, field: 'lastName', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="lastName" name="lastName" value="${fieldValue(bean: employeeSupervisor, field: 'lastName')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="lastName" name="lastName" class="readOnlyField" value="${fieldValue(bean: employeeSupervisor, field: 'lastName')}" readonly="true"/>
                </g:else>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="phone">Phone&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeSupervisor, field: 'phone', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="phone" name="phone" value="${fieldValue(bean: employeeSupervisor, field: 'phone')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="phone" name="phone" class="readOnlyField" value="${fieldValue(bean: employeeSupervisor, field: 'phone')}" readonly="true"/>
                </g:else>
            </td>
        </tr>


        <tr class="prop">
            <td valign="top" class="name">
                <label for="slid">SLID&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeSupervisor, field: 'slid', 'errors')}">
                <g:if test="${fail == 'true'}">
                    <input type="text" id="slid" name="slid" value="${fieldValue(bean: employeeSupervisor, field: 'slid')}" readonly="true"/>
                </g:if>
                <g:else>
                    <input type="text" id="slid" name="slid" value="${fieldValue(bean: employeeSupervisor, field: 'slid')}"/>
                </g:else>
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable != 'true'}">
                    <a id="checkSlid" class="filterbutton" href="#" onclick="checkSlid(jQuery('#slid').val());">
                        <span>Check Slid</span>
                    </a>
                </g:if>
                <div id="errorMessage" style="display:none; color: red;">No Employee found with this slid</div>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="notes">Notes</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeSupervisor, field: 'notes', 'errors')}">
                <textarea id="notes" name="notes" cols="" class="area" style="width:237px; height:50px; " rows="3">${fieldValue(bean: employeeSupervisor, field: 'notes')}</textarea>
            </td>
        </tr>

        </tbody>
    </table>
</div>
<script type="text/javascript">
    function checkSlid(slid) {
        emptyValues();
        jQuery.getJSON('${createLink(action: "checkSlid")}', {slid:slid}, function(jsonData) {
            if (jsonData.fail != 'true') {
                populateValues(jsonData);
                jQuery('#errorMessage').hide();
            } else {
                jQuery('#errorMessage').show();
            }
        });
    }
</script>