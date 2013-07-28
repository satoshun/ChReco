package jp.satoshun.chreco;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import jp.satoshun.chreco.feed.FeedListAdapter;
import jp.satoshun.chreco.libs.Logger;

public class MainActivity extends Activity {
    FeedListAdapter feedListAdapter;
    Dialog dialog = null;
    String[] feedUrlList;

    private BroadcastReceiver mFinishRetriverReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isFinishing()) {
                Logger.e();
                feedListAdapter.sendNotifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dialog == null) {
            allocDialog();
        }

        if (feedUrlList == null || feedListAdapter == null) {
            allocFeedListAdapter();
        }

        setBroadCastReceiver();
        setContentView(createList(this));
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseFeedListAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        releaseBroadCastReceiver();
        releaseFeedListAdapter();
    }

    private void setBroadCastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mFinishRetriverReceiver, new IntentFilter("finish-feed-retriever"));
    }

    private void releaseBroadCastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mFinishRetriverReceiver);
    }

    private View createList(final Activity context) {
        LinearLayout mainPanel = new LinearLayout(context);
        ListView listView = new ListView(context);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
                feedListAdapter.click(position);
            }
        });

        listView.setAdapter(feedListAdapter);
        mainPanel.addView(listView);

        return mainPanel;
    }

    private void allocFeedListAdapter() {
        feedUrlList = getResources().getStringArray(R.array.feed_url_list);
        feedListAdapter = new FeedListAdapter(this, feedUrlList);
    }

    private void releaseFeedListAdapter() {
        if (feedListAdapter != null) {
            feedListAdapter.unBindService(this);
        }
    }

    private void allocDialog() {
        dialog = DialogManager.getInstance(this);
    }
}
