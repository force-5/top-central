<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>TOP By Force 5 : Access Request</title>
  <g:render template="/layouts/importCssAndJs"/>
  <!--[if IE 6]>
<style type="text/css">
      .loginBox1{background:url(${resource(dir:'images', file:'shadow.png')}) no-repeat bottom right;}
      </style>
  <![endif]-->
  <!--[if IE 7]>
 <style type="text/css">
      .loginBox1{background: url(${resource(dir:'images', file:'shadow.png')}) no-repeat bottom right;}
      </style>   <![endif]-->
  <!--[if gte IE 8]>
<style type="text/css">
      .loginBox1{background: url(${resource(dir:'images', file:'shadow.png')}) no-repeat bottom right ;}
      </style>   <![endif]-->

</head>
<body>
<div id="wrapper">
</div>
<div class="loginBox1">
<div class="loginBox">
  <h1><a href="${care.createLink()}"><img src="${resource(dir: 'images', file: 'careCentralLogo.png')}" alt="TOP By Force 5" width="203" border="0"/></a></h1>
     <div id="errorsBox"><g:if test="${flash.message}">
       <div style="color:${hasErrors ? 'red' : 'blue'};">${flash.message}</div>
  </g:if></div>
  <h2 style="color:#ff6800; font-size:22px; line-height:36px;">Access Request</h2>
  <h3 style="font-size:16px; line-height:22px;  font-weight:normal;">To Request Access, Enter your SLID, and click the Submit button</h3>
  <br/>
  <g:form action="getAccessInfo" method="post">
    <input class="${hasErrors ? 'errors' : ''} inputNew" type="text" id="slid" name="slid"/>
    <br/>
    <input type="submit" class="buttonNew" value="Submit"/>
  </g:form>
</div>
</div>
</body>
</html>