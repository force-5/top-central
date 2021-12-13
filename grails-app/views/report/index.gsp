<%@ page import="com.force5solutions.care.cc.Worker" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Report</title>
</head>

<body>
<br/>

<div id="wrapper">
<div id="right-panel">
<div class="nav">
    <span class="menuButton"><g:link class="home" url="${resource(dir:'')}">Home</g:link></span>
</div>

<div>
<h1>Reports</h1>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<table>
<thead>
<th width="40%">
    Choose Report
</th>
<th align="left">
    Define Filters
</th>
</thead>
<tbody>
<tr>
<td>
    <input id="contractorAnchor" checked="true" name="vendorAnchor" type="radio"/>Contractor Detail<br/>
    <input id="vendorAnchor" name="vendorAnchor" type="radio"/>Contractor by Vendor<br/>
    <input id="burAnchor" name="vendorAnchor" type="radio"/>Contractor by Business Unit Requester<br/>
    <input id="locationAnchor" name="vendorAnchor" type="radio"/>Entitlement Role<br/>
    <input id="locationAccessAnchor" name="vendorAnchor" type="radio"/>Entitlement Role Access<br/>
    <input id="complianceAnchor" name="vendorAnchor" type="radio"/>Compliance Summary Report<br/>
    <input id="employeeToEntitlementRolesAnchor" name="vendorAnchor" type="radio"/>Employee To Entitlement Roles<br/>
    <input id="entitlementRoleToEmployeesAnchor" name="vendorAnchor" type="radio"/>Entitlement Role To Employees<br/>
    <input id="currentFPLEmployeeCCAUserPopulationAnchor" name="vendorAnchor"
           type="radio"/>Current FPL Employee CCA User Population<br/>
    <input id="contractorToEntitlementRolesAnchor" name="vendorAnchor"
           type="radio"/>Contractor To Entitlement Roles<br/>
    <input id="entitlementRoleToContractorsAnchor" name="vendorAnchor"
           type="radio"/>Entitlement Role To Contractors<br/>
    <input id="employeeAnchor" name="vendorAnchor" type="radio"/>Employee Detail<br/>
    <input id="supervisorAnchor" name="vendorAnchor" type="radio"/>Employee by Supervisor<br/>
    <input id="cipAccessAnchor" name="vendorAnchor" type="radio"/>CIP Access<br/>
</td>
<td style="border-left-color:#333333;">
<div id="vendorReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="reportByVendor">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>

        <div>
            <input type="radio" checked="true" id="vendor_all" name="vendorRadio" value="allVendors">All Vendors<br/>
            <input type="radio" id="vendor_one" name="vendorRadio" value="selectedVendor">
            <g:select from="${vendors.sort{it.companyName}}" style="width:142px;"
                      optionKey="id" optionValue="companyName" id="vendorSelect" name="vendorSelect"
                      noSelection="['':'(select one)']"/>
        </div>

        <div>
            <input type="submit" id="vendorSubmit" class="button" value="OK"/>
        </div>
    </g:form>

</div>

<div id="employeeToEntitlementRolesFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="employeeToEntitlementRoles">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>
        <input type="submit" id="employeeToEntitlementRolesSubmit" class="button" value="OK"/>
    </g:form>
</div>

<div id="entitlementRoleToEmployeesFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="entitlementRoleToEmployees">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>
        <input type="submit" id="employeeToEntitlementRolesSubmit" class="button" value="OK"/>
    </g:form>
</div>

<div id="entitlementRoleToContractorsFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="entitlementRoleToContractors">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>
        <input type="submit" id="contractorToEntitlementRolesSubmit" class="button" value="OK"/>
    </g:form>
</div>

<div id="contractorToEntitlementRolesFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="contractorToEntitlementRoles">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>
        <input type="submit" id="contractorToEntitlementRolesSubmit" class="button" value="OK"/>
    </g:form>
</div>

<div id="currentFPLEmployeeCCAFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="currentFPLEmployeeCCAUserPopulation">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>
        <input type="submit" id="employeeToEntitlementRolesSubmit" class="button" value="OK"/>
    </g:form>
</div>

<div id="burReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="reportByBur">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>

        </div>

        <div>
            <input type="radio" checked="true" id="bur_all" name="burRadio"
                   value="allBurs">All Business Unit Requesters<br/>
            <input type="radio" id="bur_one" name="burRadio" value="selectedBur">
            <g:select from="${businessUnitRequesters.sort{it.name}}" style="width:142px;"
                      optionKey="id" id="burSelect" name="burSelect" noSelection="['':'(select one)']"/>
        </div>

        <div>
            <input type="submit" id="burSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

