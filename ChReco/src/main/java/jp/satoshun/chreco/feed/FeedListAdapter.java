package jp.satoshun.chreco.feed;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.satoshun.chreco.IDefs;
import jp.satoshun.chreco.R;
import jp.satoshun.chreco.WebViewActivity;
import jp.satoshun.chreco.feed.service.FeedServiceComponent;
import jp.satoshun.chreco.libs.MyHttp;
import jp.satoshun.chreco.libs.User;

public class FeedListAdapter extends BaseAdapter {
    private List<SyndEntry> entryList;
    private FeedServiceComponent feedComponent;
    private Activity context;

    public void sendNotifyDataSetChanged() {
        entryList = feedComponent.getEntryList();
        notifyDataSetChanged();
    }

    public FeedListAdapter(final Activity context, final String[] feedUrlList) {
        this.context = context;
        entryList = new ArrayList<SyndEntry>();
        feedComponent = new FeedServiceComponent(context,
            new ArrayList(Arrays.asList(feedUrlList)));
    }

    public List<SyndEntry> getEntryList() {
        return entryList;
    }

    public void unBindService(Activity context) {
        feedComponent.unBindService(context);
    }

    @Override
    public int getCount() {
        if (getEntryList() == null) {
            return 0;
        }

        return getEntryList().size();
    }

    @Override
    public SyndEntry getItem(int index) {
        if (getEntryList() == null) {
            return null;
        }
        return getEntryList().get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View cellRenderer, ViewGroup viewGroup) {
        View v = cellRenderer;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_list_cell, null);
            setText(v, index);
        }

        return v;
    }

    void setText(View v, int index) {
        TextView titleTextView = (TextView) v.findViewById(R.id.title_view);
        titleTextView.setText(getTitle(index));
    }

    private String getTitle(int index) {
        return getItem(index).getTitle();
    }

    public void click(int index) {
        Intent i = new Intent(context, WebViewActivity.class);

        i.setData(Uri.parse(getItem(index).getLink()));
        context.startActivity(i);
    }

    private void post(final int index) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> sendData = new HashMap<String, String>();
                sendData.put("user_id", User.getId(context));
                sendData.put("title", getTitle(index));
                MyHttp.post(IDefs.PUT_USER_ACCESS_URL, sendData);
            }
        })).start();
    }
}
