package com.force5solutions.care.feed

class LocationAccessFeedVO {

    String locationName
    String badgeNumber
    Long pernr

    LocationAccessFeedVO(){}

    LocationAccessFeedVO(String locationName, String badgeNumber, Long pernr){
        this.locationName = locationName
        this.badgeNumber = badgeNumber
        this.pernr = pernr
    }

    String toString(){
        return ("${locationName} : ${badgeNumber} : ${pernr}")
    }

}