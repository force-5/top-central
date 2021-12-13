package com.force5solutions.care.cc

public class CareUtilController {
  def grailsApplication

  def downloadFile = {
    Class clazz = grailsApplication.getClassForName(params.className)
    Object object = clazz.get(params.id)
    byte[] fileContent = object[params.fieldName]
    String fileName = object['fileName']
    response.setContentLength(fileContent.size())
    response.setHeader("Content-disposition", "attachment; filename=" + fileName)
    def contentType = AppUtil.getMimeContentType(fileName.tokenize(".").last().toString())
    if (contentType) {
        response.setContentType(contentType)
    }
    OutputStream out = response.getOutputStream()
    out.write(fileContent)
    out.flush()
    out.close()
  }
}