<div id="contractorReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="reportByContractor">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>

        </div>

        <div>
            <input type="radio" checked="true" id="contractor_all" name="contractorRadio"
                   value="allContractors">All Contractors
            <br/><input type="radio" id="contractor_name" name="contractorRadio" value="selectedContractor">
            By Name : <g:select from="${contractors.sort{it?.name?.toString()?.toLowerCase()}}" style="width:142px;"
                                optionKey="id" id="contractorSelectByName" name="contractorSelect"
                                noSelection="['':'(select one)']"/>
            <br/><input type="radio" id="contractor_badge" name="contractorRadio" value="selectedContractor">
            By Badge : <g:select
                    from="${contractors.findAll{it.badgeNumber}?.sort{it.badgeNumber?.toString()?.toLowerCase()}}"
                    style="width:142px;"
                    optionKey="id" optionValue="${{it.badgeNumber}}" id="contractorSelectByBadge"
                    name="contractorSelect" noSelection="['':'(select one)']"/>
            <br/><input type="radio" id="contractor_slid" name="contractorRadio" value="selectedContractor">
            By SLID : <g:select from="${contractors?.findAll{it.slid}?.sort{it.slid?.toString()?.toLowerCase()}}"
                                style="width:142px;"
                                optionKey="id" optionValue="${{it.slid}}" id="contractorSelectBySlid"
                                name="contractorSelect" noSelection="['':'(select one)']"/>
            <br/><input type="radio" id="contractor_number" name="contractorRadio" value="selectedContractor">
            By Contractor No. : <g:select from="${contractors.findAll{it.workerNumber}?.sort{it.workerNumber}}"
                                          style="width:142px;"
                                          optionKey="id" optionValue="${{it.workerNumber}}"
                                          id="contractorSelectByWorkerNumber" name="contractorSelect"
                                          noSelection="['':'(select one)']"/>
        </div>

        <div>
            <input type="submit" id="contractorSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

<div id="cipAccessReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="cipAccessReport">
        <div>
            <input type="submit" id="cipAccessSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

<div id="locationReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="entitlementRoleGroupReport">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>
        </div>

        <div>
            <input type="submit" id="locationSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

<div id="locationAccessReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="entitlementRoleAccessReport">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>

        </div>
        <!--style="border:1px solid black;-->
        <div>
            <input type="radio" checked=true name="accessLocations" id="allLocations"
                   value="allLocations"/>All Entitlement Roles<br/>
            <input type="radio" name="accessLocations" id="specificGroup" value="specificGroup"/>Select Entitlement Role
        <g:select from="${entitlementRoles.sort{it.name}}" style="width:142px;"
                  optionKey="id" id="entitlementRoleAccessSelectByName"
                  name="entitlementRoleAccessSelect"
                  noSelection="['':'(select one)']"/>
        </div>
        <br/>

        <div style="width:300px;">
            <input type="radio" checked=true name="dateRange" id="allDates" value="allDates"/>All Dates
            <br/>
            <input type="radio" name="dateRange" id="dateRange" value="dateRange"/>
            From:<calendar:datePicker name="fromDate" id="fromDate"/><br/>

            <div style="padding-left:28px">
                To:<calendar:datePicker name="toDate" id="toDate"/></div>
        </div>

        <div>
            <input type="submit" id="locationAccessSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>


<div id="complianceReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="complianceSummaryReport">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="XLS"/>

        </div>

        <div>
            <input type="radio" checked="true" id="compliance_all" name="complianceRadio"
                   value="allContractors">All Contractors Compliance Summary<br/>
            <input type="radio" id="compliance_one" name="complianceRadio" value="selectedContractor">
            <g:select from="${contractors.sort{it.name}}" style="width:142px;"
                      optionKey="id" id="complianceSelect" name="contractorSelect" noSelection="['':'(select one)']"/>
        </div>

        <div>
            <input type="submit" id="complianceSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

<div id="employeeReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="reportByEmployee">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>

        </div>

        <div>
            <input type="radio" checked="true" id="employee_all" name="employeeRadio" value="allEmployees">All Employees
            <br/><input type="radio" id="employee_name" name="employeeRadio" value="selectedEmployee">
            By Name : <g:select from="${employees.sort{it?.name?.toString()?.toLowerCase()}}" style="width:142px;"
                                optionKey="id" id="employeeSelectByName" name="employeeSelect"
                                noSelection="['':'(select one)']"/>
            <br/><input type="radio" id="employee_badge" name="employeeRadio" value="selectedContractor">
            By Badge : <g:select
                    from="${employees.findAll{it.badgeNumber}?.sort{it.badgeNumber?.toString()?.toLowerCase()}}"
                    style="width:142px;"
                    optionKey="id" optionValue="${{it.badgeNumber}}" id="employeeSelectByBadge" name="employeeSelect"
                    noSelection="['':'(select one)']"/>
            <br/><input type="radio" id="employee_slid" name="employeeRadio" value="selectedContractor">
            By SLID : <g:select from="${employees?.findAll{it.slid}?.sort{it.slid?.toString()?.toLowerCase()}}"
                                style="width:142px;"
                                optionKey="id" optionValue="${{it.slid}}" id="employeeSelectBySlid"
                                name="employeeSelect" noSelection="['':'(select one)']"/>
        </div>

        <div>
            <input type="submit" id="employeeSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

