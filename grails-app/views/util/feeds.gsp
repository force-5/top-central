<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Trigger Feeds</title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
</div>
<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
</div>
<br/>
<div>
<h1> <g:link action="triggerHrInfoFeed" target="_blank"> Trigger HrInfo Feed </g:link> </h1>
<h1> <g:link action="triggerCourseFeed" target="_blank"> Trigger Course Feed </g:link> </h1>
<h1> <g:link action="triggerPraFeed" target="_blank"> Trigger PRA Feed </g:link> </h1>
<h1> <g:link action="triggerActiveWorkerFeed" target="_blank"> Trigger Active Worker Feed </g:link> </h1>
</div>

</body>
</html>