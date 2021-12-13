import com.force5solutions.care.cc.News
pre {
    if (!News.count()) {
        println "Populating Sample News"
        populateSampleNews();
    }

}

fixture {}

void populateSampleNews() {
    (1..15).each {
        News news = new News()
        news.headline = "News And Notes Comes Here $it"
        news.description = "They Are Added By Users and Have a Title and Body $it"
        news.s()
    }
}

