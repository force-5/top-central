<%@ page import="com.force5solutions.care.cc.BusinessUnit; org.codehaus.groovy.grails.commons.ConfigurationHolder;" %>

<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="firstName">First Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: instance, field: 'firstName', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="firstName" name="firstName"
                           value="${fieldValue(bean: instance, field: 'firstName')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="firstName" name="firstName" class="readOnlyField"
                           value="${fieldValue(bean: instance, field: 'firstName')}" readonly="true"/>
                </g:else>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="middleName">Middle Name:</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: instance, field: 'middleName', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="middleName" name="middleName"
                           value="${fieldValue(bean: instance, field: 'middleName')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="middleName" name="middleName" class="readOnlyField"
                           value="${fieldValue(bean: instance, field: 'middleName')}" readonly="true"/>
                </g:else>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="lastName">Last Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: instance, field: 'lastName', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="lastName" name="lastName"
                           value="${fieldValue(bean: instance, field: 'lastName')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="lastName" name="lastName" class="readOnlyField"
                           value="${fieldValue(bean: instance, field: 'lastName')}" readonly="true"/>
                </g:else>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="phone">Phone&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: instance, field: 'phone', 'errors')}">
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                    <input type="text" id="phone" name="phone" value="${fieldValue(bean: instance, field: 'phone')}"/>
                </g:if>
                <g:else>
                    <input type="text" id="phone" name="phone" class="readOnlyField"
                           value="${fieldValue(bean: instance, field: 'phone')}" readonly="true"/>
                </g:else>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="slid">Contractor SLID&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: instance, field: 'slid', 'errors')}">
                <g:if test="${fail == 'true'}">
                    <input type="text" id="slid" name="slid" value="${fieldValue(bean: instance, field: 'slid')}"
                           readonly="true"/>
                </g:if>
                <g:else>
                    <input type="text" id="slid" name="slid" value="${fieldValue(bean: instance, field: 'slid')}"/>
                </g:else>
                <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable != 'true'}">
                    <a id="checkSlid" class="filterbutton" href="#" onclick="checkSlid(jQuery('#slid').val());">
                        <span>Check SLID</span>
                    </a>
                </g:if>
                <div id="errorMessage" style="display:none; color: red;">No Contractor found with this SLID</div>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="notes">Notes:</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: instance, field: 'notes', 'errors')}">
                <textarea name="notes" id="notes" cols="" class="area" style="width:237px; height:50px;"
                          rows="3">${fieldValue(bean: instance, field: 'notes')}</textarea>
            </td>
        </tr>

        </tbody>
    </table>
</div>
<script type="text/javascript">
    function checkSlid(slid) {
        emptyValues();
        jQuery.getJSON('${createLink(action: "checkSlid")}', {slid:slid}, function (jsonData) {
            if (jsonData.fail != 'true') {
                populateValues(jsonData);
                jQuery('#errorMessage').hide();
            } else {
                jQuery('#errorMessage').show();
            }
        });
    }
</script>