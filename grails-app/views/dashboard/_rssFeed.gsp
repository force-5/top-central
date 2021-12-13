<%@ page import="com.force5solutions.care.ldap.Permission" %>
<table style="width:380px;" border="0" cellpadding="0" cellspacing="0">
    <tbody><tr>
        <td width="86%" valign="bottom" align="center" class="sub-header-text2 paddingtp"><span class="font-12px"><strong>RSS Feed</strong></span></td>
        <td width="8%" valign="bottom" class="paddingtp">
            <g:if test="${care.hasPermission(permission: Permission.EDIT_RSS_FEEDS)}">
                <input type="image" onclick="showModalDialog('editRssFeed', false);"
                        src="${createLinkTo(dir: 'images', file: 'dashboarddemo2-edit.jpg')}"
                        style="border: medium none ;"/>
            </g:if>
    </tr>
    </tbody></table>

<div class="dashboarddemo2-box-container16 clr">
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box27">

            <div class="dashboarddemo2-rss">
                <div id='content1'>
                    <div id='mycustomscroll-1' class='flexcroll'>
                        <div class='lipsum' id="rssFeeds">
                            <g:each id="set1" in="${rssFeedsVOs}" var="rssFeedsVO" status="i">
                                <span id="spanrssFeed${i}" style="display:none;">${rssFeedsVO.description}</span>
                                <a id="rssFeed${i}" href="${rssFeedsVO.uri}" target="_blank" title="">${rssFeedsVO.title}</a><br/>
                            </g:each>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dashboarddemo2-img3"></div><div class="dashboarddemo2-img4"></div></div>
    </div>
    <div class="clr"></div>
    <div id="edit-rss-feed">
        <g:render template="/dashboard/editRssFeedUrl" model="[rssFeedUrl: rssFeedUrl]"/>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery.each(jQuery('#rssFeeds>a'), function() {
            var anchorId = jQuery(this).attr('id');
            var spanId = 'span' + anchorId;
            jQuery('#' + anchorId).easyTooltip({
                useElement: spanId
            });
        })
    });
</script>