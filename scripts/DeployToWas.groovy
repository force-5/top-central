import grails.util.*
target(main: "Runs a Grails application in the AppEngine development environment") {
	def warFile = "C:\\care\\trunk\\care\\care.war"
	def targetDir = "C:\\Program Files\\IBM\\WebSphere\\AppServer2\\profiles\\AppSrv01\\installedApps\\intellig-8bd958Node01Cell\\care-0_1_war.ear\\care-0.1.war"
	ant.unzip(src:warFile, dest:targetDir)
	println"Created distribution at $targetDir"
}
setDefaultTarget(main)

