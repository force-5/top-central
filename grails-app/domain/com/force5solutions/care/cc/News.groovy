package com.force5solutions.care.cc

class News {

	Date dateCreated
	Date lastUpdated
	String description
	String headline

	static constraints = {
		description(maxSize: 5000)
	}
}
