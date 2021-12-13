<div>
   <ul>
        <li><g:link controller="employee" action="employeeOrgTreeView" params="[workerSlid: employee.person.slid]">View Direct Reports</g:link></li>
        <li><g:link controller="employee" action="show" id="${employee.id}">View Details</g:link></li>
    </ul>
</div>
