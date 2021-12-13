<%@ page import="com.force5solutions.care.cc.Worker; com.force5solutions.care.ldap.Permission;" %><html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Employee</title>
</head>
<body>
<care:recentStatus workerId="${worker?.id}"/>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
    <span class="menuButton">
        <g:link action="access" controller="workerEntitlementRole" params="[id: worker.id]"
                class="list">Access</g:link>
    </span>
    <span class="menuButton">
        <g:if test="${care.hasPermission(permission: Permission.READ_EMPLOYEE_CERTIFICATION, worker: Worker.findById(worker?.id))}">
            <span class="menuButton"><g:link action="certification" controller="workerCertification" params="[id: worker.id]"
                    class="list">Certification</g:link></span>
        </g:if>
        <g:else>
            <span class="menuButton"><a class="list-disabled  employeeCertificationLink">Certification</a></span>
        </g:else>
    </span>
    <span class="menuButton">
        <g:link controller="worker" action="workflowReportBySlidOrId" params="[id: worker.slid]" class="list">Workflows</g:link>
    </span>
</div>
<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
</div>
<br/>
<div id="right-panel">
    <div id="right-top">
        <div id="photo-section">

            <div id="photo">
                <g:hiddenField name="workerImageId" value="${worker?.workerImageId}"/>
                <care:workerImage id="${worker?.workerImageId}"/>
            </div>
            <div id="upload-button">
                <input type="button" class="button" value="Upload"
                        onclick="showModalDialog('create_uploadImage', true);"/>
            </div>
            <div id="create_uploadImage" class="popupWindow">
                <g:uploadForm name="photoUpload" controller="employee" action="updateImage" method="post">
                    <input type="hidden" name="workerId" id="workerId" value="${worker?.id}"/>
                    <div class="popupWindowTitle">Select an Image to upload</div>
                    <br/>
                    <div align="center">
                        <input type="file" id="fileContent" name="fileContent" size="30"/>
                    </div>
                    <br/>
                    <div align="center">
                        <input type="button" class="button simplemodal-close" value="Submit" onclick='jQuery("form[name=photoUpload]").submit();'/>
                        &nbsp; &nbsp; <input type="button" class="button simplemodal-close" value="Cancel"/>
                    </div>
                </g:uploadForm>
            </div>
        </div>

        <div class="form-section-new">
            <div class="namerow"><span>First Name</span>
                <input type="text" class="inp" style="width:130px;"
                        name="firstName" value="${worker.firstName}"/>
            </div>
            <div class="namerow"><span>Middle Name</span>
                <input type="text" class="inp " style="width:120px;"
                        name="middleName" value="${worker.middleName}"/>
            </div>

            <div class="namerow"><span>Last Name</span>
                <input type="text" class="inp" style="width:125px;"
                        name="lastName" value="${worker.lastName}"/>
            </div>
            <div class="namerow"><span>Title</span>
                <input type="text" class="inp " style="width:110px;"
                        name="title" value="${worker.title}"/>
            </div>
            <div class="namerow"><span>Department</span>
                <input type="text" class="inp " style="width:110px;"
                        name="department" value="${worker.department}"/>
            </div>
            <div id="supervisorList" class="namerow">
                <span>Supervisor</span>
                <select class="auto-resize listbox ${hasErrors(bean: worker, field: 'supervisor', 'errors')}"
                        style="width:125px;" name="supervisor" id="supervisor">
                    <option value="-2">(Select One)</option>
                    <g:each in="${supervisors}" var="supervisor">
                        <g:if test="${supervisor.id==worker?.supervisor?.toLong()}">
                            <option selected value="${supervisor.id}">${supervisor}</option>
                        </g:if>
                        <g:else>
                            <option value="${supervisor.id}">${supervisor}</option>
                        </g:else>
                    </g:each>
                </select>
            </div>
            <div style="float:left;">Business Unit Requester</div>
            <ui:multiSelect
                    name="businessUnitRequesters"
                    from="[]"
                    value="${selectedBusinessUnitRequesters}"
                    noSelection="['':'(Select One)']"
                    class="listbox"
                    style="width:90px;"/>

        </div>
        <div class="form-section-new">
            <div class="namerowbig"><span>Phone</span>
                <input type="text" class="inp " style="width:185px;"
                        name="phone" value="${worker?.phone}"/>
            </div>
            <div class="namerowbig"><span>Email</span>
                <input type="text" class="inp " style="width:190px;"
                        name="email" value="${worker?.email}"/>
            </div>


            <div class="namerowbig"><span>Employee #</span>
                <input type="text" class="inp " style="width:145px;"
                        id="workerNumber" name="workerNumber" value="${worker.workerNumber}"/>
            </div>


            <div class="namerowbig"><span>SLID</span>
                <input type="text" class="inp " style="width:125px;"
                        name="slid" value="${worker.slid}"/>
            </div>
            <div class="namerowbig"><span>Badge Number</span>

                <input type="text" class="inp " style="width:125px;"
                        name="badgeNumber" value="${worker.badgeNumber}"/>
            </div>
            <div class="namearea" align="left"><span>Notes</span>
                <textarea name="notes" cols="" class="area" style="width:237px; height:67px; " rows="3">${worker.notes}</textarea>
            </div>
        </div>

    </div>
    <versionable:showHistory object="${employee}"/>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('input').not('.button,#fileContent,#workerId').attr('disabled', true);
        jQuery('select').attr('disabled', true);
        jQuery('textarea').attr('disabled', true);
        disableMultiSelect('businessUnitRequesters');
    });
</script>
</body>
</html>