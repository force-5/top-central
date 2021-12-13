<div id="footer1"></div>
<div style="text-align:right;">Copyright © 2009 Force 5, Inc. All rights reserved.</div>

<%@ page import="org.codehaus.groovy.grails.commons.GrailsApplication" %>
<%@ page import="grails.util.GrailsUtil" %>
<g:if test="${GrailsUtil.environment == GrailsApplication.ENV_DEVELOPMENT}">
    <g:each var="c" in="${grailsApplication.controllerClasses}">
        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
    </g:each>
</g:if>
</div>