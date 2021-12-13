import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.ldap.PermissionLevel
import com.force5solutions.care.ldap.SecurityRole
import static com.force5solutions.care.common.CareConstants.*

fixture {
    careAdmin(SecurityRole) {
        name = CAREADMIN
        description = 'Admin with all permissions'
    }
    careEditor(SecurityRole) {
        name = CAREEDITOR
        description = 'CARE Editor'
    }
}

post {
    Permission.values().each {Permission permission ->
        new PermissionLevel(role: careAdmin, permission: permission, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
    }
    new PermissionLevel(role: careEditor, permission: Permission.CAN_ACCESS_PERSONNEL_MENU, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
    new PermissionLevel(role: careEditor, permission: Permission.CAN_ACCESS_ADMIN_MENU, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
    new PermissionLevel(role: careEditor, permission: Permission.CAN_ACCESS_CONTACTS_MENU, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
    new PermissionLevel(role: careEditor, permission: Permission.CAN_ACCESS_REPORTS_MENU, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
    new PermissionLevel(role: careEditor, permission: Permission.CAN_ADD_CONTRACTOR_LOCATION, level: ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL).s()
    new PermissionLevel(role: careEditor, permission: Permission.READ_CONTRACTOR_ACCESS, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL) * (ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.CREATE_CONTRACTOR_ACCESS, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL) * (ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.UPDATE_CONTRACTOR_ACCESS, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL) * (ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.REQUEST_CONTRACTOR_REVOKE, level: (ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL)).s()
    new PermissionLevel(role: careEditor, permission: Permission.READ_CONTRACTOR_PROFILE, level: ((ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.READ_CONTRACTOR_CERTIFICATION, level: ((ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.READ_EMPLOYEE_PROFILE, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.READ_EMPLOYEE_CERTIFICATION, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.READ_EMPLOYEE_ACCESS, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.CREATE_EMPLOYEE_ACCESS, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.UPDATE_EMPLOYEE_ACCESS, level: ((ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL))).s()
    new PermissionLevel(role: careEditor, permission: Permission.REQUEST_EMPLOYEE_ACCESS, level: (ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL)).s()
}
