<div>
    <input type="hidden" name="id" value="${roleInstance?.id}"/>
    <input type="hidden" name="role" value="${session?.role}"/>
    <table>
        <tbody>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="firstName">First Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'firstName', 'errors')}">
                <input type="text" id="firstName" name="firstName" value="${fieldValue(bean: roleInstance, field: 'firstName')}"/>
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="middleName">Middle Name</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'middleName', 'errors')}">
                <input type="text" id="middleName" name="middleName" value="${fieldValue(bean: roleInstance, field: 'middleName')}"/>
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="lastName">Last Name&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'lastName', 'errors')}">
                <input type="text" id="lastName" name="lastName" value="${fieldValue(bean: roleInstance, field: 'lastName')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="phone">Phone&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'phone', 'errors')}">
                <input type="text" id="phone" name="phone" value="${fieldValue(bean: roleInstance, field: 'phone')}"/>
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">
                <label for="email">Email&nbsp;<span class="asterisk">*</span></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'email', 'errors')}">
                <input type="text" id="email" name="email" value="${fieldValue(bean: roleInstance, field: 'email')}"/>
            </td>
        </tr>


        <tr class="prop">
            <td valign="top" class="name">
                <label for="notes">Notes</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'notes', 'errors')}">
                <textarea name="notes" cols="" class="area" style="width:237px; height:50px; " rows="3">${fieldValue(bean: roleInstance, field: 'notes')}</textarea>
            </td>
        </tr>
        </tbody>
    </table>
</div>
