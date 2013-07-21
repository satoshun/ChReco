package jp.satoshun.chreco.feed.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import jp.satoshun.chreco.service.IFeedObserver;
import jp.satoshun.chreco.service.IFeedRetrieverService;

import java.util.List;

public class FeedServiceComponent {
    private IFeedRetrieverService feedRetrieverService;
    private List<String> feedUrlList;

    public FeedServiceComponent(final Activity context, List<String> feedUrlList) {
        bindService(context);
        this.feedUrlList = feedUrlList;
    }

    private ServiceConnection feedRetrieverConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            feedRetrieverService = IFeedRetrieverService.Stub.asInterface(service);
            retriveSyndEntryList(feedUrlList);
        }
        public void onServiceDisconnected(ComponentName name) {
            feedRetrieverService = null;
        }
    };

    public void bindService(Activity context) {
        context.bindService(new Intent(context.getApplicationContext(), FeedRetrieverService.class), feedRetrieverConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(Activity context) {
        if (feedRetrieverService != null) {
            context.unbindService(feedRetrieverConnection);
        }
    }

    public void retriveSyndEntryList(List<String> feedUrlList) {
        try {
            if (feedRetrieverService != null) {
                feedRetrieverService.retriveSyndEntryList(feedUrlList);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
     * set service
     */
    public void setObserver(IFeedObserver target) {
        if (feedRetrieverService != null) {
            try {
                feedRetrieverService.setObserver(target);
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public List<SyndEntry> getEntryList() {
        return FeedRetrieverService.getEntryList();
    }
}
