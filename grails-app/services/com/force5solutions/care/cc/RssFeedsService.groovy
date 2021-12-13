package com.force5solutions.care.cc

import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.common.RunTimeStampHelper

class RssFeedsService {

    boolean transactional = false
    static config = ConfigurationHolder.config
    List<RssFeedsVO> rssFeedsVOs = []
    Date lastFetchTime;

    List<RssFeedsVO> getRssFeeds() {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entering RSS Feeds Service")
        Long cacheTimeOut = config.rssFeedCacheTimeOut as Long
        if (lastFetchTime) {
            if ((new Date().time - lastFetchTime.time) > cacheTimeOut) {
                log.debug("Populating RSS Data 1")
                populateRssFeeds()
            } else {
                log.debug("Returned Data From CACHE")
            }
        }
        else {
            log.debug("Populating RSS Data 1")
            populateRssFeeds()
        }
        log.debug("Leaving RSS Feed Service ${runTimeStampHelper.stamp()}")
        return rssFeedsVOs
    }

    private void populateRssFeeds() {
        if (config.rssFeedUrl) {
            try {
                String url = config.rssFeedUrl
                URL feedUrl = new URL(url)
                List feedEntries
                SyndFeedInput input = new SyndFeedInput()
                SyndFeed feed = input.build(new XmlReader(feedUrl))
                feedEntries = feed.getEntries();
                rssFeedsVOs = []
                feedEntries.each {
                    rssFeedsVOs << new RssFeedsVO(it.title, it.uri.toString(), it.publishedDate, it.description?.value)
                }
            } catch (Exception e) {
                log.error 'Exception Occurred '
            }
        }
        lastFetchTime = new Date()
    }
}
