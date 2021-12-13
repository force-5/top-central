<html>
<head>
    <meta name="layout" content="contractor"/>
    <title>TOP By Force 5 : Contractor</title>
</head>
<body>

<br>

<div id="wrapper">
    <g:if test="${flash.message}">
        <div align="center"><b>${flash.message}</b></div>
    </g:if>
    <g:hasErrors bean="${worker}">
        <div class="error-status" style="text-align:center;">
            Please enter values into the required fields<br/>
            <g:if test="${worker.slid}">
                <g:fieldError bean="${worker}" field="slid"/>
            </g:if>
        </div>
    </g:hasErrors>

</div>

<div id="right-panel">
    <g:form controller="contractor" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${worker?.id}"/>
        <div id="right-top">
            <g:render template="/contractor/addContractor" model="[worker:worker]"/>
        </div>

        <div id="submit-button">
            <g:actionSubmit class="button" action="createContractorFromContractorHrInfo" value="Add"/>&nbsp;
        </div>

    </g:form>
    <div id="create_businessUnitRequester">
        <g:formRemote name="businessUnitRequesterForm" onSuccess="updateList('note_businessUnitRequester','businessUnitRequesters', e)"
                method="post" url="${[controller:'businessUnitRequester', action:'saveBusinessUnitRequester']}">
            <div id="note_businessUnitRequester">
            </div>
        </g:formRemote>
    </div>
</div>

</body>
</html>