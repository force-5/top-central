<style type="text/css">
.workerInfoTable td {
    padding-right: 16px;
}

.workerInfoTable {
    border: none;
    margin-top: 15px;
    margin-bottom: 20px;
}

span.vendor {
    padding-right: 10px;
    font-size: 11px;
    white-space: nowrap;
}

.workerInfoTable p {
    padding: 5px;

}
</style>
<table class="workerInfoTable">
    <tr>
        <td width="25%">
            <p><span class="vendor">SLID:</span><span>${worker.slid}</span></p>
            <p><span class="vendor">First Name:</span><span>${worker.firstName}</span></p>
            <p><span class="vendor">Last Name:</span><span>${worker.lastName}</span></p>
        </td>
        <td width="35%">
            <p><span class="vendor">Title:</span><span>${hrInfo?.POSITION_TITLE ?: ''}</span></p>
            <p><span class="vendor">Company:</span><span>${hrInfo?.PERS_AREA_DESC ?: ''}</span></p>
            <p><span class="vendor">Org. Unit:</span><span>${hrInfo?.ORGUNIT_DESC ?: ''}</span></p>
        </td>
        <td>
            <p><span class="vendor">Supervisor:</span><span>${hrInfo?.SUPV_FULL_NAME ?: ''}&nbsp;${hrInfo?.supervisorSlid ? '('+hrInfo?.supervisorSlid+')': ''}</span></p>
        </td>
    </tr>
</table>
