function showLocation(locationId, url) {
     jQuery.post(url,
     {'id':locationId},
             function(htmlText) {
                 jQuery('#show_location_content').html(htmlText);
             });
     showModalDialog('show_location', false);
     return false;
 }

 function editLocation(locationId, url) {
     jQuery.post(url,
     {'id':locationId},
             function(htmlText) {
                 jQuery('#edit_location_content').html(htmlText);
             });
     showModalDialog('edit_location', false);
     return false;
 }

 function updateLocationTree(htmlResponse) {
     var result = htmlResponse.responseText;
     if (result.startsWith('<div id=')) {
         jQuery('#successful_operation').html(result);
         var newId = jQuery('#location_id').text();
         var newName = jQuery('#location_name').text();
         jQuery("a[rel='" + newId + "'] >span").text(newName);
         jQuery('#successful_operation').html('');
         jQuery.modal.close();
     } else {
         jQuery('#edit_location_content').html(result);
     }
 }

 function deleteLocation(locationId, url) {
     jQuery.post(url,
     {'id':locationId},
             function(htmlResponse) {
                 var result = htmlResponse;
                 if (result.startsWith('<div id=')) {
                     jQuery('#successful_operation').html(result);
                     var deletedId = jQuery('#location_id').text();
                     var y = jQuery("a[rel='" + deletedId + "']").parent('li');
                     if(y.length>0){
                         y.remove();
                     }else{
                         y = jQuery("a[rel='" + deletedId + "']");
                         y.remove();
                     }
                     jQuery('#successful_operation').html('');
                 } else {
                     alert(result);
                 }
             });
     return false;
 }

 function createLocation(locationTypeId, parentId, url) {
     jQuery.post(url,
     {'locationTypeId':locationTypeId,'parentId':parentId},
             function(htmlText) {
                 jQuery('#create_location_content').html(htmlText);
                 jQuery("form#createLocationForm").
                        prepend("<input type='hidden' name='requestFrom' value='newTree' ");
             });
     showModalDialog('create_location', false);

     return false;
 }

 function saveLocation(htmlResponse) {
     var result = htmlResponse.responseText;
     if (result.startsWith('<div id=')) {
         jQuery('div#container_id').html(result);
         jQuery.modal.close();
     } else {
         jQuery('#create_location_content').html(result);
         jQuery("form#createLocationForm").
                prepend("<input type='hidden' name='requestFrom' value='newTree' ");
     }
 }
