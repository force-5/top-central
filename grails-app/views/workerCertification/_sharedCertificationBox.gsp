<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.ldap.Permission" %>
<div id="wrapper">
    <div id="right-panel">
        <div align="center"><b>${flash.message}</b></div>
        <div id="right-top1">
            <div id="contractor">
                <span class="font12-bold">${worker}</span>
            </div>
            <div>
                <div class="in-panel">
                    <div class="form-section3">
                        <div class="namerowbig">
                            <g:if test="${worker instanceof Contractor}">
                                <g:set var="permission" value="${Permission.CREATE_CONTRACTOR_CERTIFICATION}"/>
                            </g:if>
                            <g:else>
                                <g:set var="permission" value="${Permission.CREATE_EMPLOYEE_CERTIFICATION}"/>
                            </g:else>
                            <g:if test="${!worker.terminateForCause && care.hasPermission(permission: permission, worker: worker)}">
                                <a class="department-enabled  createContractorCertificationLink" rel="addWorkerCertification" onclick='updateNewCertificationFormWithUrl("");
                                return false;'>
                                    <span>Add&nbsp;Certification</span>
                                </a>
                            </g:if>
                            <g:else>
                                <a class="department-disabled createContractorCertificationLink">
                                    <span>Add&nbsp;Certification</span>
                                </a>
                            </g:else>
                        </div>
                    </div>
                </div>
                <div class="in-panel">
                    <g:render template="/workerCertification/certificationTable"
                            model="[workerCertifications:workerCertifications, worker:worker]"/>
                </div>
                <div id="completedCourses_div">
                    <g:if test="${worker.courses}">
                        <div id="miss1" style="margin:0;">
                            <div id="miss">
                                <span>Completed Courses</span>
                            </div>
                        </div>
                        <div id="background" style="margin:0;">
                            <div style="padding:10px;">
                                <g:each in="${worker.courses}" var="course" status="i">
                                    ${course}
                                    <g:if test="${i < worker.courses.size() -1}">&nbsp;|&nbsp;</g:if>
                                </g:each>
                            </div>
                            <div id="background-bot"></div>
                        </div>
                    </g:if>
                </div>
                <div id="missingCertification_div">
                    <care:missingCertifications worker="${worker}"/>
                </div>
                <div id="missingCourses_div">
                    <care:missingCourses worker="${worker}"/>
                </div>
            </div>
        </div>
    </div>
</div>
