package com.force5solutions.care.cc

import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.ldap.*
import static com.force5solutions.care.common.CareConstants.*

class PermissionService {

    private Boolean validateIfBusinessUnitRequester(String slid) {
        return BusinessUnitRequester.countBySlid(slid)
    }

    private Boolean validateIfBusinessUnitRequesterOwns(String slid, Worker worker) {
        return (slid in worker?.businessUnitRequesters?.collect {it.slid})
    }

    private Boolean validateIfEmployee(String slid) {
        return Employee.countBySlid(slid)
    }

    private Boolean validateIfEmployeeSupervisor(String slid) {
        return EmployeeSupervisor.countBySlid(slid)
    }

    private Boolean validateIfEmployeeSupervisorOwns(String slid, Worker worker) {
        return (worker in EmployeeSupervisor.getInheritedSubordinates(slid))
    }

    private Boolean validateIfSponsorHasCertifications(String slid, Worker worker, Location location) {
        if (validateIfBusinessUnitRequesterOwns(slid, worker)) {
            BusinessUnitRequester businessUnitRequester = BusinessUnitRequester.findBySlid(slid)
            Set<Certification> burRequiredCertifications = location.sponsorCertifications + location.inheritedSponsorCertifications
            if (burRequiredCertifications) {
                Set<WorkerCertification> effectiveCertifications = businessUnitRequester.effectiveCertifications?.findAll {!it.isExpired()}
                return (burRequiredCertifications.every {it in effectiveCertifications*.certification})
            } else {
                return true
            }
        } else {
            //Validate if Employee Supervisor has required certifications
        }
        return false
    }

    private Boolean validateIfSelf(String slid, Worker worker) {
        return (slid == worker?.slid)
    }

    private Boolean isPermitted(String slid, Long level, Worker worker, Location location) {
        if (level == NOT_AUTHORIZED_PERMISSION_LEVEL) {
            return false
        }
        if (level == UNRESTRICTED_ACCESS_PERMISSION_LEVEL) {
            return true
        }
        if ((level % ACCESS_IF_BUR_PERMISSION_LEVEL) == 0 && (validateIfBusinessUnitRequester(slid))) {
            return true
        } else if ((level % ACCESS_IF_EMPLOYEE_PERMISSION_LEVEL) == 0 && (validateIfEmployee(slid))) {
            return true
        } else if ((level % ACCESS_IF_EMPLOYEE_SUPERVISOR_PERMISSION_LEVEL) == 0 && (validateIfEmployeeSupervisor(slid))) {
            return true
        } else if ((level % ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL) == 0 && (validateIfBusinessUnitRequesterOwns(slid, worker))) {
            return true
        } else if ((level % ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL) == 0 && (validateIfEmployeeSupervisorOwns(slid, worker))) {
            return true
        } else if ((level % ACCESS_IF_SPONSOR_HAS_CERTIFICATIONS_PERMISSION_LEVEL) == 0 && (validateIfSponsorHasCertifications(slid, worker, location))) {
            return true
        } else if ((level % ACCESS_IF_SELF_PERMISSION_LEVEL) == 0 && (validateIfSelf(slid, worker))) {
            return true
        }

        return false
    }

    public Boolean hasPermission(Permission permission, Worker worker = null, Location location = null, boolean ignorePermissionLevel = false) {
        String slid = SessionUtils.session.loggedUser
        if (!slid) {
            return false
        }
        List<SecurityRole> roles = SessionUtils.session?.roles ? SecurityRole.findAllByNameInList(SessionUtils.session?.roles) : []
        Boolean result = roles?.any {SecurityRole role ->
            PermissionLevel permissionLevel = PermissionLevel.findByPermissionAndRole(permission, role)
            if (!permissionLevel) {return false}
            if (ignorePermissionLevel) {
                return (permissionLevel.level ? true : false)
            }
            Long level = permissionLevel.level
            return isPermitted(slid, level, worker, location)
        }
        return result
    }

}
