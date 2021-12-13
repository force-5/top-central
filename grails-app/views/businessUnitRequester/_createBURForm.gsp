<%@ page import="com.force5solutions.care.cc.BusinessUnit; org.codehaus.groovy.grails.commons.ConfigurationHolder;" %>
<div class="form-section">
    <div class="error-status">
        <g:if test="${instance.hasErrors()}">
            Please enter valid values in the required fields
        </g:if>
    </div>
    <div class="namerowbig"><span>Business Unit</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'unit', 'errors')}">
            <g:select name="unit" class="${hasErrors(bean: instance, field: 'unit', 'errors ')} listbox"
                    style="width:140px;" id="unit" from="${BusinessUnit.list()*.name}"/>
        </label>
    </div>
    <div class="namerowbig"><span>First Name</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'firstName', 'errors')}">

            <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                <input type="text" class="inp" style="width:135px;" name="firstName" id="firstName" value="${fieldValue(bean: instance, field: 'firstName')}"/>
            </g:if>
            <g:else>
                <input type="text" class="inp" style="width:135px;" name="firstName" id="firstName" value="${fieldValue(bean: instance, field: 'firstName')}" readonly="true"/>
            </g:else>

        </label>
    </div>
    <div class="namerowbig"><span>Middle Name</span>
        <label class="${hasErrors(bean: instance, field: 'middleName', 'errors')}">

            <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                <input type="text" class="inp" style="width:135px;" name="middleName" id="middleName" value="${fieldValue(bean: instance, field: 'middleName')}"/>
            </g:if>
            <g:else>
                <input type="text" class="inp" style="width:135px;" name="middleName" id="middleName" value="${fieldValue(bean: instance, field: 'middleName')}" readonly="true"/>
            </g:else>

        </label>
    </div>
    <div class="namerowbig"><span>Last Name</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'lastName', 'errors')}">

            <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                <input type="text" class="inp" style="width:135px;" name="lastName" id="lastName" value="${fieldValue(bean: instance, field: 'lastName')}"/>
            </g:if>
            <g:else>
                <input type="text" class="inp" style="width:135px;" name="lastName" id="lastName" value="${fieldValue(bean: instance, field: 'lastName')}" readonly="true"/>
            </g:else>

        </label>
    </div>
    <div class="namerowbig"><span>Phone</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'phone', 'errors')}">
            <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable == 'true'}">
                <input type="text" class="inp" style="width:135px;" name="phone" id="phone" value="${fieldValue(bean: instance, field: 'phone')}"/>
            </g:if>
            <g:else>
                <input type="text" class="inp" style="width:135px;" name="phone" id="phone" value="${fieldValue(bean: instance, field: 'phone')}" readonly="true"/>
            </g:else>

        </label>
    </div>
    <div class="namerowbig"><span>SLID</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'slid', 'errors')}">

            <g:if test="${fail == 'true'}">
                <input type="text" class="inp" style="width:135px;" name="slid" id="slid" value="${fieldValue(bean: instance, field: 'slid')}" readonly="true"/>
            </g:if>
            <g:else>
                <input type="text" class="inp" style="width:135px;" name="slid" id="slid" value="${fieldValue(bean: instance, field: 'slid')}"/>
            </g:else>

        </label>
    </div>
    <div class="clearfix" style="text-align:right;">
        <g:if test="${ConfigurationHolder?.config?.isEmployeeEditable != 'true'}">
            <a id="checkSlid" class="filterbutton" href="#" onclick="checkSlid(jQuery('#slid').val());">
                <span>Check SLID</span>
            </a>
        </g:if>
    </div>
    <div id="errorMessage" style="display:none; color: red;" class="clearfix">No Employee found with this SLID</div>
    <div class="namerowbig"><span>Notes:</span>
        <label class="${hasErrors(bean: instance, field: 'notes', 'errors')}" style="height:auto;">
            <input name="notes" class="area" style="width:135px;" id="name" value="${fieldValue(bean: instance, field: 'notes')}"/>
        </label>
    </div>
</div>
<div id="submit-button">
    <input type="submit" class="button" value="Create"/> &nbsp; &nbsp; <input type="button" class="button" value="Cancel" onclick="jQuery.modal.close();"/>
</div>
<div><br/>
    &nbsp;<span style="color:red;">*</span> indicates a required field</div>
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