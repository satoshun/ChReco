package jp.co.satoshun.chreco;


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

import java.util.ArrayList;
import java.util.List;

public class FeedListAdapter extends BaseAdapter {
    private List<SyndFeed> feedList;
    private SyndFeed feed;
    private Activity context;

    public FeedListAdapter(Activity context, final String[] feedUrlList) {
        this.context = context;
        feedList = new ArrayList<SyndFeed>();

        final RssAtomFeedRetriever feedRetriever = new RssAtomFeedRetriever();
        (new Thread(new Runnable(){
            @Override
            public void run() {
                for (String feedUrl : feedUrlList) {
                    feed = feedRetriever.getMostRecentNews(feedUrl);
                    feedList.add(feed);
                }
            }
        })).start();
    }

    public int getCount() {
        if (feed == null) {
            return 0;
        }
        return feed.getEntries().size();
    }

    public SyndEntry getItem(int index) {
        if (feed == null) {
            return null;
        }
        return (SyndEntry) feed.getEntries().get(index);
    }

    public long getItemId(int index) {
        return index;
    }

    public View getView(int index, View cellRenderer, ViewGroup viewGroup) {
        NewsEntryCellView newsEntryCellView = (NewsEntryCellView) cellRenderer;

        if (cellRenderer == null)
        {
            newsEntryCellView = new NewsEntryCellView();
        }

        newsEntryCellView.display(index);
        return newsEntryCellView;
    }

    public void click(int position) {
        String uri = getItem(position).getUri();
        Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
        context.startActivity(webIntent);
    }

    private class NewsEntryCellView extends TableLayout {
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView summaryTextView;

        public NewsEntryCellView() {
            super(context);
            createUI();
        }

        private void createUI() {
            setColumnShrinkable(0, false);
            setColumnStretchable(0, false);
            setColumnShrinkable(1, false);
            setColumnStretchable(1, false);
            setColumnShrinkable(2, false);
            setColumnStretchable(2, true);

            setPadding(10, 10, 10, 10);

            titleTextView = new TextView(context);
            titleTextView.setPadding(10, 10, 10, 10);
            addView(titleTextView);

            dateTextView = new TextView(context);
            dateTextView.setPadding(10, 10, 10, 10);
            addView(dateTextView);

            summaryTextView = new TextView(context);
            summaryTextView.setPadding(10, 10, 10, 10);
            addView(summaryTextView);
        }


        public void display(int index) {
            SyndEntry entry = getItem(index);
            titleTextView.setText(entry.getTitle());
            dateTextView.setText(entry.getPublishedDate().toString());
            summaryTextView.setText(entry.getDescription().getValue());
        }
    }
}
