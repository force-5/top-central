package com.force5solutions.care.feed

import groovy.sql.GroovyRowResult

class HrInfoFeedVO {

    Long pernr
    String slid
    String FULL_NAME
    String FIRST_NAME
    String LAST_NAME
    String PERSON_STATUS
    String ORGUNIT_NUM
    String ORGUNIT_DESC
    String workerNumber
    String POSITION_TITLE
    String PERS_AREA_NUM
    String PERS_AREA_DESC
    String PERS_SUBAREA_NUM
    String PERS_SUBAREA_DESC
    String OFFICE_PHONE_NUM
    String CELL_PHONE_NUM
    String PAGER_NUM
    String supervisorSlid
    String SUPV_FULL_NAME
    String SUPVSUPV_SLID_ID
    String SUPVSUPV_FULL_NAME
    Date UPDATE_DT
    Date INSERT_DT

    HrInfoFeedVO() {}

    HrInfo toHrInfo() {
        HrInfo hrInfo = new HrInfo()
        hrInfo.pernr = pernr
        hrInfo.slid = slid
        hrInfo.FULL_NAME = FULL_NAME
        hrInfo.FIRST_NAME = FIRST_NAME
        hrInfo.LAST_NAME = LAST_NAME
        hrInfo.PERSON_STATUS = PERSON_STATUS
        hrInfo.ORGUNIT_NUM = ORGUNIT_NUM
        hrInfo.ORGUNIT_DESC = ORGUNIT_DESC
        hrInfo.workerNumber = workerNumber
        hrInfo.POSITION_TITLE = POSITION_TITLE
        hrInfo.PERS_AREA_NUM = PERS_AREA_NUM
        hrInfo.PERS_AREA_DESC = PERS_AREA_DESC
        hrInfo.PERS_SUBAREA_NUM = PERS_SUBAREA_NUM
        hrInfo.PERS_SUBAREA_DESC = PERS_SUBAREA_DESC
        hrInfo.OFFICE_PHONE_NUM = OFFICE_PHONE_NUM
        hrInfo.CELL_PHONE_NUM = CELL_PHONE_NUM
        hrInfo.PAGER_NUM = PAGER_NUM
        hrInfo.supervisorSlid = supervisorSlid
        hrInfo.SUPV_FULL_NAME = SUPV_FULL_NAME
        hrInfo.SUPVSUPV_SLID_ID = SUPVSUPV_SLID_ID
        hrInfo.SUPVSUPV_FULL_NAME = SUPVSUPV_FULL_NAME
        hrInfo.UPDATE_DT = UPDATE_DT
        hrInfo.INSERT_DT = INSERT_DT
        return hrInfo
    }

    HrInfoFeedVO(GroovyRowResult rowResult) {
        pernr = rowResult.containsKey('PERNR') ? rowResult.getProperty('PERNR') as Long : null
        slid = rowResult.containsKey('SLID_ID') ? rowResult.getProperty('SLID_ID') as String : null
        FULL_NAME = rowResult.containsKey('FULL_NAME') ? rowResult.getProperty('FULL_NAME') as String : null
        FIRST_NAME = rowResult.containsKey('FIRST_NAME') ? rowResult.getProperty('FIRST_NAME') as String : null
        LAST_NAME = rowResult.containsKey('LAST_NAME') ? rowResult.getProperty('LAST_NAME') as String : null
        PERSON_STATUS = rowResult.containsKey('PERSON_STATUS') ? rowResult.getProperty('PERSON_STATUS') as String : null
        ORGUNIT_NUM = rowResult.containsKey('ORGUNIT_NUM') ? rowResult.getProperty('ORGUNIT_NUM') as String : null
        ORGUNIT_DESC = rowResult.containsKey('ORGUNIT_DESC') ? rowResult.getProperty('ORGUNIT_DESC') as String : null
        workerNumber = rowResult.containsKey('POSITION_NUM') ? rowResult.getProperty('POSITION_NUM') as String : null
        POSITION_TITLE = rowResult.containsKey('POSITION_TITLE') ? rowResult.getProperty('POSITION_TITLE') as String : null
        PERS_AREA_NUM = rowResult.containsKey('PERS_AREA_NUM') ? rowResult.getProperty('PERS_AREA_NUM') as String : null
        PERS_AREA_DESC = rowResult.containsKey('PERS_AREA_DESC') ? rowResult.getProperty('PERS_AREA_DESC') as String : null
        PERS_SUBAREA_NUM = rowResult.containsKey('PERS_SUBAREA_NUM') ? rowResult.getProperty('PERS_SUBAREA_NUM') as String : null
        PERS_SUBAREA_DESC = rowResult.containsKey('PERS_SUBAREA_DESC') ? rowResult.getProperty('PERS_SUBAREA_DESC') as String : null
        OFFICE_PHONE_NUM = rowResult.containsKey('OFFICE_PHONE_NUM') ? rowResult.getProperty('OFFICE_PHONE_NUM') as String : null
        CELL_PHONE_NUM = rowResult.containsKey('CELL_PHONE_NUM') ? rowResult.getProperty('CELL_PHONE_NUM') as String : null
        PAGER_NUM = rowResult.containsKey('PAGER_NUM') ? rowResult.getProperty('PAGER_NUM') as String : null
        supervisorSlid = rowResult.containsKey('SUPV_SLID_ID') ? rowResult.getProperty('SUPV_SLID_ID') as String : null
        SUPV_FULL_NAME = rowResult.containsKey('SUPV_FULL_NAME') ? rowResult.getProperty('SUPV_FULL_NAME') as String : null
        SUPVSUPV_SLID_ID = rowResult.containsKey('SUPVSUPV_SLID_ID') ? rowResult.getProperty('SUPVSUPV_SLID_ID') as String : null
        SUPVSUPV_FULL_NAME = rowResult.containsKey('SUPVSUPV_FULL_NAME') ? rowResult.getProperty('SUPVSUPV_FULL_NAME') as String : null
        UPDATE_DT = rowResult.containsKey('UPDATE_DT') ? rowResult.getProperty('UPDATE_DT') as Date : null
        INSERT_DT = rowResult.containsKey('INSERT_DT') ? rowResult.getProperty('INSERT_DT') as Date : null
    }

    String toString() {
        return """${pernr}:${slid}:${FULL_NAME}:
${FIRST_NAME}:${LAST_NAME}:${PERSON_STATUS}:${ORGUNIT_NUM}:${ORGUNIT_DESC}:${workerNumber}:
${POSITION_TITLE}:${PERS_AREA_NUM}:${PERS_AREA_DESC}:${PERS_SUBAREA_NUM}:${PERS_SUBAREA_DESC}:${OFFICE_PHONE_NUM}:
${CELL_PHONE_NUM}:${PAGER_NUM}:${supervisorSlid}:${SUPV_FULL_NAME}:${SUPVSUPV_SLID_ID}:${SUPVSUPV_FULL_NAME}:${UPDATE_DT}:${INSERT_DT}"""
    }

}
