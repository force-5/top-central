<div class="form-section" style="width:310px;padding-bottom: 5px;">
    <g:render template="/shared/errors" model="[instance: instance]"/>
    <div class="namerowbig"><span>Company Name</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'companyName', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="companyName" name="companyName" value="${fieldValue(bean: instance, field: 'companyName')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Address Line 1</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'addressLine1', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="addressLine1" name="addressLine1" value="${fieldValue(bean: instance, field: 'addressLine1')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Address Line 2</span>
        <label class="${hasErrors(bean: instance, field: 'addressLine2', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="addressLine2" name="addressLine2" value="${fieldValue(bean: instance, field: 'addressLine2')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>City</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'city', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="city" name="city" value="${fieldValue(bean: instance, field: 'city')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>State</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'state', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="state" name="state" value="${stateMap.get(instance?.state) ?: instance?.state}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Zip Code</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'zipCode', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="zipCode" name="zipCode" value="${fieldValue(bean: instance, field: 'zipCode')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Phone</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: instance, field: 'phone', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="phone" name="phone" value="${fieldValue(bean: instance, field: 'phone')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Notes</span>
        <label class="${hasErrors(bean: instance, field: 'notes', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="notes" name="notes" value="${fieldValue(bean: instance, field: 'notes')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Master Agreement Expiration Date</span>
        <label class="${hasErrors(bean: instance, field: 'agreementExpirationDate', 'errors')}">
            <calendar:datePicker name="agreementExpirationDate" id="agreementExpirationDate"
                    value="${instance?.agreementExpirationDate}"/>
        </label>
    </div>
</div>
<div id="submit-button">
    <input type="submit" class="button" value="Create"/> &nbsp; &nbsp; <input type="button" class="button" value="Cancel" onclick="jQuery.modal.close();"/>
</div>
<div><br/>
    &nbsp;<span style="color:red;">*</span> indicates a required field</div>

<script type="text/javascript">
    var vendors = "${(vendors) ? ((vendors*.companyName).join(',')) : null}".split(",");
    jQuery("#companyName").autocomplete(vendors);

    var stateList = "${((stateMap.keySet() as List)+stateMap.values()).join(',')}".split(",");
    jQuery("#state").autocomplete(stateList);
</script>
