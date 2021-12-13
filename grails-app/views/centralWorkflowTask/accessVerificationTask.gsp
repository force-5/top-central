<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Access Verification</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div class="body">

    <style type="text/css">
    h1 {
        color: #F15A29;
        font-weight: normal;
        font-size: 16px;
        margin: 8px 0px 3px 0px;
        padding: 0px;
    }

    .list {
        border: 1px solid #ddd;
        margin-bottom: 20px;
        padding: 5px 10px;

    }

    .heading-access {
        font-size: 16px;
        color: #7e7e7e
    }

    .table-access-verification {
        font-size: 14px;
        border: none;
    }

    .table-access-verification th {
        font-size: 14px;
        padding: 6px 6px;
        background: #fff;
        border: none;
        border-bottom: 1px solid #ddd;
    }

    .table-access-verification th:hover {
        background: #fff;
    }

    .table-access-verification td {
        font-size: 14px;
        padding: 10px 6px;
        border: none
    }

    table {
        border: 1px solid #ccc;
        width: 100%;
    }

    tr {
        border: 0;
    }

    td, th {
        font: 11px verdana, arial, helvetica, sans-serif;
        line-height: 12px;
        padding: 3px 6px;
        text-align: left;
        vertical-align: top;
    }

    th {
        background: #fff url(../images/skin/shadow.jpg);
        color: #666;
        font-size: 10px;
        line-height: 17px;
        padding: 2px 6px;
    }

    th a:link, th a:visited, th a:hover {
        color: #333;
        display: block;
        font-size: 10px;
        text-decoration: none;
        width: 100%;
    }

    .odd {
        background: #CFD0D2;
    }

    .even {
        background: #fff;
    }

    .buttonContainer {
        border: 0px;
    }
    </style>

    <h1>Access Verification</h1>
    <p style="font-size:14px; padding-bottom:20px; text-align:justify !important;">Below is a list of personnel reporting to ${supervisorName} that have authorized cyber and/or authorized unescorted physical access to the
    System Control Center's Critical Cyber Assets (CCAs). Please verify these individuals' continued need for access by clicking the <strong>Confirm</strong> link below.
    Should any of these employees listed no longer require access, please click the <strong>Make Changes</strong> link to revoke employees no longer needing access.
    Failure to verify continued need for access will result in the revocation of access to these CCAs for the individuals listed below.</p>

    <g:form action="sendUserResponse" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${taskId}"/>
        <g:each in="${rolesMap}" var="roleMap">
            <div class="list">
                <p class="heading-access">Entitlement Role : <span style="color:black;">${roleMap.key}</span></p>
                <table cellpadding="0" cellspacing="0" border="0" class="table-access-verification">
                    <thead>
                    <tr>
                        <th width="300">Name</th>
                        <th>SLID</th>
                        <th>Badge</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roleMap.value}" status="i" var="emp">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td>${emp.name}</td>
                            <td>${emp.slid}</td>
                            <td>${emp.badge}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </g:each>
        <div class="list buttonContainer" style="text-align:center;">
            <input class="button" type="submit" value="Confirm"/>
            <input class="buttonAccessVerification" type="button" value="Make Changes" onclick="window.location.href = '${createLink(controller:'employee',action:'list')}'"/>
        </div>
    </g:form>
</div>
</body>
</html>
