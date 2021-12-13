<div class="container role " style="float:left;">
    <div class="rolesbox">Roles</div>
    <div class="accessRequestTreeView treeview-red" id="red">
        <care:showRolesInTreeView tree="${availableRoleMap}" entitlementRoleIds="${entitlementRoleIds}"/>
    </div>
</div>
<div class="low container" style="padding-top: 85px;width:25%; float:left; text-align:center;">
    <input name="left2right" value="Add >" type="button" class="buttonAdd" onclick="addToSelected()"/>
    <input name="right2left" value="< Remove" type="button" class="buttonRemove" onclick="removeFromSelected()"/>
</div>
<div class="container role" style="float:right; ">
    <div class="rolesbox">Your Request</div>
    <div class="selectedAccessRequestTreeView">
        <ul>
            <care:showSelectedRolesInTreeView entitlementRoles="${entitlementRoles}"/>
        </ul>
    </div>
</div>
<script type="text/javascript">
    function showDescription() {
        jQuery('#entitlementRolesDescriptionTable li').hide();
        jQuery.each(jQuery('li.selectedEntitlementRoles', '.accessRequestTreeView'), function() {
            var x = jQuery(this).attr('rel').split('_')[1];
            var y = '#entitlementRolesDescriptionTable .' + x;
            jQuery(y).show();
        });
        if (jQuery('#entitlementRolesDescriptionTable li:visible').size() > 0) {
            jQuery('#entitlementRolesDescriptionTable li:first').show();
        }
    }

    function addEntitlementRoleIds() {
        jQuery('li.selectable', '.selectedAccessRequestTreeView').each(function() {
            var idValue = jQuery(this).attr('rel').split('_')[1];
            var hiddenInputField = '<input type="hidden" name="entitlementRoleIds" value="' + idValue + '"/>';
            jQuery('#addEntitlementRoles').append(hiddenInputField);
        })
    }
    function addToSelected() {
        jQuery('li.selectedEntitlementRoles', '.accessRequestTreeView').each(function() {
            var role = jQuery(this);
            var relValue = jQuery(role).attr('rel');
            if (jQuery("li[rel=" + relValue + "]", '.selectedAccessRequestTreeView').length < 1) {
                var selectedString = "";
                var elementsList = jQuery(role).parents('li');
                for (var counter = 1; counter < elementsList.length - 1; counter++) {
                    var elementName = jQuery(elementsList[counter]).children().filter('span').text();
                    selectedString = elementName + "/" + selectedString;
                }
                selectedString += jQuery(role).text();
                var elementId = jQuery(role).attr('rel');
                var elementToAdd = "<li class='selectable' rel='" + elementId + "'>" + selectedString + "</li>";
                jQuery('.selectedAccessRequestTreeView>ul').append(elementToAdd);
            }
            jQuery(this).removeClass('selectedEntitlementRoles').hide();
            jQuery('li.selectable', '.selectedAccessRequestTreeView').unbind('click').bind('click', selectItem);
            jQuery('li.selectable', '.selectedAccessRequestTreeView').disableSelection();
        })
    }

    function removeFromSelected() {
        jQuery('li.selectedEntitlementRoles', '.selectedAccessRequestTreeView').each(function() {
            var selectedElementId = jQuery(this).attr('rel');
            jQuery("li[rel=" + selectedElementId + "]").show();
            jQuery(this).remove();
        });
        jQuery('li.selectable', '.accessRequestTreeView').unbind('click').bind('click', selectItem);
        jQuery('li.selectable', '.accessRequestTreeView').disableSelection();
    }
    var lastElement=null;
    function selectItem(e) {
        var element = e.currentTarget;
        var contextSelector=jQuery('.selectedEntitlementRoles').parents('div.accessRequestTreeView').length>0 ?'.accessRequestTreeView':'.selectedAccessRequestTreeView';
        if (e.ctrlKey) {
            jQuery(element).toggleClass('selectedEntitlementRoles');
            lastElement = element
        } else if (e.shiftKey) {
            if (lastElement==null) {
                lastElement = element;
                jQuery(element).addClass('selectedEntitlementRoles');
            } else {
                jQuery('.selectedEntitlementRoles',contextSelector).removeClass('selectedEntitlementRoles');
                var selectable = jQuery('.selectable',contextSelector)
                var lastElementIndex = selectable.index(lastElement)
                var thisElementIndex = selectable.index(element)
                if (thisElementIndex > lastElementIndex) {
                    selectable.slice(lastElementIndex, thisElementIndex + 1).addClass('selectedEntitlementRoles');
                } else {
                    selectable.slice(thisElementIndex, lastElementIndex + 1).addClass('selectedEntitlementRoles');
                }
            }
        } else {
            jQuery('.selectedEntitlementRoles',contextSelector).removeClass('selectedEntitlementRoles');
            jQuery(element).addClass('selectedEntitlementRoles')
            lastElement = element
        }
        showDescription();
    }

    jQuery(function() {
        jQuery('li.selectable').bind('click', selectItem);
        jQuery('li.selectable').disableSelection();
    })
</script>
