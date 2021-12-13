<div class="form-section">
    <g:render template="/shared/errors" model="[instance: supervisor]"/>
    <div class="namerowbig"><span>Vendor</span>
        <label class="${hasErrors(bean: supervisor, field: 'vendorId', 'errors')}">
            <input type="text" style="display:none;" id="vendorId" name="vendorId"
                    value="${fieldValue(bean: supervisor, field: 'vendorId')}"/>
            <g:hiddenField name="vendorName" value="${supervisor.vendorName}"/>
            <span style="font-weight:bold;font-size:14px;width:140px;height: auto;">${supervisor.vendorName}</span>
        </label>
    </div>
    <div class="namerowbig"><span>First Name</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: supervisor, field: 'firstName', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="firstName" name="firstName"
                    value="${fieldValue(bean: supervisor, field: 'firstName')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Middle Name</span>
        <label class="${hasErrors(bean: supervisor, field: 'middleName', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="middleName" name="middleName"
                    value="${fieldValue(bean: supervisor, field: 'middleName')}"/>
        </label>
    </div>
    <div class="namerowbig"><span>Last Name</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: supervisor, field: 'lastName', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="lastName" name="lastName"
                    value="${fieldValue(bean: supervisor, field: 'lastName')}"/>
        </label>
    </div>

    <div class="namerowbig"><span>Email</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: supervisor, field: 'email', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="email" name="email"
                    value="${fieldValue(bean: supervisor, field: 'email')}"/>
        </label>
    </div>

    <div class="namerowbig"><span>Phone</span><span class="asterisk">*</span>
        <label class="${hasErrors(bean: supervisor, field: 'phone', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="phone" name="phone"
                    value="${fieldValue(bean: supervisor, field: 'phone')}"/>
        </label>
    </div>

    <div class="namerowbig"><span>Notes</span>
        <label class="${hasErrors(bean: supervisor, field: 'notes', 'errors')}">
            <input type="text" class="inp" style="width:135px;" id="notes" name="notes"
                    value="${fieldValue(bean: supervisor, field: 'notes')}"/>
        </label>
    </div>

</div>
<div id="submit-button">
    <input type="submit" class="button" value="Create"/> &nbsp; &nbsp; <input type="button" class="button" value="Cancel" onclick="jQuery.modal.close();"/>
</div>
<div> <br/>
    &nbsp;<span style="color:red;">*</span> indicates a required field </div>
