package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ImageController {

    def grailsApplication
    def static config = ConfigurationHolder.config

    def image = {ImageRequest imageRequest ->
        Class clazz = grailsApplication.getClassForName(imageRequest.className)
        Object object = clazz.get(imageRequest.id)
        byte[] image = object[imageRequest.fieldName]
        response.setContentLength(image.size());
        OutputStream out = response.getOutputStream();
        out.write(image)
        out.close();
    }
}

public class ImageRequest {
    String className;
    String fieldName;
    int id;

    public String toString() {
        "${className}:${fieldName}:${id}"
    }
}