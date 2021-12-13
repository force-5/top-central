import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.CannedResponse

pre {
    CannedResponse cannedResponse
    (1..3).each {
        cannedResponse = new CannedResponse()
        cannedResponse.taskDescription = CareConstants.CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_JUSTIFICATION
        cannedResponse.priority = it
        cannedResponse.response = "Access Request Response - " + it
        cannedResponse.responseDescription = "Dummy text for Access Request - " + it
        cannedResponse.s()
    }

    (1..2).each {
        cannedResponse = new CannedResponse()
        cannedResponse.taskDescription = CareConstants.CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_SUPERVISOR_APPROVAL
        cannedResponse.priority = it
        cannedResponse.response = "Supervisor Response - " + it
        cannedResponse.responseDescription = "Dummy text for Supervisor Response for Access Request - " + it
        cannedResponse.s()
    }

    Map revokeJustification = [:]
    revokeJustification.put('Transferred', 'Transferred to another department')
    revokeJustification.put('New Assignment', 'New assignment within his/her current position')
    revokeJustification.put('Left Voluntarily', 'Retired or left company voluntarily')
    revokeJustification.put('Fired Without Cause', 'Fired or let go from employment without cause')
    revokeJustification.put('Fired for cause', 'Fired for cause')
    revokeJustification.put('Disability', 'Long or short term disability')
    revokeJustification.put('Temporary Assignment', 'Rotational or temporary assignment')

    revokeJustification.eachWithIndex { k, v, i ->
        cannedResponse = new CannedResponse()
        cannedResponse.taskDescription = CareConstants.CANNED_RESPONSE_CENTRAL_REVOKE_REQUEST_JUSTIFICATION
        cannedResponse.priority = (++i)
        cannedResponse.response = k.toString()
        cannedResponse.responseDescription = v.toString()
        cannedResponse.s()
    }
}

fixture {
}