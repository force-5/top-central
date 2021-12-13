<%@ page import="com.force5solutions.care.cc.Certification; com.force5solutions.care.cc.Course; com.force5solutions.care.cc.PeriodUnit; com.force5solutions.care.*" %>

<div class="namerowbig1"><span>Name</span><span class="asterisk">*</span>
    <label style="width:225px;" class="">
        <input type="text" class="${hasErrors(bean: certificationInstance, field: 'name', 'errors ')} inp" style="width:225px;" id="name" name="name"
                value="${fieldValue(bean: certificationInstance, field: 'name')}"/>
    </label>
</div>

<div>
    <span style="float: left;">Courses</span>
    <span style="float: right; width: 240px;">
        <ui:multiSelect name="courses" from="${Course.list()}"
                noSelection="['':'(Select One)']"
                class="listbox" style="width:245px;"
                multiple="yes" optionKey="id" size="1"
                value="${certificationInstance?.courses}"/>

    </span>
</div>
<div class="namerowbig1"><span>Description</span>
    <label style="width:190px;" class="${hasErrors(bean: certificationInstance, field: 'description', 'errors')}">
        <input type="text" class="inp" style="width:190px;" id="description" name="description"
                value="${fieldValue(bean: certificationInstance, field: 'description')}"/>
    </label>
</div>
<div class="rows clearfix">
    <div class="chechbox">
        <g:checkBox name="quarterly" value="${certificationInstance?.quarterly}"/>
    </div>
    <div class="nameroticks">Quarterly</div>
</div>

<div class="namerowbig1"><span class="">Period<font color="red">*</font>
    <input type="text" class="${hasErrors(bean: certificationInstance, field: 'period', 'errors')} inp" style="width:60px;" id="period" name="period"
            value="${fieldValue(bean: certificationInstance, field: 'period')}"/>
    &nbsp; Period Unit</span>
    <label style="width:70px;" class="${hasErrors(bean: certificationInstance, field: 'periodUnit', 'errors')}">
        <g:select from="${PeriodUnit.list()}" class="listbox1" style="width:70px; text-align:center;"
                value="${PeriodUnit.getPeriodUnit(fieldValue(bean: certificationInstance, field: 'periodUnit'))}" name="periodUnit"/>
    </label>
</div>
<div class="namerowbig1"><span class="">Expiration Offset (In Days)<font color="red">*</font>
    <input type="text" class="${hasErrors(bean: certificationInstance, field: 'expirationOffset', 'errors')} inp"
            style="width:60px;" id="expirationOffset" name="expirationOffset"
            value="${fieldValue(bean: certificationInstance, field: 'expirationOffset')}"/>
</span>
</div>
<div class="rows clearfix">
    <div class="chechbox">
        <g:checkBox name="providerRequired" value="${certificationInstance?.providerRequired}"/>
    </div>
    <div class="nameroticks">Provider Required<br/>
        <input type="text" class="inp ${hasErrors(bean: certificationInstance, field: 'providers', 'errors')}"
                style="width:265px;" id="providers" name="providers"
                value="${fieldValue(bean: certificationInstance, field: 'providers')}"/>
    </div>
</div>
<div class="rows clearfix">
    <div class="chechbox">
        <g:checkBox name="subTypeRequired" value="${certificationInstance?.subTypeRequired}"/>
    </div>
    <div class="nameroticks">Sub Type Required<br/>
        <input type="text" class="inp ${hasErrors(bean: certificationInstance, field: 'subTypes', 'errors')}"
                style="width:265px;" id="subTypes" name="subTypes"
                value="${fieldValue(bean: certificationInstance, field: 'subTypes')}"/>
    </div>
</div>
<div class="rows clearfix">
    <div class="chechbox">
        <g:checkBox name="trainingRequired" value="${certificationInstance?.trainingRequired}"/>
    </div>
    <div class="nameroticks">Training Required<br/>
        <input type="text" class="inp ${hasErrors(bean: certificationInstance, field: 'trainingProviders', 'errors')}"
                style="width:265px;" id="trainingProviders" name="trainingProviders"
                value="${fieldValue(bean: certificationInstance, field: 'trainingProviders')}"/>
    </div>
