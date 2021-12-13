import grails.util.GrailsUtil

includeTargets << grailsScript("_GrailsWar")
includeTargets << grailsScript("Init")

target(main: "Creates the custom WAR file for CARE") {
    def doWithWebDescriptor = {xml ->
        def grailsEnv = System.getProperty("grails.env")
        if (grailsEnv != "development") {
            xml << {
                'security-constraint' {
                    'web-resource-collection' {
                        'web-resource-name'('CARERealm') 'url-pattern'('/*')
                    }
                    'auth-constraint' {
                        'role-name'('admin')
                    }
                }

                'login-config' { 'auth-method'('BASIC') 'realm-name'('CARERealm') }
            }

        }
    }
    depends(war)
}

setDefaultTarget(main)
