<%@ page import="com.force5solutions.care.*" %>
<table>
    <tr>
        <td>Name</td>
        <td>
            <input type="text" class="inp" style="width:230px;" value="${locationInstance?.name}" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td>Required Certifications</td>
        <td>
            <g:if test="${locationInstance?.requiredCertifications?.size()<1}">
                <strong>(No Certifications required)</strong>
            </g:if>
            <g:each in="${locationInstance?.requiredCertifications}" var="ic">
                <strong>${ic}</strong> &nbsp;
            </g:each>
        </td>
    </tr>
    <tr>
        <td>Required Sponsor Certifications</td>
        <td>
            <g:if test="${locationInstance?.sponsorCertifications}">
                <g:each in="${locationInstance?.sponsorCertifications}" var="ic">
                    <strong>${ic}</strong> &nbsp;
                </g:each>
            </g:if>
            <g:else>
                <strong>(No Certifications required)</strong>
            </g:else>
        </td>
    </tr>
    <tr>
        <td>Inherited Certifications</td>
        <td>
            <g:if test="${locationInstance?.getInheritedCertifications()?.size()<1}">
                <strong>(No Certifications inherited)</strong>
            </g:if>
            <g:each in="${locationInstance?.getInheritedCertifications()}" var="ic">
                <strong>${ic}</strong> &nbsp;
            </g:each>
        </td>
    </tr>
    <tr>
        <td>Inherited Sponsor Certifications</td>
        <td>
            <g:if test="${locationInstance?.inheritedSponsorCertifications}">
                <g:each in="${locationInstance?.inheritedSponsorCertifications}" var="ic">
                    <strong>${ic}</strong> &nbsp;
                </g:each>
            </g:if>
            <g:else>
                <strong>(No Certifications required)</strong>
            </g:else>
        </td>
    </tr>
    <tr>
        <td>Notes</td>
        <td>
            <textarea name="notes" class="area" style="width:230px;height:50px;" rows="3" readonly="true">${locationInstance?.notes}</textarea>
        </td>
    </tr>

</table>

<script type="text/javascript">
    jQuery("div[title='show_title']").text("Show ${locationInstance?.name} (${locationInstance?.locationType?.type})");
</script>