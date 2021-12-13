import com.force5solutions.care.cc.Location
import com.force5solutions.care.cc.LocationType

import static com.force5solutions.care.common.CareConstants.*

fixture {

    locationTypeCareSystem(LocationType) {
        type = LOCATION_TYPE_CARE_SYSTEM
        level = 1
    }

    locationTypeCompany(LocationType) {
        type = LOCATION_TYPE_COMPANY
        parent = locationTypeCareSystem
        level = 2
    }

    locationTypeBusinessUnit(LocationType) {
        type = LOCATION_TYPE_BUSINESS_UNIT
        parent = locationTypeCompany
        level = 3
    }

    locationCareSystem(Location) {
        name = CARE_SYSTEM_LOCATION
        locationType = locationTypeCareSystem
    }

    locationCareSystemCompany(Location) {
        name = CARE_SYSTEM_COMPANY_LOCATION
        locationType = locationTypeCompany
    }

    locationCareSystemBusinessUnit(Location) {
        name = CARE_SYSTEM_BUSINESS_UNIT_LOCATION
        locationType = locationTypeBusinessUnit
    }
}
