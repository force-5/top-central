<div id="addNewsAndNotes" class="popupWindow" style="text-align:center;width:350px;">
    <div class="popupWindowTitleForNews">Add News & Notes</div>

    <g:formRemote name="newsAndNotesForm" method="post"
            url="${[controller:'news',action:'save']}" onSuccess="updateNewsDiv(e);">
        <br/>
        <div class="popupWindowTextForNews" style="clear:both;">
            <span style="float:left; display:block;">News&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <input type="text" name="headline" maxlength="150" value="" style="width:260px;">
        </div>
        <br/>
        <div class="popupWindowTextForNews" style="clear:both;">
            <span style="float:left;">Description&nbsp;</span>
            <textarea rows="2" cols="3" name="description" style="height:70px; width:260px;" value=""></textarea>
        </div>
        <br/><br/>
        <input type="submit" class="button" name="okButton" value="OK">&nbsp;&nbsp;
        <input type="button" class="button" value="Cancel" onclick="jQuery.modal.close();"/>
    </g:formRemote>
</div>
<script type="text/javascript">
    function updateNewsDiv(e) {
        jQuery('#newsListDiv').prepend(e.responseText);
        jQuery('#headerNewsListDiv').prepend(e.responseText);
        jQuery.modal.close();
    }
</script>