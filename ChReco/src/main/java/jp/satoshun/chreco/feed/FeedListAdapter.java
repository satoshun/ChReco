package jp.satoshun.chreco.feed;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import jp.satoshun.chreco.WebViewActivity;
import jp.satoshun.chreco.feed.service.FeedServiceComponent;
import jp.satoshun.chreco.libs.Logger;
import jp.satoshun.chreco.service.IFeedObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedListAdapter extends BaseAdapter {
    private List<SyndEntry> entryList;
    private FeedServiceComponent feedComponent;
    private Activity context;

    private final IFeedObserver observer = new IFeedObserver() {
        @Override
        public void notifyDataSetChanged() {
            entryList = feedComponent.getEntryList();
            Logger.e();

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    public FeedListAdapter(final Activity context, final String[] feedUrlList) {
        this.context = context;
        entryList = new ArrayList<SyndEntry>();
        feedComponent = new FeedServiceComponent(context,
            new ArrayList(Arrays.asList(feedUrlList)), observer);

        // (new Thread(new Runnable(){
        //     @Override
        //     public void run() {
        //         SyndFeed feed;
        //         //  TODO
        //         for (String feedUrl : feedUrlList) {
        //             feed = feedRetriever.getMostRecentNews(feedUrl);
        //             for (Object entry : feed.getEntries()) {
        //                 entryList.add((SyndEntry) entry);
        //             }
        //         }
        //         context.runOnUiThread(new Runnable() {
        //             @Override
        //             public void run() {
        //                 notifyDataSetChanged();
        //             }
        //         });
        //     }
        // })).start();

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
