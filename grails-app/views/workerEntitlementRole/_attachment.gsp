<br/><br/>
<div id="${containerDivId}" >
    <div id="boxbor_left_attach">Attach Files:<br/>
        <input size="5" style="width:150px;" type="file" onchange="addSelectedFile(this, '${containerDivId}');"/>
    </div>
    <div id="boxborderAttach" class="affidavitList">
        <ul>
            <g:each in="${attachments}" var="attachment">
                <li>
                    <g:link controller="careUtil" action="downloadFile" id="${attachment.id}" params ="[className: 'com.force5solutions.care.cc.CentralDataFile', fieldName: 'bytes']">
                        <img src="${createLinkTo(dir: 'images', file: 'magnifing.gif')}" alt="Magnify" border="0"/>
                    </g:link>
                    <img src="${createLinkTo(dir: 'images', file: 'cross1.gif')}" alt="Delete" onclick='return removeThisFile(this);'/>
                    <span>${attachment.fileName}</span>
                    <g:hiddenField name="remainingAttachments" value="${attachment.id}"/>
                </li>
            </g:each>
        </ul>
    </div>
</div>
<script type="text/javascript">
    var counter1 = 0;

    function getFileNameOnly(fullName) {
        return (fullName.match(/[^\/\\]+$/));
    }

    function removeThisFile(imageTag) {
        jQuery(imageTag).parent('li').remove();
        return false;
    }

    function addSelectedFile(inputFile, containerDivId) {

        var clonedInputFile = jQuery(inputFile);
        var selectedFileName = getFileNameOnly(clonedInputFile.attr('value'));
        if (selectedFileName) {
            counter1++;
            var nameOfClonedInput = 'multiFiles' + counter1;
            clonedInputFile.attr('name', nameOfClonedInput);
            clonedInputFile.css('display', 'none');
            var ULElement = jQuery('#' + containerDivId +' .affidavitList ul:visible');
            jQuery("<li></li>").appendTo(ULElement);
            var lastLIElement = jQuery('#' + containerDivId +' .affidavitList ul li:last:visible');
            clonedInputFile.appendTo(lastLIElement);
            var crossFile = "${createLinkTo(dir:'images', file:'cross1.gif')}";
            jQuery('<span> &nbsp; &nbsp; </span><img src="' + crossFile + '" alt="Delete" onclick="return removeThisFile(this);"/>').appendTo(lastLIElement);
            jQuery("<span>&nbsp;" + selectedFileName + "</span>").appendTo(lastLIElement);

            jQuery('<input  size="5"  type="file" onchange="addSelectedFile(this,\'' + containerDivId +'\');"/>').appendTo('#' + containerDivId +' #boxbor_left_attach');
            //Remove the li elements that dont have input of type file or hidden.    **** a bug in IE7
            jQuery('#boxborderAttach li').not('li:has(input)').each(function() {
                jQuery(this).remove()
            });
            if(jQuery('#boxbor_left_attach input:file').size()==2){
              jQuery('#boxbor_left_attach input:file:last').remove();
            }
        }
    }
</script>
