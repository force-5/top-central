<div class="dialog">
    <table>
        <tbody>

            <tr class="prop">
                <td valign="top" class="name">
                    <label>Vendor&nbsp;<span class="asterisk">*</span></label>
                </td>
                <td valign="top" class=" value ${hasErrors(bean: supervisor, field: 'vendorId', 'errors ')}">
                  <div style=" float:left;width:140px;">
                    <g:select style="width:140px;" from="${vendors?.sort{it.toString().toLowerCase()}}"
                            class="${hasErrors(bean: supervisor, field: 'vendorId', 'errors ')} auto-resize "
                            optionKey="id" optionValue="companyName"
                            value="${supervisor?.vendorId}" name="vendorId"/>
                    </div>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="firstName">First Name&nbsp;<span class="asterisk">*</span></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: supervisor, field: 'firstName', 'errors')}">
                    <input type="text" id="firstName" name="firstName"
                            value="${fieldValue(bean: supervisor, field: 'firstName')}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="middleName">Middle Name</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: supervisor, field: 'middleName', 'errors')}">
                    <input type="text" id="middleName" name="middleName"
                            value="${fieldValue(bean: supervisor, field: 'middleName')}"/>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="lastName">Last Name&nbsp;<span class="asterisk">*</span></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: supervisor, field: 'lastName', 'errors')}">
                    <input type="text" id="lastName" name="lastName"
                            value="${fieldValue(bean: supervisor, field: 'lastName')}"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="phone">Phone&nbsp;<span class="asterisk">*</span></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: supervisor, field: 'phone', 'errors')}">
                    <input type="text" id="phone" name="phone"
                            value="${fieldValue(bean: supervisor, field: 'phone')}"/>
                </td>
            </tr>

            <tr class="prop">
                <td valign="top" class="name">
                    <label for="email">Email&nbsp;<span class="asterisk">*</span></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: supervisor, field: 'email', 'errors')}">
                    <input type="text" id="email" name="email"
                            value="${fieldValue(bean: supervisor, field: 'email')}"/>
                </td>
            </tr>


            <tr class="prop">
                <td valign="top" class="name">
                    <label for="notes">Notes</label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: supervisor, field: 'notes', 'errors')}">
                    <textarea id="notes" name="notes" cols="" class="area" style="width:237px; height:50px; " rows="3">${fieldValue(bean: supervisor, field: 'notes')}</textarea>
                </td>
            </tr>

        </tbody>
    </table>
</div>