</div>
<div class="rows clearfix">
    <div class="chechbox">
        <g:checkBox name="testRequired" value="${certificationInstance?.testRequired}"/>
    </div>
    <div class="nameroticks">Test Required<br/>
        <input type="text" class="inp ${hasErrors(bean: certificationInstance, field: 'testProviders', 'errors')}"
                style="width:265px;" id="testProviders" name="testProviders"
                value="${fieldValue(bean: certificationInstance, field: 'testProviders')}"/>
    </div>
</div>
<div class="rows clearfix">
    <div class="chechbox">
        <g:checkBox name="affidavitRequired" value="${certificationInstance?.affidavitRequired}"/>
    </div>
    <div class="nameroticks">Affidavit Required</div>
</div>

%{--<g:if test="${certificationInstance.id}">--}%
    %{--<div>--}%
        %{--<span style="float: left;">Notification Periods: </span>--}%
        %{--<a id="notificationButton" class="filterbutton" href="#" rel="notificationButton" onclick="showModalDialogWithPosition('addNotificationPeriod', true);--}%
        %{--return false;">--}%
            %{--<span>Add Notification Period</span>--}%
        %{--</a>--}%
        %{--<span style="float: right; width: 240px;">--}%
            %{--<div id="div" class="selected-items">--}%
                %{--<ul id="ul">--}%
                    %{--<g:each var="notificationPeriod" in="${Certification.get(certificationInstance?.id)? NotificationPeriod.findAllByCertification(Certification.get(certificationInstance?.id)) : []}">--}%
                        %{--<li>--}%
                            %{--${notificationPeriod?.encodeAsHTML()}--}%
                            %{--<a class="removeButton" onclick="removeNotification(${notificationPeriod.id}, ${certificationInstance?.id}, this);"><img src="${g.resource(dir: 'images', file: 'cross.gif')}"/></a>--}%
                        %{--</li>--}%
                    %{--</g:each>--}%
                %{--</ul>--}%
            %{--</div>--}%
        %{--</span>--}%
    %{--</div>--}%
%{--</g:if>--}%

<script type="text/javascript">
    %{--function removeNotification(notificationPeriodId, certificationId, anchor) {--}%
        %{--jQuery.post("${createLink(controller:'certification', action:'removeNotificationPeriod')}", {notificationPeriodId:notificationPeriodId, certificationId: certificationId}, function(data) {--}%
            %{--if (data == "Success") {--}%
                %{--jQuery(anchor).parent('li').remove();--}%
            %{--}--}%
        %{--});--}%
    %{--}--}%
    function showHideLabels(checkBoxId, providerId) {
        var checkBox = jQuery('input#' + checkBoxId);

        if (checkBox.attr('checked')) {
            jQuery('input#' + providerId).css('display', 'block');
        } else {
            jQuery('input#' + providerId).css('display', 'none');
        }
    }

    function enableQuarterly(isQuarterly) {
        if (isQuarterly) {
            jQuery('#period').attr('disabled', true);
            jQuery('#periodUnit').attr('disabled', true);
        } else {
            jQuery('#period').removeAttr('disabled');
            jQuery('#periodUnit').removeAttr('disabled');
        }
    }

    jQuery(document).ready(function() {
        jQuery('#trainingRequired').click(function() {
            showHideLabels('trainingRequired', 'trainingProviders');
        });
        jQuery('#testRequired').click(function() {
            showHideLabels('testRequired', 'testProviders');
        });
        jQuery('#providerRequired').click(function() {
            showHideLabels('providerRequired', 'providers');
        });
        jQuery('#subTypeRequired').click(function() {
            showHideLabels('subTypeRequired', 'subTypes');
        });
        showHideLabels('trainingRequired', 'trainingProviders');
        showHideLabels('testRequired', 'testProviders');
        showHideLabels('providerRequired', 'providers');
        showHideLabels('subTypeRequired', 'subTypes');

        jQuery('#quarterly').click(function() {
            enableQuarterly(jQuery(this).attr('checked'));
        });

//        jQuery('#notificationButton').click(function() {
//            //            enableQuarterly(jQuery(this).attr('checked'));
//        });

        enableQuarterly(jQuery('#quarterly').attr('checked'));

        if (jQuery('input.errors').size() > 0) {
            jQuery('input:text.errors:visible:first').focus();
        } else {
            jQuery('input:text:first:visible').focus();
        }

    });

</script>

