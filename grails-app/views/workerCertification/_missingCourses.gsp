<g:if test="${missingCourses.size()>0}">
        <div id="miss1" style="margin:0;">
            <div id="miss">
                <span>Missing/Expired Courses</span>
            </div>
        </div>
        <div id="background" style="margin:0;">
            <div id="soc">
                <g:each in="${missingCourses}" var="missingCourse" status="i">
                       ${missingCourse}
                    <g:if test="${i < missingCourses.size() -1}">&nbsp; <br /></g:if>
                </g:each>
            </div>
            <div id="background-bot"></div>
        </div>
</g:if>
<div id="certification-history-popup" style="display:none;">
</div>
