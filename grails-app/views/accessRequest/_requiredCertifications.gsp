<%@ page import="org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<div class="grayBox1">
    <h3 class="heading" style="padding:10px 17px 0;">${entitlementRole}</h3>
    <table width="100%" border="0" cellspacing="10" cellpadding="0" style="border:none;">

        <tr>
            <g:if test="${ConfigurationHolder.config.showCertificationsInPublicAccessRequest.equalsIgnoreCase('true')}">
                <td align="center" valign="top" class="fonsize">
                    <table width="100%" style="border:none;" border="0" cellpadding="0" cellspacing="1"
                           bgcolor="#b7b7b7" id="justication">
                        <g:if test="${(entitlementRole.getInheritedCertifications(worker) + entitlementRole.requiredCertifications)}">
                            <g:each in="${(entitlementRole.getInheritedCertifications(worker) + entitlementRole.requiredCertifications)}"
                                    var="requiredCertification" status="i">
                                <g:set var="userCertification"
                                       value="${worker.activeCertifications?.find{it.certification == requiredCertification}}"/>
                                <tr>
                                    <td style="padding:0;"
                                        bgcolor="${userCertification ? (((userCertification?.fudgedExpiry - new Date()) < 90) ? '#f5f5ad' : '#f5f5f5') : '#f5f5ad'}">
                                        <table style="border:none;" cellpadding="0" cellspacing="0" border="0"><tr>
                                            <td width="70" style=" vertical-align:middle;text-align:center;">
                                                <g:if test="${((worker?.activeCertifications) && (requiredCertification in worker.activeCertifications*.certification))}">
                                                    <img src="${resource(dir: 'images', file: 'checked-icon.png')}"/>
                                                </g:if>
                                            </td><td valign="top"
                                                     class="contentHeadTableBody">${requiredCertification}</td></tr>
                                        </table>

                                    </td>
                                </tr>
                                <tr>
                                    <td bgcolor="#FFFFFF" style="padding:0;">
                                        <table cellpadding="0" cellspacing="0" border="0"
                                               style="background:url(${resource(dir: 'images', file: 'gray-dot.png')}) repeat-y  76px 0px;border:none;">
                                            <tr class="contentBorder">
                                                <td width="70"
                                                    style=" vertical-align:middle;text-align:center;">Required Courses</td>
                                                <td>${requiredCertification?.courses?.join(', ')} <span
                                                        class="leftHeadLight">${(userCertification) ? "Expires " + userCertification?.fudgedExpiry?.myFormat() : ''}</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </g:each>
                        </g:if>
                    </table>
                </td>
            </g:if>

            <td align="right" class="positionbox">
                <g:if test="${entitlementRole.tags && entitlementRole.tags.contains('CIP')}">
                        <a href="${ConfigurationHolder.config.EAMSUrl}" target="_blank"
                           style="color:#990000;text-decoration: underline;">
                            This is a CIP role, please request using EAMS
                        </a>
                </g:if>
                <g:else>
                    <div style="${(flash.message && !accessJustifications['accessJustification-' + entitlementRole.id]) ? 'border: 1px solid red;' : ''}"
                         class="accessJustificationDummy textAreaLabel">Enter Justification Here</div>
                    <textarea name="accessJustification-${entitlementRole.id}"
                              style="${(flash.message && !accessJustifications['accessJustification-' + entitlementRole.id]) ? 'border: 1px solid red;' : ''}"
                              class="accessJustification textAreaBox"
                              id="accessJustification-${entitlementRole.id}">${accessJustifications['accessJustification-' + entitlementRole.id]}</textarea>
                </g:else>
            </td>
        </tr>
    </table>
</div>

