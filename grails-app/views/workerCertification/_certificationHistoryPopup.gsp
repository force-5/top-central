<%@ page import="com.force5solutions.care.cc.Contractor; com.force5solutions.care.*; com.force5solutions.care.ldap.Permission" %>
<div class="popup-container">
    <div><img alt="" src="${resource(dir: 'images', file: 'topbg1.gif')}"/></div>
    <div class="pop-header">${workerCertification?.certification} History</div>

    <div class="popup-body1">

        <div class="popup-body">
            <g:each in="${oldWorkerCertifications}" var="workerCertification">
                <g:render template="/workerCertification/certificationReadOnly" model="[workerCertification:workerCertification]"/>
            </g:each>

        </div>
    </div>
    <div class="btn-con2">
        <g:if test="${worker instanceof Contractor}">
            <g:set var="permission" value="${Permission.CREATE_CONTRACTOR_CERTIFICATION}"/>
            <g:set var="permissionRenew" value="${Permission.RENEW_CONTRACTOR_CERTIFICATION}"/>
        </g:if>
        <g:else>
            <g:set var="permission" value="${Permission.CREATE_EMPLOYEE_CERTIFICATION}"/>
            <g:set var="permissionRenew" value="${Permission.RENEW_EMPLOYEE_CERTIFICATION}"/>
        </g:else>
        <g:if test="${!worker?.terminateForCause && care.hasPermission(permission: permission, worker: worker)}">

            <g:if test="${workerCertification?.dateCompleted}">
                <div class="buttona2">
                    <g:if test="${care.hasPermission(permission: permissionRenew, worker: worker)}">

                        <g:remoteLink controller="workerCertification"
                                action="renewWorkerCertification"
                                id="${worker?.id}"
                                params="[certificationId:workerCertification?.certification?.id]"
                                onSuccess="editWorkerCertification(e)">
                            <span>&nbsp;Renew&nbsp;Certification</span>
                        </g:remoteLink>
                    </g:if>
                    <g:else>
                        <a><span style="color: gray;">&nbsp;Renew&nbsp;Certification</span></a>
                    </g:else>
                </div>
            </g:if>

            <div class="buttona2"><a href="#" onclick="showEditCertificationPopup()"><span>Hide History</span></a></div>
            <div class="buttona3" style="padding:0px;margin-top:2px;"><a href="#" onclick="jQuery.modal.close();"><span>Cancel</span></a></div>
        </g:if>
        <g:else>
            <div class="department3">
                <div class="buttona4"><a href="#" onclick="jQuery.modal.close();"><span>Close</span></a></div>
            </div>
        </g:else>
    </div>
</div>