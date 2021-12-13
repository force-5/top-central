<%@ page import="com.force5solutions.care.cc.CcEntitlementRole; com.force5solutions.care.cc.Location" %>
<ul>
    <g:if test="${tree instanceof Map && !(tree.any{it.key instanceof String})}">
        <g:each in="${tree}" var="item">
            <g:set var="mapKeys" value="${tree.keySet().size()}"/>
            <li class="${(mapKeys > 1) ? 'closed' : ''}"><span>${item.key}</span><care:showRolesInTreeView tree="${item.value}"/></li>
        </g:each>
    </g:if>
    <g:else>
        <g:each in="${tree}" var="item">
            <g:set var="mapKeys" value="${tree.keySet().size()}"/>
            <li class="${(mapKeys > 1) ? 'closed' : ''}"><span>${item.key}</span>
                <ul>
                    <g:each in="${item.value}" var="role">
                        <li class="selectable ${(role in entitlementRoles) ? 'hidden' : ''}" rel="entitlementRole_${role.id}">
                            <span>${role}</span>
                        </li>
                    </g:each>
                </ul>
            </li>
        </g:each>
    </g:else>
</ul>

