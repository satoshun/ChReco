package jp.co.satoshun.chreco;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import jp.co.satoshun.chreco.feed.FeedListAdapter;

public class MainActivity extends Activity {
    FeedListAdapter feedListAdapter;
    Dialog dialog = null;
    String[] feedUrlList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedUrlList = getResources().getStringArray(R.array.feed_url_list);

        dialog = DialogManager.getInstance(getApplicationContext());

        setContentView(createList(this));
    }

    private View createList(Activity activity) {
        LinearLayout mainPanel = new LinearLayout(activity);
        ListView listView = new ListView(activity);

        feedListAdapter = new FeedListAdapter(activity, feedUrlList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
                feedListAdapter.click(position);
            }
        });

        listView.setAdapter(feedListAdapter);
        mainPanel.addView(listView);

        return mainPanel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        feedListAdapter.unBindService(this);
    }
}