<div id="supervisorReportFilter" class="reportFilter" align="left" style="display:none">
    <g:form action="employeeBySupervisor">
        <div>
            <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
            <input type="hidden" name="_format" value="PDF"/>

        </div>

        <div>
            <input type="radio" checked="true" id="supervisor_all" name="supervisorRadio"
                   value="allSupervisors">All Supervisors<br/>
            <input type="radio" id="supervisor_one" name="supervisorRadio" value="selectedSupervisor">
            <g:select from="${supervisors.sort{it?.name?.toString()?.toLowerCase()}}" style="width:142px;"
                      optionKey="id" optionValue="name" id="supervisorSelect" name="supervisorSelect"
                      noSelection="['':'(select one)']"/>
        </div>

        <div>
            <input type="submit" id="supervisorSubmit" class="button" value="OK"/>
        </div>
    </g:form>
</div>

</td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        ///////////////////////////////////////////////////////////////////////////
        jQuery('#vendor_all').click(function() {
            jQuery('#vendorSelect').attr('disabled', true);
        });
        jQuery('#vendor_one').click(function() {
            jQuery('#vendorSelect').removeAttr('disabled');
        });

        jQuery('#vendorSelect').attr('disabled', true);

        jQuery('#vendorAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#vendorReportFilter').show();
        });

        jQuery('#vendorSubmit').bind('click', function() {
            var selectedId = jQuery('#vendorSelect').val();
            if (jQuery('#vendor_one').attr('checked') && selectedId.length < 1) {
                alert('Please select a Vendor first.');
                return false;
            }
        });

///////////////////////////////////////////////////////////////////////////

        jQuery('#allLocations').click(function() {
            jQuery('#entitlementRoleAccessSelectByName').attr('disabled', true);
        });
        jQuery('#specificGroup').click(function() {
            jQuery('#entitlementRoleAccessSelectByName').removeAttr('disabled');
        });

        jQuery('#entitlementRoleAccessSelectByName').attr('disabled', true);

        jQuery('#allDates').click(function() {
            jQuery('#fromDate-trigger').hide();
            jQuery('#toDate-trigger').hide();
        });
        jQuery('#dateRange').click(function() {
            jQuery('#fromDate-trigger').show();
            jQuery('#toDate-trigger').show();
        });

        jQuery('#fromDate-trigger').hide();
        jQuery('#toDate-trigger').hide();

        jQuery('#locationAccessAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#locationAccessReportFilter').show();
        });

        jQuery('#locationAccessSubmit').bind('click', function() {
            var selectedId = jQuery('#entitlementRoleAccessSelectByName').val();
            if (jQuery('#specificGroup').attr('checked') && selectedId.length < 1) {
                alert('Please select a Location first.');
                return false;
            }
            var fromDateLen = jQuery.trim(jQuery('#fromDate_value').val()).length;
            var toDateLen = jQuery.trim(jQuery('#toDate_value').val()).length;
            if ((jQuery('#dateRange').attr('checked')) && (fromDateLen < 1 || toDateLen < 1)) {
                alert('Please specify a valid date range.');
                return false;
            }
        });

        jQuery('#cipAccessAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#cipAccessReportFilter').show();
        });

        jQuery('#locationAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#locationReportFilter').show();
        });

///////////////////////////////////////////////////////////////////////////
        jQuery('#bur_all').click(function() {
            jQuery('#burSelect').attr('disabled', true);
        });

        jQuery('#bur_one').click(function() {
            jQuery('#burSelect').removeAttr('disabled');
        });

        jQuery('#burSelect').attr('disabled', true);

        jQuery('#burAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#burReportFilter').show();
        });

        jQuery('#burSubmit').bind('click', function() {
            var selectedId = jQuery('#burSelect').val();
            if (jQuery('#bur_one').attr('checked') && selectedId.length < 1) {
                alert('Please select a Business Unit Requester first.');
                return false;
            }
        });
