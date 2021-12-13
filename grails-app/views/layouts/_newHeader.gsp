<div id="container">
<div id="headerNew">
    <div class="dashboarddemo2-box-container21">
        <a href="${createLink(uri: '/')}">
            <img alt="logo" height="82" style="border: none;"
                    src="${createLinkTo(dir: 'images', file: ('careCentralLogo.png'))}"/>
        </a>
</div>
<care:headerNews/>
<g:if test="${session.loggedUser}">
    <span style="float:right;margin:10px 5px;">
        ${session.loggedUser}&nbsp;${(session.roles) ? '(' + session.roles?.join(',') + ')' : ''}&nbsp;&nbsp;&nbsp;&nbsp;
        <a name="logout" href="${createLink(controller: 'login', action: 'logout')}">Logout</a></span>
</g:if>
<g:else>
    <span style="float:right;margin:10px 5px;">
        <g:if test="${!(params.controller == 'dashboard')}">
            <a name="login" href="${createLink(controller: 'login', action: 'index')}">Login</a>
        </g:if>
    </span>
</g:else>
</div>
<g:if test="${session.loggedUser}">
    <g:render template="/layouts/topMenuBar"/>
</g:if>
<div id="showNewsAndNotes" class="popupWindowShowNews" style="text-align:center;width:350px; padding-bottom:30px;">

    <div style="clear:both;"></div>
</div>
