<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Simulated Certification Expiration Notification</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div class="body">
    <div class="list">
        <h1 style="padding-top:20px;">Please enter the future date/time to see the expected behavior of Certification Expiration Notification:</h1><br/>
        <g:form action="simulateCertificationExpirationNotification" method='post'>
            <calendar:datePicker style="float:left" name="futureDate" id="futureDate"/>
            <br/>
            <br/>
            <g:submitButton name="submit" value="OK"/>
        </g:form>
    </div>
    <br/>
    <br/>

    <h1>Simulated Certification Expiration Notification for simulated date: ${simulationDate.myFormat()}</h1>
    <div class="list">
        <table>
            <thead>
            <tr>
                <th>Worker Name</th>
                <th>SLID</th>
                <th>Certification</th>
                <th>Days Remaining</th>
                <th>Template Name</th>
                <th>Notification Period</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${notificationVOs}" status="i" var="notificationVO">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td>${notificationVO?.workerCertification?.worker?.firstMiddleLastName}</td>

                    <td>${notificationVO?.workerCertification?.worker?.slid}</td>

                    <td>${notificationVO?.workerCertification?.certification}</td>

                    <td>${notificationVO?.daysRemaining}</td>

                    <td>${notificationVO?.taskTemplateName}</td>

                    <td>${notificationVO?.notificationPeriod}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