///////////////////////////////////////////////////////////////////////////
        jQuery('#compliance_all').click(function() {
            jQuery('#complianceSelect').attr('disabled', true);
        });

        jQuery('#compliance_one').click(function() {
            jQuery('#complianceSelect').removeAttr('disabled');
        });

        jQuery('#complianceSelect').attr('disabled', true);

        jQuery('#complianceAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#complianceReportFilter').show();
        });

        jQuery('#complianceSubmit').bind('click', function() {
            var selectedId = jQuery('#complianceSelect').val();
            if (jQuery('#compliance_one').attr('checked') && selectedId.length < 1) {
                alert('Please select a Contractor first.');
                return false;
            }
        });

///////////////////////////////////////////////////////////////////////////

        jQuery('#contractor_all').click(function() {
            jQuery('#contractorReportFilter select').attr('disabled', true);
        });

        jQuery('#contractor_name').click(function() {
            jQuery('#contractorReportFilter select').attr('disabled', true);
            jQuery('#contractorSelectByName').removeAttr('disabled');
        });
        jQuery('#contractor_badge').click(function() {
            jQuery('#contractorReportFilter select').attr('disabled', true);
            jQuery('#contractorSelectByBadge').removeAttr('disabled');
        });
        jQuery('#contractor_slid').click(function() {
            jQuery('#contractorReportFilter select').attr('disabled', true);
            jQuery('#contractorSelectBySlid').removeAttr('disabled');
        });
        jQuery('#contractor_number').click(function() {
            jQuery('#contractorReportFilter select').attr('disabled', true);
            jQuery('#contractorSelectByWorkerNumber').removeAttr('disabled');
        });

        jQuery('#contractorReportFilter select').attr('disabled', true);

        jQuery('#contractorAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#contractorReportFilter').show();
        });

        jQuery('#contractorSubmit').bind('click', function() {
            var selectBoxes = jQuery('#contractorReportFilter input:radio:checked').next('select');
            if (jQuery(selectBoxes).size() > 0 && jQuery(selectBoxes).val().length < 1) {
                alert('Please select a Contractor first.');
                return false;
            }
        });

        jQuery('#contractorReportFilter').show();

///////////////////////////////////////////////////////////////////////////

      jQuery('#employee_all').click(function() {
        jQuery('#employeeReportFilter select').attr('disabled', true);
      });

      jQuery('#employee_name').click(function() {
        jQuery('#employeeReportFilter select').attr('disabled', true);
        jQuery('#employeeSelectByName').removeAttr('disabled');
      });
      jQuery('#employee_badge').click(function() {
        jQuery('#employeeReportFilter select').attr('disabled', true);
        jQuery('#employeeSelectByBadge').removeAttr('disabled');
      });
      jQuery('#employee_slid').click(function() {
        jQuery('#employeeReportFilter select').attr('disabled', true);
        jQuery('#employeeSelectBySlid').removeAttr('disabled');
      });
      jQuery('#employeeReportFilter select').attr('disabled', true);

      jQuery('#employeeAnchor').bind('click', function() {
        jQuery('.reportFilter').hide();
        jQuery('#employeeReportFilter').show();
      });

      jQuery('#employeeSubmit').bind('click', function() {
        var selectBoxes = jQuery('#employeeReportFilter input:radio:checked').next('select');
        if (jQuery(selectBoxes).size() > 0 && jQuery(selectBoxes).val().length < 1) {
          alert('Please select an Employee first.');
          return false;
        }
      });

      jQuery('#contractorReportFilter').show();
///////////////////////////////////////////////////////////////////////////

        jQuery('#employeeToEntitlementRolesAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#employeeToEntitlementRolesFilter').show();
        });

        jQuery('#entitlementRoleToEmployeesAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#entitlementRoleToEmployeesFilter').show();
        });

        jQuery('#currentFPLEmployeeCCAUserPopulationAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#currentFPLEmployeeCCAFilter').show();
        });
///////////////////////////////////////////////////////////////////////////
        jQuery('#supervisor_all').click(function() {
            jQuery('#supervisorSelect').attr('disabled', true);
        });

        jQuery('#supervisor_one').click(function() {
            jQuery('#supervisorSelect').removeAttr('disabled');
        });

        jQuery('#supervisorSelect').attr('disabled', true);

        jQuery('#supervisorAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#supervisorReportFilter').show();
        });

        jQuery('#supervisorSubmit').bind('click', function() {
            var selectedId = jQuery('#supervisorSelect').val();
            if (jQuery('#supervisor_one').attr('checked') && selectedId.length < 1) {
                alert('Please select a Supervisor first.');
                return false;
            }
        });
        //////////////////////////////////////////////////////////////////////////

        jQuery('#contractorToEntitlementRolesAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#contractorToEntitlementRolesFilter').show();
        });

        jQuery('#entitlementRoleToContractorsAnchor').bind('click', function() {
            jQuery('.reportFilter').hide();
            jQuery('#entitlementRoleToContractorsFilter').show();
        });

    });
</script>
</body>
</html>
