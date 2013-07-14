package jp.co.satoshun.chreco.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

import java.util.ArrayList;
import java.util.List;

public class FeedRetrieverService extends Service {
    private List<SyndEntry> entryList;

    private final IFeedRetrieverService.Stub binder = new IFeedRetrieverService.Stub() {
        public List<SyndEntry> getSyndEntryList() {
            return entryList;
        }

        public void renewSyndEntryList() {
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
        return binder;
    }

    // @Override
    // public int onStartCommand(Intent intent, int flags, int startId) {
    //     return super.onStartCommand(intent, flags, startId);
    // }
}
