package com.force5solutions.care.cc

import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.common.Secured

class NewsController {

    def index = {

    }

    @Secured(value = Permission.ADD_NEWS_AND_NOTES)
    def save = {
        News news = new News()
        news.properties = params.properties
        if (!news.hasErrors() && news.s()) {
            flash.message = "News Added Successfully"

            render(template: '/news/newsAndNotesLink', model: [news: news])
        }
        else {
            flash.message = "News not saved"
            render 'failure'
        }
    }

    def show = {
        News news = News.get(params.id)
        if (!news) {
            flash.message = "News not found"
            redirect(controller: dashboard, action: list)
        }
        else
            render(template: '/news/showNewsAndNotes', model: [news: news])
    }

    def delete = {
        News news = News.get(params.id)
        if (news) {
            try {
                news.delete(flush: true)
                render(params.id)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
             redirect(controller:'dashboard',action:'index')
            }
        } 
        else{
            redirect(controller:'dashboard',action:'index')
        }

    }


}