<%@ page import="com.force5solutions.care.*" %>

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
                        <input id="employeeAnchor" checked="true" name="vendorAnchor" type="radio"/>Employee Detail<br/>
                        <!--<input id="supervisorAnchor" name="vendorAnchor" type="radio"/>Employee by Supervisor<br/>-->
                        <input id="burAnchor" name="vendorAnchor" type="radio"/>Employee by Business Unit Requester<br/>
                        <input id="locationAnchor" name="vendorAnchor" type="radio"/>Location<br/>
                        <input id="locationAccessAnchor" name="vendorAnchor" type="radio"/>Location Access<br/>
                        <!--<input id="complianceAnchor" name="vendorAnchor" type="radio" readonly/>Compliance Summary Report<br/>-->
                    </td>
                    <td style="border-left-color:#333333;">
                        <div id="burReportFilter" class="reportFilter" align="left" style="display:none">
                            <g:form action="employeeByBur">
                                <div>
                                    <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
                                    <input type="hidden" name="_format" value="PDF"/>

                                </div>
                                <div>
                                    <input type="radio" checked="true" id="bur_all" name="burRadio" value="allBurs">All Business Unit Requesters<br/>
                                    <input type="radio" id="bur_one" name="burRadio" value="selectedBur">
                                    <g:select from="${businessUnitRequesters}" style="width:142px;"
                                            optionKey="id" id="burSelect" name="burSelect" noSelection="['':'(select one)']"/>
                                </div>
                                <div>
                                    <input type="submit" id="burSubmit" class="button" value="OK"/>
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
                                    <input type="radio" checked="true" id="supervisor_all" name="supervisorRadio" value="allSupervisors">All Supervisors<br/>
                                    <input type="radio" id="supervisor_one" name="supervisorRadio" value="selectedSupervisor">
                                    <g:select from="${supervisors}" style="width:142px;"
                                            optionKey="id" optionValue="name" id="supervisorSelect" name="supervisorSelect" noSelection="['':'(select one)']"/>
                                </div>
                                <div>
                                    <input type="submit" id="supervisorSubmit" class="button" value="OK"/>
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
                                    By Name : <g:select from="${employees}" style="width:142px;"
                                            optionKey="id" id="employeeSelectByName" name="employeeSelect" noSelection="['':'(select one)']"/>
                                    <br/><input type="radio" id="employee_badge" name="employeeRadio" value="selectedEmployee">
                                    By Badge : <g:select from="${employees.findAll{it.badgeNumber}}" style="width:142px;"
                                            optionKey="id" optionValue="${{it.badgeNumber}}" id="employeeSelectByBadge" name="employeeSelect" noSelection="['':'(select one)']"/>
                                    <br/><input type="radio" id="employee_slid" name="employeeRadio" value="selectedEmployee">
                                    By SLID : <g:select from="${employees.findAll{it.slid}}" style="width:142px;"
                                            optionKey="id" optionValue="${{it.slid}}" id="employeeSelectBySlid" name="employeeSelect" noSelection="['':'(select one)']"/>
                                    <br/><input type="radio" id="employee_number" name="employeeRadio" value="selectedEmployee">
                                    By Employee No. : <g:select from="${employees.findAll{it.workerNumber}}" style="width:142px;"
                                            optionKey="id" optionValue="${{it.workerNumber}}" id="employeeSelectByWorkerNumber" name="employeeSelect" noSelection="['':'(select one)']"/>
                                </div>
                                <div>
                                    <input type="submit" id="employeeSubmit" class="button" value="OK"/>
                                </div>
                            </g:form>
                        </div>
                        <div id="locationReportFilter" class="reportFilter" align="left" style="display:none">
                            <g:form action="reportLocation">
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
                            <g:form action="locationAccessReport">
                                <div>
                                    <input type="hidden" name="SUBREPORT_DIR" value="${application.getRealPath('/reports') + File.separator}"/>
                                    <input type="hidden" name="_format" value="PDF"/>

                                </div>
                                <!--style="border:1px solid black;-->
                                <div>
                                    <input type="radio" checked=true name="accessLocations" id="allLocations" value="allLocations"/>All Locations<br/>
                                    <input type="radio" name="accessLocations" id="specificLocation" value="specificLocation"/>Select a location
                                <g:select from="${locations}" style="width:142px;"
                                        optionKey="id" id="locationAccessSelectByName"
                                        name="locationAccessSelect"
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
                                    <input type="radio" checked="true" id="compliance_all" name="complianceRadio" value="allEmployees">All Employees Compliance Summary<br/>
                                    <input type="radio" id="compliance_one" name="complianceRadio" value="selectedEmployee">
                                    <g:select from="${employees}" style="width:142px;"
                                            optionKey="id" id="complianceSelect" name="employeeSelect" noSelection="['':'(select one)']"/>
                                </div>
                                <div>
                                    <input type="submit" id="complianceSubmit" class="button" value="OK"/>
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

        jQuery('#allLocations').click(function() {
            jQuery('#locationAccessSelectByName').attr('disabled', true);
        });
        jQuery('#specificLocation').click(function() {
            jQuery('#locationAccessSelectByName').removeAttr('disabled');
        });

        jQuery('#locationAccessSelectByName').attr('disabled', true);

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
            var selectedId = jQuery('#locationAccessSelectByName').val();
            if (jQuery('#specificLocation').attr('checked') && selectedId.length < 1) {
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
                alert('Please select an Employee first.');
                return false;
            }
        });

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
        jQuery('#employee_number').click(function() {
            jQuery('#employeeReportFilter select').attr('disabled', true);
            jQuery('#employeeSelectByWorkerNumber').removeAttr('disabled');
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

        jQuery('#employeeReportFilter').show();

///////////////////////////////////////////////////////////////////////////

    });
</script>
</body>
</html>
