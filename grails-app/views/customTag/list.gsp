<%@ page import="com.force5solutions.care.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="contractor"/>
    <title>Custom Tag List</title>
</head>

<body>
<br/>

<div id="wrapper">
    <div id="right-panel">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New CustomTag</g:link></span>
        </div>

        <div class="body">
            <h1>CustomTag List
                <span style="float: right;color: #666666;">Show: <g:select from="[10, 25, 50, 100, 'Unlimited']"
                                                                           name="rowCount"
                                                                           id="rowCount" value="${max}"/></span>
            </h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list" id="list" style="padding-top: 10px;">
                <g:render template="customTagsTable"
                          model="[customTagList: customTagList, customTagTotal: customTagTotal, offset: offset, max: max, order: order, sort: sort]"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
