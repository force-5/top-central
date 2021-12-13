<g:render template="/securityRole/crud" model="[title: 'Vendor', role: role, createPermission: 'CREATE_VENDOR',
readPermission: 'READ_VENDOR', updatePermission: 'UPDATE_VENDOR',deletePermission: 'DELETE_VENDOR']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Contractor Supervisor', role: role, createPermission: 'CREATE_CONTRACTOR_SUP',
readPermission: 'READ_CONTRACTOR_SUP', updatePermission: 'UPDATE_CONTRACTOR_SUP',deletePermission: 'DELETE_CONTRACTOR_SUP']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Employee Supervisor', role: role, createPermission: 'CREATE_EMPLOYEE_SUP',
readPermission: 'READ_EMPLOYEE_SUP', updatePermission: 'UPDATE_EMPLOYEE_SUP',deletePermission: 'DELETE_EMPLOYEE_SUP']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Business Unit', role: role, createPermission: 'CREATE_BUSINESS_UNIT',
readPermission: 'READ_BUSINESS_UNIT', updatePermission: 'UPDATE_BUSINESS_UNIT',deletePermission: 'DELETE_BUSINESS_UNIT']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Business Unit Requester', role: role, createPermission: 'CREATE_BUR',
readPermission: 'READ_BUR', updatePermission: 'UPDATE_BUR',deletePermission: 'DELETE_BUR']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/contractorProfile" model="[role: role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/contractorAccess" model="[role:role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/contractorCertification" model="[role:role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/employeeProfile" model="[role: role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/employeeAccess" model="[role:role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/employeeCertification" model="[role:role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Admin Certifications', role: role, createPermission: 'CREATE_CERTIFICATION',
readPermission: 'READ_CERTIFICATION', updatePermission: 'UPDATE_CERTIFICATION',deletePermission: 'DELETE_CERTIFICATION']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Courses', role: role, createPermission: 'CREATE_COURSE',
readPermission: 'READ_COURSE', updatePermission: 'UPDATE_COURSE',deletePermission: 'DELETE_COURSE']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/adminLocations" model="[role:role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Origins', role: role, createPermission: 'CREATE_ORIGIN',
readPermission: 'READ_ORIGIN', updatePermission: 'UPDATE_ORIGIN',deletePermission: 'DELETE_ORIGIN']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Entitlement Policies', role: role, createPermission: 'CREATE_ENTITLEMENT_POLICY',
readPermission: 'READ_ENTITLEMENT_POLICY', updatePermission: 'UPDATE_ENTITLEMENT_POLICY',deletePermission: 'DELETE_ENTITLEMENT_POLICY']"/>
<g:render template="/securityRole/separatorImage"/>

%{--<g:render template="/securityRole/crud" model="[title: 'Entitlement Policies', role: role, createPermission: 'CREATE_ENTITLEMENT_POLICY',--}%
%{--readPermission: 'READ_ENTITLEMENT_POLICY', updatePermission: 'UPDATE_ENTITLEMENT_POLICY',deletePermission: 'DELETE_ENTITLEMENT_POLICY']"/>--}%
%{--<g:render template="/securityRole/separatorImage"/>--}%

<g:render template="/securityRole/crud" model="[title: 'Security Roles', role: role, createPermission: 'CREATE_SECURITY_ROLE',
readPermission: 'READ_SECURITY_ROLE', updatePermission: 'UPDATE_SECURITY_ROLE',deletePermission: 'DELETE_SECURITY_ROLE']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/crud" model="[title: 'Message Templates', role: role, createPermission: 'CREATE_MESSAGE_TEMPLATE',
readPermission: 'READ_MESSAGE_TEMPLATE', updatePermission: 'UPDATE_MESSAGE_TEMPLATE',deletePermission: 'DELETE_MESSAGE_TEMPLATE']"/>
<g:render template="/securityRole/separatorImage"/>


<g:render template="/securityRole/crud" model="[title: 'Alerts', role: role, createPermission: 'CREATE_ALERT',
readPermission: 'READ_ALERT', updatePermission: 'UPDATE_ALERT',deletePermission: 'DELETE_ALERT']"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/editRssFeed" model="[role: role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/addNewsAndNotes" model="[role: role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/readManageWorkflow" model="[role: role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/menuBar" model="[role: role]"/>
<g:render template="/securityRole/separatorImage"/>

<g:render template="/securityRole/accessDashBoardType" model="[role: role]"/>

<script type="text/javascript">
    function checkAllCheckbox() {
        jQuery(':checkbox').attr('checked', true);
        jQuery('.child-check-box').attr('checked', false);
    }
    function unCheckAllCheckbox() {
        jQuery(':checkbox').attr('checked', false);
    }

    jQuery(document).ready(function() {
        jQuery.each(jQuery('.parent-check-box'), function() {
            jQuery(this).click(function() {
                disableChildrenIfUnchecked(this);
            });
            disableChildrenIfUnchecked(this);
        });
    });


    function disableChildrenIfUnchecked(parent) {
        if (jQuery(parent).is(':checked')) {
            jQuery.each(jQuery(parent).parents('li').find('.child-check-box'), function() {
                jQuery(this).removeAttr('disabled');
            })
        } else {
            jQuery.each(jQuery(parent).parents('li').find('.child-check-box'), function() {
                jQuery(this).removeAttr('checked');
                jQuery(this).attr('disabled', 'true');
            })
        }
    }

    function getPermissionsForSecurityRole(roleId, ajaxUrl) {
        unCheckAllCheckbox();
        jQuery.get(ajaxUrl,
        { ajax: 'true',roleId:roleId}, function(data) {
            jQuery('#permissions-div').html(data);
            jQuery.each(jQuery('.parent-check-box'), function() {
                disableChildrenIfUnchecked(this);
            });
        });
        jQuery('#securityRole option[value="noSelection"]').attr('selected', 'true');
    }
</script>

