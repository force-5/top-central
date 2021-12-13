<div style="margin:0 50px;font-size:14px;">
    <g:form action="sendUserResponse" method="post" enctype="multipart/form-data">
        <div style="font-size:14px;">
            <span style="float:left;">Action for approval of Entitlement Policy: <b>${task.entitlementPolicy}</b>&nbsp;&nbsp;&nbsp;&nbsp;
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
    jQuery(document).ready(function () {
        jQuery('#actionDate').datetimepicker({
            ampm:true
        });
        jQuery('.cannedSelectBox').focus();
        jQuery('form').submit(function () {
            return isStatusChangeCommentEmpty('accessJustification', 'explanation-error')
        });
    });
</script>