/*
 *
 */

package jp.satoshun.chreco.feed.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import jp.satoshun.chreco.IDefs;
import jp.satoshun.chreco.feed.RssAtomFeedRetriever;
import jp.satoshun.chreco.libs.Logger;
import jp.satoshun.chreco.service.IFeedObserver;

import java.util.ArrayList;
import java.util.List;

public class FeedRetrieverService extends Service implements IDefs {
    private static List<SyndEntry> entryList;
    public static List<SyndEntry> getEntryList() {
        return entryList;
    }

    private IFeedObserver observer;

    private final IFeedRetrieverService.Stub binder = new IFeedRetrieverService.Stub() {
        @Override
        public void retriveSyndEntryList(final List<String> feedUrlList) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    final RssAtomFeedRetriever retriever = new RssAtomFeedRetriever();
                    List<SyndEntry> _entryList = new ArrayList<SyndEntry>();

                    for (String feedUrl : feedUrlList) {
                        SyndFeed feed = retriever.getMostRecentNews(feedUrl);
                        _entryList.addAll((List<SyndEntry>) feed.getEntries());
                    }

                    entryList = _entryList;
                    sendMessage();
                }
            })).start();
        }

        @Override
        public void setObserver(IFeedObserver target) {
            observer = target;
        }
    };

    private void sendMessage() {
        Intent intent = new Intent(FEED_RETRIVER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        entryList = new ArrayList<SyndEntry>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.e();
        return binder;
    }
}
