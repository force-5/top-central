package com.force5solutions.care.cc

import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.common.Secured

class LocationController {

    def index = {
        redirect(action: list, params: params)
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']
    def newTree = {
        Location location
        if (params.id) {
            location = Location.get(params.id)
        }
        if (!location) {//start with root node
            location = Location.list().find {
                !it.parent
            };
        }
        Set<Certification> physicalRemainingCertifications = Certification.list()
        [locationTypes: LocationType.list(), location: location,
                physicalSelectedCertifications: [] as List,
                physicalRemainingCertifications: physicalRemainingCertifications]
    }

    def getLocationTree = {
        params.ajax = true
        render(care.showTree(id: params.id))
    }

    def list = {
        redirect(action: 'newTree')
    }

    @Secured(value = Permission.READ_LOCATION)
    def showLocationWithId = {
        Location locationInstance = Location.get(params.id)
        if (locationInstance) {
            List<Location> children = Location.findAllByParent(locationInstance)
            Set<Certification> inheritedCertifications = locationInstance.getInheritedCertifications()
            render(template: 'showLocation', model: [locationInstance: locationInstance,
                    children: children,
                    inheritedCertifications: inheritedCertifications,
                    requiredCertifications: locationInstance.requiredCertifications,
                    sponsorCertifications: locationInstance.sponsorCertifications
            ])
        }
        render ""
    }

    @Secured(value = Permission.UPDATE_LOCATION)
    def editLocationWithId = {
        LocationCommand locationCommand = new LocationCommand()
        Location locationInstance = Location.get(params.id)

        if (!locationInstance) {
            flash.message = "Location not found with id ${params.id}"
            redirect(action: list)
        }
        else {
            locationCommand = createLocationCommand(locationInstance)
            render(template: '/location/editFormPopup', model: createModelForLocation(locationCommand))
        }

    }

    def updateLocationWithId = {LocationCommand locationCommand ->
        if (!locationCommand.hasErrors()) {
            Location locationInstance = createLocationInstance(locationCommand)
            if (locationInstance && locationInstance.s()) {
                updateRequiredCertificationsForChildren(locationInstance)
                render(template: '/location/successfullOperation', model: ['locationInstance': locationCommand])
                return
            }
        }
        render(template: '/location/editFormPopup', model: createModelForLocation(locationCommand))
    }

    def createLocationWithId = {
        LocationCommand locationCommand = new LocationCommand()
        if (params.locationTypeId) {
            LocationType locationType1 = LocationType.get(params.locationTypeId)
            locationCommand.locationType = locationType1?.id
            //            locationCommand.areDevicesRequired = locationType1?.areDevicesRequired
        }
        if (params.parentId) {
            locationCommand.parent = params.parentId
        }
        render(template: '/location/createFormPopup', model: createModelForLocation(locationCommand))
    }

    def saveLocationWithId = {LocationCommand locationCommand ->
        if (!locationCommand.hasErrors()) {
            Location locationInstance = createLocationInstance(locationCommand)
            if (locationInstance && locationInstance.s()) {
                locationCommand.id = locationInstance.id
                if (params.requestFrom?.equals('newTree')) {
                    render(care.showTree(id: locationCommand?.parent?.toInteger()))
                } else {
                    render(template: '/location/successfullOperation', model: ['locationInstance': locationCommand, menuLevel: locationInstance?.locationType?.type?.replaceAll(" ", "")])
                }
                return
            }
        }
        render(template: '/location/createFormPopup', model: createModelForLocation(locationCommand))
    }


    @Secured(value = Permission.DELETE_LOCATION)
    def deleteLocationWithId = {
        Location locationInstance = Location.get(params.id)
        if (locationInstance && locationInstance?.childLocations?.size() < 1) {
            LocationCommand locationCommand = createLocationCommand(locationInstance)
            try {
                locationInstance.locationType
                locationInstance.delete(flush: true)
                render(template: '/location/successfullOperation', model: ['locationInstance': locationCommand])
                return
            }
            catch (ex) {
                redirect(action: 'deleteError')
                return
            }
        }
        else {
            render("Child locations exist for this location. So it cannot be deleted.")
        }
    }

    def deleteError = {
        render("Location could not be deleted. (Some personnel might have access to this location)")
    }

    def cancel = {
        if (params.id) {
            redirect(action: 'show', id: params.id)
        } else {
            redirect(action: 'list')
        }
    }

    private Map createModelForLocation(LocationCommand locationCommand) {
        List<LocationType> locationTypes = LocationType.list()
        List<Location> parentLocations = Location.list()
        Location parentLocation = locationCommand.parent ? Location.findById(locationCommand.parent.toLong()) : null
        LocationType locationTypeInstance = locationCommand.locationType ? LocationType.findById(locationCommand.locationType.toLong()) : null
        Set<Certification> inheritedCertifications = []
        Set<Certification> inheritedSponsorCertifications = []
        if (parentLocation) {
            Location p = new Location()
            p.locationType = locationTypeInstance
            p.parent = parentLocation
            inheritedCertifications = p.inheritedCertifications
            inheritedSponsorCertifications = p.inheritedSponsorCertifications

        }
        Set<Certification> remainingCertifications = (Certification.list() - inheritedCertifications).
                findAll {!(it.id in locationCommand.requiredCertifications)}
        Set<Certification> remainingSponsorCertifications = (Certification.list() - inheritedSponsorCertifications).
                findAll {!(it.id in locationCommand.sponsorCertifications)}
        Set<Certification> requiredCertifications = Certification.list().findAll {it.id in locationCommand.requiredCertifications}
        Set<Certification> sponsorCertifications = Certification.list().findAll {it.id in locationCommand.sponsorCertifications}
        return [
                'locationInstance': locationCommand,
                'locationTypeInstance': locationTypeInstance,
                'remainingCertifications': remainingCertifications,
                'remainingSponsorCertifications': remainingSponsorCertifications,
                'requiredCertifications': requiredCertifications,
                'sponsorCertifications': sponsorCertifications,
                'inheritedCertifications': inheritedCertifications,
                'inheritedSponsorCertifications': inheritedSponsorCertifications
        ]
    }

    def createLocationInstance(LocationCommand locationCommand) {
        Location location = new Location()
        if (locationCommand.id) {
            location = Location.findById(locationCommand.id?.toLong())
        }

        location.locationType =
            locationCommand.locationType ? LocationType.findById(locationCommand.locationType.toLong()) : null
        location.parent =
            locationCommand.parent ? Location.findById(locationCommand.parent.toLong()) : null
        location.name = locationCommand?.name
        location.notes = locationCommand?.notes

        location.requiredCertifications =
            locationCommand.requiredCertifications ? Certification.findAllByIdInList(locationCommand.requiredCertifications) : []
        location.sponsorCertifications =
            locationCommand.sponsorCertifications ? Certification.findAllByIdInList(locationCommand.sponsorCertifications) : []
        return location
    }

    def createLocationCommand = {Location location ->
        LocationCommand locationCommand = new LocationCommand()
        locationCommand.id = location?.id
        locationCommand.locationType = location.locationType.id
        locationCommand.parent = location?.parent?.id
        locationCommand.name = location?.name
        locationCommand.notes = location?.notes
        locationCommand.requiredCertifications = location.requiredCertifications*.id
        locationCommand.sponsorCertifications = location.sponsorCertifications*.id
        return locationCommand
    }

    //  Remove certifications from children that are now in the list of requiredCertifications of current Location
    void updateRequiredCertificationsForChildren(Location currentLocation) {
        currentLocation.childLocations.each {Location locationInstance1 ->
            locationInstance1.requiredCertifications = locationInstance1.requiredCertifications.findAll {!(it.toString() in currentLocation.requiredCertifications*.toString())} as Set
            locationInstance1.s()
        }
        currentLocation.childLocations.each {Location locationInstance1 ->
            locationInstance1.sponsorCertifications = locationInstance1.sponsorCertifications.findAll {!(it.toString() in currentLocation.sponsorCertifications*.toString())} as Set
            locationInstance1.s()
        }
    }

}

class LocationCommand {
    String id
    String name
    String notes
    String locationType
    String parent
    String devices

    def requiredCertifications = []
    def sponsorCertifications = []

    static constraints = {
        name(blank: false, validator: {val, obj ->
            List<Location> locations = Location.findAllByName(obj.name)
            if (obj.parent) {
                locations = locations.findAll {
                    obj.parent?.toLong() in it?.parent.id
                }
            }
            if ((locations?.size() == 1) && (locations.first().id != obj.id?.toLong())) {
                return "default.not.unique.message"
            }
        })
        notes(nullabel: true, blank: true)
        locationType(blank: false)
        parent(nullabel: true, blank: true)
    }

    void setRequiredCertifications(val) {
        requiredCertifications = []
        if (val) {
            requiredCertifications = ([val]).flatten()*.toLong()
        }
    }

    void setSponsorCertifications(val) {
        sponsorCertifications = []
        if (val) {
            sponsorCertifications = ([val]).flatten()*.toLong()
        }
    }
}