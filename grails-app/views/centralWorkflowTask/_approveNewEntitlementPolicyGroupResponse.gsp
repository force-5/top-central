<div style="margin:0 50px;font-size:14px;">
    <g:form action="sendGroupResponse" method="post" enctype="multipart/form-data">
        <g:render template="groupResponseTable" model="[tasks: tasks]"/>
        <div style="font-size:14px;">
            <span style="float:left;">Action for approval of Entitlement Policies: &nbsp;&nbsp;&nbsp;&nbsp;
            </span>
            <g:if test="${task.actions}">
                <span><g:select class="listbox" style="padding:0;width:150px;" name="userAction"
                                from="${task.actions}"/></span>
            </g:if>
        </div>
        <br/><br/>

        <div id="explanation-error" class="error-status" style="text-align:center; display:none;">
            <span>Comment can not be left blank.</span></div>

        <div>
            <span>Comment:</span>
            <g:render template="accessJustificationMarginLeft130"/>
            <input type="hidden" name="id" value="${task.id}"/>
            <g:render template="/centralWorkflowTask/attachment"/>
        </div>

        <div style="text-align:center;">
            <input class="button" type="submit" value="Submit"/>
        </div>
    </g:form>
</div>
<script type="text/javascript">
    jQuery(function () {
        jQuery('#actionDate').datetimepicker({
            ampm:true
        });
        jQuery('.cannedSelectBox').focus();
        jQuery('form').submit(function () {
            if (jQuery(':checkbox:checked').length < 1) {
                alert('Please select any one check-box');
                return false;
            } else {
                return isStatusChangeCommentEmpty('accessJustification', 'explanation-error')
            }
        });
        if (jQuery("#tablesorter tr").size() > 1) {
            jQuery("#tablesorter").tablesorter({textExtraction:myTextExtraction, sortList:[
                [1, 1]
            ], headers:{
                5:{
                    sorter:false
                }}});
        }
    });
</script>