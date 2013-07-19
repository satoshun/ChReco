package jp.co.satoshun.chreco.feed.service;

/*
 *
 */


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import jp.co.satoshun.chreco.feed.Feed;
import jp.co.satoshun.chreco.feed.IFeedRetriever;
import jp.co.satoshun.chreco.feed.RssAtomFeedRetriever;
import jp.co.satoshun.chreco.service.IFeedRetrieverService;

import java.util.ArrayList;
import java.util.List;

public class FeedRetrieverService extends Service {
    private List<SyndEntry> entryList;
    private IFeedRetriever feedRetriever;

    private final IFeedRetrieverService.Stub binder = new IFeedRetrieverService.Stub() {
        @Override
        public void retriveSyndEntryList(List<String> feedUrlList) {
            final RssAtomFeedRetriever retriver = new RssAtomFeedRetriever();
            entryList = new ArrayList<SyndEntry>();
            for (String feedUrl : feedUrlList) {
                SyndFeed feed = retriver.getMostRecentNews(feedUrl);
                entryList.addAll((List<SyndEntry>) feed.getEntries());
            }
            feedRetriever.feedCallback(entryList);
        }

        @Override
        public void renewSyndEntryList() {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        feedRetriever = Feed.feedRetriever;
        entryList = new ArrayList<SyndEntry>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    // @Override
    // public int onStartCommand(Intent intent, int flags, int startId) {
    //     return super.onStartCommand(intent, flags, startId);
    // }
}