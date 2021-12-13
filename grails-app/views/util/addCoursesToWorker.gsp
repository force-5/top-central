<%@ page import="com.force5solutions.care.ldap.Permission;" %><html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Worker</title>
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
<g:form action="saveCoursesToWorker">
    <div class="namerowbig"><span>Personnel : &nbsp;&nbsp;</span>
        <g:select class="listbox" name="id" from="${workers}" optionKey="id" optionValue="person" noSelection="['':'(Select One)']"/>
    </div>
    <div style="float:left;width: 200px;"><span>Courses : &nbsp;&nbsp;</span>
        <ui:multiSelect
                name="courses"
                from="${courses}"
                value=""
                noSelection="['':'(Select One)']"
                class="listbox"
                style="width:200px;"/>
    </div>
    <div style="clear: both; text-align :right; width: 200px; margin-top: 20px;"><g:submitButton name="submit" value="Submit"/></div>
</g:form>

</body>
</html>