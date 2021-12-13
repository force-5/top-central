package com.force5solutions.care.cc

import grails.plugin.spock.*

import com.force5solutions.care.common.MetaClassHelper
import groovy.sql.Sql
import com.force5solutions.care.feed.*

class MessagePropertiesSpec extends UnitSpec {

    HrInfoFeedService service;

    def setupSpec() {
    }

    def setup() {
    }

    def "Feed runs without problems"() {
        when:
        File f = new File ("grails-app/i18n/messages.properties")

        then:
        f.text
    }
}

