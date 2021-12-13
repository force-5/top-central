<div class="font12-bold" style="float:right;text-align:right;padding:5px 0px;">
    <g:if test="${worker?.id}">
        Status: <care:statusIcon status="${status}"/>
        <g:if test="${date}">Last Status Change: ${date?.myDateTimeFormat()}</g:if>
    </g:if>
</div>
<div class="clr"></div>