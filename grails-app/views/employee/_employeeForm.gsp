<g:render template="/shared/profileImage" model="[worker:worker]"/>
<div class="form-section" style="margin-left:70px;">
    <div class="namerow"><span>First Name</span><span class="asterisk">*</span>
        <input type="text" class="inp ${hasErrors(bean: worker, field: 'firstName', 'errors')}" style="width:130px;"
                name="firstName" value="${worker.firstName}"/>
    </div>
    <div class="namerow"><span>Middle Name</span>
        <input type="text" class="inp " style="width:120px;"
                name="middleName" value="${worker.middleName}"/>
    </div>

    <div class="namerow"><span>Last Name</span><span class="asterisk">*</span>
        <input type="text" class="inp ${hasErrors(bean: worker, field: 'lastName', 'errors')}" style="width:125px;"
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
    <g:render template="/employee/supervisors" model="[worker:worker,supervisors:supervisors]"/>
    <g:render template="/shared/businessUnitRequesters"
            model="[worker:worker,
        remainingBusinessUnitRequesters:remainingBusinessUnitRequesters,
        selectedBusinessUnitRequesters:selectedBusinessUnitRequesters]"/>
</div>
<div class="form-section" style="margin-left:100px;">
    <div class="namerowbig"><span>Phone</span>
        <input type="text" class="inp " style="width:185px;"
                name="phone" value="${worker.phone}"/>
    </div>
    <div class="namerowbig"><span>Email</span>
        <input type="text" class="inp ${hasErrors(bean: worker, field: 'email', 'errors')}" style="width:190px;"
                name="email" value="${worker.email}"/>
    </div>


    <div class="namerowbig"><span>Employee #</span>
        <input type="text" class="inp " style="width:145px;"
                id="workerNumber" name="workerNumber" value="${worker.workerNumber}"/>
    </div>


    <div class="namerowbig"><span>SLID</span><span class="asterisk">*</span>
        <input type="text" class="inp ${hasErrors(bean: worker, field: 'slid', 'errors')}" style="width:125px;"
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
