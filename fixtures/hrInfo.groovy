import com.force5solutions.care.feed.HrInfo
import com.force5solutions.care.feed.HrInfoFeedService

pre {
    if (HrInfo.count() < 1) {
        new HrInfoFeedService().execute()
    }
}

fixture {}
