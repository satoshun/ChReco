package jp.co.satoshun.chreco.feed;


import android.app.Activity;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import jp.co.satoshun.chreco.feed.service.FeedServiceComponent;

import java.util.List;

public class Feed {
    private static List<SyndEntry> entryList;
    public static IFeedRetriever feedRetriever = new IFeedRetriever() {
        public void feedCallback(List<SyndEntry> _entryList) {
            entryList = _entryList;
        }
    };

    private FeedServiceComponent feedServiceComponent;

    public Feed(Activity context, List<String> feedUrlList) {
        feedServiceComponent = new FeedServiceComponent();
        feedServiceComponent.bindService(context);
        feedServiceComponent.retriveSyndEntryList(feedUrlList);
    }

    public List<SyndEntry> getEntryList() {
        return entryList;
    }

    public void unBindService(Activity context) {
        feedServiceComponent.unBindService(context);
    }
}
