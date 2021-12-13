import com.force5solutions.care.common.CustomTag

pre {
    CustomTag customTag;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Access Request") ?: new CustomTag();
    customTag.with {
        name = "Access Request"
        displayValue = '###AccessRequest###'
        value = '''
<h3>Access Request</h3>
<table border="1" cellpadding="5">
    <tr><th>Role</th><th>Description</th></tr>
    <tr><td>${workerEntitlementRole}</td><td>${workerEntitlementRole?.entitlementRole?.notes}</td></tr>
</table>

<br />'''
        dummyData = '<br />Preview Access Request Table<br />'
    }
    customTag.save()

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Personnel Name") ?: new CustomTag();
    customTag.with {
        name = "Personnel Name"
        displayValue = '###PersonnelName###'
        value = '${worker.name}'
        dummyData = 'Preview Person Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Personnel Name by Group") ?: new CustomTag();
    customTag.with {
        name = "Personnel Name by Group"
        displayValue = '###PersonnelNameByGroup###'
        value = '${workerEntitlementRole.worker.name}'
        dummyData = 'Preview Person Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Contractor Name by Group in format First Name Middle Name Last Name") ?: new CustomTag();
    customTag.with {
        name = "Contractor Name by Group in format First Name Middle Name Last Name"
        displayValue = '###PersonnelFirstMiddleLastNameByGroup###'
        value = '''<%
            out << workerEntitlementRole.worker.firstMiddleLastName
        %>'''
        dummyData = 'Preview Person Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Supervisor FirstName MiddleName LastName") ?: new CustomTag();
    customTag.with {
        name = "Supervisor FirstName MiddleName LastName"
        displayValue = '###SupervisorFirstMiddleLastName###'
        value = '''<%
            out << worker?.firstMiddleLastName
        %>'''
        dummyData = 'Preview Person Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Worker's Supervisor Name") ?: new CustomTag();
    customTag.with {
        name = "Worker's Supervisor Name"
        displayValue = '###WorkerSupervisorFirstMiddleLastName###'
        value = '''<%
            out << worker?.sponsorName
        %>'''
        dummyData = 'Preview Worker Supervisor Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Personnel Name by Certification") ?: new CustomTag();
    customTag.with {
        name = "Personnel Name by Certification"
        displayValue = '###PersonnelNameByCertification###'
        value = '${workerCertification.worker.name}'
        dummyData = 'Preview Person Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Pending Certification Names") ?: new CustomTag();
    customTag.with {
        name = "Pending Certification Names"
        displayValue = '###PendingCertificationNames###'
        value = '	${workerEntitlementRole.missingCertifications? workerEntitlementRole.missingCertifications*.name: ""}'
        dummyData = 'Preview Certfification Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Required Certification Names") ?: new CustomTag();
    customTag.with {
        name = "Required Certification Names"
        displayValue = '###RequiredCertificationNames###'
        value = '${(workerEntitlementRole.entitlementRole.requiredCertifications + workerEntitlementRole.entitlementRole.inheritedCertifications)?.unique() ?:""}'
        dummyData = 'Preview Certfification Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Required Certfication Status") ?: new CustomTag();
    customTag.with {
        name = "Required Certfication Status"
        displayValue = '###RequiredCertificationsStatus###'
        value = """<%
             Set requiredCertfications = (workerEntitlementRole?.entitlementRole?.requiredCertifications + workerEntitlementRole?.entitlementRole?.inheritedCertifications)?.unique()
        out << '''<table bgcolor="#cccccc" cellspacing="1" cellpadding="5" style="width:100%;font-size:12px;border:none">
                    <thead>
                        <tr>
                            <th bgcolor="#ffffff" align="left">Certification</th>
                            <th bgcolor="#ffffff" align="left">Employee Status</th>
                        </tr>
                    </thead>
                    <tbody>'''
        requiredCertfications?.each { certification ->
            out << '''<tr><td bgcolor="#ffffff">'''
            out << certification
            out << '''</td>'''
            out << '''<td bgcolor="#ffffff">'''
            if (certification in workerEntitlementRole.worker.effectiveCertifications*.certification) {
                def workerCertification = workerEntitlementRole.worker.effectiveCertifications.find { it.certification == certification}
                out << workerCertification.currentStatus
            } else {
                out << "PENDING"
            }
            out << '''</td></tr>'''
        }
        out << '''</tbody></table>'''
    %>
"""
        dummyData = 'Preview Data for Rquired Cerification Status'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Worker Entitlement Role Name") ?: new CustomTag();
    customTag.with {
        name = "Worker Entitlement Role Name"
        displayValue = '###PersonnelEntitlementRoleName###'
        value = '${workerEntitlementRole}'
        dummyData = 'Preview Entitlement Role Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Worker Entitlement Role Description") ?: new CustomTag();
    customTag.with {
        name = "Worker Entitlement Role Description"
        displayValue = '###PersonnelEntitlementRoleDescription###'
        value = '${workerEntitlementRole?.entitlementRole?.notes}'
        dummyData = 'Preview Entitlement Role Description'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Certification Name") ?: new CustomTag();
    customTag.with {
        name = "Certification Name"
        displayValue = '###PersonnelCertificationName###'
        value = '${workerCertification}'
        dummyData = 'Preview Certification Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Days Remaining in Certification Expiration") ?: new CustomTag();
    customTag.with {
        name = "Days Remaining in Certification Expiration"
        displayValue = '###DaysRemainingInCertificationExpiration###'
        value = '${(workerCertification.fudgedExpiry - new Date())}'
        dummyData = "1"
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("More Information Link") ?: new CustomTag();
    customTag.with {
        name = "More Information Link"
        displayValue = '###MoreInformationLink###'
        value = '${link}'
        dummyData = 'http://previewdomain/previewurl'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Confirmation Link") ?: new CustomTag();
    customTag.with {
        name = "Confirmation Link"
        displayValue = '###ConfirmationLink###'
        value = '${confirmationLink}'
        dummyData = 'http://previewdomain/confirmationUrl'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Employee List Link") ?: new CustomTag();
    customTag.with {
        name = "Employee List Link"
        displayValue = '###EmployeeListLink###'
        value = '${employeeListLink}'
        dummyData = 'http://previewdomain/confirmationUrl'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Alert Name") ?: new CustomTag();
    customTag.with {
        name = "Alert Name"
        displayValue = '###AlertName###'
        value = '${alert.name}'
        dummyData = 'xxx'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Entitlement Policy Creation") ?: new CustomTag();
    customTag.with {
        name = "Entitlement Policy Creation"
        displayValue = '###EntitlementPolicyName###'
        value = '${entitlementPolicy.name}'
        dummyData = 'Preview Entitlement Name'
    }
    customTag.save();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    customTag = CustomTag.findByName("Active Workers By Entitlement Role") ?: new CustomTag();
    customTag.with {
        name = "Active Workers By Entitlement Role"
        displayValue = '###activeWorkersGroupByEntitlementRole###'
        value = """<%
        out << '''
<h1 style="color: #F15A29;font-weight: normal;font-size: 16px;margin: 8px 0px 3px 0px;padding: 0px;">Access Verification</h1>'''

        int index
        workerAsSupervisor.activeWorkersGroupByEntitlementRole.each { roleMap ->
            out << '''<div class="list" style="border: 1px solid #ddd;margin-bottom: 20px;padding: 5px 10px;"><p class="heading-access" style="font-size: 16px;color: #7e7e7e">Entitlement Role : <span style="color:black;">'''
            out << roleMap.key.name
            out << '''</span></p>
                    <table cellpadding="0" cellspacing="0" border="0" class="table-access-verification" style="width: 100%; font-size: 14px;border: none;">
                    <thead>
                        <tr style="border: 0;">
                            <th style="color: #666;line-height: 17px;font: 11px verdana, arial, helvetica, sans-serif;line-height: 12px;padding: 3px 6px;text-align: left;vertical-align: top;font-size: 14px;padding: 6px 6px;background: #fff;border: none;border-bottom: 1px solid #ddd;" width="300">Name</th>
                            <th style="color: #666;line-height: 17px;font: 11px verdana, arial, helvetica, sans-serif;line-height: 12px;padding: 3px 6px;text-align: left;vertical-align: top;font-size: 14px;padding: 6px 6px;background: #fff;border: none;border-bottom: 1px solid #ddd;">SLID</th>
                            <th style="color: #666;line-height: 17px;font: 11px verdana, arial, helvetica, sans-serif;line-height: 12px;padding: 3px 6px;text-align: left;vertical-align: top;font-size: 14px;padding: 6px 6px;background: #fff;border: none;border-bottom: 1px solid #ddd;">Badge</th>
                        </tr>
                    </thead>
                    <tbody>'''
            index = 0
            roleMap.value.each { emp ->
                if ((index % 2) == 0) {
                    out << '''<tr style="background: #fff;border: 0;"><td style="font: 11px verdana, arial, helvetica, sans-serif;line-height: 12px;text-align: left;vertical-align: top; font-size: 14px;padding: 10px 6px;border: none">'''
                } else {
                    out << '''<tr style="background: #CFD0D2;border: 0;"><td style="font: 11px verdana, arial, helvetica, sans-serif;line-height: 12px;text-align: left;vertical-align: top; font-size: 14px;padding: 10px 6px;border: none">'''
                }
                out << emp?.name
                out << '''</td><td>'''
                out << emp?.person?.slid?: ""
                out << '''</td><td>'''
                out << ((emp.badgeNumber)? emp.badgeNumber : "")
                out << '''</td></tr>'''
                index++
            }
            out << '''</tbody></table></div>'''
        }
    %>

     """
        dummyData = 'Preview Data for Active Workers By Entitlement Role'
    }
    customTag.save();
}

fixture {
}

