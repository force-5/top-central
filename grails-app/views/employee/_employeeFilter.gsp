<%@ page import="com.force5solutions.care.cc.WorkerStatus; com.force5solutions.care.cc.EntitlementRoleAccessStatus; com.force5solutions.care.*" %>
<meta name="layout" content="someLayoutThatDoesNotExist"/>

<div class="popupWindowTitle">Filter Employee List</div>
<br/>
<g:form action="filterList">
    <div class="form-section">
        <div class="namerow"><span>First Name</span>
            <input type="text" class="inp" style="width:130px;"
                    name="firstName" value="${fieldValue(bean: employeeCommand, field: 'firstName')}"/>
        </div>
        <div class="namerow"><span>Middle Name</span>
            <input type="text" class="inp" style="width:120px;"
                    name="middleName" value="${fieldValue(bean: employeeCommand, field: 'middleName')}"/>
        </div>
        <div class="namerow"><span>Last Name</span>
            <input type="text" class="inp" style="width:125px;"
                    name="lastName" value="${fieldValue(bean: employeeCommand, field: 'lastName')}"/>
        </div>
        <div class="namerow"><span>Title</span>
            <input type="text" class="inp" style="width:125px;"
                    name="title" value="${fieldValue(bean: employeeCommand, field: 'title')}"/>
        </div>
        <div class="namerow"><span>Department</span>
            <input type="text" class="inp" style="width:125px;"
                    name="department" value="${fieldValue(bean: employeeCommand, field: 'department')}"/>
        </div>
        <div class="namerowbig">
            <span>Supervisor</span>
            <g:select class="listbox" style="width:170px;" name="supervisor" from="${supervisors}"
                    optionKey="id" value="${employeeCommand?.supervisor}"
                    noSelection="['':'(Select One)']"/>
        </div>
    </div>
    <div class="form-section">

        <div class="namerowbig"><span>Business Unit Requester</span>
            <g:select class="listbox" style="width:95px;" name="businessUnitRequester" from="${businessUnitRequesters}"
                    optionKey="id" value="${employeeCommand?.businessUnitRequester}"
                    noSelection="['':'(Select One)']"/>
        </div>
        <div class="namerowbig"><span>Phone</span>
            <input type="text" class="inp" style="width:185px;"
                    name="phone" value="${fieldValue(bean: employeeCommand, field: 'phone')}"/>
        </div>
        <div class="namerowbig"><span>Email</span>
            <input type="text" class="inp" style="width:190px;"
                    name="email" value="${fieldValue(bean: employeeCommand, field: 'email')}"/>
        </div>
        <div class="namerowbig"><span>Employee #</span>
            <input type="text" class="inp" style="width:145px;"
                    name="workerNumber" value="${fieldValue(bean: employeeCommand, field: 'workerNumber')}"/>
        </div>
        <div class="namerowbig"><span> SLID</span>
            <input type="text" class="inp" style="width:145px;"
                    name="slid" value="${fieldValue(bean: employeeCommand, field: 'slid')}"/>
        </div>
        <div class="namerowbig"><span> Badge Number</span>
            <input type="text" class="inp" style="width:145px;"
                    name="badgeNumber" value="${fieldValue(bean: employeeCommand, field: 'badgeNumber')}"/>
        </div>
        <div class="namerowbig"><span>Status</span>
            <g:select class="listbox" style="width:160px;" name="status" id="status" from="${WorkerStatus.list()}" noSelection="['':'(Select One)']" optionKey="key" value="${employeeCommand?.status}"/>
        </div>
        <div class="namerowbig"><span>Role Status</span>
            <g:select class="listbox" style="width:140px;" name="roleStatus" id="roleStatus" optionKey="key" from="${EntitlementRoleAccessStatus.list()}" noSelection="['':'(Select One)']" value="${employeeCommand?.roleStatus}"/>
        </div>
    </div>
    <div style="clear:both;text-align:center;">
        <br/>
        <br/>

        <input type="submit" class="button" value="Filter"/>
        <input type="button" class="button" value="Close" onclick="jQuery.modal.close();"/>
    </div>
</g:form>
