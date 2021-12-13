<div class="dashboarddemo2-box-container">
    <div align="center" style="font-weight:bold; font-size:13px;padding-bottom:10px;">Certification Expirations</div>
    <div style="height:23px;"></div>
    <div class="dashboarddemo2-box">
        <div class="dashboarddemo2-box1"></div><div class="dashboarddemo2-box22"></div>
        <div class="dashboarddemo2-box2">
            <div id="jMonthCalendar"></div>
            <div class="dashboarddemo2-img8"></div><div class="dashboarddemo2-img9"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var events
    jQuery().ready(function() {
        var options = {
            onMonthChanging: function(dateIn) {
                jQuery.ajax(
                {
                    url:"${createLink(controller: 'dashboard', action:'calendarData')}?date=" + dateIn.toString('dd-MM-yyyy') + "&workerType=${workerType}&viewType=${viewType}",
                    async:false,
                    datatype:'json',
                    success:function(data) {
                        events = eval(data);
                        jQuery.jMonthCalendar.ReplaceEventCollection(events);
                    }
                });
                return true;
            },
            onMonthChanged: function(dateIn) {
                disableOtherMonths();
                return true;
            },
            onEventBlockOut: function(event) {
                return true;
            }
        };

        events = ${result};
        jQuery.jMonthCalendar.Initialize(options, events);
        disableOtherMonths();
    });

    function disableOtherMonths() {
        jQuery.each(jQuery('#jMonthCalendar .DateLabel'), function() {
            jQuery(this).html(jQuery(this).text());
            jQuery(this).css('padding-left', '3px');
            if (jQuery(this).parent().hasClass('Inactive')) {
                jQuery(this).css('color', 'gray');
            }
        })

        var count = jQuery('.DateLabel').size();
        var lastDate = parseInt(jQuery('.DateLabel:last').text());
        if (lastDate > 7) {
            lastDate = 1;
        }
        var more = parseInt((42 - count) / 7);
        var x = '';
        for (i = 1; i <= more; i++) {
            x += '<tr>'
            for (j = 0; j <= 6; j++) {
                x += '<td class="Inactive"><div class="DateLabel" style="color:gray;padding-left: 3px;">' + (lastDate++) + '</div></td>'
            }
            x += '</tr>'
        }
        jQuery('#CalendarBody').append(x);
    }
</script>
