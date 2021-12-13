<div style="position:relative;"id="documentsTableDiv1" class="body"  >
    <h1>Documents</h1>
        <table>
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
        <span>Message: ${message}</span>
    <br/>
    <input type="button" onclick="jQuery.modal.close();" value="Close" class="button" style="position:absolute;bottom:-15px;left:45%;">
</div>
<script type="text/javascript">
    if (jQuery('#documentsTable tr').size() < 5) {
        jQuery('#documentsTableDiv').css('height', 'auto');
    }
</script>