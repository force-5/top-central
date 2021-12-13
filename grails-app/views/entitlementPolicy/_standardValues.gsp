<div id="standard-${standard}">
    <input type="hidden" value="${standard}" name="standardName"/>
    <strong>
        <a href="#" onclick="editStandard('standard-${standard}', '${standard}','${standard}')">${standard}</a>
    </strong> &nbsp;&nbsp;<a onclick="removeStandard(this);">
    <img src="${resource(dir: 'images', file: ('cross.gif'))}"/>
</a><br/>
</div>