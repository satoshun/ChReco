package jp.satoshun.chreco.feed.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import jp.satoshun.chreco.libs.Logger;
import jp.satoshun.chreco.service.IFeedObserver;

import java.util.List;

public class FeedServiceComponent {
    private IFeedRetrieverService feedRetrieverService;
    private List<String> feedUrlList;

    public FeedServiceComponent(final Activity context,
        List<String> feedUrlList) {
        bindService(context);
        this.feedUrlList = feedUrlList;
    }

    private ServiceConnection feedRetrieverConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.e();
            feedRetrieverService = IFeedRetrieverService.Stub.asInterface(service);
            retriveSyndEntryList(feedUrlList);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.e();
            feedRetrieverService = null;
        }
    };

    public void bindService(final Activity context) {
        boolean result = context.bindService(new Intent(context, FeedRetrieverService.class),
            feedRetrieverConnection, Context.BIND_AUTO_CREATE);
        Logger.e(String.valueOf(result));
    }

    public void unBindService(Activity context) {
        if (feedRetrieverService != null) {
            context.unbindService(feedRetrieverConnection);
        }
    }

    public void retriveSyndEntryList(List<String> feedUrlList) {
        try {
            if (feedRetrieverService != null) {
                Logger.e();
                feedRetrieverService.retriveSyndEntryList(feedUrlList);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<SyndEntry> getEntryList() {
        return FeedRetrieverService.getEntryList();
    }
}
