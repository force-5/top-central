<div style="position:relative;">
    <h1>Documents</h1>
    <div id="documentsTableDiv" style="height:100px; overflow:auto;">
        <table id="documentsTable">
            <g:set value='com.force5solutions.care.cc.CentralDataFile' var='className'/>
            <g:each in="${documents}" status="i" var="document">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>
                        <g:link controller="careUtil" action="downloadFile" id="${document.id}" params="[className: className, fieldName: 'bytes']">
                            ${document?.fileName}
                        </g:link>
                    </td>
                </tr>
            </g:each>
        </table>
    </div>
    <br/>
    <div>
        <span>Message: ${message}</span>
    </div>
</div>
<script type="text/javascript">
    if (jQuery('#documentsTable tr').size() < 5) {
        jQuery('#documentsTableDiv').css('height', 'auto');
    }
</script>
