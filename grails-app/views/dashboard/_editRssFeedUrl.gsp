<div id="editRssFeed" class="popupWindow" style="text-align:center;width:350px;">
    <div class="popupWindowTitleForNews">RSS Feed URL</div>
    <g:formRemote name="rssFeedForm" method="post"
            url="${[controller:'dashboard',action:'editRssFeedUrl']}" onSuccess="updateFeedsDiv(e);">
        <br/>
        <div class="popupWindowTextForNews" style="clear:both;">
            <span style="float:left; display:block;">Feed Url &nbsp;&nbsp;&nbsp;</span>
            <input type="text" name="feedUrl" maxlength="250" value="${rssFeedUrl}" style="width:260px;">
        </div>
        <br/><br/><br/>
        <input type="submit" class="button" name="okButton" value="OK">&nbsp;&nbsp;
        <input type="button" class="button" value="Cancel" onclick="jQuery.modal.close();"/>
    </g:formRemote>
</div>
<script type="text/javascript">
    function updateFeedsDiv(e) {
        jQuery('#rssFeedDiv').html(e.responseText);
        jQuery.modal.close();
    }
</script>