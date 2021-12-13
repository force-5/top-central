<div id="container">
    <div id="header">
        <h1>
            <a href="${care.createLink()}">
            <img src="${createLinkTo(dir: 'images', file: ('contractorlogo.jpg'))}"
                alt="TOP By Force 5" width="203" border="0"/>
            </a>
        </h1>
<span style="float:right;margin-top:10px;">
    ${session.loggedUser}&nbsp;${(session.roles)? '('+session.roles?.join(',')+')': ''}&nbsp;&nbsp;&nbsp;&nbsp;
        <a name="logout" href="${createLink(controller: 'login', action: 'logout')}">Logout</a></span>
    </div>
    

