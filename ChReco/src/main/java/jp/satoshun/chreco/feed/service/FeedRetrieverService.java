/*
 *
 */

package jp.satoshun.chreco.feed.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import jp.satoshun.chreco.feed.RssAtomFeedRetriever;
import jp.satoshun.chreco.libs.Logger;
import jp.satoshun.chreco.service.IFeedObserver;

import java.util.ArrayList;
import java.util.List;

public class FeedRetrieverService extends Service {
    private static List<SyndEntry> entryList;
    public static List<SyndEntry> getEntryList() {
        return entryList;
    }

    private IFeedObserver observer;

    private final IFeedRetrieverService.Stub binder = new IFeedRetrieverService.Stub() {
        @Override
        public void retriveSyndEntryList(List<String> feedUrlList) {
            Logger.e();

            final RssAtomFeedRetriever retriever = new RssAtomFeedRetriever();
            entryList = new ArrayList<SyndEntry>();
            
            for (String feedUrl : feedUrlList) {
                SyndFeed feed = retriever.getMostRecentNews(feedUrl);
                entryList.addAll((List<SyndEntry>) feed.getEntries());
            }

            if (observer != null) {
                try {
                    observer.notifyDataSetChanged();
                } catch(RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void setObserver(IFeedObserver target) {
            observer = target;
        }
    };

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
