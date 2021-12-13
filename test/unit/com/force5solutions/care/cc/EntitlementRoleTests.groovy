package com.force5solutions.care.cc

import grails.test.*
import com.force5solutions.care.web.EntitlementRoleDTO
import static com.force5solutions.care.common.CareConstants.LOCATION_TYPE_BUSINESS_UNIT
import com.force5solutions.care.common.MetaClassHelper

class EntitlementRoleTests extends GrailsUnitTestCase {


    protected void setUp() {
        super.setUp()
        MetaClassHelper.enrichClasses()
        mockDomain(CcEntitlementRole)
        mockDomain(Location)
        CcEntitlementRole.mapping = {}
        mockDomain(LocationType)
        LocationType locationType = new LocationType(level: 1, type: LOCATION_TYPE_BUSINESS_UNIT).s()
        new Location(name: 'Location-1', locationType: locationType).s()

    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreate() {
        EntitlementRoleDTO entitlementRoleDTO = new EntitlementRoleDTO()
        entitlementRoleDTO.with {
            id = "1"
            name = "CcEntitlement Role-1"
            status = "ACTIVE"
            gatekeepers = "gatekeeper-1"
        }
        CcEntitlementRole entitlementRole = CcEntitlementRole.upsert(entitlementRoleDTO)
        entitlementRole.errors.allErrors.each {
            log.error it
        }
        assertNotNull(entitlementRole)
        assertEquals("1", entitlementRole.id)
        assertEquals('CcEntitlement Role-1', entitlementRole.name)
    }

    void testUpdate() {
        EntitlementRoleDTO entitlementRoleDTO = new EntitlementRoleDTO()
        entitlementRoleDTO.with {
            id = "1"
            name = "CcEntitlement Role-1"
            status = "ACTIVE"
            gatekeepers = "gatekeeper-1"
        }
        CcEntitlementRole entitlementRole = CcEntitlementRole.upsert(entitlementRoleDTO)
        entitlementRole.errors.allErrors.each {
            log.error it
        }
        assertNotNull(entitlementRole)
        assertEquals("1", entitlementRole.id)
        assertEquals('CcEntitlement Role-1', entitlementRole.name)
        assertEquals('gatekeeper-1', entitlementRole.gatekeepers)



        entitlementRoleDTO.with {
            id = "1"
            name = "CcEntitlement Role-2"
            status = "ACTIVE"
            gatekeepers = "gatekeeper-2"
        }
        entitlementRole = CcEntitlementRole.upsert(entitlementRoleDTO)
        assertEquals("1", entitlementRole.id)
        assertEquals('CcEntitlement Role-2', entitlementRole.name)
        assertEquals('gatekeeper-2', entitlementRole.gatekeepers)
    }
}
