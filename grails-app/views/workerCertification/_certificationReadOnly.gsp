<div>
    <div align="center" style="font-weight:bold; font-size:12px;">Date Completed</div>
    <div align="center">${workerCertification.dateCompleted?.myFormat()}</div>

    <g:if test="${workerCertification.provider || workerCertification.subType}">
        <div class="provider">
            <g:if test="${workerCertification.provider}">
                <div class="provider1">Provider</div>
            </g:if>
            <g:if test="${workerCertification.subType}">
                <div class="type">Type</div>
            </g:if>
            <g:if test="${workerCertification.provider}">
                <div class="provider1">${workerCertification.provider}</div>
            </g:if>
            <g:if test="${workerCertification.subType}">
                <div class="type">${workerCertification.subType}</div>
            </g:if>
            <div class="clr"></div>
        </div>
    </g:if>

    <g:if test="${workerCertification.certification.trainingRequired}">
        <div class="training">
            <div class="training-status">Training Status</div>
            <div class="training-date">Training Date</div>
            <div class="training-time">Training Provider</div>
            <div class="training-status">${workerCertification.trainingStatus?.status}</div>
            <div class="training-date">${workerCertification.trainingStatus?.date?.myFormat()}</div>
            <div class="training-time">${workerCertification.trainingStatus?.provider}</div>
            <div class="clr"></div>
        </div>
    </g:if>
    <g:if test="${workerCertification.certification.testRequired}">
        <div class="training">
            <div class="training-status">Test Status</div>
            <div class="training-date">Test Date</div>
            <div class="training-time">Test Provider</div>
            <div class="training-status">${workerCertification.testStatus?.status}</div>
            <div class="training-date">${workerCertification.testStatus?.date?.myFormat()}</div>
            <div class="training-time">${workerCertification.testStatus?.provider}</div>
            <div class="clr"></div>
        </div>
    </g:if>
    <g:if test="${workerCertification.certification.affidavitRequired && workerCertification.affidavits}">
        <div class="pdf">
            <g:each in="${workerCertification.affidavits}" var="affidavit">
                <div class="search">
                    <g:link controller="careUtil" action="downloadFile" id="${affidavit.id}" params="[className: 'com.force5solutions.care.cc.CentralDataFile', fieldName: 'bytes']">
                        <img src="${createLinkTo(dir: 'images', file: 'magnifing.gif')}" alt="Magnify" border="0"/>
                    </g:link>
                &nbsp;&nbsp;${affidavit.fileName} <span>${affidavit.dateCreated?.myFormat()}</span></div>
            </g:each>
        </div>
    </g:if>
    <div class="clr" align="center"><img src="${resource(dir: 'images', file: 'blue-line.jpg')}"/></div> <br/>
</div>
