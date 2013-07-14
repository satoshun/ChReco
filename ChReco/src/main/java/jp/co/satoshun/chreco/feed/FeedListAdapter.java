package jp.co.satoshun.chreco.feed;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import jp.co.satoshun.chreco.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class FeedListAdapter extends BaseAdapter {
    private List<SyndEntry> entryList;
    private SyndFeed feed;
    private Activity context;

    public FeedListAdapter(final Activity context, final String[] feedUrlList) {
        this.context = context;
        entryList = new ArrayList<SyndEntry>();

        final RssAtomFeedRetriever feedRetriever = new RssAtomFeedRetriever();
        (new Thread(new Runnable(){
            @Override
            public void run() {
                //  TODO
                for (String feedUrl : feedUrlList) {
                    feed = feedRetriever.getMostRecentNews(feedUrl);
                    for (Object entry : feed.getEntries()) {
                        entryList.add((SyndEntry) entry);
                    }
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        })).start();
    }

    @Override
    public int getCount() {
        if (entryList == null) {
            return 0;
        }
        return entryList.size();
    }

    @Override
    public SyndEntry getItem(int index) {
        if (entryList == null) {
            return null;
        }
        return entryList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View cellRenderer, ViewGroup viewGroup) {
        NewsEntryCellView newsEntryCellView = (NewsEntryCellView) cellRenderer;

        if (cellRenderer == null) {
            newsEntryCellView = new NewsEntryCellView();
        }

        newsEntryCellView.display(index);
        return newsEntryCellView;
    }

    public void click(int position) {
        Intent i = new Intent(context, WebViewActivity.class);
        i.setData(Uri.parse(getItem(position).getLink()));
        context.startActivity(i);
    }

    private class NewsEntryCellView extends TableLayout {
        private TextView titleTextView;
        private TextView summaryTextView;

        public NewsEntryCellView() {
            super(context);
            createUI();
        }

        private void createUI() {
            setTableLayout();
            addTitle();
        }

        private void setTableLayout() {
            setColumnShrinkable(0, false);
            setColumnStretchable(0, true);
            setPadding(10, 10, 10, 10);
        }

        private void addTitle() {
            titleTextView = new TextView(context);
            titleTextView.setPadding(10, 10, 10, 10);
            addView(titleTextView);
        }

        private void addSummary() {
            summaryTextView = new TextView(context);
            summaryTextView.setPadding(10, 10, 10, 10);
            addView(summaryTextView);
        }

        public void display(int index) {
            SyndEntry entry = getItem(index);
            titleTextView.setText(entry.getTitle());
            // summaryTextView.setText(entry.getDescription().getValue());
        }
    }
}
