package com.force5solutions.care.feed

class CIPFeedVO {

    Long pernr
    Date dateCompleted
    String courseNumber

    CIPFeedVO(){}

    CIPFeedVO(Long pernr, Date dateCompleted, String courseNumber){
        this.pernr = pernr
        this.dateCompleted = dateCompleted
        this.courseNumber = courseNumber
    }

    String toString(){
        return ("${pernr} : ${dateCompleted} : ${courseNumber}")
    }

}
