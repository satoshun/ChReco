package jp.co.satoshun.chreco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import jp.co.satoshun.chreco.service.FeedServiceComponent;

public class MainActivity extends Activity {
    FeedServiceComponent feedService = new FeedServiceComponent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createList(this));

        feedService.bindService(this);
    }

    private View createList(Activity activity) {
        LinearLayout mainPanel = new LinearLayout(activity);
        ListView listView = new ListView(activity);

        String[] feedUrlList = getResources().getStringArray(R.array.feed_url_list);
        final FeedListAdapter feedListAdapter = new FeedListAdapter(activity, feedUrlList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.setData(feedListAdapter.getUri(position));
                startActivity(i);
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
        feedService.unBindService(this);
    }
}
