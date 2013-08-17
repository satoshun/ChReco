package jp.satoshun.chreco.tests;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

import java.util.List;

import jp.satoshun.chreco.MainActivity;
import jp.satoshun.chreco.libs.User;

public class FeedListAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity context;

    public FeedListAdapterTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public void setUp() {
        context = getActivity();
    }

    public void testUserId() throws Exception {
    }


    private List<SyndEntry> getEntryList() {
        return null;
    }

}
