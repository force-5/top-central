<%@ page import="com.force5solutions.care.ldap.Permission" %>
<g:if test="${care.hasPermission(permission: Permission.CAN_ACCESS_ADMIN_MENU)}">
    <li id="admin" style="cursor:pointer;">

        <a style="font-size:18px;">
            <div class="left-padding10"><strong>Admin</strong>
                <img height="10" style="display:none;" id="bottomArrowAdminImg" src="${createLinkTo(dir: 'images', file: 'BottomArrow.jpg')}" alt="Bottom Arrow"/>
                <img height="12" id="rightArrowAdminImg" src="${createLinkTo(dir: 'images', file: 'RightArrow.jpg')}" alt="Right Arrow"/>
            </div>
        </a>
        <ul style="display:none;">
            <li>
                <g:link controller="certification" action="list"
                        class="${params?.controller=='certification'?'current':''}">
                    <div class="left-padding20">Certifications</div>
                </g:link>
            </li>

            <li>
                <g:link controller="location" action="newTree"
                        class="${params?.controller=='location'?'current':''}">
                    <div class="left-padding20">Organizational Tree</div>
                </g:link>
            </li>
        </ul></li>
</g:if>
<g:else>
    <div style="font-size:18px;color:gray;" class="left-padding10"><strong>Admin</strong>
        <img height="12" src="${createLinkTo(dir: 'images', file: 'RightArrow.jpg')}" alt="Right Arrow"/>
    </div>
</g:else>
