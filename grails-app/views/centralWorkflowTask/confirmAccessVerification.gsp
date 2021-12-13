<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Access Verification Report Confirmation</title>
</head>
<body>
<br/>
<div id="wrapper">
    <h1>Access Verification Report Confirmation</h1>
    <br/>
    <div style="margin:0 50px;font-size:14px;">
        <div>
            <span>
                Thank you <b><care:fullName slid="${taskPermittedSlid.slid}" /></b>.
                <br/>
                <br/>
                Your confirmation for Access Verification Report has been received on <b>${new Date().format("EEEE MMMM dd, yyyy 'at' h:mm a")}.</b>
                <br/>
                <br/>
            </span>
        </div>
    </div>
</body>
</html>
