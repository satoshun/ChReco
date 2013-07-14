package jp.co.satoshun.chreco.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FeedRetrieverService extends Service {
    private final IFeedRetrieverService.Stub binder = new IFeedRetrieverService.Stub(){
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
