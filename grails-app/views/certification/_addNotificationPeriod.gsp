<%@ page import="com.force5solutions.care.common.CentralMessageTemplate" %>
<div id="add_contractor_cert">
    <div id="head-add_contractor" title="add_title"><h3>New Notification Period</h3></div>
    <g:form id="addNotificationPeriodForm" name="addNotificationPeriodForm"
            method="post" url="${[controller:'certification', action:'addNotificationPeriod',  params: [certificationId: certificationInstance?.id]]}">
        <div class="contractor_cert-dept1 clearfix">
            <div style="text-align:center;">
                <strong>Days:</strong>
                <input name="days" id="days" type="text"/>
            </div>

            <div class="department1" style="text-align:center;">
                <br>
                <input type="submit" class="button" id="addNotificationButton" name="addNotificationButton" value="OK" onclick="return validateForm();">&nbsp;&nbsp;
                <input type="button" class="button" name="cancelButton" value="Cancel" onclick="jQuery.modal.close();">
            </div>
        </div>
    </g:form>
    <div id="close-add_contractor">
        <img src="${createLinkTo(dir: 'images', file: 'popup-bot-close1.gif')}"/>
    </div>
</div>
<script type="text/javascript">
    function validateForm() {
        jQuery("#addNotificationPeriodForm").validate({
            rules: {
                days: {
                    required: true,
                    digits: true,
                    min: 1
                }
            },
            messages: {
                days: "Enter a number greater than 0"
            }
        });
    }
</script>