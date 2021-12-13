<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="companyName">Company Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'companyName', 'errors')}">
                <input type="text" id="companyName" name="companyName" value="${fieldValue(bean: vendorInstance, field: 'companyName')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="addressLine1">Address Line1&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'addressLine1', 'errors')}">
                <input type="text" id="addressLine1" name="addressLine1" value="${fieldValue(bean: vendorInstance, field: 'addressLine1')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="addressLine2">Address Line2</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'addressLine2', 'errors')}">
                <input type="text" id="addressLine2" name="addressLine2" value="${fieldValue(bean: vendorInstance, field: 'addressLine2')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="city">City&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'city', 'errors')}">
                <input type="text" id="city" name="city" value="${fieldValue(bean: vendorInstance, field: 'city')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="state">State&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'state', 'errors')}">
                <input type="text" id="state" name="state" value="${stateMap.get(vendorInstance?.state) ?: vendorInstance?.state}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="zipCode">Zip Code&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'zipCode', 'errors')}">
                <input type="text" id="zipCode" name="zipCode" value="${fieldValue(bean: vendorInstance, field: 'zipCode')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="phone">Phone&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'phone', 'errors')}">
                <input type="text" id="phone" name="phone" value="${fieldValue(bean: vendorInstance, field: 'phone')}"/>
            </td>
        </tr>


        <tr class="prop">
            <td valign="top" class="name">
                <label for="notes">Notes</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'notes', 'errors')}">
                <textarea id="notes" name="notes" cols="" class="area" style="width:237px; height:50px; " rows="3">${fieldValue(bean: vendorInstance, field: 'notes')}</textarea>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="agreementExpirationDate">Master Agreement Expiration Date:</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: vendorInstance, field: 'agreementExpirationDate', 'errors')}">
                <calendar:datePicker name="agreementExpirationDate" id="agreementExpirationDate"
                        value="${vendorInstance?.agreementExpirationDate}"/>
            </td>
        </tr>

        </tbody>
    </table>
</div>
<script type="text/javascript">
    var vendors = "${(vendors) ? ((vendors*.companyName).join(',')) : null}".split(",");
    jQuery("#companyName").autocomplete(vendors);

    var stateList = "${((stateMap.keySet() as List)+stateMap.values()).join(',')}".split(",");
    jQuery("#state").autocomplete(stateList);
</script>
